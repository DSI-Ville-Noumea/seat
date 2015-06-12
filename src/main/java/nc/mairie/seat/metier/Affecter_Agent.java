package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;
import nc.mairie.technique.Transaction;

/**
 * Objet métier Affecter_Agent
 */
public class Affecter_Agent extends BasicMetier {
	public String matricule;
	public String numeroinventaire;
	public String datedebut;
	public String datefin;
	public String hdeb;
	public String hfin;
	public String hdebmn;
	public String hfinmn;
	public String numeroimmatriculation;
	public String datemiseencirculation;
	public String nom;
	public String prenom;
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
 * Retourne un ArrayList d'objet métier : Affecter_Agent.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public static ArrayList<Affecter_Agent> listerAffecter_Agent(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Affecter_Agent unAffecter_Agent = new Affecter_Agent();
	return unAffecter_Agent.getMyAffecter_AgentBroker().listerAffecter_Agent(aTransaction);
}
/**
 * Retourne un Affecter_Agent.
 * @return Affecter_Agent
 * @param aTransaction Transaction
 * @param inv inv
 * @param matr matr
 * @param date date
 * @throws Exception Exception
 */
public static Affecter_Agent chercherAffecter_Agent(nc.mairie.technique.Transaction aTransaction, String inv,String matr,String date) throws Exception{
	Affecter_Agent unAffecter_Agent = new Affecter_Agent();
	return unAffecter_Agent.getMyAffecter_AgentBroker().chercherAffecter_Agent(aTransaction, inv,matr,date);
}

/**
 * Retourne un Affecter_Agent.
 * @return Affecter_Agent
 * @param aTransaction Transaction
 * @param code code
 * @throws Exception Exception
 */
public static ArrayList<Affecter_Agent> chercherListAffecter_AgentEquip(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Affecter_Agent unAffecter_Agent = new Affecter_Agent();
	return unAffecter_Agent.getMyAffecter_AgentBroker().chercherListerAffecter_AgentEquip(aTransaction, code);
}

/**
 * Retourne un Affecter_Agent.
 * @return Affecter_Agent
 * @param aTransaction Transaction
 * @param servi servi
 * @throws Exception Exception
 */
public static ArrayList<Affecter_Agent> chercherListAffecter_AgentSce(nc.mairie.technique.Transaction aTransaction, String servi) throws Exception{
	Affecter_Agent unAffecter_Agent = new Affecter_Agent();
	String param = servi;
	if (param.trim().length()==4){
		if(param.substring(3,4).equals("0")){
			param=servi.substring(0,3);
		}
	}
	return unAffecter_Agent.getMyAffecter_AgentBroker().chercherListerAffecter_AgentEquip(aTransaction, param);
}

public static ArrayList<Affecter_Agent> chercherListAffecter_AgentEquipSce(nc.mairie.technique.Transaction aTransaction,String inv, String servi,String date) throws Exception{
	Affecter_Agent unAffecter_Agent = new Affecter_Agent();
	return unAffecter_Agent.getMyAffecter_AgentBroker().chercherListerAffecter_AgentEquipSce(aTransaction, inv, servi, date);
}

public static ArrayList<Affecter_Agent> chercherListAffecter_AgentEquipSceEnCours(nc.mairie.technique.Transaction aTransaction,String inv, String servi,String date) throws Exception{
	Affecter_Agent unAffecter_Agent = new Affecter_Agent();
	return unAffecter_Agent.getMyAffecter_AgentBroker().chercherListerAffecter_AgentEquipSceEnCours(aTransaction, inv, servi, date);
}


