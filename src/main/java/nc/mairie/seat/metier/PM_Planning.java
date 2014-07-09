package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PM_Planning
 */
public class PM_Planning extends BasicMetier {
	public String pminv;
	public String pmserie;
	public String codepmpep;
	public String numfiche;
	public String dprev;
	public String dreal;
	public String duree;
	public String sinistre;
	public String commentaire;
	public String codeentretien;
	public String libelleentretien;
/**
 * Constructeur PM_Planning.
 */
public PM_Planning() {
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PM_PlanningBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PM_PlanningBroker getMyPM_PlanningBroker() {
	return (PM_PlanningBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : PM_Planning.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PM_Planning> listerPM_Planning(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPM_Planning(aTransaction);
}
/**
 * Retourne un PM_Planning.
 * @param aTransaction aTransaction
 * @param code code
 * @return PM_Planning
 * @throws Exception Exception
 */
public static PM_Planning chercherPM_Planning(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().chercherPM_Planning(aTransaction, code);
}

// lister tous les entretiens d'un petit matériel
public static ArrayList<PM_Planning> listerPM_EntretiensPm(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	if (tri.equals("")){
		tri = "codepmpep";
	}
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPM_EntretiensPm(aTransaction,inv,tri);
}

// lister les entretiens d'un petit matériel à faire
public static ArrayList<PM_Planning> listerPePersoInfosAFaire(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPM_EntretiensPmAFaire(aTransaction,inv,tri);
}

// lister les entretiens d'un petit matériel fait
public static ArrayList<PM_Planning> listerPM_EntretiensPmFait(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPM_EntretiensPmFait(aTransaction,inv,tri);
}

// liste des encours
public static ArrayList<PM_Planning> listerPlanningEnCours(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPlanningEnCours(aTransaction,numfiche);
}

//liste des encours avec PFM VALIDE à T
public static ArrayList<PM_Planning> listerPlanningEnCoursAvecFPMValideDiffetentT(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPlanningEnCoursAvecFPMValideDiffetentT(aTransaction,numfiche);
}
// liste des afaire
public static ArrayList<PM_Planning> listerPlanningAFaire(nc.mairie.technique.Transaction aTransaction,String dateFinPrev) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPlanningAFaire(aTransaction,dateFinPrev);
}

//liste des enretard
public static ArrayList<PM_Planning> listerPlanningEnRetard(nc.mairie.technique.Transaction aTransaction,String date) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPlanningEnRetard(aTransaction,date);
}

public static ArrayList<PM_Planning> chercherPM_Planning_FPM(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().chercherPM_Planning_FPM(aTransaction, code);
}

}
