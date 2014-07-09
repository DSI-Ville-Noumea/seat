package nc.mairie.seat.process;

import java.util.ArrayList;

import nc.mairie.seat.metier.AActifs;
import nc.mairie.seat.metier.AffectationServiceInfos;
import nc.mairie.seat.metier.AgentCCAS;
import nc.mairie.seat.metier.AgentCDE;
import nc.mairie.seat.metier.Agents;
import nc.mairie.seat.metier.AgentsMunicipaux;
import nc.mairie.seat.metier.Declarations;
import nc.mairie.seat.metier.Equipement;
import nc.mairie.seat.metier.EquipementInfos; 
import nc.mairie.seat.metier.FPM;
import nc.mairie.seat.metier.OT;
import nc.mairie.seat.metier.PM_Affectation_Sce_Infos;
import nc.mairie.seat.metier.PMatInfos;
import nc.mairie.seat.metier.PMateriel;
import nc.mairie.seat.metier.Service;
import nc.mairie.technique.*;
/**
 * Process OeDeclarations_ajout
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
*/
public class OeDeclarations_ajout extends nc.mairie.technique.BasicProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8341080906642833040L;
	public static final int STATUT_FPM = 4;
	public static final int STATUT_RECH_PM = 3;
	public static final int STATUT_OT = 2;
	public static final int STATUT_RECHERCHER = 1;
//	private String ACTION_SUPPRESSION = "Suppression<br><FONT color='red'> Veuillez valider votre choix.</FONT>";
	private String ACTION_MODIFICATION = "Modification";
	private String ACTION_CREATION = "Création";
	private EquipementInfos equipementInfosCourant;
	private PMatInfos pMatInfosCourant;
	private Declarations declarationCourante;
	private ArrayList<Service> listService;
	private ArrayList<?> listAgent;
	private Service serviceCourant;
	private Service serviceAffCourant;
	
	private Agents agentCourant;
	private boolean first = true;
	private String focus = null;
	private boolean isSelection = false;
	private boolean isSelectionService = false;
	private boolean isMateriel = false;
/**
 * Initialisation des zones à afficher dans la JSP
 * Alimentation des listes, s'il y en a, avec setListeLB_XXX()
 * ATTENTION : Les Objets dans la liste doivent avoir les Fields PUBLIC
 * Utilisation de la méthode addZone(getNOMxxx, String);
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
public void initialiseZones(javax.servlet.http.HttpServletRequest request) throws Exception{
	
	if((first)||(etatStatut()==STATUT_RECHERCHER)||(etatStatut()==STATUT_RECH_PM)){
		isMateriel = false;
		String recup = (String)VariableActivite.recuperer(this,"TYPE");
		if("equipement".equals(recup)){
			isMateriel = false;
			EquipementInfos unEquipementInfos =(EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
			if(unEquipementInfos!=null){
				if(unEquipementInfos.getNumeroinventaire()!=null){
					setEquipementInfosCourant(unEquipementInfos);
					setSelection(true);
					//recherche du service auquel il est affecté
//					 on recherche le service où l'équipement a été affecté 
					AffectationServiceInfos unAffectationServiceInfos = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),Services.dateDuJour());
					if(getTransaction().isErreur()){
							//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
							getTransaction().traiterErreur();
							addZone(getNOM_ST_SERVICE(),"pas affecté");
					}else{
						addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getLiserv());
						Service unService = Service.chercherService(getTransaction(),unAffectationServiceInfos.getCodeservice());
						if(getTransaction().isErreur()){
							return ;
						}
						setServiceAffCourant(unService);
						setServiceCourant(unService);
					}
				}
			}
		}else if("pmateriel".equals(recup)){
			isMateriel = true;
			PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
			if(unPMatInfos!=null){
				if(unPMatInfos.getPminv()!=null){
					setPMatInfosCourant(unPMatInfos);
					setSelection(true);
				}
				// recherche du service auquel il est affecté
				PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),Services.dateDuJour());
				if(getTransaction().isErreur()){
					getTransaction().traiterErreur();
					addZone(getNOM_ST_SERVICE(),"pas affecté");
				}else{
					Service unService = Service.chercherService(getTransaction(),unPMASI.getSiserv());
					if(getTransaction().isErreur()){
						return;
					}
					addZone(getNOM_ST_SERVICE(),unService.getLiserv());
					setServiceAffCourant(unService);
					setServiceCourant(unService);
				}
			}
		}else{
			EquipementInfos unEquipementInfos =(EquipementInfos)VariableGlobale.recuperer(request,"EQUIPEMENTINFOS");
			if(unEquipementInfos!=null){
				if(unEquipementInfos.getNumeroinventaire()!=null){
					setEquipementInfosCourant(unEquipementInfos);
					setSelection(true);
					//recherche du service auquel il est affecté
//					 on recherche le service où l'équipement a été affecté 
					AffectationServiceInfos unAffectationServiceInfos = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),Services.dateDuJour());
					if(getTransaction().isErreur()){
							//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
							getTransaction().traiterErreur();
							addZone(getNOM_ST_SERVICE(),"pas affecté");
					}else{
						addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getLiserv());
						Service unService = Service.chercherService(getTransaction(),unAffectationServiceInfos.getCodeservice());
						if(getTransaction().isErreur()){
							return ;
						}
						setServiceAffCourant(unService);
						setServiceCourant(unService);
					}
				}else{
					isMateriel = true;
				}
			}else{
				isMateriel = true;
			}
			if(isMateriel){
				PMatInfos unPMatInfos = (PMatInfos)VariableGlobale.recuperer(request,"PMATINFOS");
				if(unPMatInfos!=null){
					if(unPMatInfos.getPminv()!=null){
						setPMatInfosCourant(unPMatInfos);
						setSelection(true);
					}
					// recherche du service auquel il est affecté
					PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),Services.dateDuJour());
					if(getTransaction().isErreur()){
						getTransaction().traiterErreur();
						addZone(getNOM_ST_SERVICE(),"pas affecté");
					}else{
						Service unService = Service.chercherService(getTransaction(),unPMASI.getSiserv());
						if(getTransaction().isErreur()){
							return;
						}
						addZone(getNOM_ST_SERVICE(),unService.getLiserv());
						setServiceAffCourant(unService);
						setServiceCourant(unService);
					}
				}
			}
		}
		VariableActivite.enlever(this,"TYPE");
		
		Declarations uneDeclaration = (Declarations)VariableActivite.recuperer(this,"DECLARATION");
		if(uneDeclaration!=null){
			if(uneDeclaration.getCodedec()!=null){
				setDeclarationCourante(uneDeclaration);
				Service unService = Service.chercherService(getTransaction(),uneDeclaration.getCodeservice());
				if(getTransaction().isErreur()){
					return ;
				}
				setServiceCourant(unService);
			}
			
		}
		String titreAction = (String)VariableActivite.recuperer(this,"TITRE_ACTION");
		addZone(getNOM_ST_TITRE_ACTION(),titreAction);
	}
	
//	initialisation des listes
	initialiseListeService(request);
	initialiseListeAgents(request);
	
//	 si l'équipement est affecté à un service, on sélectionne ce service par défaut
	if(!isSelectionService()){
		if(getServiceAffCourant()!=null){
			if(getServiceAffCourant().getServi()!=null){
				addZone(getNOM_LB_SERVICE_SELECT(),String.valueOf(-1));
				for (int i = 0; i < getListService().size(); i++) {
					Service unService = getListService().get(i);
					if (unService.getServi().equals(getServiceAffCourant().getServi())) {
						addZone(getNOM_LB_SERVICE_SELECT(),String.valueOf(i));
						break;
					}
				}
				initialiseListeAgents(request);
			}
		}
		
	}
	
	
	if(getDeclarationCourante()!=null){
		if(getDeclarationCourante().getCodedec()!=null){
			addZone(getNOM_EF_COMMENTAIRE(),getDeclarationCourante().getAnomalies());
			addZone(getNOM_EF_DATE(),getDeclarationCourante().getDate());
			if(getDeclarationCourante().getCodedec()!=null){
				/*AgentServiceInfos unAgentSI = AgentServiceInfos.chercherAgentServiceInfos(getTransaction(),getDeclarationCourante().getMatricule());
				if(getTransaction().isErreur()){
					return ;
				}*/
				
				//if(getDeclarationCourante().getCodeservice()!=null){
					Service unService = Service.chercherService(getTransaction(),getDeclarationCourante().getCodeservice());
					if(getTransaction().isErreur()){
						return ;
					}
					setServiceCourant(unService);
					// modification du 08/08/08 : agents ccas et agent cde pris en compte
