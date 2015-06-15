package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;
import nc.mairie.technique.Transaction;

/**
 * Objet métier PM_Affecter_Sce
 */
public class PM_Affecter_Sce extends BasicMetier {
	public String siserv;
	public String pminv;
	public String ddebut;
	public String dfin;
	public String nomatr;
/**
* Renvoie une chaîne correspondant à la valeur de cet objet.
* @return une représentation sous forme de chaîne du destinataire
*/
@Override
public String toString() {
	// Insérez ici le code pour finaliser le destinataire
	// Cette implémentation transmet le message au super. Vous pouvez remplacer ou compléter le message.
	return super.toString();
}
/**
 * Retourne un ArrayList d'objet métier : PM_Affecter_Sce.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PM_Affecter_Sce> listerPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PM_Affecter_Sce unPM_Affecter_Sce = new PM_Affecter_Sce();
	return unPM_Affecter_Sce.getMyPM_Affecter_SceBroker().listerPM_Affecter_Sce(aTransaction);
}
/**
 * Retourne un PM_Affecter_Sce.
 * @param aTransaction Transaction
 * @param inv inv
 * @param servi servi
 * @param date date
 * @param dfin dfin
 * @return PM_Affecter_Sce
 * @throws Exception Exception
 */
public static PM_Affecter_Sce chercherPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction, String inv,String servi,String date,String dfin) throws Exception{
	if(dfin.equals("")){
		dfin = "0001-01-01";
	}
	PM_Affecter_Sce unPM_Affecter_Sce = new PM_Affecter_Sce();
	return unPM_Affecter_Sce.getMyPM_Affecter_SceBroker().chercherPM_Affecter_Sce(aTransaction, inv,servi,date,dfin);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @param unPMateriel unPMateriel
 * @param unService unService
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel, Service unService )  throws Exception {
//	on vérifie ques les paramètres ne sont pas null
	if (null == unPMateriel){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit Matériel"));
		return false;
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
//	Affectation des liens
	setPminv(unPMateriel.getPminv());
	setSiserv(unService.getServi());
	
	//on controle que la date soit supérieur à la date de mise en circulation
	setDdebut(Services.formateDate(getDdebut()));
	int controle = Services.compareDates(getDdebut(),unPMateriel.getDmes());
	if (controle==-1){
		aTransaction.declarerErreur("La date doit être supérieur à la date de mise en circulation ("+unPMateriel.getDmes()+")");
		return false;
	}else if(controle==-9999){
		return false;
	}
	//Creation du PM_Affecter_Sce
	return getMyPM_Affecter_SceBroker().creerPM_Affecter_Sce(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @param unPMateriel unPMateriel
 * @return boolean
 * @throws Exception Exception
 */
public boolean modifierPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel) throws Exception {
	if (null==unPMateriel){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}

	//on controle que la date soit supérieur à la date de mise en circulation et différent de 0001-01-01
	setDdebut(Services.formateDate(getDdebut()));
	int controle = Services.compareDates(getDdebut(),unPMateriel.getDmes());
	if (controle==-1){
		aTransaction.declarerErreur("La date doit être supérieur à la date de mise en circulation ("+unPMateriel.getDmes()+")");
		return false;
	}else if(controle==-9999){
		return false;
	}
	// Modification du PM_Affecter_Sce
	return getMyPM_Affecter_SceBroker().modifierPM_Affecter_Sce(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean supprimerPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PM_Affecter_Sce
	return getMyPM_Affecter_SceBroker().supprimerPM_Affecter_Sce(aTransaction);
}

//si suppression alors datefin de l'affectation précédent à blanc
public boolean affecter_serviceSupp(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,PM_Affecter_Sce unPmAffecter_Service) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unPmAffecter_Service.getPminv()){
		//aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","une affectation à un service"));
		//return false;
	}

	//#15962 Empecher une affectation d'un petit matériel à un service (C U D) si affectation ponctuelle à un agent en cours.
	if (PM_Affecter_Agent.existePM_Affecter_AgentAvantDate(aTransaction, unPMateriel.getPminv() , getDdebut())) {
		aTransaction.declarerErreur("Erreur : Ce petit matériel a une affectation ponctuelle à un agent. Suppression impossible");
		return false;
	}
	
//	 modification de l'affectation précédente
	if (null != unPmAffecter_Service.getPminv()){
		//on met à jour la date de fin de l'affectation précédente
		unPmAffecter_Service.modifierPM_Affecter_Sce(aTransaction,unPMateriel);
		if (aTransaction.isErreur()){
			return false;
		}
	}
	// suppression de l'affectation
	supprimerPM_Affecter_Sce(aTransaction);
	return true;
}

public boolean affecter_service(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,Service unService, PM_Affecter_Sce unPM_Affecter_Sce,String date) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unPMateriel.getPminv()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit Matériel"));
		return false;
	}
	if (null == unService.getServi()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
	if (null == unPM_Affecter_Sce.getPminv()){
		//aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Affecter_Agent"));
		//return false;
	}
//	 modification de l'affectation précédente
	if (null != unPM_Affecter_Sce.getPminv()){
		//on met à jour la date de fin de l'affectation précédente
		unPM_Affecter_Sce.modifierPM_Affecter_Sce(aTransaction,unPMateriel);
		if (aTransaction.isErreur()){
			return false;
		}
	}
	// on teste la date début >= date fin de l'affectation précédente
	if (null != unPM_Affecter_Sce.getPminv()){
		setDdebut(Services.formateDate(getDdebut()));
		int controle = Services.compareDates(getDdebut(),unPM_Affecter_Sce.getDdebut());
		if (controle==-1){
			aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de début de l'affectation précédente. ("+unPM_Affecter_Sce.getDdebut()+")");
			return false;
		}else if(controle==-9999){
			return false;
		}
	}
	
	//#15962 Empecher une affectation d'un petit matériel à un service (C U D) si affectation ponctuelle à un agent en cours.
	if (null != unPM_Affecter_Sce.getPminv()) {
		if (PM_Affecter_Agent.existePM_Affecter_AgentAvantDate(aTransaction, unPMateriel.getPminv() , Services.ajouteJours(getDdebut(),-1))) {
			aTransaction.declarerErreur("Erreur : Ce petit matériel a une affectation ponctuelle à un agent. Modifiez la date ou l'a date de l'affectation ponctuelle.");
			return false;
		}
	}

		
	// ajout de l'affectation
	creerPM_Affecter_Sce(aTransaction,unPMateriel,unService);
	if(aTransaction.isErreur()){
		return false;
	}
	return true;
}

