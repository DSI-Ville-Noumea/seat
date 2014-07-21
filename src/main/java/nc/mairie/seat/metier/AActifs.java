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
 * @param aTransaction Transaction
 * @throws Exception Exception
 * @return ArrayList
 */
public static ArrayList<AActifs> listerAActifs(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AActifs unAActifs = new AActifs();
	return unAActifs.getMyAActifsBroker().listerAActifs(aTransaction);
}
/**
 * Retourne un AActifs.
 * @param aTransaction Transaction
 * @param code code
 * @return AActifs AActifs
 * @throws Exception Exception
 */
public static AActifs chercherAActifs(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AActifs unAActifs = new AActifs();
	return unAActifs.getMyAActifsBroker().chercherAActifs(aTransaction, code);
}

/**
 * Retourne un ArrayList d'objet métier : AActifs.
 * @param aTransaction Transaction
 * @param servi servi
 * @return ArrayList ArrayList
 * @throws Exception Exception
 */
public static ArrayList<AActifs> listerAActifsService(nc.mairie.technique.Transaction aTransaction,String servi) throws Exception{
	AActifs unAActifs = new AActifs();
	return unAActifs.getMyAActifsBroker().listerAActifsService(aTransaction,servi);
}

/**
 * Retourne un AActifs.
 * @param aTransaction Transaction
 * @param servi servi
 * @return ArrayList ArrayList
 * @throws Exception Exception
 */
public static ArrayList<AActifs> chercherListAgentServiceInfosSce(nc.mairie.technique.Transaction aTransaction, String servi) throws Exception{
	// on récupère les 3 premiers caractères pour rechercher les agents de tout le service
	
	AActifs unAActifs = new AActifs();
	return unAActifs.getMyAActifsBroker().chercherListAgentServiceInfosSce(aTransaction, servi);
}

/**
 * Constructeur AActifs.
 * 
 */
public AActifs() {
	super();
}
/**
 * Getter de l'attribut nomatr.
 * @return nomatr
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
 * @param newPrenom newPrenom
 */
public void setPrenom(String newPrenom) { 
	prenom = newPrenom;
}
/**
 * Getter de l'attribut codact.
 * @return codact
 */
public String getCodact() {
	return codact;
}
/**
 * Setter de l'attribut codact.
 * @param newCodact newCodact
 */
public void setCodact(String newCodact) { 
	codact = newCodact;
}
/**
 * Getter de l'attribut servi.
 * @return servi
 */
public String getServi() {
	return servi;
}
/**
 * Setter de l'attribut servi.
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
	return new AActifsBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
 * @return AActifsBroker
 */
protected AActifsBroker getMyAActifsBroker() {
	return (AActifsBroker)getMyBasicBroker();
}
}