//					int position = -1;
//					addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(-1));
//					for (int i = 0; i < getListAgent().size(); i++) {
//						AgentsMunicipaux agentSI = (AgentsMunicipaux)getListAgent().get(i);
//						if (agentSI.getNomatr().trim().equals(getDeclarationCourante().getMatricule().trim())) {
//							addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
//							break;
//						}
//					}	
					
					
					if (getDeclarationCourante().getCodeservice().equals("4000")){
						AgentCDE unAgent = AgentCDE.chercherAgentCDE(getTransaction(),getDeclarationCourante().getMatricule());
						if(getTransaction().isErreur()){
							return;
						}
						//	on sélectionne le bon agent
						if(unAgent!=null){
							addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(-1));
							for (int i = 0; i < getListAgent().size(); i++) {
								AgentCDE agentSI = (AgentCDE)getListAgent().get(i);
								if (agentSI.getNomatr().trim().equals(unAgent.getNomatr().trim())) {
									addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
									break;
								}
							}	
						}
					}else if(getDeclarationCourante().getCodeservice().equals("5000")){
						AgentCCAS unAgent = AgentCCAS.chercherAgentCCAS(getTransaction(),getDeclarationCourante().getMatricule());
						if(getTransaction().isErreur()){
							return;
						}
						//	on sélectionne le bon agent
						if(unAgent!=null){
							addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(-1));
							for (int i = 0; i < getListAgent().size(); i++) {
								AgentCCAS agentSI = (AgentCCAS)getListAgent().get(i);
								if (agentSI.getNomatr().trim().equals(unAgent.getNomatr().trim())) {
									addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
									break;
								}
							}	
						}
					}else {
						Agents unAgent = Agents.chercherAgents(getTransaction(),getDeclarationCourante().getMatricule());
						if(getTransaction().isErreur()){
							return;
						}
						setAgentCourant(unAgent);
						//	on sélectionne le bon agent
						if(unAgent!=null){
							addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(-1));
							for (int i = 0; i < getListAgent().size(); i++) {
								AActifs agentSI = (AActifs)getListAgent().get(i);
//								AgentsMunicipaux agentSI = (AgentsMunicipaux)getListAgent().get(i);
								if (agentSI.getNomatr().trim().equals(getAgentCourant().getNomatr().trim())) {
									addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
									break;
								}
							}	
						}
					}
				}else{
					Agents unAgent = Agents.chercherAgents(getTransaction(),getDeclarationCourante().getMatricule());
					if(getTransaction().isErreur()){
						return;
					}
					setAgentCourant(unAgent);
					//	on sélectionne le bon agent
					if(unAgent!=null){
						addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(-1));
						for (int i = 0; i < getListAgent().size(); i++) {
							AActifs agentSI = (AActifs)getListAgent().get(i);
							if (agentSI.getNomatr().trim().equals(getAgentCourant().getNomatr().trim())) {
								addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
								break;
							}
						}	
					}
				}
		
			}
		}
	//}
	

	
	//initialisation des zones
	if(!isMateriel){
		if(getEquipementInfosCourant()!=null){
			addZone(getNOM_ST_MARQUE(),getEquipementInfosCourant().getDesignationmarque());
			addZone(getNOM_ST_MODELE(),getEquipementInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_NOINV(),getEquipementInfosCourant().getNumeroinventaire());
			addZone(getNOM_ST_NOIMMAT(),getEquipementInfosCourant().getNumeroimmatriculation());
			addZone(getNOM_ST_TYPE(),getEquipementInfosCourant().getDesignationtypeequip());
			//recherche du service en cours
			String date = Services.dateDuJour();
			if(getTransaction().isErreur()){
				return ;
			}
			AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),date);
			if(getTransaction().isErreur()){
				//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
				getTransaction().traiterErreur();
				addZone(getNOM_ST_SERVICE(),"pas affecté");
			}else{
				/*int position = -1;
				addZone(getNOM_LB_SERVICE_SELECT(),String.valueOf(-1));
				for (int i = 0; i < getListService().size(); i++) {
					Service unService = (Service)getListService().get(i);
					if (unService.getLiserv().equals(unAffectationServiceInfos.getLiserv())) {
						addZone(getNOM_LB_SERVICE_SELECT(),String.valueOf(i));
						break;
					}
				}	
				addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getLiserv());*/
			}
		}
	}else{
		if(getPMatInfosCourant()!=null){
			addZone(getNOM_ST_MARQUE(),getPMatInfosCourant().getDesignationmarque());
			addZone(getNOM_ST_MODELE(),getPMatInfosCourant().getDesignationmodele());
			addZone(getNOM_ST_NOINV(),getPMatInfosCourant().getPminv());
			addZone(getNOM_ST_NOIMMAT(),getPMatInfosCourant().getPmserie());
			addZone(getNOM_ST_TYPE(),getPMatInfosCourant().getDesignationtypeequip());
			//recherche du service en cours
			String date = Services.dateDuJour();
			if(getTransaction().isErreur()){
				return ;
			}
			PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),date);
			if(getTransaction().isErreur()){
				//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
				getTransaction().traiterErreur();
				addZone(getNOM_ST_SERVICE(),"pas affecté");
			}
		}
	}
	
	

