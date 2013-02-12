package nc.mairie.seat.metier;
/**
 * Objet métier PM_Planning
 */
public class PM_Planning extends nc.mairie.technique.BasicMetier {
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
 * Getter de l'attribut codeentretien.
 */
public String getCodeentretien() {
	return codeentretien;
}
/**
 * Setter de l'attribut codeentretien.
 */
public void setCodeentretien(String newCodeentretien) { 
	codeentretien = newCodeentretien;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PM_PlanningBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
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
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPM_Planning(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPM_Planning(aTransaction);
}
/**
 * Retourne un PM_Planning.
 * @return PM_Planning
 */
public static PM_Planning chercherPM_Planning(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().chercherPM_Planning(aTransaction, code);
}

// lister tous les entretiens d'un petit matériel
public static java.util.ArrayList listerPM_EntretiensPm(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	if (tri.equals("")){
		tri = "codepmpep";
	}
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPM_EntretiensPm(aTransaction,inv,tri);
}

// lister les entretiens d'un petit matériel à faire
public static java.util.ArrayList listerPePersoInfosAFaire(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPM_EntretiensPmAFaire(aTransaction,inv,tri);
}

// lister les entretiens d'un petit matériel fait
public static java.util.ArrayList listerPM_EntretiensPmFait(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPM_EntretiensPmFait(aTransaction,inv,tri);
}

// liste des encours
public static java.util.ArrayList listerPlanningEnCours(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPlanningEnCours(aTransaction,numfiche);
}

//liste des encours avec PFM VALIDE à T
public static java.util.ArrayList listerPlanningEnCoursAvecFPMValideDiffetentT(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPlanningEnCoursAvecFPMValideDiffetentT(aTransaction,numfiche);
}
// liste des afaire
public static java.util.ArrayList listerPlanningAFaire(nc.mairie.technique.Transaction aTransaction,String dateFinPrev) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPlanningAFaire(aTransaction,dateFinPrev);
}

//liste des enretard
public static java.util.ArrayList listerPlanningEnRetard(nc.mairie.technique.Transaction aTransaction,String date) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().listerPlanningEnRetard(aTransaction,date);
}

public static java.util.ArrayList chercherPM_Planning_FPM(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PM_Planning unPM_Planning = new PM_Planning();
	return unPM_Planning.getMyPM_PlanningBroker().chercherPM_Planning_FPM(aTransaction, code);
}

}
