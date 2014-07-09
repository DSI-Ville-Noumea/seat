package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier AffectationServiceInfos
 */
public class AffectationServiceInfos extends BasicMetier {
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
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<AffectationServiceInfos> listerAffectationServiceInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().listerAffectationServiceInfos(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : AffectationServiceInfos.
 * @param aTransaction Transaction
 * @param nomatr nomatr
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<AffectationServiceInfos> listerAffectationServiceInfosAgent(nc.mairie.technique.Transaction aTransaction,String nomatr) throws Exception{
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().listerAffectationServiceInfosAgent(aTransaction,nomatr);
}
/**
 * Retourne un AffectationServiceInfos.
 * @param aTransaction Transaction
 * @param code code
 * @return AffectationServiceInfos
 * @throws Exception Exception
 */
public static ArrayList<AffectationServiceInfos> chercherAffectationServiceInfosService(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	if(code.substring(code.length()-1,code.length()).equals("0")){
		code = code.substring(0,code.length()-1);
	}
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().chercherAffectationServiceInfosService(aTransaction, code);
}

//lors de la gestion par service : possibilité que n'importel quel agent du service (et non département par département) utilise l'appli
public static ArrayList<AffectationServiceInfos> chercherAffectationServiceInfosService3(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	code = code.substring(0,code.length()-1);
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().chercherAffectationServiceInfosService(aTransaction, code);
}

/**
 * Retourne un AffectationServiceInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @param date date
 * @return AffectationServiceInfos
 * @throws Exception Exception
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
 * @param aTransaction Transaction
 * @param code code
 * @return AffectationServiceInfos
 * @throws Exception Exception
 */
public static ArrayList<AffectationServiceInfos> chercherAffectationServiceInfosEquip(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AffectationServiceInfos unAffectationServiceInfos = new AffectationServiceInfos();
	return unAffectationServiceInfos.getMyAffectationServiceInfosBroker().chercherAffectationServiceInfosEquip(aTransaction, code);
}
/**
 * Retourne un AffectationServiceInfos.
 * @param aTransaction Transaction
 * @param code code
 * @return AffectationServiceInfos
 * @throws Exception Exception
 */
public static ArrayList<AffectationServiceInfos> chercherListAffectationServiceInfosEquip(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
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
 * @return numeroinventaire
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 * @param newNumeroinventaire newNumeroinventaire
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
}
/**
 * Getter de l'attribut numeroimmatriculation.
 * @return numeroimmatriculation
 */
public String getNumeroimmatriculation() {
	return numeroimmatriculation;
}
/**
 * Setter de l'attribut numeroimmatriculation.
 * @param newNumeroimmatriculation newNumeroimmatriculation
 */
public void setNumeroimmatriculation(String newNumeroimmatriculation) { 
	numeroimmatriculation = newNumeroimmatriculation;
}
/**
 * Getter de l'attribut datemiseencirculation.
 * @return datemiseencirculation
 */
public String getDatemiseencirculation() {
	return datemiseencirculation;
}
/**
 * Setter de l'attribut datemiseencirculation.
 * @param newDatemiseencirculation newDatemiseencirculation
 */
public void setDatemiseencirculation(String newDatemiseencirculation) { 
	datemiseencirculation = newDatemiseencirculation;
}
/**
 * Getter de l'attribut codeservice.
 * @return codeservice
 */
public String getCodeservice() {
	return codeservice;
}
/**
 * Setter de l'attribut codeservice.
 * @param newCodeservice newCodeservice
 */
public void setCodeservice(String newCodeservice) { 
	codeservice = newCodeservice;
}
/**
 * Getter de l'attribut ddebut.
 * @return ddebut
 */
public String getDdebut() {
	return ddebut;
}
/**
 * Setter de l'attribut ddebut.
 * @param newDdebut newDdebut
 */
public void setDdebut(String newDdebut) { 
	ddebut = newDdebut;
}
/**
 * Getter de l'attribut dfin.
 * @return String
 */
public String getDfin() {
	return dfin;
}
/**
 * Setter de l'attribut dfin.
 * @param newDfin newDfin
 */
public void setDfin(String newDfin) { 
	dfin = newDfin;
}
/**
 * Getter de l'attribut liserv.
 * @return String
 */
public String getLiserv() {
	return liserv;
}
/**
 * Setter de l'attribut liserv.
 * @param newLiserv newLiserv
 */
public void setLiserv(String newLiserv) { 
	liserv = newLiserv;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AffectationServiceInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
 * @return BasicBroker
*/
protected AffectationServiceInfosBroker getMyAffectationServiceInfosBroker() {
	return (AffectationServiceInfosBroker)getMyBasicBroker();
}
}
