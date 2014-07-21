package nc.mairie.seat.process;

import java.util.ArrayList;
import java.util.ListIterator;

import nc.mairie.seat.metier.AgentsMunicipaux;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeAgent_Equipements
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
*/
public class OeAgent_PMateriel extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2639146056196000052L;
	public static final int STATUT_AFF_SCE = 6;
	public static final int STATUT_AFF_AGENT = 5;
	public static final int STATUT_PMPEPERSO = 4;
	public static final int STATUT_TDBPMAT = 3;
	public static final int STATUT_AGENTS = 2;
	public static final int STATUT_VISUALISER = 1;
	private java.lang.String[] LB_EQUIP;
	private ArrayList<PM_Affectation_Sce_Infos> listEquip;
	String matricule = "";
	public String origine = "";
	public boolean isVide = false;
	private String focus = null;
	String responsable = "";
	boolean trouve = false;
	String codeservice = "";
	public boolean isDebranche = false;
	public boolean menuService = false;
	private Service serviceCourant;
	private boolean first = true;
	
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if (getFirst()){
//		 si menu des services
		String menu = (String)VariableGlobale.recuperer(request,"MENU");
		if("menuService".equals(menu)){
			menuService = true;
//			 récupération du Service
			String accro = (String)VariableGlobale.recuperer(request,"ACCRONYME");
			ArrayList<?> listService = Service.chercherListServiceAccro(getTransaction(),accro);
			if(getTransaction().isErreur()){
				return ;
			}
			if(listService.size()>0){
				Service unService = (Service)listService.get(0);
				VariableGlobale.ajouter(request,"SERVICE",unService);
				setServiceCourant(unService);
				if(!codeservice.equals("")){
					if(!unService.getServi().substring(0,3).equals(codeservice.substring(0,3))){
						getTransaction().declarerErreur("L'agent ne fait pas partie du service.");
						return;
					}
				}
			}
		}else{
			menuService = false;
		}
	}
//		if(menuService){
//			Service unService = (Service)VariableGlobale.recuperer(request,"SERVICE");
//			setServiceCourant(unService);
//			if(!codeservice.equals("")){
//				if(!unService.getServi().substring(0,3).equals(codeservice.substring(0,3))){
//					getTransaction().declarerErreur("L'agent ne fait pas partie du service.");
//					return;
//				}
//			}
//		}

	matricule = (String)VariableActivite.recuperer(this,"NOMATR");
	origine = (String)VariableActivite.recuperer(this,"ORIGINE");
	if((!"".equals(origine))&&(null!=origine)){
		isDebranche = true;
	}else{
		isDebranche = false;
	}
	//(String)VariableActivite.recuperer(this,"CODESERVICE");
	if((getZone(getNOM_EF_AGENT())!=null)&&(!getZone(getNOM_EF_AGENT()).equals(""))){
		performPB_AGENT(request);
	}
	if((matricule!=null)&&(!matricule.equals(""))){
		initialiseInfos(request);
	}
	
//	if((getListEquip()==null)||(getListEquip().size()==0)){
//		//addZone(getNOM_PB_AGENT(),"toto");
//		request.setAttribute(getNOM_PB_AGENT(),"toto");
//		
//	}
	setFocus(getNOM_EF_AGENT());
	
//	if((getZone(getNOM_EF_AGENT())!=null)&&(!getZone(getNOM_EF_AGENT()).equals(""))){
//		performPB_AGENT(request);
//	}
	isFirst(false);
}

