package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PeBaseInfos
 */
public class PeBaseInfos extends BasicMetier {
	public String codeentretien;
	public String libelleentretien;
	public String intervalle;
	public String codeti;
	public String duree;
	public String codetd;
	public String designationduree;
	public String designation;
	public String codemodele;
	public String designationmarque;
	public String designationmodele;
	public String designationtypeequip;
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
 * Retourne un ArrayList d'objet métier : PeBaseInfos.
 * @param aTransaction aTransaction
 * @param modele modele
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PeBaseInfos> listerPeBaseInfos(nc.mairie.technique.Transaction aTransaction,String modele) throws Exception{
	PeBaseInfos unPeBaseInfos = new PeBaseInfos();
	return unPeBaseInfos.getMyPeBaseInfosBroker().listerPeBaseInfos(aTransaction,modele);
}
/**
 * Retourne un PeBaseInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return PeBaseInfos
 * @throws Exception Exception
 */
public static PeBaseInfos chercherPeBaseInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PeBaseInfos unPeBaseInfos = new PeBaseInfos();
	return unPeBaseInfos.getMyPeBaseInfosBroker().chercherPeBaseInfos(aTransaction, code);
}
	public String commentaire;
/**
 * Getter de l'attribut designationduree.
 * @return String
 */
public String getDesignationduree() {
	return designationduree;
}
/**
 * Setter de l'attribut designationduree.
 */
/**
 * @param newDesignationduree newDesignationduree
 */
public void setDesignationduree(String newDesignationduree) { 
	designationduree = newDesignationduree;
}
	public String desactive;
	public String datedesactivation;
/**
 * Constructeur PeBaseInfos.
 */
public PeBaseInfos() {
	super();
}
/**
 * Getter de l'attribut codeentretien.
 * @return String
 */
public String getCodeentretien() {
	return codeentretien;
}
/**
 * Setter de l'attribut codeentretien.
 * @param newCodeentretien newCodeentretien
 */
public void setCodeentretien(String newCodeentretien) { 
	codeentretien = newCodeentretien;
}
/**
 * Getter de l'attribut libelleentretien.
 * @return String
 */
public String getLibelleentretien() {
	return libelleentretien;
}
/**
 * Setter de l'attribut libelleentretien.
 */
/**
 * @param newLibelleentretien newLibelleentretien
 */
public void setLibelleentretien(String newLibelleentretien) { 
	libelleentretien = newLibelleentretien;
}
/**
 * Getter de l'attribut intervalle.
 * @return String
 */
public String getIntervalle() {
	return intervalle;
}
/**
 * Setter de l'attribut intervalle.
 */
/**
 * @param newIntervalle newIntervalle
 */
public void setIntervalle(String newIntervalle) { 
	intervalle = newIntervalle;
}
/**
 * Getter de l'attribut codeti.
 * @return String
 */
public String getCodeti() {
	return codeti;
}
/**
 * Setter de l'attribut codeti.
 */
/**
 * @param newCodeti newCodeti
 */
public void setCodeti(String newCodeti) { 
	codeti = newCodeti;
}
/**
 * Getter de l'attribut duree.
 * @return String
 */
public String getDuree() {
	return duree;
}
/**
 * Setter de l'attribut duree.
 */
/**
 * @param newDuree newDuree
 */
public void setDuree(String newDuree) { 
	duree = newDuree;
}
/**
 * Getter de l'attribut codetd.
 * @return String
 */
public String getCodetd() {
	return codetd;
}
/**
 * Setter de l'attribut codetd.
 */
/**
 * @param newCodetd newCodetd
 */
public void setCodetd(String newCodetd) { 
	codetd = newCodetd;
}
/**
 * Getter de l'attribut designation.
 * @return String
 */
public String getDesignation() {
	return designation;
}
/**
 * Setter de l'attribut designation.
 */
/**
 * @param newDesignation newDesignation
 */
public void setDesignation(String newDesignation) { 
	designation = newDesignation;
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
 * Getter de l'attribut commentaire.
 * @return String
 */
public String getCommentaire() {
	return commentaire;
}
/**
 * Setter de l'attribut commentaire.
 */
/**
 * @param newCommentaire newCommentaire
 */
public void setCommentaire(String newCommentaire) { 
	commentaire = newCommentaire;
}
/**
 * Getter de l'attribut desactive.
 * @return String
 */
public String getDesactive() {
	return desactive;
}
/**
 * Setter de l'attribut desactive.
 */
/**
 * @param newDesactive newDesactive
 */
public void setDesactive(String newDesactive) { 
	desactive = newDesactive;
}
/**
 * Getter de l'attribut datedesactivation.
 * @return String
 */
public String getDatedesactivation() {
	return datedesactivation;
}
/**
 * Setter de l'attribut datedesactivation.
 */
/**
 * @param newDatedesactivation newDatedesactivation
 */
public void setDatedesactivation(String newDatedesactivation) { 
	datedesactivation = newDatedesactivation;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PeBaseInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PeBaseInfosBroker getMyPeBaseInfosBroker() {
	return (PeBaseInfosBroker)getMyBasicBroker();
}
}
