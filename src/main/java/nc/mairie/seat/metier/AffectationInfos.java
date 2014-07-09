package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier AffectationInfos
 */
public class AffectationInfos extends BasicMetier {
	public String numeroinventaire;
	public String numeroimmatriculation;
	public String datemiseencirculation;
	public String codeservice;
	public String ddebut;
	public String dfin;
	public String liserv;
	public String datdeb;
	public String datfin;
	public String nom;
	public String prenom;
	public String datedebut;
	public String datefin;
	public String matricule;
	public String hdeb;
	public String hfin;
	public String hdebmn;
	public String hfinmn;
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
 * Retourne un ArrayList d'objet métier : AffectationInfos.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public static ArrayList<AffectationInfos> listerAffectationInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().listerAffectationInfos(aTransaction);
}
/**
 * Retourne un AffectationInfos.
 * @return AffectationInfos
 * @param aTransaction Transaction
 * @param matr matr
 * @throws Exception Exception
 */
public static ArrayList<AffectationInfos> chercherAffectationInfosAgent(nc.mairie.technique.Transaction aTransaction, String matr) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherAffectationInfosAgent(aTransaction, matr);
}

/**
 * Retourne un AffectationInfos.
 * @return AffectationInfos
 * @param aTransaction Transaction
 * @param matr matr
 * @param inv inv
 * @throws Exception Exception
 */
public static ArrayList<AffectationInfos> chercherAffectationInfosAgentEquip(nc.mairie.technique.Transaction aTransaction, String matr,String inv) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherAffectationInfosAgentEquip(aTransaction, matr,inv);
}

/**
 * Retourne un AffectationInfos.
 * @param aTransaction aTransaction
 * @param inv inv 
 * @return AffectationInfos AffectationInfos
 * @throws Exception Exception 
 */
public static ArrayList<AffectationInfos> chercherAffectationInfosEquip(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherAffectationInfosEquip(aTransaction, inv);
}

/**
 * Retourne un AffectationInfos.
 * @param aTransaction aTransaction 
 * @param code code 
 * @return AffectationInfos AffectationInfos
 * @throws Exception Exception 
 */
public static ArrayList<AffectationInfos> chercherListAffectationInfosSce(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherListAffectationInfosSce(aTransaction, code);
}

/**
 * Retourne un AffectationInfos.
 * @param aTransaction aTransaction 
 * @param code code 
 * @return AffectationInfos AffectationInfos
 * @throws Exception Exception 
 */
public static ArrayList<AffectationInfos> chercherListAffectationInfosEquip(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherListAffectationInfosEquip(aTransaction, code);
}

/**
 * Retourne un AffectationInfos.
 * @param aTransaction  aTransaction
 * @param servi servi 
 * @return AffectationInfos AffectationInfos 
 * @throws Exception Exception 
 */
public static ArrayList<AffectationInfos> chercherListAgentEquipSce(nc.mairie.technique.Transaction aTransaction, String servi) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherListAgentEquipSce(aTransaction, servi);
}
	
/**
 * Constructeur AffectationInfos.
 */
