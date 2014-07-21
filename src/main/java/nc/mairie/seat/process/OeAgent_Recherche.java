package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.AgentInterface;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.AgentsMunicipaux;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeAgent_Recherche
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
*/
public class OeAgent_Recherche extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2862610822622316028L;
	public static final int STATUT_PMATAGENT = 16;
	public static final int STATUT_TDBPMAT = 15;
	public static final int STATUT_VISUPMAT = 14;
	public static final int STATUT_PEPERSO = 13;
	public static final int STATUT_OT_MAJ = 12;
	public static final int STATUT_OT = 11;
	public static final int STATUT_DECL_VISU_EQUIP = 10;
	public static final int STATUT_DECL_AJOUT = 9;
	public static final int STATUT_BPC_VISU_EQUIP = 8;
	public static final int STATUT_BPC_VISU_COMPLETE = 7;
	public static final int STATUT_BPC_AJOUT = 6;
	public static final int STATUT_BPC = 5;
	public static final int STATUT_AFFECTATION_SCE = 4;
	public static final int STATUT_EQUIPEMENTSAGENT = 3;
	public static final int STATUT_TDB = 2;
	public static final int STATUT_VISUALISER = 1;
	private java.lang.String[] LB_AGENT;
	private java.lang.String[] LB_SERVICE;
	private ArrayList<AgentInterface> listAgents;
	private Service serviceCourant;
	private String modeRenvoie;
	private boolean first = true;
	public boolean menuService = false;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 */
@Override
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	if(first){
		String menu = (String)VariableGlobale.recuperer(request,"MENU");
		if("menuService".equals(menu)){
			Service unService = (Service)VariableGlobale.recuperer(request,"SERVICE");
			if(null!=unService){
				setServiceCourant(unService);
			}
		}
		
		String nom = (String) VariableActivite.recuperer(this,"NOM");
		modeRenvoie = (String)VariableActivite.recuperer(this,"MODE");
		VariableActivite.enlever(this,"NOM");
		addZone(getNOM_EF_AGENT(),nom);
		if((nom!=null)&&(!nom.equals(""))){
			initialiseListeAgent(request,nom);
		}
	}
	
	first = false;
}


public void initialiseListeAgent(javax.servlet.http.HttpServletRequest request,String nom) throws Exception{
	ArrayList<AgentInterface> listeAgent = new ArrayList<AgentInterface>();
	if(getServiceCourant()!=null){
		listeAgent.addAll(AgentsMunicipaux.listerAgentsMunicipauxNomServi(getTransaction(),nom,getServiceCourant().getServi()));
	}else{
		listeAgent.addAll(AgentsMunicipaux.listerAgentsMunicipauxNom(getTransaction(),nom));
	}
	
	if(getTransaction().isErreur()){
		return ;
	}
	setListAgents(listeAgent);
	if(listeAgent.size()>0){
//		les élèments de la liste 
		int [] tailles = {5,30,5};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","G","G"};
		FormateListe aFormat = new FormateListe(tailles,padding, false);
		for (int i = 0;i<listeAgent.size();i++){
			AgentsMunicipaux unAgentM = (AgentsMunicipaux)listeAgent.get(i);
			String ligne[] = { unAgentM.getNomatr(),unAgentM.getNom().trim()+" "+ unAgentM.getPrenom().trim(),unAgentM.getServi()};
			aFormat.ajouteLigne(ligne);
		}
		setLB_AGENT(aFormat.getListeFormatee());
	}else{
		setLB_AGENT(LBVide);
	}
	if(listAgents.size()==1){
		performPB_VALIDER(request);
	}
	
}


/**
 * Constructeur du process OeAgent_Recherche.
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 */
public OeAgent_Recherche() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AGENT
 * Date de création : (05/04/07 12:06:27)
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
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_AGENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	//setLB_AGENT(LBVide);
	if(getZone(getNOM_EF_AGENT()).equals("")){
		getTransaction().declarerErreur("Le nom de l'agent n'est pas renseigné.");
		return false;
	}
	String nom = getZone(getNOM_EF_AGENT()).toUpperCase();
