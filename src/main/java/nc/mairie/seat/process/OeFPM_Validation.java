package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.technique.*;
/**
 * Process OeFPM_Validation
 * Date de création : (02/08/05 13:40:12)
* 
*/
public class OeFPM_Validation extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_VISUALISER = 2;
	public static final int STATUT_MODIFIER = 1;
	private java.lang.String[] LB_FPMAVALIDER;
	private java.lang.String[] LB_FPMENCOURS;
	private ArrayList listFpmEncours = new ArrayList();
	private ArrayList listFpmAValider = new ArrayList();
	private Hashtable<String, PMateriel> hashPMateriel;
	private boolean first = true;
	public boolean AValider;
	public boolean estEnregistre = false;
	public boolean listFpmEncoursVide = true;
	private String information = "";
	private PMatInfos pMatInfosCourant;
	private FPM fpmCourant;
	private String ACTION_MODIFICATION = "Modification";
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (02/08/05 13:40:12)
* 
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	//LB optimisation 07/09
	if (getHashPMateriel().size() == 0) {
		initialiseListeEquipements();
	}

	//Depmande Eddy septembre 2009
	//String[] colonnes = {"dentree"};
	String[] colonnes = {"numfiche"};
	boolean ordres[] = {false};
	//	initialisation de la liste des FPM en cours
	if(first){
		ArrayList a = FPM.listerFPMAValider(getTransaction());
		a= Services.trier(a,colonnes,ordres);
		setListFpmEncours(a);
		setEstEnregistre(false);
	}
	if(getListFPMEncours().size()>0){
		setIsListFpmEncoursVide(false);
	}
	initialiseListFpmEncours(request);
	//si une information à afficher (concerne la validation des fpm)
	if(!information.equals("")){
		getTransaction().declarerErreur(information);
	}
	setFirst(false);
}

/**
 * Init de la liste des équipements 
 * 
 */
public void initialiseListeEquipements() throws Exception{
	ArrayList arr = PMateriel.listerPMateriel(getTransaction());
	
	for (Iterator iter = arr.iterator(); iter.hasNext();) {
		PMateriel pMateriel = (PMateriel) iter.next();
		getHashPMateriel().put(pMateriel.getPminv(), pMateriel);
	}
	
}

public void initialiseListFpmEncours(javax.servlet.http.HttpServletRequest request) throws Exception{
	String erreur="";
	String dentree = "";
	String dsortie = "";
	if(getListFPMEncours().size()>0){
		int tailles [] = {10,5,20,10,10};
		String[] padding = {"D","G","G","C","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for(int i = 0;i<getListFPMEncours().size();i++){
			FPM unFPM = (FPM)getListFPMEncours().get(i);
//			 affichage 
			if (unFPM.getDentree().equals("01/01/0001")){
				dentree="";
			}else{
				dentree=unFPM.getDentree();
			}
			if(unFPM.getDsortie().equals("01/01/0001")){
				dsortie="";
			}else{
				dsortie=unFPM.getDsortie();
			}
			/*LB optimisation 07/09/11
			//Modifs ofonteneau août 2011
			//System.out.println("FPM num inv="+unFPM.getPminv());
			PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),unFPM.getPminv());
			if (getTransaction().isErreur()){
				erreur+=" "+unFPM.getPminv();
				getTransaction().traiterErreur();
				continue;
				//return ;
			}
			*/
			PMateriel unPMateriel = getHashPMateriel().get(unFPM.getPminv());
			if (unPMateriel ==  null) { 
				getTransaction().declarerErreur("Erreur : le petit materiel "+unFPM.getPminv()+" n'existe pas." );
				return;
			}
			String ligne [] = { unFPM.getNumfiche(),unFPM.getPminv(),unPMateriel.getPmserie(),dentree,dsortie};
			aFormat.ajouteLigne(ligne);
		}
		setLB_FPMENCOURS(aFormat.getListeFormatee());
	} else {
		setLB_FPMENCOURS(null);
	}
	if (erreur.length() > 0)
		getTransaction().declarerErreur("Les inventaires "+erreur+" posent problème car ils sont sur des fiches mais n'existent pas en tant que petit matériel.");
}