//quand on modifie une affectation on doit modifier l'affectation précédente
public boolean affecter_serviceModif(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel, PM_Affecter_Sce unPM_Affecter_Sce, PM_Affecter_Sce unPM_Affecter_Sce2,String date) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unPMateriel.getPminv()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	if (null == unPM_Affecter_Sce.getPminv()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","une affectation à un service"));
		return false;
	}
	

	//#15962 Empecher une affectation d'un petit matériel à un service (C U D) si affectation ponctuelle à un agent en cours.
	//recherche du dernier AffecterService
	PM_Affecter_Sce dernierAffSvc = PM_Affecter_Sce.chercherDernierPM_Affecter_Sce(aTransaction, unPMateriel.getPminv());
	if (aTransaction.isErreur()) {
		aTransaction.traiterErreur();
	} else {
		
		//si changement date deb ou service
		if ( ! (getSiserv().equals(dernierAffSvc.getSiserv()) && getDdebut().equals(dernierAffSvc.getDdebut()))) {	
			
			//si on ne change pas de service
			if (getSiserv().equals(dernierAffSvc.getSiserv())) {
				//on vérifi que pour l'ancien service il n'y avait pas d'affectation
				if (PM_Affecter_Agent.existePM_Affecter_AgentEntreDate(aTransaction, unPMateriel.getPminv() , dernierAffSvc.getDdebut(), Services.ajouteJours(getDdebut(),-1))){
					aTransaction.declarerErreur("Erreur : Ce petit matériel a une affectation ponctuelle à un agent entre "+dernierAffSvc.getDdebut()+" et "+ getDdebut()+". Modification impossible");
					return false;
				}
			} else {
				//on vérifi que pour l'ancien service il n'y avait pas d'affectation
				if (PM_Affecter_Agent.existePM_Affecter_AgentAvantDate(aTransaction, unPMateriel.getPminv() , dernierAffSvc.getDdebut())) {
					aTransaction.declarerErreur("Erreur : Ce petit matériel a une affectation ponctuelle à un agent avant "+dernierAffSvc.getDdebut()+". Modification impossible");
					return false;
				}
			}
		}
	}

	
