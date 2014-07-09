package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier BPCPMInfos
 */
public class BPCPMInfos extends BasicMetier {
	public String numerobpc;
	public String date;
	public String heure;
	public String valeurcompteur;
	public String numeropompe;
	public String quantite;
	public String modedeprise;
	public String numeroinventaire;
	public String pmserie;
	public String dmes;
	public String siserv;
	public String ddebut;
	public String dfin;
	public String liserv;
	public String nomatr;
/**
 * Constructeur BPCPMInfos.
 */
public BPCPMInfos() {
	super();
}
/**
 * Getter de l'attribut numerobpc.
 */
/**
 * @return String
 */
public String getNumerobpc() {
	return numerobpc;
}
/**
 * Setter de l'attribut numerobpc.
 */
/**
 * @param newNumerobpc newNumerobpc
 */
public void setNumerobpc(String newNumerobpc) { 
	numerobpc = newNumerobpc;
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
 * Getter de l'attribut heure.
 */
/**
 * @return String
 */
public String getHeure() {
	return heure;
}
/**
 * Setter de l'attribut heure.
 */
/**
 * @param newHeure newHeure
 */
public void setHeure(String newHeure) { 
	heure = newHeure;
}
/**
 * Getter de l'attribut valeurcompteur.
 */
/**
 * @return String
 */
public String getValeurcompteur() {
	return valeurcompteur;
}
/**
 * Setter de l'attribut valeurcompteur.
 */
/**
 * @param newValeurcompteur newValeurcompteur
 */
public void setValeurcompteur(String newValeurcompteur) { 
	valeurcompteur = newValeurcompteur;
}
/**
 * Getter de l'attribut numeropompe.
 */
/**
 * @return String
 */
public String getNumeropompe() {
	return numeropompe;
}
/**
 * Setter de l'attribut numeropompe.
 */
/**
 * @param newNumeropompe newNumeropompe
 */
public void setNumeropompe(String newNumeropompe) { 
	numeropompe = newNumeropompe;
}
/**
 * Getter de l'attribut quantite.
 */
/**
 * @return String
 */
public String getQuantite() {
	return quantite;
}
/**
 * Setter de l'attribut quantite.
 */
/**
 * @param newQuantite newQuantite
 */
public void setQuantite(String newQuantite) { 
	quantite = newQuantite;
}
/**
 * Getter de l'attribut modedeprise.
 */
/**
 * @return String
 */
public String getModedeprise() {
	return modedeprise;
}
/**
 * Setter de l'attribut modedeprise.
 */
/**
 * @param newModedeprise newModedeprise
 */
public void setModedeprise(String newModedeprise) { 
	modedeprise = newModedeprise;
}
/**
 * Getter de l'attribut numeroinventaire.
 */
/**
 * @return String
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 */
/**
 * @param newNumeroinventaire newNumeroinventaire
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
}
/**
 * Getter de l'attribut pmserie.
 */
/**
 * @return String
 */
public String getPmserie() {
	return pmserie;
}
/**
 * Setter de l'attribut pmserie.
 */
/**
 * @param newPmserie newPmserie
 */
public void setPmserie(String newPmserie) { 
	pmserie = newPmserie;
}
/**
 * Getter de l'attribut dmes.
 */
/**
 * @return String
 */
public String getDmes() {
	return dmes;
}
/**
 * Setter de l'attribut dmes.
 */
/**
 * @param newDmes newDmes
 */
public void setDmes(String newDmes) { 
	dmes = newDmes;
}
/**
 * Getter de l'attribut siserv.
 */
/**
 * @return String
 */
public String getSiserv() {
	return siserv;
}
/**
 * Setter de l'attribut siserv.
 */
/**
 * @param newSiserv newSiserv
 */
public void setSiserv(String newSiserv) { 
	siserv = newSiserv;
}
/**
 * Getter de l'attribut ddebut.
 */
/**
 * @return String
 */
public String getDdebut() {
	return ddebut;
}
/**
 * Setter de l'attribut ddebut.
 */
/**
 * @param newDdebut newDdebut
 */
public void setDdebut(String newDdebut) { 
	ddebut = newDdebut;
}
/**
 * Getter de l'attribut dfin.
 */
/**
 * @return String
 */
public String getDfin() {
	return dfin;
}
/**
 * Setter de l'attribut dfin.
 */
/**
 * @param newDfin newDfin
 */
public void setDfin(String newDfin) { 
	dfin = newDfin;
}
/**
 * Getter de l'attribut liserv.
 */
/**
 * @return String
 */
public String getLiserv() {
	return liserv;
}
/**
 * Setter de l'attribut liserv.
 */
/**
 * @param newLiserv newLiserv
 */
public void setLiserv(String newLiserv) { 
	liserv = newLiserv;
}
/**
 * Getter de l'attribut nomatr.
 */
/**
 * @return String
 */
public String getNomatr() {
	return nomatr;
}
/**
 * Setter de l'attribut nomatr.
 */
/**
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
	return new BPCPMInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected BPCPMInfosBroker getMyBPCPMInfosBroker() {
	return (BPCPMInfosBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : BPCPMInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<BPCPMInfos> listerBPCPMInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	BPCPMInfos unBPCPMInfos = new BPCPMInfos();
	return unBPCPMInfos.getMyBPCPMInfosBroker().listerBPCPMInfos(aTransaction);
}
/**
 * Retourne un BPCPMInfos.
 * @param aTransaction Transaction
 * @param code code
 * @return BPCPMInfos
 * @throws Exception Exception
 */
public static BPCPMInfos chercherBPCPMInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	BPCPMInfos unBPCPMInfos = new BPCPMInfos();
	return unBPCPMInfos.getMyBPCPMInfosBroker().chercherBPCPMInfos(aTransaction, code);
}
/**
 * Retourne un ArrayList d'objet métier : BPCInfosCompletes.
 * @param aTransaction Transaction
 * @param inv inv
 * @param servi servi
 * @param ddeb ddeb
 * @param dfin dfin
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<BPCPMInfos> listerBPCPMInfosParams(nc.mairie.technique.Transaction aTransaction,String inv,String servi,String ddeb,String dfin) throws Exception{
	String periode = "";
	if(inv.equals("")){
		inv="";
	}else{
		inv = "numeroinventaire like '"+inv+"%' ";
	}
	if(servi.equals("")){
		servi="";
	}else{
		servi = " siserv like '"+servi+"'";
	}
	if(ddeb.equals("")&&(dfin.equals(""))){
		periode = "";
	}
	if(!ddeb.equals("")&&(!dfin.equals(""))){
		ddeb = Services.formateDateInternationale(ddeb);
		dfin = Services.formateDateInternationale(dfin);
		periode = " date<='"+dfin+"' and date>='"+ddeb+"' ";
	}
	if(!ddeb.equals("")&&(dfin.equals(""))){
		ddeb = Services.formateDateInternationale(ddeb);
		periode = " date='"+ddeb+"' ";
	}
	if(ddeb.equals("")&&(!dfin.equals(""))){
		dfin = Services.formateDateInternationale(dfin);
		periode = " date='"+dfin+"' ";
	}
	
	BPCPMInfos unBPCPMInfos = new BPCPMInfos();
	return unBPCPMInfos.getMyBPCPMInfosBroker().listerBPCPMInfosParams(aTransaction,inv,servi,periode);
}
}
