package nc.mairie.seat.process;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.Locale;




import nc.mairie.seat.metier.AActifs;
import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.Affecter_Agent;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.AgentInterface;
//import nc.mairie.seat.metier.AgentServiceInfos;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.Service;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos;
import nc.mairie.technique.*;
/**
 * Process OeAffectation_Agent
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
*/
public class OeAffectation_Agent extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2541822876307570931L;
	private java.lang.String[] LB_AFFECTATION;
	private java.lang.String[] LB_AGENT;
	public static final int STATUT_RECHERCHER = 2;
	private String ACTION_SUPPRESSION = "Suppression d'une affectation<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'une affectation.";
	private String ACTION_CREATION = "Création d'une nouvelle affectation.";
	private EquipementInfos equipementInfosCourant;
	private Equipement equipementCourant;
	private Service serviceCourant;
	private Agents agentCourant;
	private Affecter_Agent affecterAgentCourant;
	private Affecter_Agent affecterAgentPrecedent;
	private ArrayList<Affecter_Agent> listeAffectation;
	private ArrayList<AgentInterface> listeAgent;
	private String focus = null;
	private String codeservice = "";
	public int isVide = 0;
	private boolean isFirst = true;
	private boolean manqueParam = false;
	private AgentCDE agentCDECourant;
	private AgentCCAS agentCCASCourant;
	private String[] tri = {"datedebut","hdeb"};
	private boolean[] tOrdre = {false,false};
	public boolean isAction = false;
	public boolean isAffecter = false;
	public boolean isDebranche = false;
	public boolean menuService = false;
	private String dateAff = "";
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	if(isFirst){
//		 si menu des services
		String menu = (String)VariableGlobale.recuperer(request,"MENU");
		if("menuService".equals(menu)){
			menuService = true;
//			 récupération du Service
			String accro = (String)VariableGlobale.recuperer(request,"ACCRONYME");
			ArrayList<Service> listService = Service.chercherListServiceAccro(getTransaction(),accro);
			if(getTransaction().isErreur()){
				return ;
			}
			if(listService.size()>0){
				Service unService = (Service)listService.get(0);
				VariableGlobale.ajouter(request,"SERVICE",unService);
				setServiceCourant(unService);
			}
		}else{
			menuService = false;
		}
	}
	
	String service = "";
	String origine= "";
	EquipementInfos unEquipementInfos = (EquipementInfos)VariableGlobale.recuperer(request, "EQUIPEMENTINFOS");
	setEquipementInfosCourant(unEquipementInfos);
	if(isFirst){
		origine = (String)VariableActivite.recuperer(this,"DEBRANCHE");
		if("TRUE".equals(origine)){
			isDebranche = true;
		}else{
			isDebranche = false;
		}
	}
	//Service unService = (Service)VariableGlobale.recuperer(request,"SERVICE");
	
	//}
	
