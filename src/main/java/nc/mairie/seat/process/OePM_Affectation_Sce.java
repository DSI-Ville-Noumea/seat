package nc.mairie.seat.process;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.Locale;

import nc.mairie.seat.metier.AActifs;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.AgentInterface;
import nc.mairie.seat.metier.AgentServiceInfos;
import nc.mairie.seat.metier.AgentsMunicipaux;
import nc.mairie.seat.metier.PM_AffectAgentsInfos;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PM_Affecter_Agent;
import nc.mairie.seat.metier.PM_Affecter_Sce;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeAffectation_Service
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
*/
public class OePM_Affectation_Sce extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4815623589766254633L;
	public static final int STATUT_RECHERCHESERVICE = 1;
	private java.lang.String[] LB_AFFECTATION;
	private java.lang.String[] LB_AGENT;
	public static final int STATUT_RECHERCHEREQUIPEMENT = 2;
	private String ACTION_SUPPRESSION = "Suppression d'une affectation<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification d'une affectation.";
	private String ACTION_CREATION = "Création d'une nouvelle affectation.";
	private PMatInfos pMatInfosCourant;
	private Service serviceCourant;
	private AgentServiceInfos agentServiceInfosCourant;
	private PM_Affecter_Sce pmAffecterServiceCourant;
	private PM_Affecter_Agent pmAffecterAgentCourant;
	private PM_Affectation_Sce_Infos pMaffectationServiceInfosCourant;
	private ArrayList<PM_Affectation_Sce_Infos> listeAffectation;
	private ArrayList<AgentInterface> listeAgent;
	private String focus = null;
	private AgentCDE agentCDECourant;
	private AgentCCAS agentCCASCourant;
	public int isVide = 0;
	public boolean isAction = false;
	public boolean first = true;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request, "PMATINFOS");
	setpMatInfosCourant(unPMatInfos);
	
	if (etatStatut() == STATUT_RECHERCHESERVICE) {
		Service unService = (Service)VariableGlobale.recuperer(request,"SERVICE");
		setServiceCourant(unService);
	}	
	
	if (null==getServiceCourant()){
		Service unService = (Service)VariableGlobale.recuperer(request,"SERVICE");
		setServiceCourant(unService);
	}

		
//	on met la date du jour par défaut
	if ("".equals(getZone(getNOM_EF_DATE()))){
		DateFormat datedujour = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE); 
		addZone(getNOM_EF_DATE(),datedujour.format(new Date()));
	}
	
	if (null!=(getPMatInfosCourant())){
		initialiseListeAffectation(request);
			addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
			addZone(getNOM_ST_NOINVENT(),getPMatInfosCourant().getPminv());
			addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
			addZone(getNOM_ST_NOMEQUIP(),getPMatInfosCourant().getDesignationmarque()+" "+getPMatInfosCourant().getDesignationmodele());	
	}else{
		setLB_AFFECTATION(LBVide);
		addZone(getNOM_ST_TYPE(),"");
		addZone(getNOM_ST_NOINVENT(),"");
		addZone(getNOM_ST_NOIMMAT(),"");
		addZone(getNOM_ST_NOMEQUIP(),"");
	}
	if (null!=(serviceCourant) && serviceCourant.getServi() != null){
		addZone(getNOM_ST_SERVICE(),getServiceCourant().getLiserv());
		//on remplit la liste des agents du service
		//java.util.ArrayList a = AgentServiceInfos.chercherListAgentServiceInfosSce(getTransaction(),serviceCourant.getServi());
		if (getServiceCourant().getServi().equals("4000")){
			ArrayList<AgentCDE> a =AgentCDE.listerAgentCDE(getTransaction());
			setListeAgent(new ArrayList<AgentInterface>());
			getListeAgent().addAll(a);
			if(a.size()>0){
				//	les élèments de la liste 
				int [] tailles = {40};
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
		}else if (getServiceCourant().getServi().equals("5000")){
			ArrayList<AgentCCAS> a =AgentCCAS.listerAgentCCAS(getTransaction());
			setListeAgent(new ArrayList<AgentInterface>());
			getListeAgent().addAll(a);
			if(a.size()>0){
				//	les élèments de la liste 
				int [] tailles = {40};
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
		
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
			//	on sélectionne le bon agent
			int position = -1;
			addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(position));
			if(getServiceCourant()!=null){
				if(getServiceCourant().getServi().equals("4000")){
					for (int i = 0; i < getListeAgent().size(); i++) {
						AgentCDE unAgent = (AgentCDE)getListeAgent().get(i);
						if (getPmAffectationServiceInfosCourant().getNomatr()!=null){
							if (unAgent.getNomatr().trim() .equals(getPmAffectationServiceInfosCourant().getNomatr().trim())) {
								addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
								break;
							}
						}
					}
				}else if (getServiceCourant().getServi().equals("5000")){
					for (int i = 0; i < getListeAgent().size(); i++) {
						AgentCCAS unAgent = (AgentCCAS)getListeAgent().get(i);
						if (getPmAffectationServiceInfosCourant().getNomatr()!=null){
							if (unAgent.getNomatr().trim() .equals(getPmAffectationServiceInfosCourant().getNomatr().trim())) {
								addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
								break;
							}
						}
					}
				}else{
					for (int i = 0; i < getListeAgent().size(); i++) {
						AActifs unAgent = (AActifs)getListeAgent().get(i);
						if (getPmAffectationServiceInfosCourant().getNomatr()!=null){
							if (unAgent.getNomatr().trim() .equals(getPmAffectationServiceInfosCourant().getNomatr().trim())) {
								addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
								break;
							}
						}
					}
				}
			}
			
			// on checked si pas de responsable
			if (getVAL_LB_AGENT_SELECT().equals("-1")){
				addZone(getNOM_CK_AGENT(),getCHECKED_ON());
			}else{
				addZone(getNOM_CK_AGENT(),getCHECKED_OFF());
			}
		}
	} else {
		addZone(getNOM_ST_SERVICE(),"");
	}
	setFocus(getNOM_EF_EQUIP());
	if(!first&&getListeAffectation()!=null&&getListeAffectation().size()>0){
		int indice  = (Services.estNumerique(getVAL_LB_AFFECTATION_SELECT()) ? Integer.parseInt(getVAL_LB_AFFECTATION_SELECT()): -1);
		if(indice==-1){
			addZone(getNOM_LB_AFFECTATION_SELECT(),"0");
		}else{
			if(indice>getListeAffectation().size()-1){
				addZone(getNOM_LB_AFFECTATION_SELECT(),"0");
			}else{
				addZone(getNOM_LB_AFFECTATION_SELECT(),String.valueOf(indice));
			}
		}			
		performPB_RESPONSABLE(request);
	}else{
		addZone(getNOM_ST_AGENT(),"");
	}
	first = false;
}

