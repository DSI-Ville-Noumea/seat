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
 * Getter de l'attribut numfiche.
 */
public String getNumfiche() {
	return numfiche;
}
/**
 * Setter de l'attribut numfiche.
 */
public void setNumfiche(String newNumfiche) { 
	numfiche = newNumfiche;
}
/**
 * Getter de l'attribut dentree.
 */
public String getDentree() {
	return dentree;
}
/**
 * Setter de l'attribut dentree.
 */
public void setDentree(String newDentree) { 
	dentree = newDentree;
}
/**
 * Getter de l'attribut dsortie.
 */
public String getDsortie() {
	return dsortie;
}
/**
 * Setter de l'attribut dsortie.
 */
public void setDsortie(String newDsortie) { 
	dsortie = newDsortie;
}
/**
 * Getter de l'attribut valide.
 */
public String getValide() {
	return valide;
}
/**
 * Setter de l'attribut valide.
 */
public void setValide(String newValide) { 
	valide = newValide;
}
/**
 * Getter de l'attribut codepmpep.
 */
public String getCodepmpep() {
	return codepmpep;
}
/**
 * Setter de l'attribut codepmpep.
 */
public void setCodepmpep(String newCodepmpep) { 
	codepmpep = newCodepmpep;
}
/**
 * Getter de l'attribut libelleentretien.
 */
public String getLibelleentretien() {
	return libelleentretien;
}
/**
 * Setter de l'attribut libelleentretien.
 */
public void setLibelleentretien(String newLibelleentretien) { 
	libelleentretien = newLibelleentretien;
}
/**
 * Getter de l'attribut dprev.
 */
public String getDprev() {
	return dprev;
}
/**
 * Setter de l'attribut dprev.
 */
public void setDprev(String newDprev) { 
	dprev = newDprev;
}
/**
 * Getter de l'attribut dreal.
 */
public String getDreal() {
	return dreal;
}
/**
 * Setter de l'attribut dreal.
 */
public void setDreal(String newDreal) { 
	dreal = newDreal;
}
/**
 * Getter de l'attribut duree.
 */
public String getDuree() {
	return duree;
}
/**
 * Setter de l'attribut duree.
 */
public void setDuree(String newDuree) { 
	duree = newDuree;
}
/**
 * Getter de l'attribut sinistre.
 */
public String getSinistre() {
	return sinistre;
}
/**
 * Setter de l'attribut sinistre.
 */
public void setSinistre(String newSinistre) { 
	sinistre = newSinistre;
}
/**
 * Getter de l'attribut commentaire.
 */
public String getCommentaire() {
	return commentaire;
}
/**
 * Setter de l'attribut commentaire.
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
 * @return java.util.ArrayList
 */
public static ArrayList<Pm_PePersoInfos> listerPm_PePersoInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Pm_PePersoInfos unPm_PePersoInfos = new Pm_PePersoInfos();
	return unPm_PePersoInfos.getMyPm_PePersoInfosBroker().listerPm_PePersoInfos(aTransaction);
}
/**
 * Retourne un Pm_PePersoInfos.
 * @return Pm_PePersoInfos
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
 */

public static ArrayList<Pm_PePersoInfos> listerPmPePersoInfosFait(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	Pm_PePersoInfos unPm_PePersoInfos = new Pm_PePersoInfos();
	return unPm_PePersoInfos.getMyPm_PePersoInfosBroker().listerPmPePersoInfosFait(aTransaction,inv,tri);
}

}
