package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PM_AffectAgentsInfos
 */
public class PM_AffectAgentsInfos extends BasicMetier {
	public String matricule;
	public String pminv;
	public String ddeb;
	public String dfin;
	public String hdeb;
	public String hfin;
	public String hdebmn;
	public String hfinmn;
	public String pmserie;
	public String codesce;
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
 * Retourne un ArrayList d'objet métier : PM_AffectAgentsInfos.
 * @param aTransaction Transaction
 * @param servi servi
 * @param inv inv
 * @param date date
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PM_AffectAgentsInfos> listerPM_AffectAgentsInfosScePMatDate(nc.mairie.technique.Transaction aTransaction,String servi,String inv, String date) throws Exception {
	PM_AffectAgentsInfos unPM_AffectAgentsInfos = new PM_AffectAgentsInfos();
	return unPM_AffectAgentsInfos.getMyPM_AffectAgentsInfosBroker().listerPM_AffectAgentInfosScePMatDate(aTransaction,servi,inv,date);
}
public static ArrayList<PM_AffectAgentsInfos> listerPM_AffectAgentsInfos(nc.mairie.technique.Transaction aTransaction,String servi,String inv, String date) throws Exception {
	PM_AffectAgentsInfos unPM_AffectAgentsInfos = new PM_AffectAgentsInfos();
	return unPM_AffectAgentsInfos.getMyPM_AffectAgentsInfosBroker().listerPM_AffectAgentsInfos(aTransaction);
}
/**
 * Retourne un PM_AffectAgentsInfos.
 * @param aTransaction Transaction
 * @param servi servi
 * @return PM_AffectAgentsInfos
 * @throws Exception Exception
 */
public static ArrayList<PM_AffectAgentsInfos> chercherPM_AffectAgentsInfosSce(nc.mairie.technique.Transaction aTransaction, String servi) throws Exception{
	PM_AffectAgentsInfos unPM_AffectAgentsInfos = new PM_AffectAgentsInfos();
	return unPM_AffectAgentsInfos.getMyPM_AffectAgentsInfosBroker().chercherPM_AffectAgentsInfosSce(aTransaction, servi);
}

public static ArrayList<PM_AffectAgentsInfos> chercherPM_AffectAgentsInfosScePM(nc.mairie.technique.Transaction aTransaction, String inv, String servi) throws Exception{
	PM_AffectAgentsInfos unPM_AffectAgentsInfos = new PM_AffectAgentsInfos();
	return unPM_AffectAgentsInfos.getMyPM_AffectAgentsInfosBroker().chercherPM_AffectAgentsInfosScePM(aTransaction, inv, servi);
}

/**
 * Constructeur PM_AffectAgentsInfos.
 */
public PM_AffectAgentsInfos() {
	super();
}
/**
 * Getter de l'attribut matricule.
 * @return String
 */
public String getMatricule() {
	return matricule;
}
/**
 * Setter de l'attribut matricule.
 * @param newMatricule newMatricule
 */
public void setMatricule(String newMatricule) { 
	matricule = newMatricule;
}
/**
 * Getter de l'attribut pminv.
 * @return String
 */
public String getPminv() {
	return pminv;
}
/**
 * Setter de l'attribut pminv.
 * @param newPminv newPminv
 */
public void setPminv(String newPminv) { 
	pminv = newPminv;
}
/**
 * Getter de l'attribut ddeb.
 * @return String
 */
public String getDdeb() {
	return ddeb;
}
/**
 * Setter de l'attribut ddeb.
 * @param newDdeb newDdeb
 */
public void setDdeb(String newDdeb) { 
	ddeb = newDdeb;
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
 * Getter de l'attribut hdeb.
 * @return String
 */
public String getHdeb() {
	return hdeb;
}
/**
 * Setter de l'attribut hdeb.
 * @param newHdeb newHdeb
 */
public void setHdeb(String newHdeb) { 
	hdeb = newHdeb;
}
/**
 * Getter de l'attribut hfin.
 * @return String
 */
public String getHfin() {
	return hfin;
}
/**
 * Setter de l'attribut hfin.
 * @param newHfin newHfin
 */
public void setHfin(String newHfin) { 
	hfin = newHfin;
}
/**
 * Getter de l'attribut hdebmn.
 * @return String
 */
public String getHdebmn() {
	return hdebmn;
}
/**
 * Setter de l'attribut hdebmn.
 * @param newHdebmn newHdebmn
 */
public void setHdebmn(String newHdebmn) { 
	hdebmn = newHdebmn;
}
/**
 * Getter de l'attribut hfinmn.
 * @return String
 */
public String getHfinmn() {
	return hfinmn;
}
/**
 * Setter de l'attribut hfinmn.
 * @param newHfinmn newHfinmn
 */
public void setHfinmn(String newHfinmn) { 
	hfinmn = newHfinmn;
}
/**
 * Getter de l'attribut pmserie.
 * @return String
 */
public String getPmserie() {
	return pmserie;
}
/**
 * Setter de l'attribut pmserie.
 * @param newPmserie newPmserie
 */
public void setPmserie(String newPmserie) { 
	pmserie = newPmserie;
}
/**
 * Getter de l'attribut codesce.
 * @return String
 */
public String getCodesce() {
	return codesce;
}
/**
 * Setter de l'attribut codesce.
 * @param newCodesce newCodesce
 */
public void setCodesce(String newCodesce) { 
	codesce = newCodesce;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PM_AffectAgentsInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PM_AffectAgentsInfosBroker getMyPM_AffectAgentsInfosBroker() {
	return (PM_AffectAgentsInfosBroker)getMyBasicBroker();
}
}