//	 modification de l'affectation précédente
	if (null != unPM_Affecter_Sce2.getPminv()){
		//on met à jour la date de fin de l'affectation précédente
		unPM_Affecter_Sce2.modifierPM_Affecter_Sce(aTransaction,unPMateriel);
		if (aTransaction.isErreur()){
			return false;
		}
	}
	// on teste la date début >= date fin de l'affectation précédente
	if (null != unPM_Affecter_Sce2.getPminv()){
		setDdebut(Services.formateDate(getDdebut()));
		int controle = Services.compareDates(unPM_Affecter_Sce2.getDfin(),getDdebut());
		if (controle==-1){
			aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de début de l'affectation précédente. ("+unPM_Affecter_Sce2.getDdebut()+")");
			return false;
		}else if(controle==-9999){
			return false;
		}
	}
	
	
	// modif de l'affectation
	modifierPM_Affecter_Sce(aTransaction,unPMateriel);
	if(aTransaction.isErreur()){
		return false;
	}
	return true;
}

public static PM_Affecter_Sce chercherListPmAffecter_ScePmSce(nc.mairie.technique.Transaction aTransaction, String inv,String servi) throws Exception{
	PM_Affecter_Sce unPM_Affecter_Sce = new PM_Affecter_Sce();
	return unPM_Affecter_Sce.getMyPM_Affecter_SceBroker().chercherListerPmAffecter_ServicePmSce(aTransaction, inv,servi);
}

public static ArrayList<PM_Affecter_Sce> chercherListPmAffecter_ScePm(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception{
	PM_Affecter_Sce unPM_Affecter_Sce = new PM_Affecter_Sce();
	return unPM_Affecter_Sce.getMyPM_Affecter_SceBroker().chercherListerPmAffecter_ServicePm(aTransaction, inv);
}

/**
 * Constructeur PM_Affecter_Sce.
 */
public PM_Affecter_Sce() {
	super();
}
/**
 * Getter de l'attribut siserv.
 * @return String
 */
public String getSiserv() {
	return siserv;
}
/**
 * Setter de l'attribut siserv.
 * @param newSiserv newSiserv
 */
public void setSiserv(String newSiserv) { 
	siserv = newSiserv;
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
 * Getter de l'attribut ddebut.
 * @return String
 */
public String getDdebut() {
	return ddebut;
}
/**
 * Setter de l'attribut ddebut.
 * @param newDdebut newDdebut
 */
public void setDdebut(String newDdebut) { 
	ddebut = newDdebut;
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
 * Getter de l'attribut nomatr.
 * @return String
 */
public String getNomatr() {
	return nomatr;
}
/**
 * Setter de l'attribut nomatr.
 * @param newNomatr newNomatr
 */
public void setNomatr(String newNomatr) { 
	nomatr = newNomatr;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PM_Affecter_SceBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PM_Affecter_SceBroker getMyPM_Affecter_SceBroker() {
	return (PM_Affecter_SceBroker)getMyBasicBroker();
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
	if(!Services.estUneDate(datefin)){
		aTransaction.declarerErreur("La date de fin n'est pas une date valide");
	}
	return unPM_Affecter_Agent.getMyPM_Affecter_AgentBroker().existePM_Affecter_AgentEntreDate(aTransaction, inv,datedeb, datefin);
}

/**
 * Retourne un PM_Affecter_Svc.
 * @param aTransaction Transaction
 * @param inv inv
 * @return PM_Affecter_Sce
 * @throws Exception Exception
 */
public static PM_Affecter_Sce chercherDernierPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception{
	PM_Affecter_Sce unPM_Affecter_Sce = new PM_Affecter_Sce();
	return unPM_Affecter_Sce.getMyPM_Affecter_SceBroker().chercherDernierPM_Affecter_Sce(aTransaction, inv);
}


}