setFirst(false);
}

public void initialiseListeService(javax.servlet.http.HttpServletRequest request) throws Exception{
	// on récupère tous les services
	ArrayList<Service> listeServices = Service.listerService(getTransaction());
	if(getTransaction().isErreur()){
		return ;
	}
	setListService(listeServices);
	if(getListService().size()>0){
		int tailles [] = {60};
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding, true);
				
		for (java.util.ListIterator<Service> list = getListService().listIterator(); list.hasNext(); ) {
			Service aService = list.next();
			
			String ligne [] = { aService.getLiserv()};
			aFormat.ajouteLigne(ligne);
			
		}
		setLB_SERVICE(aFormat.getListeFormatee());
	}else{
		setLB_SERVICE(LBVide);
	}
	if(getServiceAffCourant()==null){
		Service unService = getListService().get(0);
		if(unService!=null){
			setServiceCourant(unService);
		}
	}
}

public boolean initialiseListeAgents(javax.servlet.http.HttpServletRequest request) throws Exception{
	//if(getServiceCourant()!=null){
		// on récupère l'élément sélectionné
	int indice = (Services.estNumerique(getVAL_LB_SERVICE_SELECT()) ? Integer.parseInt(getVAL_LB_SERVICE_SELECT()): -1); 
	/*if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un élement");
		return ;
	}*/
	if(indice<0){
		indice = 0;
	}
	Service monService = getListService().get(indice);
	setServiceCourant(monService);
	//ArrayList resultat = new ArrayList();
	ArrayList<?> resultat = AgentsMunicipaux.listerAgentsMunicipauxServi(getTransaction(),getServiceCourant().getServi());
	if(getTransaction().isErreur()){
		return false;
	}
	//if(getServiceCourant().getServi().equals("4000")){
	//if(getDeclarationCourante()!=null){
	if(getServiceCourant()!=null){
		if(getServiceCourant().getServi().equals("4000")){
			resultat = AgentCDE.listerAgentCDE(getTransaction());
		}else if(getServiceCourant().getServi().equals("5000")){
			resultat = AgentCCAS.listerAgentCCAS(getTransaction());
		}else{
			resultat = AActifs.listerAActifsService(getTransaction(),monService.getServi());//AgentServiceInfos.listerAgentService(getTransaction(),getServiceCourant().getServi());
		}
	}else{
		resultat = AActifs.listerAActifsService(getTransaction(),monService.getServi());//AgentServiceInfos.listerAgentService(getTransaction(),getServiceCourant().getServi());
	}
	/*}else{
		resultat = AActifs.listerAActifsService(getTransaction(),monService.getServi());//AgentServiceInfos.listerAgentService(getTransaction(),getServiceCourant().getServi());
	}*/
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur(getTransaction().getMessageErreur());
		return false;
	}
	setListAgent(resultat);
	if(getListAgent().size()>0){
		int tailles [] = {60};
		String [] padding = {"G"};
		FormateListe aFormat = new FormateListe(tailles,padding, true);
		for (java.util.ListIterator<?> list = getListAgent().listIterator(); list.hasNext(); ) {
		//	if(getDeclarationCourante()!=null){
				if(getServiceCourant().getServi()!=null){
					if(getServiceCourant().getServi().equals("4000")){
						AgentCDE aAActifs = (AgentCDE)list.next();
						String ligne [] = { aAActifs.getNom().trim()+" "+aAActifs.getPrenom().trim()};
						aFormat.ajouteLigne(ligne);
					}else if(getServiceCourant().getServi().equals("5000")){
						AgentCCAS aAActifs = (AgentCCAS)list.next();
						String ligne [] = { aAActifs.getNom().trim()+" "+aAActifs.getPrenom().trim()};
						aFormat.ajouteLigne(ligne);
					}else{
						AActifs aAActifs = (AActifs)list.next();
						String ligne [] = { aAActifs.getNom().trim()+" "+aAActifs.getPrenom().trim()};
						aFormat.ajouteLigne(ligne);
					}
				}else{
					AActifs aAActifs = (AActifs)list.next();
					String ligne [] = { aAActifs.getNom().trim()+" "+aAActifs.getPrenom().trim()};
					aFormat.ajouteLigne(ligne);
				}
	//		}
			/*}else{
				AActifs aAActifs = (AActifs)list.next();
				String ligne [] = { aAActifs.getNom().trim()+" "+aAActifs.getPrenom().trim()};
				aFormat.ajouteLigne(ligne);
			}*/
		
//			AgentsMunicipaux unAgent = (AgentsMunicipaux)list.next();
//			String ligne [] = { unAgent.getNom().trim()+" "+unAgent.getPrenom().trim()};
//			aFormat.ajouteLigne(ligne);
			
		}
		
		setLB_AGENT(aFormat.getListeFormatee());
	}else{
		setLB_AGENT(LBVide);
	}
	
