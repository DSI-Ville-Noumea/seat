package nc.mairie.seat.process;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.BPCInfosCompletes;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.Service;
import nc.mairie.seat.servlet.ServletSeat;
import nc.mairie.servlets.Frontale;
import nc.mairie.technique.FormateListe;
import nc.mairie.technique.Services;
import nc.mairie.technique.StarjetGeneration;
import nc.mairie.technique.VariableActivite;
import nc.mairie.technique.VariableGlobale;

/**
 * Process OeBPC_VisualisationComplete
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
*/
public class OePMBPC_VisualisationComplete extends nc.mairie.technique.BasicProcess {
	public static final int STATUT_SERVICE = 2;
	public static final int STATUT_EQUIP = 1;
	private java.lang.String[] LB_BPCINFOS;
	private ArrayList listBPCInfos;
	private Service serviceCourant = new Service();
	private PMatInfos pMatInfosCourant = new PMatInfos();
	private PMateriel pMaterielCourant = new PMateriel();
	private boolean first;
	private String nbBPC;
	private String totalQte;
	private String starjetMode = (String)Frontale.getMesParametres().get("STARJET_MODE");
	private String script;
	public boolean isVide = true;
	public int kmParcourusTotal = 0;
	public String kmParcouru ;
	public int quantiteTotal = 0;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	//récupération des variables
	if((first)||(etatStatut()==STATUT_EQUIP)){
		PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
		if(unPMatInfos!=null){
			if(unPMatInfos.getPminv()!=null){
				setPMatInfosCourant(unPMatInfos);
				PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
				if(getTransaction().isErreur()){
					return;
				}
				setPMaterielCourant(unPMateriel);
			}
		}
		addZone(getNOM_EF_RECHE_EQUIP(),getPMatInfosCourant().getPminv());
	}
	if((first)||(etatStatut()==STATUT_SERVICE)){
		Service unService = (Service)VariableGlobale.recuperer(request,"SERVICE");
		if(unService!=null){
			if(unService.getServi()!=null){
				setServiceCourant(unService);
			}
		}else{
			getTransaction().traiterErreur();
		}
		addZone(getNOM_EF_RECH_SERVICE(),getServiceCourant().getServi());
	}
	/*if(!(getZone(getNOM_EF_RECH_SERVICE()).equals(""))){
		addZone(getNOM_ST_SERVICE(),getServiceCourant().getLiserv());
	}*/
	//initialisation de la liste lb
	initialiseListBPCInfos(request);
	initialiseListeTotal(request);
	
setFirst(false);
}
public void initialiseListeTotal(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(getListBPCInfos()!=null){
		if(getListBPCInfos().size()>0){
			setVide(false);
			int tailles [] = {9,10,10,10,10};
			String[] padding = {"C","C","D","D","D"};
			FormateListe aFormat = new FormateListe(tailles,padding,false);
			String ligne [] = { nbBPC,"","","",totalQte+" L"};
			aFormat.ajouteLigne(ligne);
			setLB_TOTAUX(aFormat.getListeFormatee());
		} else {
			setLB_TOTAUX(null);
			setVide(true);
		}
	}
}
public void initialiseListBPCInfos(javax.servlet.http.HttpServletRequest request) throws Exception{
	int totalBPC = 0;
	int totalQuantite = 0;
	//addZone(getNOM_ST_SERVICE(),"");
	if(getListBPCInfos().size()>0){
		int tailles [] = {9,10,10,10,10};
		String[] padding = {"D","G","C","D","D"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (int i = 0; i < getListBPCInfos().size() ; i++) {
			BPCInfosCompletes unBPCIC = (BPCInfosCompletes)getListBPCInfos().get(i);
			totalQuantite = totalQuantite + Integer.parseInt(unBPCIC.getQuantite());
			String ligne [] = { unBPCIC.getNumerobpc(),unBPCIC.getNumeroimmatriculation(),unBPCIC.getDate(),unBPCIC.getValeurcompteur(),unBPCIC.getQuantite().trim()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_BPCINFOS(aFormat.getListeFormatee());
		totalBPC = getLB_BPCINFOS().length;
		nbBPC = String.valueOf(totalBPC);
		totalQte = String.valueOf(totalQuantite);
	} else {
		setLB_BPCINFOS(null);
	}
	
}

/**
 * Constructeur du process OeBPC_VisualisationComplete.
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public OePMBPC_VisualisationComplete() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_EQUIP
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_EQUIP() {
	return "NOM_PB_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public boolean performPB_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	setLB_BPCINFOS(null);
	setLB_TOTALEQUIP(null);
	setLB_TOTAUX(null);
	setListBPCInfos(null);
	setPMatInfosCourant(null);
	setPMaterielCourant(null);
	setServiceCourant(null);
	addZone(getNOM_ST_SERVICE(),"");
	VariableActivite.ajouter(this,"MODE","BPC_VISUCOMPLETE");
	setStatut(STATUT_EQUIP,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHE_EQUIP
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECHE_EQUIP() {
	return "NOM_PB_RECHE_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public boolean performPB_RECHE_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	String equip = getZone(getNOM_EF_RECHE_EQUIP());
	if(equip.equals("")){
		getTransaction().declarerErreur("Vous devez saisir le numéro d'immatriculation ou d'inventaire");
		return false;
	}else{
		PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),equip);
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur();
			VariableActivite.ajouter(this,"PARAM",equip);
			setStatut(STATUT_EQUIP,true);
		}
		if(unPMateriel!=null){
			if(unPMateriel.getPminv()!=null){
				setPMaterielCourant(unPMateriel);
			}
		}
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECH_SERVICE
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_RECH_SERVICE() {
	return "NOM_PB_RECH_SERVICE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public boolean performPB_RECH_SERVICE(javax.servlet.http.HttpServletRequest request) throws Exception {
	String service = getZone(getNOM_EF_RECH_SERVICE());
	if(!service.equals("")){
		Service unService = Service.chercherService(getTransaction(),service);
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur();
			//getTransaction().declarerErreur("Aucun service ne co")
			VariableActivite.ajouter(this,"PARAM",service);
			setStatut(STATUT_SERVICE,true);
		}
		if(unService!=null){
			if(unService.getServi()!=null){
				setServiceCourant(unService);
			}
		}
	}else{
		getTransaction().declarerErreur("Vous devez saisir le numéro d'un service");
		return false;
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SERVICE
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_SERVICE() {
	return "NOM_PB_SERVICE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public boolean performPB_SERVICE(javax.servlet.http.HttpServletRequest request) throws Exception {
	setLB_BPCINFOS(null);
	setLB_TOTALEQUIP(null);
	setLB_TOTAUX(null);
	setListBPCInfos(null);
	setPMatInfosCourant(null);
	setPMaterielCourant(null);
	setServiceCourant(null);
	addZone(getNOM_ST_SERVICE(),"");
	setStatut(STATUT_SERVICE,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (25/08/05 11:40:11)
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
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on efface st_service
	addZone(getNOM_ST_SERVICE(),"");
	//on récupère les 4 paramètres qui vont permettre de chercher les BPC
	//1er paramètre
	String servi = getZone(getNOM_EF_RECH_SERVICE());
	String inv = getZone(getNOM_EF_RECHE_EQUIP());
	String ddeb = getZone(getNOM_EF_DDEB());
	String dfin = getZone(getNOM_EF_DFIN());
	if((servi.equals(""))&&(inv.equals(""))&&(ddeb.equals(""))&&(dfin.equals(""))){
		getTransaction().declarerErreur("Aucun paramètre n'a été renseigné.");
		return false;
	}
	if((!Services.estUneDate(ddeb)&&(!ddeb.equals("")))){
		getTransaction().declarerErreur("La date de début n'est pas correcte.");
		return false;
	}
	if((!Services.estUneDate(dfin)&&(!dfin.equals("")))){
		getTransaction().declarerErreur("La date de fin n'est pas correcte.");
		return false;
	}
	if(getTransaction().isErreur()){
		return false;
	}
	//si l'utilisateur a oublié de cliquer sur rechercher
	if(!getZone(getNOM_EF_RECH_SERVICE()).equals("")){
		performPB_RECH_SERVICE(request);
		if(getServiceCourant().getServi()!=null){
			servi = getServiceCourant().getServi();
		}
	}else{
		setServiceCourant(new Service());
	}
	if(!getZone(getNOM_EF_RECHE_EQUIP()).equals("")){
		performPB_RECHE_EQUIP(request);
		if(getPMaterielCourant().getPminv()!=null){
			inv=getPMatInfosCourant().getPminv();
		}
	}
	if(getTransaction().isErreur()){
		return false;
	}
	ArrayList resultat = BPCInfosCompletes.listerBPCInfosCompletesParams(getTransaction(),inv,servi,ddeb,dfin);
	if(getTransaction().isErreur()){
		return false;
	}
	setListBPCInfos(resultat);
	//si le service est un des paramètres de la recherche : on renseigne st_service
	addZone(getNOM_ST_SERVICE(),getServiceCourant().getLiserv());
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DDEB
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DDEB() {
	return "NOM_EF_DDEB";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DDEB
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DDEB() {
	return getZone(getNOM_EF_DDEB());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DFIN
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_DFIN() {
	return "NOM_EF_DFIN";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DFIN
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_DFIN() {
	return getZone(getNOM_EF_DFIN());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHE_EQUIP
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_RECHE_EQUIP() {
	return "NOM_EF_RECHE_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHE_EQUIP
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_RECHE_EQUIP() {
	return getZone(getNOM_EF_RECHE_EQUIP());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECH_SERVICE
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_EF_RECH_SERVICE() {
	return "NOM_EF_RECH_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECH_SERVICE
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getVAL_EF_RECH_SERVICE() {
	return getZone(getNOM_EF_RECH_SERVICE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_BPCINFOS
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
private String [] getLB_BPCINFOS() {
	if (LB_BPCINFOS == null)
		LB_BPCINFOS = initialiseLazyLB();
	return LB_BPCINFOS;
}
/**
 * Setter de la liste:
 * LB_BPCINFOS
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
private void setLB_BPCINFOS(java.lang.String[] newLB_BPCINFOS) {
	LB_BPCINFOS = newLB_BPCINFOS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_BPCINFOS
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPCINFOS() {
	return "NOM_LB_BPCINFOS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_BPCINFOS_SELECT
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_BPCINFOS_SELECT() {
	return "NOM_LB_BPCINFOS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_BPCINFOS
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_BPCINFOS() {
	return getLB_BPCINFOS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_BPCINFOS
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_BPCINFOS_SELECT() {
	return getZone(getNOM_LB_BPCINFOS_SELECT());
}
public PMateriel getPMaterielCourant() {
	return pMaterielCourant;
}
public void setPMaterielCourant(PMateriel pMaterielCourant) {
	this.pMaterielCourant = pMaterielCourant;
}
public PMatInfos getPMatInfosCourant() {
	return pMatInfosCourant;
}
public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
	this.pMatInfosCourant = pMatInfosCourant;
}
public ArrayList getListBPCInfos() {
	if(listBPCInfos==null){
		listBPCInfos = new ArrayList();
	}
	return listBPCInfos;
}
public void setListBPCInfos(ArrayList listBPCInfos) {
	this.listBPCInfos = listBPCInfos;
}
	public Service getServiceCourant() {
		return serviceCourant;
	}
	public void setServiceCourant(Service serviceCourant) {
		this.serviceCourant = serviceCourant;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (29/08/05 13:13:30)
 * @author : Générateur de process
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (29/08/05 13:13:30)
 * @author : Générateur de process
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_DETAILS
 * Date de création : (29/08/05 13:14:07)
 * @author : Générateur de process
 */
public java.lang.String getNOM_PB_DETAILS() {
	return "NOM_PB_DETAILS";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (29/08/05 13:14:07)
 * @author : Générateur de process
 */
public boolean performPB_DETAILS(javax.servlet.http.HttpServletRequest request) throws Exception {
	addZone(getNOM_ST_SERVICE(),"");
	int indice = (Services.estNumerique(getVAL_LB_BPCINFOS_SELECT()) ? Integer.parseInt(getVAL_LB_BPCINFOS_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	BPCInfosCompletes unBPCIC = (BPCInfosCompletes)getListBPCInfos().get(indice);
	addZone(getNOM_ST_SERVICE(),unBPCIC.getLiserv());
	return true;
}


	private java.lang.String[] LB_TOTAUX;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_TOTAUX
 * Date de création : (02/09/05 10:13:01)
 * @author : Générateur de process
 */
private String [] getLB_TOTAUX() {
	if (LB_TOTAUX == null)
		LB_TOTAUX = initialiseLazyLB();
	return LB_TOTAUX;
}
/**
 * Setter de la liste:
 * LB_TOTAUX
 * Date de création : (02/09/05 10:13:01)
 * @author : Générateur de process
 */
private void setLB_TOTAUX(java.lang.String[] newLB_TOTAUX) {
	LB_TOTAUX = newLB_TOTAUX;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TOTAUX
 * Date de création : (02/09/05 10:13:01)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_TOTAUX() {
	return "NOM_LB_TOTAUX";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TOTAUX_SELECT
 * Date de création : (02/09/05 10:13:01)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_TOTAUX_SELECT() {
	return "NOM_LB_TOTAUX_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_TOTAUX
 * Date de création : (02/09/05 10:13:01)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_TOTAUX() {
	return getLB_TOTAUX();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_TOTAUX
 * Date de création : (02/09/05 10:13:01)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_TOTAUX_SELECT() {
	return getZone(getNOM_LB_TOTAUX_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_IMPRIMER
 * Date de création : (07/06/07 10:50:57)
 * @author : Générateur de process
 */
/*construction du fichier d'impression : 
*1er caractère : 1 pour le BPC 2 pour le total de l'équipement
*  numéro d'invenaire sur 10
* numéro d'immatriculation sur 10
* nom d'équipement sur 64
* type sur 32
* service sur 60
* début de période sur 10
* fin de période sur 10
* numéro de BPC sur 10
* date sur 10
* compteur sur 10
* quantité sur 6
* km parcouru sur 10
* moyenne sur 10
* pour le total
* 
*/
public java.lang.String getNOM_PB_IMPRIMER() {
	return "NOM_PB_IMPRIMER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (07/06/07 10:50:57)
 * @author : Générateur de process
 */
public boolean performPB_IMPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	ArrayList listeBPCEquip = new ArrayList();
	String numinv = "";
	String numinvder ="";
	String agentResponsable;
	String codeService;
	boolean isVideDDeb = true;
	boolean isVideDFin = true;
	// recherche BPC correspondant aux paramètres renseignés
	String servi = getZone(getNOM_EF_RECH_SERVICE());
	String inv = getZone(getNOM_EF_RECHE_EQUIP());
	String ddeb = getZone(getNOM_EF_DDEB());
	String dfin = getZone(getNOM_EF_DFIN());
	String stService = "";
	
	if((servi.equals(""))&&(inv.equals(""))&&(ddeb.equals(""))&&(dfin.equals(""))){
		getTransaction().declarerErreur("Aucun paramètre n'a été renseigné.");
		return false;
	}
	if((!Services.estUneDate(ddeb)&&(!ddeb.equals("")))){
		getTransaction().declarerErreur("La date de début n'est pas correcte.");
		return false;
	}else{
		if(ddeb.equals("")){
			isVideDDeb = true;
		}else{
			isVideDDeb = false;
		}
	}
	if((!Services.estUneDate(dfin)&&(!dfin.equals("")))){
		getTransaction().declarerErreur("La date de fin n'est pas correcte.");
		return false;
	}else{
		if(dfin.equals("")){
			isVideDFin = true;
		}else{
			isVideDFin = false;
		}
	}
	
	if(getTransaction().isErreur()){
		return false;
	}
	//si l'utilisateur a oublié de cliquer sur rechercher
	if(!getZone(getNOM_EF_RECH_SERVICE()).equals("")){
		performPB_RECH_SERVICE(request);
		if(getServiceCourant().getServi()!=null){
			servi = getServiceCourant().getServi();
		}
	}else{
		setServiceCourant(new Service());
	}
	if(!getZone(getNOM_EF_RECHE_EQUIP()).equals("")){
		performPB_RECHE_EQUIP(request);
		if(getPMaterielCourant().getPminv()!=null){
			inv=getPMaterielCourant().getPminv();
		}
	}
	if(getTransaction().isErreur()){
		return false;
	}
	ArrayList resultat = BPCInfosCompletes.listerBPCInfosCompletesParams(getTransaction(),inv,servi,ddeb,dfin);
	if(getTransaction().isErreur()){
		return false;
	}
	String[] nomChamps = {"pminv","date","valeurcompteur"};
	boolean[] ordres = {true,false,false};
	resultat = Services.trier(resultat,nomChamps,ordres);
	setListBPCInfos(resultat);
	//initialiseListBPCInfos(request);
	
	if(getListBPCInfos().size()>0){
		StarjetGeneration g = new StarjetGeneration(getTransaction(), "MAIRIE", starjetMode, "SEAT", "listeBPCEquip.sp", "listeBPCEquip");
		File f = g.getFileData();
		
		FileWriter fw = new FileWriter(f);
		PrintWriter pw = new PrintWriter(fw);
		try {	
			if(getListBPCInfos().size()>0){
				BPCInfosCompletes unBPC = (BPCInfosCompletes)getListBPCInfos().get(0);
				numinvder = unBPC.getNumeroinventaire();
			}
			for(int i=0;i<getListBPCInfos().size();i++){
				BPCInfosCompletes unBPCComplet = (BPCInfosCompletes)getListBPCInfos().get(i);
				BPCInfosCompletes bpcAvant = null;
				if(isVideDDeb){
					ddeb = unBPCComplet.getDatemiseencirculation();
				}
				if(isVideDFin){
					dfin = Services.dateDuJour();
				}
				try {
					bpcAvant = (BPCInfosCompletes) getListBPCInfos().get(i+1);	
				} catch (Exception e) {
					bpcAvant = null;
				}
				int kmParcourus =0 ;
				double moyennecalcul = 0;
				// recherche de l'agent responsabl
				if ((unBPCComplet.getNomatr()!=null)&&(!unBPCComplet.getNomatr().equals("0"))){
					if(unBPCComplet.getCodeservice().equals("EAAA")){
						AgentCDE unAgentCde = AgentCDE.chercherAgentCDE(getTransaction(),unBPCComplet.getNomatr());
						if(getTransaction().isErreur()){
							return false;
						}
						agentResponsable = unAgentCde.nom.trim()+" "+unAgentCde.prenom.trim();
					}else if(unBPCComplet.getCodeservice().equals("5000")){
						AgentCCAS unAgentCcas = AgentCCAS.chercherAgentCCAS(getTransaction(),unBPCComplet.getNomatr());
						if(getTransaction().isErreur()){
							return false;
						}
						agentResponsable = unAgentCcas.nom.trim()+" "+unAgentCcas.prenom.trim();
					}else {
						Agents unAgent = Agents.chercherAgents(getTransaction(),unBPCComplet.getNomatr());
						if(getTransaction().isErreur()){
							return false;
						}
						agentResponsable = unAgent.nom.trim()+" "+unAgent.prenom.trim();
					}
				}else{
					agentResponsable = "sans";
				}
				
				String moyenneL = "";
				numinv = unBPCComplet.getNumeroinventaire().trim();
				if(numinv.equals(numinvder)){
					listeBPCEquip.add(unBPCComplet);
				}else{
					initialiseListeTotalEquip(request,listeBPCEquip);
//					Total
					pw.print("2");
					pw.print(Services.lpad(numinvder,10," "));
					pw.print(getLB_TOTALEQUIP()[0]);
					pw.println();
					setLB_TOTALEQUIP(null);
					kmParcourus = 0;
					listeBPCEquip = new ArrayList();
				}
				//Entete
				pw.print("1");
				pw.print(Services.lpad(unBPCComplet.getNumeroinventaire(),10," "));
				pw.print(Services.lpad(unBPCComplet.getNumeroimmatriculation(),10," "));
				EquipementInfos unEI = EquipementInfos.chercherEquipementInfos(getTransaction(),unBPCComplet.getNumeroinventaire());
				if(getTransaction().isErreur()){
					return false;
				}
				pw.print(Services.lpad(unEI.getDesignationmarque().trim()+" "+unEI.getDesignationmodele().trim(),64," "));
				pw.print(Services.lpad(unEI.getDesignationtypeequip().trim(),32," "));
				stService = unBPCComplet.getCodeservice()+" "+unBPCComplet.getLiserv().trim();
				if(stService.length()>64){
					stService = stService.substring(0,64);
					pw.print(stService);
				}else{
					pw.print(Services.lpad(unBPCComplet.getCodeservice().trim()+" "+unBPCComplet.getLiserv().trim(),64," "));
				}
				pw.print(Services.lpad(Services.formateDate(ddeb),10," "));
				pw.print(Services.lpad(Services.formateDate(dfin),10," "));
				pw.print(Services.lpad(agentResponsable,50," "));
				//BPC
				pw.print(Services.lpad(unBPCComplet.getNumerobpc(),10," "));
				pw.print(Services.lpad(unBPCComplet.getDate(),10," "));
				pw.print(Services.lpad(unBPCComplet.getValeurcompteur(),10," "));
				pw.print(Services.lpad(unBPCComplet.getQuantite(),6," "));
				if ((null != bpcAvant)&&(numinv.equals(bpcAvant.getNumeroinventaire()))){
					kmParcourus =  Integer.parseInt(unBPCComplet.getValeurcompteur())-Integer.parseInt(bpcAvant.getValeurcompteur());
					int qteAvant = Integer.parseInt(bpcAvant.getQuantite());
					int qte = Integer.parseInt(unBPCComplet.getQuantite());
					//moyennecalcul = (double)qteAvant/(double)kmParcourus*100;
					//moyennecalcul = (double)qte/(double)kmParcourus*100;
					moyennecalcul = (double)qte/(double)kmParcourus*100;
//					if (("KILOMETRIQUE").equals(getPMatInfosCourant().getDesignationcompteur())){
//						moyennecalcul = moyennecalcul*100;
//					}
					NumberFormat moyenneFormat = new DecimalFormat("0.00");
					moyenneL = moyenneFormat.format(moyennecalcul);
					kmParcouru = ""+kmParcourus;
				}
				pw.print(Services.lpad(kmParcouru,10," "));
				pw.print(Services.lpad(moyenneL,10," "));
				pw.println();
				quantiteTotal = quantiteTotal + Integer.parseInt(unBPCComplet.getQuantite());
				kmParcourusTotal = kmParcourusTotal + Integer.parseInt(unBPCComplet.getValeurcompteur());
				numinvder = unBPCComplet.getNumeroinventaire().trim();
				kmParcouru = "";
			}
			// pour le dernier équipment
			initialiseListeTotalEquip(request,listeBPCEquip);
//			Total
			pw.print("2");
			pw.print(Services.lpad(numinvder,10," "));
			pw.print(getLB_TOTALEQUIP()[0]);
			pw.println();
			setLB_TOTALEQUIP(null);
			//kmParcourus = 0;
			pw.close();
			fw.close();
			
		} catch (Exception e) {
			pw.close();
			fw.close();
			throw e;
		}
		
		setScript(g.getScriptOuverture());
		
	}
	//setListBPCInfos(new ArrayList());
	return true;
}

public void initialiseListeTotalEquip(javax.servlet.http.HttpServletRequest request,ArrayList listBPC) throws Exception{
	NumberFormat moyenneTotalFormat = new DecimalFormat("0.00");
	int kmParcouru = 0;
	int total = 0;
	if(listBPC.size()>0){
		for (int i=0;i<listBPC.size();i++){
			BPCInfosCompletes unBPCIC = (BPCInfosCompletes)listBPC.get(i);
			total = total + Integer.parseInt(unBPCIC.getQuantite()); 
		}
		BPCInfosCompletes unBPCPrem = (BPCInfosCompletes)listBPC.get(0);
		BPCInfosCompletes unbpcDer = (BPCInfosCompletes)listBPC.get(listBPC.size()-1);
		kmParcouru = Integer.parseInt(unBPCPrem.getValeurcompteur()) - Integer.parseInt(unbpcDer.getValeurcompteur());
		quantiteTotal = total - Integer.parseInt(unbpcDer.getQuantite());
	}

	if(listBPC.size()>0){
		BPCInfosCompletes unBPC = (BPCInfosCompletes)listBPC.get(0);
		double MoyenneTotal = (double)quantiteTotal/(double)kmParcouru*100;
		if(listBPC!=null){
			if(listBPC.size()>0){
				int tailles [] = {10,10,10,6,10,10};
				String[] padding = {"D","C","D","D","D","D"};
				FormateListe aFormat = new FormateListe(tailles,padding,false);

				String ligne [] = { String.valueOf(listBPC.size()),"","",String.valueOf(total),String.valueOf(kmParcouru),String.valueOf(moyenneTotalFormat.format(MoyenneTotal))};
				aFormat.ajouteLigne(ligne);
				setLB_TOTALEQUIP(aFormat.getListeFormatee());
			} else {
				setLB_TOTALEQUIP(null);
			}
		}
		MoyenneTotal = 0;
	}
	total = 0;
	kmParcourusTotal = 0;
	kmParcouru = 0;
	quantiteTotal = 0;
	
}

public String getScript() {
	if (script == null) script="";
	return script;
}
public void setScript(String script) {
	this.script = script;
}
public String afficheScript() {
	
	String res = new String(getScript());
	setScript(null);
	return res;
}
public boolean isVide() {
	return isVide;
}
public void setVide(boolean isVide) {
	this.isVide = isVide;
}
	private java.lang.String[] LB_TOTALEQUIP;
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (25/08/05 11:40:11)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_IMPRIMER
		if (testerParametre(request, getNOM_PB_IMPRIMER())) {
			return performPB_IMPRIMER(request);
		}

		//Si clic sur le bouton PB_DETAILS
		if (testerParametre(request, getNOM_PB_DETAILS())) {
			return performPB_DETAILS(request);
		}

		//Si clic sur le bouton PB_EQUIP
		if (testerParametre(request, getNOM_PB_EQUIP())) {
			return performPB_EQUIP(request);
		}

		//Si clic sur le bouton PB_RECHE_EQUIP
		if (testerParametre(request, getNOM_PB_RECHE_EQUIP())) {
			return performPB_RECHE_EQUIP(request);
		}

		//Si clic sur le bouton PB_RECH_SERVICE
		if (testerParametre(request, getNOM_PB_RECH_SERVICE())) {
			return performPB_RECH_SERVICE(request);
		}

		//Si clic sur le bouton PB_SERVICE
		if (testerParametre(request, getNOM_PB_SERVICE())) {
			return performPB_SERVICE(request);
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
 * Date de création : (07/06/07 11:42:05)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OeBPC_VisualisationComplete.jsp";
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_TOTALEQUIP
 * Date de création : (07/06/07 11:42:05)
 * @author : Générateur de process
 */
private String [] getLB_TOTALEQUIP() {
	if (LB_TOTALEQUIP == null)
		LB_TOTALEQUIP = initialiseLazyLB();
	return LB_TOTALEQUIP;
}
/**
 * Setter de la liste:
 * LB_TOTALEQUIP
 * Date de création : (07/06/07 11:42:05)
 * @author : Générateur de process
 */
private void setLB_TOTALEQUIP(java.lang.String[] newLB_TOTALEQUIP) {
	LB_TOTALEQUIP = newLB_TOTALEQUIP;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_TOTALEQUIP
 * Date de création : (07/06/07 11:42:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_TOTALEQUIP() {
	return "NOM_LB_TOTALEQUIP";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_TOTALEQUIP_SELECT
 * Date de création : (07/06/07 11:42:05)
 * @author : Générateur de process
 */
public java.lang.String getNOM_LB_TOTALEQUIP_SELECT() {
	return "NOM_LB_TOTALEQUIP_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_TOTALEQUIP
 * Date de création : (07/06/07 11:42:05)
 * @author : Générateur de process
 */
public java.lang.String [] getVAL_LB_TOTALEQUIP() {
	return getLB_TOTALEQUIP();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_TOTALEQUIP
 * Date de création : (07/06/07 11:42:05)
 * @author : Générateur de process
 */
public java.lang.String getVAL_LB_TOTALEQUIP_SELECT() {
	return getZone(getNOM_LB_TOTALEQUIP_SELECT());
}
}
