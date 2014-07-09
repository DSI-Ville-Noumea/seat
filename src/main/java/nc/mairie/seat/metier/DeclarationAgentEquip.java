package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier DeclarationAgentEquip
 */
public class DeclarationAgentEquip extends BasicMetier {
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
/**
 * @return String
 */
public String getCodedec() {
	return codedec;
}
/**
 * Setter de l'attribut codedec.
 */
/**
 * @param newCodedec newCodedec
 */
public void setCodedec(String newCodedec) { 
	codedec = newCodedec;
}
/**
 * Getter de l'attribut date.
 */
/**
 * @return String
 */
public String getDate() {
	return date;
}
/**
 * Setter de l'attribut date.
 */
/**
 * @param newDate newDate
 */
public void setDate(String newDate) { 
	date = newDate;
}
/**
 * Getter de l'attribut immat.
 */
/**
 * @return String
 */
public String getImmat() {
	return immat;
}
/**
 * Setter de l'attribut immat.
 */
/**
 * @param newImmat newImmat
 */
public void setImmat(String newImmat) { 
	immat = newImmat;
}
/**
 * Getter de l'attribut declarant.
 */
/**
 * @return String
 */
public String getDeclarant() {
	return declarant;
}
/**
 * Setter de l'attribut declarant.
 */
/**
 * @param newDeclarant newDeclarant
 */
public void setDeclarant(String newDeclarant) { 
	declarant = newDeclarant;
}
/**
 * Getter de l'attribut codeot.
 */
/**
 * @return String
 */
public String getCodeot() {
	return codeot;
}
/**
 * Setter de l'attribut codeot.
 */
/**
 * @param newCodeot newCodeot
 */
public void setCodeot(String newCodeot) { 
	codeot = newCodeot;
}
/**
 * Getter de l'attribut codeservice.
 */
/**
 * @return String
 */
public String getCodeservice() {
	return codeservice;
}
/**
 * Setter de l'attribut codeservice.
 */
/**
 * @param newCodeservice newCodeservice
 */
public void setCodeservice(String newCodeservice) { 
	codeservice = newCodeservice;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected BasicBroker definirMyBroker() { 
	return new DeclarationAgentEquipBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
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
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception exception
 */
public static ArrayList<DeclarationAgentEquip> listerDeclarationAgentEquip(nc.mairie.technique.Transaction aTransaction) throws Exception{
	DeclarationAgentEquip unDeclarationAgentEquip = new DeclarationAgentEquip();
	return unDeclarationAgentEquip.getMyDeclarationAgentEquipBroker().listerDeclarationAgentEquip(aTransaction);
}
}
