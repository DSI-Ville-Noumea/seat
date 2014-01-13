package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier DeclarationsInfos
 */
public class DeclarationsInfos extends BasicMetier {
	public String codedec;
	public String date;
	public String commentaire;
	public String nom;
	public String prenom;
	public String dateentree;
	public String datesortie;
	public String numeroot;
	public String compteur;
	public String valide;
	public String matricule;
	public String anomalies;
	public String numinv;
	public String numeroimmatriculation;
	public String datemiseencirculation;
	public String prixachat;
	public String codemodele;
	public String dureegarantie;

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
 * Retourne un ArrayList d'objet métier : DeclarationsInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<DeclarationsInfos> listerDeclarationsInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	DeclarationsInfos unDeclarationsInfos = new DeclarationsInfos();
	return unDeclarationsInfos.getMyDeclarationsInfosBroker().listerDeclarationsInfos(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : DeclarationsInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<DeclarationsInfos> listerDeclarationsInfosEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	DeclarationsInfos unDeclarationsInfos = new DeclarationsInfos();
	return unDeclarationsInfos.getMyDeclarationsInfosBroker().listerDeclarationsInfosEquip(aTransaction,inv);
}

/**
 * Retourne un ArrayList d'objet métier : DeclarationsInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<DeclarationsInfos> listerDeclarationsInfosOT(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	DeclarationsInfos unDeclarationsInfos = new DeclarationsInfos();
	return unDeclarationsInfos.getMyDeclarationsInfosBroker().listerDeclarationsInfosOT(aTransaction,inv);
}

/**
 * Retourne un DeclarationsInfos.
 * @return DeclarationsInfos
 */
public static DeclarationsInfos chercherDeclarationsInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	DeclarationsInfos unDeclarationsInfos = new DeclarationsInfos();
	return unDeclarationsInfos.getMyDeclarationsInfosBroker().chercherDeclarationsInfos(aTransaction, code);
}

	
// recherche des déclarations pour l'équipement à partir d'une date
public static ArrayList<DeclarationsInfos> listerDeclarationsInfosSce(nc.mairie.technique.Transaction aTransaction,String sce,String ddeb,String numinv,String dfin) throws Exception{
	DeclarationsInfos unDeclarationsInfos = new DeclarationsInfos();
	return unDeclarationsInfos.getMyDeclarationsInfosBroker().listerDeclarationsInfosSce(aTransaction,sce,ddeb,numinv,dfin);
}

	public String codeservice;
/**
 * Constructeur DeclarationsInfos.
 */
public DeclarationsInfos() {
	super();
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
 * Getter de l'attribut dateentree.
 */
public String getDateentree() {
	return dateentree;
}
/**
 * Setter de l'attribut dateentree.
 */
public void setDateentree(String newDateentree) { 
	dateentree = newDateentree;
}
/**
 * Getter de l'attribut datesortie.
 */
public String getDatesortie() {
	return datesortie;
}
/**
 * Setter de l'attribut datesortie.
 */
public void setDatesortie(String newDatesortie) { 
	datesortie = newDatesortie;
}
/**
 * Getter de l'attribut numeroot.
 */
public String getNumeroot() {
	return numeroot;
}
/**
 * Setter de l'attribut numeroot.
 */
public void setNumeroot(String newNumeroot) { 
	numeroot = newNumeroot;
}
/**
 * Getter de l'attribut compteur.
 */
public String getCompteur() {
	return compteur;
}
/**
 * Setter de l'attribut compteur.
 */
public void setCompteur(String newCompteur) { 
	compteur = newCompteur;
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
 * Getter de l'attribut codedec.
 */
public String getCodedec() {
	return codedec;
}
/**
 * Setter de l'attribut codedec.
 */
public void setCodedec(String newCodedec) { 
	codedec = newCodedec;
}
/**
 * Getter de l'attribut date.
 */
public String getDate() {
	return date;
}
/**
 * Setter de l'attribut date.
 */
public void setDate(String newDate) { 
	date = newDate;
}
/**
 * Getter de l'attribut anomalies.
 */
public String getAnomalies() {
	return anomalies;
}
/**
 * Setter de l'attribut anomalies.
 */
public void setAnomalies(String newAnomalies) { 
	anomalies = newAnomalies;
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
 * Getter de l'attribut numinv.
 */
public String getNuminv() {
	return numinv;
}
/**
 * Setter de l'attribut numinv.
 */
public void setNuminv(String newNuminv) { 
	numinv = newNuminv;
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
 * Getter de l'attribut prixachat.
 */
public String getPrixachat() {
	return prixachat;
}
/**
 * Setter de l'attribut prixachat.
 */
public void setPrixachat(String newPrixachat) { 
	prixachat = newPrixachat;
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
 * Getter de l'attribut dureegarantie.
 */
public String getDureegarantie() {
	return dureegarantie;
}
/**
 * Setter de l'attribut dureegarantie.
 */
public void setDureegarantie(String newDureegarantie) { 
	dureegarantie = newDureegarantie;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new DeclarationsInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected DeclarationsInfosBroker getMyDeclarationsInfosBroker() {
	return (DeclarationsInfosBroker)getMyBasicBroker();
}
}
