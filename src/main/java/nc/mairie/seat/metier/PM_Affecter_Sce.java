package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.Services;
/**
 * Objet métier PM_Affecter_Sce
 */
public class PM_Affecter_Sce extends nc.mairie.technique.BasicMetier {
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
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PM_Affecter_Sce unPM_Affecter_Sce = new PM_Affecter_Sce();
	return unPM_Affecter_Sce.getMyPM_Affecter_SceBroker().listerPM_Affecter_Sce(aTransaction);
}
/**
 * Retourne un PM_Affecter_Sce.
 * @return PM_Affecter_Sce
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
 */
public boolean supprimerPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PM_Affecter_Sce
	return getMyPM_Affecter_SceBroker().supprimerPM_Affecter_Sce(aTransaction);
}

//si suppression alors datefin de l'affectation précédent à blanc
public boolean pmAffecter_serviceSupp(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,PM_Affecter_Sce unPmAffecter_Service) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unPmAffecter_Service.getPminv()){
		//aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","une affectation à un service"));
		//return false;
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
	/*if (null == unAffecter_Service2.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","une affectation à un service"));
		return false;
	}*/
	// on teste si des agents ont été affectés à cette affectation
	ArrayList uneListAgent = PM_AffectAgentsInfos.chercherPM_AffectAgentsInfosScePM(aTransaction,unPM_Affecter_Sce.getPminv(),unPM_Affecter_Sce.getSiserv());
	if (uneListAgent.size()>0){
		PM_AffectAgentsInfos unAAI = (PM_AffectAgentsInfos)uneListAgent.get(0);
		if(!unAAI.getCodesce().substring(0,3).equals(getSiserv().substring(0,3))){
			aTransaction.declarerErreur("Modification impossible.Des agents ont été affectés à ce petit matériel, vous devez d'abord supprimer les affectations des agents.");
			return false;
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
		int controle = Services.compareDates(unPM_Affecter_Sce2.getDdebut(),getDdebut());
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

//si suppression alors datefin de l'affectation précédent à blanc
public boolean affecter_serviceSupp(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,PM_Affecter_Sce unPM_Affecter_Sce) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unPM_Affecter_Sce.getPminv()){
		//aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","une affectation à un service"));
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
	// suppression de l'affectation
	supprimerPM_Affecter_Sce(aTransaction);
	return true;
}

public static PM_Affecter_Sce chercherListPmAffecter_ScePmSce(nc.mairie.technique.Transaction aTransaction, String inv,String servi) throws Exception{
	PM_Affecter_Sce unPM_Affecter_Sce = new PM_Affecter_Sce();
	return unPM_Affecter_Sce.getMyPM_Affecter_SceBroker().chercherListerPmAffecter_ServicePmSce(aTransaction, inv,servi);
}

public static ArrayList chercherListPmAffecter_ScePm(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception{
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
 */
public String getSiserv() {
	return siserv;
}
/**
 * Setter de l'attribut siserv.
 */
public void setSiserv(String newSiserv) { 
	siserv = newSiserv;
}
/**
 * Getter de l'attribut pminv.
 */
public String getPminv() {
	return pminv;
}
/**
 * Setter de l'attribut pminv.
 */
public void setPminv(String newPminv) { 
	pminv = newPminv;
}
/**
 * Getter de l'attribut ddebut.
 */
public String getDdebut() {
	return ddebut;
}
/**
 * Setter de l'attribut ddebut.
 */
public void setDdebut(String newDdebut) { 
	ddebut = newDdebut;
}
/**
 * Getter de l'attribut dfin.
 */
public String getDfin() {
	return dfin;
}
/**
 * Setter de l'attribut dfin.
 */
public void setDfin(String newDfin) { 
	dfin = newDfin;
}
/**
 * Getter de l'attribut nomatr.
 */
public String getNomatr() {
	return nomatr;
}
/**
 * Setter de l'attribut nomatr.
 */
public void setNomatr(String newNomatr) { 
	nomatr = newNomatr;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PM_Affecter_SceBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected PM_Affecter_SceBroker getMyPM_Affecter_SceBroker() {
	return (PM_Affecter_SceBroker)getMyBasicBroker();
}
}
