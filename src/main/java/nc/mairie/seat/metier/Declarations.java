package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Declarations
 */
public class Declarations extends BasicMetier {
	public String codedec;
	public String date;
	public String matricule;
	public String anomalies;
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
 * Retourne un ArrayList d'objet métier : Declarations.
 * @return java.util.ArrayList
 */
public static ArrayList<Declarations> listerDeclarations(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Declarations unDeclarations = new Declarations();
	return unDeclarations.getMyDeclarationsBroker().listerDeclarations(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : Declarations.
 * @return java.util.ArrayList
 */
public static ArrayList<Declarations> listerDeclarationsOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	Declarations unDeclarations = new Declarations();
	return unDeclarations.getMyDeclarationsBroker().listerDeclarationsOT(aTransaction,numot);
}

/**
 * Retourne un ArrayList d'objet métier : Declarations.
 * @return java.util.ArrayList
 */
public static ArrayList<Declarations> listerDeclarationsEquip(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception{
	Declarations unDeclarations = new Declarations();
	return unDeclarations.getMyDeclarationsBroker().listerDeclarationsEquip(aTransaction,inv);
}

/**
 * Retourne un Declarations.
 * @return Declarations
 */
public static Declarations chercherDeclarations(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Declarations unDeclarations = new Declarations();
	return unDeclarations.getMyDeclarationsBroker().chercherDeclarations(aTransaction, code);
}

public String creationDeclarationsOTAgentCDE(nc.mairie.technique.Transaction aTransaction,AgentCDE unAgent,Equipement unEquipement,Service unService)  throws Exception {
//	 controle si null
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return "erreur";
	}
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return "erreur";
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return "erreur";
	}
	// création de l'OT
	OT unOT = new OT();
	unOT.setCommentaire(getAnomalies());
	String codeOT = unOT.creationOTDeclaration(aTransaction,unEquipement);
	if(aTransaction.isErreur()){
		return "erreur";
	}
	setCodeot(codeOT);
	creerDeclarationsAgentCDE(aTransaction,unEquipement,unAgent, unService);
	
	return codeOT;
}

public String creationDeclarationsOTAgentCCAS(nc.mairie.technique.Transaction aTransaction,AgentCCAS unAgent,Equipement unEquipement,Service unService)  throws Exception {
//	 controle si null
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return "erreur";
	}
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return "erreur";
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return "erreur";
	}
	// création de l'OT
	OT unOT = new OT();
	unOT.setCommentaire(getAnomalies());
	String codeOT = unOT.creationOTDeclaration(aTransaction,unEquipement);
	if(aTransaction.isErreur()){
		return "erreur";
	}
	setCodeot(codeOT);
	creerDeclarationsAgentCCAS(aTransaction,unEquipement,unAgent, unService);
	
	return codeOT;
}

public String creationDeclarationsOT(nc.mairie.technique.Transaction aTransaction,Agents unAgent,Equipement unEquipement,Service unService)  throws Exception {
//	 controle si null
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return "erreur";
	}
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return "erreur";
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return "erreur";
	}
	// contrôle de la date
	// la date doit être supérieure ou égale à la date de mise en circulation
	int controle = Services.compareDates(getDate(),unEquipement.getDatemiseencirculation());
	if (controle==-1){
		aTransaction.declarerErreur("La date de la déclaration doit être supérieure ou égale à la date de mise en circulation de l'équipement ");
		return "erreur";
	}else if(controle==-9999){
		return "erreur";
	}
	
	
	// création de l'OT
	OT unOT = new OT();
	unOT.setCommentaire(getAnomalies());
	String codeOT = unOT.creationOTDeclaration(aTransaction,unEquipement);
	if(aTransaction.isErreur()){
		return "erreur";
	}
	setCodeot(codeOT);
	creerDeclarations(aTransaction,unEquipement,unAgent, unService);
	
	return codeOT;
}

