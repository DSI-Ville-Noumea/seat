package nc.mairie.seat.metier;
/**
 * Objet métier AgentsMunicipaux
 */
public class AgentsMunicipaux extends nc.mairie.technique.BasicMetier {
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new AgentsMunicipauxBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
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
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerAgentsMunicipaux(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().listerAgentsMunicipaux(aTransaction);
}
/**
 * Retourne un AgentsMunicipaux.
 * @return AgentsMunicipaux
 */
public static AgentsMunicipaux chercherAgentsMunicipaux(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().chercherAgentsMunicipaux(aTransaction, code);
}

public static AgentsMunicipaux chercherAgentsMunicipauxService(nc.mairie.technique.Transaction aTransaction, String code,String servi) throws Exception{
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().chercherAgentsMunicipauxService(aTransaction, code,servi);
}

public static java.util.ArrayList listerAgentsMunicipauxNom(nc.mairie.technique.Transaction aTransaction,String nom) throws Exception{
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().listerAgentsMunicipauxNom(aTransaction,nom);
}

public static java.util.ArrayList listerAgentsMunicipauxNomServi(nc.mairie.technique.Transaction aTransaction,String nom,String servi) throws Exception{
	if(servi.length()>3){
		servi = servi.substring(0,3);
	}
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().listerAgentsMunicipauxNomServi(aTransaction,nom,servi);
}

public static java.util.ArrayList listerAgentsMunicipauxServi(nc.mairie.technique.Transaction aTransaction,String servi) throws Exception{
	if(servi.length()>3){
		servi = servi.substring(0,3);
	}
	AgentsMunicipaux unAgentsMunicipaux = new AgentsMunicipaux();
	return unAgentsMunicipaux.getMyAgentsMunicipauxBroker().listerAgentsMunicipauxServi(aTransaction,servi);
}

}