//	 quand appuie sur entrée
	if(!("").equals(getZone(getNOM_EF_EQUIP()))){
		performPB_EQUIP(request);
		addZone(getNOM_EF_EQUIP(),"");
	}
	if(getTransaction().isErreur()){
		return;
	}
	if (null!=(getEquipementInfosCourant())){
		if(null!=getEquipementInfosCourant().getNumeroinventaire()){
			AffectationServiceInfos unASI = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),Services.dateDuJour());
			if(getTransaction().isErreur()){
				getTransaction().traiterErreur();
				getTransaction().declarerErreur("L'équipement n'est affecté à aucun service.");
				return;
			}
			dateAff = unASI == null ? null : unASI.getDdebut();
			if(menuService){
				if(null!=getServiceCourant()){
					if(!getServiceCourant().getServi().substring(0,3).equals(unASI.getCodeservice().substring(0,3))){
						getTransaction().declarerErreur("L'équipement n'appartient pas au service.");
						return;
					}
				}
			}
			if(unASI!=null){
				if(unASI.getNumeroinventaire()==null){
					getTransaction().traiterErreur();
					isAffecter = false;
					addZone(getNOM_ST_SERVICE(),"PAS AFFECTE");
				}else{
					Service aService = Service.chercherService(getTransaction(),unASI.getCodeservice());
					if(getTransaction().isErreur()){
						getTransaction().declarerErreur(getTransaction().traiterErreur());
						return;
					}
					isAffecter = true;
					if(getTransaction().isErreur()){
						getTransaction().traiterErreur() ;
						service = "Le service n'a pas été trouvé ou n'est pas actif";
					}else{
						service = aService.getLiserv();
						setServiceCourant(aService);
						codeservice = aService.getServi();
					}
					addZone(getNOM_ST_SERVICE(),codeservice+" "+service);
				}
			}
			//initialisation des infos de l'équipement
			addZone(getNOM_ST_NOMEQUIP(),getEquipementInfosCourant().getDesignationmarque()+" "+getEquipementInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
			addZone(getNOM_ST_NOINVENT(),getEquipementInfosCourant().getNumeroinventaire());
			
			/*if (null!=(getServiceCourant())){
				if(getServiceCourant().getServi()!=null){
					codeservice = unService.getServi();
					initialiseListeAffectation(request);
					addZone(getNOM_ST_SERVICE(),getServiceCourant().getLiserv());
					if (!isFirst){
						addZone(getNOM_ST_SERVICE(),getServiceCourant().getLiserv());
						addZone(getNOM_ST_TYPE(),unEquipementInfos.getDesignationtypeequip());
						addZone(getNOM_ST_NOINVENT(),unEquipementInfos.getNumeroinventaire());
						addZone(getNOM_ST_NOIMMAT(),unEquipementInfos.getNumeroimmatriculation());
						addZone(getNOM_ST_NOMEQUIP(),unEquipementInfos.getDesignationmarque()+" "+unEquipementInfos.getDesignationmodele());
					}
				}
			}else{*/
				
//				on remplit la liste des affectations
				initialiseListeAffectation(request);
		}
		//}
	}else{
		setLB_AFFECTATION(LBVide);
		addZone(getNOM_ST_TYPE(),"");
		addZone(getNOM_ST_NOINVENT(),"");
		addZone(getNOM_ST_NOIMMAT(),"");
		addZone(getNOM_ST_NOMEQUIP(),"");
	}
	if (null!=getServiceCourant()){
		//on remplit la liste des agents du service
		if(getServiceCourant().getServi().equals("4000")){
			ArrayList<AgentCDE> a = AgentCDE.listerAgentCDE(getTransaction());
			setListeAgent(new ArrayList<AgentInterface>());
			getListeAgent().addAll(a);
			if (a.size()>0){
				//les élèments de la liste 
				int [] tailles = {40};
				//String [] champs = {"nom", "prenom"};
				String [] padding = {"G"};
				FormateListe aFormat = new FormateListe(tailles,padding, true);
				for (ListIterator<AgentCDE> list = a.listIterator(); list.hasNext(); ) {
					AgentCDE aAActifs = (AgentCDE)list.next();
					String ligne [] = { aAActifs.getNom().trim()+" "+aAActifs.getPrenom().trim()};
					aFormat.ajouteLigne(ligne);
				}
				setLB_AGENT(aFormat.getListeFormatee());
			}else{
				setLB_AGENT(LBVide);
			}
		}else if(getServiceCourant().getServi().equals("5000")){
			ArrayList<AgentCCAS> a = AgentCCAS.listerAgentCCAS(getTransaction());
			setListeAgent(new ArrayList<AgentInterface>());
			getListeAgent().addAll(a);
			if (a.size()>0){
				//les élèments de la liste 
				int [] tailles = {40};
				//String [] champs = {"nom", "prenom"};
				String [] padding = {"G"};
				FormateListe aFormat = new FormateListe(tailles,padding, true);
				for (ListIterator<AgentCCAS> list = a.listIterator(); list.hasNext(); ) {
					AgentCCAS aAActifs = (AgentCCAS)list.next();
					String ligne [] = { aAActifs.getNom().trim()+" "+aAActifs.getPrenom().trim()};
					aFormat.ajouteLigne(ligne);
				}
				setLB_AGENT(aFormat.getListeFormatee());
			}else{
				setLB_AGENT(LBVide);
			}
		}else{
			ArrayList<AActifs> a = AActifs.listerAActifsService(getTransaction(),serviceCourant.getServi());
			setListeAgent(new ArrayList<AgentInterface>());
			getListeAgent().addAll(a);
			if (a.size()>0){
				//les élèments de la liste 
				int [] tailles = {40};
				//String [] champs = {"nom", "prenom"};
				String [] padding = {"G"};
				FormateListe aFormat = new FormateListe(tailles,padding, true);
				for (ListIterator<AActifs> list = a.listIterator(); list.hasNext(); ) {
					AActifs aAActifs = (AActifs)list.next();
					String ligne [] = { aAActifs.getNom().trim()+" "+aAActifs.getPrenom().trim()};
					aFormat.ajouteLigne(ligne);
				}
				setLB_AGENT(aFormat.getListeFormatee());
			}else{
				setLB_AGENT(LBVide);
			}
		}
			if (LB_AGENT.length==0){
				getTransaction().declarerErreur("Aucun agent n'est enregistré pour ce service.L'affectation n'est donc pas possible.");
				setManqueParam(true);
			}else{
				setManqueParam(false);
			}
	}
	setFocus(getNOM_EF_EQUIP());
	isFirst=false;
	if(null==getServiceCourant()){
		setServiceCourant(new Service());
	}
	VariableGlobale.ajouter(request,"SERVICE",getServiceCourant());
}

/**
 * Initialisation de la liste des entretiens
 * @author : Coralie NICOLAS
 */
