package nc.mairie.seat.process;

import java.util.ArrayList;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.Modeles;
import nc.mairie.technique.*;
/**
 * Process OeEquipement
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
*/
public class OeEquipement extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_AJOUTER = 1;
	public static final int STATUT_MODIFIER = 2;
	private java.lang.String[] LB_EQUIPEMENTINFOS;
	private String ACTION_SUPPRESSION = "Mise hors service d'un équipement.<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private ArrayList listeEquipementInfos;
	private Equipement equipementCourant;
	private EquipementInfos equipementInfosCourant;
	private String tri = "numeroinventaire";
	private String param ="actifs";
	public String codeinventaire;
	public int isVide = 0;
	private java.lang.String[] LB_EQUIP_Couleurs;
	private java.lang.String[] LB_EQUIP_FCouleurs;
	private String focus = null;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
	if(unEquipementInfos!=null){
		setEquipementInfosCourant(unEquipementInfos);
	}
//	on met la date du jour par défaut
	String datedujour = Services.dateDuJour(); 
	addZone(getNOM_EF_DATEVENTEOUR(),datedujour);
//	Si liste des équipements est vide
	if (getLB_EQUIPEMENTINFOS() == LBVide || etatStatut() != STATUT_MEME_PROCESS) {
		
		// Si param = "" on liste tous les équipements
		//optimisation de luc 01/09/11
		//java.util.ArrayList a = EquipementInfos.listerEquipementInfos(getTransaction(),tri);
		java.util.ArrayList a = null; 
		// Si param = actifs on liste les équipements actifs
		if ("actifs".equals(param)){
			a = EquipementInfos.listerEquipementInfosActifs(getTransaction(),tri);
		// Si param = inactifs on liste les équipements inactifs
		}else if("inactifs".equals(param)){
			a = EquipementInfos.listerEquipementInfosInactifs(getTransaction(),tri);
		//optimisation de luc 01/09/11
		} else {
			a = EquipementInfos.listerEquipementInfos(getTransaction(),tri);
		}
		setListeEquipementInfos(a);
}				
		trier(getListeEquipementInfos(), tri);
	//}
	cocher(param,tri);
	
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
	return getNOM_EF_DATEHORSCIRCUIT();
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (27/05/05 10:53:17)
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
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	setStatut(STATUT_MODIFIER,false);
	setStatut(STATUT_AJOUTER,true);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (27/05/05 10:53:17)
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
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	je récupère le code de la ligne sélectionner
	int indice = (Services.estNumerique(getVAL_LB_EQUIPEMENTINFOS_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIPEMENTINFOS_SELECT()): -1); 
	
if (indice == -1) {
//	getTransaction().declarerErreur("Vous devez sélectionner un élement");
//	return false;
	
}else{
	EquipementInfos monEquipementInfos = (EquipementInfos)getListeEquipementInfos().get(indice);
	setEquipementInfosCourant(monEquipementInfos);
}
	if (getEquipementInfosCourant().getNumeroinventaire()== null){
		getTransaction().declarerErreur("Vous n'avez rien sélectionner");
		return false;
	}
	
	//On met la variable activité
	VariableGlobale.ajouter(request, "EQUIPEMENTINFOS", getEquipementInfosCourant());
	
	setStatut(STATUT_AJOUTER,false);
	setStatut(STATUT_MODIFIER,true);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (27/05/05 10:53:17)
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
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_EQUIPEMENTINFOS_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIPEMENTINFOS_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	EquipementInfos monEquipementInfos = (EquipementInfos)getListeEquipementInfos().get(indice);
	setEquipementInfosCourant(monEquipementInfos);
	
	Equipement monEquipement = Equipement.chercherEquipement(getTransaction(),monEquipementInfos.getNumeroinventaire());
	if (getTransaction().isErreur()){
		return false;
	}
	setEquipementCourant(monEquipement);
