package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier BPCInfosCompletes
 */
public class BPCInfosCompletes extends BasicMetier {
	public String numerobpc;
	public String date;
	public String heure;
	public String valeurcompteur;
	public String numeropompe;
	public String quantite;
	public String modedeprise;
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
 * Retourne un ArrayList d'objet métier : BPCInfosCompletes.
 * @return java.util.ArrayList
 */
public static ArrayList<BPCInfosCompletes> listerBPCInfosCompletes(nc.mairie.technique.Transaction aTransaction) throws Exception{
	BPCInfosCompletes unBPCInfosCompletes = new BPCInfosCompletes();
	return unBPCInfosCompletes.getMyBPCInfosCompletesBroker().listerBPCInfosCompletes(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : BPCInfosCompletes.
 * @return java.util.ArrayList
 */
public static ArrayList<BPCInfosCompletes> listerBPCInfosCompletesParams(nc.mairie.technique.Transaction aTransaction,String inv,String servi,String ddeb,String dfin) throws Exception{
	String periode = "";
	if(inv.equals("")){
		inv="";
	}else{
		inv = "numeroinventaire like '"+inv+"%' ";
	}
	if(servi.equals("")){
		servi="";
	}else{
		servi = " codeservice like '"+servi+"'";
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
	
	
	BPCInfosCompletes unBPCInfosCompletes = new BPCInfosCompletes();
	return unBPCInfosCompletes.getMyBPCInfosCompletesBroker().listerBPCInfosCompletesParams(aTransaction,inv,servi,periode);
}

/**
 * Retourne un BPCInfosCompletes.
 * @return BPCInfosCompletes
 */
public static BPCInfosCompletes chercherBPCInfosCompletes(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	BPCInfosCompletes unBPCInfosCompletes = new BPCInfosCompletes();
	return unBPCInfosCompletes.getMyBPCInfosCompletesBroker().chercherBPCInfosCompletes(aTransaction, code);
}
	public String nomatr;
/**
 * Constructeur BPCInfosCompletes.
 */
public BPCInfosCompletes() {
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
protected BasicBroker definirMyBroker() { 
	return new BPCInfosCompletesBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected BPCInfosCompletesBroker getMyBPCInfosCompletesBroker() {
	return (BPCInfosCompletesBroker)getMyBasicBroker();
}
}
