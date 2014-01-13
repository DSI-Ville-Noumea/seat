package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Compteur;
import nc.mairie.technique.*;
/**
 * Process OeCompteur
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
*/
public class OeCompteur extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1133663544517208994L;
	private java.lang.String[] LB_COMPTEUR;
	private String ACTION_SUPPRESSION = "Suppression d'un type de compteur.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'un type de compteur.";
	private String ACTION_CREATION = "Création d'un type de compteur.";
	private ArrayList<Compteur> listeCompteur = null;
	private Compteur compteurCourant;
	private String focus = null;
	public int isVide = 0;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

//	Si liste des compteurs est vide
	if (getLB_COMPTEUR() == LBVide) {
		ArrayList<Compteur> a = Compteur.listerCompteur(getTransaction());
		setListeCompteur(a);
		
		if(a.size()>0){
			//les élèments de la liste
			int [] tailles = {30};
			String [] champs = {"designationcompteur"};
			boolean[] colonnes = {true};
			a = Services.trier(a,champs,colonnes);
			setListeCompteur(a);
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G"};
			
			setLB_COMPTEUR(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}else{
			setLB_COMPTEUR(null);
		}
		setIsVide(a.size());
	}
//	 on sélectionne l'élément en cours
	if(getCompteurCourant()!=null){
		if(getCompteurCourant().getCodecompteur()!=null){
			addZone(getNOM_LB_COMPTEUR_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeCompteur().size(); i++) {
				Compteur unCompteur = (Compteur)getListeCompteur().get(i);
				if (unCompteur.getCodecompteur().equals(getCompteurCourant().getCodecompteur())) {
					addZone(getNOM_LB_COMPTEUR_SELECT(),String.valueOf(i));
					break;
				}
			}	
		}
	}

}