//	on sélectionne le bon agent
	if(getAgentCourant()!=null){
		addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(-1));
		for (int i = 0; i < getListAgent().size(); i++) {
		AActifs agentSI = (AActifs)getListAgent().get(i);
			//AgentsMunicipaux agentSI = (AgentsMunicipaux)getListAgent().get(i);
			if (agentSI.getNomatr().trim().equals(getAgentCourant().getNomatr().trim())) {
				addZone(getNOM_LB_AGENT_SELECT(),String.valueOf(i));
				break;
			}
		}	
	}
	
	return true;
}

/**
 * Constructeur du process OeDeclarations_ajout.
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
public OeDeclarations_ajout() {
	super();
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MARQUE
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_MARQUE() {
	return "NOM_ST_MARQUE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MARQUE
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_MARQUE() {
	return getZone(getNOM_ST_MARQUE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_MODELE
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_MODELE() {
	return "NOM_ST_MODELE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_MODELE
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_MODELE() {
	return getZone(getNOM_ST_MODELE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOIMMAT
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_NOIMMAT() {
	return "NOM_ST_NOIMMAT";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOIMMAT
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_NOIMMAT() {
	return getZone(getNOM_ST_NOIMMAT());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_NOINV
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_NOINV() {
	return "NOM_ST_NOINV";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_NOINV
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_NOINV() {
	return getZone(getNOM_ST_NOINV());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_SERVICE
 * Date de création : (22/08/05 08:58:32)
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
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_SERVICE() {
	return getZone(getNOM_ST_SERVICE());
}
/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TYPE
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_TYPE() {
	return "NOM_ST_TYPE";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TYPE
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_TYPE() {
	return getZone(getNOM_ST_TYPE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_COMMENTAIRE
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_COMMENTAIRE() {
	return "NOM_EF_COMMENTAIRE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_COMMENTAIRE
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_COMMENTAIRE() {
	return getZone(getNOM_EF_COMMENTAIRE());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_DATE
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_DATE() {
	return "NOM_EF_DATE";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_DATE
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_DATE() {
	return getZone(getNOM_EF_DATE());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_EQUIP
 * Date de création : (22/08/05 09:00:36)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE_EQUIP() {
	return "NOM_PB_RECHERCHE_EQUIP";
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHE_EQUIP
 * Date de création : (22/08/05 09:00:36)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHE_PM() {
	return "NOM_PB_RECHERCHE_PM";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (22/08/05 09:00:36)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE_EQUIP(javax.servlet.http.HttpServletRequest request) throws Exception {
	//si un numéro saisie on recherche l'équipement équivalent
	String numero = getZone(getNOM_EF_RECHERCHE_EQUIP());
	addZone(getNOM_EF_RECHERCHE_PM(), "");
	setEquipementInfosCourant(null);
	setPMatInfosCourant(null);
	
	EquipementInfos unEquipementInfos = EquipementInfos.chercherEquipementInfosInvOuImmat(getTransaction(),numero);
	if(getTransaction().isErreur()){
		/*LB le 24/09/11 suite à la demande de sabrina
		isMateriel = true;
		getTransaction().traiterErreur();
		// on cherche si pas petit matériel
		PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),numero);
		if(getTransaction().isErreur()){
			getTransaction().declarerErreur("Aucun résultat trouvé.");
			return false;
		}
		if(unPMatInfos!=null){
			if(null!=unPMatInfos.getPminv()){
				setPMatInfosCourant(unPMatInfos);
			}
		}
		PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),Services.dateDuJour());
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur();
			addZone(getNOM_ST_SERVICE(),"pas affecté");
		}else{
			Service unService = Service.chercherService(getTransaction(),unPMASI.getSiserv());
			if(getTransaction().isErreur()){
				return false;
			}
			addZone(getNOM_ST_SERVICE(),unService.getLiserv());
			setServiceAffCourant(unService);
			setSelection(true);
			setSelectionService(false);
		}
		*/
		return false;
	}else{
		isMateriel = false;
		if(unEquipementInfos!=null){
			setEquipementInfosCourant(unEquipementInfos);
		}
		AffectationServiceInfos unAffectationServiceInfos = AffectationServiceInfos.chercherAffectationServiceInfosCourantEquip(getTransaction(),getEquipementInfosCourant().getNumeroinventaire(),Services.dateDuJour());
		if(getTransaction().isErreur()){
				//si pas trouvé, c'est fonctionellement normal, on traite l'erreur
				getTransaction().traiterErreur();
				addZone(getNOM_ST_SERVICE(),"pas affecté");
		}else{
			addZone(getNOM_ST_SERVICE(),unAffectationServiceInfos.getLiserv());
		}
		if(unAffectationServiceInfos!=null){
			if(unAffectationServiceInfos.getNumeroinventaire()!=null){
				Service unService = Service.chercherService(getTransaction(),unAffectationServiceInfos.getCodeservice());
				if(getTransaction().isErreur()){
					return false;
				}
				setServiceAffCourant(unService);
//				 si l'équipement est affecté à un service, on sélectionne ce service par défaut
				setSelection(true);
				setSelectionService(false);
			}
		}
	}
	
	return true;
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (22/08/05 09:00:36)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHE_PM(javax.servlet.http.HttpServletRequest request) throws Exception {
	//si un numéro saisie on recherche l'équipement équivalent
	String numero = getZone(getNOM_EF_RECHERCHE_PM());
	addZone(getNOM_EF_RECHERCHE_EQUIP(), "");
	setEquipementInfosCourant(null);
	setPMatInfosCourant(null);
	
	// on cherche si pas petit matériel
	PMatInfos unPMatInfos = PMatInfos.chercherPMatInfos(getTransaction(),numero);
	if(getTransaction().isErreur()){
		getTransaction().declarerErreur("Aucun résultat trouvé.");
		return false;
	}
	isMateriel = true;
		
		if(unPMatInfos!=null){
			if(null!=unPMatInfos.getPminv()){
				setPMatInfosCourant(unPMatInfos);
			}
		}
		PM_Affectation_Sce_Infos unPMASI = PM_Affectation_Sce_Infos.chercherPM_Affectation_Sce_InfosCourantPm(getTransaction(),getPMatInfosCourant().getPminv(),Services.dateDuJour());
		if(getTransaction().isErreur()){
			getTransaction().traiterErreur();
			addZone(getNOM_ST_SERVICE(),"pas affecté");
		}else{
			Service unService = Service.chercherService(getTransaction(),unPMASI.getSiserv());
			if(getTransaction().isErreur()){
				return false;
			}
			addZone(getNOM_ST_SERVICE(),unService.getLiserv());
			setServiceAffCourant(unService);
			setSelection(true);
			setSelectionService(false);
		}
	
	return true;
}

