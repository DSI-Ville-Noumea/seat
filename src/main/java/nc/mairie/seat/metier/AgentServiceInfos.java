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
 * @return java.util.ArrayList
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
 * @return AgentServiceInfos
 */
public static AgentServiceInfos chercherAgentServiceInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AgentServiceInfos unAgentServiceInfos = new AgentServiceInfos();
	return unAgentServiceInfos.getMyAgentServiceInfosBroker().chercherAgentServiceInfos(aTransaction, code);
}

/**
 * Retourne un AgentServiceInfos.
 * @return AgentServiceInfos
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
public String getLiserv() {
	return liserv;
}
/**
 * Setter de l'attribut liserv.
 */
public void setLiserv(String newLiserv) { 
	liserv = newLiserv;
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
 * Getter de l'attribut datdeb.
 */
public String getDatdeb() {
	return datdeb;
}
/**
 * Setter de l'attribut datdeb.
 */
public void setDatdeb(String newDatdeb) { 
	datdeb = newDatdeb;
}
/**
 * Getter de l'attribut datfin.
 */
public String getDatfin() {
	return datfin;
}
/**
 * Setter de l'attribut datfin.
 */
public void setDatfin(String newDatfin) { 
	datfin = newDatfin;
}
/**
 * Getter de l'attribut servi.
 */
public String getServi() {
	return servi;
}
/**
 * Setter de l'attribut servi.
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
*/
protected AgentServiceInfosBroker getMyAgentServiceInfosBroker() {
	return (AgentServiceInfosBroker)getMyBasicBroker();
}
}