/**
 * Initialisation de la liste des affectations
 * author : Coralie NICOLAS
 */
private void initialiseListeAffectation(javax.servlet.http.HttpServletRequest request) throws Exception{
//	Recherche des affectations des petits matériels aux services
	ArrayList<PM_Affectation_Sce_Infos> a = PM_Affectation_Sce_Infos.chercherListPM_Affectation_Sce_InfosPm(getTransaction(),getPMatInfosCourant().getPminv());
	if(getTransaction().isErreur()){
		return ;
	}
	//java.util.ArrayList a = AActifs.chercherListAffectationServiceInfosEquip(getTransaction(),equipementInfosCourant.getNumeroinventaire());
	setListeAffectation(a);
	if (a.size() !=0 ) {
		int tailles [] = {5,55,10,10};
		String[] padding = {"G","G","C","C"};
		FormateListe aFormat = new FormateListe(tailles,padding,false);
		for (ListIterator<PM_Affectation_Sce_Infos> list = a.listIterator(); list.hasNext(); ) {
			PM_Affectation_Sce_Infos aPM_Affectation_Sce_Infos = (PM_Affectation_Sce_Infos)list.next();
			String datefin = "";
			if (!aPM_Affectation_Sce_Infos.getDfin().equals("01/01/0001")){
				datefin = aPM_Affectation_Sce_Infos.getDfin();
			}
			// 
			Service unService = Service.chercherService(getTransaction(),aPM_Affectation_Sce_Infos.getSiserv());
			if(getTransaction().isErreur()){
				getTransaction().traiterErreur();
				getTransaction().declarerErreur("Erreur, le service "+aPM_Affectation_Sce_Infos.getSiserv()+" n'existe pas");
				return ;
			}
			String ligne [] = { aPM_Affectation_Sce_Infos.getSiserv(),unService.getLiserv(),aPM_Affectation_Sce_Infos.getDdebut(),datefin};
			aFormat.ajouteLigne(ligne);
		}
	
		setLB_AFFECTATION(aFormat.getListeFormatee());
	} else {
		setLB_AFFECTATION(null);
	}
	setIsVide(a.size());
	return ;		
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AJOUTER
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AJOUTER(javax.servlet.http.HttpServletRequest request) throws Exception {
	isAction = true;
	addZone(getNOM_ST_TITRE_ACTION(),ACTION_CREATION);
	addZone(getNOM_LB_AGENT_SELECT(),"-1");
	//addZone(getNOM_EF_DATEFIN(),"");
	VariableGlobale.enlever(request,"SERVICE");
	setServiceCourant(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_ANNULER(javax.servlet.http.HttpServletRequest request) throws Exception {
	isAction = false;
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DATE(),"");
	addZone(getNOM_EF_DATE(),"");
	addZone(getNOM_ST_SERVICE(),"");
	setServiceCourant(null);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_MODIFIER
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_MODIFIER(javax.servlet.http.HttpServletRequest request) throws Exception {
	isAction = true;
//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_AFFECTATION_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_AFFECTATION_SELECT())) : -1);
	if (numligne == -1 || getListeAffectation().size() == 0 || numligne > getListeAffectation().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Affectations"));
		return false;
	}
	
//	 si la ligne sélectionner n'est pas la dernière affectation alors on ne peut pas modifier
	if (numligne==0){
		addZone(getNOM_ST_TITRE_ACTION(),ACTION_MODIFICATION);
	}else{
		getTransaction().declarerErreur("Seule la dernière affectation peut être modifiée ou supprimée.");
		return false;
	}
	//Récup 
	PM_Affectation_Sce_Infos aPM_Affectation_Sce_Infos = (PM_Affectation_Sce_Infos)getListeAffectation().get(numligne);
	setPmAffectationServiceInfosCourant(aPM_Affectation_Sce_Infos);
	// on cherche le bon service 
	Service monService = Service.chercherService(getTransaction(),aPM_Affectation_Sce_Infos.getSiserv());
	if(getTransaction().isErreur()){
		return false;
	}
	setServiceCourant(monService);
	
	//Alim zones
	addZone(getNOM_ST_SERVICE(),monService.getLiserv());
	addZone(getNOM_EF_DATE(), aPM_Affectation_Sce_Infos.getDdebut());

	setStatut(STATUT_MEME_PROCESS);
	//VariableGlobale.enlever(request,"SERVICE");
	setFocus(getNOM_EF_DATE());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
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
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_RECHERCHESERVICE,false);
	VariableActivite.ajouter(this,"MODE","AFFECTATION_SCE");
	setStatut(STATUT_RECHERCHEREQUIPEMENT,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SELECTIONNER
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
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
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SELECTIONNER(javax.servlet.http.HttpServletRequest request) throws Exception {
	setStatut(STATUT_RECHERCHEREQUIPEMENT,false);
	setStatut(STATUT_RECHERCHESERVICE,true);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_SUPPRIMER
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
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
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {
	isAction = true;
	String dateF;
	//	Test si ligne sélectionnée
	int numligne = (Services.estNumerique(getZone(getNOM_LB_AFFECTATION_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_AFFECTATION_SELECT())) : -1);
	if (numligne == -1 || getListeAffectation().size() == 0 || numligne > getListeAffectation().size() -1 ) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Affectations"));
		return false;
	}
//	 si la ligne sélectionner n'est pas la dernière affectation alors on ne peut pas modifier
	if (numligne==0){
		addZone(getNOM_ST_TITRE_ACTION(),ACTION_SUPPRESSION);
	}else{
		getTransaction().declarerErreur("Seule la dernière affectation peut être modifiée ou supprimée.");
		return false;
	}
//	Récup 
	PM_Affectation_Sce_Infos affectationServiceInfos = (PM_Affectation_Sce_Infos)getListeAffectation().get(0);
	setPmAffectationServiceInfosCourant(affectationServiceInfos);
	// RG : si des affectations agents ont eu lieu on ne peut pas supprimer l'affectation service
	ArrayList<PM_AffectAgentsInfos> listAffectationAgent = PM_AffectAgentsInfos.listerPM_AffectAgentsInfosScePMatDate(getTransaction(),affectationServiceInfos.getSiserv(),affectationServiceInfos.getPminv(),affectationServiceInfos.getDdebut());
	if(getTransaction().isErreur()){
		return false;
	}
	if(listAffectationAgent.size()>0){
		addZone(getNOM_ST_TITRE_ACTION(),"");
		getTransaction().declarerErreur("Des agents ont été affectés à ce petit matériel, vous devez d'abord supprimer les affectations des agents.");
		return false;
	}
	
	
	String datefin=""; 
//	Alim zones
	addZone(getNOM_ST_DATE(), affectationServiceInfos.getDdebut());
	if (!affectationServiceInfos.getDfin().equals("01/01/0001")){
		datefin = affectationServiceInfos.getDfin();
	}
	addZone(getNOM_ST_DATEFIN(),datefin);
	
	//on cherche l'enreg d'Affecter_Service correspondant
	String dateC = Services.formateDateInternationale(getVAL_ST_DATE());
	if(!affectationServiceInfos.getDfin().equals("")){
		dateF = Services.formateDateInternationale(affectationServiceInfos.getDfin());
	}else{
		dateF = "";
	}
	PM_Affecter_Sce monAS = PM_Affecter_Sce.chercherPM_Affecter_Sce(getTransaction(),affectationServiceInfos.getPminv(),affectationServiceInfos.getSiserv(),dateC,dateF);
	if(getTransaction().isErreur()){
		return false;
	}
	setPmAffecterServiceCourant(monAS);
	Service unService = Service.chercherService(getTransaction(),monAS.getSiserv());
	if(getTransaction().isErreur()){
		return false;
	}
	addZone(getNOM_ST_SERVICE(), unService.getLiserv());
//	 on cherche le bon service 
	setServiceCourant(unService);
	// on cherche l'agent responsable
	if (!affectationServiceInfos.getNomatr().equals("0")){
		if(affectationServiceInfos.getSiserv().equals("4000")){
			AgentCDE unAgent = AgentCDE.chercherAgentCDE(getTransaction(),getPmAffectationServiceInfosCourant().getNomatr());
			addZone(getNOM_ST_AGENT(),unAgent.getNom()+" "+unAgent.getPrenom());
		}else if(affectationServiceInfos.getSiserv().equals("5000")){
			AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),getPmAffectationServiceInfosCourant().getNomatr());
			addZone(getNOM_ST_AGENT(),unAgent.getNom()+" "+unAgent.getPrenom());
		}else{
			AgentServiceInfos unAgent = AgentServiceInfos.chercherAgentServiceInfos(getTransaction(),getPmAffectationServiceInfosCourant().getNomatr());
			addZone(getNOM_ST_AGENT(),unAgent.getNom()+" "+unAgent.getPrenom());
		}
	}else{
		addZone(getNOM_ST_AGENT(),"Aucun");
	}
	
	setStatut(STATUT_MEME_PROCESS);
	//setFocus(getNOM_EF_DATE());
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_VALIDER
 * Date de création : (13/06/05 13:31:38)
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
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	String dateF;
//	 on recherche le pm correspondant
	PMateriel unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
	if(getTransaction().isErreur()){
		return false;
	}
	
	// on renseigne la table des affectations des services
	String newDateDeb = "";
	if (Services.estUneDate(getZone(getNOM_EF_DATE()))){
		newDateDeb = Services.formateDate(getZone(getNOM_EF_DATE()));
	}else{
		getTransaction().declarerErreur("La date n'est pas correcte.");
		setFocus(getNOM_EF_DATE());
		return false;
	}
	String newAgent = "0";
	if(!getVAL_ST_TITRE_ACTION().equals(ACTION_SUPPRESSION)){
		//on regarde l'agent sélectionné
		if(getVAL_CK_AGENT().equals(getCHECKED_ON())){
			newAgent = "0";
		}else{
			
			// #16010 Mauvais message d'erreur si on ne sélectionne pas un service dans l'affectation d'un PM à un service
			if (getServiceCourant() == null || getServiceCourant().getServi() ==  null) {
				getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Services"));
				return false;
			}
			
	//		on enregistre l'agent sélectionné
			int numligne = (Services.estNumerique(getZone(getNOM_LB_AGENT_SELECT())) ? Integer.parseInt(getZone(getNOM_LB_AGENT_SELECT())) : -1);
			if (numligne == -1 || getListeAgent().size() == 0 || numligne > getListeAgent().size() -1 ) {
				getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","Agents"));
				return false;
			}
			if(getServiceCourant().getServi().equals("4000")){
				AgentCDE monAActifs = (AgentCDE)getListeAgent().get(numligne);
				newAgent = monAActifs.getNomatr();
			}else if(getServiceCourant().getServi().equals("5000")){
				AgentCCAS monAActifs = (AgentCCAS)getListeAgent().get(numligne);
				newAgent = monAActifs.getNomatr();
			}else{
				AActifs monAActifs = (AActifs)getListeAgent().get(numligne);
				newAgent = monAActifs.getNomatr();
			}
		}
	}
	
	//String dateN = Services.formateDateInternationale(newDateDeb);
	
	if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_SUPPRESSION)) {
		// on doit mettre à blanc la date de fin de l'affectation précédente
		PM_Affectation_Sce_Infos monPM_Affectation_Sce_Infos = new PM_Affectation_Sce_Infos();
		PM_Affecter_Sce monPmASPrec = new PM_Affecter_Sce();
		String dateC = "";
		if(listeAffectation.size()>1){
			monPM_Affectation_Sce_Infos = (PM_Affectation_Sce_Infos)getListeAffectation().get(1);
			dateC = Services.formateDateInternationale(monPM_Affectation_Sce_Infos.getDdebut());
			monPmASPrec = PM_Affecter_Sce.chercherPM_Affecter_Sce(getTransaction(),monPM_Affectation_Sce_Infos.getPminv(),monPM_Affectation_Sce_Infos.getSiserv(),dateC,Services.formateDateInternationale(monPM_Affectation_Sce_Infos.getDfin()));
			monPmASPrec.setDfin("");
		}
		//suppression de la dernière affectation
		try{
			getPmAffecterServiceCourant().affecter_serviceSupp(getTransaction(),unPMateriel,monPmASPrec);
			if (getTransaction().isErreur()){
				return false;
			}
		}catch(Exception e){
			getTransaction().declarerErreur(" La suppression n'est pas possible.");
		}
		if (getTransaction().isErreur())
			return false;
		
	//modification d'une affectation
	}else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_MODIFICATION)) {
		//on modifie l'affectation
		//getAffecterServiceCourant().setNomatr(newAgent);
		PM_Affectation_Sce_Infos monAsIPrecedent = new PM_Affectation_Sce_Infos();
		PM_Affecter_Sce monAS = new PM_Affecter_Sce();
		PM_Affecter_Sce monASPrec = new PM_Affecter_Sce();
		String dateC = "";
		if (listeAffectation.size()>0){
			monAsIPrecedent = (PM_Affectation_Sce_Infos)getListeAffectation().get(0);
			dateC = Services.formateDateInternationale(monAsIPrecedent.getDdebut());
			if(!monAsIPrecedent.getDfin().equals("")){
				dateF = Services.formateDateInternationale(monAsIPrecedent.getDfin());
			}else{
				dateF = "";
			}
			monAS = PM_Affecter_Sce.chercherPM_Affecter_Sce(getTransaction(),monAsIPrecedent.getPminv(),monAsIPrecedent.getSiserv(),dateC,dateF);
			if (getTransaction().isErreur()){
				return false;
			}
			monAS.setDdebut(newDateDeb);
			monAS.setSiserv(getServiceCourant().getServi());
			monAS.setNomatr(newAgent);
			//monAS.setDfin(newDateFin);
			if(listeAffectation.size()>1){
				monAsIPrecedent = (PM_Affectation_Sce_Infos)getListeAffectation().get(1);
				dateC = Services.formateDateInternationale(monAsIPrecedent.getDdebut());
				if(!monAsIPrecedent.getDfin().equals("")){
					dateF = Services.formateDateInternationale(monAsIPrecedent.getDfin());
				}else{
					dateF = "";
				}
				monASPrec = PM_Affecter_Sce.chercherPM_Affecter_Sce(getTransaction(),monAsIPrecedent.getPminv(),monAsIPrecedent.getSiserv(),dateC,dateF);
				monASPrec.setDfin(newDateDeb);
			}
		}
		monAS.affecter_serviceModif(getTransaction(),unPMateriel,monAS,monASPrec,newDateDeb);
		
	//changement d'affectation
	}else if (getZone(getNOM_ST_TITRE_ACTION()).equals(ACTION_CREATION)) {
		setPmAffecterServiceCourant(new PM_Affecter_Sce());
		getPmAffecterServiceCourant().setDdebut(newDateDeb);
		getPmAffecterServiceCourant().setNomatr(newAgent);
		//getAffecterServiceCourant().setDfin(newDateFin);
		
		String dateC = newDateDeb;
		//on change la date de fin de l'affectation précédente
		PM_Affectation_Sce_Infos monAsIPrecedent = new PM_Affectation_Sce_Infos();
		PM_Affecter_Sce monAS = new PM_Affecter_Sce();
		if (listeAffectation.size()>0){
			monAsIPrecedent = (PM_Affectation_Sce_Infos)getListeAffectation().get(0);
			dateC = Services.formateDateInternationale(monAsIPrecedent.getDdebut());
			if(!monAsIPrecedent.getDfin().equals("")){
				dateF = Services.formateDateInternationale(monAsIPrecedent.getDfin());
			}else{
				dateF = "";
			}
			monAS = PM_Affecter_Sce.chercherPM_Affecter_Sce(getTransaction(),monAsIPrecedent.getPminv(),monAsIPrecedent.getSiserv(),dateC,dateF);
			if(getTransaction().isErreur()){
				return false;
			}
			monAS.setDfin(newDateDeb);
		}

//		on créee l'affectation
		if (null!=monAsIPrecedent.getSiserv()){
			if (!serviceCourant.getServi().equals(monAsIPrecedent.getSiserv().trim())){
				getPmAffecterServiceCourant().affecter_service(getTransaction(),unPMateriel,getServiceCourant(),monAS,newDateDeb);
			}else{
				if(!newAgent.equals(monAsIPrecedent.getNomatr())){
					getPmAffecterServiceCourant().affecter_service(getTransaction(),unPMateriel,getServiceCourant(),monAS,newDateDeb);
				}else{
					getTransaction().declarerErreur("Ce petit matériel est déjà en cours d'utilisation par ce service et par ce responsable.");
					return false;
				}
			}
		}else{
			getPmAffecterServiceCourant().affecter_service(getTransaction(),unPMateriel,getServiceCourant(),monAS,newDateDeb);
		}
				
	}
	if(getTransaction().isErreur()){
		return false;
	}
