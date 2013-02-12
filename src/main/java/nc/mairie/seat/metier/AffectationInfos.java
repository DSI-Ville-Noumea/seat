package nc.mairie.seat.metier;
/**
 * Objet métier AffectationInfos
 */
public class AffectationInfos extends nc.mairie.technique.BasicMetier {
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
 */
public static java.util.ArrayList listerAffectationInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().listerAffectationInfos(aTransaction);
}
/**
 * Retourne un AffectationInfos.
 * @return AffectationInfos
 */
public static java.util.ArrayList chercherAffectationInfosAgent(nc.mairie.technique.Transaction aTransaction, String matr) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherAffectationInfosAgent(aTransaction, matr);
}

/**
 * Retourne un AffectationInfos.
 * @return AffectationInfos
 */
public static java.util.ArrayList chercherAffectationInfosAgentEquip(nc.mairie.technique.Transaction aTransaction, String matr,String inv) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherAffectationInfosAgentEquip(aTransaction, matr,inv);
}

/**
 * Retourne un AffectationInfos.
 * @return AffectationInfos
 */
public static java.util.ArrayList chercherAffectationInfosEquip(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherAffectationInfosEquip(aTransaction, inv);
}

/**
 * Retourne un AffectationInfos.
 * @return AffectationInfos
 */
public static java.util.ArrayList chercherListAffectationInfosSce(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherListAffectationInfosSce(aTransaction, code);
}

/**
 * Retourne un AffectationInfos.
 * @return AffectationInfos
 */
public static java.util.ArrayList chercherListAffectationInfosEquip(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	AffectationInfos unAffectationInfos = new AffectationInfos();
	return unAffectationInfos.getMyAffectationInfosBroker().chercherListAffectationInfosEquip(aTransaction, code);
}

/**
 * Retourne un AffectationInfos.
 * @return AffectationInfos
 */
public static java.util.ArrayList chercherListAgentEquipSce(nc.mairie.technique.Transaction aTransaction, String servi) throws Exception{
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
 * Getter de l'attribut datdeb.
 */
public String getDatdeb() {
	return datdeb;
}
/**
 * Setter de l'attribut datdeb.
 */
public void setDatdeb(String newDatdeb) { 
	datdeb = newDatdeb;
}
/**
 * Getter de l'attribut datfin.
 */
public String getDatfin() {
	return datfin;
}
/**
 * Setter de l'attribut datfin.
 */
public void setDatfin(String newDatfin) { 
	datfin = newDatfin;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new AffectationInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected AffectationInfosBroker getMyAffectationInfosBroker() {
	return (AffectationInfosBroker)getMyBasicBroker();
}
}
