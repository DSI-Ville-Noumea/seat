package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PePersoInfos
 */
public class PePersoInfos extends BasicMetier {
	public String codeot;
	public String codeequip;
	public String sinistre;
	public String duree;
	public String commentairete;
	public String intervalle;
	public String codeentretien;
	public String libelleentretien;
	public String codetypeent;
	public String designationtypeent;
	public String codeti;
	public String designationti;
	public String dateprev;
	public String datereal;
	public String codepep;

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
 * Retourne un ArrayList d'objet métier : PePersoInfos.
 * @param aTransaction aTransaction
 * @param inv inv
 * @param tri tri
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PePersoInfos> listerPePersoInfosEquip(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	if (tri.equals("")){
		tri = "codepep";
	}
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().listerPePersoInfosEquip(aTransaction,inv,tri);
}
/**
 * Retourne un PePersoInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return PePersoInfos
 * @throws Exception Exception
 */
public static PePersoInfos chercherPePersoInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().chercherPePersoInfos(aTransaction, code);
}
	
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param inv inv
 * @param tri tri
 * @return boolean
 * @throws Exception Exception
 */

public static ArrayList<PePersoInfos> listerPePersoInfosFait(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().listerPePersoInfosFait(aTransaction,inv,tri);
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * on retourne la liste des Ot d'un équipement
 * @param aTransaction aTransaction
 * @param inv inv
 * @return boolean
 * @throws Exception Exception
 */

public static ArrayList<PePersoInfos> listerPePersoInfosOTEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().listerPePersoInfosOTEquip(aTransaction,inv);
}

/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @param aTransaction aTransaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PePersoInfos> chercherPePersoInfosOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().chercherPePersoInfosOT(aTransaction,numot);
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param inv inv
 * @param tri tri
 * @return boolean
 * @throws Exception Exception
 */

public static ArrayList<PePersoInfos> listerPePersoInfosAFaire(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().listerPePersoInfosAFaire(aTransaction,inv,tri);
}
	
/**
 * Constructeur PePersoInfos.
 */
public PePersoInfos() {
	super();
}
/**
 * Getter de l'attribut sinistre.
 * @return String
 */
public String getSinistre() {
	return sinistre;
}
/**
 * Setter de l'attribut sinistre.
 */
/**
 * @param newSinistre newSinistre
 */
public void setSinistre(String newSinistre) { 
	sinistre = newSinistre;
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
 * Getter de l'attribut codeot.
 * @return String
 */
public String getCodeot() {
	return codeot;
}
/**
 * Setter de l'attribut codeot.
 */
/**
 * @param newCodeot newCodeot
 */
public void setCodeot(String newCodeot) { 
	codeot = newCodeot;
}
/**
 * Getter de l'attribut commentairete.
 * @return String
 */
public String getCommentairete() {
	return commentairete;
}
/**
 * Setter de l'attribut commentairete.
 */
/**
 * @param newCommentairete newCommentairete
 */
public void setCommentairete(String newCommentairete) { 
	commentairete = newCommentairete;
}
/**
 * Getter de l'attribut codepep.
 * @return String
 */
public String getCodepep() {
	return codepep;
}
/**
 * Setter de l'attribut codepep.
 */
/**
 * @param newCodepep newCodepep
 */
public void setCodepep(String newCodepep) { 
	codepep = newCodepep;
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
 * Getter de l'attribut datereal.
 * @return String
 */
public String getDatereal() {
	return datereal;
}
/**
 * Setter de l'attribut datereal.
 */
/**
 * @param newDatereal newDatereal
 */
public void setDatereal(String newDatereal) { 
	datereal = newDatereal;
}
/**
 * Getter de l'attribut codeequip.
 * @return String
 */
public String getCodeequip() {
	return codeequip;
}
/**
 * Setter de l'attribut codeequip.
 */
/**
 * @param newCodeequip newCodeequip
 */
public void setCodeequip(String newCodeequip) { 
	codeequip = newCodeequip;
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
 * Getter de l'attribut codetypeent.
 * @return String
 */
public String getCodetypeent() {
	return codetypeent;
}
/**
 * Setter de l'attribut codetypeent.
 */
/**
 * @param newCodetypeent newCodetypeent
 */
public void setCodetypeent(String newCodetypeent) { 
	codetypeent = newCodetypeent;
}
/**
 * Getter de l'attribut designationtypeent.
 * @return String
 */
public String getDesignationtypeent() {
	return designationtypeent;
}
/**
 * Setter de l'attribut designationtypeent.
 */
/**
 * @param newDesignationtypeent newDesignationtypeent
 */
public void setDesignationtypeent(String newDesignationtypeent) { 
	designationtypeent = newDesignationtypeent;
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
 * Getter de l'attribut designationti.
 * @return String
 */
public String getDesignationti() {
	return designationti;
}
/**
 * Setter de l'attribut designationti.
 */
/**
 * @param newDesignationti newDesignationti
 */
public void setDesignationti(String newDesignationti) { 
	designationti = newDesignationti;
}
/**
 * Getter de l'attribut dateprev.
 * @return String
 */
public String getDateprev() {
	return dateprev;
}
/**
 * Setter de l'attribut dateprev.
 */
/**
 * @param newDateprev newDateprev
 */
public void setDateprev(String newDateprev) { 
	dateprev = newDateprev;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PePersoInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PePersoInfosBroker getMyPePersoInfosBroker() {
	return (PePersoInfosBroker)getMyBasicBroker();
}
}