/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE_EQUIP
 * Date de création : (22/08/05 09:00:36)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_RECHERCHE_EQUIP() {
	return "NOM_EF_RECHERCHE_EQUIP";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE_EQUIP
 * Date de création : (22/08/05 09:00:36)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_RECHERCHE_EQUIP() {
	return getZone(getNOM_EF_RECHERCHE_EQUIP());
}
/**
 * Retourne le nom d'une zone de saisie pour la JSP :
 * EF_RECHERCHE_PM
 * Date de création : (22/08/05 09:00:36)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_EF_RECHERCHE_PM() {
	return "NOM_EF_RECHERCHE_PM";
}
/**
 * Retourne la valeur à afficher par la JSP pour la zone de saisie  :
 * EF_RECHERCHE_EQUIP
 * Date de création : (22/08/05 09:00:36)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_EF_RECHERCHE_PM() {
	return getZone(getNOM_EF_RECHERCHE_PM());
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_ANNULER
 * Date de création : (22/08/05 09:01:44)
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
 * Date de création : (22/08/05 09:01:44)
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
 * PB_VALIDER
 * Date de création : (22/08/05 09:01:44)
 * @author : Générateur de process
 */
/**
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
 * Date de création : (22/08/05 09:01:44)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_VALIDER(javax.servlet.http.HttpServletRequest request) throws Exception {
	boolean resultat;
	String commentaire = "";
	Equipement unEquipement = new Equipement();
	PMateriel unPMateriel =  new PMateriel();
	
	// contrôle de la longueur de l'anomalie
	commentaire = getZone(getNOM_EF_COMMENTAIRE());
	if (commentaire.length()>600){
		getTransaction().declarerErreur("La longueur des anomalies ne doit pas dépasser 600 caractères.");
		setFocus(getNOM_EF_COMMENTAIRE());
		return false;
	}
	isMateriel = false;
	if((getPMatInfosCourant()!=null)&&(getPMatInfosCourant().getPminv()!=null)){
		if(getVAL_ST_NOINV().equals(getPMatInfosCourant().getPminv())){
			isMateriel = true;
		}else{
			isMateriel = false;
		}
		}
	if(!isMateriel){
		// il faut sélectionner un équipement
		if(getEquipementInfosCourant()==null){
			getTransaction().declarerErreur("Vous devez sélectionner un équipement");
			return false;
		}
		unEquipement = Equipement.chercherEquipement(getTransaction(),getEquipementInfosCourant().getNumeroinventaire());
		if(getTransaction().isErreur()){
			return false;
		}
	}else{
		// il faut sélectionner un petit matériel
		if(getPMatInfosCourant()==null){
			getTransaction().declarerErreur("Vous devez sélectionner un petit matériel");
			return false;
		}
		unPMateriel = PMateriel.chercherPMateriel(getTransaction(),getPMatInfosCourant().getPminv());
		if(getTransaction().isErreur()){
			return false;
		}
	}
	// on récupère les infos avant d'enregistrer
	int indice = (Services.estNumerique(getVAL_LB_AGENT_SELECT()) ? Integer.parseInt(getVAL_LB_AGENT_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un agent");
		return false;
	}
	//AJOUT OFONTENEAU 20090408 on récupère aussi le service (département) pour enlever le bug (c'est encore le plus simple a faire)
	int indiceservice = (Services.estNumerique(getVAL_LB_SERVICE_SELECT()) ? Integer.parseInt(getVAL_LB_SERVICE_SELECT()): -1);
	if (indiceservice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un service");
		return false;
	}else{
		Service unService = getListService().get(indiceservice);
		setServiceCourant(unService);
	}
	
	resultat = validationAgent(request,indice,unEquipement,unPMateriel);
//	if(getServiceCourant().getServi().equals("4000")){
//		resultat = validationAgentCDE(request,indice,unEquipement);
//	}else if(getServiceCourant().getServi().equals("5000")){
//		resultat = validationAgentCCAS(request,indice,unEquipement);
//	}else{
//		resultat = validationAgentActifs(request,indice,unEquipement);
//	}
	return resultat;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECHERCHER
 * Date de création : (22/08/05 09:08:53)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_PB_RECHERCHER() {
	return "NOM_PB_RECHERCHER";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (22/08/05 09:08:53)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECHERCHER(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","DECL_AJOUT");
	setStatut(STATUT_RECHERCHER,true);
	return true;
}
	public Declarations getDeclarationCourante() {
		return declarationCourante;
	}
	public void setDeclarationCourante(Declarations declarationCourante) {
		this.declarationCourante = declarationCourante;
	}
	public EquipementInfos getEquipementInfosCourant() {
		return equipementInfosCourant;
	}
	public void setEquipementInfosCourant(EquipementInfos equipementInfosCourant) {
		this.equipementInfosCourant = equipementInfosCourant;
	}
	public PMatInfos getPMatInfosCourant() {
		return pMatInfosCourant;
	}
	public void setPMatInfosCourant(PMatInfos pMatInfosCourant) {
		this.pMatInfosCourant = pMatInfosCourant;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	private java.lang.String[] LB_AGENT;
	private java.lang.String[] LB_SERVICE;
/**
 * Getter de la liste avec un lazy initialize :
 * LB_AGENT
 * Date de création : (22/08/05 09:37:34)
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
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
private void setLB_AGENT(java.lang.String[] newLB_AGENT) {
	LB_AGENT = newLB_AGENT;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_AGENT
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_AGENT() {
	return "NOM_LB_AGENT";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_AGENT_SELECT
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_AGENT_SELECT() {
	return "NOM_LB_AGENT_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_AGENT
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String [] getVAL_LB_AGENT() {
	return getLB_AGENT();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_AGENT
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_LB_AGENT_SELECT() {
	return getZone(getNOM_LB_AGENT_SELECT());
}
/**
 * Getter de la liste avec un lazy initialize :
 * LB_SERVICE
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
private String [] getLB_SERVICE() {
	if (LB_SERVICE == null)
		LB_SERVICE = initialiseLazyLB();
	return LB_SERVICE;
}
/**
 * Setter de la liste:
 * LB_SERVICE
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
private void setLB_SERVICE(java.lang.String[] newLB_SERVICE) {
	LB_SERVICE = newLB_SERVICE;
}
/**
 * Retourne le nom de la zone pour la JSP :
 * NOM_LB_SERVICE
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_SERVICE() {
	return "NOM_LB_SERVICE";
}
/**
 * Retourne le nom de la zone de la ligne sélectionnée pour la JSP :
 * NOM_LB_SERVICE_SELECT
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_LB_SERVICE_SELECT() {
	return "NOM_LB_SERVICE_SELECT";
}
/**
 * Méthode à personnaliser
 * Retourne la valeur à afficher pour la zone de la JSP :
 * LB_SERVICE
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String [] getVAL_LB_SERVICE() {
	return getLB_SERVICE();
}
/**
 * Méthode à personnaliser
 * Retourne l'indice à sélectionner pour la zone de la JSP :
 * LB_SERVICE
 * Date de création : (22/08/05 09:37:34)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_LB_SERVICE_SELECT() {
	return getZone(getNOM_LB_SERVICE_SELECT());
}
	public ArrayList<?> getListAgent() {
		return listAgent;
	}
	public void setListAgent(ArrayList<?> listAgent) {
		this.listAgent = listAgent;
	}
	public ArrayList<Service> getListService() {
		return listService;
	}
	public void setListService(ArrayList<Service> listService) {
		this.listService = listService;
	}
	public Agents getAgentCourant() {
		return agentCourant;
	}
	public void setAgentCourant(Agents agentCourant) {
		this.agentCourant = agentCourant;
	}
	public Service getServiceCourant() {
		return serviceCourant;
	}
	public void setServiceCourant(Service serviceCourant) {
		this.serviceCourant = serviceCourant;
	}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_OK_SCE
 * Date de création : (22/08/05 10:00:10)
 * @author : Générateur de process
 */
/**
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
 * Date de création : (22/08/05 10:00:10)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_OK_SCE(javax.servlet.http.HttpServletRequest request) throws Exception {
	// on récupère les infos si elles sont saisies
	if(getDeclarationCourante()!=null){
		getDeclarationCourante().setDate(getZone(getNOM_EF_DATE()));
		getDeclarationCourante().setAnomalies(getZone(getNOM_EF_COMMENTAIRE()));
	}
		
	//initialisation des agents
	int indice = (Services.estNumerique(getVAL_LB_SERVICE_SELECT()) ? Integer.parseInt(getVAL_LB_SERVICE_SELECT()): -1);
	if (indice == -1) {
		getTransaction().declarerErreur("Vous devez sélectionner un service");
		return false;
	}
	Service unService = getListService().get(indice);
	setServiceCourant(unService);
	setSelectionService(true);
	return true;
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
	return getNOM_EF_RECHERCHE_EQUIP();
}

/**
 * Retourne pour la JSP le nom de la zone statique :
 * ST_TITRE_ACTION
 * Date de création : (22/08/05 11:40:26)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_ST_TITRE_ACTION() {
	return "NOM_ST_TITRE_ACTION";
}
/**
 * Retourne la valeur à afficher par la JSP  pour la zone :
 * ST_TITRE_ACTION
 * Date de création : (22/08/05 11:40:26)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getVAL_ST_TITRE_ACTION() {
	return getZone(getNOM_ST_TITRE_ACTION());
}
public boolean isSelection() {
	return isSelection;
}
public void setSelection(boolean isSelection) {
	this.isSelection = isSelection;
}
public Service getServiceAffCourant() {
	return serviceAffCourant;
}
public void setServiceAffCourant(Service serviceAffCourant) {
	this.serviceAffCourant = serviceAffCourant;
}
public boolean isSelectionService() {
	return isSelectionService;
}
public void setSelectionService(boolean isSelectionService) {
	this.isSelectionService = isSelectionService;
}

public boolean validationAgent(javax.servlet.http.HttpServletRequest request,int indice,Equipement unEquipement,PMateriel unPmateriel) throws Exception{
	String numot = "";
	//AgentsMunicipaux unAgentM = (AgentsMunicipaux)getListAgent().get(indice);
	Agents unAgent = Agents.chercherAgents(getTransaction(),"1");
	if (null!=getListAgent()&&getListAgent().size()!=0){
		AActifs unAgentM = (AActifs)getListAgent().get(indice);
		unAgent = Agents.chercherAgents(getTransaction(),unAgentM.getNomatr());
	}
	setAgentCourant(unAgent);
	String newDate = getZone(getNOM_EF_DATE());
	String newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	
	// controle : champs obligatoires
	if (newDate.length() == 0) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la date"));//"ERR008","Le libellé @ est obligatoire"
		return false;
	}else{
		// vérification du format de la date
		if (newDate!=""){
			if (!Services.estUneDate(newDate)){
				getTransaction().declarerErreur("La date n'est pas correcte.");
				return false;
			}
		}
	}
	if(newCommentaire.equals("")){
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","Anomalies signalées"));
		return false;
	}
	//si création
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		setDeclarationCourante(new Declarations());
		getDeclarationCourante().setAnomalies(newCommentaire);
		getDeclarationCourante().setDate(newDate);
		
		// on crée la déclaration
		if(!isMateriel){
			numot = getDeclarationCourante().creationDeclarationsOT(getTransaction(),getAgentCourant(),unEquipement,getServiceCourant());
		}else{
			numot = getDeclarationCourante().creationDeclarationsFPM(getTransaction(),getAgentCourant(),unPmateriel,getServiceCourant());
		}
		if(numot.equals("erreur")){
			getTransaction().declarerErreur("Une erreur s'est produite");
			return false;
		}
	//modification
	}else if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
		getDeclarationCourante().setAnomalies(newCommentaire);
		getDeclarationCourante().setDate(newDate);
		// on modifie la déclaration
		if(!isMateriel){
			getDeclarationCourante().modifierDeclarations(getTransaction(),unEquipement,getAgentCourant(),getServiceCourant());
		}else{
			getDeclarationCourante().modifierDeclarationsPM(getTransaction(),unPmateriel,getAgentCourant(),getServiceCourant());
		}
		
	}
	if(getTransaction().isErreur()){
		return false;
	}else{
		commitTransaction();
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
			if(!isMateriel){
				//	 on met les variables dont a besoin l'OT
				OT unOT = OT.chercherOT(getTransaction(),numot);
				if(getTransaction().isErreur()){
					getTransaction().declarerErreur(getTransaction().traiterErreur());
					return false;
				}
				if(null==unOT){
					unOT = new OT();
				}
				VariableGlobale.ajouter(request,"OT",unOT);
				VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
				VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
				VariableActivite.ajouter(this,"DEBRANCHEDEC","TRUE");
				setStatut(STATUT_OT,true);
			}else{
				if(null==getPMatInfosCourant()){
					setPMatInfosCourant(new PMatInfos());
				}
//				 on met les variables dont a besoin la fiche
				FPM unFPM = FPM.chercherFPM(getTransaction(),numot);
				if(getTransaction().isErreur()){
					getTransaction().declarerErreur(getTransaction().traiterErreur());
					return false;
				}
				if(null==unFPM){
					unFPM = new FPM();
				}
				VariableGlobale.ajouter(request,"FPM",unFPM);
				VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
				VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
				VariableActivite.ajouter(this,"DEBRANCHEDEC","TRUE");
				setStatut(STATUT_FPM,true);
			}
		}else{
			if(null==getPMatInfosCourant()){
				setPMatInfosCourant(new PMatInfos());
			}
			if(null==getEquipementInfosCourant()){
				setEquipementInfosCourant(new EquipementInfos());
			}
			VariableGlobale.ajouter(request,"PMATINFOS",getPMatInfosCourant());
			VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
			setStatut(STATUT_PROCESS_APPELANT);
		}
	}
	return true;
}

public boolean validationAgentActifs(javax.servlet.http.HttpServletRequest request,int indice,Equipement unEquipement) throws Exception{
	String numot = "";
	AActifs unAActifs = (AActifs)getListAgent().get(indice);
	Agents unAgent = Agents.chercherAgents(getTransaction(),unAActifs.getNomatr());
	if(getTransaction().isErreur()){
		return false;
	}
	setAgentCourant(unAgent);
	String newDate = getZone(getNOM_EF_DATE());
	String newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	
	// controle : champs obligatoires
	if (newDate.length() == 0) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la date"));//"ERR008","Le libellé @ est obligatoire"
		return false;
	}else{
		// vérification du format de la date
		if (newDate!=""){
			if (!Services.estUneDate(newDate)){
				getTransaction().declarerErreur("La date n'est pas correcte.");
				return false;
			}
		}
	}
	if(newCommentaire.equals("")){
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","Anomalies signalées"));
		return false;
	}
	//si création
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		setDeclarationCourante(new Declarations());
		getDeclarationCourante().setAnomalies(newCommentaire);
		getDeclarationCourante().setDate(newDate);
		
		// on crée la déclaration
		numot = getDeclarationCourante().creationDeclarationsOT(getTransaction(),getAgentCourant(),unEquipement,getServiceCourant());
		if(numot.equals("erreur")){
			getTransaction().declarerErreur("Une erreur s'est produite");
			return false;
		}
	//modification
	}else if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
		getDeclarationCourante().setAnomalies(newCommentaire);
		getDeclarationCourante().setDate(newDate);
		// on crée la déclaration
		getDeclarationCourante().modifierDeclarations(getTransaction(),unEquipement,getAgentCourant(),getServiceCourant());
		
	}
	if(getTransaction().isErreur()){
		return false;
	}else{
		commitTransaction();
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
			//	 on met les variables dont a besoin l'OT
			OT unOT = OT.chercherOT(getTransaction(),numot);
			if(null==unOT){
				unOT = new OT();
			}
			VariableGlobale.ajouter(request,"OT",unOT);
			VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
			VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
			VariableActivite.ajouter(this,"DEBRANCHEDEC","TRUE");
			setStatut(STATUT_OT,true);
		}else{
			VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
			setStatut(STATUT_PROCESS_APPELANT);
		}
	}
	return true;
}

public boolean validationAgentCDE(javax.servlet.http.HttpServletRequest request,int indice, Equipement unEquipement)throws Exception{
	String numot = "";
	AgentCDE unAActifs = (AgentCDE)getListAgent().get(indice);
	//Agents unAgent = Agents.chercherAgents(getTransaction(),unAActifs.getNomatr());
	/*if(getTransaction().isErreur()){
		return false;
	}*/
	//setAgentCourant(unAgent);
	String newDate = getZone(getNOM_EF_DATE());
	String newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	
	// controle : champs obligatoires
	if (newDate.length() == 0) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la date"));//"ERR008","Le libellé @ est obligatoire"
		return false;
	}
	if(newCommentaire.equals("")){
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","Anomalies signalées"));
		return false;
	}
	//si création
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		setDeclarationCourante(new Declarations());
		getDeclarationCourante().setAnomalies(newCommentaire);
		getDeclarationCourante().setDate(newDate);
		
		// on crée la déclaration
		numot = getDeclarationCourante().creationDeclarationsOTAgentCDE(getTransaction(),unAActifs,unEquipement,getServiceCourant());
		if(numot.equals("erreur")){
			getTransaction().declarerErreur("Une erreur s'est produite");
			return false;
		}
	//modification
	}else if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
		getDeclarationCourante().setAnomalies(newCommentaire);
		getDeclarationCourante().setDate(newDate);
		// on crée la déclaration
		getDeclarationCourante().modifierDeclarationsAgentCDE(getTransaction(),unEquipement,unAActifs,getServiceCourant());
		
	}
	if(getTransaction().isErreur()){
		return false;
	}else{
		commitTransaction();
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
			//	 on met les variables dont a besoin l'OT
			OT unOT = OT.chercherOT(getTransaction(),numot);
			VariableGlobale.ajouter(request,"OT",unOT);
			VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
			VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
			setStatut(STATUT_OT,true);
		}else{
			VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
			setStatut(STATUT_PROCESS_APPELANT);
		}
	}
	return true;
}
public boolean validationAgentCCAS(javax.servlet.http.HttpServletRequest request,int indice,Equipement unEquipement)throws Exception{
	String numot = "";
	AgentCCAS unAActifs = (AgentCCAS)getListAgent().get(indice);
	String newDate = getZone(getNOM_EF_DATE());
	String newCommentaire = getZone(getNOM_EF_COMMENTAIRE()).toUpperCase();
	
	// controle : champs obligatoires
	if (newDate.length() == 0) {
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","de la date"));//"ERR008","Le libellé @ est obligatoire"
		return false;
	}
	if(newCommentaire.equals("")){
		getTransaction().declarerErreur(MairieMessages.getMessage("ERR008","Anomalies signalées"));
		return false;
	}
	//si création
	if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
		setDeclarationCourante(new Declarations());
		getDeclarationCourante().setAnomalies(newCommentaire);
		getDeclarationCourante().setDate(newDate);
		
		// on crée la déclaration
		numot = getDeclarationCourante().creationDeclarationsOTAgentCCAS(getTransaction(),unAActifs,unEquipement,getServiceCourant());
		if(numot.equals("erreur")){
			getTransaction().declarerErreur("Une erreur s'est produite");
			return false;
		}
	//modification
	}else if(getVAL_ST_TITRE_ACTION().equals(ACTION_MODIFICATION)){
		getDeclarationCourante().setAnomalies(newCommentaire);
		getDeclarationCourante().setDate(newDate);
		// on crée la déclaration
		getDeclarationCourante().modifierDeclarationsAgentCCAS(getTransaction(),unEquipement,unAActifs,getServiceCourant());
		
	}
	if(getTransaction().isErreur()){
		return false;
	}else{
		commitTransaction();
		if(getVAL_ST_TITRE_ACTION().equals(ACTION_CREATION)){
			//	 on met les variables dont a besoin l'OT
			OT unOT = OT.chercherOT(getTransaction(),numot);
			if(getTransaction().isErreur()){
				getTransaction().declarerErreur(getTransaction().traiterErreur());
				return false;
			}
			VariableGlobale.ajouter(request,"OT",unOT);
			VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
			VariableActivite.ajouter(this,"TITRE_ACTION",ACTION_MODIFICATION);
			setStatut(STATUT_OT,true);
		}else{
			VariableGlobale.ajouter(request,"EQUIPEMENTINFOS",getEquipementInfosCourant());
			setStatut(STATUT_PROCESS_APPELANT);
		}
	}
	return true;
}
/**
 * Retourne le nom d'un bouton pour la JSP :
 * PB_RECH_PM
 * Date de création : (18/09/07 14:04:18)
 * @author : Générateur de process
 */
