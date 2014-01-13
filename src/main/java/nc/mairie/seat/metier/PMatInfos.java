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
 * @return java.util.ArrayList
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
 * @return java.util.ArrayList
 */
public static ArrayList<PMatInfos> listerPMatInfosSansEntretienPlanifie(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().listerPMatInfosSansEntretienPlanifie(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PMatInfos.
 * @return java.util.ArrayList
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
 * @return java.util.ArrayList
 */
public static ArrayList<PMatInfos> listerPMatInfosActifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().listerPMatInfosActifs(aTransaction,tri);
}

/**
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<PMatInfos> listerPMatInfosInactifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().listerPMatInfosInactifs(aTransaction,tri);
}
/**
 * Retourne un PMatInfos.
 * @return PMatInfos
 */
public static PMatInfos chercherPMatInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().chercherPMatInfos(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerPMatInfos(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du PMatInfos
	return getMyPMatInfosBroker().creerPMatInfos(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierPMatInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PMatInfos
	return getMyPMatInfosBroker().modifierPMatInfos(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
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
 */
public String getLibellefre() {
	return libellefre;
}
/**
 * Setter de l'attribut libellefre.
 */
public void setLibellefre(String newLibellefre) { 
	libellefre = newLibellefre;
}
/**
 * Getter de l'attribut observationsfre.
 */
public String getObservationsfre() {
	return observationsfre;
}
/**
 * Setter de l'attribut observationsfre.
 */
public void setObservationsfre(String newObservationsfre) { 
	observationsfre = newObservationsfre;
}
/**
 * Getter de l'attribut contact.
 */
public String getContact() {
	return contact;
}
/**
 * Setter de l'attribut contact.
 */
public void setContact(String newContact) { 
	contact = newContact;
}
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
 * Getter de l'attribut designationmarque.
 */
public String getDesignationcarbu() {
	return designationcarbu;
}
/**
 * Setter de l'attribut designationmarque.
 */
public void setDesignationcarbu(String newDesignationcarbu) { 
	designationcarbu = newDesignationcarbu;
}
/**
 * Getter de l'attribut designationmarque.
 */
public String getDesignationcompteur() {
	return designationcompteur;
}
/**
 * Setter de l'attribut designationmarque.
 */
public void setDesignationcompteur(String newDesignationcompteur) { 
	designationcompteur = newDesignationcompteur;
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
 * Getter de l'attribut codetypeequip.
 */
public String getCodetypeequip() {
	return codetypeequip;
}
/**
 * Setter de l'attribut codetypeequip.
 */
public void setCodetypeequip(String newCodetypeequip) { 
	codetypeequip = newCodetypeequip;
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
 * Getter de l'attribut version.
 */
public String getVersion() {
	return version;
}
/**
 * Setter de l'attribut version.
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
*/
protected PMatInfosBroker getMyPMatInfosBroker() {
	return (PMatInfosBroker)getMyBasicBroker();
}

/**
 * Retourne un EquipementInfos.
 * @return EquipementInfos
 */
public static ArrayList<PMatInfos> chercherListPMatInfosTous(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PMatInfos unPMatInfos = new PMatInfos();
	return unPMatInfos.getMyPMatInfosBroker().chercherListPMatInfosTous(aTransaction, code);
}

}