//	On met la variable activité
	//VariableGlobale.ajouter(request, "EQUIPEMENTINFOS", getEquipementInfosCourant());	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_SUP
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_SUP() {
	return "NOM_PB_OK_SUP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public boolean performPB_OK_SUP(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (27/05/05 10:53:17)
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
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_EQUIPEMENTINFOS_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_EQUIPEMENTINFOS_SELECT())) : -1);
	if (numligne == -1 || getListeEquipementInfos().size() == 0 || numligne > getListeEquipementInfos().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Equipement"));
		return false;
	}
	
	//On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);

	//Récup de l'entretien courant
	EquipementInfos equipementInfos = (EquipementInfos)getListeEquipementInfos().get(numligne);
	Equipement equipement = Equipement.chercherEquipement(getTransaction(),equipementInfos.getNumeroinventaire());
	setEquipementCourant(equipement);

	addZone(getNOM_ST_DATE(), equipementInfos.getDatemiseencirculation());
	addZone(getNOM_ST_IMMAT(), equipementInfos.getNumeroimmatriculation());
	addZone(getNOM_ST_INVENTAIRE(), equipementInfos.getNumeroinventaire());	
	addZone(getNOM_ST_MODELE(),equipementInfos.getDesignationmodele());
	addZone(getNOM_ST_MARQUE(), equipementInfos.getDesignationmarque());
	addZone(getNOM_ST_TYPE(),equipementInfos.getDesignationtypeequip());	
	setStatut(STATUT_MEME_PROCESS);	
	
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_DATE() {
	return "NOM_ST_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_DATE() {
	return getZone(getNOM_ST_DATE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_IMMAT
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_IMMAT() {
	return "NOM_ST_IMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_IMMAT
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_IMMAT() {
	return getZone(getNOM_ST_IMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_INVENTAIRE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_INVENTAIRE() {
	return "NOM_ST_INVENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_INVENTAIRE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_INVENTAIRE() {
	return getZone(getNOM_ST_INVENTAIRE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_EQUIPEMENT
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
private String [] getLB_EQUIPEMENTINFOS() {
	if (LB_EQUIPEMENTINFOS == null)
		LB_EQUIPEMENTINFOS = initialiseLazyLB();
	return LB_EQUIPEMENTINFOS;
}
/**
 * Setter de la liste:
 * LB_EQUIPEMENT
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
private void setLB_EQUIPEMENTINFOS(java.lang.String[] newLB_EQUIPEMENT) {
	LB_EQUIPEMENTINFOS = newLB_EQUIPEMENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_EQUIPEMENT
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_EQUIPEMENTINFOS() {
	return "NOM_LB_EQUIPEMENTINFOS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_EQUIPEMENT_SELECT
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_EQUIPEMENTINFOS_SELECT() {
	return "NOM_LB_EQUIPEMENTINFOS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_EQUIPEMENTINFOS() {
	return getLB_EQUIPEMENTINFOS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_EQUIPEMENT
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_EQUIPEMENTINFOS_SELECT() {
	return getZone(getNOM_LB_EQUIPEMENTINFOS_SELECT());
}
	/**
	 * @return Renvoie equipementCourant.
	 */
	private Equipement getEquipementCourant() {
		return equipementCourant;
	}
	/**
	 * @param equipementCourant equipementCourant à définir.
	 */
	private void setEquipementCourant(Equipement equipementCourant) {
		this.equipementCourant = equipementCourant;
	}
	/**
	 * @return Renvoie listeEquipement.
	 */
	private ArrayList getListeEquipementInfos() {
		return listeEquipementInfos;
	}
	/**
	 * @param listeEquipement listeEquipement à définir.
	 */
	private void setListeEquipementInfos(ArrayList listeEquipement) {
		this.listeEquipementInfos = listeEquipement;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (27/05/05 11:07:34)
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
 * Date de création : (27/05/05 11:07:34)
 * @author : Générateur de process
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	je récupère le code de la ligne sélectionner
	int indice = (Services.estNumerique(getVAL_LB_EQUIPEMENTINFOS_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIPEMENTINFOS_SELECT()): -1);
	
	if ("".equals(getVAL_ST_TITRE_ACTION())) {
		setStatut(STATUT_PROCESS_APPELANT);
	} else {
		//initialiseListeEquipement(request);
		setLB_EQUIPEMENTINFOS(LBVide);
	}
//	On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),"");
	
 
	// pour avoir l'équipement pour toute les actions
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	if (param.equals("actifs")){
		
	}
	EquipementInfos monEquipementInfos = (EquipementInfos)getListeEquipementInfos().get(indice);
	setEquipementInfosCourant(monEquipementInfos);
	//On met la variable activité
	VariableActivite.ajouter(this, "EQUIPEMENTINFOS", getEquipementInfosCourant());

	
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (27/05/05 11:07:34)
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
 * Date de création : (27/05/05 11:07:34)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//Si aucune action en cours
	if (getZone(getNOM_ST_TITRE_ACTION()).length() == 0) {
		setStatut(STATUT_MEME_PROCESS, true, MairieMessages.getMessage("ERR996"));//"Vous ne pouvez pas valider, il n'y a pas d'action en cours."
		return false;
	}
		
//	on récupère la zone DateHorsCircuit
	String newDateVouR = getZone(getNOM_EF_DATEVENTEOUR());
	if(!newDateVouR.equals("")){
		if(!Services.estUneDate(newDateVouR)){
			getTransaction().declarerErreur("La date de vente ou réforme n'est pas correcte.");
			setFocus(getNOM_EF_DATEVENTEOUR());
			return false;
		}
	}
	newDateVouR = Services.formateDate(newDateVouR);
	//Suppression d'un équipement signifie qu'il est inactif
	//on compare la date hors circuit avec la date de mise en circulation
	int controle = Services.compareDates(newDateVouR,equipementCourant.getDatemiseencirculation());
	if (controle>-1){
		getEquipementCourant().setDateventeoureforme(newDateVouR);
		equipementCourant.modifierEquipement(getTransaction(),getEquipementCourant().getNumeroinventaire());
	}else if (controle==-1){
		getTransaction().declarerErreur("La date hors circuit doit être supérieure à la date de mise en circulation.");
	}else{
		return false;
	}
	
	if (getTransaction().isErreur()){
		return false;
	}

//	Tout s'est bien passé
	commitTransaction();
	//initialiseListeEquipement(request);
	setLB_EQUIPEMENTINFOS(LBVide);
//	On nomme l'action
	addZone(getNOM_ST_TITRE_ACTION(),"");
	return true;

}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_IMMAT
 * Date de création : (31/05/05 10:34:30)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_IMMAT() {
	return "NOM_RB_TRI_IMMAT";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_INV
 * Date de création : (31/05/05 10:34:30)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_INV() {
	return "NOM_RB_TRI_INV";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_MARQUE
 * Date de création : (31/05/05 10:34:30)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_MARQUE() {
	return "NOM_RB_TRI_MARQUE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_MODELE
 * Date de création : (31/05/05 10:34:30)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_MODELE() {
	return "NOM_RB_TRI_MODELE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_TYPEEQUIP
 * Date de création : (31/05/05 10:34:30)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_TYPEEQUIP() {
	return "NOM_RB_TRI_TYPEEQUIP";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_TRI
 * Date de création : (31/05/05 10:38:58)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_OK_TRI() {
	return "NOM_PB_OK_TRI";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/05/05 10:38:58)
 * @author : Générateur de process
 */
public boolean performPB_OK_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
	// selon la sélection la liste sera triée
	if (getNOM_RB_TRI_INV().equals(getZone(getNOM_RG_TRI()))){
		tri = "numeroinventaire";
	}
	if (getNOM_RB_TRI_IMMAT().equals(getZone(getNOM_RG_TRI()))){
		tri = "numeroimmatriculation";
	}
	if (getNOM_RB_TRI_MARQUE().equals(getZone(getNOM_RG_TRI()))){
		tri = "designationmarque";
	}
	if (getNOM_RB_TRI_MODELE().equals(getZone(getNOM_RG_TRI()))){
		tri = "designationmodele";
	}
	if (getNOM_RB_TRI_TYPEEQUIP().equals(getZone(getNOM_RG_TRI()))){
		tri = "designationtypeequip";
	}
	if (getNOM_RB_TRI_ENSERVICE().equals(getZone(getNOM_RG_TRI()))){
		tri = "datemiseencirculation";
	}
	
	//Optimisation LUC 01/09/11
	String oldParam = param;
	// selon les équipements voulus (tous / actifs / inactifs)
	if (getNOM_RB_AFFICHAGE_INACTIFS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "inactifs";
	}
	if (getNOM_RB_AFFICHAGE_ACTIFS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "actifs";
	}
	if (getNOM_RB_AFFICHAGE_TOUS().equals(getZone(getNOM_RG_AFFICHAGE()))){
		param = "tous";
	}
	
	//optimisation luc 01/09/11
	if (! oldParam.equals(param)) {
		setLB_EQUIPEMENTINFOS(LBVide);
		//optimisation luc 01/09/11
		//java.util.ArrayList a = EquipementInfos.listerEquipementInfos(getTransaction(),tri);
/*		java.util.ArrayList a;
		// Si param = actifs on liste les équipements actifs
		if ("actifs".equals(param)){
			a = EquipementInfos.listerEquipementInfosActifs(getTransaction(),tri);
		// Si param = inactifs on liste les équipements inactifs
		}else if("inactifs".equals(param)){
			a = EquipementInfos.listerEquipementInfosInactifs(getTransaction(),tri);
		} else {	
			a = EquipementInfos.listerEquipementInfos(getTransaction(),tri);
		}
		setListeEquipementInfos(a);
*/
	}

//	trier(getListeEquipementInfos(), tri);
//	cocher(param,tri);
	
	return true;
}
public void trier2(ArrayList a,String colonne) throws Exception{
	
	//a= Services.trier(a,colonnes,ordres);
	setListeEquipementInfos(a);

	if(a.size()>0){
		//les élèments de la liste 
		int [] tailles = {5,10,10,20,15,10};
		String [] champs = {"numeroinventaire","numeroimmatriculation","designationmarque","designationmodele","designationtypeequip","datemiseencirculation"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","G","G","G","G","C"};
		
		setLB_EQUIPEMENTINFOS(new FormateListe(tailles,a,champs,padding,true).getListeFormatee());
	}else{
		setLB_EQUIPEMENTINFOS(LBVide);
	}
	setIsVide(a.size());
	return ;
}

public void trier(ArrayList a,String colonne) throws Exception{
	boolean[] ordres = {true};//,true};
	String[] colonnes = {colonne};
	if (tri.indexOf("date")>=0)
		ordres=new boolean[]{false};
	a = Services.trier(a,colonnes,ordres);
	setListeEquipementInfos(a);
	
//	Si au moins un bpc
	if (a.size() !=0 ) {
		int [] tailles = {5,10,10,20,15,10};
		String [] padding = {"G","G","G","G","G","C"};
		setLB_EQUIP_Couleurs(new String[a.size()]);
		setLB_EQUIP_FCouleurs(new String[a.size()]);
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		
		
		for (int i=0;i<a.size();i++){
			EquipementInfos unEquipInfos = (EquipementInfos)a.get(i);
			
			if (getEquipementInfosCourant()!=null){
				if (unEquipInfos.getNumeroinventaire().equals(getEquipementInfosCourant().getNumeroinventaire())){
					getLB_EQUIP_Couleurs()[i] = "white";
					getLB_EQUIP_FCouleurs()[i] = "teal";
				}
			}
			String ligne [] = { unEquipInfos.getNumeroinventaire(),unEquipInfos.getNumeroimmatriculation(),unEquipInfos.getDesignationmarque(),unEquipInfos.getDesignationmodele(),unEquipInfos.getDesignationtypeequip(),unEquipInfos.getDatemiseencirculation()};
			aFormat.ajouteLigne(ligne);
			setLB_EQUIPEMENTINFOS(aFormat.getListeFormatee());
		}		
	} else {
		setLB_EQUIPEMENTINFOS(null);
	}
	setIsVide(a.size());
	return ;
}

public void cocher(String param, String tri){
	// Selon le param on coche le bon affichage
	addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_ACTIFS());
	if ("tous".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_TOUS());
	}else if("inactifs".equals(param)){
		addZone(getNOM_RG_AFFICHAGE(),getNOM_RB_AFFICHAGE_INACTIFS());
	}
	
	//Selon le tri coche la bonne colonne
	addZone(getNOM_RG_TRI(),getNOM_RB_TRI_INV());
	if (tri.equals("numeroimmatriculation")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_IMMAT());
	}else if (tri.equals("designationmarque")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_MARQUE());
	}else if (tri.equals("designationmodele")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_MODELE());
	}else if (tri.equals("designationtypeequip")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_TYPEEQUIP());
	}else if (tri.equals("datemiseencirculation")){
		addZone(getNOM_RG_TRI(),getNOM_RB_TRI_ENSERVICE());
	}
}


/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_AFFICHAGE
 * Date de création : (31/05/05 14:05:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RG_AFFICHAGE() {
	return "NOM_RG_AFFICHAGE";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_AFFICHAGE
 * Date de création : (31/05/05 14:05:53)
 * @author : Générateur de process
 */
public java.lang.String getVAL_RG_AFFICHAGE() {
	return getZone(getNOM_RG_AFFICHAGE());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_ACTIFS
 * Date de création : (31/05/05 14:05:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_ACTIFS() {
	return "NOM_RB_AFFICHAGE_ACTIFS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_INACTIFS
 * Date de création : (31/05/05 14:05:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_INACTIFS() {
	return "NOM_RB_AFFICHAGE_INACTIFS";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_AFFICHAGE_TOUS
 * Date de création : (31/05/05 14:05:53)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_AFFICHAGE_TOUS() {
	return "NOM_RB_AFFICHAGE_TOUS";
}

	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	private EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	/**
	 * @param equipementInfosCourant equipementInfosCourant à définir.
	 */
	private void setEquipementInfosCourant(
			EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}

/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATEHORSCIRCUIT
 * Date de création : (08/06/05 07:29:19)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DATEHORSCIRCUIT() {
	return "NOM_EF_DATEHORSCIRCUIT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATEHORSCIRCUIT
 * Date de création : (08/06/05 07:29:19)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DATEHORSCIRCUIT() {
	return getZone(getNOM_EF_DATEHORSCIRCUIT());
}
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
/**
 * Constructeur du process OeEquipement.
 * Date de création : (07/07/05 13:28:38)
 * @author : Générateur de process
 */
public OeEquipement() {
	super();
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATEVENTEOUR
 * Date de création : (07/07/05 13:28:38)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DATEVENTEOUR() {
	return "NOM_EF_DATEVENTEOUR";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATEVENTEOUR
 * Date de création : (07/07/05 13:28:38)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DATEVENTEOUR() {
	return getZone(getNOM_EF_DATEVENTEOUR());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (27/05/05 10:53:17)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {
//Si clic sur le bouton PB_OK_TRI
		if (testerParametre(request, getNOM_PB_OK_TRI())) {
			return performPB_OK_TRI(request);
		}
	//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}
		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_MODIFIER
		if (testerParametre(request, getNOM_PB_MODIFIER())) {
			return performPB_MODIFIER(request);
		}

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

		//Si clic sur le bouton PB_OK_SUP
		if (testerParametre(request, getNOM_PB_OK_SUP())) {
			return performPB_OK_SUP(request);
		}

		//Si clic sur le bouton PB_SUPPRIMER
		if (testerParametre(request, getNOM_PB_SUPPRIMER())) {
			return performPB_SUPPRIMER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (25/08/05 10:29:19)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OeEquipement.jsp";
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (25/08/05 10:29:19)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (25/08/05 10:29:20)
 * @author : Générateur de process
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_ENSERVICE
 * Date de création : (25/08/05 10:29:20)
 * @author : Générateur de process
 */
public java.lang.String getNOM_RB_TRI_ENSERVICE() {
	return "NOM_RB_TRI_ENSERVICE";
}

public java.lang.String[] getLB_EQUIP_Couleurs() {
	return LB_EQUIP_Couleurs;
}
public void setLB_EQUIP_Couleurs(java.lang.String[] couleurs) {
	LB_EQUIP_Couleurs = couleurs;
}
public java.lang.String[] getLB_EQUIP_FCouleurs() {
	return LB_EQUIP_FCouleurs;
}
public void setLB_EQUIP_FCouleurs(java.lang.String[] fcouleurs) {
	LB_EQUIP_FCouleurs = fcouleurs;
}
}
