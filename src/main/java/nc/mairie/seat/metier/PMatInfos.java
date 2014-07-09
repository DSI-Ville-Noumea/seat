package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PMatInfos
 */
public class PMatInfos extends BasicMetier {
	public String pminv;
	public String pmserie;
	public String codemodele;
	public String codemarque;
	public String codetypeequip;
	public String codefre;
	public String dgarantie;
	public String reserve;
	public String dmes;
	public String dmhs;
	public String prix;
	public String dachat;
	public String libellefre;
	public String observationsfre;
	public String contact;
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
 * Retourne un ArrayList d'objet métier : PMatInfos.
 * @param aTransaction aTransaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PMatInfos> listerPMatInfos(nc.mairie.technique.Transaction aTransaction,String param) throws Exception{
	if(("").equals(param)){
		param = "pminv";
	}
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().listerPMatInfos(aTransaction,param);
}
/**
 * Retourne un ArrayList d'objet métier : PMatInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PMatInfos> listerPMatInfosSansEntretienPlanifie(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().listerPMatInfosSansEntretienPlanifie(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PMatInfos.
 * @param aTransaction aTransaction
 * @param pminv pminv
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PMatInfos> listerPMatInfosRecherche(nc.mairie.technique.Transaction aTransaction,String pminv,String param) throws Exception{
	if(("").equals(param)){
		param = "pminv";
	}
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().listerPMatInfosRecherche(aTransaction,pminv,param);
}
/**
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @param aTransaction aTransaction
 * @param tri tri
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PMatInfos> listerPMatInfosActifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().listerPMatInfosActifs(aTransaction,tri);
}

/**
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @param aTransaction aTransaction
 * @param tri tri
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PMatInfos> listerPMatInfosInactifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().listerPMatInfosInactifs(aTransaction,tri);
}
/**
 * Retourne un PMatInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return PMatInfos
 * @throws Exception Exception
 */
public static PMatInfos chercherPMatInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().chercherPMatInfos(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPMatInfos(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du PMatInfos
	return getMyPMatInfosBroker().creerPMatInfos(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean modifierPMatInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PMatInfos
	return getMyPMatInfosBroker().modifierPMatInfos(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean supprimerPMatInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PMatInfos
	return getMyPMatInfosBroker().supprimerPMatInfos(aTransaction);
}
	public String designationmodele;
	public String designationtypeequip;
	public String typete;
	public String designationmarque;
	public String designationcompteur;
	public String designationcarbu;
public boolean existePMatInfosPm(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().existePMatInfosPm(aTransaction, param);
}
	public String version;
/**
 * Constructeur PMatInfos.
 */
public PMatInfos() {
	super();
}
/**
 * Getter de l'attribut libellefre.
 * @return String
 */
public String getLibellefre() {
	return libellefre;
}
/**
 * Setter de l'attribut libellefre.
 */
/**
 * @param newLibellefre newLibellefre
 */
public void setLibellefre(String newLibellefre) { 
	libellefre = newLibellefre;
}
/**
 * Getter de l'attribut observationsfre.
 * @return String
 */
public String getObservationsfre() {
	return observationsfre;
}
/**
 * Setter de l'attribut observationsfre.
 */
/**
 * @param newObservationsfre newObservationsfre
 */
public void setObservationsfre(String newObservationsfre) { 
	observationsfre = newObservationsfre;
}
/**
 * Getter de l'attribut contact.
 * @return String
 */
public String getContact() {
	return contact;
}
/**
 * Setter de l'attribut contact.
 */
/**
 * @param newContact newContact
 */
public void setContact(String newContact) { 
	contact = newContact;
}
/**
 * Getter de l'attribut codefre.
 * @return String
 */
public String getCodefre() {
	return codefre;
}
/**
 * Setter de l'attribut codefre.
 */
/**
 * @param newCodefre newCodefre
 */
public void setCodefre(String newCodefre) { 
	codefre = newCodefre;
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
 */
/**
 * @param newCodemodele newCodemodele
 */
public void setCodemodele(String newCodemodele) { 
	codemodele = newCodemodele;
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
 */
/**
 * @param newPmserie newPmserie
 */
public void setPmserie(String newPmserie) { 
	pmserie = newPmserie;
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
 */
/**
 * @param newDgarantie newDgarantie
 */
public void setDgarantie(String newDgarantie) { 
	dgarantie = newDgarantie;
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
 */
/**
 * @param newReserve newReserve
 */
public void setReserve(String newReserve) { 
	reserve = newReserve;
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
 */
/**
 * @param newDmes newDmes
 */
public void setDmes(String newDmes) { 
	dmes = newDmes;
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
 */
/**
 * @param newDmhs newDmhs
 */
public void setDmhs(String newDmhs) { 
	dmhs = newDmhs;
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
 */
/**
 * @param newPrix newPrix
 */
public void setPrix(String newPrix) { 
	prix = newPrix;
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
 */
/**
 * @param newDachat newDachat
 */
public void setDachat(String newDachat) { 
	dachat = newDachat;
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
 */
/**
 * @param newDesignationmodele newDesignationmodele
 */
public void setDesignationmodele(String newDesignationmodele) { 
	designationmodele = newDesignationmodele;
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
 */
/**
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
 */
/**
 * @param newTypete newTypete
 */
public void setTypete(String newTypete) { 
	typete = newTypete;
}
/**
 * Getter de l'attribut designationmarque.
 * @return String
 */
public String getDesignationcarbu() {
	return designationcarbu;
}
/**
 * Setter de l'attribut designationmarque.
 */
/**
 * @param newDesignationcarbu newDesignationcarbu
 */
public void setDesignationcarbu(String newDesignationcarbu) { 
	designationcarbu = newDesignationcarbu;
}
/**
 * Getter de l'attribut designationmarque.
 * @return String
 */
public String getDesignationcompteur() {
	return designationcompteur;
}
/**
 * Setter de l'attribut designationmarque.
 */
/**
 * @param newDesignationcompteur newDesignationcompteur
 */
public void setDesignationcompteur(String newDesignationcompteur) { 
	designationcompteur = newDesignationcompteur;
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
 */
/**
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
 */
/**
 * @param newCodemarque newCodemarque
 */
public void setCodemarque(String newCodemarque) { 
	codemarque = newCodemarque;
}
/**
 * Getter de l'attribut codetypeequip.
 * @return String
 */
public String getCodetypeequip() {
	return codetypeequip;
}
/**
 * Setter de l'attribut codetypeequip.
 */
/**
 * @param newCodetypeequip newCodetypeequip
 */
public void setCodetypeequip(String newCodetypeequip) { 
	codetypeequip = newCodetypeequip;
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
 */
/**
 * @param newPminv newPminv
 */
public void setPminv(String newPminv) { 
	pminv = newPminv;
}
/**
 * Getter de l'attribut version.
 * @return String
 */
public String getVersion() {
	return version;
}
/**
 * Setter de l'attribut version.
 */
/**
 * @param newVersion newVersion
 */
public void setVersion(String newVersion) { 
	version = newVersion;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PMatInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PMatInfosBroker getMyPMatInfosBroker() {
	return (PMatInfosBroker)getMyBasicBroker();
}

/**
 * Retourne un EquipementInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return EquipementInfos
 * @throws Exception Exception
 */
public static ArrayList<PMatInfos> chercherListPMatInfosTous(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().chercherListPMatInfosTous(aTransaction, code);
}

}
