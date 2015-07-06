package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.OT;
import nc.mairie.technique.*;
/**
 * Process OeOT_Validation
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
*/
public class OeOT_Validation extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2208624490682058647L;
	public static final int STATUT_VISUALISER = 2;
	public static final int STATUT_MODIFIER = 1;
	private java.lang.String[] LB_OTAVALIDER;
	private java.lang.String[] LB_OTENCOURS;
	private ArrayList<OT> listOTEncours = new ArrayList<OT>();
	private ArrayList<OT> listOTAValider = new ArrayList<OT>();
	private Hashtable<String, Equipement> hashEquipement;
	private boolean first = true;
	public boolean AValider;
	public boolean estEnregistre = false;
	public boolean listOtEncoursVide = true;
	private String information = "";
	private EquipementInfos equipementInfosCourant;
	private OT otCourant;
	private String ACTION_MODIFICATION = "Modification";
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC 
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//LB optimisation 07/09
	if (getHashEquipement().size() == 0) {
		initialiseListeEquipements();
	}
	
	// OB mail d'octobre 07 : classer les OT comme la gestion
	//String[] colonnes = {"dateentree"};
	String[] colonnes = {"numeroot"};
	boolean ordres[] = {false};
	//	initialisation de la liste des OT en cours
	if(first){
		//ArrayList a = OTInfos.listerOTInfosAValider(getTransaction());
		ArrayList<OT> a = OT.listerOTAValider(getTransaction());
		if(getTransaction().isErreur()){
			return;
		}
		a= Services.trier(a,colonnes,ordres);
		setListOTEncours(a);
		setEstEnregistre(false);
	}
	if(getListOTEncours().size()>0){
		setListOtEncoursVide(false);
	}
	initialiseListOTEncours(request);
	//si une information à afficher (concerne la validation des Ot)
	if(!information.equals("")){
		getTransaction().declarerErreur(information);
	}
	setFirst(false);
}

/**
 * Init de la liste des équipements 
 * @throws Exception Exception
 * 
 */
public void initialiseListeEquipements() throws Exception{
	ArrayList<Equipement> arr = Equipement.listerEquipement(getTransaction());
	
	for (Iterator<Equipement> iter = arr.iterator(); iter.hasNext();) {
		Equipement equipement = (Equipement) iter.next();
		getHashEquipement().put(equipement.getNumeroinventaire(), equipement);
	}
	
}