/**
 * Constructeur du process OeCompteur.
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public OeCompteur() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	
	//On vide la zone de saisie
	addZone(getNOM_EF_DESIGNATION(),"");

	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_DESIGNATION());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (23/05/05 07:54:40)
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
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		//initialiseListeCompteur(request);
		setLB_COMPTEUR(LBVide);
	}
	// on vide les zones
	addZone(getNOM_EF_DESIGNATION(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {

//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_COMPTEUR_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_COMPTEUR_SELECT())) : -1);
	if (numligne == -1 || getListeCompteur().size() == 0 || numligne > getListeCompteur().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","compteur"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);

	//Récup du compteur courant
	Compteur compteur = (Compteur)getListeCompteur().get(numligne);
	setCompteurCourant(compteur);

	//Alim zones
	addZone(getNOM_EF_DESIGNATION(), compteur.getDesignationcompteur());
	
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_DESIGNATION());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (23/05/05 07:54:40)
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
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_COMPTEUR_SELECT()) ? Integer.parseInt(getVAL_LB_COMPTEUR_SELECT()): -1); 
		
	//récup du compteur
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Compteur monCompteur = (Compteur)getListeCompteur().get(indice);
	setCompteurCourant(monCompteur);
	
	addZone(getNOM_EF_DESIGNATION(), monCompteur.getDesignationcompteur());
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_COMPTEUR_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_COMPTEUR_SELECT())) : -1);
	if (numligne == -1 || getListeCompteur().size() == 0 || numligne > getListeCompteur().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Compteur"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);

	//Récup du compteur courant
	Compteur compteur = (Compteur)getListeCompteur().get(numligne);
	setCompteurCourant(compteur);

	//Alim zones
	addZone(getNOM_ST_DESIGNATION(), compteur.getDesignationcompteur());
	addZone(getNOM_EF_DESIGNATION(), compteur.getDesignationcompteur());
	
	setStatut(STATUT_MEME_PROCESS);	
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_VALIDER() {
	return "NOM_PB_VALIDER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}

	//Récup des zones saisies
	//TypeContact newType = (TypeContact)getListeTypeContact().get(Integer.parseInt(getZone(getNOM_LB_TYPE_CONTACT_SELECT())));
	String newDesignation = getZone(getNOM_EF_DESIGNATION()).toUpperCase();
	
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		if (! newDesignation.equals(getCompteurCourant().getDesignationcompteur().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		
		//Suppression
		try{
			getCompteurCourant().supprimerCompteur(getTransaction());
		}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du compteur"));//"ERR008","Le libellé @ est obligatoire"
			return false;
		}
		
		//Affectation des attributs
		getCompteurCourant().setDesignationcompteur(newDesignation);
		
		//Modification
		getCompteurCourant().modifierCompteur(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib contact non saisit
		if (newDesignation.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du compteur"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_DESIGNATION());
			return false;
		}
		
		setCompteurCourant(new Compteur());
		
		//Affectation des attributs
		getCompteurCourant().setDesignationcompteur(newDesignation);

		//Création
		getCompteurCourant().creerCompteur(getTransaction(),newDesignation);
		if (getTransaction().isErreur())
			return false;
	}

	//Tout s'est bien passé
	commitTransaction();
	//initialiseListeCompteur(request);
	setLB_COMPTEUR(LBVide);
	addZone(getNOM_EF_DESIGNATION(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DESIGNATION(),"");
	setFocus(null);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DESIGNATION
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DESIGNATION() {
	return "NOM_EF_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DESIGNATION
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DESIGNATION() {
	return getZone(getNOM_EF_DESIGNATION());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_COMPTEUR
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
private String [] getLB_COMPTEUR() {
	if (LB_COMPTEUR == null)
		LB_COMPTEUR = initialiseLazyLB();
	return LB_COMPTEUR;
}
/**
 * Setter de la liste:
 * LB_COMPTEUR
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
private void setLB_COMPTEUR(java.lang.String[] newLB_COMPTEUR) {
	LB_COMPTEUR = newLB_COMPTEUR;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_COMPTEUR
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_COMPTEUR() {
	return "NOM_LB_COMPTEUR";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_COMPTEUR_SELECT
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_COMPTEUR_SELECT() {
	return "NOM_LB_COMPTEUR_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_COMPTEUR
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_COMPTEUR() {
	return getLB_COMPTEUR();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_COMPTEUR
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_COMPTEUR_SELECT() {
	return getZone(getNOM_LB_COMPTEUR_SELECT());
}
	/**
	 * @return Renvoie compteurCourant.
	 */
	private Compteur getCompteurCourant() {
		return compteurCourant;
	}
	/**
	 * @param compteurCourant compteurCourant à définir.
	 */
	private void setCompteurCourant(Compteur compteurCourant) {
		this.compteurCourant = compteurCourant;
	}
	/**
	 * @return Renvoie listeCompteur.
	 */
	private ArrayList<Compteur> getListeCompteur() {
		return listeCompteur;
	}
	/**
	 * @param listeCompteur listeCompteur à définir.
	 */
	private void setListeCompteur(ArrayList<Compteur> listeCompteur) {
		this.listeCompteur = listeCompteur;
	}
	/**
	 * @param focus focus à définir.
	 */
	public String getDefaultFocus() {
		return getNOM_LB_COMPTEUR();
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
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (23/05/05 07:54:40)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_MODIFIER
		if (testerParametre(request, getNOM_PB_MODIFIER())) {
			return performPB_MODIFIER(request);
		}

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

		//Si clic sur le bouton PB_SUPPRIMER
		if (testerParametre(request, getNOM_PB_SUPPRIMER())) {
			return performPB_SUPPRIMER(request);
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
 * Date de création : (14/06/05 13:09:39)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeCompteur.jsp";
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:09:39)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DESIGNATION() {
	return "NOM_ST_DESIGNATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DESIGNATION
 * Date de création : (14/06/05 13:09:39)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DESIGNATION() {
	return getZone(getNOM_ST_DESIGNATION());
}
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
}
