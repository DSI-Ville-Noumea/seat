package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier AgentAtmRenseignements
 */
public class AgentAtmRenseignements extends BasicMetier {
	public String nomatr;
	public String nom;
	public String prenom;
	public String codespe;
	public String libellespe;
	public String estmecanicien;
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
 * Retourne un ArrayList d'objet métier : AgentAtmRenseignements.
 * @return java.util.ArrayList
 */
public static ArrayList<AgentAtmRenseignements> listerAgentAtmRenseignements(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AgentAtmRenseignements unAgentAtmRenseignements = new AgentAtmRenseignements();
	return unAgentAtmRenseignements.getMyAgentAtmRenseignementsBroker().listerAgentAtmRenseignements(aTransaction);
}
/**
 * Retourne un AgentAtmRenseignements.
 * @return AgentAtmRenseignements
 */
public static AgentAtmRenseignements chercherAgentAtmRenseignements(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AgentAtmRenseignements unAgentAtmRenseignements = new AgentAtmRenseignements();
	return unAgentAtmRenseignements.getMyAgentAtmRenseignementsBroker().chercherAgentAtmRenseignements(aTransaction, code);
}

public boolean existeMecanicien(nc.mairie.technique.Transaction aTransaction,String nomatr) throws Exception{
	AgentAtmRenseignements unAgentAtmRenseignements = new AgentAtmRenseignements();
	return unAgentAtmRenseignements.getMyAgentAtmRenseignementsBroker().existeMecanicien(aTransaction, nomatr);
}

/**
 * Constructeur AgentAtmRenseignements.
 */
public AgentAtmRenseignements() {
	super();
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
 * Getter de l'attribut nom.
 */
public String getNom() {
	return nom;
}
/**
 * Setter de l'attribut nom.
 */
public void setNom(String newNom) { 
	nom = newNom;
}
/**
 * Getter de l'attribut prenom.
 */
public String getPrenom() {
	return prenom;
}
/**
 * Setter de l'attribut prenom.
 */
public void setPrenom(String newPrenom) { 
	prenom = newPrenom;
}
/**
 * Getter de l'attribut codespe.
 */
public String getCodespe() {
	return codespe;
}
/**
 * Setter de l'attribut codespe.
 */
public void setCodespe(String newCodespe) { 
	codespe = newCodespe;
}
/**
 * Getter de l'attribut libellespe.
 */
public String getLibellespe() {
	return libellespe;
}
/**
 * Setter de l'attribut libellespe.
 */
public void setLibellespe(String newLibellespe) { 
	libellespe = newLibellespe;
}
/**
 * Getter de l'attribut estmecanicien.
 */
public String getEstmecanicien() {
	return estmecanicien;
}
/**
 * Setter de l'attribut estmecanicien.
 */
public void setEstmecanicien(String newEstmecanicien) { 
	estmecanicien = newEstmecanicien;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AgentAtmRenseignementsBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected AgentAtmRenseignementsBroker getMyAgentAtmRenseignementsBroker() {
	return (AgentAtmRenseignementsBroker)getMyBasicBroker();
}
}
