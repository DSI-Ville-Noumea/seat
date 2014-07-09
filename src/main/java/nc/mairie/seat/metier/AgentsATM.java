package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier AgentsATM
 */
public class AgentsATM extends BasicMetier {
	public String matricule;
	public String estmecanicien;
	public String codespe;
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
 * Retourne un ArrayList d'objet métier : AgentsATM.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<AgentsATM> listerAgentsATM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AgentsATM unAgentsATM = new AgentsATM();
	return unAgentsATM.getMyAgentsATMBroker().listerAgentsATM(aTransaction);
}
/**
 * Retourne un AgentsATM.
 * @param aTransaction aTransaction
 * @param code code
 * @return AgentsATM
 * @throws Exception Exception
 */
public static AgentsATM chercherAgentsATM(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AgentsATM unAgentsATM = new AgentsATM();
	return unAgentsATM.getMyAgentsATMBroker().chercherAgentsATM(aTransaction, code);
}
/**
 * Retourne un AgentsATM.
 * @param aTransaction aTransaction
 * @param nomatr nomatr
 * @return AgentsATM
 * @throws Exception Exception
 */
public static AgentsATM chercherAgentsATMMatr(nc.mairie.technique.Transaction aTransaction, String nomatr) throws Exception{
	AgentsATM unAgentsATM = new AgentsATM();
	return unAgentsATM.getMyAgentsATMBroker().chercherAgentsATMMatr(aTransaction, nomatr);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param unAgent unAgent
 * @param uneSpecialite uneSpecialite
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerAgentsATM(nc.mairie.technique.Transaction aTransaction,Agents unAgent,Specialite uneSpecialite)  throws Exception {
	// controle si objet vide
	if (null==unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agent"));
		return false;
	}
	if (null==uneSpecialite){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Specialite"));
		return false;
	}
	//si déjà enregistré : msg erreur
	if(existeAgentsATMSpe(aTransaction,unAgent.getNomatr())){
		aTransaction.declarerErreur("Attention, cet agent a déjà été enregistré comme mécanicien.");
		return false;
	}
	//affectation du champ
	setEstmecanicien("T");
	
	//affectation des clés étrangères
	setMatricule(unAgent.getNomatr());
	setCodespe(uneSpecialite.getCodespecialite());
	
	//Creation du AgentsATM
	return getMyAgentsATMBroker().creerAgentsATM(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean modifierAgentsATM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du AgentsATM
	return getMyAgentsATMBroker().modifierAgentsATM(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param unAgent unAgent
 * @param uneSpecialite uneSpecialite
 * @return boolean 
 * @throws Exception Exception
 */
public boolean modificationAgentsATM(nc.mairie.technique.Transaction aTransaction,Agents unAgent,Specialite uneSpecialite) throws Exception {
//	 controle si objet vide
	if (null==unAgent){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Agent"));
		return false;
	}
	if (null==uneSpecialite){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Specialite"));
		return false;
	}
	
	//affectation des clés étrangères
	setMatricule(unAgent.getNomatr());
	setCodespe(uneSpecialite.getCodespecialite());
	
	//Modification du AgentsATM
	return modifierAgentsATM(aTransaction);
}

/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean supprimerAgentsATM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//controle : si l'agent a déjà été affecté à un OT on ne peut pas le supprimer
	OT_ATM unOTATM = OT_ATM.chercherOT_ATM(aTransaction,getMatricule());
	if(unOTATM!=null){
		if(unOTATM.getNumot()!=null){
			aTransaction.declarerErreur("La suppression est impossible, cet agent a déjà été affecté à un OT.");
			return false;
		}else{
			aTransaction.traiterErreur();
		}		
	}
	//Suppression de l'AgentsATM
	return getMyAgentsATMBroker().supprimerAgentsATM(aTransaction);
}
/**
 * Constructeur AgentsATM.
 */
public AgentsATM() {
	super();
}
/**
 * Getter de l'attribut matricule.
 */
/**
 * @return String
 */
public String getMatricule() {
	return matricule;
}
/**
 * Setter de l'attribut matricule.
 */
/**
 * @param newMatricule newMatricule
 */
public void setMatricule(String newMatricule) { 
	matricule = newMatricule;
}
/**
 * Getter de l'attribut estmecanicien.
 */
/**
 * @return String
 */
public String getEstmecanicien() {
	return estmecanicien;
}
/**
 * Setter de l'attribut estmecanicien.
 */
/**
 * @param newEstmecanicien newEstmecanicien
 */
public void setEstmecanicien(String newEstmecanicien) { 
	estmecanicien = newEstmecanicien;
}
/**
 * Getter de l'attribut codespe.
 */
/**
 * @return String
 */
public String getCodespe() {
	return codespe;
}
/**
 * Setter de l'attribut codespe.
 */
/**
 * @param newCodespe newCodespe
 */
public void setCodespe(String newCodespe) { 
	codespe = newCodespe;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AgentsATMBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected AgentsATMBroker getMyAgentsATMBroker() {
	return (AgentsATMBroker)getMyBasicBroker();
}
/**
 * Retourne un booléen.
 * Vérifie si existe
 * @param aTransaction aTransaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeAgentsATMSpe(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	AgentsATM unAgentsATM = new AgentsATM();
	return unAgentsATM.getMyAgentsATMBroker().existeAgentsATMSpe(aTransaction, param);
}
}