public void initialiseListFpmAValider(javax.servlet.http.HttpServletRequest request) throws Exception{
	String erreur="";
	String dentree = "";
	String dsortie = "";
	if(getListFPMAValider().size()>0){
		setAValider(true);
		//les élèments de la liste 
		int tailles [] = {10,5,20,10,10};
		String[] padding = {"D","G","G","C","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < getListFPMAValider().size() ; i++) {
			FPM aFPM = (FPM)getListFPMAValider().get(i);	
			
			// on affiche les dates si elle sont différentes de 01/01/0001
			if (aFPM.getDentree().equals("01/01/0001")){
				dentree = "";
			}else{
				dentree = aFPM.getDentree();
			}
			if(aFPM.getDsortie().equals("01/01/0001")){
				dsortie = "";
			}else{
				dsortie = aFPM.getDsortie();
			}
			/*LB optimisation 07/09/11
			PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),aFPM.getPminv());
			if (getTransaction().isErreur()){
				erreur+=" "+aFPM.getPminv();
				getTransaction().traiterErreur();
				continue;
				//return ;
			}
			*/
			PMateriel unPMateriel = getHashPMateriel().get(aFPM.getPminv());
			if (unPMateriel ==  null) { 
				getTransaction().declarerErreur("Erreur : le petit materiel "+aFPM.getPminv()+" n'existe pas." );
				return;
			}
			String ligne [] = { aFPM.getNumfiche(),aFPM.getPminv(),unPMateriel.getPmserie(),dentree,dsortie};
			aFormat.ajouteLigne(ligne);
		}
		setAValider(true);
		setLB_FPMAVALIDER(aFormat.getListeFormatee());
	}else{
		setLB_FPMAVALIDER(LBVide);
		setAValider(false);
	}
	if (erreur.length() > 0)
		getTransaction().declarerErreur("Les inventaires "+erreur+" posent problème car ils sont sur des fiches mais n'existent pas en tant que petit matériel.");
}

/**
 * Constructeur du process OeFPM_Validation.
 * Date de création : (02/08/05 13:40:12)
* 
 */
