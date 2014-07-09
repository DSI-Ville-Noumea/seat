package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier AgentsATMInfos
 */
public class AgentsATMInfos extends BasicMetier {
	public String codespecialite;
	public String libellespe;
	public String matricule;
	public String estmecanicien;
	public String nom;
	public String prenom;
	public String servi;
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
 * Retourne un ArrayList d'objet métier : AgentsATMInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<AgentsATMInfos> listerAgentsATMInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AgentsATMInfos unAgentsATMInfos = new AgentsATMInfos();
	return unAgentsATMInfos.getMyAgentsATMInfosBroker().listerAgentsATMInfos(aTransaction);
}
/**
 * Retourne un AgentsATMInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return AgentsATMInfos
 * @throws Exception Exception
 */
public static AgentsATMInfos chercherAgentsATMInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AgentsATMInfos unAgentsATMInfos = new AgentsATMInfos();
	return unAgentsATMInfos.getMyAgentsATMInfosBroker().chercherAgentsATMInfos(aTransaction, code);
}
	public String numot;
/**
 * Constructeur AgentsATMInfos.
 */
public AgentsATMInfos() {
	super();
}
/**
 * Getter de l'attribut codespecialite.
 */
/**
 * @return String
 */
public String getCodespecialite() {
	return codespecialite;
}
/**
 * Setter de l'attribut codespecialite.
 */
/**
 * @param newCodespecialite newCodespecialite
 */
public void setCodespecialite(String newCodespecialite) { 
	codespecialite = newCodespecialite;
}
/**
 * Getter de l'attribut libellespe.
 */
/**
 * @return String
 */
public String getLibellespe() {
	return libellespe;
}
/**
 * Setter de l'attribut libellespe.
 */
/**
 * @param newLibellespe newLibellespe
 */
public void setLibellespe(String newLibellespe) { 
	libellespe = newLibellespe;
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
 * Getter de l'attribut nom.
 */
/**
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
 */
/**
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
 * Getter de l'attribut numot.
 */
/**
 * @return String
 */
public String getNumot() {
	return numot;
}
/**
 * Setter de l'attribut numot.
 */
/**
 * @param newNumot newNumot
 */
public void setNumot(String newNumot) { 
	numot = newNumot;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AgentsATMInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected AgentsATMInfosBroker getMyAgentsATMInfosBroker() {
	return (AgentsATMInfosBroker)getMyBasicBroker();
}
}
