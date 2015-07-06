package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.ListIterator;

import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeService_Equipements
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
*/
public class OeService_PMateriel extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1095921440717234810L;
	public static final int STATUT_AFFECTATION = 4;
	public static final int STATUT_VISUALISER = 3;
	public static final int STATUT_TDB = 2;
	public static final int STATUT_SCE_RECHERCHE = 1;
	private java.lang.String[] LB_EQUIP;
	private Service serviceCourant;
	private ArrayList<PM_Affectation_Sce_Infos> listEquip;
	public String focus=null;
	public boolean menuService = false;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{

//	 permet l'affichage ou non de la recherche du service
	String menu = (String)VariableGlobale.recuperer(request,"MENU");
	if("menuService".equals(menu)){
		menuService = true;
//		 récupération du Service
		String accro = (String)VariableGlobale.recuperer(request,"ACCRONYME");
		ArrayList<Service> listService = Service.chercherListServiceAccro(getTransaction(),accro);
		if(getTransaction().isErreur()){
			return ;
		}
		// dans le cas où c'est un service autre que ATM
		if(listService.size()>0){
			Service unService = (Service)listService.get(0);
			VariableGlobale.ajouter(request,"SERVICE",unService);
		}
	}else{
		menuService = false;
	}
	
	Service unService = (Service)VariableGlobale.recuperer(request, "SERVICE");
	if (unService!=null){
		setServiceCourant(unService);
		VariableGlobale.enlever(request,"SERVICE");
	}else{
		PMatInfos unPmatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
		if(unPmatInfos!=null){
			if(unPmatInfos.getPminv()!=null){
				PM_Affectation_Sce_Infos unPMAFSI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPmEnCours(getTransaction(),unPmatInfos.getPminv());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
				}
				if(unPMAFSI.getPminv()!=null){
					Service aService = Service.chercherService(getTransaction(),unPMAFSI.getSiserv());
					if(getTransaction().isErreur()){
						return;
					}
					setServiceCourant(aService);
				}
			}
		}
	}
	//quand appuie sur entree
	if(!getZone(getNOM_EF_SERVICE()).equals("")){
		performPB_SERVICE(request);
	}
	if (null!=(getServiceCourant())){
		if(null!=getServiceCourant().getServi()){
			
			addZone(getNOM_ST_CODE_SCE(),getServiceCourant().getServi());
			addZone(getNOM_ST_LIBELLE_SCE(),getServiceCourant().getLiserv());
			initialiseListePMat(request);	
		}
	}
	VariableGlobale.ajouter(request,"SERVICE",getServiceCourant());
}