private void initialiseListeAffectation(javax.servlet.http.HttpServletRequest request) throws Exception{
//	Recherche des agents du service
	//java.util.ArrayList a = AffectationAgentInfos.chercherListAffectationsSce(getTransaction(),serviceCourant.getServi());
	if(null==dateAff){
		dateAff = Services.dateDuJour();
	}
	ArrayList<Affecter_Agent> a = Affecter_Agent.chercherListAffecter_AgentEquipSceEnCours(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),getServiceCourant().getServi(),Services.formateDateInternationale(dateAff));
	if(getTransaction().isErreur()){
		return;
	}
	if (null == a){
		System.out.println("Aucun élément enregistré dans la base.");
	}else{
		if (a.size()>0){
			setListeAffectation(a);
			trier(a);
		}else{
			setLB_AFFECTATION(null);
			setIsVide(a.size());
		}
	}
	
	return ;		
}
/**
 * Constructeur du process OeAffectation_Agent.
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
public OeAffectation_Agent() {
	super();
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	isAction = true;
	//	on met la date du jour par défaut
	DateFormat datedujour = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE); 
	addZone(getNOM_EF_DATE(),datedujour.format(new Date()));
	addZone(getNOM_EF_DATEFIN(),datedujour.format(new Date()));
	
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	addZone(getNOM_LB_AGENT_SELECT(),"0");
	addZone(getNOM_EF_HDEB(),"");
	addZone(getNOM_EF_HDEBMN(),"");
	addZone(getNOM_EF_HFIN(),"");
	addZone(getNOM_EF_HFINMN(),"");
	//VariableGlobale.enlever(request,"SERVICE");
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
/**
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
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	isAction = false;
	if(codeservice!=null && (!codeservice.equals(""))){
		Service unService = Service.chercherService(getTransaction(),codeservice);
		if(getTransaction().isErreur()){
			return false;
		}
		setServiceCourant(unService);
	}
	addZone(getNOM_ST_TITRE_ACTION(),"");
	//on vide les zones
	addZone(getNOM_EF_DATE(),"");
	addZone(getNOM_EF_DATEFIN(),"");
	addZone(getNOM_EF_HDEB(),"");
	addZone(getNOM_EF_HDEBMN(),"");
	addZone(getNOM_EF_HFIN(),"");
	addZone(getNOM_EF_HFINMN(),"");
	addZone(getNOM_EF_SERVICE(),"");
	addZone(getNOM_ST_TYPE(),"");
	addZone(getNOM_ST_AGENT(),"");
	addZone(getNOM_ST_DATE(),"");
	addZone(getNOM_ST_DATEDEB(),"");
	addZone(getNOM_ST_DATEFIN(),"");
	addZone(getNOM_ST_HEUREDEB(),"");
	addZone(getNOM_ST_HEUREFIN(),"");
	addZone(getNOM_ST_TYPE(),"");
	setLB_AGENT(LBVide);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	isAction = true;
	//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_AFFECTATION_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_AFFECTATION_SELECT())) : -1);
	if (numligne == -1 || getListeAffectation().size() == 0 || numligne > getListeAffectation().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","affectations"));
		return false;
	}
	if (numligne == 0){
		//On nomme l'action
		addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	
		//Récup de l'affectation courante
		Affecter_Agent unAffecter_Agent = (Affecter_Agent)getListeAffectation().get(numligne);
		
		// Récup de l'affectation précédent
		if(getListeAffectation().size()>1){
			Affecter_Agent unAffecterAgentPrec = (Affecter_Agent)getListeAffectation().get(numligne+1);
			setAffecterAgentPrecedent(unAffecterAgentPrec);
		}
		//getAffecterAgentCourant().setDatedebut(aAffInfos.getDatedebut());
		//Affecter_Agent unAffecter_Agent = Affecter_Agent.chercherAffecter_Agent(getTransaction(),aAffInfos.getNumeroinventaire(),aAffInfos.getMatricule(),aAffInfos.getDatedebut());
		setAffecterAgentCourant(unAffecter_Agent);
		
		//Alim zones
		addZone(getNOM_EF_DATE(), affecterAgentCourant.getDatedebut());
		addZone(getNOM_EF_SERVICE(), serviceCourant.getLiserv());
		addZone(getNOM_EF_HDEB(),affecterAgentCourant.getHdeb());
		addZone(getNOM_EF_HDEBMN(),affecterAgentCourant.getHdebmn());
		addZone(getNOM_EF_HFIN(),affecterAgentCourant.getHfin());
		addZone(getNOM_EF_HFINMN(),affecterAgentCourant.getHfinmn());
		// contrôle pour un affichage correct
		if (!affecterAgentCourant.getDatefin().equals("01/01/0001")){
			addZone(getNOM_EF_DATEFIN(),affecterAgentCourant.getDatefin());
		}
		if (affecterAgentCourant.getHdebmn().equals("0")){
			addZone(getNOM_EF_HDEBMN(),"00");
		}
		if (affecterAgentCourant.getHfinmn().equals("0")){
			addZone(getNOM_EF_HFINMN(),"00");
		}
		//on sélectionne le bon agent
		int position = -1;
		addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(position));
		if(getServiceCourant().getServi().equals("4000")){
			for (int i = 0; i < getListeAgent().size(); i++) {
				AgentCDE unAgent = (AgentCDE)getListeAgent().get(i);
				if (unAgent.getNomatr().trim() .equals(getAffecterAgentCourant().getMatricule().trim())) {
					addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
					break;
				}
			}
		}else if(getServiceCourant().getServi().equals("5000")){
			for (int i = 0; i < getListeAgent().size(); i++) {
				AgentCCAS unAgent = (AgentCCAS)getListeAgent().get(i);
				if (unAgent.getNomatr().trim() .equals(getAffecterAgentCourant().getMatricule().trim())) {
					addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
					break;
				}
			}
		}else{
			for (int i = 0; i < getListeAgent().size(); i++) {
				AActifs unAgent = (AActifs)getListeAgent().get(i);
				if (unAgent.getNomatr().trim() .equals(getAffecterAgentCourant().getMatricule().trim())) {
					addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
					break;
				}
			}
		}
	}else{
		getTransaction().declarerErreur("Seule la dernière affectation peut être supprimée ou modifiée.");
	}
	setStatut(STATUT_MEME_PROCESS);
	setFocus(getNOM_EF_SERVICE());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","AFFECTATION_AGENT");
	setStatut(STATUT_RECHERCHER,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SELECTIONNER
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_SELECTIONNER() {
	return "NOM_PB_SELECTIONNER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SELECTIONNER(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	isAction = true;
	int numligne = (Services.estNumerique(getZone(getNOM_LB_AFFECTATION_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_AFFECTATION_SELECT())) : -1);
	if (numligne == -1 || getListeAffectation().size() == 0 || numligne > getListeAffectation().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Affecter un agent"));
		return false;
	}		
		//Récup de l'affectation courante
		Affecter_Agent unAffecterAgent = (Affecter_Agent)getListeAffectation().get(numligne);
		//Affecter_Agent unAffecterAgent = Affecter_Agent.chercherAffecter_Agent(getTransaction(),aAffInfos.getNumeroinventaire(),aAffInfos.getMatricule(),aAffInfos.getDatedebut());
		setAffecterAgentCourant(unAffecterAgent);
		// on recherche les infos concernant l'agent
		if(unAffecterAgent.getCodeservice().equals("4000")){
			AgentCDE unAgent = AgentCDE.chercherAgentCDE(getTransaction(),unAffecterAgent.getMatricule());
			addZone(getNOM_ST_AGENT(),unAgent.getNom()+" "+unAgent.getPrenom());
		}else if(unAffecterAgent.getCodeservice().equals("5000")){
			AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),unAffecterAgent.getMatricule());
			addZone(getNOM_ST_AGENT(),unAgent.getNom()+" "+unAgent.getPrenom());
		}else{
			Agents unAgent = Agents.chercherAgents(getTransaction(),unAffecterAgent.getMatricule());
			addZone(getNOM_ST_AGENT(),unAgent.getNom()+" "+unAgent.getPrenom());
		}
	if (numligne == 0){
//		On nomme l'action
		addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
		//addZone(getNOM_ST_DATE(),getAffecterAgentCourant().getDatedebut());
		
		addZone(getNOM_ST_DATEDEB(),unAffecterAgent.getDatedebut());
		addZone(getNOM_ST_DATEFIN(),unAffecterAgent.getDatefin());
		addZone(getNOM_LB_AFFECTATION_SELECT(),"0");
		String mn = unAffecterAgent.getHdebmn();
		if (unAffecterAgent.getHdebmn().equals("0")){
			mn="00";
		}
		addZone(getNOM_ST_HEUREDEB(),unAffecterAgent.getHdeb()+"h"+mn);
		if (unAffecterAgent.getHfinmn().equals("0")){
			mn="00";
		}
		addZone(getNOM_ST_HEUREFIN(),unAffecterAgent.getHfin()+"h"+mn);
	}else{
		getTransaction().declarerErreur("Seule la dernière affectation peut être supprimée ou modifiée.");
	}

return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
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
	if (!getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		if (getZone(getNOM_EF_DATE()).equals("")){
			getTransaction().declarerErreur("La date de début doit être renseignée.");
			setFocus(getNOM_EF_DATE());
			return false;
		}
		if (getZone(getNOM_EF_DATEFIN()).equals("")){
			getTransaction().declarerErreur("La date de fin doit être renseignée.");
			setFocus(getNOM_EF_DATEFIN());
			return false;
		}
	}
	
	/*if(getServiceCourant().getServi().equals("4000")){
		AgentCDE monAActifs = new AgentCDE();
	}else if (getServiceCourant().getServi().equals("5000")){
		AgentCCAS monAActifs = new AgentCCAS();
	}else{
		AActifs monAActifs = new AActifs();
	}*/
	// si l'action n'est pas une suppression
	if (!getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
	//	Test si ligne sélectionnée
		int numligne = (Services.estNumerique(getZone(getNOM_LB_AGENT_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_AGENT_SELECT())) : -1);
		if (numligne == -1 || getListeAgent().size() == 0 || numligne > getListeAgent().size() -1 ) {
			getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Agents"));
			return false;
		}
	//	Récup du l'agent courant
		if("4000".equals(getServiceCourant().getServi())){
			AgentCDE monAActifs = new AgentCDE();
			monAActifs = (AgentCDE)getListeAgent().get(numligne);
			AgentCDE unAgent = AgentCDE.chercherAgentCDE(getTransaction(),monAActifs.getNomatr());
			if (getTransaction().isErreur()){
				return false;
			}else{
				setAgentCDECourant(unAgent);
			}
		}else if(getServiceCourant().getServi().equals("5000")){
			AgentCCAS monAActifs = new AgentCCAS();
			monAActifs = (AgentCCAS)getListeAgent().get(numligne);
			AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),monAActifs.getNomatr());
			if (getTransaction().isErreur()){
				return false;
			}else{
				setAgentCCASCourant(unAgent);
			}
		}else{
			AActifs monAActifs = new AActifs();
			monAActifs = (AActifs)getListeAgent().get(numligne);
			Agents unAgent = Agents.chercherAgents(getTransaction(),monAActifs.getNomatr());
			if (getTransaction().isErreur()){
				return false;
			}else{
				setAgentCourant(unAgent);
			}
		}
	}
	//récupération des infos
	String newDate = "";
	if (Services.estUneDate(getZone(getNOM_EF_DATE()))){
		newDate = Services.formateDate(getZone(getNOM_EF_DATE()));
	}
	String newDatefin = "";
	if (Services.estUneDate(getZone(getNOM_EF_DATEFIN()))){
		newDatefin = Services.formateDate(getZone(getNOM_EF_DATEFIN()));
	}
	String newHDeb = getZone(getNOM_EF_HDEB());
	String newHFin = getZone(getNOM_EF_HFIN());
	String newDebMn = getZone(getNOM_EF_HDEBMN());
	String newFinMn = getZone(getNOM_EF_HFINMN());