/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @param unEquipement unEquipement
 * @param unAgent unAgent
 * @param newDate newDate
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerAffecter_Agent(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Agents unAgent,String newDate)  throws Exception {
	//on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unAgent.getNomatr()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agent"));
		return false;
	}
	//RG : controle des dates
	if((getDatedebut()==null)||(getDatedebut().equals(""))){
		if(!Services.estUneDate(getDatedebut())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDatedebut(Services.formateDate(getDatedebut()));
		}
	}
	if((getDatefin()!=null)&&(!getDatefin().equals(""))){
		if(!Services.estUneDate(getDatefin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDatefin(Services.formateDate(getDatefin()));
		}
	}
//	on controle que la date soit supérieur ou égale à la date de début
	int controle = 0;
	if (null!=getDatefin()){
		controle = Services.compareDates(getDatefin(),getDatedebut());
		if (controle==-1){
			aTransaction.declarerErreur("La date de fin doit être supérieur ou égale à la date de début d'affectation ");
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
	setNumeroinventaire(unEquipement.getNumeroinventaire());
	setMatricule(unAgent.getNomatr());
	setDatedebut(newDate);
	//on controle que la date soit supérieur à la date de mise en circulation
	controle = Services.compareDates(getDatedebut(),unEquipement.getDatemiseencirculation());
	if (controle==-1){
		aTransaction.declarerErreur("La date doit être supérieur à la date de mise en circulation ("+unEquipement.getDatemiseencirculation()+")");
		return false;
	}else if(controle==-9999){
		return false;
	}
	//controle de la date de fin
	if ((!"01/01/0001".equals(getDatefin())&&(null!=getDatefin()))){
		controle = Services.compareDates(getDatefin(),getDatedebut());
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
	if(existeAffecter_Agent(aTransaction,getNumeroinventaire(),getMatricule(),getDatedebut(),getHdeb())){
		aTransaction.declarerErreur("L'affectation de cet agent pour cet équipement est déjà enregistré pour cette date.");
		return false;
	}
	//Creation du Affecter_Agent
	return getMyAffecter_AgentBroker().creerAffecter_Agent(aTransaction);
}

public boolean creerAffecter_AgentCCAS(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,AgentCCAS unAgent,String newDate)  throws Exception {
	//on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unAgent.getNomatr()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agent"));
		return false;
	}
	//RG : controle des dates
	if((getDatedebut()==null)||(getDatedebut().equals(""))){
		if(!Services.estUneDate(getDatedebut())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDatedebut(Services.formateDate(getDatedebut()));
		}
	}
	if((getDatefin()!=null)&&(!getDatefin().equals(""))){
		if(!Services.estUneDate(getDatefin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDatefin(Services.formateDate(getDatefin()));
		}
	}
//	on controle que la date soit supérieur ou égale à la date de début
	int controle = 0;
	if (null!=getDatefin()){
		controle = Services.compareDates(getDatefin(),getDatedebut());
		if (controle==-1){
			aTransaction.declarerErreur("La date doit être supérieur ou égale à la date de début d'affectation ");
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
	setNumeroinventaire(unEquipement.getNumeroinventaire());
	setMatricule(unAgent.getNomatr());
	setDatedebut(newDate);
	//on controle que la date soit supérieur à la date de mise en circulation
	controle = Services.compareDates(getDatedebut(),unEquipement.getDatemiseencirculation());
	if (controle==-1){
		aTransaction.declarerErreur("La date doit être supérieur à la date de mise en circulation ("+unEquipement.getDatemiseencirculation()+")");
		return false;
	}else if(controle==-9999){
		return false;
	}
	//controle de la date de fin
	if ((!"01/01/0001".equals(getDatefin())&&(null!=getDatefin()))){
		controle = Services.compareDates(getDatefin(),getDatedebut());
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
	if(existeAffecter_Agent(aTransaction,getNumeroinventaire(),getMatricule(),getDatedebut(),getHdeb())){
		aTransaction.declarerErreur("L'affectation de cet agent pour cet équipement est déjà enregistré pour cette date.");
		return false;
	}
	//Creation du Affecter_Agent
	return getMyAffecter_AgentBroker().creerAffecter_Agent(aTransaction);
}

public boolean creerAffecter_AgentCDE(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,AgentCDE unAgent,String newDate)  throws Exception {
	//on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unAgent.getNomatr()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agent"));
		return false;
	}
	//RG : controle des dates
	if((getDatedebut()==null)||(getDatedebut().equals(""))){
		if(!Services.estUneDate(getDatedebut())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDatedebut(Services.formateDate(getDatedebut()));
		}
	}
	if((getDatefin()!=null)&&(!getDatefin().equals(""))){
		if(!Services.estUneDate(getDatefin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDatefin(Services.formateDate(getDatefin()));
		}
	}
//	on controle que la date soit supérieur ou égale à la date de début
	int controle = 0;
	if (null!=getDatefin()){
		controle = Services.compareDates(getDatefin(),getDatedebut());
		if (controle==-1){
			aTransaction.declarerErreur("La date doit être supérieur ou égale à la date de début d'affectation ");
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
	setNumeroinventaire(unEquipement.getNumeroinventaire());
	setMatricule(unAgent.getNomatr());
	setDatedebut(newDate);
	//on controle que la date soit supérieur à la date de mise en circulation
	controle = Services.compareDates(getDatedebut(),unEquipement.getDatemiseencirculation());
	if (controle==-1){
		aTransaction.declarerErreur("La date doit être supérieur à la date de mise en circulation ("+unEquipement.getDatemiseencirculation()+")");
		return false;
	}else if(controle==-9999){
		return false;
	}
	//controle de la date de fin
	if ((!"01/01/0001".equals(getDatefin())&&(null!=getDatefin()))){
		controle = Services.compareDates(getDatefin(),getDatedebut());
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
	if(existeAffecter_Agent(aTransaction,getNumeroinventaire(),getMatricule(),getDatedebut(),getHdeb())){
		aTransaction.declarerErreur("L'affectation de cet agent pour cet équipement est déjà enregistré pour cette date.");
		return false;
	}
	//Creation du Affecter_Agent
	return getMyAffecter_AgentBroker().creerAffecter_Agent(aTransaction);
}

public boolean affecter_agentCCAS(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,AgentCCAS unAgent, Affecter_Agent unAffecter_Agent,Service unService, String date,Affecter_Agent unAffecterAgentPrec) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
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
	if((getDatedebut()!=null)&&(!getDatedebut().equals(""))){
		if(!Services.estUneDate(getDatedebut())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDatedebut(Services.formateDate(getDatedebut()));
		}
	}
	if((getDatefin()!=null)&&(!getDatefin().equals(""))){
		if(!Services.estUneDate(getDatefin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDatefin(Services.formateDate(getDatefin()));
		}
		
	}
	//RG : on controle les dates de fin et date de début par rapport à la date d'affectation au Service
	Affecter_Service unAffecter_Service = Affecter_Service.chercherListAffecter_ServiceEquipSce(aTransaction,unEquipement.getNumeroinventaire(),unService.getServi());
	if(aTransaction.isErreur()){
		return false;
	}
	
//	 on controle par rapport à l'affectation précédente
	if(null != unAffecterAgentPrec.getNumeroinventaire()){
		controle = Services.compareDates(getDatedebut(),unAffecterAgentPrec.getDatefin());
		if (controle==-9999){
			return false;
		}
		if(controle==-1){
			aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de fin de l'affectation précédente.");
			return false;
		}
		if(controle==0){
			// on controle par rapport aux heures
			if (!unAffecterAgentPrec.getHfin().equals("0")){
				if(Integer.parseInt(unAffecterAgentPrec.getHfin())>Integer.parseInt(getHdeb())){
					aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
					return false;
				}
				if(Integer.parseInt(unAffecterAgentPrec.getHfin())==Integer.parseInt(getHdeb())){
					if(Integer.parseInt(unAffecterAgentPrec.getHfinmn())>Integer.parseInt(getHdebmn())){
						aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
						return false;
					}
				}
			}else{
				aTransaction.declarerErreur("La date du début de l'affectation doit être supérieur à la date de fin de l'affectation précédente.");
				return false;
			}
		}
		
	}else{
		// si null c'est fonctionnellement normal : il n'y a pas de BPC précédent;
		aTransaction.traiterErreur();
	}
	
	//Comparaison avec la date de fin de l'affectation au service
	if ((unAffecter_Service.getDfin()!=null)&&(!unAffecter_Service.getDfin().equals("01/01/0001"))){
		// pour la date de début <= date de fin de l'affectation
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatedebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin <= date de fin de l'affectation au service
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatefin());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
//	Comparaison avec la date de début de l'affectation au service
	if ((unAffecter_Service.getDdebut()!=null)&&(!unAffecter_Service.getDdebut().equals("01/01/0001"))){
		// pour la date de début >= date de début de l'affectation
		controle = Services.compareDates(getDatedebut(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin >= date de début de l'affectation au service
		controle = Services.compareDates(getDatefin(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
	
//	 on vérifie que la fin de l'affectation précédente à été renseignée
	if (null != unAffecter_Agent.getNumeroinventaire()){
		if ("".equals(unAffecter_Agent.getDatefin())){
			aTransaction.declarerErreur("La fin de l'affectation précédente n'est pas enregistrée.");
			return false;
		}else{
			if ("00".equals(unAffecter_Agent.getHfin())||("".equals(unAffecter_Agent.getHfinmn()))){
				aTransaction.declarerErreur("L'heure de fin d'affectation précédente doit être renseignée.");
				return false;
			}
		}
	}
	
	// ajout de l'affectation
	creerAffecter_AgentCCAS(aTransaction,unEquipement,unAgent,date);
	return true;
}

public boolean affecter_agentCDE(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,AgentCDE unAgent, Affecter_Agent unAffecter_Agent,Service unService, String date,Affecter_Agent unAffecterAgentPrec) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
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
	if((getDatedebut()!=null)&&(!getDatedebut().equals(""))){
		if(!Services.estUneDate(getDatedebut())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDatedebut(Services.formateDate(getDatedebut()));
		}
	}
	if((getDatefin()!=null)&&(!getDatefin().equals(""))){
		if(!Services.estUneDate(getDatefin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDatefin(Services.formateDate(getDatefin()));
		}
		
	}
	//RG : on controle les dates de fin et date de début par rapport à la date d'affectation au Service
	Affecter_Service unAffecter_Service = Affecter_Service.chercherListAffecter_ServiceEquipSce(aTransaction,unEquipement.getNumeroinventaire(),unService.getServi());
	if(aTransaction.isErreur()){
		return false;
	}
	
//	 on controle par rapport à l'affectation précédente
	if(null != unAffecterAgentPrec.getNumeroinventaire()){
		controle = Services.compareDates(getDatedebut(),unAffecterAgentPrec.getDatefin());
		if (controle==-9999){
			return false;
		}
		if(controle==-1){
			aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de fin de l'affectation précédente.");
			return false;
		}
		if(controle==0){
			// on controle par rapport aux heures
			if (!unAffecterAgentPrec.getHfin().equals("0")){
				if(Integer.parseInt(unAffecterAgentPrec.getHfin())>Integer.parseInt(getHdeb())){
					aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
					return false;
				}
				if(Integer.parseInt(unAffecterAgentPrec.getHfin())==Integer.parseInt(getHdeb())){
					if(Integer.parseInt(unAffecterAgentPrec.getHfinmn())>Integer.parseInt(getHdebmn())){
						aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
						return false;
					}
				}
			}else{
				aTransaction.declarerErreur("La date du début de l'affectation doit être supérieur à la date de fin de l'affectation précédente.");
				return false;
			}
		}
		
	}else{
		// si null c'est fonctionnellement normal : il n'y a pas de BPC précédent;
		aTransaction.traiterErreur();
	}
	
	//Comparaison avec la date de fin de l'affectation au service
	if ((unAffecter_Service.getDfin()!=null)&&(!unAffecter_Service.getDfin().equals("01/01/0001"))){
		// pour la date de début <= date de fin de l'affectation
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatedebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin <= date de fin de l'affectation au service
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatefin());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
//	Comparaison avec la date de début de l'affectation au service
	if ((unAffecter_Service.getDdebut()!=null)&&(!unAffecter_Service.getDdebut().equals("01/01/0001"))){
		// pour la date de début >= date de début de l'affectation
		controle = Services.compareDates(getDatedebut(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin >= date de début de l'affectation au service
		controle = Services.compareDates(getDatefin(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
	
//	 on vérifie que la fin de l'affectation précédente à été renseignée
	if (null != unAffecter_Agent.getNumeroinventaire()){
		if ("".equals(unAffecter_Agent.getDatefin())){
			aTransaction.declarerErreur("La fin de l'affectation précédente n'est pas enregistrée.");
			return false;
		}else{
			if ("00".equals(unAffecter_Agent.getHfin())||("".equals(unAffecter_Agent.getHfinmn()))){
				aTransaction.declarerErreur("L'heure de fin d'affectation précédente doit être renseignée.");
				return false;
			}
		}
	}
	
	// ajout de l'affectation
	creerAffecter_AgentCDE(aTransaction,unEquipement,unAgent,date);
	return true;
}

/*affectation d'un nouvel agent
 * 
 * 
 */
public boolean affecter_agent(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Agents unAgent, Affecter_Agent unAffecter_Agent,Service unService, String date,Affecter_Agent unAffecterAgentPrec) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
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
	if((getDatedebut()!=null)&&(!getDatedebut().equals(""))){
		if(!Services.estUneDate(getDatedebut())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDatedebut(Services.formateDate(getDatedebut()));
		}
	}else{
		aTransaction.declarerErreur("La date de début doit être renseignée.");
		return false;
	}
	if((getDatefin()!=null)&&(!getDatefin().equals(""))){
		if(!Services.estUneDate(getDatefin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDatefin(Services.formateDate(getDatefin()));
		}
	}else{
		aTransaction.declarerErreur("La date de fin doit être renseignée.");
		return false;
	}
	//RG : on controle les dates de fin et date de début par rapport à la date d'affectation au Service
	Affecter_Service unAffecter_Service = Affecter_Service.chercherListAffecter_ServiceEquipSce(aTransaction,unEquipement.getNumeroinventaire(),unService.getServi());
	if(aTransaction.isErreur()){
		return false;
	}
	
//	 on controle par rapport à l'affectation précédente
	if(null != unAffecterAgentPrec.getNumeroinventaire()){
		controle = Services.compareDates(getDatedebut(),unAffecterAgentPrec.getDatefin());
		if (controle==-9999){
			return false;
		}
		if(controle==-1){
			aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de fin de l'affectation précédente.");
			return false;
		}
		if(controle==0){
			// on controle par rapport aux heures
			if (!unAffecterAgentPrec.getHfin().equals("0")){
				if(Integer.parseInt(unAffecterAgentPrec.getHfin())>Integer.parseInt(getHdeb())){
					aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
					return false;
				}
				if(Integer.parseInt(unAffecterAgentPrec.getHfin())==Integer.parseInt(getHdeb())){
					if(Integer.parseInt(unAffecterAgentPrec.getHfinmn())>Integer.parseInt(getHdebmn())){
						aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
						return false;
					}
				}
			}else{
				aTransaction.declarerErreur("La date du début de l'affectation doit être supérieur à la date de fin de l'affectation précédente.");
				return false;
			}
		}
		
	}else{
		// si null c'est fonctionnellement normal : il n'y a pas de BPC précédent;
		aTransaction.traiterErreur();
	}
	
	//Comparaison avec la date de fin de l'affectation au service
	if ((unAffecter_Service.getDfin()!=null)&&(!unAffecter_Service.getDfin().equals("01/01/0001"))&&(!unAffecter_Service.getDfin().equals(""))){
		// pour la date de début <= date de fin de l'affectation
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatedebut());
		if (controle==-9999){
			//aTransaction.declarerErreur("Un problème est survenu pour la date de fin.");
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin <= date de fin de l'affectation au service
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatefin());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
//	Comparaison avec la date de début de l'affectation au service
	if ((unAffecter_Service.getDdebut()!=null)&&(!unAffecter_Service.getDdebut().equals("01/01/0001"))){
		// pour la date de début >= date de début de l'affectation
		controle = Services.compareDates(getDatedebut(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin >= date de début de l'affectation au service
		controle = Services.compareDates(getDatefin(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
	
//	 on vérifie que la fin de l'affectation précédente à été renseignée
	if (null != unAffecter_Agent.getNumeroinventaire()){
		if ("".equals(unAffecter_Agent.getDatefin())){
			aTransaction.declarerErreur("La fin de l'affectation précédente n'est pas enregistrée.");
			return false;
		}else{
			if ("00".equals(unAffecter_Agent.getHfin())||("".equals(unAffecter_Agent.getHfinmn()))){
				aTransaction.declarerErreur("L'heure de fin d'affectation précédente doit être renseignée.");
				return false;
			}
		}
	}
	
	// ajout de l'affectation
	creerAffecter_Agent(aTransaction,unEquipement,unAgent,date);
	return true;
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean modifierAffecter_Agent(nc.mairie.technique.Transaction aTransaction) throws Exception {
//	contrôle
	if ((Integer.parseInt(getHdeb())>24)||(Integer.parseInt(getHdebmn())>60)||(Integer.parseInt(getHdeb())<0)||(Integer.parseInt(getHdebmn())<0)){
		aTransaction.declarerErreur("L'heure de début est incorrecte");
		return false;
	}
	if ((Integer.parseInt(getHfin())>24)||(Integer.parseInt(getHfinmn())>60)||(Integer.parseInt(getHfin())<0)||(Integer.parseInt(getHfinmn())<0)){
		aTransaction.declarerErreur("L'heure de fin est incorrecte");
		return false;
	}
//	RG : controle des dates
	if((getDatedebut()!=null)&&(!getDatedebut().equals(""))){
		if(!Services.estUneDate(getDatedebut())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDatedebut(Services.formateDate(getDatedebut()));
		}
	}
	if((getDatefin()!=null)&&(!getDatefin().equals(""))){
		if(!Services.estUneDate(getDatefin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDatefin(Services.formateDate(getDatefin()));
		}
		
	}
	//on controle que la date soit supérieur ou égale à la date de début
	int controle = Services.compareDates(getDatefin(),getDatedebut());
	if (controle==-1){
		aTransaction.declarerErreur("La date de fin doit être supérieur ou égale à la date de début d'affectation ");
	}else if(controle==-9999){
		return false;
	}else if (controle==0){
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
	//Modification du Affecter_Agent
	return getMyAffecter_AgentBroker().modifierAffecter_Agent(aTransaction);
}

//on modifie une affectation
public boolean affecter_agentModif(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Agents unAgent, Affecter_Agent unAffecter_Agent,Service unService,Affecter_Agent unAffecter_AgentPrec) throws Exception{
	int controle = 0;
//	RG : controle des dates
	if((getDatedebut()!=null)&&(!getDatedebut().equals(""))){
		if(!Services.estUneDate(getDatedebut())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDatedebut(Services.formateDate(getDatedebut()));
		}
	}
	if((getDatefin()!=null)&&(!getDatefin().equals(""))){
		if(!Services.estUneDate(getDatefin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDatefin(Services.formateDate(getDatefin()));
		}
		
	}
//	on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
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
	if(null!=unAffecter_AgentPrec){
		if(null != unAffecter_AgentPrec.getNumeroinventaire()){
			controle = Services.compareDates(getDatedebut(),unAffecter_AgentPrec.getDatefin());
			if (controle==-9999){
				return false;
			}
			if(controle==-1){
				aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de fin de l'affectation précédente.");
				return false;
			}
			if(controle==0){
				// on controle par rapport aux minutes
				if(Integer.parseInt(unAffecter_AgentPrec.getHfin())>Integer.parseInt(getHdeb())){
					aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
					return false;
				}
				if(Integer.parseInt(unAffecter_AgentPrec.getHfin())==Integer.parseInt(getHdeb())){
					if(Integer.parseInt(unAffecter_AgentPrec.getHfinmn())>Integer.parseInt(getHdebmn())){
						aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
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
	Affecter_Service unAffecter_Service = Affecter_Service.chercherListAffecter_ServiceEquipSce(aTransaction,unEquipement.getNumeroinventaire(),unService.getServi());
	if(aTransaction.isErreur()){
		return false;
	}
	//Comparaison avec la date de fin de l'affectation au service
	if ((unAffecter_Service.getDfin()!=null)&&(!unAffecter_Service.getDfin().equals("01/01/0001"))&&(unAffecter_Agent.getDatefin().equals(""))){
		// pour la date de début <= date de fin de l'affectation
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatedebut());
		if (controle==-9999){
			aTransaction.declarerErreur("Un problème est survenu avec les dates.");
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin <= date de fin de l'affectation au service
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatefin());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
//	Comparaison avec la date de début de l'affectation au service
	if ((unAffecter_Service.getDdebut()!=null)&&(!unAffecter_Service.getDdebut().equals("01/01/0001"))){
		// pour la date de début >= date de début de l'affectation
		controle = Services.compareDates(getDatedebut(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin <= date de fin de l'affectation au service
		controle = Services.compareDates(getDatefin(),unAffecter_Service.getDfin());
		if (controle>0){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
	
//	 on vérifie que la fin de l'affectation précédente à été renseignée
	/*if (null != unAffecter_Agent.getNumeroinventaire()){
		if ("".equals(unAffecter_Agent.getDatefin())){
			aTransaction.declarerErreur("La fin de l'affectation précédente n'est pas enregistrée.");
			return false;
		}else{
			if ("00".equals(unAffecter_Agent.getHfin())||("".equals(unAffecter_Agent.getHfinmn()))){
				aTransaction.declarerErreur("L'heure de fin d'affectation précédente doit être renseignée.");
				return false;
			}
		}
	}*/
//	 controle que l'affectation n'existe pas déjà
	/*if(existeAffecter_Agent(aTransaction,getNumeroinventaire(),getMatricule(),getDatedebut(),getHdeb())){
		aTransaction.declarerErreur("L'affectation de cet agent pour cet équipement est déjà enregistré pour cette date.");
		return false;
	}*/
	// modifie l'affectation
	modifierAffecter_Agent(aTransaction);
	return true;
}

//on modifie une affectation
public boolean affecter_agentModifCDE(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,AgentCDE unAgent, Affecter_Agent unAffecter_Agent,Service unService,Affecter_Agent unAffecter_AgentPrec) throws Exception{
	int controle = 0;
//	RG : controle des dates
	if((getDatedebut()!=null)&&(!getDatedebut().equals(""))){
		if(!Services.estUneDate(getDatedebut())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDatedebut(Services.formateDate(getDatedebut()));
		}
	}
	if((getDatefin()!=null)&&(!getDatefin().equals(""))){
		if(!Services.estUneDate(getDatefin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDatefin(Services.formateDate(getDatefin()));
		}
		
	}
//	on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
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
	if(null!=unAffecter_AgentPrec){
		if(null != unAffecter_AgentPrec.getNumeroinventaire()){
			controle = Services.compareDates(unAffecter_AgentPrec.getDatefin(),getDatedebut());
			if (controle==-9999){
				return false;
			}
			if(controle==-1){
				aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de fin de l'affectation précédente.");
				return false;
			}
			if(controle==0){
				// on controle par rapport aux minutes
				if(Integer.parseInt(unAffecter_AgentPrec.getHfin())>Integer.parseInt(getHdeb())){
					aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
					return false;
				}
				if(Integer.parseInt(unAffecter_AgentPrec.getHfin())==Integer.parseInt(getHdeb())){
					if(Integer.parseInt(unAffecter_AgentPrec.getHfinmn())>Integer.parseInt(getHdebmn())){
						aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
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
	Affecter_Service unAffecter_Service = Affecter_Service.chercherListAffecter_ServiceEquipSce(aTransaction,unEquipement.getNumeroinventaire(),unService.getServi());
	if(aTransaction.isErreur()){
		return false;
	}
	//Comparaison avec la date de fin de l'affectation au service
	if ((unAffecter_Service.getDfin()!=null)&&(!unAffecter_Service.getDfin().equals("01/01/0001"))){
		// pour la date de début <= date de fin de l'affectation
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatedebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin <= date de fin de l'affectation au service
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatefin());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
//	Comparaison avec la date de début de l'affectation au service
	if ((unAffecter_Service.getDdebut()!=null)&&(!unAffecter_Service.getDdebut().equals("01/01/0001"))){
		// pour la date de début >= date de début de l'affectation
		controle = Services.compareDates(getDatedebut(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin >= date de début de l'affectation au service
		controle = Services.compareDates(getDatefin(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
	// modifie l'affectation
	modifierAffecter_Agent(aTransaction);
	return true;
}

//on modifie une affectation
public boolean affecter_agentModifCCAS(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,AgentCCAS unAgent, Affecter_Agent unAffecter_Agent,Service unService,Affecter_Agent unAffecter_AgentPrec) throws Exception{
	int controle = 0;
//	RG : controle des dates
	if((getDatedebut()!=null)&&(!getDatedebut().equals(""))){
		if(!Services.estUneDate(getDatedebut())){
			aTransaction.declarerErreur("La date de début n'est pas correcte.");
			return false;
		}else{
			setDatedebut(Services.formateDate(getDatedebut()));
		}
	}
	if((getDatefin()!=null)&&(!getDatefin().equals(""))){
		if(!Services.estUneDate(getDatefin())){
			aTransaction.declarerErreur("La date de fin n'est pas correcte.");
			return false;
		}else{
			setDatefin(Services.formateDate(getDatefin()));
		}
		
	}
//	on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
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
	if(null!=unAffecter_AgentPrec){
		if(null != unAffecter_AgentPrec.getNumeroinventaire()){
			controle = Services.compareDates(unAffecter_AgentPrec.getDatefin(),getDatedebut());
			if (controle==-9999){
				return false;
			}
			if(controle==-1){
				aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de fin de l'affectation précédente.");
				return false;
			}
			if(controle==0){
				// on controle par rapport aux minutes
				if(Integer.parseInt(unAffecter_AgentPrec.getHfin())>Integer.parseInt(getHdeb())){
					aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
					return false;
				}
				if(Integer.parseInt(unAffecter_AgentPrec.getHfin())==Integer.parseInt(getHdeb())){
					if(Integer.parseInt(unAffecter_AgentPrec.getHfinmn())>Integer.parseInt(getHdebmn())){
						aTransaction.declarerErreur("L'heure de début doit être supérieur ou égale à l'heure de fin de l'affectation précédente");
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
	Affecter_Service unAffecter_Service = Affecter_Service.chercherListAffecter_ServiceEquipSce(aTransaction,unEquipement.getNumeroinventaire(),unService.getServi());
	if(aTransaction.isErreur()){
		return false;
	}
	//Comparaison avec la date de fin de l'affectation au service
	if ((unAffecter_Service.getDfin()!=null)&&(!unAffecter_Service.getDfin().equals("01/01/0001"))){
		// pour la date de début <= date de fin de l'affectation
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatedebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin <= date de fin de l'affectation au service
		controle = Services.compareDates(unAffecter_Service.getDfin(),getDatefin());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
//	Comparaison avec la date de début de l'affectation au service
	if ((unAffecter_Service.getDdebut()!=null)&&(!unAffecter_Service.getDdebut().equals("01/01/0001"))){
		// pour la date de début >= date de début de l'affectation
		controle = Services.compareDates(getDatedebut(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de début ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
		// pour la date de fin >= date de début de l'affectation au service
		controle = Services.compareDates(getDatefin(),unAffecter_Service.getDdebut());
		if (controle==-9999){
			return false;
		}else if (controle==-1){
			aTransaction.declarerErreur("L'affectation est impossible car la date de fin ne correspond pas à l'intervalle d'affectation au service.");
			return false;
		}
	}
	// modifie l'affectation
	modifierAffecter_Agent(aTransaction);
	return true;
}

/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean supprimerAffecter_Agent(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'Affecter_Agent
	return getMyAffecter_AgentBroker().supprimerAffecter_Agent(aTransaction);
}
	
	
/**
 * Getter de l'attribut numeroimmatriculation.
 * @return numeroimmatriculation
 */
public String getNumeroimmatriculation() {
	return numeroimmatriculation;
}
/**
 * Setter de l'attribut numeroimmatriculation.
 * @param newNumeroimmatriculation newNumeroimmatriculation
 */
public void setNumeroimmatriculation(String newNumeroimmatriculation) { 
	numeroimmatriculation = newNumeroimmatriculation;
}
/**
 * Getter de l'attribut datemiseencirculation.
 * @return datemiseencirculation
 */
public String getDatemiseencirculation() {
	return datemiseencirculation;
}
/**
 * Setter de l'attribut datemiseencirculation.
 * @param newDatemiseencirculation newDatemiseencirculation
 */
public void setDatemiseencirculation(String newDatemiseencirculation) { 
	datemiseencirculation = newDatemiseencirculation;
}
/**
 * Getter de l'attribut nom.
 * @return nom
 */
public String getNom() {
	return nom;
}
/**
 * Setter de l'attribut nom.
 * @param newNom newNom
 */
public void setNom(String newNom) { 
	nom = newNom;
}
/**
 * Getter de l'attribut prenom.
 * @return prenom
 */
public String getPrenom() {
	return prenom;
}
/**
 * Setter de l'attribut prenom.
 * @param newPrenom newPrenom
 */
public void setPrenom(String newPrenom) { 
	prenom = newPrenom;
}
	/**
	 * 
	 */
	public String codeservice;

/**
 * Constructeur Affecter_Agent.
 * 
 */
public Affecter_Agent() {
	super();
}
/**
 * Getter de l'attribut matricule.
 * @return matricule
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
 * Getter de l'attribut numeroinventaire.
 * @return numeroinventaire
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 * @param newNumeroinventaire newNumeroinventaire
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
}
/**
 * Getter de l'attribut datedebut.
 * @return datedebut
 */
public String getDatedebut() {
	return datedebut;
}
/**
 * Setter de l'attribut datedebut.
 * @param newDatedebut newDatedebut
 */
public void setDatedebut(String newDatedebut) { 
	datedebut = newDatedebut;
}
/**
 * Getter de l'attribut datefin.
 * @return datefin
 */
public String getDatefin() {
	return datefin;
}
/**
 * Setter de l'attribut datefin.
 * @param newDatefin newDatefin
 */
public void setDatefin(String newDatefin) { 
	datefin = newDatefin;
}
/**
 * Getter de l'attribut hdeb.
 * @return hdeb
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
 * @return hfin
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
 * @return hdebmn
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
 * @return hfinmn
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
 * Getter de l'attribut codeservice.
 * @return codeservice
 */
public String getCodeservice() {
	return codeservice;
}
/**
 * Setter de l'attribut codeservice.
 * @param newCodeservice newCodeservice
 */
public void setCodeservice(String newCodeservice) { 
	codeservice = newCodeservice;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected BasicBroker definirMyBroker() { 
	return new Affecter_AgentBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected Affecter_AgentBroker getMyAffecter_AgentBroker() {
	return (Affecter_AgentBroker)getMyBasicBroker();
}
/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 * @param aTransaction Transaction
 * @param inv inv
 * @param nomatr nomatr
 * @param datedeb datedeb
 * @param hdeb hdeb
 * @throws Exception Exception
 */
public boolean existeAffecter_Agent(nc.mairie.technique.Transaction aTransaction, String inv, String nomatr, String datedeb,String hdeb) throws Exception{
	Affecter_Agent unAffecter_Agent = new Affecter_Agent();
	if(!Services.estUneDate(datedeb)){
		aTransaction.declarerErreur("La date de début n'est pas une date valide");
	}
	return unAffecter_Agent.getMyAffecter_AgentBroker().existeAffecter_Agent(aTransaction, inv,nomatr,datedeb,hdeb);
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
public static boolean existeAffecter_AgentAvantDate(Transaction aTransaction, String inv,String datedeb) throws Exception{
	Affecter_Agent unAffecter_Agent = new Affecter_Agent();
	if(!Services.estUneDate(datedeb)){
		aTransaction.declarerErreur("La date de début n'est pas une date valide");
	}
	return unAffecter_Agent.getMyAffecter_AgentBroker().existeAffecter_AgentAvantDate(aTransaction, inv,datedeb);
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
public static boolean existeAffecter_AgentEntreDate(Transaction aTransaction, String inv,String datedeb, String datefin) throws Exception{
	Affecter_Agent unAffecter_Agent = new Affecter_Agent();
	if(!Services.estUneDate(datedeb)){
		aTransaction.declarerErreur("La date de début n'est pas une date valide");
	}
	if(!Services.estUneDate(datefin)){
		aTransaction.declarerErreur("La date de fin n'est pas une date valide");
	}
	return unAffecter_Agent.getMyAffecter_AgentBroker().existeAffecter_AgentEntreDate(aTransaction, inv,datedeb, datefin);
}


}
