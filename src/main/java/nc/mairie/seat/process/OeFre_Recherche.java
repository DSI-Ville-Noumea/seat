package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Fournisseurs;
import nc.mairie.technique.*;
/**
 * Process OeFre_Recherche
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
*/
public class OeFre_Recherche extends nc.mairie.technique.BasicProcess {
	private java.lang.String[] LB_FRE;
	private ArrayList listFre;
	private Fournisseurs fournisseurCourant;
	private String focus = null;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	performPB_RECHERCHE(request);
	if(getListFre().size()==0){
		getTransaction().declarerErreur("Aucun fournisseur n'a été enregistré.");
		return;
	}
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Constructeur du process OeFre_Recherche.
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public OeFre_Recherche() {
	super();
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeFre_Recherche.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_FRE_SELECT()) ? Integer.parseInt(getVAL_LB_FRE_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	Fournisseurs monFournisseur = (Fournisseurs)getListFre().get(indice);
	if(getTransaction().isErreur()){
		return false;
	}
	if(null==monFournisseur){
		monFournisseur = new Fournisseurs();
	}
	setFournisseurCourant(monFournisseur);
	// on envoie le fournisseur sélectionné
	VariableGlobale.ajouter(request,"FRE",getFournisseurCourant());
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHERCHE() {
	return "NOM_PB_RECHERCHE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	String param = getZone(getNOM_EF_RECHERCHE());
	ArrayList resultatFre = Fournisseurs.listerFournisseursNom(getTransaction(),param);
	// on remplit la liste des fournisseurs
	setListFre(resultatFre);
	if(resultatFre.size()>0){
		//les élèments de la liste 
		int [] tailles = {70};
		String [] champs = {"enscom"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G"};
		
		setLB_FRE(new FormateListe(tailles,resultatFre,champs,padding,true).getListeFormatee());
	}else{
		setLB_FRE(LBVide);
	}
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_RECHERCHE() {
	return "NOM_EF_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_RECHERCHE() {
	return getZone(getNOM_EF_RECHERCHE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FRE
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
private String [] getLB_FRE() {
	if (LB_FRE == null)
		LB_FRE = initialiseLazyLB();
	return LB_FRE;
}
/**
 * Setter de la liste:
 * LB_FRE
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
private void setLB_FRE(java.lang.String[] newLB_FRE) {
	LB_FRE = newLB_FRE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FRE
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_FRE() {
	return "NOM_LB_FRE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FRE_SELECT
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_FRE_SELECT() {
	return "NOM_LB_FRE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FRE
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_FRE() {
	return getLB_FRE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FRE
 * Date de création : (09/08/05 08:22:15)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_FRE_SELECT() {
	return getZone(getNOM_LB_FRE_SELECT());
}
	public Fournisseurs getFournisseurCourant() {
		return fournisseurCourant;
	}
	public void setFournisseurCourant(Fournisseurs fournisseurCourant) {
		this.fournisseurCourant = fournisseurCourant;
	}
	/**
	 * @return Renvoie focus.
	 */
	public String getFocus() {
		if (focus == null) {
			focus=getDefaultFocus();
		}
		return focus;
	}
	/**
	 * @param focus focus à définir.
	 */
	public void setFocus(String focus) {
		this.focus = focus;
	}
	/**
	 * @param focus focus à définir.
	 */
	public String getDefaultFocus() {
		return getNOM_EF_RECHERCHE();
	}
	public ArrayList getListFre() {
		return listFre;
	}
	public void setListFre(ArrayList listFre) {
		this.listFre = listFre;
	}
}
