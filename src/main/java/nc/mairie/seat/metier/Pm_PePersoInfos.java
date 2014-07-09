package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Pm_PePersoInfos
 */
public class Pm_PePersoInfos extends BasicMetier {
	public String pminv;
	public String pmserie;
	public String dmes;
	public String numfiche;
	public String dentree;
	public String dsortie;
	public String valide;
	public String codepmpep;
	public String libelleentretien;
	public String dprev;
	public String dreal;
	public String duree;
	public String sinistre;
	public String commentaire;
/**
 * Constructeur Pm_PePersoInfos.
 */
public Pm_PePersoInfos() {
	super();
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
 * Getter de l'attribut numfiche.
 * @return String
 */
public String getNumfiche() {
	return numfiche;
}
/**
 * Setter de l'attribut numfiche.
 */
/**
 * @param newNumfiche newNumfiche
 */
public void setNumfiche(String newNumfiche) { 
	numfiche = newNumfiche;
}
/**
 * Getter de l'attribut dentree.
 * @return String
 */
public String getDentree() {
	return dentree;
}
/**
 * Setter de l'attribut dentree.
 */
/**
 * @param newDentree newDentree
 */
public void setDentree(String newDentree) { 
	dentree = newDentree;
}
/**
 * Getter de l'attribut dsortie.
 * @return String
 */
public String getDsortie() {
	return dsortie;
}
/**
 * Setter de l'attribut dsortie.
 */
/**
 * @param newDsortie newDsortie
 */
public void setDsortie(String newDsortie) { 
	dsortie = newDsortie;
}
/**
 * Getter de l'attribut valide.
 * @return String
 */
public String getValide() {
	return valide;
}
/**
 * Setter de l'attribut valide.
 */
/**
 * @param newValide newValide
 */
public void setValide(String newValide) { 
	valide = newValide;
}
/**
 * Getter de l'attribut codepmpep.
 * @return String
 */
public String getCodepmpep() {
	return codepmpep;
}
/**
 * Setter de l'attribut codepmpep.
 */
/**
 * @param newCodepmpep newCodepmpep
 */
public void setCodepmpep(String newCodepmpep) { 
	codepmpep = newCodepmpep;
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
 * Getter de l'attribut dprev.
 * @return String
 */
public String getDprev() {
	return dprev;
}
/**
 * Setter de l'attribut dprev.
 */
/**
 * @param newDprev newDprev
 */
public void setDprev(String newDprev) { 
	dprev = newDprev;
}
/**
 * Getter de l'attribut dreal.
 * @return String
 */
public String getDreal() {
	return dreal;
}
/**
 * Setter de l'attribut dreal.
 */
/**
 * @param newDreal newDreal
 */
public void setDreal(String newDreal) { 
	dreal = newDreal;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new Pm_PePersoInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected Pm_PePersoInfosBroker getMyPm_PePersoInfosBroker() {
	return (Pm_PePersoInfosBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : Pm_PePersoInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Pm_PePersoInfos> listerPm_PePersoInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Pm_PePersoInfos unPm_PePersoInfos = new Pm_PePersoInfos();
	return unPm_PePersoInfos.getMyPm_PePersoInfosBroker().listerPm_PePersoInfos(aTransaction);
}
/**
 * Retourne un Pm_PePersoInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return Pm_PePersoInfos
 * @throws Exception Exception
 */
public static Pm_PePersoInfos chercherPm_PePersoInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Pm_PePersoInfos unPm_PePersoInfos = new Pm_PePersoInfos();
	return unPm_PePersoInfos.getMyPm_PePersoInfosBroker().chercherPm_PePersoInfos(aTransaction, code);
}

// liste des peperso d'une fiche
public static ArrayList<Pm_PePersoInfos> chercherPmPePersoInfosFPM(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	Pm_PePersoInfos unPm_PePersoInfos = new Pm_PePersoInfos();
	return unPm_PePersoInfos.getMyPm_PePersoInfosBroker().chercherPmPePersoInfosFPM(aTransaction,numfiche);
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

public static ArrayList<Pm_PePersoInfos> listerPmPePersoInfosFait(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	Pm_PePersoInfos unPm_PePersoInfos = new Pm_PePersoInfos();
	return unPm_PePersoInfos.getMyPm_PePersoInfosBroker().listerPmPePersoInfosFait(aTransaction,inv,tri);
}

}