//	 on met 00  pour les heures et mn
	if ("".equals(newHDeb)){
		newHDeb="00";
	}else{
		if(Integer.parseInt(newHDeb)>24){
			getTransaction().declarerErreur("La valeur de l'heure de début ne doit pas être supérieure à 24.");
			setFocus(getNOM_EF_HDEB());
			return false;
		}
		if(Integer.parseInt(newHDeb)<0){
			getTransaction().declarerErreur("La valeur de l'heure de début ne doit pas être négative.");
			setFocus(getNOM_EF_HDEB());
			return false;
		}
	}
	if ("".equals(newDebMn)){
		newDebMn="00";
	}else{
		if(Integer.parseInt(newDebMn)>59){
			getTransaction().declarerErreur("Les minutes de l'heure de début ne doivent pas être supérieures à 59.");
			setFocus(getNOM_EF_HDEBMN());
			return false;
		}
		if(Integer.parseInt(newDebMn)<0){
			getTransaction().declarerErreur("La valeur ne doit pas être négative.");
			setFocus(getNOM_EF_HDEBMN());
			return false;
		}
	}
	if ("".equals(newHFin)){
		newHFin ="00";
	}else{
		if(Integer.parseInt(newHFin)>24){
			getTransaction().declarerErreur("La valeur de l'heure de fin ne doit pas être supérieure à 25.");
			setFocus(getNOM_EF_HFIN());
			return false;
		}
		if(Integer.parseInt(newHFin)<0){
			getTransaction().declarerErreur("La valeur ne doit pas être négative.");
			setFocus(getNOM_EF_HFIN());
			return false;
		}
	}
	if ("".equals(newFinMn)){
		newFinMn="00";
	}else{
		if(Integer.parseInt(newFinMn)>59){
			getTransaction().declarerErreur("Les minutes de l'heure de fin ne doivent pas être supérieures à 59.");
			setFocus(getNOM_EF_HFINMN());
			return false;
		}
		if(Integer.parseInt(newFinMn)<0){
			getTransaction().declarerErreur("La valeur ne doit pas être négative.");
			setFocus(getNOM_EF_HFINMN());
			return false;
		}
	}