/**
 * @return String
 */
public java.lang.String getNOM_PB_RECH_PM() {
	return "NOM_PB_RECH_PM";
}
/**
 * - Traite et affecte les zones saisies dans la JSP.
 * - Implémente les règles de gestion du process
 * - Positionne un statut en fonction de ces règles :
 *   setStatut(STATUT, boolean veutRetour) ou setStatut(STATUT,Message d'erreur)
 * Date de création : (18/09/07 14:04:18)
 * @author : Générateur de process
 * @param request request
 * @return boolean 
 * @throws Exception Exception
 */
public boolean performPB_RECH_PM(javax.servlet.http.HttpServletRequest request) throws Exception {
	VariableActivite.ajouter(this,"MODE","DECL_AJOUT");
	setStatut(STATUT_RECH_PM,true);
	return true;
}
/**
 * Méthode appelée par la servlet qui aiguille le traitement : 
 * en fonction du bouton de la JSP 
 * Date de création : (22/08/05 08:58:32)
 * @author : Générateur de process
 */
public boolean recupererStatut(javax.servlet.http.HttpServletRequest request) throws Exception{

	//Si on arrive de la JSP alors on traite le get
	if (request.getParameter("JSP")!=null && request.getParameter("JSP").equals(getJSP())) {

		//Si clic sur le bouton PB_RECH_PM
		if (testerParametre(request, getNOM_PB_RECH_PM())) {
			return performPB_RECH_PM(request);
		}

		//Si clic sur le bouton PB_OK_SCE
		if (testerParametre(request, getNOM_PB_OK_SCE())) {
			return performPB_OK_SCE(request);
		}

		//Si clic sur le bouton PB_RECHERCHER
		if (testerParametre(request, getNOM_PB_RECHERCHER())) {
			return performPB_RECHERCHER(request);
		}

		//Si clic sur le bouton PB_ANNULER
		if (testerParametre(request, getNOM_PB_ANNULER())) {
			return performPB_ANNULER(request);
		}

		//Si clic sur le bouton PB_VALIDER
		if (testerParametre(request, getNOM_PB_VALIDER())) {
			return performPB_VALIDER(request);
		}

		//Si clic sur le bouton PB_RECHERCHE_EQUIP
		if (testerParametre(request, getNOM_PB_RECHERCHE_EQUIP())) {
			return performPB_RECHERCHE_EQUIP(request);
		}

		//Si clic sur le bouton PB_RECHERCHE_EQUIP
		if (testerParametre(request, getNOM_PB_RECHERCHE_PM())) {
			return performPB_RECHERCHE_PM(request);
		}

	}
	//Si TAG INPUT non géré par le process
	setStatut(STATUT_MEME_PROCESS);
	return true;
}
/**
 * Retourne le nom de la JSP du process
 * Zone à utiliser dans un champ caché dans chaque formulaire de la JSP.
 * Date de création : (18/09/07 14:05:02)
 * @author : Générateur de process
 */
public String getJSP() {
	return "OeDeclarations_ajout.jsp";
}
}