public boolean initialiseListePMat(javax.servlet.http.HttpServletRequest request) throws Exception{
//	 recherche des petits matériels affectés
	ArrayList<PM_Affectation_Sce_Infos> a = PM_Affectation_Sce_Infos.chercherPmAffectationServiceInfosService3(getTransaction(),getServiceCourant().getServi());
	if(getTransaction().isErreur()){
		return false;
	}
	if (a.size()>0){
		setListEquip(a);
		trier(a);
	}else{
		setLB_EQUIP(null);
		setListEquip(null);
	}
	return true;
}
public void trier(ArrayList<PM_Affectation_Sce_Infos> a) throws Exception{
	String[] colonnes = {"pminv","pmserie"};
	//ordre croissant
	boolean[] ordres = {true,true};
	
//	Si au moins une affectation
	if (a.size() !=0 ) {
		ArrayList<PM_Affectation_Sce_Infos> aTrier = Services.trier(a,colonnes,ordres);
		setListEquip(aTrier);
		int tailles [] = {5,10,25,15,10};
		String[] padding = {"G","G","G","G","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (ListIterator<PM_Affectation_Sce_Infos> list = aTrier.listIterator(); list.hasNext(); ) {
			PM_Affectation_Sce_Infos monASI = (PM_Affectation_Sce_Infos)list.next();
			PMatInfos unEI = PMatInfos.chercherPMatInfos(getTransaction(),monASI.getPminv());
			if(getTransaction().isErreur()){
				return ;
			}
			String ligne [] = { monASI.getPminv(),monASI.getPmserie(),unEI.getDesignationmarque().trim()+" "+unEI.getDesignationmodele(),unEI.getDesignationtypeequip().trim(),monASI.getDdebut()};
			aFormat.ajouteLigne(ligne);
		}
	
		setLB_EQUIP(aFormat.getListeFormatee());
	} else {
		setLB_EQUIP(null);
	}
	return ;
}
/**
 * Constructeur du process OeService_Equipements.
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 */
public OeService_PMateriel() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SERVICE
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_SERVICE() {
	return "NOM_PB_SERVICE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SERVICE(javax.servlet.http.HttpServletRequest request) throws Exception {
	String recherche = getZone(getNOM_EF_SERVICE());
	addZone(getNOM_EF_SERVICE(),"");
	Service unService = Service.chercherService(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("Le service recherché "+recherche+" n'a pas été trouvé.");
		return false;
	}
	if(unService!=null){
		if(unService.getServi()==null){
			getTransaction().declarerErreur("Le service recherché "+recherche+" n'a pas été trouvé.");
			return false;
		}
	}
	
	setServiceCourant(unService);

	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_TDB
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_TDB() {
	return "NOM_PB_TDB";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_TDB(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on débranche sur le tableau de bord
	int indice  = (Services.estNumerique(getVAL_LB_EQUIP_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIP_SELECT()): -1);
	if (indice == -1 || getListEquip().size() == 0 || indice > getListEquip().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","affectations d'un petit matériel"));
		return false;
	}
	PM_Affectation_Sce_Infos unASI = (PM_Affectation_Sce_Infos)getListEquip().get(indice);
	PMatInfos unPM = PMatInfos.chercherPMatInfos(getTransaction(),unASI.getPminv());
	if(getTransaction().isErreur()){
		return false;
	}
	if(null==unPM){
		unPM = new PMatInfos();
	}
	// on envoie petit matériel infos au tableau de bord
	VariableGlobale.ajouter(request,"PMATINFOS",unPM);
	setStatut(STATUT_TDB,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VISUALISER
 * Date de création : (04/04/07 14:38:26)
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
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VISUALISER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// débranchement sur l'écran de visu d'un petit matériel
	int indice  = (Services.estNumerique(getVAL_LB_EQUIP_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIP_SELECT()): -1);
	if (indice == -1 || getListEquip().size() == 0 || indice > getListEquip().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","affectations d'un petit matériel"));
		return false;
	}
	PM_Affectation_Sce_Infos unASI = (PM_Affectation_Sce_Infos)getListEquip().get(indice);
	PMatInfos unPM = PMatInfos.chercherPMatInfos(getTransaction(),unASI.getPminv());
	if(getTransaction().isErreur()){
		return false;
	}
	if(null==unPM){
		unPM = new PMatInfos();
	}
	// on envoie petit matériel infos à l'écran de visu
	VariableGlobale.ajouter(request,"PMATINFOS",unPM);
	setStatut(STATUT_VISUALISER,true);
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_SERVICE
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_SERVICE() {
	return "NOM_EF_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_SERVICE
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_SERVICE() {
	return getZone(getNOM_EF_SERVICE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_EQUIP
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 */
private String [] getLB_EQUIP() {
	if (LB_EQUIP == null)
		LB_EQUIP = initialiseLazyLB();
	return LB_EQUIP;
}
/**
 * Setter de la liste:
 * LB_EQUIP
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 */
private void setLB_EQUIP(java.lang.String[] newLB_EQUIP) {
	LB_EQUIP = newLB_EQUIP;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_EQUIP
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_EQUIP() {
	return "NOM_LB_EQUIP";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_EQUIP_SELECT
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_EQUIP_SELECT() {
	return "NOM_LB_EQUIP_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_EQUIP
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_EQUIP() {
	return getLB_EQUIP();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_EQUIP
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_EQUIP_SELECT() {
	return getZone(getNOM_LB_EQUIP_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RESPONSABLE
 * Date de création : (05/04/07 09:40:35)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RESPONSABLE() {
	return "NOM_PB_RESPONSABLE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (05/04/07 09:40:35)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RESPONSABLE(javax.servlet.http.HttpServletRequest request) throws Exception {
	if((getListEquip()!=null)&&(getListEquip().size()>0)){
		String responsable = "";
		int indice  = (Services.estNumerique(getVAL_LB_EQUIP_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIP_SELECT()): -1);
		if (indice == -1 || getListEquip().size() == 0 || indice > getListEquip().size() -1) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","affectations d'un petit matériel"));
			return false;
		}
		responsable = "sans";
		PM_Affectation_Sce_Infos unASI = (PM_Affectation_Sce_Infos)getListEquip().get(indice);
		// selon le code du service : 
		if(!unASI.getNomatr().equals("0")){
			if (unASI.getSiserv().equals("4000")){
				AgentCDE unAgent = AgentCDE.chercherAgentCDE(getTransaction(),unASI.getNomatr());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					responsable = "agent non trouvé";
				}else{
					responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
				}
			}else if(unASI.getSiserv().equals("5000")){
				AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),unASI.getNomatr());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					responsable = "agent non trouvé";
				}else{
					responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
				}
			}else{
				Agents unAgent = Agents.chercherAgents(getTransaction(),unASI.getNomatr());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					responsable = "agent non trouvé";
				}else{
					responsable = unAgent.getNom().trim()+" "+unAgent.getPrenom().trim();
				}
			}
		}
		addZone(getNOM_ST_RESPONSABLE(),responsable);
	}
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_RESPONSABLE
 * Date de création : (05/04/07 09:40:35)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_RESPONSABLE() {
	return "NOM_ST_RESPONSABLE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_RESPONSABLE
 * Date de création : (05/04/07 09:40:35)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_RESPONSABLE() {
	return getZone(getNOM_ST_RESPONSABLE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SCE_RECHERCHE
 * Date de création : (05/04/07 09:42:19)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_SCE_RECHERCHE() {
	return "NOM_PB_SCE_RECHERCHE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (05/04/07 09:42:19)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SCE_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_SCE_RECHERCHE,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CODE_SCE
 * Date de création : (05/04/07 09:42:59)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_CODE_SCE() {
	return "NOM_ST_CODE_SCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CODE_SCE
 * Date de création : (05/04/07 09:42:59)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_CODE_SCE() {
	return getZone(getNOM_ST_CODE_SCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_LIBELLE_SCE
 * Date de création : (05/04/07 09:42:59)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_LIBELLE_SCE() {
	return "NOM_ST_LIBELLE_SCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_LIBELLE_SCE
 * Date de création : (05/04/07 09:42:59)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_LIBELLE_SCE() {
	return getZone(getNOM_ST_LIBELLE_SCE());
}
/**
 * @return Renvoie equipementInfosCourant.
 */
private Service getServiceCourant() {
	return serviceCourant;
}
/**
 * @param equipementInfosCourant equipementInfosCourant à définir.
 */
private void setServiceCourant(
		Service serviceCourant) {
	this.serviceCourant = serviceCourant;
}
/**
 * @return Renvoie listeAffectation.
 */
public ArrayList<PM_Affectation_Sce_Infos> getListEquip() {
	return listEquip;
}
/**
 * @param listEquip listEquip à définir.
 */
public void setListEquip(ArrayList<PM_Affectation_Sce_Infos> listEquip) {
	this.listEquip = listEquip;
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
	return getNOM_EF_SERVICE();
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (04/04/07 14:38:26)
 * author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_AFFECTATION
		if (testerParametre(request, getNOM_PB_AFFECTATION())) {
			return performPB_AFFECTATION(request);
		}

		//Si clic sur le bouton PB_SCE_RECHERCHE
		if (testerParametre(request, getNOM_PB_SCE_RECHERCHE())) {
			return performPB_SCE_RECHERCHE(request);
		}

		//Si clic sur le bouton PB_RESPONSABLE
		if (testerParametre(request, getNOM_PB_RESPONSABLE())) {
			return performPB_RESPONSABLE(request);
		}

		//Si clic sur le bouton PB_SERVICE
		if (testerParametre(request, getNOM_PB_SERVICE())) {
			return performPB_SERVICE(request);
		}

		//Si clic sur le bouton PB_TDB
		if (testerParametre(request, getNOM_PB_TDB())) {
			return performPB_TDB(request);
		}

		//Si clic sur le bouton PB_VISUALISER
		if (testerParametre(request, getNOM_PB_VISUALISER())) {
			return performPB_VISUALISER(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (10/08/07 10:58:39)
 * author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeService_PMateriel.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AFFECTATION
 * Date de création : (10/08/07 10:58:39)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_AFFECTATION() {
	return "NOM_PB_AFFECTATION";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (10/08/07 10:58:39)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AFFECTATION(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice  = (Services.estNumerique(getVAL_LB_EQUIP_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIP_SELECT()): -1);
	if (indice == -1 || getListEquip().size() == 0 || indice > getListEquip().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","affectations d'un petit matériel"));
		return false;
	}
	PM_Affectation_Sce_Infos unASI = (PM_Affectation_Sce_Infos)getListEquip().get(indice);
	PMatInfos unPM = PMatInfos.chercherPMatInfos(getTransaction(),unASI.getPminv());
	if(getTransaction().isErreur()){
		return false;
	}
	if(null==unPM){
		unPM = new PMatInfos();
	}
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	VariableGlobale.ajouter(request,"PMATINFOS",unPM);
	setStatut(STATUT_AFFECTATION,true);
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_AFFECTATION
 * Date de création : (10/08/07 10:58:39)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_AFFECTATION() {
	return "NOM_ST_AFFECTATION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_AFFECTATION
 * Date de création : (10/08/07 10:58:39)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_AFFECTATION() {
	return getZone(getNOM_ST_AFFECTATION());
}
}
