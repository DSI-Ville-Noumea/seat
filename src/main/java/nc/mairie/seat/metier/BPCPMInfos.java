package nc.mairie.seat.metier;

import nc.mairie.technique.Services;
/**
 * Objet métier BPCPMInfos
 */
public class BPCPMInfos extends nc.mairie.technique.BasicMetier {
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
public String getNumerobpc() {
	return numerobpc;
}
/**
 * Setter de l'attribut numerobpc.
 */
public void setNumerobpc(String newNumerobpc) { 
	numerobpc = newNumerobpc;
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
 * Getter de l'attribut heure.
 */
public String getHeure() {
	return heure;
}
/**
 * Setter de l'attribut heure.
 */
public void setHeure(String newHeure) { 
	heure = newHeure;
}
/**
 * Getter de l'attribut valeurcompteur.
 */
public String getValeurcompteur() {
	return valeurcompteur;
}
/**
 * Setter de l'attribut valeurcompteur.
 */
public void setValeurcompteur(String newValeurcompteur) { 
	valeurcompteur = newValeurcompteur;
}
/**
 * Getter de l'attribut numeropompe.
 */
public String getNumeropompe() {
	return numeropompe;
}
/**
 * Setter de l'attribut numeropompe.
 */
public void setNumeropompe(String newNumeropompe) { 
	numeropompe = newNumeropompe;
}
/**
 * Getter de l'attribut quantite.
 */
public String getQuantite() {
	return quantite;
}
/**
 * Setter de l'attribut quantite.
 */
public void setQuantite(String newQuantite) { 
	quantite = newQuantite;
}
/**
 * Getter de l'attribut modedeprise.
 */
public String getModedeprise() {
	return modedeprise;
}
/**
 * Setter de l'attribut modedeprise.
 */
public void setModedeprise(String newModedeprise) { 
	modedeprise = newModedeprise;
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
	return new BPCPMInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
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
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerBPCPMInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	BPCPMInfos unBPCPMInfos = new BPCPMInfos();
	return unBPCPMInfos.getMyBPCPMInfosBroker().listerBPCPMInfos(aTransaction);
}
/**
 * Retourne un BPCPMInfos.
 * @return BPCPMInfos
 */
public static BPCPMInfos chercherBPCPMInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	BPCPMInfos unBPCPMInfos = new BPCPMInfos();
	return unBPCPMInfos.getMyBPCPMInfosBroker().chercherBPCPMInfos(aTransaction, code);
}
/**
 * Retourne un ArrayList d'objet métier : BPCInfosCompletes.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerBPCPMInfosParams(nc.mairie.technique.Transaction aTransaction,String inv,String servi,String ddeb,String dfin) throws Exception{
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