//	Tout s'est bien passé
	commitTransaction();
	//on vide toutes les zones
	addZone(getNOM_ST_TITRE_ACTION(),"");
	addZone(getNOM_ST_DATE(),"");
	addZone(getNOM_EF_DATE(),"");
	addZone(getNOM_ST_SERVICE(),"");
	addZone(getNOM_EF_DATEFIN(),"");
	isAction = false;
	
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINVENT
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOINVENT() {
	return "NOM_ST_NOINVENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINVENT
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOINVENT() {
	return getZone(getNOM_ST_NOINVENT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOMEQUIP
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_NOMEQUIP() {
	return "NOM_ST_NOMEQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOMEQUIP
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_NOMEQUIP() {
	return getZone(getNOM_ST_NOMEQUIP());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATE
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DATE() {
	return "NOM_EF_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATE
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DATE() {
	return getZone(getNOM_EF_DATE());
}

/**
 * Getter de la liste avec un lazy initialize :
 * LB_AFFECTATION
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
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
 * author : Générateur de process
 */
private void setLB_AFFECTATION(java.lang.String[] newLB_AFFECTATION) {
	LB_AFFECTATION = newLB_AFFECTATION;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_AFFECTATION
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_AFFECTATION() {
	return "NOM_LB_AFFECTATION";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_AFFECTATION_SELECT
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
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
 * author : Générateur de process
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
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_AFFECTATION_SELECT() {
	return getZone(getNOM_LB_AFFECTATION_SELECT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK
 * Date de création : (13/06/05 13:46:18)
 * author : Générateur de process
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
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on récupère les infos de la ligne sélectionné 
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_AFFECTATION_SELECT()) ? Integer.parseInt(getVAL_LB_AFFECTATION_SELECT()): -1); 
		
	//récup du compteur
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	PM_Affectation_Sce_Infos monASI = getListeAffectation().get(indice);
	//Affecter_Service monAS = Affecter_Service.chercherAffecter_Service(getTransaction(),monASI.getNumeroinventaire(),monASI.getCodeservice(),daten);
	//setAffecterServiceCourant(monAS);
	Service monService = Service.chercherService(getTransaction(),monASI.getSiserv());
	setServiceCourant(monService);
	addZone(getNOM_EF_DATE(), getPmAffecterServiceCourant().getDdebut());
	performPB_OK_SCE(request);
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_AGENT
 * Date de création : (13/06/05 13:46:18)
 * author : Générateur de process
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
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK_AGENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on récupère les infos de l'agent
//	Récup de l'indice sélectionné
	/*int indice = (Services.estNumerique(getVAL_LB_AGENT_SELECT()) ? Integer.parseInt(getVAL_LB_AGENT_SELECT()): -1); 
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un agent");
		return false;
	}
	AgentServiceInfos monAsi = (AgentServiceInfos)getListeAgent().get(indice);
	if (getTransaction().isErreur()){
		return false;
	}
	setAgentServiceInfosCourant(monAsi);*/
	addZone(getNOM_CK_AGENT(),getCHECKED_OFF());
	return true;
}
	
	/**
	 * @return Renvoie equipementInfosCourant.
	 */
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	/**
	 * @param pMatInfosCourant pMatInfosCourant à définir.
	 */
	public void setpMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	/**
	 * @return Renvoie listeAffectation.
	 */
	public ArrayList<PM_Affectation_Sce_Infos> getListeAffectation() {
		return listeAffectation;
	}
	/**
	 * @param listeAffectation listeAffectation à définir.
	 */
	public void setListeAffectation(ArrayList<PM_Affectation_Sce_Infos> listeAffectation) {
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
	 * @return focus
	 */
	public String getDefaultFocus() {
		return getNOM_EF_DATE();
	}
	/**
	 * @return Renvoie affecterServiceCourant.
	 */
	public PM_Affectation_Sce_Infos getPmAffectationServiceInfosCourant() {
		return pMaffectationServiceInfosCourant;
	}
	/**
	 * @param pMaffectationServiceInfosCourant pMaffectationServiceInfosCourant à définir.
	 */
	public void setPmAffectationServiceInfosCourant(
			PM_Affectation_Sce_Infos pMaffectationServiceInfosCourant) {
		this.pMaffectationServiceInfosCourant = pMaffectationServiceInfosCourant;
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
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_SCE
 * Date de création : (15/06/05 15:01:45)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_OK_SCE() {
	return "NOM_PB_OK_SCE";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (15/06/05 15:01:45)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_OK_SCE(javax.servlet.http.HttpServletRequest request) throws Exception {
//	 on récupère les infos de la ligne sélectionné 
//	Récup de l'indice sélectionné
	int indice = (Services.estNumerique(getVAL_LB_AFFECTATION_SELECT()) ? Integer.parseInt(getVAL_LB_AFFECTATION_SELECT()): -1); 
		
	//récup du compteur
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return false;
	}
	
	PM_Affectation_Sce_Infos monASI = getListeAffectation().get(indice);
	Service monService = Service.chercherService(getTransaction(),monASI.getSiserv());
	setServiceCourant(monService);
	//	on remplit la liste des agents du service
	ArrayList<AActifs> a = AActifs.listerAActifsService(getTransaction(),getServiceCourant().getServi());
	setListeAgent(new ArrayList<AgentInterface>());
	getListeAgent().addAll(a);
	//les élèments de la liste 
	int [] tailles = {40};
	String [] padding = {"G"};
	FormateListe aFormat = new FormateListe(tailles,padding, true);
	for (ListIterator<AActifs> list = a.listIterator(); list.hasNext(); ) {
		AActifs aAActifs = (AActifs)list.next();
		String ligne [] = { aAActifs.getNom().trim()+" "+aAActifs.getPrenom().trim()};
		aFormat.ajouteLigne(ligne);
	}
	setLB_AGENT(aFormat.getListeFormatee());
	return true;
}
/**
 * Constructeur du process OeAffectation_Service.
 * Date de création : (15/06/05 15:24:52)
 * author : Générateur de process
 */
public OePM_Affectation_Sce() {
	super();
}
/**
 * @return Renvoie agentServiceInfosCourant.
 */
public AgentServiceInfos getAgentServiceInfosCourant() {
	return agentServiceInfosCourant;
}
/**
 * @param agentServiceInfosCourant agentServiceInfosCourant à définir.
 */
public void setAgentServiceInfosCourant(
		AgentServiceInfos agentServiceInfosCourant) {
	this.agentServiceInfosCourant = agentServiceInfosCourant;
}

	/**
	 * @return Renvoie affecterServiceCourant.
	 */
	public PM_Affecter_Sce getPmAffecterServiceCourant() {
		return pmAffecterServiceCourant;
	}
	/**
	 * @param pmAffecterServiceCourant pmAffecterServiceCourant à définir.
	 */
	public void setPmAffecterServiceCourant(
			PM_Affecter_Sce pmAffecterServiceCourant) {
		this.pmAffecterServiceCourant = pmAffecterServiceCourant;
	}
/**
 * Retourne le nom de la case à cocher sélectionnée pour la JSP :
 * CK_AGENT
 * Date de création : (16/06/05 16:11:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_CK_AGENT() {
	return "NOM_CK_AGENT";
}
/**
 * Retourne la valeur de la case à cocher à afficher par la JSP pour la case à cocher  :
 * CK_AGENT
 * Date de création : (16/06/05 16:11:57)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_CK_AGENT() {
	return getZone(getNOM_CK_AGENT());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AGENT
 * Date de création : (17/06/05 07:55:38)
 * author : Générateur de process
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
 * Date de création : (17/06/05 07:55:38)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AGENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	//on affiche la liste déroulante des agents
	addZone(getNOM_ST_AGENT(),"Affecter un agent.");
	
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_AFFICH_AGENT
 * Date de création : (20/06/05 09:09:14)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_PB_AFFICH_AGENT() {
	return "NOM_PB_AFFICH_AGENT";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (20/06/05 09:09:14)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_AFFICH_AGENT(javax.servlet.http.HttpServletRequest request) throws Exception {
	ArrayList<AgentServiceInfos> a = AgentServiceInfos.chercherListAgentServiceInfosSce(getTransaction(),serviceCourant.getServi());
	
	setListeAgent(new ArrayList<AgentInterface>());
	getListeAgent().addAll(a);
//	Si au moins un modèle
	if (a.size() !=0 ) {
		int [] tailles = {15,15};
		String [] champs = {"nom","prenom"};
		//Liste possibles de padding : G(Gauche) C(Centre) D(Droite)
		String [] padding = {"G","G"};
		
		setLB_AGENT(new FormateListe(tailles,a,champs,padding,false).getListeFormatee());
	}else{
		setLB_AGENT(null);
	}
	return true;
}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (21/06/05 15:28:15)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_SERVICE() {
	return "NOM_ST_SERVICE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_SERVICE
 * Date de création : (21/06/05 15:28:15)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATE
 * Date de création : (22/06/05 07:32:44)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DATE() {
	return "NOM_ST_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATE
 * Date de création : (22/06/05 07:32:44)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DATE() {
	return getZone(getNOM_ST_DATE());
}
	public PM_Affecter_Agent getPmAffecterAgentCourant() {
		return pmAffecterAgentCourant;
	}
	public void setPmAffecterAgentCourant(PM_Affecter_Agent pmAffecterAgentCourant) {
		this.pmAffecterAgentCourant = pmAffecterAgentCourant;
	}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATEFIN
 * Date de création : (27/06/05 08:14:01)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_DATEFIN() {
	return "NOM_EF_DATEFIN";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATEFIN
 * Date de création : (27/06/05 08:14:01)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_DATEFIN() {
	return getZone(getNOM_EF_DATEFIN());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_DATEFIN
 * Date de création : (27/06/05 08:16:29)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_DATEFIN() {
	return "NOM_ST_DATEFIN";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_DATEFIN
 * Date de création : (27/06/05 08:16:29)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_DATEFIN() {
	return getZone(getNOM_ST_DATEFIN());
}
	public int getIsVide() {
		return isVide;
	}
	public void setIsVide(int isVide) {
		this.isVide = isVide;
	}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_AGENT
 * Date de création : (30/06/05 11:20:40)
 * author : Générateur de process
 */
private String [] getLB_AGENT() {
	if (LB_AGENT == null)
		LB_AGENT = initialiseLazyLB();
	return LB_AGENT;
}
/**
 * Setter de la liste:
 * LB_AGENT
 * Date de création : (30/06/05 11:20:41)
 * author : Générateur de process
 */
private void setLB_AGENT(java.lang.String[] newLB_AGENT) {
	LB_AGENT = newLB_AGENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_AGENT
 * Date de création : (30/06/05 11:20:41)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_AGENT() {
	return "NOM_LB_AGENT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_AGENT_SELECT
 * Date de création : (30/06/05 11:20:41)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_LB_AGENT_SELECT() {
	return "NOM_LB_AGENT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_AGENT
 * Date de création : (30/06/05 11:20:41)
 * author : Générateur de process
 * @return String
 */
public java.lang.String [] getVAL_LB_AGENT() {
	return getLB_AGENT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_AGENT
 * Date de création : (30/06/05 11:20:41)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_LB_AGENT_SELECT() {
	return getZone(getNOM_LB_AGENT_SELECT());
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
 * Date de création : (02/04/07 13:36:35)
 * author : Générateur de process
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
 * Date de création : (02/04/07 13:36:35)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	String recherche = getZone(getNOM_EF_EQUIP()).toUpperCase();
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),recherche);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("Le petit matériel recherché n'a pas été trouvé.");
		return false;
	}
	if (null==unPMatInfos){
		unPMatInfos = new PMatInfos();
	}
	setpMatInfosCourant(unPMatInfos);
		
	// on renseigne la liste des BPC
	VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
	return true;
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_EQUIP
 * Date de création : (02/04/07 13:36:35)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_EF_EQUIP() {
	return "NOM_EF_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_EQUIP
 * Date de création : (02/04/07 13:36:35)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_EF_EQUIP() {
	return getZone(getNOM_EF_EQUIP());
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (13/06/05 13:31:38)
 * author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RESPONSABLE
		if (testerParametre(request, getNOM_PB_RESPONSABLE())) {
			return performPB_RESPONSABLE(request);
		}

		//Si clic sur le bouton PB_EQUIP
		if (testerParametre(request, getNOM_PB_EQUIP())) {
			return performPB_EQUIP(request);
		}

		//Si clic sur le bouton PB_AFFICH_AGENT
		if (testerParametre(request, getNOM_PB_AFFICH_AGENT())) {
			return performPB_AFFICH_AGENT(request);
		}

		//Si clic sur le bouton PB_AGENT
		if (testerParametre(request, getNOM_PB_AGENT())) {
			return performPB_AGENT(request);
		}

		//Si clic sur le bouton PB_OK_SCE
		if (testerParametre(request, getNOM_PB_OK_SCE())) {
			return performPB_OK_SCE(request);
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
 * Date de création : (31/07/07 13:48:40)
 * author : Générateur de process
 */
public String getJSP() {
	return "OePM_Affectation_Sce.jsp";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RESPONSABLE
 * Date de création : (31/07/07 13:48:40)
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
 * Date de création : (31/07/07 13:48:40)
 * author : Générateur de process
 * @param request request
 * @return boolean
 * @throws Exception Exception
 */
public boolean performPB_RESPONSABLE(javax.servlet.http.HttpServletRequest request) throws Exception {
	int indice  = (Services.estNumerique(getVAL_LB_AFFECTATION_SELECT()) ? Integer.parseInt(getVAL_LB_AFFECTATION_SELECT()): -1);
	if (indice == -1 || getListeAffectation().size() == 0 || indice > getListeAffectation().size() -1) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR997","AFFECTATIONS"));
		return false;
	}
	PM_Affectation_Sce_Infos unService = (PM_Affectation_Sce_Infos)getListeAffectation().get(indice);
	if("0".equals(unService.getNomatr())){
		addZone(getNOM_ST_AGENT(),"Aucun");
	}else{
		AgentsMunicipaux unAgent = AgentsMunicipaux.chercherAgentsMunicipauxService(getTransaction(),unService.getNomatr(),unService.getSiserv());
		if(getTransaction().isErreur()){
			return false;
		}
		addZone(getNOM_ST_AGENT(),unAgent.getNom().trim()+" "+unAgent.getPrenom().trim());
	}
	
	return true;
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_AGENT
 * Date de création : (31/07/07 13:48:40)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getNOM_ST_AGENT() {
	return "NOM_ST_AGENT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_AGENT
 * Date de création : (31/07/07 13:48:40)
 * author : Générateur de process
 * @return String
 */
public java.lang.String getVAL_ST_AGENT() {
	return getZone(getNOM_ST_AGENT());
}
}