//	String codeservice = getZone(getNOM_EF_SERVICE());
//	ArrayList listAgent = new ArrayList();
//	ArrayList listInter = new ArrayList();
//	boolean trouve = false;
	if(getZone(getNOM_EF_AGENT()).equals("")){//||getZone(getNOM_EF_SERVICE()).equals("")){
		getTransaction().declarerErreur("Vous devez saisir le nom de l'agent ");
		return false;
	}
	initialiseListeAgent(request,nom);
//		listInter = AgentCDE.listerAgentCDENom(getTransaction(),nom);
//		if(getTransaction().isErreur()){
//			getTransaction().traiterErreur();
//			trouve = false;
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
//		}
//		if (listInter.size()>0){
//			for (int i=0;i<listInter.size();i++){
//				listAgent.add(listInter.get(i));
//			}
//		}
//		setListAgents(listAgent);
		//initialiseListeAgent(request,);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SERVICE
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
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
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_SERVICE(javax.servlet.http.HttpServletRequest request) throws Exception {
	
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_AGENT
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_AGENT() {
	return "NOM_EF_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_AGENT
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_AGENT() {
	return getZone(getNOM_EF_AGENT());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_SERVICE
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_SERVICE() {
	return "NOM_EF_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_SERVICE
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_SERVICE() {
	return getZone(getNOM_EF_SERVICE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_AGENT
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 */
private String [] getLB_AGENT() {
	if (LB_AGENT == null)
		LB_AGENT = initialiseLazyLB();
	return LB_AGENT;
}
/**
 * Setter de la liste:
 * LB_AGENT
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 */
private void setLB_AGENT(java.lang.String[] newLB_AGENT) {
	LB_AGENT = newLB_AGENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_AGENT
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_AGENT() {
	return "NOM_LB_AGENT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_AGENT_SELECT
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_AGENT_SELECT() {
	return "NOM_LB_AGENT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_AGENT
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_AGENT() {
	return getLB_AGENT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_AGENT
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_AGENT_SELECT() {
	return getZone(getNOM_LB_AGENT_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_SERVICE
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 */
private String [] getLB_SERVICE() {
	if (LB_SERVICE == null)
		LB_SERVICE = initialiseLazyLB();
	return LB_SERVICE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_SERVICE
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_SERVICE() {
	return "NOM_LB_SERVICE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_SERVICE_SELECT
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_SERVICE_SELECT() {
	return "NOM_LB_SERVICE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_SERVICE
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_SERVICE() {
	return getLB_SERVICE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_SERVICE
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_SERVICE_SELECT() {
	return getZone(getNOM_LB_SERVICE_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (05/04/07 12:08:13)
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
 * Date de création : (05/04/07 12:08:13)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on récupère l'agent
	String type = (String)VariableActivite.recuperer(this,"TYPE");
	ArrayList<?> a;
	int indice  = (Services.estNumerique(getVAL_LB_AGENT_SELECT()) ? Integer.parseInt(getVAL_LB_AGENT_SELECT()): -1);
//	if (indice == -1 || getListAgents().size() == 0 || indice > getListAgents().size() -1) {
//		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Agent"));
//		return false;
//	}
	if(indice==-1){
		if(listAgents.size()==1){
			indice =0;
		}else{
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Agent"));
			return false;
		}
	}
	//Agents unAgent = (Agents)getListAgents().get(indice);
	AgentsMunicipaux unAgentM = (AgentsMunicipaux)getListAgents().get(indice);
	
	//ArrayList a = new ArrayList();
	// on regarde sur quelle fenêtre on débranche (liste des équip d'un agent ou le visu directement)
	// si type = Equipement
	if(("Equipement").equals(type)){
		a = AffectationServiceInfos.listerAffectationServiceInfosAgent(getTransaction(),unAgentM.getNomatr());
	}else{
		a = PM_Affectation_Sce_Infos.listerPmAffectationSceInfosAgent(getTransaction(),unAgentM.getNomatr());
	}
	
	if(getTransaction().isErreur()){
		return false;
	}
	if ((a.size()>1)||(a.size()==0)){
		if ((("VISUALISER").equals(modeRenvoie))||(("VISUPMAT").equals(modeRenvoie))){
			VariableActivite.ajouter(this,"NOMATR",unAgentM.getNomatr());
			VariableActivite.ajouter(this,"CODESERVICE",getZone(getNOM_EF_SERVICE()));
			setStatut(STATUT_PROCESS_APPELANT);
		}else{
			// on ouvre l'écran des équipements d'un agent
			VariableActivite.ajouter(this,"NOMATR",unAgentM.getNomatr());
			VariableActivite.ajouter(this,"CODESERVICE",getZone(getNOM_EF_SERVICE()));
//			if (("TDB").equals(modeRenvoie)){
//			VariableActivite.ajouter(this,"ORIGINE","TDB");
//			}else{
//				VariableActivite.ajouter(this,"ORIGINE","EQUIPEMENT");
//			}
			VariableActivite.ajouter(this,"ORIGINE",modeRenvoie);
			//setStatut(STATUT_EQUIPEMENTSAGENT,false);
			if(a.size()==0){
				VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",new EquipementInfos());
				VariableGlobale.ajouter(request,"PMATINFOS",new PMatInfos());
				if(modeRenvoie.equals("TDB")){
					setStatut(STATUT_TDB);
				}else if(modeRenvoie.equals("TDBPMAT")){
					setStatut(STATUT_TDBPMAT);
				}else{
					setStatut(STATUT_PROCESS_APPELANT);
				}
			}else{
				if(("Equipement").equals(type)){
					setStatut(STATUT_EQUIPEMENTSAGENT,true);
				}else{
					 if(("TDBPMAT").equals(modeRenvoie)){
						setStatut(STATUT_PMATAGENT,true);
					}else{
					setStatut(STATUT_PMATAGENT);
					}
				}
			}
		}
	}else {
		if(("Equipement").equals(type)){
			EquipementInfos unEI = new EquipementInfos();
			if(a.size()==1){
				AffectationServiceInfos monASI = (AffectationServiceInfos)a.get(0);
				unEI = EquipementInfos.chercherEquipementInfos(getTransaction(),monASI.getNumeroinventaire());
				if(getTransaction().isErreur()){
					return false;
				}
				if(null==unEI){
					unEI = new EquipementInfos();
				}
			}
			VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",unEI);
		}else{
			PMatInfos unPMI = new PMatInfos();
			if(a.size()==1){
				PM_Affectation_Sce_Infos monPMASI = (PM_Affectation_Sce_Infos)a.get(0);
				unPMI = PMatInfos.chercherPMatInfos(getTransaction(),monPMASI.getPminv());
				if(getTransaction().isErreur()){
					return false;
				}
			}
			if (null==unPMI){
				unPMI = new PMatInfos();
			}
			VariableGlobale.ajouter(request,"PMATINFOS",unPMI);
		}
		VariableActivite.ajouter(this,"DEBRANCHE","TRUE");
		
		if (("VISUALISER").equals(modeRenvoie)){
			setStatut(STATUT_VISUALISER,true);
		}else if(("TDB").equals(modeRenvoie)){
			setStatut(STATUT_TDB,false);
		}else if(("BPC_AJOUT").equals(modeRenvoie)){
			setStatut(STATUT_BPC_AJOUT,false);
		}else if(("BPC_VISUCOMPLETE").equals(modeRenvoie)){
			setStatut(STATUT_BPC_VISU_COMPLETE,false);
		}else if (("PEPERSO").equals(modeRenvoie)){
			setStatut(STATUT_PEPERSO,false);
		}else if(("BPC_VISU_EQUIP").equals(modeRenvoie)){
			setStatut(STATUT_BPC_VISU_EQUIP,false);
		}else if(("BPC").equals(modeRenvoie)){
			setStatut(STATUT_BPC,false);
		}else if(("DECL_VISU").equals(modeRenvoie)){
			setStatut(STATUT_DECL_VISU_EQUIP,false);
		}else if(("DECL_AJOUT").equals(modeRenvoie)){
			setStatut(STATUT_DECL_AJOUT,false);
		}else if(("OT_MAJ").equals(modeRenvoie)){
			setStatut(STATUT_OT_MAJ,false);
		}else if(("OT").equals(modeRenvoie)){
			setStatut(STATUT_OT,false);
		}else if(("AFFECTATION_SCE").equals(modeRenvoie)){
			setStatut(STATUT_AFFECTATION_SCE,false);
		}else if(("TDBPMAT").equals(modeRenvoie)){
			setStatut(STATUT_TDBPMAT,false);
		}else if(("VISUPMAT").equals(modeRenvoie)){
			setStatut(STATUT_VISUPMAT,false);
		}
	}
	
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (05/04/07 12:10:38)
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
 * Date de création : (05/04/07 12:10:38)
 * @author : Générateur de process
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
 * Date de création : (05/04/07 13:12:45)
 * @author : Générateur de process
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
 * Date de création : (05/04/07 13:12:45)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	if(!getZone(getNOM_EF_SERVICE()).equals("")){
		Service unService = Service.chercherService(getTransaction(),getZone(getNOM_EF_SERVICE()));
		if(getTransaction().isErreur()){
			/* Modif par luc le 08/01/10
			getTransaction().declarerErreur("Le code du service n'est pas valide.");
			*/
			return false;
		}
		setServiceCourant(unService);
	}
	//	 on liste les agents du service
	//String param = getZone(getNOM_EF_SERVICE());
	ArrayList<AgentInterface> liste = new ArrayList<AgentInterface>();
//	 selon le code service
	if(getServiceCourant()!=null){
		if(getServiceCourant().getServi()!=null){
			if(getServiceCourant().getServi().equals("4000")){
				liste.addAll(AgentCDE.listerAgentCDE(getTransaction()));
				if(getTransaction().isErreur()){
					return false;
				}
			}else if (getServiceCourant().getServi().equals("5000")){
				liste.addAll(AgentCCAS.listerAgentCCAS(getTransaction()));
				if(getTransaction().isErreur()){
					return false;
				}
			}else{
				liste.addAll(Agents.listerAgents(getTransaction()));
				if(getTransaction().isErreur()){
					return false;
				}
			}
			setListAgents(liste);
			//initialiseListeAgent(request);
		}
	}
	return true;
}
/**
 * @return Renvoie listeAffectation.
 */
public ArrayList<AgentInterface> getListAgents() {
	return listAgents;
}
/**
 * @param listAgents listAgents à définir.
 */
public void setListAgents(ArrayList<AgentInterface> listAgents) {
	this.listAgents = listAgents;
}
/**
 * @return Renvoie tintervalleCourant.
 */
private Service getServiceCourant() {
	return serviceCourant;
}
/**
 * @param tintervalleCourant tintervalleCourant à définir.
 */
private void setServiceCourant(Service serviceCourant) {
	this.serviceCourant = serviceCourant;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (05/04/07 12:06:27)
 * @author : Générateur de process
 */
@Override
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}

		//Si clic sur le bouton PB_AGENT
		if (testerParametre(request, getNOM_PB_AGENT())) {
			return performPB_AGENT(request);
		}

		//Si clic sur le bouton PB_SERVICE
		if (testerParametre(request, getNOM_PB_SERVICE())) {
			return performPB_SERVICE(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (25/04/07 08:06:07)
 * @author : Générateur de process
 */
@Override
public String getJSP() {
	return "OeAgent_Recherche.jsp";
}
}
