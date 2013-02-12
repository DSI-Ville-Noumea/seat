package nc.mairie.seat.metier;
/**
 * Objet métier AgentCDESppost
 */
public class AgentCDESppost extends nc.mairie.technique.BasicMetier {
	public String poanne;
	public String ponuor;
	public String cdlieu;
	public String poserv;
	public String ctitre;
	public String poetud;
	public String crespo;
	public String pograd;
	public String pomis1;
	public String pomis2;
	public String pomis3;
	public String pomis4;
	public String pomatr;
	public String pocond;
	public String podval;
	public String podsup;
	public String poserp;
	public String codfon;
	public String codact;
	public String noacti;
/**
 * Constructeur AgentCDESppost.
 */
public AgentCDESppost() {
	super();
}
/**
 * Getter de l'attribut poanne.
 */
public String getPoanne() {
	return poanne;
}
/**
 * Setter de l'attribut poanne.
 */
public void setPoanne(String newPoanne) { 
	poanne = newPoanne;
}
/**
 * Getter de l'attribut ponuor.
 */
public String getPonuor() {
	return ponuor;
}
/**
 * Setter de l'attribut ponuor.
 */
public void setPonuor(String newPonuor) { 
	ponuor = newPonuor;
}
/**
 * Getter de l'attribut cdlieu.
 */
public String getCdlieu() {
	return cdlieu;
}
/**
 * Setter de l'attribut cdlieu.
 */
public void setCdlieu(String newCdlieu) { 
	cdlieu = newCdlieu;
}
/**
 * Getter de l'attribut poserv.
 */
public String getPoserv() {
	return poserv;
}
/**
 * Setter de l'attribut poserv.
 */
public void setPoserv(String newPoserv) { 
	poserv = newPoserv;
}
/**
 * Getter de l'attribut ctitre.
 */
public String getCtitre() {
	return ctitre;
}
/**
 * Setter de l'attribut ctitre.
 */
public void setCtitre(String newCtitre) { 
	ctitre = newCtitre;
}
/**
 * Getter de l'attribut poetud.
 */
public String getPoetud() {
	return poetud;
}
/**
 * Setter de l'attribut poetud.
 */
public void setPoetud(String newPoetud) { 
	poetud = newPoetud;
}
/**
 * Getter de l'attribut crespo.
 */
public String getCrespo() {
	return crespo;
}
/**
 * Setter de l'attribut crespo.
 */
public void setCrespo(String newCrespo) { 
	crespo = newCrespo;
}
/**
 * Getter de l'attribut pograd.
 */
public String getPograd() {
	return pograd;
}
/**
 * Setter de l'attribut pograd.
 */
public void setPograd(String newPograd) { 
	pograd = newPograd;
}
/**
 * Getter de l'attribut pomis1.
 */
public String getPomis1() {
	return pomis1;
}
/**
 * Setter de l'attribut pomis1.
 */
public void setPomis1(String newPomis1) { 
	pomis1 = newPomis1;
}
/**
 * Getter de l'attribut pomis2.
 */
public String getPomis2() {
	return pomis2;
}
/**
 * Setter de l'attribut pomis2.
 */
public void setPomis2(String newPomis2) { 
	pomis2 = newPomis2;
}
/**
 * Getter de l'attribut pomis3.
 */
public String getPomis3() {
	return pomis3;
}
/**
 * Setter de l'attribut pomis3.
 */
public void setPomis3(String newPomis3) { 
	pomis3 = newPomis3;
}
/**
 * Getter de l'attribut pomis4.
 */
public String getPomis4() {
	return pomis4;
}
/**
 * Setter de l'attribut pomis4.
 */
public void setPomis4(String newPomis4) { 
	pomis4 = newPomis4;
}
/**
 * Getter de l'attribut pomatr.
 */
public String getPomatr() {
	return pomatr;
}
/**
 * Setter de l'attribut pomatr.
 */
public void setPomatr(String newPomatr) { 
	pomatr = newPomatr;
}
/**
 * Getter de l'attribut pocond.
 */
public String getPocond() {
	return pocond;
}
/**
 * Setter de l'attribut pocond.
 */
public void setPocond(String newPocond) { 
	pocond = newPocond;
}
/**
 * Getter de l'attribut podval.
 */
public String getPodval() {
	return podval;
}
/**
 * Setter de l'attribut podval.
 */
public void setPodval(String newPodval) { 
	podval = newPodval;
}
/**
 * Getter de l'attribut podsup.
 */
public String getPodsup() {
	return podsup;
}
/**
 * Setter de l'attribut podsup.
 */
public void setPodsup(String newPodsup) { 
	podsup = newPodsup;
}
/**
 * Getter de l'attribut poserp.
 */
public String getPoserp() {
	return poserp;
}
/**
 * Setter de l'attribut poserp.
 */
public void setPoserp(String newPoserp) { 
	poserp = newPoserp;
}
/**
 * Getter de l'attribut codfon.
 */
public String getCodfon() {
	return codfon;
}
/**
 * Setter de l'attribut codfon.
 */
public void setCodfon(String newCodfon) { 
	codfon = newCodfon;
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
 * Getter de l'attribut noacti.
 */
public String getNoacti() {
	return noacti;
}
/**
 * Setter de l'attribut noacti.
 */
public void setNoacti(String newNoacti) { 
	noacti = newNoacti;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new AgentCDESppostBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected AgentCDESppostBroker getMyAgentCDESppostBroker() {
	return (AgentCDESppostBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : AgentCDESppost.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerAgentCDESppost(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AgentCDESppost unAgentCDESppost = new AgentCDESppost();
	return unAgentCDESppost.getMyAgentCDESppostBroker().listerAgentCDESppost(aTransaction);
}
/**
 * Retourne un AgentCDESppost.
 * @return AgentCDESppost
 */
public static AgentCDESppost chercherAgentCDESppost(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AgentCDESppost unAgentCDESppost = new AgentCDESppost();
	return unAgentCDESppost.getMyAgentCDESppostBroker().chercherAgentCDESppost(aTransaction, code);
}
}
