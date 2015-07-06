package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.ModeleInfos;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.technique.*;
/**
 * Process OeModele_Recherche
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
*/
public class OeModele_Recherche extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7172159404263484575L;
	private java.lang.String[] LB_MODELE;
	private ArrayList<ModeleInfos> listeModeles;
	private ModeleInfos modelesInfosCourant;
	private Modeles modeleCourant;
	private String focus = null;
	private String recherche ;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	recherche = (String)VariableActivite.recuperer(this,"RECHMODELE");
	VariableActivite.enlever(this,"RECHMODELE");
	if ((recherche!=null)&&(!recherche.equals(""))){
		addZone(getNOM_EF_RECHERCHE(),recherche);
		performPB_RECHERCHE(request);
	}
	// si la zone de recherche est renseignée alors on exécute le bouton de recherche
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_MODELE_SELECT()) ? Integer.parseInt(getVAL_LB_MODELE_SELECT()): -1); 
	if (indice != -1) {
		performPB_VALIDER(request);
	}else{
		performPB_RECHERCHE(request);
	}

}
/**
 * Constructeur du process OeModele_Recherche.
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 */
public OeModele_Recherche() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_ANNULER() {
	return "NOM_PB_ANNULER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE() {
	return "NOM_PB_RECHERCHE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	String param = getZone(getNOM_EF_RECHERCHE());
	ArrayList<ModeleInfos> resultat = ModeleInfos.chercherListModeleInfosTous(getTransaction(),param);
	// on remplit la liste des équipements
	setListeModeles(resultat);
	if(resultat.size()>0){
		//les élèments de la liste 
		int [] tailles = {15,8,25};
		String [] champs = {"designationmodele","version","designationtypeequip"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","G","G"};
		
		setLB_MODELE(new FormateListe(tailles,resultat,champs,padding,true).getListeFormatee());
	}else{
		setLB_MODELE(LBVide);
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_MODELE_SELECT()) ? Integer.parseInt(getVAL_LB_MODELE_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	ModeleInfos monModeleInfos = (ModeleInfos)getListeModeles().get(indice);
	//setModeleCourant(monModeles);
	//ModeleInfos monModeleInfos = ModeleInfos.chercherModeleInfos(getTransaction(),modeleCourant.getCodemodele());
	
	if (getTransaction().isErreur()){
		return false;
	}
	if(null==monModeleInfos){
		monModeleInfos = new ModeleInfos();
	}
	setModelesInfosCourant(monModeleInfos);
	
//	On met la variable activité
	VariableGlobale.ajouter(request, "MODELEINFOS", getModelesInfosCourant());
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_RECHERCHE() {
	return "NOM_EF_RECHERCHE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_RECHERCHE() {
	return getZone(getNOM_EF_RECHERCHE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_MODELE
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 */
private String [] getLB_MODELE() {
	if (LB_MODELE == null)
		LB_MODELE = initialiseLazyLB();
	return LB_MODELE;
}
/**
 * Setter de la liste:
 * LB_MODELE
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 */
private void setLB_MODELE(java.lang.String[] newLB_MODELE) {
	LB_MODELE = newLB_MODELE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_MODELE
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MODELE() {
	return "NOM_LB_MODELE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_MODELE_SELECT
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_MODELE_SELECT() {
	return "NOM_LB_MODELE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_MODELE
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_MODELE() {
	return getLB_MODELE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_MODELE
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_MODELE_SELECT() {
	return getZone(getNOM_LB_MODELE_SELECT());
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
	 * focus focus à définir.
	 * @return focus
	 */
	public String getDefaultFocus() {
		return getNOM_EF_RECHERCHE();
	}
	/**
	 * @return Renvoie listeModeles.
	 */
	public ArrayList<ModeleInfos> getListeModeles() {
		return listeModeles;
	}
	/**
	 * @param listeModeles listeModeles à définir.
	 */
	public void setListeModeles(ArrayList<ModeleInfos> listeModeles) {
		this.listeModeles = listeModeles;
	}
	/**
	 * @return Renvoie modelesCourant.
	 */
	public ModeleInfos getModelesInfosCourant() {
		return modelesInfosCourant;
	}
	/**
	 * @param modelesInfosCourant modelesInfosCourant à définir.
	 */
	public void setModelesInfosCourant(ModeleInfos modelesInfosCourant) {
		this.modelesInfosCourant = modelesInfosCourant;
	}
	/**
	 * @return Renvoie modeleCourant.
	 */
	public Modeles getModeleCourant() {
		return modeleCourant;
	}
	/**
	 * @param modeleCourant modeleCourant à définir.
	 */
	public void setModeleCourant(Modeles modeleCourant) {
		this.modeleCourant = modeleCourant;
	}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (10/06/05 10:56:49)
 * author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (17/06/05 15:28:55)
 * author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeModele_Recherche.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_ENTREE
 * Date de création : (17/06/05 15:28:55)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_ENTREE() {
	return "NOM_ST_ENTREE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_ENTREE
 * Date de création : (17/06/05 15:28:55)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_ENTREE() {
	return getZone(getNOM_ST_ENTREE());
}
}
