package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier AgentServiceInfos
 */
public class AgentServiceInfos extends BasicMetier implements AgentInterface {
	public String liserv;
	public String nomatr;
	public String nom;
	public String prenom;
	public String datdeb;
	public String datfin;
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
 * Retourne un ArrayList d'objet métier : AgentServiceInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<AgentServiceInfos> listerAgentServiceInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AgentServiceInfos unAgentServiceInfos = new AgentServiceInfos();
	return unAgentServiceInfos.getMyAgentServiceInfosBroker().listerAgentServiceInfos(aTransaction);
}
public static ArrayList<AgentServiceInfos> listerAgentService(nc.mairie.technique.Transaction aTransaction,String serv) throws Exception{
	AgentServiceInfos unAgentServiceInfos = new AgentServiceInfos();
	return unAgentServiceInfos.getMyAgentServiceInfosBroker().listerAgentService(aTransaction, serv);
}
/**
 * Retourne un AgentServiceInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return AgentServiceInfos
 * @throws Exception Exception
 */
public static AgentServiceInfos chercherAgentServiceInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AgentServiceInfos unAgentServiceInfos = new AgentServiceInfos();
	return unAgentServiceInfos.getMyAgentServiceInfosBroker().chercherAgentServiceInfos(aTransaction, code);
}

/**
 * Retourne un AgentServiceInfos.
 * @param aTransaction aTransaction
 * @param servi servi
 * @return AgentServiceInfos
 * @throws Exception Exception
 */
public static ArrayList<AgentServiceInfos> chercherListAgentServiceInfosSce(nc.mairie.technique.Transaction aTransaction, String servi) throws Exception{
	// on récupère les 3 premiers caractères pour rechercher les agents de tout le service
	
	AgentServiceInfos unAgentServiceInfos = new AgentServiceInfos();
	return unAgentServiceInfos.getMyAgentServiceInfosBroker().chercherListAgentServiceInfosSce(aTransaction, servi);
}

	public String servi;
/**
 * Constructeur AgentServiceInfos.
 */
public AgentServiceInfos() {
	super();
}
/**
 * Getter de l'attribut liserv.
 */
/**
 * @return String
 */
public String getLiserv() {
	return liserv;
}
/**
 * Setter de l'attribut liserv.
 */
/**
 * @param newLiserv newLiserv
 */
public void setLiserv(String newLiserv) { 
	liserv = newLiserv;
}
/**
 * Getter de l'attribut nomatr.
 */
/**
 * @return String
 */
public String getNomatr() {
	return nomatr;
}
/**
 * Setter de l'attribut nomatr.
 */
/**
 * @param newNomatr newNomatr
 */
public void setNomatr(String newNomatr) { 
	nomatr = newNomatr;
}
/**
 * Getter de l'attribut nom.
 * @return String
 */
public String getNom() {
	return nom;
}
/**
 * Setter de l'attribut nom.
 */
/**
 * @param newNom newNom
 */
public void setNom(String newNom) { 
	nom = newNom;
}
/**
 * Getter de l'attribut prenom.
 * @return String
 */
public String getPrenom() {
	return prenom;
}
/**
 * Setter de l'attribut prenom.
 */
/**
 * @param newPrenom newPrenom
 */
public void setPrenom(String newPrenom) { 
	prenom = newPrenom;
}
/**
 * Getter de l'attribut datdeb.
 */
/**
 * @return String
 */
public String getDatdeb() {
	return datdeb;
}
/**
 * Setter de l'attribut datdeb.
 */
/**
 * @param newDatdeb newDatdeb
 */
public void setDatdeb(String newDatdeb) { 
	datdeb = newDatdeb;
}
/**
 * Getter de l'attribut datfin.
 */
/**
 * @return String
 */
public String getDatfin() {
	return datfin;
}
/**
 * Setter de l'attribut datfin.
 */
/**
 * @param newDatfin newDatfin
 */
public void setDatfin(String newDatfin) { 
	datfin = newDatfin;
}
/**
 * Getter de l'attribut servi.
 */
/**
 * @return String
 */
public String getServi() {
	return servi;
}
/**
 * Setter de l'attribut servi.
 */
/**
 * @param newServi newServi
 */
public void setServi(String newServi) { 
	servi = newServi;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AgentServiceInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected AgentServiceInfosBroker getMyAgentServiceInfosBroker() {
	return (AgentServiceInfosBroker)getMyBasicBroker();
}
}