public void initialiseListOTEncours(javax.servlet.http.HttpServletRequest request) throws Exception{
	String dentree = "";
	String dsortie = "";
	if(getListOTEncours().size()>0){
		int tailles [] = {10,5,10,10,10,10};
		String[] padding = {"D","G","G","C","C","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i = 0;i<getListOTEncours().size();i++){
			OT unOT = (OT)getListOTEncours().get(i);
//			 affichage 
			if (unOT.getDateentree().equals("01/01/0001")){
				dentree="";
			}else{
				dentree=unOT.getDateentree();
			}
			if(unOT.getDatesortie().equals("01/01/0001")){
				dsortie="";
			}else{
				dsortie=unOT.getDatesortie();
			}
			/*LB optimisation 07/09/11
			Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),unOT.getNuminv());
			if (getTransaction().isErreur()){
				return ;
			}
			*/
			Equipement unEquipement = getHashEquipement().get(unOT.getNuminv().trim());
			if (unEquipement ==  null) { 
				getTransaction().declarerErreur("Erreur : le n° d'inventaire "+unOT.getNuminv()+" n'existe pas." );
				return;
			}
			
			String ligne [] = { unOT.getNumeroot(),unOT.getNuminv(),unEquipement.getNumeroimmatriculation(),dentree,dsortie,unOT.getCompteur()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_OTENCOURS(aFormat.getListeFormatee());
	} else {
		setLB_OTENCOURS(null);
	}
	
}

public void initialiseListOTAValider(javax.servlet.http.HttpServletRequest request) throws Exception{
	String dentree = "";
	String dsortie = "";
	if(getListOTAValider().size()>0){
		setAValider(true);
		//les élèments de la liste 
		int tailles [] = {10,5,10,10,10,10};
		String[] padding = {"D","G","G","C","C","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < getListOTAValider().size() ; i++) {
			//OTInfos aOTInfos = (OTInfos)getListOTAValider().get(i);
			//OT aOT = OT.chercherOT(getTransaction(),aOTInfos.getNumeroot());
			OT aOT = (OT)getListOTAValider().get(i);	
			
			// on affiche les dates si elle sont différentes de 01/01/0001
			if (aOT.getDateentree().equals("01/01/0001")){
				dentree = "";
			}else{
				dentree = aOT.getDateentree();
			}
			if(aOT.getDatesortie().equals("01/01/0001")){
				dsortie = "";
			}else{
				dsortie = aOT.getDatesortie();
			}
			/*LB optimisation 07/09/11
			Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),aOT.getNuminv());
			if(getTransaction().isErreur()){
				return ;
			}
			*/
			Equipement unEquipement = getHashEquipement().get(aOT.getNuminv());
			if (unEquipement ==  null) { 
				getTransaction().declarerErreur("Erreur : le n° d'inventaire "+aOT.getNuminv()+" n'existe pas." );
				return;
			}
			
			String ligne [] = { aOT.getNumeroot(),aOT.getNuminv(),unEquipement.getNumeroimmatriculation(),dentree,dsortie,aOT.getCompteur()};
			aFormat.ajouteLigne(ligne);
		}
		setAValider(true);
		setLB_OTAVALIDER(aFormat.getListeFormatee());
	}else{
		setLB_OTAVALIDER(LBVide);
		setAValider(false);
	}
}

/**
 * Constructeur du process OeOT_Validation.
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 */
public OeOT_Validation() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
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
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on ajoute l'OT dans la liste à valider
	int indice = (Services.estNumerique(getVAL_LB_OTENCOURS_SELECT()) ? Integer.parseInt(getVAL_LB_OTENCOURS_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement à ajouter dans la liste des Ordres de travaux ");
		return false;
	}
	OT monOT = (OT)getListOTEncours().get(indice);
	// on ajoute dans la liste de validation
	getListOTAValider().add(monOT);
	//LUC
	getListOTEncours().remove(monOT);
	initialiseListOTAValider(request);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (02/08/05 13:40:12)
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
 * Date de création : (02/08/05 13:40:12)
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
 * PB_ENLEVER
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_ENLEVER() {
	return "NOM_PB_ENLEVER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ENLEVER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on ajoute l'entretien à faire dans la liste des OT en cours 
	int indice = (Services.estNumerique(getVAL_LB_OTAVALIDER_SELECT()) ? Integer.parseInt(getVAL_LB_OTAVALIDER_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement à enlever de la liste des Ordres de Travaux à valider ");
		return false;
	}
	OT monOT = (OT)getListOTAValider().get(indice);
	// on ajoute dans le planning
	getListOTEncours().add(monOT);
	// on ajoute à la liste à faire
	getListOTAValider().remove(indice);
	// on rafraichit la liste
	initialiseListOTAValider(request);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (02/08/05 13:40:12)
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
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on valide tous les OT qui ont été sélectionné
	
	if(getListOTAValider().size()>0){
		for(int i=0;i<getListOTAValider().size();i++){
			OT unOT = (OT)getListOTAValider().get(i);
			/*OTInfos unOTInfos = (OTInfos)getListOTAValider().get(i);
			OT unOT = OT.chercherOT(getTransaction(),unOTInfos.getNumeroot());
			if(getTransaction().isErreur()){
				return false;
			}*/
			String msg = unOT.validationOT(getTransaction());
			/*if(msg.equals("erreur")){
				// modification du 06/08/08
				getTransaction().declarerErreur(msg);
				return false;
			}else if(!msg.equals("ok")){
				information = information +" "+ msg;
			}*/
			if (!("ok").equals(msg)){
				getTransaction().declarerErreur(msg);
				return false;
			}else{
				information = information +" "+ msg;
			}
		}
	}
	commitTransaction();
	setEstEnregistre(true);
	// on recherche les OT validé avec les infos mises à jour
	ArrayList<OT> listAjour = new ArrayList<OT>();
	for(int i=0;i<getListOTAValider().size();i++){
	//	OTInfos unOTInfos = (OTInfos)getListOTAValider().get(i);
		OT unOT = (OT)getListOTAValider().get(i);
		//OTInfos unOTAjour = OTInfos.chercherOTInfos(getTransaction(),unOTInfos.getNumeroot());
		/*OTInfos unOTAjour = OTInfos.chercherOTInfos(getTransaction(),unOT.getNumeroot());
		if(getTransaction().isErreur()){
			return false;
		}*/
		//listAjour.add(unOTAjour);
		listAjour.add(unOT);
	}
	setListOTAValider(listAjour);
	initialiseListOTAValider(request);
	return true;
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_OTAVALIDER
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 */
private String [] getLB_OTAVALIDER() {
	if (LB_OTAVALIDER == null)
		LB_OTAVALIDER = initialiseLazyLB();
	return LB_OTAVALIDER;
}
/**
 * Setter de la liste:
 * LB_OTAVALIDER
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 */
private void setLB_OTAVALIDER(java.lang.String[] newLB_OTAVALIDER) {
	LB_OTAVALIDER = newLB_OTAVALIDER;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_OTAVALIDER
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_OTAVALIDER() {
	return "NOM_LB_OTAVALIDER";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_OTAVALIDER_SELECT
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_OTAVALIDER_SELECT() {
	return "NOM_LB_OTAVALIDER_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_OTAVALIDER
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_OTAVALIDER() {
	return getLB_OTAVALIDER();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_OTAVALIDER
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_OTAVALIDER_SELECT() {
	return getZone(getNOM_LB_OTAVALIDER_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_OTENCOURS
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 */
private String [] getLB_OTENCOURS() {
	if (LB_OTENCOURS == null)
		LB_OTENCOURS = initialiseLazyLB();
	return LB_OTENCOURS;
}
/**
 * Setter de la liste:
 * LB_OTENCOURS
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 */
private void setLB_OTENCOURS(java.lang.String[] newLB_OTENCOURS) {
	LB_OTENCOURS = newLB_OTENCOURS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_OTENCOURS
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_OTENCOURS() {
	return "NOM_LB_OTENCOURS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_OTENCOURS_SELECT
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_OTENCOURS_SELECT() {
	return "NOM_LB_OTENCOURS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_OTENCOURS
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_OTENCOURS() {
	return getLB_OTENCOURS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_OTENCOURS
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_OTENCOURS_SELECT() {
	return getZone(getNOM_LB_OTENCOURS_SELECT());
}
public ArrayList<OT> getListOTAValider() {
	return listOTAValider;
}
public void setListOTAValider(ArrayList<OT> listOTAValider) {
	this.listOTAValider = listOTAValider;
}
public ArrayList<OT> getListOTEncours() {
	return listOTEncours;
}
public void setListOTEncours(ArrayList<OT> listOTEncours) {
	this.listOTEncours = listOTEncours;
}
public boolean isFirst() {
	return first;
}
public void setFirst(boolean first) {
	this.first = first;
}
public boolean isAValider() {
	return AValider;
}
public void setAValider(boolean valider) {
	AValider = valider;
}
public boolean isEstEnregistre() {
	return estEnregistre;
}
public void setEstEnregistre(boolean estEnregistre) {
	this.estEnregistre = estEnregistre;
}

/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_VALIDATION
 * Date de création : (03/08/05 07:50:43)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK_VALIDATION() {
	return "NOM_PB_OK_VALIDATION";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (03/08/05 07:50:43)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
//retour à l'écran d'origine
public boolean performPB_OK_VALIDATION(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on vide la listes des OT à valider
	setListOTAValider(new ArrayList<OT>());
	setLB_OTAVALIDER(LBVide);
	setEstEnregistre(false);
	setAValider(false);
	information = "";
	return true;
}
	public boolean isListOtEncoursVide() {
		return listOtEncoursVide;
	}
	public void setListOtEncoursVide(boolean listOtEncoursVide) {
		this.listOtEncoursVide = listOtEncoursVide;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:57:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:57:12)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (03/04/07 08:57:36)
 * author : Générateur de process
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
 * Date de création : (03/04/07 08:57:36)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice  = (Services.estNumerique(getVAL_LB_OTENCOURS_SELECT()) ? Integer.parseInt(getVAL_LB_OTENCOURS_SELECT()): -1);
	if (indice == -1 || getListOTEncours().size() == 0 || indice > getListOTEncours().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","OT"));
		return false;
	}
	OT unOT = (OT)getListOTEncours().get(indice);
	setOtCourant(unOT);
	//recherche de l'équipement correspondant à l'ot
	setEquipementInfosCourant(new EquipementInfos());
	if(getOtCourant().getNuminv()!=null){
		EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),getOtCourant().getNuminv());
		if(getTransaction().isErreur()){
			return false;
		}
		setEquipementInfosCourant(unEquipementInfos);
	}
	// on renseigne les variable globales
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableGlobale.ajouter(request,"OT",getOtCourant());
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
	setStatut(STATUT_MODIFIER,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VISUALISER
 * Date de création : (03/04/07 08:57:36)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_VISUALISER() {
	return "NOM_PB_VISUALISER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (03/04/07 08:57:36)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VISUALISER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on envoie l'OT sélectionné
	int indice  = (Services.estNumerique(getVAL_LB_OTENCOURS_SELECT()) ? Integer.parseInt(getVAL_LB_OTENCOURS_SELECT()): -1);
	if (indice == -1 || getListOTEncours().size() == 0 || indice > getListOTEncours().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","OT"));
		return false;
	}
	OT unOT = (OT)getListOTEncours().get(indice);
	setEquipementInfosCourant(new EquipementInfos());
	if(unOT.getNuminv()!=null){
		EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfos(getTransaction(),unOT.getNuminv());
		if(getTransaction().isErreur()){
			return false;
		}
		setEquipementInfosCourant(unEquipementInfos);
	}
	// on envoie l'OT à la fenetre de visualisation de l'OT
	VariableGlobale.ajouter(request,"OT",unOT);
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	setStatut(STATUT_VISUALISER,true);
	return true;
}
public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
	this.equipementInfosCourant = equipementInfosCourant;
}
public EquipementInfos getEquipementInfosCourant() {
	return equipementInfosCourant;
}
public OT getOtCourant() {
	return otCourant;
}
public void setOtCourant(OT otCourant) {
	this.otCourant = otCourant;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (02/08/05 13:40:12)
 * author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_MODIFIER
		if (testerParametre(request, getNOM_PB_MODIFIER())) {
			return performPB_MODIFIER(request);
		}

		//Si clic sur le bouton PB_VISUALISER
		if (testerParametre(request, getNOM_PB_VISUALISER())) {
			return performPB_VISUALISER(request);
		}

		//Si clic sur le bouton PB_OK_VALIDATION
		if (testerParametre(request, getNOM_PB_OK_VALIDATION())) {
			return performPB_OK_VALIDATION(request);
		}

		//Si clic sur le bouton PB_AJOUTER
		if (testerParametre(request, getNOM_PB_AJOUTER())) {
			return performPB_AJOUTER(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_ENLEVER
		if (testerParametre(request, getNOM_PB_ENLEVER())) {
			return performPB_ENLEVER(request);
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
 * Date de création : (03/04/07 09:12:12)
 * author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeOT_Validation.jsp";
}

private Hashtable<String, Equipement> getHashEquipement() {
	if (hashEquipement == null)
			hashEquipement=new Hashtable<String, Equipement>();
	return hashEquipement;
}

}