public boolean initialiseInfos(javax.servlet.http.HttpServletRequest request) throws Exception{

	if ((matricule!=null)&&(!matricule.equals(""))){
		// pour renseigner les infos du service 
		
		AgentsMunicipaux unAgent;
		if(getServiceCourant()!=null){
			unAgent = AgentsMunicipaux.chercherAgentsMunicipauxService(getTransaction(),matricule,getServiceCourant().getServi());
		}else{
			unAgent = AgentsMunicipaux.chercherAgentsMunicipaux(getTransaction(),matricule);
		}
		if(getTransaction().isErreur()){
			return false;
		}
		codeservice = unAgent.getServi();
		addZone(getNOM_ST_AGENT(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
		
//		AgentCDE unAgentCDE = AgentCDE.chercherAgentCDE(getTransaction(),matricule);
//		if (getTransaction().isErreur()){
//			getTransaction().traiterErreur();
//			trouve = false;
//		}
//		if(unAgentCDE!=null){
//			if (unAgentCDE.getNomatr()!=null){
//				trouve = true;
//				if(!codeservice.equals("4000")){
//					codeservice = "4000";
//					addZone(getNOM_ST_AGENT(),unAgentCDE.getNom().trim()+" "+unAgentCDE.getPrenom().trim());
//				}
//			}
//		}
//		AgentCCAS unAgentCCAS = AgentCCAS.chercherAgentCCAS(getTransaction(),matricule);
//		if (getTransaction().isErreur()){
//			getTransaction().traiterErreur();
//			trouve = false;
//		}
//		if(unAgentCCAS!=null){
//			if (unAgentCCAS.getNomatr()!=null){
//				trouve = true;
//				if(!codeservice.equals("5000")){
//					codeservice = "5000";
//					addZone(getNOM_ST_AGENT(),unAgentCCAS.getNom().trim()+" "+unAgentCCAS.getPrenom().trim());
//				}
//			}
//		}
//		AActifs unAgentActif = AActifs.chercherAActifs(getTransaction(),matricule);
//		if (getTransaction().isErreur()){
//			getTransaction().traiterErreur();
//			trouve = false;
//		}
//		if(unAgentActif!=null){
//			if (unAgentActif.getNomatr()!=null){
//				trouve = true;
//				if (!codeservice.equals(unAgentActif.getServi())){
//					codeservice = unAgentActif.getServi();
//					addZone(getNOM_ST_AGENT(),unAgentActif.getNom().trim()+" "+unAgentActif.getPrenom().trim());
//				}
//			}
//		}
		if(menuService){
			if(null!=getServiceCourant()){
				if(!codeservice.substring(0,3).equals(getServiceCourant().getServi().substring(0,3))){
					getTransaction().declarerErreur("L'agent n'appartient pas au service.");
					return false;
				}
			}
		}
		// affichage des infos du service
		Service unService = Service.chercherService(getTransaction(),codeservice);
		if(getTransaction().isErreur()){
			return false;
		}
		addZone(getNOM_ST_CODE_SCE(),codeservice);
		addZone(getNOM_ST_LIBELLE_SCE(),unService.getLiserv().trim());
//		 recherche des petits matériels dont il est responsable
		initialiseListePMat(request);
	}
	return true;
}

public boolean initialiseListePMat(javax.servlet.http.HttpServletRequest request) throws Exception{
//	 recherche des petits matériels affectés
	ArrayList<PM_Affectation_Sce_Infos> a = PM_Affectation_Sce_Infos.listerPmAffectationSceInfosAgent(getTransaction(),matricule);
	if(getTransaction().isErreur()){
		return false;
	}
	if (a.size()>0){
			setListEquip(a);
			trier(a);
			if(!menuService){
				isVide = true;
			}
	}else{
		setLB_EQUIP(null);
		getTransaction().declarerErreur("Cet agent n'est responsable d'aucun petit matériel.");
		return false;
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
			PMatInfos unPM = PMatInfos.chercherPMatInfos(getTransaction(),monASI.getPminv());
			if(getTransaction().isErreur()){
				return ;
			}
			String ligne [] = { monASI.getPminv(),monASI.getPmserie(),unPM.getDesignationmarque().trim()+" "+unPM.getDesignationmodele(),unPM.getDesignationtypeequip().trim(),monASI.getDdebut()};
			aFormat.ajouteLigne(ligne);
		}
	
		setLB_EQUIP(aFormat.getListeFormatee());
	} else {
		setLB_EQUIP(null);
		
	}
	return ;
}
/**
 * Constructeur du process OeAgent_Equipements.
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 */
public OeAgent_PMateriel() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AGENT
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_AGENT() {
	return "NOM_PB_AGENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_AGENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	String nom = getZone(getNOM_EF_AGENT()).toUpperCase();
	ArrayList<AgentsMunicipaux> listAgent = new ArrayList<AgentsMunicipaux>();
	boolean trouve = false;
	
	addZone(getNOM_ST_AGENT(),"");
	addZone(getNOM_ST_CODE_SCE(),"");
	addZone(getNOM_ST_LIBELLE_SCE(),"");
	setLB_AGENTS(LBVide);
	setLB_EQUIP(LBVide);
	
	if(getZone(getNOM_EF_AGENT()).equals("")){//||getZone(getNOM_EF_SERVICE()).equals("")){
		getTransaction().declarerErreur("Vous devez saisir le nom de l'agent ");
		return false;
	}
	if(menuService){
		if(getServiceCourant()!=null){
			listAgent = AgentsMunicipaux.listerAgentsMunicipauxNomServi(getTransaction(),nom,getServiceCourant().getServi());
		}else{
			listAgent = AgentsMunicipaux.listerAgentsMunicipauxNom(getTransaction(),nom);
		}
	}else{
		listAgent = AgentsMunicipaux.listerAgentsMunicipauxNom(getTransaction(),nom);
	}
	if(getTransaction().isErreur()){
		return false;
	}
	trouve = true;
//		listInter = AgentCDE.listerAgentCDENom(getTransaction(),nom);
//		if(getTransaction().isErreur()){
//			getTransaction().traiterErreur();
//			trouve = false;
//		}else{
//			trouve = true;
//		}
//		if (listInter.size()>0){
//			for (int i=0;i<listInter.size();i++){
//				listAgent.add(listInter.get(i));
//			}
//		}
//		listInter = AgentCCAS.listerAgentCCASNom(getTransaction(),nom);
//		if(getTransaction().isErreur()){
//			getTransaction().traiterErreur();
//			trouve = false;
//		}else{
//			trouve = true;
//		}
//		if (listInter.size()>0){
//			for (int i=0;i<listInter.size();i++){
//				listAgent.add(listInter.get(i));
//			}
//		}
//		listInter = Agents.listerAgentsNom(getTransaction(),nom);
//		if(getTransaction().isErreur()){
//			getTransaction().traiterErreur();
//			trouve = false;
//		}else{
//			trouve = true;
//		}
//		if (listInter.size()>0){
//			for (int i=0;i<listInter.size();i++){
//				listAgent.add(listInter.get(i));
//			}
//		}
	if(listAgent.size()==0){
		if(Services.estNumerique(nom)){
//			contrôle si l'agent fait partie du service
			if(menuService){
				AgentsMunicipaux unAgent = AgentsMunicipaux.chercherAgentsMunicipauxService(getTransaction(),nom,getServiceCourant().getServi());
				if(getTransaction().isErreur()){
					getTransaction().declarerErreur("Aucun agent trouvé pour ce service.");
					return false;
				}
				if(null!=unAgent){
					listAgent.add(unAgent);
				}else{
					getTransaction().declarerErreur("Aucun agent trouvé pour ce service.");
					return false;
				}
				
			}else{
				AgentsMunicipaux unAgent = AgentsMunicipaux.chercherAgentsMunicipaux(getTransaction(),nom);
				if(getTransaction().isErreur()){
					return false;
				}
				listAgent.add(unAgent);
			}
		}
	}
	if (trouve){
		if (listAgent.size()==1){
			AgentsMunicipaux unAgent = (AgentsMunicipaux)listAgent.get(0);
			// s'il n'a qu'un petit matériel on débranche directement sur la fenêtre visu sinon on les affiche
			ArrayList<?> a = PM_Affectation_Sce_Infos.listerPmAffectationSceInfosAgent(getTransaction(),unAgent.getNomatr());
			if(getTransaction().isErreur()){
				return false;
			}
			if(menuService){
				
				if(a.size()>0){
					PM_Affectation_Sce_Infos monASI = (PM_Affectation_Sce_Infos)a.get(0);
					PMatInfos.chercherPMatInfos(getTransaction(),monASI.getPminv());
					if(getTransaction().isErreur()){
						return false;
					}
					matricule = unAgent.getNomatr();
					initialiseInfos(request);
				}else{
					matricule = unAgent.getNomatr();
					initialiseInfos(request);
					getTransaction().declarerErreur("Cet agent n'est responsable d'aucun petit matériel.");
					return false;
				}
				
			}else{
				if (a.size()==1){
					PM_Affectation_Sce_Infos monASI = (PM_Affectation_Sce_Infos)a.get(0);
					PMatInfos unPM = PMatInfos.chercherPMatInfos(getTransaction(),monASI.getPminv());
					if(getTransaction().isErreur()){
						return false;
					}else if(a.size()==0){
						getTransaction().declarerErreur("Cet agent n'est responsable d'aucun petit matériel.");
						return false;
					}else if(a.size()>0){
						//VariableActivite.ajouter(this,"NOMATR",unAgent.getNomatr());
						matricule = unAgent.getNomatr();
						initialiseInfos(request);
						addZone(getNOM_EF_AGENT(),"");
					}
					if (null==unPM){
						unPM = new PMatInfos();
					}
					setListEquip(null);
					VariableGlobale.ajouter(request,"PMATINFOS",unPM);
					VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
					setStatut(STATUT_VISUALISER,true);
				}else if(a.size()==0){
					matricule = unAgent.getNomatr();
					initialiseInfos(request);
					getTransaction().declarerErreur("Cet agent n'est responsable d'aucun petit matériel.");
					return false;
				}else if(a.size()>1){
					//VariableActivite.ajouter(this,"NOMATR",unAgent.getNomatr());
					matricule = unAgent.getNomatr();
					initialiseInfos(request);
					addZone(getNOM_EF_AGENT(),"");
				}
			}
		}else{
			// si plusieurs on affiche la liste du résultat des agents
			VariableActivite.ajouter(this,"NOM",nom);
			VariableActivite.ajouter(this,"MODE","VISUPMAT");
			VariableActivite.ajouter(this,"TYPE","PMATERIEL");
			setStatut(STATUT_AGENTS,true);
		}
	}
	VariableActivite.enlever(this,"NOMATR");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VISUALISER
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
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
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_VISUALISER(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 débranchement sur l'écran de visu d'un petit matériel
	int indice = 0;
	if(getListEquip().size()>1){
		indice  = (Services.estNumerique(getVAL_LB_EQUIP_SELECT()) ? Integer.parseInt(getVAL_LB_EQUIP_SELECT()): -1);
		if (indice == -1 || getListEquip().size() == 0 || indice > getListEquip().size() -1) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Affectations d'un petit matériel"));
			return false;
		}
	}
	PM_Affectation_Sce_Infos unASI = (PM_Affectation_Sce_Infos)getListEquip().get(indice);
	PMatInfos unPM = PMatInfos.chercherPMatInfos(getTransaction(),unASI.getPminv());
	if(getTransaction().isErreur()){
		return false;
	}
	if(null==unPM){
		unPM = new PMatInfos();
	}
	VariableActivite.enlever(this,"NOMATR");
	VariableGlobale.ajouter(request,"PMATINFOS",unPM);
	VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
	if((origine!=null)&&(!origine.equals(""))){
		 if(("TDBPMAT").equals(origine)){
			setStatut(STATUT_TDBPMAT,false);
		}else if(("VISUPMAT").equals(origine)){
			setStatut(STATUT_VISUALISER,true);
		}else if(("PMPEPERSO").equals(origine)){
			setStatut(STATUT_PMPEPERSO,true);
		}else if(("AFFECTATION_AGENT").equals(origine)){
			setStatut(STATUT_AFF_AGENT,true);
		}else if(("AFFECTATION_SCE").equals(origine)){
			setStatut(STATUT_AFF_SCE,true);
		}else{
			setStatut(STATUT_PROCESS_APPELANT);
		}
	}else{
//		 on envoie le petit matériel infos à l'écran visu
		setStatut(STATUT_VISUALISER,true);
	}
	
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_CODE_SCE
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_CODE_SCE() {
	return "NOM_ST_CODE_SCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_CODE_SCE
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_CODE_SCE() {
	return getZone(getNOM_ST_CODE_SCE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_LIBELLE_SCE
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_LIBELLE_SCE() {
	return "NOM_ST_LIBELLE_SCE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_LIBELLE_SCE
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_LIBELLE_SCE() {
	return getZone(getNOM_ST_LIBELLE_SCE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_AGENT
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_AGENT() {
	return "NOM_EF_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_AGENT
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_AGENT() {
	return getZone(getNOM_EF_AGENT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_EQUIP
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 */
private String [] getLB_EQUIP() {
	if (LB_EQUIP == null)
		LB_EQUIP = initialiseLazyLB();
	return LB_EQUIP;
}
/**
 * Setter de la liste:
 * LB_EQUIP
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 */
private void setLB_EQUIP(java.lang.String[] newLB_EQUIP) {
	LB_EQUIP = newLB_EQUIP;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_EQUIP
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_EQUIP() {
	return "NOM_LB_EQUIP";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_EQUIP_SELECT
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_EQUIP_SELECT() {
	return "NOM_LB_EQUIP_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_EQUIP
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_EQUIP() {
	return getLB_EQUIP();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_EQUIP
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_EQUIP_SELECT() {
	return getZone(getNOM_LB_EQUIP_SELECT());
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
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_SERVICE
 * Date de création : (05/04/07 12:34:50)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_SERVICE() {
	return "NOM_EF_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_SERVICE
 * Date de création : (05/04/07 12:34:50)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_SERVICE() {
	return getZone(getNOM_EF_SERVICE());
}
	private java.lang.String[] LB_AGENTS;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_AGENTS
 * Date de création : (05/04/07 12:46:31)
 * @author : Générateur de process
 */
private String [] getLB_AGENTS() {
	if (LB_AGENTS == null)
		LB_AGENTS = initialiseLazyLB();
	return LB_AGENTS;
}
/**
 * Setter de la liste:
 * LB_AGENTS
 * Date de création : (05/04/07 12:46:31)
 * @author : Générateur de process
 */
private void setLB_AGENTS(java.lang.String[] newLB_AGENTS) {
	LB_AGENTS = newLB_AGENTS;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_AGENTS
 * Date de création : (05/04/07 12:46:31)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_AGENTS() {
	return "NOM_LB_AGENTS";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_AGENTS_SELECT
 * Date de création : (05/04/07 12:46:31)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_AGENTS_SELECT() {
	return "NOM_LB_AGENTS_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_AGENTS
 * Date de création : (05/04/07 12:46:31)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_AGENTS() {
	return getLB_AGENTS();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_AGENTS
 * Date de création : (05/04/07 12:46:31)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_AGENTS_SELECT() {
	return getZone(getNOM_LB_AGENTS_SELECT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_AGENT
 * Date de création : (06/04/07 09:04:30)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_AGENT() {
	return "NOM_ST_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_AGENT
 * Date de création : (06/04/07 09:04:30)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_AGENT() {
	return getZone(getNOM_ST_AGENT());
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
 * @return String
 */
public String getDefaultFocus() {
	return getNOM_EF_AGENT();
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (05/04/07 11:28:20)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RETOUR
		if (testerParametre(request, getNOM_PB_RETOUR())) {
			return performPB_RETOUR(request);
		}
		
//		Si clic sur le bouton PB_VISUALISER
		if (testerParametre(request, getNOM_PB_VISUALISER())) {
			return performPB_VISUALISER(request);
		}
		
		//Si clic sur le bouton PB_AGENT
		if (testerParametre(request, getNOM_PB_AGENT())) {
			return performPB_AGENT(request);
		}

	

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (31/07/07 10:14:33)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeAgent_PMateriel.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUR
 * Date de création : (31/07/07 10:14:33)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_RETOUR() {
	return "NOM_PB_RETOUR";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (31/07/07 10:14:33)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RETOUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
/**
 * @return Renvoie serviceCourant.
 */
public Service getServiceCourant() {
	return serviceCourant;
}
/**
 * @param serviceCourant serviceCourant à définir.
 */
public void setServiceCourant(Service serviceCourant) {
	this.serviceCourant = serviceCourant;
}
/**
 * @return Renvoie serviceCourant.
 */
public boolean getFirst() {
	return first;
}
/**
 * @param first first
 */
public void isFirst(boolean first) {
	this.first = first;
}
}
