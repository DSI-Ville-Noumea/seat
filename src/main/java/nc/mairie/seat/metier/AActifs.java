package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;
/**
 * Objet métier AActifs
 */
public class AActifs extends BasicMetier implements AgentInterface{
	public String nomatr;
	public String nom;
	public String prenom;
	public String codact;
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
 * Retourne un ArrayList d'objet métier : AActifs.
 * @return java.util.ArrayList
 */
public static ArrayList<AActifs> listerAActifs(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AActifs unAActifs = new AActifs();
	return unAActifs.getMyAActifsBroker().listerAActifs(aTransaction);
}
/**
 * Retourne un AActifs.
 * @return AActifs
 */
public static AActifs chercherAActifs(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AActifs unAActifs = new AActifs();
	return unAActifs.getMyAActifsBroker().chercherAActifs(aTransaction, code);
}

/**
 * Retourne un ArrayList d'objet métier : AActifs.
 * @return java.util.ArrayList
 */
public static ArrayList<AActifs> listerAActifsService(nc.mairie.technique.Transaction aTransaction,String servi) throws Exception{
	AActifs unAActifs = new AActifs();
	return unAActifs.getMyAActifsBroker().listerAActifsService(aTransaction,servi);
}

/**
 * Retourne un AActifs.
 * @return AActifs
 */
public static ArrayList<AActifs> chercherListAgentServiceInfosSce(nc.mairie.technique.Transaction aTransaction, String servi) throws Exception{
	// on récupère les 3 premiers caractères pour rechercher les agents de tout le service
	
	AActifs unAActifs = new AActifs();
	return unAActifs.getMyAActifsBroker().chercherListAgentServiceInfosSce(aTransaction, servi);
}

/**
 * Constructeur AActifs.
 */
public AActifs() {
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
 * Getter de l'attribut codact.
 */
public String getCodact() {
	return codact;
}
/**
 * Setter de l'attribut codact.
 */
public void setCodact(String newCodact) { 
	codact = newCodact;
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
	return new AActifsBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected AActifsBroker getMyAActifsBroker() {
	return (AActifsBroker)getMyBasicBroker();
}
}
