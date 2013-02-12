package nc.mairie.seat.metier;

import nc.mairie.technique.Services;


/**
 * Objet métier PM_Affectation_Sce_Infos
 */
public class PM_Affectation_Sce_Infos extends nc.mairie.technique.BasicMetier {
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
 */
public String getCodefre() {
	return codefre;
}
/**
 * Setter de l'attribut codefre.
 */
public void setCodefre(String newCodefre) { 
	codefre = newCodefre;
}
/**
 * Getter de l'attribut dgarantie.
 */
public String getDgarantie() {
	return dgarantie;
}
/**
 * Setter de l'attribut dgarantie.
 */
public void setDgarantie(String newDgarantie) { 
	dgarantie = newDgarantie;
}
/**
 * Getter de l'attribut dachat.
 */
public String getDachat() {
	return dachat;
}
/**
 * Setter de l'attribut dachat.
 */
public void setDachat(String newDachat) { 
	dachat = newDachat;
}
/**
 * Getter de l'attribut prix.
 */
public String getPrix() {
	return prix;
}
/**
 * Setter de l'attribut prix.
 */
public void setPrix(String newPrix) { 
	prix = newPrix;
}
/**
 * Getter de l'attribut dmhs.
 */
public String getDmhs() {
	return dmhs;
}
/**
 * Setter de l'attribut dmhs.
 */
public void setDmhs(String newDmhs) { 
	dmhs = newDmhs;
}
/**
 * Getter de l'attribut reserve.
 */
public String getReserve() {
	return reserve;
}
/**
 * Setter de l'attribut reserve.
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
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPM_Affectation_Sce_Infos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos = new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().listerPM_Affectation_Sce_Infos(aTransaction);
}
/**
 * Retourne un PM_Affectation_Sce_Infos.
 * @return PM_Affectation_Sce_Infos
 */
public static PM_Affectation_Sce_Infos chercherPM_Affectation_Sce_Infos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos = new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().chercherPM_Affectation_Sce_Infos(aTransaction, code);
}
public static java.util.ArrayList chercherListPM_Affectation_Sce_InfosPm(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
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


public static java.util.ArrayList listerPmAffectationSceInfosAgent(nc.mairie.technique.Transaction aTransaction,String nomatr) throws Exception{
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos = new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().listerPmAffectationSceInfosAgent(aTransaction,nomatr);
}

public static java.util.ArrayList chercherPmAffectationServiceInfosService(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	if(code.substring(code.length()-1,code.length()).equals("0")){
		code = code.substring(0,code.length()-1);
	}
	PM_Affectation_Sce_Infos unPM_Affectation_Sce_Infos= new PM_Affectation_Sce_Infos();
	return unPM_Affectation_Sce_Infos.getMyPM_Affectation_Sce_InfosBroker().chercherPmAffectationSceInfosService(aTransaction, code);
}

// lors de la gestion par service : possibilité que n'importel quel agent du service (et non département par département) utilise l'appli
public static java.util.ArrayList chercherPmAffectationServiceInfosService3(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
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
 */
public String getPminv() {
	return pminv;
}
/**
 * Setter de l'attribut pminv.
 */
public void setPminv(String newPminv) { 
	pminv = newPminv;
}
/**
 * Getter de l'attribut pmserie.
 */
public String getPmserie() {
	return pmserie;
}
/**
 * Setter de l'attribut pmserie.
 */
public void setPmserie(String newPmserie) { 
	pmserie = newPmserie;
}
/**
 * Getter de l'attribut dmes.
 */
public String getDmes() {
	return dmes;
}
/**
 * Setter de l'attribut dmes.
 */
public void setDmes(String newDmes) { 
	dmes = newDmes;
}
/**
 * Getter de l'attribut codemodele.
 */
public String getCodemodele() {
	return codemodele;
}
/**
 * Setter de l'attribut codemodele.
 */
public void setCodemodele(String newCodemodele) { 
	codemodele = newCodemodele;
}
/**
 * Getter de l'attribut designationmodele.
 */
public String getDesignationmodele() {
	return designationmodele;
}
/**
 * Setter de l'attribut designationmodele.
 */
public void setDesignationmodele(String newDesignationmodele) { 
	designationmodele = newDesignationmodele;
}
/**
 * Getter de l'attribut designationmarque.
 */
public String getDesignationmarque() {
	return designationmarque;
}
/**
 * Setter de l'attribut designationmarque.
 */
public void setDesignationmarque(String newDesignationmarque) { 
	designationmarque = newDesignationmarque;
}
/**
 * Getter de l'attribut codemarque.
 */
public String getCodemarque() {
	return codemarque;
}
/**
 * Setter de l'attribut codemarque.
 */
public void setCodemarque(String newCodemarque) { 
	codemarque = newCodemarque;
}
/**
 * Getter de l'attribut codete.
 */
public String getCodete() {
	return codete;
}
/**
 * Setter de l'attribut codete.
 */
public void setCodete(String newCodete) { 
	codete = newCodete;
}
/**
 * Getter de l'attribut designationtypeequip.
 */
public String getDesignationtypeequip() {
	return designationtypeequip;
}
/**
 * Setter de l'attribut designationtypeequip.
 */
public void setDesignationtypeequip(String newDesignationtypeequip) { 
	designationtypeequip = newDesignationtypeequip;
}
/**
 * Getter de l'attribut typete.
 */
public String getTypete() {
	return typete;
}
/**
 * Setter de l'attribut typete.
 */
public void setTypete(String newTypete) { 
	typete = newTypete;
}
/**
 * Getter de l'attribut siserv.
 */
public String getSiserv() {
	return siserv;
}
/**
 * Setter de l'attribut siserv.
 */
public void setSiserv(String newSiserv) { 
	siserv = newSiserv;
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
	return new PM_Affectation_Sce_InfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected PM_Affectation_Sce_InfosBroker getMyPM_Affectation_Sce_InfosBroker() {
	return (PM_Affectation_Sce_InfosBroker)getMyBasicBroker();
}
}
