package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier AffectationAgentInfos
 */
public class AffectationAgentInfos extends BasicMetier {
	public String matricule;
	public String numeroinventaire;
	public String datedebut;
	public String datefin;
	public String nom;
	public String prenom;
	public String datemiseencirculation;
	public String dateventeoureforme;
	public String hdeb;
	public String hfin;
	public String hfinmn;
	public String hdebmn;
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
 * Retourne un ArrayList d'objet métier : AffectationAgentInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<AffectationAgentInfos> listerAffectationAgentInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AffectationAgentInfos unAffectationAgentInfos = new AffectationAgentInfos();
	return unAffectationAgentInfos.getMyAffectationAgentInfosBroker().listerAffectationAgentInfos(aTransaction);
}
/**
 * Retourne un AffectationAgentInfos.
 * @return AffectationAgentInfos
 */
public static AffectationAgentInfos chercherAffectationAgentInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AffectationAgentInfos unAffectationAgentInfos = new AffectationAgentInfos();
	return unAffectationAgentInfos.getMyAffectationAgentInfosBroker().chercherAffectationAgentInfos(aTransaction, code);
}

/**
 * Retourne un ArrayList d'objet métier : AffectationAgentInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<AffectationAgentInfos> chercherListAffectationsSce(nc.mairie.technique.Transaction aTransaction,String servi) throws Exception{
	AffectationAgentInfos unAffectationAgentInfos = new AffectationAgentInfos();
	String param = servi;
	if (param.trim().length()==4){
		if(param.substring(3,4).equals("0")){
			param=servi.substring(0,3);
		}
	}
	return unAffectationAgentInfos.getMyAffectationAgentInfosBroker().chercherListAffectationsSce(aTransaction,param);
}

public static ArrayList<AffectationAgentInfos> chercherListAffectationsSce2(nc.mairie.technique.Transaction aTransaction,String servi) throws Exception{
	AffectationAgentInfos unAffectationAgentInfos = new AffectationAgentInfos();
	String param = servi;
	return unAffectationAgentInfos.getMyAffectationAgentInfosBroker().chercherListAffectationsSce(aTransaction,param);
}

public static ArrayList<AffectationAgentInfos> chercherListAffectationsSceEquip(nc.mairie.technique.Transaction aTransaction,String servi,String numinv) throws Exception{
	AffectationAgentInfos unAffectationAgentInfos = new AffectationAgentInfos();
	String param = servi;
	if (param.trim().length()==4){
		if(param.substring(3,4).equals("0")){
			param=servi.substring(0,3);
		}
	}
	return unAffectationAgentInfos.getMyAffectationAgentInfosBroker().chercherListAffectationsSceEquip(aTransaction,param,numinv);
}

	public String codeservice;
/**
 * Retourne un ArrayList d'objet métier : AffectationAgentInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<AffectationAgentInfos> listAffectationsSceEquipDate(nc.mairie.technique.Transaction aTransaction,String servi,String inv, String date) throws Exception {
	AffectationAgentInfos unAffectationAgentInfos = new AffectationAgentInfos();
	String param = servi;
	if (param.trim().length()==4){
		if(param.substring(3,4).equals("0")){
			param=servi.substring(0,3);
		}
	}
	return unAffectationAgentInfos.getMyAffectationAgentInfosBroker().listAffectationsSceEquipDate(aTransaction,servi,inv,date);
}

/**
 * Constructeur AffectationAgentInfos.
 */
public AffectationAgentInfos() {
	super();
}
/**
 * Getter de l'attribut matricule.
 */
public String getMatricule() {
	return matricule;
}
/**
 * Setter de l'attribut matricule.
 */
public void setMatricule(String newMatricule) { 
	matricule = newMatricule;
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
 * Getter de l'attribut datedebut.
 */
public String getDatedebut() {
	return datedebut;
}
/**
 * Setter de l'attribut datedebut.
 */
public void setDatedebut(String newDatedebut) { 
	datedebut = newDatedebut;
}
/**
 * Getter de l'attribut datefin.
 */
public String getDatefin() {
	return datefin;
}
/**
 * Setter de l'attribut datefin.
 */
public void setDatefin(String newDatefin) { 
	datefin = newDatefin;
}
/**
 * Getter de l'attribut nom.
 */
public String getNom() {
	return nom;
}
/**
 * Setter de l'attribut nom.
 */
public void setNom(String newNom) { 
	nom = newNom;
}
/**
 * Getter de l'attribut prenom.
 */
public String getPrenom() {
	return prenom;
}
/**
 * Setter de l'attribut prenom.
 */
public void setPrenom(String newPrenom) { 
	prenom = newPrenom;
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
 * Getter de l'attribut dateventeoureforme.
 */
public String getDateventeoureforme() {
	return dateventeoureforme;
}
/**
 * Setter de l'attribut dateventeoureforme.
 */
public void setDateventeoureforme(String newDateventeoureforme) { 
	dateventeoureforme = newDateventeoureforme;
}
/**
 * Getter de l'attribut hdeb.
 */
public String getHdeb() {
	return hdeb;
}
/**
 * Setter de l'attribut hdeb.
 */
public void setHdeb(String newHdeb) { 
	hdeb = newHdeb;
}
/**
 * Getter de l'attribut hfin.
 */
public String getHfin() {
	return hfin;
}
/**
 * Setter de l'attribut hfin.
 */
public void setHfin(String newHfin) { 
	hfin = newHfin;
}
/**
 * Getter de l'attribut hfinmn.
 */
public String getHfinmn() {
	return hfinmn;
}
/**
 * Setter de l'attribut hfinmn.
 */
public void setHfinmn(String newHfinmn) { 
	hfinmn = newHfinmn;
}
/**
 * Getter de l'attribut hdebmn.
 */
public String getHdebmn() {
	return hdebmn;
}
/**
 * Setter de l'attribut hdebmn.
 */
public void setHdebmn(String newHdebmn) { 
	hdebmn = newHdebmn;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AffectationAgentInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected AffectationAgentInfosBroker getMyAffectationAgentInfosBroker() {
	return (AffectationAgentInfosBroker)getMyBasicBroker();
}
}