public String creationDeclarationsFPM(nc.mairie.technique.Transaction aTransaction,Agents unAgent,PMateriel unPMateriel,Service unService)  throws Exception {
//	 controle si null
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return "erreur";
	}
	if (null == unPMateriel){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return "erreur";
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return "erreur";
	}
	// contrôle de la date
	// la date doit être supérieure ou égale à la date de mise en circulation
	int controle = Services.compareDates(getDate(),unPMateriel.getDmes());
	if (controle==-1){
		aTransaction.declarerErreur("La date de la déclaration doit être supérieure ou égale à la date de mise en circulation de l'équipement ");
		return "erreur";
	}else if(controle==-9999){
		return "erreur";
	}
	
	
	// création de l'OT
	FPM unFPM = new FPM();
	unFPM.setCommentaire(getAnomalies());
	String codeOT = unFPM.creationFPMDeclaration(aTransaction,unPMateriel);
	if(aTransaction.isErreur()){
		return "erreur";
	}
	setCodeot(codeOT);
	creerDeclarationsPM(aTransaction,unPMateriel,unAgent, unService);
	
	return codeOT;
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerDeclarations(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Agents unAgent,Service unService)  throws Exception {
//	 controle si null
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return false;
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
//	 RG : controle de la date
	if((getDate()!=null)&&(!getDate().equals(""))){
		if(!Services.estUneDate(getDate())){
			aTransaction.declarerErreur("La date saisie n'est pas correcte");
			return false;
		}
	}
	// autoincrémentation du code de la déclaration
	setCodedec(String.valueOf(nouvCodeDecl(aTransaction)));
	//on renseigne la clé étrangère
	setMatricule(unAgent.getNomatr());	
	setNuminv(unEquipement.getNumeroinventaire());
	setCodeservice(unService.getServi());
	//Creation du Declarations
	return getMyDeclarationsBroker().creerDeclarations(aTransaction);
}

