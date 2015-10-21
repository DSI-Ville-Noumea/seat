package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier AgentsMunicipaux
 */
public class AgentsMunicipaux extends BasicMetier implements AgentInterface {
	public String nomatr;
	public String nom;
	public String prenom;
	public String codact;
	public String servi;
	public String datfin;
/**
 * Constructeur AgentsMunicipaux.
 */
public AgentsMunicipaux() {
	super();
}
/**
 * Getter de l'attribut nomatr.
 * @return String
 */
public String getNomatr() {
	return nomatr;
}
/**
 * Setter de l'attribut nomatr.
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
 * @return String
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
 * @return String
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
 * Getter de l'attribut datfin.
 * @return String
 */
public String getDatfin() {
	return datfin;
}
/**
 * Setter de l'attribut datfin.
 * @param newDatfin newDatfin
 */
public void setDatfin(String newDatfin) { 
	datfin = newDatfin;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AgentsMunicipauxBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected AgentsMunicipauxBroker getMyAgentsMunicipauxBroker() {
	return (AgentsMunicipauxBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : AgentsMunicipaux.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<AgentsMunicipaux> listerAgentsMunicipaux(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().listerAgentsMunicipaux(aTransaction);
}
/**
 * Retourne un AgentsMunicipaux.
 * @param aTransaction Transaction
 * @param code code
 * @return AgentsMunicipaux
 * @throws Exception Exception
 */
public static AgentsMunicipaux chercherAgentsMunicipaux(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().chercherAgentsMunicipaux(aTransaction, code);
}

public static AgentsMunicipaux chercherAgentsMunicipauxService(nc.mairie.technique.Transaction aTransaction, String code,String servi) throws Exception{
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().chercherAgentsMunicipauxService(aTransaction, code,servi);
}

public static ArrayList<AgentsMunicipaux> listerAgentsMunicipauxNom(nc.mairie.technique.Transaction aTransaction,String nom) throws Exception{
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().listerAgentsMunicipauxNom(aTransaction,nom);
}

public static ArrayList<AgentsMunicipaux> listerAgentsMunicipauxNomServi(nc.mairie.technique.Transaction aTransaction,String nom,String servi) throws Exception{
	if(servi.length()>3){
		servi = servi.substring(0,3); //OK
	}
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().listerAgentsMunicipauxNomServi(aTransaction,nom,servi);
}

public static ArrayList<AgentsMunicipaux> listerAgentsMunicipauxServi(nc.mairie.technique.Transaction aTransaction,String servi) throws Exception{
	if(servi.length()>3){
		servi = servi.substring(0,3); //OK
	}
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().listerAgentsMunicipauxServi(aTransaction,servi);
}

}
