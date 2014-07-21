package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;
/**
 * Objet métier PM_Affectation_Sce_Infos
 */
public class PM_Affectation_Sce_Infos extends BasicMetier {
	public String pminv;
	public String pmserie;
	public String codemodele;
	public String codefre;
	public String dgarantie;
	public String dachat;
	public String prix;
	public String dmhs;
	public String dmes;
	public String reserve;
	public String siserv;
	public String ddebut;
	public String dfin;
	public String nomatr;
	public String designationmodele;
	public String designationmarque;
	public String codemarque;
	public String codete;
	public String designationtypeequip;
	public String typete;
/**
 * Getter de l'attribut codefre.
 * @return String
 */
public String getCodefre() {
	return codefre;
}
/**
 * Setter de l'attribut codefre.
 * @param newCodefre newCodefre
 */
public void setCodefre(String newCodefre) { 
	codefre = newCodefre;
}
/**
 * Getter de l'attribut dgarantie.
 * @return String
 */
public String getDgarantie() {
	return dgarantie;
}
/**
 * Setter de l'attribut dgarantie.
 * @param newDgarantie newDgarantie
 */
public void setDgarantie(String newDgarantie) { 
	dgarantie = newDgarantie;
}
/**
 * Getter de l'attribut dachat.
 * @return String
 */
public String getDachat() {
	return dachat;
}
/**
 * Setter de l'attribut dachat.
 * @param newDachat newDachat
 */
public void setDachat(String newDachat) { 
	dachat = newDachat;
}
/**
 * Getter de l'attribut prix.
 * @return String
 */
public String getPrix() {
	return prix;
}
/**
 * Setter de l'attribut prix.
 * @param newPrix newPrix
 */
public void setPrix(String newPrix) { 
	prix = newPrix;
}
/**
 * Getter de l'attribut dmhs.
 * @return String
 */
public String getDmhs() {
	return dmhs;
}
/**
 * Setter de l'attribut dmhs.
 * @param newDmhs newDmhs
 */
public void setDmhs(String newDmhs) { 
	dmhs = newDmhs;
}
/**
 * Getter de l'attribut reserve.
 * @return String
 */
public String getReserve() {
	return reserve;
}
/**
 * Setter de l'attribut reserve.
 * @param newReserve newReserve
 */
public void setReserve(String newReserve) { 
	reserve = newReserve;
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
 * Retourne un ArrayList d'objet métier : PM_Affectation_Sce_Infos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PM_Affectation_Sce_Infos> listerPM_Affectation_Sce_Infos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos = new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().listerPM_Affectation_Sce_Infos(aTransaction);
}
/**
 * Retourne un PM_Affectation_Sce_Infos.
 * @param aTransaction Transaction
 * @param code code
 * @return PM_Affectation_Sce_Infos
 * @throws Exception Exception
 */
public static PM_Affectation_Sce_Infos chercherPM_Affectation_Sce_Infos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos = new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().chercherPM_Affectation_Sce_Infos(aTransaction, code);
}
public static ArrayList<PM_Affectation_Sce_Infos> chercherListPM_Affectation_Sce_InfosPm(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos = new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().chercherListPM_Affectation_Sce_InfosPm(aTransaction, code);
}

public static PM_Affectation_Sce_Infos chercherPM_Affectation_Sce_InfosCourantPm(nc.mairie.technique.Transaction aTransaction, String inv,String date) throws Exception{
	String dateP = "";
	if((date==null)||(date.equals("01/01/0001"))){
		date = Services.dateDuJour();
	}
	dateP = Services.formateDateInternationale(date);
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos = new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().chercherPM_Affectation_Sce_InfosCourantPm(aTransaction, inv,dateP);
}