public boolean creerDeclarationsPM(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,Agents unAgent,Service unService)  throws Exception {
//	 controle si null
	if (null == unPMateriel){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return false;
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
//	 RG : controle de la date
	if((getDate()!=null)&&(!getDate().equals(""))){
		if(!Services.estUneDate(getDate())){
			aTransaction.declarerErreur("La date saisie n'est pas correcte");
			return false;
		}
	}
	// autoincrémentation du code de la déclaration
	setCodedec(String.valueOf(nouvCodeDecl(aTransaction)));
	//on renseigne la clé étrangère
	setMatricule(unAgent.getNomatr());	
	setNuminv(unPMateriel.getPminv());
	setCodeservice(unService.getServi());
	//Creation du Declarations
	return getMyDeclarationsBroker().creerDeclarations(aTransaction);
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerDeclarationsAgentCDE(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,AgentCDE unAgent,Service unService)  throws Exception {
//	 controle si null
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return false;
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
//	 RG : controle de la date
	if((getDate()!=null)&&(!getDate().equals(""))){
		if(!Services.estUneDate(getDate())){
			aTransaction.declarerErreur("La date saisie n'est pas correcte");
			return false;
		}
	}
	// autoincrémentation du code de la déclaration
	setCodedec(String.valueOf(nouvCodeDecl(aTransaction)));
	//on renseigne la clé étrangère
	setMatricule(unAgent.getNomatr());	
	setNuminv(unEquipement.getNumeroinventaire());
	setCodeservice(unService.getServi());
	//Creation du Declarations
	return getMyDeclarationsBroker().creerDeclarations(aTransaction);
}

public boolean creerDeclarationsAgentCCAS(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,AgentCCAS unAgent,Service unService)  throws Exception {
//	 controle si null
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return false;
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
//	 RG : controle de la date
	if((getDate()!=null)&&(!getDate().equals(""))){
		if(!Services.estUneDate(getDate())){
			aTransaction.declarerErreur("La date saisie n'est pas correcte");
			return false;
		}
	}
	// autoincrémentation du code de la déclaration
	setCodedec(String.valueOf(nouvCodeDecl(aTransaction)));
	//on renseigne la clé étrangère
	setMatricule(unAgent.getNomatr());	
	setNuminv(unEquipement.getNumeroinventaire());
	setCodeservice(unService.getServi());
	//Creation du Declarations
	return getMyDeclarationsBroker().creerDeclarations(aTransaction);
}

/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierDeclarations(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,Agents unAgent,Service unService) throws Exception {
//	 controle si null
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return false;
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
//	 contrôle de la date
	// la date doit être supérieure ou égale à la date de mise en circulation
	if ((getDate()!=null)&&(!("").equals(getDate()))){ 
		int controle = Services.compareDates(getDate(),unEquipement.getDatemiseencirculation());
		if (controle==-1){
			aTransaction.declarerErreur("La date de la déclaration doit être supérieure ou égale à la date de mise en circulation de l'équipement ");
			return false;
		}else if(controle==-9999){
			return false;
		}
	}
//	on renseigne la clé étrangère
	setMatricule(unAgent.getNomatr());
	setNuminv(unEquipement.getNumeroinventaire());
	setCodeservice(unService.getServi());
	// on modifie le commentaire de l'OT correspondant à la déclaration?
	
	//Modification du Declarations
	return getMyDeclarationsBroker().modifierDeclarations(aTransaction);
}

public boolean modifierDeclarationsPM(nc.mairie.technique.Transaction aTransaction,PMateriel unPMateriel,Agents unAgent,Service unService) throws Exception {
//	 controle si null
	if (null == unPMateriel){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Petit matériel"));
		return false;
	}
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return false;
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
//	 contrôle de la date
	// la date doit être supérieure ou égale à la date de mise en circulation
	if ((getDate()!=null)&&(!("").equals(getDate()))){ 
		int controle = Services.compareDates(getDate(),unPMateriel.getDmes());
		if (controle==-1){
			aTransaction.declarerErreur("La date de la déclaration doit être supérieure ou égale à la date de mise en circulation de l'équipement ");
			return false;
		}else if(controle==-9999){
			return false;
		}
	}
//	on renseigne la clé étrangère
	setMatricule(unAgent.getNomatr());
	setNuminv(unPMateriel.getPminv());
	setCodeservice(unService.getServi());
	// on modifie le commentaire de l'OT correspondant à la déclaration?
	
	//Modification du Declarations
	return getMyDeclarationsBroker().modifierDeclarations(aTransaction);
}

/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierDeclarationsAgentCDE(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,AgentCDE unAgent,Service unService) throws Exception {
//	 controle si null
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return false;
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
//	 contrôle de la date
	// la date doit être supérieure ou égale à la date de mise en circulation
	if ((getDate()!=null)&&(!("").equals(getDate()))){ 
		int controle = Services.compareDates(getDate(),unEquipement.getDatemiseencirculation());
		if (controle==-1){
			aTransaction.declarerErreur("La date de la déclaration doit être supérieure ou égale à la date de mise en circulation de l'équipement ");
			return false;
		}else if(controle==-9999){
			return false;
		}
	}
	
//	on renseigne la clé étrangère
	setMatricule(unAgent.getNomatr());
	setNuminv(unEquipement.getNumeroinventaire());
	setCodeservice(unService.getServi());
	// on modifie le commentaire de l'OT correspondant à la déclaration?
	
	//Modification du Declarations
	return getMyDeclarationsBroker().modifierDeclarations(aTransaction);
}

public boolean modifierDeclarationsAgentCCAS(nc.mairie.technique.Transaction aTransaction,Equipement unEquipement,AgentCCAS unAgent,Service unService) throws Exception {
//	 controle si null
	if (null == unEquipement){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Equipement"));
		return false;
	}
	if (null == unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agents"));
		return false;
	}
	if (null == unService){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Service"));
		return false;
	}
//	 contrôle de la date
	// la date doit être supérieure ou égale à la date de mise en circulation
	if ((getDate()!=null)&&(!("").equals(getDate()))){ 
		int controle = Services.compareDates(getDate(),unEquipement.getDatemiseencirculation());
		if (controle==-1){
			aTransaction.declarerErreur("La date de la déclaration doit être supérieure ou égale à la date de mise en circulation de l'équipement ");
			return false;
		}else if(controle==-9999){
			return false;
		}
	}
	
//	on renseigne la clé étrangère
	setMatricule(unAgent.getNomatr());
	setNuminv(unEquipement.getNumeroinventaire());
	setCodeservice(unService.getServi());
	// on modifie le commentaire de l'OT correspondant à la déclaration?
	
	//Modification du Declarations
	return getMyDeclarationsBroker().modifierDeclarations(aTransaction);
}

/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerDeclarations(nc.mairie.technique.Transaction aTransaction) throws Exception{
	// RG : si unOT a été généré : pas de suppression
	if((getCodeot()!=null)&&(!getCodeot().equals(""))){
		aTransaction.declarerErreur("Pas de suppression possible car la déclaratin a généré un OT.Veuillez d'abord le supprimer.");
		return false;
	}	
	//Suppression de la Declaration
	return getMyDeclarationsBroker().supprimerDeclarations(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodeDecl(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier code
//	recherche du dernier codeCarburant
	int nouvcode = getMyDeclarationsBroker().nouvCodeDecl(aTransaction);
	
	//si pas trouvé
	if (nouvcode == -1) {
		//fonctionnellement normal: table vide
		nouvcode= 1;
	} else {
		nouvcode++;
	}
	
	return nouvcode;
}

	
	public String codeot;
	public String numinv;
/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existeOTDeclarations(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception{
	Declarations unDeclarations = new Declarations();
	return unDeclarations.getMyDeclarationsBroker().existeDeclarations(aTransaction, numot);
}

/**
 * Retourne un Declarations.
 * @return Declarations
 */
public static Declarations chercherDeclarationsOT(nc.mairie.technique.Transaction aTransaction, String numot) throws Exception{
	Declarations unDeclarations = new Declarations();
	return unDeclarations.getMyDeclarationsBroker().chercherDeclarationsOT(aTransaction, numot);
}

	public String codeservice;
/**
 * Constructeur Declarations.
 */
public Declarations() {
	super();
}
/**
 * Getter de l'attribut codedec.
 */
public String getCodedec() {
	return codedec;
}
/**
 * Setter de l'attribut codedec.
 */
public void setCodedec(String newCodedec) { 
	codedec = newCodedec;
}
/**
 * Getter de l'attribut date.
 */
public String getDate() {
	return date;
}
/**
 * Setter de l'attribut date.
 */
public void setDate(String newDate) { 
	date = newDate;
}
/**
 * Getter de l'attribut anomalies.
 */
public String getAnomalies() {
	return anomalies;
}
/**
 * Setter de l'attribut anomalies.
 */
public void setAnomalies(String newAnomalies) { 
	anomalies = newAnomalies;
}
/**
 * Getter de l'attribut matricule.
 */
public String getMatricule() {
	return matricule;
}
/**
 * Setter de l'attribut matricule.
 */
public void setMatricule(String newMatricule) { 
	matricule = newMatricule;
}
/**
 * Getter de l'attribut codeot.
 */
public String getCodeot() {
	return codeot;
}
/**
 * Setter de l'attribut codeot.
 */
public void setCodeot(String newCodeot) { 
	codeot = newCodeot;
}
/**
 * Getter de l'attribut numinv.
 */
public String getNuminv() {
	return numinv;
}
/**
 * Setter de l'attribut numinv.
 */
public void setNuminv(String newNuminv) { 
	numinv = newNuminv;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected BasicBroker definirMyBroker() { 
	return new DeclarationsBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected DeclarationsBroker getMyDeclarationsBroker() {
	return (DeclarationsBroker)getMyBasicBroker();
}
}