//	 contrôle que l'utilisateur n'a pas saisie 24:30 ou 00:30
	if(newDate.equals(newDatefin)){
		if((!"".equals(newFinMn))&&(!"".equals(newHFin))){
			if((Integer.parseInt(newHFin)>=24)&&(Integer.parseInt(newFinMn)>0)){
				getTransaction().declarerErreur("L'heure de fin est incorrecte.");
				setFocus(getNOM_EF_HFIN());
				return false;
			}
			if((Integer.parseInt(newHFin)==0)&&(Integer.parseInt(newFinMn)>0)){
				getTransaction().declarerErreur("L'heure de fin est incorrecte.");
				setFocus(getNOM_EF_HFIN());
				return false;
			}
		}
	}
	Equipement unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
	if (getTransaction().isErreur()){
		return false;
	}
	setEquipementCourant(unEquipement);

//	Si Action Suppression
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {
		//Suppression
		try{
			getAffecterAgentCourant().supprimerAffecter_Agent(getTransaction());
			if (getTransaction().isErreur()){
				return false;
			}
		}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Modification
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {
		if (!Services.estUneDate(getZone(getNOM_EF_DATE()))){
			getTransaction().declarerErreur("La date de début est incorrecte.");
			setFocus(getNOM_EF_DATE());
			return false;
		}
		if (!Services.estUneDate(getZone(getNOM_EF_DATEFIN()))){
			getTransaction().declarerErreur("La date de fin est incorrecte.");
			setFocus(getNOM_EF_DATEFIN());
			return false;
		}
		//Si lib contact non saisit
		if (newDate.length() == 0) {
			getTransaction().declarerErreur("La date est obligatoire");
			return false;
		}
//		
		//Affectation des attributs
		getAffecterAgentCourant().setDatedebut(newDate);
		getAffecterAgentCourant().setHdeb(newHDeb);
		getAffecterAgentCourant().setHfin(newHFin);
		getAffecterAgentCourant().setHdebmn(newDebMn);
		getAffecterAgentCourant().setHfinmn(newFinMn);
		getAffecterAgentCourant().setDatefin(newDatefin);
		//getAffecterAgentCourant().setCodeservice()
//		 on récupère l'affectation précédente s'il y en a une
		//Modification
		
		if(getServiceCourant().getServi().equals("4000")){
			getAffecterAgentCourant().setMatricule(agentCDECourant.getNomatr());
			getAffecterAgentCourant().affecter_agentModifCDE(getTransaction(),getEquipementCourant(),getAgentCDECourant(),getAffecterAgentCourant(),getServiceCourant(),getAffecterAgentPrecedent());
		}else if(getServiceCourant().getServi().equals("5000")){
			getAffecterAgentCourant().setMatricule(agentCCASCourant.getNomatr());
			getAffecterAgentCourant().affecter_agentModifCCAS(getTransaction(),getEquipementCourant(),getAgentCCASCourant(),getAffecterAgentCourant(),getServiceCourant(),getAffecterAgentPrecedent());
		}else{
			getAffecterAgentCourant().setMatricule(agentCourant.getNomatr());
			getAffecterAgentCourant().affecter_agentModif(getTransaction(),getEquipementCourant(),getAgentCourant(),getAffecterAgentCourant(),getServiceCourant(),getAffecterAgentPrecedent());
		}
		if (getTransaction().isErreur())
			return false;
			
	//Si Action Creation		
	} else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {
		if (!Services.estUneDate(getZone(getNOM_EF_DATE()))){
			getTransaction().declarerErreur("La date de début est incorrecte.");
			setFocus(getNOM_EF_DATE());
			return false;
		}
		if (!Services.estUneDate(getZone(getNOM_EF_DATEFIN()))){
			getTransaction().declarerErreur("La date de fin est incorrecte.");
			setFocus(getNOM_EF_DATEFIN());
			return false;
		}
		//Si la date non saisit
		if (newDate.length() == 0) {
			getTransaction().declarerErreur("La date est obligatoire");
			return false;
		}
//		Si l'heure de début non saisit
		/*if (newHDeb.length() == 0) {
			getTransaction().declarerErreur("L'heure de début est obligatoire");
			return false;
		}*/
		
		setAffecterAgentCourant(new Affecter_Agent());
		
		//Affectation des attributs
		getAffecterAgentCourant().setDatedebut(newDate);
		getAffecterAgentCourant().setHdeb(newHDeb);
		getAffecterAgentCourant().setHfin(newHFin);
		getAffecterAgentCourant().setHdebmn(newDebMn);
		getAffecterAgentCourant().setHfinmn(newFinMn);
		getAffecterAgentCourant().setDatefin(newDatefin);
		getAffecterAgentCourant().setCodeservice(getServiceCourant().getServi());
		
		// on récupère l'affectation précédente s'il y en a une
		Affecter_Agent monAG = new Affecter_Agent();
		if (getListeAffectation()!=null){
			if(getListeAffectation().size()>0){
				monAG = (Affecter_Agent)getListeAffectation().get(0);//Affecter_Agent.chercherAffecter_Agent(getTransaction(),monAI.getNumeroinventaire(),monAI.getMatricule(),date);
			}
		}
		
		//Création
		if(getServiceCourant().getServi().equals("4000")){
			getAffecterAgentCourant().affecter_agentCDE(getTransaction(),getEquipementCourant(),getAgentCDECourant(),monAG,getServiceCourant(),newDate,monAG);
		}else if(getServiceCourant().getServi().equals("5000")){
			getAffecterAgentCourant().affecter_agentCCAS(getTransaction(),getEquipementCourant(),getAgentCCASCourant(),monAG,getServiceCourant(),newDate,monAG);
		}else{
			getAffecterAgentCourant().affecter_agent(getTransaction(),getEquipementCourant(),getAgentCourant(),monAG,getServiceCourant(),newDate,monAG);
		}
		
		if (getTransaction().isErreur())
			return false;
		}

	//Tout s'est bien passé
	commitTransaction();
	//initialiseListeCarburant(request);
	//on vide les zones
	addZone(getNOM_ST_TITRE_ACTION(),"");
	//on vide les zones
	addZone(getNOM_EF_DATE(),"");
	addZone(getNOM_EF_DATEFIN(),"");
	addZone(getNOM_EF_HDEB(),"");
	addZone(getNOM_EF_HDEBMN(),"");
	addZone(getNOM_EF_HFIN(),"");
	addZone(getNOM_EF_HFINMN(),"");
	addZone(getNOM_EF_SERVICE(),"");
	addZone(getNOM_ST_TYPE(),"");
	addZone(getNOM_ST_AGENT(),"");
	addZone(getNOM_ST_DATE(),"");
	addZone(getNOM_ST_DATEDEB(),"");
	addZone(getNOM_ST_DATEFIN(),"");
	addZone(getNOM_ST_HEUREDEB(),"");
	addZone(getNOM_ST_HEUREFIN(),"");
	addZone(getNOM_ST_TYPE(),"");	
	setLB_AGENT(LBVide);
	isAction = false;
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATE
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DATE() {
	return "NOM_EF_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATE
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DATE() {
	return getZone(getNOM_EF_DATE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_SERVICE
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_SERVICE() {
	return "NOM_EF_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_SERVICE
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_SERVICE() {
	return getZone(getNOM_EF_SERVICE());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_AFFECTATION
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
private String [] getLB_AFFECTATION() {
	if (LB_AFFECTATION == null)
		LB_AFFECTATION = initialiseLazyLB();
	return LB_AFFECTATION;
}
/**
 * Setter de la liste:
 * LB_AFFECTATION
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
private void setLB_AFFECTATION(java.lang.String[] newLB_AFFECTATION) {
	LB_AFFECTATION = newLB_AFFECTATION;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_AFFECTATION
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_AFFECTATION() {
	return "NOM_LB_AFFECTATION";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_AFFECTATION_SELECT
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_AFFECTATION_SELECT() {
	return "NOM_LB_AFFECTATION_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_AFFECTATION
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String [] getVAL_LB_AFFECTATION() {
	return getLB_AFFECTATION();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_AFFECTATION
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_LB_AFFECTATION_SELECT() {
	return getZone(getNOM_LB_AFFECTATION_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_AGENT
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
private void setLB_AGENT(java.lang.String[] newLB_AGENT) {
	LB_AGENT = newLB_AGENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_AGENT
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_AGENT() {
	return "NOM_LB_AGENT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_AGENT_SELECT
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_AGENT_SELECT() {
	return getZone(getNOM_LB_AGENT_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (13/06/05 13:46:18)
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
 * Date de création : (13/06/05 13:46:18)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}

public void trier(ArrayList<Affecter_Agent> a) throws Exception{
//	String[] colonnes = tri;//{tri};//"datedebut","hdeb"};
	//ordre croissant
//	boolean[] ordres = tOrdre;//false,false};
	String heuredeb="";
	String heurefin="";	
	
//	Si au moins une affectation
	if (a.size() !=0 ) {
		ArrayList<Affecter_Agent> aTrier = a;
//		if (!tri.equals("nom")){
//			aTrier = Services.trier(a,colonnes,ordres);
//		}
		setListeAffectation(aTrier);
		int tailles [] = {5,35,10,5,10,5};
		String[] padding = {"C","G","C","C","C","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (ListIterator<Affecter_Agent> list = aTrier.listIterator(); list.hasNext(); ) {
			Affecter_Agent aAffecter_Agent = (Affecter_Agent)list.next();
			String datefin =aAffecter_Agent.getDatefin();
			// 1 : on vérifie que l'heure a été renseigné si non l'équipement à été affecter pour la journée
			// 2 : on vérifie que à l'affichage l'heure sera bien formatté ex: 8:00
			if (aAffecter_Agent.getHdeb().equals("0")){
				heuredeb = "";
				heurefin = "";
			}else{
				if (aAffecter_Agent.getHdebmn().equals("0")){
					heuredeb = aAffecter_Agent.getHdeb()+":00";
				}else{
					heuredeb = aAffecter_Agent.getHdeb()+":"+aAffecter_Agent.getHdebmn();
				}
				if (aAffecter_Agent.getHfinmn().equals("0")){
					heurefin = aAffecter_Agent.getHfin()+":00";
				}else{
					heurefin = aAffecter_Agent.getHfin()+":"+aAffecter_Agent.getHfinmn();
				}
			}
			
			if ("01/01/0001".equals(aAffecter_Agent.getDatefin())){
				datefin = "";
			}
			
			// recherche de l'agent pour l'afficher
			if(aAffecter_Agent.getCodeservice().equals("4000")){
				AgentCDE unAgent = AgentCDE.chercherAgentCDE(getTransaction(),aAffecter_Agent.getMatricule());
				String ligne [] = { aAffecter_Agent.getCodeservice(),unAgent.getNom().trim()+" "+ unAgent.getPrenom().trim(),aAffecter_Agent.getDatedebut(),heuredeb,datefin,heurefin};
				aFormat.ajouteLigne(ligne);
			}else if (aAffecter_Agent.getCodeservice().equals("5000")){
				AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),aAffecter_Agent.getMatricule());
				String ligne [] = { aAffecter_Agent.getCodeservice(),unAgent.getNom().trim()+" "+ unAgent.getPrenom().trim(),aAffecter_Agent.getDatedebut(),heuredeb,datefin,heurefin};
				aFormat.ajouteLigne(ligne);
			}else{
				Agents unAgent = Agents.chercherAgents(getTransaction(),aAffecter_Agent.getMatricule());
				String ligne [] = { aAffecter_Agent.getCodeservice(),unAgent.getNom().trim()+" "+ unAgent.getPrenom().trim(),aAffecter_Agent.getDatedebut(),heuredeb,datefin,heurefin};
				aFormat.ajouteLigne(ligne);
			}
		}
		setLB_AFFECTATION(aFormat.getListeFormatee());
	} else {
		setLB_AFFECTATION(null);
	}
	setIsVide(a.size());
	
	// trier selon le nom
	/*if (tri.equals("nom")){
		for (int i= 1;i<a.size();i++){
			getLB_AFFECTATION()
		}
	}*/
	return ;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_AGENT
 * Date de création : (13/06/05 13:46:18)
 * @author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK_AGENT() {
	return "NOM_PB_OK_AGENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (13/06/05 13:46:18)
 * @author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK_AGENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	return true;
}
	
	
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	/**
	 * @param equipementInfosCourant equipementInfosCourant à définir.
	 */
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	/**
	 * @return Renvoie listeAffectation.
	 */
	public ArrayList<Affecter_Agent> getListeAffectation() {
		return listeAffectation;
	}
	/**
	 * @param listeAffectation listeAffectation à définir.
	 */
	public void setListeAffectation(ArrayList<Affecter_Agent> listeAffectation) {
		this.listeAffectation = listeAffectation;
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
	 */
	/**
	 * @return String
	 */
	public String getDefaultFocus() {
		return getNOM_EF_SERVICE();
	}
	/**
	 * @return Renvoie affecterServiceCourant.
	 */
	public Affecter_Agent getAffecterAgentCourant() {
		return affecterAgentCourant;
	}
	/**
	 * @param affecterAgentCourant affecterAgentCourant à définir.
	 */
	public void setAffecterAgentCourant(
			Affecter_Agent affecterAgentCourant) {
		this.affecterAgentCourant = affecterAgentCourant;
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
	 * @return Renvoie listeAgent.
	 */
	public ArrayList<AgentInterface> getListeAgent() {
		return listeAgent;
	}
	/**
	 * @param listeAgent listeAgent à définir.
	 */
	public void setListeAgent(ArrayList<AgentInterface> listeAgent) {
		this.listeAgent = listeAgent;
	}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATE
 * Date de création : (16/06/05 11:17:29)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_DATE() {
	return "NOM_ST_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATE
 * Date de création : (16/06/05 11:17:29)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_DATE() {
	return getZone(getNOM_ST_DATE());
}
public Equipement getEquipementCourant() {
	return equipementCourant;
}
public void setEquipementCourant(Equipement equipementCourant) {
	this.equipementCourant = equipementCourant;
}
public Agents getAgentCourant() {
	return agentCourant;
}
public void setAgentCourant(Agents agentCourant) {
	this.agentCourant = agentCourant;
}	
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATEFIN
 * Date de création : (21/06/05 08:23:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_DATEFIN() {
	return "NOM_EF_DATEFIN";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATEFIN
 * Date de création : (21/06/05 08:23:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_DATEFIN() {
	return getZone(getNOM_EF_DATEFIN());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (22/06/05 14:36:04)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (22/06/05 14:36:04)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_HDEB
 * Date de création : (22/06/05 14:42:14)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_HDEB() {
	return "NOM_EF_HDEB";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_HDEB
 * Date de création : (22/06/05 14:42:14)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_HDEB() {
	return getZone(getNOM_EF_HDEB());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_HFIN
 * Date de création : (22/06/05 14:42:14)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_HFIN() {
	return "NOM_EF_HFIN";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_HFIN
 * Date de création : (22/06/05 14:42:14)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_HFIN() {
	return getZone(getNOM_EF_HFIN());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_HDEBFIN
 * Date de création : (22/06/05 16:22:48)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_HFINMN() {
	return "NOM_EF_HFINMN";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_HDEBFIN
 * Date de création : (22/06/05 16:22:48)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_HFINMN() {
	return getZone(getNOM_EF_HFINMN());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_HDEBMN
 * Date de création : (22/06/05 16:22:48)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_HDEBMN() {
	return "NOM_EF_HDEBMN";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_HDEBMN
 * Date de création : (22/06/05 16:22:48)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_HDEBMN() {
	return getZone(getNOM_EF_HDEBMN());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_AGENT
 * Date de création : (23/06/05 15:44:15)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_AGENT() {
	return "NOM_ST_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_AGENT
 * Date de création : (23/06/05 15:44:16)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_AGENT() {
	return getZone(getNOM_ST_AGENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATEDEB
 * Date de création : (23/06/05 15:44:16)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_DATEDEB() {
	return "NOM_ST_DATEDEB";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATEDEB
 * Date de création : (23/06/05 15:44:16)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_DATEDEB() {
	return getZone(getNOM_ST_DATEDEB());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATEFIN
 * Date de création : (23/06/05 15:44:16)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_DATEFIN() {
	return "NOM_ST_DATEFIN";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATEFIN
 * Date de création : (23/06/05 15:44:16)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_DATEFIN() {
	return getZone(getNOM_ST_DATEFIN());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_HEUREDEB
 * Date de création : (23/06/05 15:44:16)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_HEUREDEB() {
	return "NOM_ST_HEUREDEB";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_HEUREDEB
 * Date de création : (23/06/05 15:44:16)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_HEUREDEB() {
	return getZone(getNOM_ST_HEUREDEB());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_HEUREFIN
 * Date de création : (23/06/05 15:44:16)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_HEUREFIN() {
	return "NOM_ST_HEUREFIN";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_HEUREFIN
 * Date de création : (23/06/05 15:44:16)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_HEUREFIN() {
	return getZone(getNOM_ST_HEUREFIN());
}
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
	public boolean isManqueParam() {
		return manqueParam;
	}
	public void setManqueParam(boolean manqueParam) {
		this.manqueParam = manqueParam;
	}
	public Affecter_Agent getAffecterAgentPrecedent() {
		return affecterAgentPrecedent;
	}
	public void setAffecterAgentPrecedent(Affecter_Agent affecterAgentPrecedent) {
		this.affecterAgentPrecedent = affecterAgentPrecedent;
	}
	public AgentCCAS getAgentCCASCourant() {
		return agentCCASCourant;
	}
	public void setAgentCCASCourant(AgentCCAS agentCCASCourant) {
		this.agentCCASCourant = agentCCASCourant;
	}
	public AgentCDE getAgentCDECourant() {
		return agentCDECourant;
	}
	public void setAgentCDECourant(AgentCDE agentCDECourant) {
		this.agentCDECourant = agentCDECourant;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_EQUIP
 * Date de création : (02/04/07 14:57:15)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_PB_EQUIP() {
	return "NOM_PB_EQUIP";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/04/07 14:57:15)
 * @author : Générateur de process
 */
/**
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	String recherche = getZone(getNOM_EF_EQUIP()).toUpperCase();
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfosInvOuImmat(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("L'équipement recherché n'a pas été trouvé.");
		return false;
	}
	if(null==unEquipementInfos){
		unEquipementInfos = new EquipementInfos();
	}
	setEquipementInfosCourant(unEquipementInfos);
		
	// on renseigne la liste 
	VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_EQUIP
 * Date de création : (02/04/07 14:57:15)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_EQUIP() {
	return "NOM_EF_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_EQUIP
 * Date de création : (02/04/07 14:57:15)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_EQUIP() {
	return getZone(getNOM_EF_EQUIP());
}
/**
 * Retourne le nom du groupe de radio boutons coché pour la JSP :
 * RG_TRI
 * Date de création : (02/04/07 14:57:15)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_RG_TRI() {
	return "NOM_RG_TRI";
}
/**
 * Retourne la valeur du radio bouton (RB_) coché dans la JSP :
 * RG_TRI
 * Date de création : (02/04/07 14:57:15)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_RG_TRI() {
	return getZone(getNOM_RG_TRI());
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_DEBUT
 * Date de création : (02/04/07 14:57:15)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_RB_TRI_DEBUT() {
	return "NOM_RB_TRI_DEBUT";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_EMPLOYE
 * Date de création : (02/04/07 14:57:15)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_RB_TRI_EMPLOYE() {
	return "NOM_RB_TRI_EMPLOYE";
}
/**
 * Retourne le nom du radio bouton pour la JSP :
 * RB_TRI_FIN
 * Date de création : (02/04/07 14:57:15)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_RB_TRI_FIN() {
	return "NOM_RB_TRI_FIN";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_TRI
 * Date de création : (02/04/07 15:15:35)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_PB_TRI() {
	return "NOM_PB_TRI";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (02/04/07 15:15:35)
 * @author : Générateur de process
 */
/**
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_TRI(javax.servlet.http.HttpServletRequest request) throws Exception {
	if (getNOM_RB_TRI_DEBUT().equals(getZone(getNOM_RG_TRI()))){
		tri[1] = "datedebut";
		tri[2] = "hdeb";
		//tri = {"datedebut","hdeb"};
		tOrdre[1] = false;
		tOrdre[2] = false;
	}
	if (getNOM_RB_TRI_EMPLOYE().equals(getZone(getNOM_RG_TRI()))){
		tri[1] = "nom";
		tri[2] = "datedebut";
		tOrdre[1] = true;
		tOrdre[2] = false;
	}
	if (getNOM_RB_TRI_FIN().equals(getZone(getNOM_RG_TRI()))){
		tri[1] = "datefin";
		tri[2] = "hfin";
		tOrdre[1] = false;
		tOrdre[2] = false;
	}
	
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (13/06/05 13:31:38)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RETOUR
		if (testerParametre(request, getNOM_PB_RETOUR())) {
			return performPB_RETOUR(request);
		}

		//Si clic sur le bouton PB_TRI
		if (testerParametre(request, getNOM_PB_TRI())) {
			return performPB_TRI(request);
		}

		//Si clic sur le bouton PB_EQUIP
		if (testerParametre(request, getNOM_PB_EQUIP())) {
			return performPB_EQUIP(request);
		}

		//Si clic sur le bouton PB_OK
		if (testerParametre(request, getNOM_PB_OK())) {
			return performPB_OK(request);
		}

		//Si clic sur le bouton PB_OK_AGENT
		if (testerParametre(request, getNOM_PB_OK_AGENT())) {
			return performPB_OK_AGENT(request);
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

		//Si clic sur le bouton PB_RECHERCHE
		if (testerParametre(request, getNOM_PB_RECHERCHE())) {
			return performPB_RECHERCHE(request);
		}

		//Si clic sur le bouton PB_SELECTIONNER
		if (testerParametre(request, getNOM_PB_SELECTIONNER())) {
			return performPB_SELECTIONNER(request);
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
 * Date de création : (10/08/07 08:45:15)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OeAffectation_Agent.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RETOUR
 * Date de création : (10/08/07 08:45:15)
 * @author : Générateur de process
 */
/**
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
 * Date de création : (10/08/07 08:45:15)
 * @author : Générateur de process
 */
/**
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RETOUR(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_PROCESS_APPELANT);
	return true;
}
}
