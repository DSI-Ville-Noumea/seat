package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.Fre_PM;
import nc.mairie.technique.*;
/**
 * Process OeFre_PM
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
*/
public class OeFre_PM extends nc.mairie.technique.BasicProcess {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6458638005772413319L;
	private String ACTION_SUPPRESSION = "Suppression d'un fournisseur de petit matériel<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'un fournisseur de petit matériel.";
	private String ACTION_CREATION = "Création d'un fournisseur de petit matériel.";
	private String focus = null;
	public int isVide = 0;
	private ArrayList<Fre_PM> listeFrePm = null;
	private Fre_PM frePmCourant;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
//	Si liste des carburants est vide
	if (getLB_FOURNISSEURS() == LBVide) {
		ArrayList<Fre_PM> a = Fre_PM.listerFre_PM(getTransaction());
		setListeFrePm(a);
		
		if (a.size()>0){
			//les élèments de la liste
			int [] tailles = {30,15};
			String [] champs = {"libellefre","contact"};
			//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
			String [] padding = {"G","G"};
			boolean[] colonnes = {true,true};
			a = Services.trier(a,champs,colonnes);
			setListeFrePm(a);
			
			setLB_FOURNISSEURS(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
		}else{
			setLB_FOURNISSEURS(null);
		}
		setIsVide(a.size());
	}
//	 on sélectionne l'élément en cours
	if(getFrePmCourant()!=null){
		if(getFrePmCourant().getCodefre()!=null){
			addZone(getNOM_LB_FOURNISSEURS_SELECT(),String.valueOf(-1));
			for (int i = 0; i < getListeFrePm().size(); i++) {
				Fre_PM unFre_PM = getListeFrePm().get(i);
				if (unFre_PM.getCodefre().equals(getFrePmCourant().getCodefre())) {
					addZone(getNOM_LB_FOURNISSEURS_SELECT(),String.valueOf(i));
					performPB_OK(request);
					break;
				}
			}	
		}
	}
}
/**
 * Constructeur du process OeFre_PM.
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 */
public OeFre_PM() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_AJOUTER() {
	return "NOM_PB_AJOUTER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	
	//On vide la zone de saisie
	addZone(getNOM_EF_CONTACT(),"");
	addZone(getNOM_EF_LIBELLE(),"");
	addZone(getNOM_EF_OBSERVATIONS(),"");

	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIBELLE());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
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
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		//initialiseListeCarburant(request);
		
		setLB_FOURNISSEURS(LBVide);
	}
