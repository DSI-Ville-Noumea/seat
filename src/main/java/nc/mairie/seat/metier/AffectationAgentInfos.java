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
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public static ArrayList<AffectationAgentInfos> listerAffectationAgentInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AffectationAgentInfos unAffectationAgentInfos = new AffectationAgentInfos();
	return unAffectationAgentInfos.getMyAffectationAgentInfosBroker().listerAffectationAgentInfos(aTransaction);
}
/**
 * Retourne un AffectationAgentInfos.
 * @return AffectationAgentInfos
 * @param aTransaction Transaction
 * @param code code
 * @throws Exception Exception
 */
public static AffectationAgentInfos chercherAffectationAgentInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AffectationAgentInfos unAffectationAgentInfos = new AffectationAgentInfos();
	return unAffectationAgentInfos.getMyAffectationAgentInfosBroker().chercherAffectationAgentInfos(aTransaction, code);
}

/**
 * Retourne un ArrayList d'objet métier : AffectationAgentInfos.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @param servi servi
 * @throws Exception Exception
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

/**
 * @param aTransaction Transaction
 * @param servi servi
 * @return Arraylist Arraylist
 * @throws Exception Exception
 */
public static ArrayList<AffectationAgentInfos> chercherListAffectationsSce2(nc.mairie.technique.Transaction aTransaction,String servi) throws Exception{
	AffectationAgentInfos unAffectationAgentInfos = new AffectationAgentInfos();
	String param = servi;
	return unAffectationAgentInfos.getMyAffectationAgentInfosBroker().chercherListAffectationsSce(aTransaction,param);
}

/**
 * @param aTransaction Transaction
 * @param servi servi
 * @param numinv numinv
 * @return Arraylist
 * @throws Exception Exception
 */
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
 * @param aTransaction Transaction
 * @param servi servi
 * @param inv inv
 * @param date date
 * @throws Exception Exception
 */
public static ArrayList<AffectationAgentInfos> listAffectationsSceEquipDate(nc.mairie.technique.Transaction aTransaction,String servi,String inv, String date) throws Exception {
	AffectationAgentInfos unAffectationAgentInfos = new AffectationAgentInfos();
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
 * @return matricule
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
 * Getter de l'attribut datedebut.
 * @return datedebut
 */
public String getDatedebut() {
	return datedebut;
}
/**
 * Setter de l'attribut datedebut.
 * @param newDatedebut newDatedebut
 */
public void setDatedebut(String newDatedebut) { 
	datedebut = newDatedebut;
}
/**
 * Getter de l'attribut datefin.
 * @return datefin
 */
public String getDatefin() {
	return datefin;
}
/**
 * Setter de l'attribut datefin.
 * @param newDatefin newDatefin
 */
public void setDatefin(String newDatefin) { 
	datefin = newDatefin;
}
/**
 * Getter de l'attribut nom.
 * @return nom
 */
public String getNom() {
	return nom;
}
/**
 * Setter de l'attribut nom.
 * @param newNom newNom
 */
public void setNom(String newNom) { 
	nom = newNom;
}
/**
 * Getter de l'attribut prenom.
 * @return prenom
 */
public String getPrenom() {
	return prenom;
}
/**
 * Setter de l'attribut prenom.
 * @param newPrenom newPrenom
 */
public void setPrenom(String newPrenom) { 
	prenom = newPrenom;
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
 * Getter de l'attribut dateventeoureforme.
 * @return dateventeoureforme
 */
public String getDateventeoureforme() {
	return dateventeoureforme;
}
/**
 * Setter de l'attribut dateventeoureforme.
 * @param newDateventeoureforme newDateventeoureforme
 */
public void setDateventeoureforme(String newDateventeoureforme) { 
	dateventeoureforme = newDateventeoureforme;
}
/**
 * Getter de l'attribut hdeb.
 * @return hdeb
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
 * @return hfin
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
 * Getter de l'attribut hfinmn.
 * @return hfinmn
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
 * Getter de l'attribut hdebmn.
 * @return hdebmn
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AffectationAgentInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
 * @return AffectationAgentInfosBroker
 */
protected AffectationAgentInfosBroker getMyAffectationAgentInfosBroker() {
	return (AffectationAgentInfosBroker)getMyBasicBroker();
}
}