public AffectationInfos() {
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
 */
/**
 * @param newNumeroimmatriculation newNumeroimmatriculation
 */
public void setNumeroimmatriculation(String newNumeroimmatriculation) { 
	numeroimmatriculation = newNumeroimmatriculation;
}
/**
 * Getter de l'attribut datemiseencirculation.
 */
/**
 * @return datemiseencirculation
 */
public String getDatemiseencirculation() {
	return datemiseencirculation;
}
/**
 * Setter de l'attribut datemiseencirculation.
 */
/**
 * @param newDatemiseencirculation newDatemiseencirculation
 */
public void setDatemiseencirculation(String newDatemiseencirculation) { 
	datemiseencirculation = newDatemiseencirculation;
}
/**
 * Getter de l'attribut nom.
 */
/**
 * @return nom
 */
public String getNom() {
	return nom;
}
/**
 * Setter de l'attribut nom.
 */
/**
 * @param newNom newNom
 */
public void setNom(String newNom) { 
	nom = newNom;
}
/**
 * Getter de l'attribut prenom.
 */
/**
 * @return prenom
 */
public String getPrenom() {
	return prenom;
}
/**
 * Setter de l'attribut prenom.
 */
/**
 * @param newPrenom newPrenom
 */
public void setPrenom(String newPrenom) { 
	prenom = newPrenom;
}
/**
 * Getter de l'attribut datedebut.
 */
/**
 * @return datedebut
 */
public String getDatedebut() {
	return datedebut;
}
/**
 * Setter de l'attribut datedebut.
 */
/**
 * @param newDatedebut newDatedebut
 */
public void setDatedebut(String newDatedebut) { 
	datedebut = newDatedebut;
}
/**
 * Getter de l'attribut datefin.
 */
/**
 * @return datefin
 */
public String getDatefin() {
	return datefin;
}
/**
 * Setter de l'attribut datefin.
 */
/**
 * @param newDatefin newDatefin
 */
public void setDatefin(String newDatefin) { 
	datefin = newDatefin;
}
/**
 * Getter de l'attribut hdeb.
 */
/**
 * @return hdeb
 */
public String getHdeb() {
	return hdeb;
}
/**
 * Setter de l'attribut hdeb.
 */
/**
 * @param newHdeb newHdeb
 */
public void setHdeb(String newHdeb) { 
	hdeb = newHdeb;
}
/**
 * Getter de l'attribut hfin.
 */
/**
 * @return hfin
 */
public String getHfin() {
	return hfin;
}
/**
 * Setter de l'attribut hfin.
 */
/**
 * @param newHfin newHfin
 */
public void setHfin(String newHfin) { 
	hfin = newHfin;
}
/**
 * Getter de l'attribut hdebmn.
 */
/**
 * @return hdebmn
 */
public String getHdebmn() {
	return hdebmn;
}
/**
 * Setter de l'attribut hdebmn.
 */
/**
 * @param newHdebmn newHdebmn
 */
public void setHdebmn(String newHdebmn) { 
	hdebmn = newHdebmn;
}
/**
 * Getter de l'attribut hfinmn.
 */
/**
 * @return hfinmn
 */
public String getHfinmn() {
	return hfinmn;
}
/**
 * Setter de l'attribut hfinmn.
 */
/**
 * @param newHfinmn newHfinmn
 */
public void setHfinmn(String newHfinmn) { 
	hfinmn = newHfinmn;
}
/**
 * Getter de l'attribut matricule.
 */
/**
 * @return matricule
 */
public String getMatricule() {
	return matricule;
}
/**
 * Setter de l'attribut matricule.
 */
/**
 * @param newMatricule newMatricule
 */
public void setMatricule(String newMatricule) { 
	matricule = newMatricule;
}
/**
 * Getter de l'attribut liserv.
 */
/**
 * @return liserv
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
 * Getter de l'attribut ddebut.
 */
/**
 * @return ddebut
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
 * @return dfin
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
 * Getter de l'attribut codeservice.
 */
/**
 * @return codeservice
 */
public String getCodeservice() {
	return codeservice;
}
/**
 * Setter de l'attribut codeservice.
 */
/**
 * @param newCodeservice newCodeservice
 */
public void setCodeservice(String newCodeservice) { 
	codeservice = newCodeservice;
}
/**
 * Getter de l'attribut datdeb.
 */
/**
 * @return datdeb
 */
public String getDatdeb() {
	return datdeb;
}
/**
 * Setter de l'attribut datdeb.
 */
/**
 * @param newDatdeb newDatdeb
 */
public void setDatdeb(String newDatdeb) { 
	datdeb = newDatdeb;
}
/**
 * Getter de l'attribut datfin.
 */
/**
 * @return datfin
 */
public String getDatfin() {
	return datfin;
}
/**
 * Setter de l'attribut datfin.
 */
/**
 * @param newDatfin newDatfin
 */
public void setDatfin(String newDatfin) { 
	datfin = newDatfin;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AffectationInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
 * @return BasicBroker
*/
protected AffectationInfosBroker getMyAffectationInfosBroker() {
	return (AffectationInfosBroker)getMyBasicBroker();
}
}
