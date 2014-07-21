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
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception exception
 */
public static ArrayList<DeclarationsInfos> listerDeclarationsInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	DeclarationsInfos unDeclarationsInfos = new DeclarationsInfos();
	return unDeclarationsInfos.getMyDeclarationsInfosBroker().listerDeclarationsInfos(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : DeclarationsInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @return java.util.ArrayList
 * @throws Exception exception
 */
public static ArrayList<DeclarationsInfos> listerDeclarationsInfosEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	DeclarationsInfos unDeclarationsInfos = new DeclarationsInfos();
	return unDeclarationsInfos.getMyDeclarationsInfosBroker().listerDeclarationsInfosEquip(aTransaction,inv);
}

/**
 * Retourne un ArrayList d'objet métier : DeclarationsInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @return java.util.ArrayList
 * @throws Exception exception
 */
public static ArrayList<DeclarationsInfos> listerDeclarationsInfosOT(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	DeclarationsInfos unDeclarationsInfos = new DeclarationsInfos();
	return unDeclarationsInfos.getMyDeclarationsInfosBroker().listerDeclarationsInfosOT(aTransaction,inv);
}

/**
 * Retourne un DeclarationsInfos.
 * @param aTransaction Transaction
 * @param code code
 * @return DeclarationsInfos
 * @throws Exception exception
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
 * @return String
 */
public String getNom() {
	return nom;
}
/**
 * Setter de l'attribut nom.
 * @param newNom newNom
 */
public void setNom(String newNom) { 
	nom = newNom;
}
/**
 * Getter de l'attribut prenom.
 * @return String
 */
public String getPrenom() {
	return prenom;
}
/**
 * Setter de l'attribut prenom.
 * @param newPrenom newPrenom
 */
public void setPrenom(String newPrenom) { 
	prenom = newPrenom;
}
/**
 * Getter de l'attribut dateentree.
 * @return String
 */
public String getDateentree() {
	return dateentree;
}
/**
 * Setter de l'attribut dateentree.
 * @param newDateentree newDateentree
 */
public void setDateentree(String newDateentree) { 
	dateentree = newDateentree;
}
/**
 * Getter de l'attribut datesortie.
 * @return String
 */
public String getDatesortie() {
	return datesortie;
}
/**
 * Setter de l'attribut datesortie.
 * @param newDatesortie newDatesortie
 */
public void setDatesortie(String newDatesortie) { 
	datesortie = newDatesortie;
}
/**
 * Getter de l'attribut numeroot.
 * @return String
 */
public String getNumeroot() {
	return numeroot;
}
/**
 * Setter de l'attribut numeroot.
 * @param newNumeroot newNumeroot
 */
public void setNumeroot(String newNumeroot) { 
	numeroot = newNumeroot;
}
/**
 * Getter de l'attribut compteur.
 * @return String
 */
public String getCompteur() {
	return compteur;
}
/**
 * Setter de l'attribut compteur.
 * @param newCompteur newCompteur
 */
public void setCompteur(String newCompteur) { 
	compteur = newCompteur;
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
 * @param newValide newValide
 */
public void setValide(String newValide) { 
	valide = newValide;
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
 * @param newCommentaire newCommentaire
 */
public void setCommentaire(String newCommentaire) { 
	commentaire = newCommentaire;
}
/**
 * Getter de l'attribut codedec.
 * @return String
 */
public String getCodedec() {
	return codedec;
}
/**
 * Setter de l'attribut codedec.
 * @param newCodedec newCodedec
 */
public void setCodedec(String newCodedec) { 
	codedec = newCodedec;
}
/**
 * Getter de l'attribut date.
 * @return String
 */
public String getDate() {
	return date;
}
/**
 * Setter de l'attribut date.
 * @param newDate newDate
 */
public void setDate(String newDate) { 
	date = newDate;
}
/**
 * Getter de l'attribut anomalies.
 * @return String
 */
public String getAnomalies() {
	return anomalies;
}
/**
 * Setter de l'attribut anomalies.
 * @param newAnomalies newAnomalies
 */
public void setAnomalies(String newAnomalies) { 
	anomalies = newAnomalies;
}
/**
 * Getter de l'attribut matricule.
 * @return String
 */
public String getMatricule() {
	return matricule;
}
/**
 * Setter de l'attribut matricule.
 * @param newMatricule newMatricule
 */
public void setMatricule(String newMatricule) { 
	matricule = newMatricule;
}
/**
 * Getter de l'attribut numinv.
 * @return String
 */
public String getNuminv() {
	return numinv;
}
/**
 * Setter de l'attribut numinv.
 * @param newNuminv newNuminv
 */
public void setNuminv(String newNuminv) { 
	numinv = newNuminv;
}
/**
 * Getter de l'attribut numeroimmatriculation.
 * @return String
 */
public String getNumeroimmatriculation() {
	return numeroimmatriculation;
}
/**
 * Setter de l'attribut numeroimmatriculation.
 * @param newNumeroimmatriculation newNumeroimmatriculation
 */
public void setNumeroimmatriculation(String newNumeroimmatriculation) { 
	numeroimmatriculation = newNumeroimmatriculation;
}
/**
 * Getter de l'attribut datemiseencirculation.
 * @return String
 */
public String getDatemiseencirculation() {
	return datemiseencirculation;
}
/**
 * Setter de l'attribut datemiseencirculation.
 * @param newDatemiseencirculation newDatemiseencirculation
 */
public void setDatemiseencirculation(String newDatemiseencirculation) { 
	datemiseencirculation = newDatemiseencirculation;
}
/**
 * Getter de l'attribut prixachat.
 * @return String
 */
public String getPrixachat() {
	return prixachat;
}
/**
 * Setter de l'attribut prixachat.
 * @param newPrixachat newPrixachat
 */
public void setPrixachat(String newPrixachat) { 
	prixachat = newPrixachat;
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
 * @param newCodemodele newCodemodele
 */
public void setCodemodele(String newCodemodele) { 
	codemodele = newCodemodele;
}
/**
 * Getter de l'attribut dureegarantie.
 * @return String
 */
public String getDureegarantie() {
	return dureegarantie;
}
/**
 * Setter de l'attribut dureegarantie.
 * @param newDureegarantie newDureegarantie
 */
public void setDureegarantie(String newDureegarantie) { 
	dureegarantie = newDureegarantie;
}
/**
 * Getter de l'attribut codeservice.
 * @return String
 */
public String getCodeservice() {
	return codeservice;
}
/**
 * Setter de l'attribut codeservice.
 * @param newCodeservice newCodeservice
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
* @return BasicBroker
*/
protected DeclarationsInfosBroker getMyDeclarationsInfosBroker() {
	return (DeclarationsInfosBroker)getMyBasicBroker();
}
}
