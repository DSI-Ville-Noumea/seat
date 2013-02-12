package nc.mairie.seat.metier;


import java.util.ArrayList;

import nc.mairie.technique.Services;
/**
 * Objet métier Affecter_Service
 */
public class Affecter_Service extends nc.mairie.technique.BasicMetier {
	public String codeservice;
	public String numeroinventaire;
	public String ddebut;
	public String dfin;


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
 * Retourne un ArrayList d'objet métier : Affecter_Service.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerAffecter_Service(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Affecter_Service unAffecter_Service = new Affecter_Service();
	return unAffecter_Service.getMyAffecter_ServiceBroker().listerAffecter_Service(aTransaction);
}
/**
 * Retourne un Affecter_Service.
 * @return Affecter_Service
 */
public static Affecter_Service chercherAffecter_Service(nc.mairie.technique.Transaction aTransaction, String inv, String servi,String date,String dfin) throws Exception{
	if(dfin.equals("")){
		dfin = "0001-01-01";
	}
	Affecter_Service unAffecter_Service = new Affecter_Service();
	return unAffecter_Service.getMyAffecter_ServiceBroker().chercherAffecter_Service(aTransaction, inv, servi,date,dfin);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerAffecter_Service(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Service unService)  throws Exception {
	//on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
//	Affectation des liens
	setNumeroinventaire(unEquipement.getNumeroinventaire());
	setCodeservice(unService.getServi());
	
	//on controle que la date soit supérieur à la date de mise en circulation
	setDdebut(Services.formateDate(getDdebut()));
	int controle = Services.compareDates(getDdebut(),unEquipement.getDatemiseencirculation());
	if (controle==-1){
		aTransaction.declarerErreur("La date doit être supérieur à la date de mise en circulation ("+unEquipement.getDatemiseencirculation()+")");
		return false;
	}else if(controle==-9999){
		return false;
	}
	
	//Creation du Affecter_Service
	return getMyAffecter_ServiceBroker().creerAffecter_Service(aTransaction);
}

public boolean affecter_service(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Service unService, Affecter_Service unAffecter_Service,String date) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unService.getServi()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agent"));
		return false;
	}
	if (null == unAffecter_Service.getNumeroinventaire()){
		//aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Affecter_Agent"));
		//return false;
	}
//	 on teste la date début >= date fin de l'affectation précédente
	if (null != unAffecter_Service.getNumeroinventaire()){
		setDdebut(Services.formateDate(getDdebut()));
		int controle = Services.compareDates(getDdebut(),unAffecter_Service.getDdebut());
		if (controle==-1){
			aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de début de l'affectation précédente. ("+unAffecter_Service.getDdebut()+")");
			return false;
		}else if(controle==-9999){
			return false;
		}
	}
//	 modification de l'affectation précédente
	if (null != unAffecter_Service.getNumeroinventaire()){
		//on met à jour la date de fin de l'affectation précédente
		unAffecter_Service.modifierAffecter_Service(aTransaction,unEquipement);
		if (aTransaction.isErreur()){
			return false;
		}
	}
	
	// on vérifie que la date de fin d'affectation soit supérieur ou égale à la date de début
	/*int controle = Services.compareDates(getDfin(),getDdebut());
	if (controle==-1){
		aTransaction.declarerErreur("La date de fin d'affectation doit être supérieur ou égale à la date de début. ("+unAffecter_Service.getDdebut()+")");
		return false;
	}else if(controle==-9999){
		return false;
	}*/
	
	// ajout de l'affectation
	creerAffecter_Service(aTransaction,unEquipement,unService);
	return true;
}

/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierAffecter_Service(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement) throws Exception {
	if (null==unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}

	//on controle que la date soit supérieur à la date de mise en circulation et différent de 0001-01-01
	setDdebut(Services.formateDate(getDdebut()));
	int controle = Services.compareDates(getDdebut(),unEquipement.getDatemiseencirculation());
	if (controle==-1){
		aTransaction.declarerErreur("La date doit être supérieur à la date de mise en circulation ("+unEquipement.getDatemiseencirculation()+")");
		return false;
	}else if(controle==-9999){
		return false;
	}
	
	//Modification de Affecter_Service
	return getMyAffecter_ServiceBroker().modifierAffecter_Service(aTransaction);
}

