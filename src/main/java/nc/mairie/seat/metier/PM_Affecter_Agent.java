package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;
import nc.mairie.technique.Transaction;

/**
 * Objet métier PM_Affecter_Agent
 */
public class PM_Affecter_Agent extends BasicMetier {
	public String matricule;
	public String pminv;
	public String ddeb;
	public String dfin;
	public String hdeb;
	public String hfin;
	public String hdebmn;
	public String hfinmn;
	public String codesce;
/**
* Renvoie une chaîne correspondant à la valeur de cet objet.
* @return une représentation sous forme de chaîne du destinataire
*/
public String toString() {
	// Insérez ici le code pour finaliser le destinataire
	// Cette implémentation transmet le message au super. Vous pouvez remplacer ou compléter le message.
	return super.toString();
}
/**
 * Retourne un ArrayList d'objet métier : PM_Affecter_Agent.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PM_Affecter_Agent> listerPM_Affecter_Agent(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PM_Affecter_Agent unPM_Affecter_Agent = new PM_Affecter_Agent();
	return unPM_Affecter_Agent.getMyPM_Affecter_AgentBroker().listerPM_Affecter_Agent(aTransaction);
}
/**
 * Retourne un PM_Affecter_Agent.
 * @param aTransaction Transaction
 * @param code code
 * @return PM_Affecter_Agent
 * @throws Exception Exception
 */
public static PM_Affecter_Agent chercherPM_Affecter_Agent(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PM_Affecter_Agent unPM_Affecter_Agent = new PM_Affecter_Agent();
	return unPM_Affecter_Agent.getMyPM_Affecter_AgentBroker().chercherPM_Affecter_Agent(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @param unPMateriel unPMateriel
 * @param unAgent unAgent
 * @param newDate newDate
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPm_Affecter_Agent(nc.mairie.technique.Transaction aTransaction, PMateriel unPMateriel,AgentInterface unAgent,String newDate )  throws Exception {
//	on vérifie ques les paramètres ne sont pas null
	if (null == unPMateriel.getPminv()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	if (null == unAgent.getNomatr()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agent"));
		return false;
	}
	//RG : controle des dates
	if((getDdeb()==null)||(getDdeb().equals(""))){
		if(!Services.estUneDate(getDdeb())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDdeb(Services.formateDate(getDdeb()));
		}
	}
	if((getDfin()!=null)&&(!getDfin().equals(""))){
		if(!Services.estUneDate(getDfin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDfin(Services.formateDate(getDfin()));
		}
	}
//	on controle que la date soit supérieur ou égale à la date de début
	int controle = 0;
	if (null!=getDfin()  && !"".equals(getDfin())){
		controle = Services.compareDates(getDfin(),getDdeb());
		if (controle==-1){
			aTransaction.declarerErreur("La date de fin doit être supérieur ou égale à la date de début d'affectation ");
			return false;
		}else if(controle==-9999){
			return false;
		}else if (controle==0){
				if((!getHdeb().equals("00"))&&(!getHfin().equals("00"))){
					if(Integer.parseInt(getHfin())<Integer.parseInt(getHdeb())){
						aTransaction.declarerErreur("L'heure de fin doit être supérieur à l'heure de début");
						return false;
					}else if (Integer.parseInt(getHfin())==Integer.parseInt(getHdeb())){
						if (Integer.parseInt(getHfinmn())<(Integer.parseInt(getHdebmn()))){
							aTransaction.declarerErreur("L'heure de fin doit être supérieur à l'heure de début");
							return false;
						}else if (Integer.parseInt(getHfinmn())==(Integer.parseInt(getHdebmn()))){
							aTransaction.declarerErreur("Attention.L'heure de début est égale à l'heure de fin pour la même date.");
						}
					}
				}
		}
	}
	
//	Affectation des liens
	setPminv(unPMateriel.getPminv());
	setMatricule(unAgent.getNomatr());
	setDdeb(newDate);
	//on controle que la date soit supérieur à la date de mise en circulation
	controle = Services.compareDates(getDdeb(),unPMateriel.getDmes());
	if (controle==-1){
		aTransaction.declarerErreur("La date doit être supérieur à la date de mise en circulation ("+unPMateriel.getDmes()+")");
		return false;
	}else if(controle==-9999){
		return false;
	}
	//controle de la date de fin
	if ((!"01/01/0001".equals(getDfin())&&(null!=getDfin()) &&(!"".equals(getDfin())))){
		controle = Services.compareDates(getDfin(),getDdeb());
		if (controle==-1){
			aTransaction.declarerErreur("La date de fin doit être supérieur ou égale à la date de début de l'affectation.");
			return false;
		}else if (controle==-9999){
			return false;
		}else if (controle==0){
			if(!getHdeb().equals("00")&&(!getHfin().equals("00")))
				if(Integer.parseInt(getHfin())<Integer.parseInt(getHdeb())){
					aTransaction.declarerErreur("L'heure de fin doit être supérieur à l'heure de début");
					return false;
				}else if (Integer.parseInt(getHfin())==Integer.parseInt(getHdeb())){
					if (Integer.parseInt(getHfinmn())<(Integer.parseInt(getHdebmn()))){
						aTransaction.declarerErreur("L'heure de fin doit être supérieur à l'heure de début");
						return false;
					}else if (Integer.parseInt(getHfinmn())==(Integer.parseInt(getHdebmn()))){
						aTransaction.declarerErreur("Attention.L'heure de début est égale à l'heure de fin pour la même date.");
					}
				}
			}
		}
	// controle que l'affectation n'existe pas déjà
	if(existePmAffecter_Agent(aTransaction,getPminv(),getMatricule(),getDdeb(),getHdeb())){
		aTransaction.declarerErreur("L'affectation de cet agent pour ce petit matériel est déjà enregistré pour cette date.");
		return false;
	}
	
	//On vérifie que le véhicule n'est pas déjà affecté 
	if (existePM_Affecter_AgentEntreDate(aTransaction,getPminv(), getDdeb(), getDfin())){
		aTransaction.declarerErreur("Des affectations existent déjà pour cette période.");
		return false;
	}
		
	
	//Creation du PM_Affecter_Agent
	return getMyPM_Affecter_AgentBroker().creerPM_Affecter_Agent(aTransaction);
}
public boolean existePmAffecter_Agent(nc.mairie.technique.Transaction aTransaction, String inv, String nomatr, String datedeb,String hdeb) throws Exception{
	PM_Affecter_Agent unPmAffecter_Agent = new PM_Affecter_Agent();
	if(!Services.estUneDate(datedeb)){
		aTransaction.declarerErreur("La date de début n'est pas une date valide");
	}
	return unPmAffecter_Agent.getMyPM_Affecter_AgentBroker().existePmAffecter_Agent(aTransaction, inv,nomatr,datedeb,hdeb);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean modifierPM_Affecter_Agent(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PM_Affecter_Agent
	return getMyPM_Affecter_AgentBroker().modifierPM_Affecter_Agent(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean supprimerPM_Affecter_Agent(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PM_Affecter_Agent
	return getMyPM_Affecter_AgentBroker().supprimerPM_Affecter_Agent(aTransaction);
}

public boolean affecter_agent(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,AgentInterface unAgent, PM_Affecter_Agent unPMAffecter_Agent,Service unService, String date,PM_Affecter_Agent unPmAffecterAgentPrec) throws Exception{
	//int heureFin;
//	on vérifie ques les paramètres ne sont pas null
	if (null == unPMateriel.getPminv()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	if (null == unService.getServi()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
	if (null == unAgent.getNomatr()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agent"));
		return false;
	}
	int controle = 0;
	//RG : controle des dates
	if((getDdeb()!=null)&&(!getDdeb().equals(""))){
		if(!Services.estUneDate(getDdeb())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDdeb(Services.formateDate(getDdeb()));
		}
	}else{
		aTransaction.declarerErreur("La date de début doit être renseignée.");
		return false;
	}
	if((getDfin()!=null)&&(!getDfin().equals(""))){
		if(!Services.estUneDate(getDfin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDfin(Services.formateDate(getDfin()));
		}
	}
	//RG : on controle les dates de fin et date de début par rapport à la date d'affectation au Service
	PM_Affecter_Sce unPmAffecter_Service = PM_Affecter_Sce.chercherListPmAffecter_ScePmSce(aTransaction,unPMateriel.getPminv(),unService.getServi());
	if(aTransaction.isErreur()){
		return false;
	}
	
//	 on controle par rapport à l'affectation précédente
	if(null != unPmAffecter_Service.getPminv()){
		if ((unPmAffecterAgentPrec.getDfin()!=null)&&(!unPmAffecterAgentPrec.getDfin().equals("")&&(getDdeb()!=null)&&(!getDdeb().equals("")))){
			controle = Services.compareDates(getDdeb(),unPmAffecterAgentPrec.getDfin());
			if (controle==-9999){
				return false;
			}
			if(controle==-1){
				aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de fin de l'affectation précédente."+"("+unPmAffecterAgentPrec.getDfin()+")");
				return false;
			}
			if(controle==0){
				// on controle par rapport aux heures
				if (!unPmAffecterAgentPrec.getHfin().equals("0")){
					if(Integer.parseInt(unPmAffecterAgentPrec.getHfin())>Integer.parseInt(getHdeb())){
						aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente"+"("+unPmAffecterAgentPrec.getHfin()+")");
						return false;
					}
					if(Integer.parseInt(unPmAffecterAgentPrec.getHfin())==Integer.parseInt(getHdeb())){
						if(Integer.parseInt(unPmAffecterAgentPrec.getHfinmn())>Integer.parseInt(getHdebmn())){
							aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente"+"("+unPmAffecterAgentPrec.getHfinmn()+")");
							return false;
						}
					}
				}else{
					aTransaction.declarerErreur("La date du début de l'affectation doit être supérieur à la date de fin de l'affectation précédente."+"("+unPmAffecterAgentPrec.getDfin()+")");
					return false;
				}
			}
		}
	}
	
	String debAffAgent = "".equals(getDdeb()) ? "01/01/0001" : getDdeb(); 
	String finAffAgent = "".equals(getDfin()) || "01/01/0001".equals(getDfin()) ? "31/12/9999" : getDfin(); 

	//Comparaison avec la date de fin de l'affectation au service
	if ((unPmAffecter_Service.getDfin()!=null)&&(!unPmAffecter_Service.getDfin().equals("01/01/0001"))&&(!unPmAffecter_Service.getDfin().equals(""))){
		// pour la date de début <= date de fin de l'affectation
		controle = Services.compareDates(debAffAgent,unPmAffecter_Service.getDfin());
		if (controle==-9999){
			aTransaction.declarerErreur("Un problème est survenu avec les dates.");
			return false;
		}else if (controle>0){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service."+unPmAffecter_Service.getDdebut()+"->"+unPmAffecter_Service.getDfin());
			return false;
		}
		// pour la date de fin <= date de fin de l'affectation au service
		controle = Services.compareDates(finAffAgent,unPmAffecter_Service.getDfin());
		if (controle==-9999){
			return false;
		}else if (controle>0){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service."+unPmAffecter_Service.getDdebut()+"->"+unPmAffecter_Service.getDfin());
			return false;
		}
	}
	
	//Comparaison avec la date de début de l'affectation au service
	if ((unPmAffecter_Service.getDdebut()!=null)&&(!unPmAffecter_Service.getDdebut().equals("01/01/0001"))){
		// pour la date de début de l'affectation < date DEBUT
		controle = Services.compareDates(debAffAgent,unPmAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle<0){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service."+unPmAffecter_Service.getDdebut()+"->"+unPmAffecter_Service.getDfin());
			return false;
		}
		// pour la date de fin <= date de début de l'affectation au service
		controle = Services.compareDates(finAffAgent,unPmAffecter_Service.getDdebut());


		if (controle<0){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service."+unPmAffecter_Service.getDdebut()+"->"+unPmAffecter_Service.getDfin());
			return false;
		}
	}
	
//	 on vérifie que la fin de l'affectation précédente à été renseignée
	if (null != unPMAffecter_Agent.getPminv()){
		if ("".equals(unPMAffecter_Agent.getDfin())){
			aTransaction.declarerErreur("La fin de l'affectation précédente n'est pas enregistrée.");
			return false;
		}else{
			if ("00".equals(unPMAffecter_Agent.getHfin())||("".equals(unPMAffecter_Agent.getHfinmn()))){
				aTransaction.declarerErreur("L'heure de fin d'affectation précédente doit être renseignée.");
				return false;
			}
		}
	}
	
	// ajout de l'affectation
	creerPm_Affecter_Agent(aTransaction,unPMateriel,unAgent,date);
	return true;
}

//on modifie une affectation
public boolean affecter_agentModif(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,AgentInterface unAgent, PM_Affecter_Agent unPmAffecter_Agent,Service unService,PM_Affecter_Agent unPmAffecter_AgentPrec) throws Exception{
	int controle = 0;
//	RG : controle des dates
	if((getDdeb()!=null)&&(!getDdeb().equals(""))){
		if(!Services.estUneDate(getDdeb())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDdeb(Services.formateDate(getDdeb()));
		}
	}
	if((getDfin()!=null)&&(!getDfin().equals(""))){
		if(!Services.estUneDate(getDfin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDfin(Services.formateDate(getDfin()));
		}
		
	}
//	on vérifie ques les paramètres ne sont pas null
	if (null == unPMateriel.getPminv()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	if (null == unService.getServi()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
	if (null == unAgent.getNomatr()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agent"));
		return false;
	}
	
	// on controle par rapport à l'affectation précédente
	if(null!=unPmAffecter_AgentPrec){
		if(null != unPmAffecter_AgentPrec.getPminv()){
			controle = Services.compareDates(getDdeb(),unPmAffecter_AgentPrec.getDfin());
			if (controle==-9999){
				return false;
			}
			if(controle==-1){
				aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de fin de l'affectation précédente."+"("+unPmAffecter_AgentPrec.getDfin()+")");
				return false;
			}
			if(controle==0){
				// on controle par rapport aux minutes
				if(Integer.parseInt(unPmAffecter_AgentPrec.getHfin())>Integer.parseInt(getHdeb())){
					aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente"+"("+unPmAffecter_AgentPrec.getHfin()+")");
					return false;
				}
				if(Integer.parseInt(unPmAffecter_AgentPrec.getHfin())==Integer.parseInt(getHdeb())){
					if(Integer.parseInt(unPmAffecter_AgentPrec.getHfinmn())>Integer.parseInt(getHdebmn())){
						aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente"+"("+unPmAffecter_AgentPrec.getHfinmn()+")");
						return false;
					}
				}
			}
			
		}else{
			// si null c'est fonctionnellement normal : il n'y a pas de BPC précédent;
			aTransaction.traiterErreur();
		}
	}else{
		aTransaction.traiterErreur();
	}
	
	
	//on controle les dates de fin et date de début par rapport à la date d'affectation au Service
	PM_Affecter_Sce unPmAffecter_Service = PM_Affecter_Sce.chercherListPmAffecter_ScePmSce(aTransaction,unPMateriel.getPminv(),unService.getServi());
	if(aTransaction.isErreur()){
		return false;
	}
	
	String debAffAgent = "".equals(getDdeb()) ? "01/01/0001" : getDdeb(); 
	String finAffAgent = "".equals(getDfin()) || "01/01/0001".equals(getDfin()) ? "31/12/9999" : getDfin(); 
	
	//Comparaison avec la date de fin de l'affectation au service
	if ((unPmAffecter_Service.getDfin()!=null)&&(!unPmAffecter_Service.getDfin().equals("01/01/0001"))&&(!unPmAffecter_Service.getDfin().equals(""))){
		// pour la date de début <= date de fin de l'affectation
		controle = Services.compareDates(debAffAgent,unPmAffecter_Service.getDfin());
		if (controle==-9999){
			aTransaction.declarerErreur("Un problème est survenu avec les dates.");
			return false;
		}else if (controle>0){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service."+unPmAffecter_Service.getDdebut()+"->"+unPmAffecter_Service.getDfin());
			return false;
		}
		// pour la date de fin <= date de fin de l'affectation au service
		controle = Services.compareDates(finAffAgent,unPmAffecter_Service.getDfin());
		if (controle==-9999){
			return false;
		}else if (controle>0){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service."+unPmAffecter_Service.getDdebut()+"->"+unPmAffecter_Service.getDfin());
			return false;
		}
	}
//	Comparaison avec la date de début de l'affectation au service
	if ((unPmAffecter_Service.getDdebut()!=null)&&(!unPmAffecter_Service.getDdebut().equals("01/01/0001"))){
		// pour la date de début de l'affectation < date DEBUT
		controle = Services.compareDates(debAffAgent,unPmAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle<0){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service."+unPmAffecter_Service.getDdebut()+"->"+unPmAffecter_Service.getDfin());
			return false;
		}
		// pour la date de fin >= date de début de l'affectation au service
		controle = Services.compareDates(finAffAgent,unPmAffecter_Service.getDdebut());
		if (controle<0){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service."+unPmAffecter_Service.getDdebut()+"->"+unPmAffecter_Service.getDfin());
			return false;
		}
	}
	
	// modifie l'affectation
	modifierPM_Affecter_Agent(aTransaction);
	return true;
}


public static ArrayList<PM_Affecter_Agent> chercherListPmAffecter_AgentPM(nc.mairie.technique.Transaction aTransaction, String pminv) throws Exception{
	PM_Affecter_Agent unPM_Affecter_Agent = new PM_Affecter_Agent();
	return unPM_Affecter_Agent.getMyPM_Affecter_AgentBroker().chercherListerPmAffecter_AgentPM(aTransaction, pminv);
}

public static ArrayList<PM_Affecter_Agent> chercherListPmAffecter_AgentEquipSceEnCours(nc.mairie.technique.Transaction aTransaction, String servi,String inv,String date) throws Exception{
	PM_Affecter_Agent unPM_Affecter_Agent = new PM_Affecter_Agent();
	String param = servi;
	if (param.trim().length()==4){
		if(param.substring(3,4).equals("0")){
			param=servi.substring(0,3);//OK
		}
	}
	return unPM_Affecter_Agent.getMyPM_Affecter_AgentBroker().chercherListerPmAffecter_AgentEquipSceEnCours(aTransaction, param,inv,date);
}

/**
 * Constructeur PM_Affecter_Agent.
 */
public PM_Affecter_Agent() {
	super();
}
/**
 * Getter de l'attribut matricule.
 * @return String
 */
public String getMatricule() {
	return matricule;
}
/**
 * Setter de l'attribut matricule.
 * @param newMatricule newMatricule
 */
public void setMatricule(String newMatricule) { 
	matricule = newMatricule;
}
/**
 * Getter de l'attribut pminv.
 * @return String
 */
public String getPminv() {
	return pminv;
}
/**
 * Setter de l'attribut pminv.
 * @param newPminv newPminv
 */
public void setPminv(String newPminv) { 
	pminv = newPminv;
}
/**
 * Getter de l'attribut ddeb.
 * @return String
 */
public String getDdeb() {
	return ddeb;
}
/**
 * Setter de l'attribut ddeb.
 * @param newDdeb newDdeb
 */
public void setDdeb(String newDdeb) { 
	ddeb = newDdeb;
}
/**
 * Getter de l'attribut dfin.
 * @return String
 */
public String getDfin() {
	return dfin;
}
/**
 * Setter de l'attribut dfin.
 * @param newDfin newDfin
 */
public void setDfin(String newDfin) { 
	dfin = newDfin;
}
/**
 * Getter de l'attribut hdeb.
 * @return String
 */
public String getHdeb() {
	return hdeb;
}
/**
 * Setter de l'attribut hdeb.
 * @param newHdeb newHdeb
 */
public void setHdeb(String newHdeb) { 
	hdeb = newHdeb;
}
/**
 * Getter de l'attribut hfin.
 * @return String
 */
public String getHfin() {
	return hfin;
}
/**
 * Setter de l'attribut hfin.
 * @param newHfin newHfin
 */
public void setHfin(String newHfin) { 
	hfin = newHfin;
}
/**
 * Getter de l'attribut hdebmn.
 * @return String
 */
public String getHdebmn() {
	return hdebmn;
}
/**
 * Setter de l'attribut hdebmn.
 * @param newHdebmn newHdebmn
 */
public void setHdebmn(String newHdebmn) { 
	hdebmn = newHdebmn;
}
/**
 * Getter de l'attribut hfinmn.
 * @return String
 */
public String getHfinmn() {
	return hfinmn;
}
/**
 * Setter de l'attribut hfinmn.
 * @param newHfinmn newHfinmn
 */
public void setHfinmn(String newHfinmn) { 
	hfinmn = newHfinmn;
}
/**
 * Getter de l'attribut codesce.
 * @return String
 */
public String getCodesce() {
	return codesce;
}
/**
 * Setter de l'attribut codesce.
 * @param newCodesce newCodesce
 */
public void setCodesce(String newCodesce) { 
	codesce = newCodesce;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected BasicBroker definirMyBroker() { 
	return new PM_Affecter_AgentBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PM_Affecter_AgentBroker getMyPM_Affecter_AgentBroker() {
	return (PM_Affecter_AgentBroker)getMyBasicBroker();
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 * @param aTransaction Transaction
 * @param inv inv
 * @param datedeb datedeb
 * @throws Exception Exception
 */
public static boolean existePM_Affecter_AgentAvantDate(Transaction aTransaction, String inv,String datedeb) throws Exception{
	PM_Affecter_Agent unPM_Affecter_Agent = new PM_Affecter_Agent();
	if(!Services.estUneDate(datedeb)){
		aTransaction.declarerErreur("La date de début n'est pas une date valide");
	}
	return unPM_Affecter_Agent.getMyPM_Affecter_AgentBroker().existePM_Affecter_AgentAvantDate(aTransaction, inv,datedeb);
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 * @param aTransaction Transaction
 * @param inv inv
 * @param datedeb datedeb
 * @param datefin datefin
 * @throws Exception Exception
 */
public static boolean existePM_Affecter_AgentEntreDate(Transaction aTransaction, String inv,String datedeb, String datefin) throws Exception{
	PM_Affecter_Agent unPM_Affecter_Agent = new PM_Affecter_Agent();
	if(!Services.estUneDate(datedeb)){
		aTransaction.declarerErreur("La date de début n'est pas une date valide");
	}
	if(datefin != null && !"".equals(datefin) && !Services.estUneDate(datefin)){
		aTransaction.declarerErreur("La date de fin n'est pas une date valide");
	}
	return unPM_Affecter_Agent.getMyPM_Affecter_AgentBroker().existePM_Affecter_AgentEntreDate(aTransaction, inv,datedeb, datefin);
}
}
