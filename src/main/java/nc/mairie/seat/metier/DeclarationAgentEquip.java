package nc.mairie.seat.metier;

/**
 * Objet métier DeclarationAgentEquip
 */
public class DeclarationAgentEquip extends nc.mairie.technique.BasicMetier {
	public String codedec;
	public String date;
	public String immat;
	public String declarant;
	public String codeot;
	public String codeservice;
/**
 * Constructeur DeclarationAgentEquip.
 */
public DeclarationAgentEquip() {
	super();
}
/**
 * Getter de l'attribut codedec.
 */
public String getCodedec() {
	return codedec;
}
/**
 * Setter de l'attribut codedec.
 */
public void setCodedec(String newCodedec) { 
	codedec = newCodedec;
}
/**
 * Getter de l'attribut date.
 */
public String getDate() {
	return date;
}
/**
 * Setter de l'attribut date.
 */
public void setDate(String newDate) { 
	date = newDate;
}
/**
 * Getter de l'attribut immat.
 */
public String getImmat() {
	return immat;
}
/**
 * Setter de l'attribut immat.
 */
public void setImmat(String newImmat) { 
	immat = newImmat;
}
/**
 * Getter de l'attribut declarant.
 */
public String getDeclarant() {
	return declarant;
}
/**
 * Setter de l'attribut declarant.
 */
public void setDeclarant(String newDeclarant) { 
	declarant = newDeclarant;
}
/**
 * Getter de l'attribut codeot.
 */
public String getCodeot() {
	return codeot;
}
/**
 * Setter de l'attribut codeot.
 */
public void setCodeot(String newCodeot) { 
	codeot = newCodeot;
}
/**
 * Getter de l'attribut codeservice.
 */
public String getCodeservice() {
	return codeservice;
}
/**
 * Setter de l'attribut codeservice.
 */
public void setCodeservice(String newCodeservice) { 
	codeservice = newCodeservice;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new DeclarationAgentEquipBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected DeclarationAgentEquipBroker getMyDeclarationAgentEquipBroker() {
	return (DeclarationAgentEquipBroker)getMyBasicBroker();
}
/**
* Renvoie une chaîne correspondant à la valeur de cet objet.
* @return une représentation sous forme de chaîne du destinataire
*/
public String toString() {
	// Insérez ici le code pour finaliser le destinataire
	// Cette implémentation transmet le message au super. Vous pouvez remplacer ou compléter le message.
	return super.toString();
}
/**
 * Retourne un ArrayList d'objet métier : DeclarationAgentEquip.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerDeclarationAgentEquip(nc.mairie.technique.Transaction aTransaction) throws Exception{
	DeclarationAgentEquip unDeclarationAgentEquip = new DeclarationAgentEquip();
	return unDeclarationAgentEquip.getMyDeclarationAgentEquipBroker().listerDeclarationAgentEquip(aTransaction);
}
}