public OeFPM_Validation() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (02/08/05 13:40:12)
* 
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
* 
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on ajoute FPM dans la liste à valider
	int indice = (Services.estNumerique(getVAL_LB_FPMENCOURS_SELECT()) ? Integer.parseInt(getVAL_LB_FPMENCOURS_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement à ajouter dans la liste des Fiches d'entretiens du petit matériel.");
		return false;
	}
	FPM monFPM = (FPM)getListFPMEncours().get(indice);
	// on ajoute dans la liste de validation
	getListFPMAValider().add(monFPM);
	//LUC
	getListFPMEncours().remove(monFPM);
	initialiseListFpmAValider(request);
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (02/08/05 13:40:12)
* 
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
* 
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ENLEVER
 * Date de création : (02/08/05 13:40:12)
* 
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
* 
 */
public boolean performPB_ENLEVER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on ajoute l'entretien à faire dans la liste des FPM en cours 
	int indice = (Services.estNumerique(getVAL_LB_FPMAVALIDER_SELECT()) ? Integer.parseInt(getVAL_LB_FPMAVALIDER_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement à enlever de la liste des Ordres de Travaux à valider ");
		return false;
	}
	FPM monFPM = (FPM)getListFPMAValider().get(indice);
	// on ajoute dans le planning
	getListFPMEncours().add(monFPM);
	// on ajoute à la liste à faire
	getListFPMAValider().remove(indice);
	// on rafraichit la liste
	initialiseListFpmAValider(request);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (02/08/05 13:40:12)
* 
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
* 
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on valide toutes les fiches d'entretiens qui ont été sélectionnées
	
	if(getListFPMAValider().size()>0){
		for(int i=0;i<getListFPMAValider().size();i++){
			FPM unFPM = (FPM)getListFPMAValider().get(i);
			String msg = unFPM.validationFPM(getTransaction());
			if(msg.equals("erreur")){
				return false;
			}else if(!msg.equals("ok")){
				information = information +" "+ msg;
			}
		}
	}
	commitTransaction();
	setEstEnregistre(true);
	// on recherche les FPM validé avec les infos mises à jour
	ArrayList listAjour = new ArrayList();
	for(int i=0;i<getListFPMAValider().size();i++){
		FPM unFPM = (FPM)getListFPMAValider().get(i);
		listAjour.add(unFPM);
	}
	setListFPMAValider(listAjour);
	initialiseListFpmAValider(request);
	return true;
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FPMAVALIDER
 * Date de création : (02/08/05 13:40:12)
* 
 */
private String [] getLB_FPMAVALIDER() {
	if (LB_FPMAVALIDER == null)
		LB_FPMAVALIDER = initialiseLazyLB();
	return LB_FPMAVALIDER;
}
/**
 * Setter de la liste:
 * LB_FPMAVALIDER
 * Date de création : (02/08/05 13:40:12)
* 
 */
private void setLB_FPMAVALIDER(java.lang.String[] newLB_FPMAVALIDER) {
	LB_FPMAVALIDER = newLB_FPMAVALIDER;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FPMAVALIDER
 * Date de création : (02/08/05 13:40:12)
* 
 */
public java.lang.String getNOM_LB_FPMAVALIDER() {
	return "NOM_LB_FPMAVALIDER";
	
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FPMAVALIDER_SELECT
 * Date de création : (02/08/05 13:40:12)
* 
 */
public java.lang.String getNOM_LB_FPMAVALIDER_SELECT() {
	return "NOM_LB_FPMAVALIDER_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FPMAVALIDER
 * Date de création : (02/08/05 13:40:12)
* 
 */
public java.lang.String [] getVAL_LB_FPMAVALIDER() {
	return getLB_FPMAVALIDER();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FPMAVALIDER
 * Date de création : (02/08/05 13:40:12)
* 
 */
public java.lang.String getVAL_LB_FPMAVALIDER_SELECT() {
	return getZone(getNOM_LB_FPMAVALIDER_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_FPMENCOURS
 * Date de création : (02/08/05 13:40:12)
* 
 */
private String [] getLB_FPMENCOURS() {
	if (LB_FPMENCOURS == null)
		LB_FPMENCOURS = initialiseLazyLB();
	return LB_FPMENCOURS;
}
/**
 * Setter de la liste:
 * LB_FPMENCOURS
 * Date de création : (02/08/05 13:40:12)
* 
 */
private void setLB_FPMENCOURS(java.lang.String[] newLB_FPMENCOURS) {
	LB_FPMENCOURS = newLB_FPMENCOURS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_FPMENCOURS
 * Date de création : (02/08/05 13:40:12)
* 
 */
public java.lang.String getNOM_LB_FPMENCOURS() {
	return "NOM_LB_FPMENCOURS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_FPMENCOURS_SELECT
 * Date de création : (02/08/05 13:40:12)
* 
 */
public java.lang.String getNOM_LB_FPMENCOURS_SELECT() {
	return "NOM_LB_FPMENCOURS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_FPMENCOURS
 * Date de création : (02/08/05 13:40:12)
* 
 */
public java.lang.String [] getVAL_LB_FPMENCOURS() {
	return getLB_FPMENCOURS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_FPMENCOURS
 * Date de création : (02/08/05 13:40:12)
* 
 */
public java.lang.String getVAL_LB_FPMENCOURS_SELECT() {
	return getZone(getNOM_LB_FPMENCOURS_SELECT());
}
public ArrayList getListFPMAValider() {
	return listFpmAValider;
}
public void setListFPMAValider(ArrayList listFpmAValider) {
	this.listFpmAValider = listFpmAValider;
}
public ArrayList getListFPMEncours() {
	return listFpmEncours;
}
public void setListFpmEncours(ArrayList listFpmEncours) {
	this.listFpmEncours = listFpmEncours;
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
* 
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
* 
 */
//retour à l'écran d'origine
public boolean performPB_OK_VALIDATION(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on vide la listes des FPM à valider
	setListFPMAValider(new ArrayList());
	setLB_FPMAVALIDER(LBVide);
	setEstEnregistre(false);
	setAValider(false);
	information = "";
	return true;
}
	public boolean isListFpmEncoursVide() {
		return listFpmEncoursVide;
	}
	public void setIsListFpmEncoursVide(boolean isListFpmEncoursVide) {
		this.listFpmEncoursVide = isListFpmEncoursVide;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:57:12)
* 
 */
public java.lang.String getNOM_ST_COMPTEUR() {
	return "NOM_ST_COMPTEUR";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_COMPTEUR
 * Date de création : (23/08/05 08:57:12)
* 
 */
public java.lang.String getVAL_ST_COMPTEUR() {
	return getZone(getNOM_ST_COMPTEUR());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (03/04/07 08:57:36)
* 
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
* 
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice  = (Services.estNumerique(getVAL_LB_FPMENCOURS_SELECT()) ? Integer.parseInt(getVAL_LB_FPMENCOURS_SELECT()): -1);
	if (indice == -1 || getListFPMEncours().size() == 0 || indice > getListFPMEncours().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","fiches d'entretiens du petit matériel"));
		return false;
	}
	FPM unFPM = (FPM)getListFPMEncours().get(indice);
	setFpmCourant(unFPM);
	//recherche du petit matériel correspondant à la fiche d'entretien
	setPMatInfosCourant(new PMatInfos());
	if(getFpmCourant().getPminv()!=null){
		PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),getFpmCourant().getPminv());
		if(getTransaction().isErreur()){
			return false;
		}
		setPMatInfosCourant(unPMatInfos);
	}
	// on renseigne les variable globales
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableGlobale.ajouter(request,"FPM",getFpmCourant());
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
	setStatut(STATUT_MODIFIER,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VISUALISER
 * Date de création : (03/04/07 08:57:36)
* 
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
* 
 */
public boolean performPB_VISUALISER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on envoie FPM sélectionné
	int indice  = (Services.estNumerique(getVAL_LB_FPMENCOURS_SELECT()) ? Integer.parseInt(getVAL_LB_FPMENCOURS_SELECT()): -1);
	if (indice == -1 || getListFPMEncours().size() == 0 || indice > getListFPMEncours().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","fiches d'entretiens du petit matériel"));
		return false;
	}
	FPM unFPM = (FPM)getListFPMEncours().get(indice);
	setPMatInfosCourant(new PMatInfos());
	if(unFPM.getPminv()!=null){
		PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),unFPM.getPminv());
		if(getTransaction().isErreur()){
			return false;
		}
		setPMatInfosCourant(unPMatInfos);
	}
	// on envoie FPM à la fenetre de visualisation de FPM
	VariableGlobale.ajouter(request,"FPM",unFPM);
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	setStatut(STATUT_VISUALISER,true);
	return true;
}
public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
	this.pMatInfosCourant = pMatInfosCourant;
}
public PMatInfos getPMatInfosCourant() {
	return pMatInfosCourant;
}
public FPM getFpmCourant() {
	return fpmCourant;
}
public void setFpmCourant(FPM fpmCourant) {
	this.fpmCourant = fpmCourant;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (02/08/05 13:40:12)
* 
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
* 
 */
@Override
public String getJSP() {
	return "OeFPM_Validation.jsp";
}

public Hashtable<String, PMateriel> getHashPMateriel() {
	if (hashPMateriel == null)
			hashPMateriel=new Hashtable<String, PMateriel>();
	return hashPMateriel;
}

public void setHashPMateriel(Hashtable<String, PMateriel> hashPMateriel) {
	this.hashPMateriel = hashPMateriel;
}
}