//	 on vide les zones
	addZone(getNOM_EF_CONTACT(),"");
	addZone(getNOM_EF_LIBELLE(),"");
	addZone(getNOM_EF_OBSERVATIONS(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_CONTACT(),"");
	addZone(getNOM_ST_LIBELLE(),"");
	addZone(getNOM_ST_OBSERVATIONS(),"");
	setLB_FOURNISSEURS(LBVide);
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_MODIFIER() {
	return "NOM_PB_MODIFIER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_FOURNISSEURS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_FOURNISSEURS_SELECT())) : -1);
	if (numligne == -1 || getListeFrePm().size() == 0 || numligne > getListeFrePm().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Fournisseur PM"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);

	//Récup du type d'intervalle courant
	Fre_PM unFrePm = getListeFrePm().get(numligne);
	setFrePmCourant(unFrePm);

	//Alim zones
	addZone(getNOM_EF_CONTACT(), getFrePmCourant().getContact());
	addZone(getNOM_EF_LIBELLE(), getFrePmCourant().getLibellefre());
	addZone(getNOM_EF_OBSERVATIONS(), getFrePmCourant().getObservationsfre());
	//addZone(getNOM_EF_NUM_POMPE_ATM(),getCarburantCourant().getNum_pompe_atm());
	
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_LIBELLE());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_SUPPRIMER() {
	return "NOM_PB_SUPPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_FOURNISSEURS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_FOURNISSEURS_SELECT())) : -1);
	if (numligne == -1 || getListeFrePm().size() == 0 || numligne > getListeFrePm().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Fournisseur PM"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);

	//Récup du type d'intervalle courante
	Fre_PM unFrePM = getListeFrePm().get(numligne);
	setFrePmCourant(unFrePM);

	//Alim zones
	addZone(getNOM_ST_CONTACT(), unFrePM.getContact());
	addZone(getNOM_EF_CONTACT(), unFrePM.getContact());
	addZone(getNOM_ST_LIBELLE(), unFrePM.getLibellefre());
	addZone(getNOM_EF_LIBELLE(), unFrePM.getLibellefre());
	addZone(getNOM_ST_OBSERVATIONS(), unFrePM.getObservationsfre());
	addZone(getNOM_EF_OBSERVATIONS(), unFrePM.getObservationsfre());
	
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
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
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}

	//Récup des zones saisies
	String newContact = getZone(getNOM_EF_CONTACT()).toUpperCase();
	String newObs = getZone(getNOM_EF_OBSERVATIONS().toUpperCase());
	String newLibelle = getZone(getNOM_EF_LIBELLE().toUpperCase());
		
	//Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {

		//Test si un des champs a été modifié
		if (! newLibelle.equals(getFrePmCourant().getLibellefre().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		if (! newContact.equals(getFrePmCourant().getContact().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		if (! newObs.equals(getFrePmCourant().getObservationsfre().trim())) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR995"));//"ERR995","En suppression, aucune zone n'est modifiable."
			return false;
		}
		
		//Suppression
		try{
			getFrePmCourant().supprimerFre_PM(getTransaction());
		}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {

		//Si lib contact non saisit
		if (newLibelle.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du fournisseur de PM"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_LIBELLE());
			return false;
		}
		
		//Affectation des attributs
		getFrePmCourant().setLibellefre(newLibelle);
		getFrePmCourant().setContact(String.valueOf(newContact));
		getFrePmCourant().setObservationsfre(newObs);
		
		//Modification
		getFrePmCourant().modifierFre_PM(getTransaction(),newLibelle,newObs,newContact);
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {

		//Si lib contact non saisit
		if (newLibelle.length() == 0) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","du fournisseur de petit matériel"));//"ERR008","Le libellé @ est obligatoire"
			setFocus(getNOM_EF_LIBELLE());
			return false;
		}
		
		setFrePmCourant(new Fre_PM());
		
		//Affectation des attributs
		getFrePmCourant().setLibellefre(newLibelle.toUpperCase());
		getFrePmCourant().setContact(String.valueOf(newContact));
		getFrePmCourant().setObservationsfre(newObs);
		
		//Création
		getFrePmCourant().creerFre_PM(getTransaction(),newLibelle,newObs,newContact);
		if (getTransaction().isErreur())
			return false;
		
		
		
	}

	//Tout s'est bien passé
	commitTransaction();
	//initialiseListeCarburant(request);
	// on vide les zones
	addZone(getNOM_EF_CONTACT(),"");
	addZone(getNOM_EF_LIBELLE(),"");
	addZone(getNOM_EF_OBSERVATIONS(),"");
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_CONTACT(),"");
	addZone(getNOM_ST_LIBELLE(),"");
	addZone(getNOM_ST_OBSERVATIONS(),"");
	setLB_FOURNISSEURS(LBVide);
	setFocus(null);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_CONTACT
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_CONTACT() {
	return "NOM_EF_CONTACT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_CONTACT
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_CONTACT() {
	return getZone(getNOM_EF_CONTACT());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_LIBELLE
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_LIBELLE() {
	return "NOM_EF_LIBELLE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_LIBELLE
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_LIBELLE() {
	return getZone(getNOM_EF_LIBELLE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_OBSERVATIONS
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_OBSERVATIONS() {
	return "NOM_EF_OBSERVATIONS";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_OBSERVATIONS
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_OBSERVATIONS() {
	return getZone(getNOM_EF_OBSERVATIONS());
}
	private java.lang.String[] LB_FOURNISSEURS;
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (24/04/07 08:28:15)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (24/04/07 08:28:15)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FOURNISSEURS
 * Date de création : (24/04/07 08:28:15)
 * @author : Générateur de process
 */
private String [] getLB_FOURNISSEURS() {
	if (LB_FOURNISSEURS == null)
		LB_FOURNISSEURS = initialiseLazyLB();
	return LB_FOURNISSEURS;
}
/**
 * Setter de la liste:
 * LB_FOURNISSEURS
 * Date de création : (24/04/07 08:28:15)
 * @author : Générateur de process
 */
private void setLB_FOURNISSEURS(java.lang.String[] newLB_FOURNISSEURS) {
	LB_FOURNISSEURS = newLB_FOURNISSEURS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FOURNISSEURS
 * Date de création : (24/04/07 08:28:15)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_FOURNISSEURS() {
	return "NOM_LB_FOURNISSEURS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FOURNISSEURS_SELECT
 * Date de création : (24/04/07 08:28:15)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_FOURNISSEURS_SELECT() {
	return "NOM_LB_FOURNISSEURS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FOURNISSEURS
 * Date de création : (24/04/07 08:28:15)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_FOURNISSEURS() {
	return getLB_FOURNISSEURS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FOURNISSEURS
 * Date de création : (24/04/07 08:28:15)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_FOURNISSEURS_SELECT() {
	return getZone(getNOM_LB_FOURNISSEURS_SELECT());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (24/04/07 08:24:32)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

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
 * Date de création : (24/04/07 08:36:02)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeFre_PM.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (24/04/07 08:36:02)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK() {
	return "NOM_PB_OK";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (24/04/07 08:36:02)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_FOURNISSEURS_SELECT()) ? Integer.parseInt(getVAL_LB_FOURNISSEURS_SELECT()): -1); 
		
	//récup du type d'intervalle

	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	Fre_PM monFre_Pm = getListeFrePm().get(indice);
	setFrePmCourant(monFre_Pm);
	
	addZone(getNOM_ST_OBSERVATIONS(), monFre_Pm.getObservationsfre());
	
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CONTACT
 * Date de création : (24/04/07 08:36:02)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_CONTACT() {
	return "NOM_ST_CONTACT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CONTACT
 * Date de création : (24/04/07 08:36:02)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_CONTACT() {
	return getZone(getNOM_ST_CONTACT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_LIBELLE
 * Date de création : (24/04/07 08:36:02)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_LIBELLE() {
	return "NOM_ST_LIBELLE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_LIBELLE
 * Date de création : (24/04/07 08:36:02)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_LIBELLE() {
	return getZone(getNOM_ST_LIBELLE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_OBSERVATIONS
 * Date de création : (24/04/07 08:36:02)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_OBSERVATIONS() {
	return "NOM_ST_OBSERVATIONS";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_OBSERVATIONS
 * Date de création : (24/04/07 08:36:02)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_OBSERVATIONS() {
	return getZone(getNOM_ST_OBSERVATIONS());
}
public int getIsVide() {
	return isVide;
}
public void setIsVide(int isVide) {
	this.isVide = isVide;
}
public ArrayList<Fre_PM> getListeFrePm() {
	return listeFrePm;
}
public void setListeFrePm(ArrayList<Fre_PM> listeFrePm) {
	this.listeFrePm = listeFrePm;
}
public Fre_PM getFrePmCourant() {
	return frePmCourant;
}
public void setFrePmCourant(Fre_PM frePmCourant) {
	this.frePmCourant = frePmCourant;
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
	return getNOM_LB_FOURNISSEURS();
}
}
