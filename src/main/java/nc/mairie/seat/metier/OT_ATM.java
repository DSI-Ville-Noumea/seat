package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier OT_ATM
 */
public class OT_ATM extends BasicMetier {
	public String matricule;
	public String numot;
/**
 * Constructeur OT_ATM.
 */
public OT_ATM() {
	super();
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
 * Getter de l'attribut numot.
 */
public String getNumot() {
	return numot;
}
/**
 * Setter de l'attribut numot.
 */
public void setNumot(String newNumot) { 
	numot = newNumot;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new OT_ATMBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected OT_ATMBroker getMyOT_ATMBroker() {
	return (OT_ATMBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : OT_ATM.
 * @return java.util.ArrayList
 */
public static ArrayList<OT_ATM> listerOT_ATM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OT_ATM unOT_ATM = new OT_ATM();
	return unOT_ATM.getMyOT_ATMBroker().listerOT_ATM(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT_ATM.
 * @return java.util.ArrayList
 */
public static ArrayList<OT_ATM> listerOT_ATMOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	OT_ATM unOT_ATM = new OT_ATM();
	return unOT_ATM.getMyOT_ATMBroker().listerOT_ATMOT(aTransaction,numot);
}

/**
 * Retourne un OT_ATM.
 * @return OT_ATM
 */
public static OT_ATM chercherOT_ATM(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	OT_ATM unOT_ATM = new OT_ATM();
	return unOT_ATM.getMyOT_ATMBroker().chercherOT_ATM(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerOT_ATM(nc.mairie.technique.Transaction aTransaction,OT unOT,AgentsATM unAgentATM)  throws Exception {
	//controle si objet vide
	if (null==unOT){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","OT"));
		return false;
	}
	if (null==unAgentATM){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","un agent de l'ATM"));
		return false;
	}
	//on vérifie que l'agent n'est pas déjà enregistré pour cet OT
	if(existeOT_ATM(aTransaction,unOT.getNumeroot(),unAgentATM.getMatricule())){
		aTransaction.declarerErreur("Cet agent est déjà enregistré pour cet OT.");
		return false;
	}
	//on renseigne les clés étrangères
	setNumot(unOT.getNumeroot());
	setMatricule(unAgentATM.getMatricule());
	//Creation du OT_ATM
	return getMyOT_ATMBroker().creerOT_ATM(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierOT_ATM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du OT_ATM
	return getMyOT_ATMBroker().modifierOT_ATM(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerOT_ATM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'OT_ATM
	return getMyOT_ATMBroker().supprimerOT_ATM(aTransaction);
}

/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean existeOT_ATM(nc.mairie.technique.Transaction aTransaction,String numot,String nomatr) throws Exception{
	//Suppression de l'OT_ATM
	return getMyOT_ATMBroker().existeOT_ATM(aTransaction,numot,nomatr);
}

}
