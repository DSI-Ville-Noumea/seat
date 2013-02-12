package nc.mairie.seat.metier;


import nc.mairie.technique.Services;
/**
 * Objet métier AffectationServiceInfos
 */
public class AffectationServiceInfos extends nc.mairie.technique.BasicMetier {
	public String numeroinventaire;
	public String numeroimmatriculation;
	public String datemiseencirculation;
	public String codeservice;
	public String ddebut;
	public String dfin;
	public String liserv;
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
 * Retourne un ArrayList d'objet métier : AffectationServiceInfos.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerAffectationServiceInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().listerAffectationServiceInfos(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : AffectationServiceInfos.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerAffectationServiceInfosAgent(nc.mairie.technique.Transaction aTransaction,String nomatr) throws Exception{
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().listerAffectationServiceInfosAgent(aTransaction,nomatr);
}
/**
 * Retourne un AffectationServiceInfos.
 * @return AffectationServiceInfos
 */
public static java.util.ArrayList chercherAffectationServiceInfosService(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	if(code.substring(code.length()-1,code.length()).equals("0")){
		code = code.substring(0,code.length()-1);
	}
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().chercherAffectationServiceInfosService(aTransaction, code);
}

//lors de la gestion par service : possibilité que n'importel quel agent du service (et non département par département) utilise l'appli
public static java.util.ArrayList chercherAffectationServiceInfosService3(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	code = code.substring(0,code.length()-1);
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().chercherAffectationServiceInfosService(aTransaction, code);
}

/**
 * Retourne un AffectationServiceInfos.
 * @return AffectationServiceInfos
 */
public static AffectationServiceInfos chercherAffectationServiceInfosCourantEquip(nc.mairie.technique.Transaction aTransaction, String inv,String date) throws Exception{
	String dateP = "";
	if((date==null)||(date.equals("01/01/0001"))||date.equals("")){
		date = Services.dateDuJour();
	}
	dateP = Services.formateDateInternationale(date);
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().chercherAffectationServiceInfosCourantEquip(aTransaction, inv,dateP);
}

/**
 * Retourne un AffectationServiceInfos.
 * @return AffectationServiceInfos
 */
public static java.util.ArrayList chercherAffectationServiceInfosEquip(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().chercherAffectationServiceInfosEquip(aTransaction, code);
}
/**
 * Retourne un AffectationServiceInfos.
 * @return AffectationServiceInfos
 */
public static java.util.ArrayList chercherListAffectationServiceInfosEquip(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().chercherListAffectationServiceInfosEquip(aTransaction, code);
}
	public String nomatr;
/**
 * Constructeur AffectationServiceInfos.
 */
public AffectationServiceInfos() {
	super();
}
/**
 * Getter de l'attribut numeroinventaire.
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
}
/**
 * Getter de l'attribut numeroimmatriculation.
 */
public String getNumeroimmatriculation() {
	return numeroimmatriculation;
}
/**
 * Setter de l'attribut numeroimmatriculation.
 */
public void setNumeroimmatriculation(String newNumeroimmatriculation) { 
	numeroimmatriculation = newNumeroimmatriculation;
}
/**
 * Getter de l'attribut datemiseencirculation.
 */
public String getDatemiseencirculation() {
	return datemiseencirculation;
}
/**
 * Setter de l'attribut datemiseencirculation.
 */
public void setDatemiseencirculation(String newDatemiseencirculation) { 
	datemiseencirculation = newDatemiseencirculation;
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
 * Getter de l'attribut ddebut.
 */
public String getDdebut() {
	return ddebut;
}
/**
 * Setter de l'attribut ddebut.
 */
public void setDdebut(String newDdebut) { 
	ddebut = newDdebut;
}
/**
 * Getter de l'attribut dfin.
 */
public String getDfin() {
	return dfin;
}
/**
 * Setter de l'attribut dfin.
 */
public void setDfin(String newDfin) { 
	dfin = newDfin;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new AffectationServiceInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected AffectationServiceInfosBroker getMyAffectationServiceInfosBroker() {
	return (AffectationServiceInfosBroker)getMyBasicBroker();
}
}
