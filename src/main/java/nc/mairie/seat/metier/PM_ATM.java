package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PM_ATM
 */
public class PM_ATM extends BasicMetier {
	public String numfiche;
	public String matricule;
/**
 * Constructeur PM_ATM.
 */
public PM_ATM() {
	super();
}
/**
 * Getter de l'attribut numfiche.
 */
public String getNumfiche() {
	return numfiche;
}
/**
 * Setter de l'attribut numfiche.
 */
public void setNumfiche(String newNumfiche) { 
	numfiche = newNumfiche;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PM_ATMBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected PM_ATMBroker getMyPM_ATMBroker() {
	return (PM_ATMBroker)getMyBasicBroker();
}
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
 * Retourne un ArrayList d'objet métier : PM_ATM.
 * @return java.util.ArrayList
 */
public static ArrayList<PM_ATM> listerPM_ATM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PM_ATM unPM_ATM = new PM_ATM();
	return unPM_ATM.getMyPM_ATMBroker().listerPM_ATM(aTransaction);
}
/**
 * Retourne un PM_ATM.
 * @return PM_ATM
 */
public static PM_ATM chercherPM_ATM(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PM_ATM unPM_ATM = new PM_ATM();
	return unPM_ATM.getMyPM_ATMBroker().chercherPM_ATM(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerPM_ATM(nc.mairie.technique.Transaction aTransaction, FPM unPMatFiche,AgentsATM unAgentATM)  throws Exception {
	//controle si objet vide
	if (null==unPMatFiche){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","FPM"));
		return false;
	}
	if (null==unAgentATM){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","un agent de l'ATM"));
		return false;
	}
	//on vérifie que l'agent n'est pas déjà enregistré pour cet FPM
	if(existePM_ATM(aTransaction,unPMatFiche.getNumfiche(),unAgentATM.getMatricule())){
		aTransaction.declarerErreur("Cet agent est déjà enregistré pour cette FPM.");
		return false;
	}
	//on renseigne les clés étrangères
	setNumfiche(unPMatFiche.getNumfiche());
	setMatricule(unAgentATM.getMatricule());
	//Creation du PM_ATM
	return getMyPM_ATMBroker().creerPM_ATM(aTransaction);
}
public boolean existePM_ATM(nc.mairie.technique.Transaction aTransaction,String numfiche,String nomatr) throws Exception{
	//Suppression de l'FPM_ATM
	return getMyPM_ATMBroker().existePM_ATM(aTransaction,numfiche,nomatr);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierPM_ATM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PM_ATM
	return getMyPM_ATMBroker().modifierPM_ATM(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerPM_ATM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PM_ATM
	return getMyPM_ATMBroker().supprimerPM_ATM(aTransaction);
}

// liste des pm_atm pour un numfiche
public static ArrayList<PM_ATM> listerPM_ATM_FPM(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	PM_ATM unPM_ATM = new PM_ATM();
	return unPM_ATM.getMyPM_ATMBroker().listerPM_ATM_FPM(aTransaction,numfiche);
}

}