// quand on modifie une affectation on doit modifier l'affectation précédente
public boolean affecter_serviceModif(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement, Affecter_Service unAffecter_Service1, Affecter_Service unAffecter_Service2,String date) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unEquipement.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unAffecter_Service1.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","une affectation à un service"));
		return false;
	}
	/*if (null == unAffecter_Service2.getNumeroinventaire()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","une affectation à un service"));
		return false;
	}*/
	// on teste si des agents ont été affectés à cette affectation
	ArrayList uneListAgent = AffectationAgentInfos.chercherListAffectationsSceEquip(aTransaction,unAffecter_Service1.getCodeservice(),getNumeroinventaire());
	if (uneListAgent.size()>0){
		AffectationAgentInfos unAAI = (AffectationAgentInfos)uneListAgent.get(0);
		if(!unAAI.getCodeservice().substring(0,3).equals(getCodeservice().substring(0,3))){
			aTransaction.declarerErreur("Modification impossible.Des agents ont été affectés à cet équipement, vous devez d'abord supprimer les affectations des agents.");
			return false;
		}
		
	}
	
	// on regarde s'il y a eu des déclarations
	ArrayList uneListDecl = DeclarationsInfos.listerDeclarationsInfosSce(aTransaction,unAffecter_Service1.getCodeservice(),getDdebut(),getNumeroinventaire(),Services.dateDuJour());
	if(aTransaction.isErreur()){
		return false;
	}
	if(uneListDecl.size()>0){
		DeclarationsInfos unDI = (DeclarationsInfos)uneListDecl.get(0);
		Declarations uneDecl = Declarations.chercherDeclarations(aTransaction,unDI.getCodedec());
		if(aTransaction.isErreur()){
			return false;
		}
		if(!uneDecl.getCodeservice().equals(getCodeservice())){
			aTransaction.declarerErreur("Modification impossible car cette affectation a permis l'enregistrement de déclarations par les agents.");
			return false;
		}
	}
//	 modification de l'affectation précédente
	if (null != unAffecter_Service2.getNumeroinventaire()){
		//on met à jour la date de fin de l'affectation précédente
		unAffecter_Service2.modifierAffecter_Service(aTransaction,unEquipement);
		if (aTransaction.isErreur()){
			return false;
		}
	}
	// on teste la date début >= date fin de l'affectation précédente
	if (null != unAffecter_Service2.getNumeroinventaire()){
		setDdebut(Services.formateDate(getDdebut()));
		int controle = Services.compareDates(getDdebut(),unAffecter_Service2.getDdebut());
		if (controle==-1){
			aTransaction.declarerErreur("La date de début d'affectation doit être supérieur ou égale à la date de début de l'affectation précédente. ("+unAffecter_Service2.getDdebut()+")");
			return false;
		}else if(controle==-9999){
			return false;
		}
	}
	
	
	// modif de l'affectation
	modifierAffecter_Service(aTransaction,unEquipement);
	if(aTransaction.isErreur()){
		return false;
	}
	return true;
}


/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerAffecter_Service(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'Affecter_Service
	return getMyAffecter_ServiceBroker().supprimerAffecter_Service(aTransaction);
}

//si suppression alors datefin de l'affectation précédent à blanc
public boolean affecter_serviceSupp(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Affecter_Service unAffecter_Service) throws Exception{
//	on vérifie ques les paramètres ne sont pas null
	if (null == unAffecter_Service.getNumeroinventaire()){
		//aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","une affectation à un service"));
		//return false;
	}
//	 modification de l'affectation précédente
	if (null != unAffecter_Service.getNumeroinventaire()){
		//on met à jour la date de fin de l'affectation précédente
		unAffecter_Service.modifierAffecter_Service(aTransaction,unEquipement);
		if (aTransaction.isErreur()){
			return false;
		}
	}
	// suppression de l'affectation
	supprimerAffecter_Service(aTransaction);
	return true;
}

/**
 * Retourne un Affecter_Service.
 * @return Affecter_Service
 */
public static java.util.ArrayList chercherListAffecter_ServiceEquip(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Affecter_Service unAffecter_Service = new Affecter_Service();
	return unAffecter_Service.getMyAffecter_ServiceBroker().chercherListerAffecter_ServiceEquip(aTransaction, code);
}

/**
 * Retourne un Affecter_Service.
 * @return Affecter_Service
 */
public static Affecter_Service chercherListAffecter_ServiceEquipSce(nc.mairie.technique.Transaction aTransaction, String inv,String servi) throws Exception{
	Affecter_Service unAffecter_Service = new Affecter_Service();
	return unAffecter_Service.getMyAffecter_ServiceBroker().chercherListerAffecter_ServiceEquipSce(aTransaction, inv,servi);
}

	
	public String nomatr;
/**
 * Constructeur Affecter_Service.
 */
public Affecter_Service() {
	super();
}
/**
 * Getter de l'attribut codeservice.
 */
public String getCodeservice() {
	return codeservice;
}
/**
 * Setter de l'attribut codeservice.
 */
public void setCodeservice(String newCodeservice) { 
	codeservice = newCodeservice;
}
/**
 * Getter de l'attribut numeroinventaire.
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
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
	return new Affecter_ServiceBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected Affecter_ServiceBroker getMyAffecter_ServiceBroker() {
	return (Affecter_ServiceBroker)getMyBasicBroker();
}
}