public static PM_Affectation_Sce_Infos chercherPM_Affectation_Sce_InfosCourantPmEnCours(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception{
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos = new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().chercherPM_Affectation_Sce_InfosCourantPmEnCours(aTransaction, inv);
}


public static ArrayList<PM_Affectation_Sce_Infos> listerPmAffectationSceInfosAgent(nc.mairie.technique.Transaction aTransaction,String nomatr) throws Exception{
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos = new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().listerPmAffectationSceInfosAgent(aTransaction,nomatr);
}

public static ArrayList<PM_Affectation_Sce_Infos> chercherPmAffectationServiceInfosService(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	if(code.substring(code.length()-1,code.length()).equals("0")){
		code = code.substring(0,code.length()-1);
	}
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos= new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().chercherPmAffectationSceInfosService(aTransaction, code);
}

// lors de la gestion par service : possibilité que n'importel quel agent du service (et non département par département) utilise l'appli
public static ArrayList<PM_Affectation_Sce_Infos> chercherPmAffectationServiceInfosService3(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	code = code.substring(0,code.length()-1);
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos= new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().chercherPmAffectationSceInfosService(aTransaction, code);
}

/**
 * Constructeur PM_Affectation_Sce_Infos.
 */
public PM_Affectation_Sce_Infos() {
	super();
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
 * Getter de l'attribut dmes.
 * @return String
 */
public String getDmes() {
	return dmes;
}
/**
 * Setter de l'attribut dmes.
 * @param newDmes newDmes
 */
public void setDmes(String newDmes) { 
	dmes = newDmes;
}
/**
 * Getter de l'attribut codemodele.
 * @return String
 */
public String getCodemodele() {
	return codemodele;
}
/**
 * Setter de l'attribut codemodele.
 * @param newCodemodele newCodemodele
 */
public void setCodemodele(String newCodemodele) { 
	codemodele = newCodemodele;
}
/**
 * Getter de l'attribut designationmodele.
 * @return String
 */
public String getDesignationmodele() {
	return designationmodele;
}
/**
 * Setter de l'attribut designationmodele.
 * @param newDesignationmodele newDesignationmodele
 */
public void setDesignationmodele(String newDesignationmodele) { 
	designationmodele = newDesignationmodele;
}
/**
 * Getter de l'attribut designationmarque.
 * @return String
 */
public String getDesignationmarque() {
	return designationmarque;
}
/**
 * Setter de l'attribut designationmarque.
 * @param newDesignationmarque newDesignationmarque
 */
public void setDesignationmarque(String newDesignationmarque) { 
	designationmarque = newDesignationmarque;
}
/**
 * Getter de l'attribut codemarque.
 * @return String
 */
public String getCodemarque() {
	return codemarque;
}
/**
 * Setter de l'attribut codemarque.
 * @param newCodemarque newCodemarque
 */
public void setCodemarque(String newCodemarque) { 
	codemarque = newCodemarque;
}
/**
 * Getter de l'attribut codete.
 * @return String
 */
public String getCodete() {
	return codete;
}
/**
 * Setter de l'attribut codete.
 * @param newCodete newCodete
 */
public void setCodete(String newCodete) { 
	codete = newCodete;
}
/**
 * Getter de l'attribut designationtypeequip.
 * @return String
 */
public String getDesignationtypeequip() {
	return designationtypeequip;
}
/**
 * Setter de l'attribut designationtypeequip.
 * @param newDesignationtypeequip newDesignationtypeequip
 */
public void setDesignationtypeequip(String newDesignationtypeequip) { 
	designationtypeequip = newDesignationtypeequip;
}
/**
 * Getter de l'attribut typete.
 * @return String
 */
public String getTypete() {
	return typete;
}
/**
 * Setter de l'attribut typete.
 * @param newTypete newTypete
 */
public void setTypete(String newTypete) { 
	typete = newTypete;
}
/**
 * Getter de l'attribut siserv.
 * @return String
 */
public String getSiserv() {
	return siserv;
}
/**
 * Setter de l'attribut siserv.
 * @param newSiserv newSiserv
 */
public void setSiserv(String newSiserv) { 
	siserv = newSiserv;
}
/**
 * Getter de l'attribut ddebut.
 * @return String
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
	return new PM_Affectation_Sce_InfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PM_Affectation_Sce_InfosBroker getMyPM_Affectation_Sce_InfosBroker() {
	return (PM_Affectation_Sce_InfosBroker)getMyBasicBroker();
}
}
