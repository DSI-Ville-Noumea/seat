package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier OTComplet
 */
public class OTComplet extends BasicMetier {
	public String numeroot;
	public String dateentree;
	public String datesortie;
	public String compteur;
	public String valide;
	public String numerobc;
	public String commentaire;
	public String numinv;
	public String numeroinventaire;
	public String numeroimmatriculation;
	public String datemiseencirculation;
	public String dateventeoureforme;
	public String datehorscircuit;
	public String prixachat;
	public String reserve;
	public String codemodele;
	public String dureegarantie;
/**
 * Constructeur OTComplet.
 */
public OTComplet() {
	super();
}
/**
 * Getter de l'attribut numeroot.
 */
/**
 * @return String
 */
public String getNumeroot() {
	return numeroot;
}
/**
 * Setter de l'attribut numeroot.
 */
/**
 * @param newNumeroot newNumeroot
 */
public void setNumeroot(String newNumeroot) { 
	numeroot = newNumeroot;
}
/**
 * Getter de l'attribut dateentree.
 */
/**
 * @return String
 */
public String getDateentree() {
	return dateentree;
}
/**
 * Setter de l'attribut dateentree.
 */
/**
 * @param newDateentree newDateentree
 */
public void setDateentree(String newDateentree) { 
	dateentree = newDateentree;
}
/**
 * Getter de l'attribut datesortie.
 */
/**
 * @return String
 */
public String getDatesortie() {
	return datesortie;
}
/**
 * Setter de l'attribut datesortie.
 */
/**
 * @param newDatesortie newDatesortie
 */
public void setDatesortie(String newDatesortie) { 
	datesortie = newDatesortie;
}
/**
 * Getter de l'attribut compteur.
 */
/**
 * @return String
 */
public String getCompteur() {
	return compteur;
}
/**
 * Setter de l'attribut compteur.
 */
/**
 * @param newCompteur newCompteur
 */
public void setCompteur(String newCompteur) { 
	compteur = newCompteur;
}
/**
 * Getter de l'attribut valide.
 */
/**
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
 * Getter de l'attribut numerobc.
 */
/**
 * @return String
 */
public String getNumerobc() {
	return numerobc;
}
/**
 * Setter de l'attribut numerobc.
 */
/**
 * @param newNumerobc newNumerobc
 */
public void setNumerobc(String newNumerobc) { 
	numerobc = newNumerobc;
}
/**
 * Getter de l'attribut commentaire.
 */
/**
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
 * Getter de l'attribut numinv.
 */
/**
 * @return String
 */
public String getNuminv() {
	return numinv;
}
/**
 * Setter de l'attribut numinv.
 */
/**
 * @param newNuminv newNuminv
 */
public void setNuminv(String newNuminv) { 
	numinv = newNuminv;
}
/**
 * Getter de l'attribut numeroinventaire.
 */
/**
 * @return String
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 */
/**
 * @param newNumeroinventaire newNumeroinventaire
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
}
/**
 * Getter de l'attribut numeroimmatriculation.
 */
/**
 * @return String
 */
public String getNumeroimmatriculation() {
	return numeroimmatriculation;
}
/**
 * Setter de l'attribut numeroimmatriculation.
 */
/**
 * @param newNumeroimmatriculation newNumeroimmatriculation
 */
public void setNumeroimmatriculation(String newNumeroimmatriculation) { 
	numeroimmatriculation = newNumeroimmatriculation;
}
/**
 * Getter de l'attribut datemiseencirculation.
 */
/**
 * @return String
 */
public String getDatemiseencirculation() {
	return datemiseencirculation;
}
/**
 * Setter de l'attribut datemiseencirculation.
 */
/**
 * @param newDatemiseencirculation newDatemiseencirculation
 */
public void setDatemiseencirculation(String newDatemiseencirculation) { 
	datemiseencirculation = newDatemiseencirculation;
}
/**
 * Getter de l'attribut dateventeoureforme.
 */
/**
 * @return String
 */
public String getDateventeoureforme() {
	return dateventeoureforme;
}
/**
 * Setter de l'attribut dateventeoureforme.
 */
/**
 * @param newDateventeoureforme newDateventeoureforme
 */
public void setDateventeoureforme(String newDateventeoureforme) { 
	dateventeoureforme = newDateventeoureforme;
}
/**
 * Getter de l'attribut datehorscircuit.
 */
/**
 * @return String
 */
public String getDatehorscircuit() {
	return datehorscircuit;
}
/**
 * Setter de l'attribut datehorscircuit.
 */
/**
 * @param newDatehorscircuit newDatehorscircuit
 */
public void setDatehorscircuit(String newDatehorscircuit) { 
	datehorscircuit = newDatehorscircuit;
}
/**
 * Getter de l'attribut prixachat.
 */
/**
 * @return String
 */
public String getPrixachat() {
	return prixachat;
}
/**
 * Setter de l'attribut prixachat.
 */
/**
 * @param newPrixachat newPrixachat
 */
public void setPrixachat(String newPrixachat) { 
	prixachat = newPrixachat;
}
/**
 * Getter de l'attribut reserve.
 */
/**
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
 * Getter de l'attribut codemodele.
 */
/**
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
 * Getter de l'attribut dureegarantie.
 */
/**
 * @return String
 */
public String getDureegarantie() {
	return dureegarantie;
}
/**
 * Setter de l'attribut dureegarantie.
 */
/**
 * @param newDureegarantie newDureegarantie
 */
public void setDureegarantie(String newDureegarantie) { 
	dureegarantie = newDureegarantie;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new OTCompletBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected OTCompletBroker getMyOTCompletBroker() {
	return (OTCompletBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : OTComplet.
 * @param aTransaction aTransaction
 * @param and and
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<OTComplet> listerOTComplet(nc.mairie.technique.Transaction aTransaction, String and) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTComplet(aTransaction, and);
}
/**
 * Retourne un OTComplet.
 * @param aTransaction aTransaction
 * @param code code
 * @return OTComplet
 * @throws Exception Exception
 */
public static OTComplet chercherOTComplet(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().chercherOTComplet(aTransaction, code);
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<OTComplet> listerOTCompletDeclarationsValide(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletDeclarationsValide(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<OTComplet> listerOTCompletDeclarationsEncours(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletDeclarationsEncours(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction aTransaction
 * @param and and
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<OTComplet> listerOTCompletEncours(nc.mairie.technique.Transaction aTransaction, String and) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletEncours(aTransaction, and);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<OTComplet> listerOTCompletAValider(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletAValider(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction aTransaction
 * @param and and
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<OTComplet> listerOTCompletValide(nc.mairie.technique.Transaction aTransaction, String and) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletValide(aTransaction, and);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @param aTransaction aTransaction
 * @param numinv numinv
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<OTComplet> listerOTCompletEquip(nc.mairie.technique.Transaction aTransaction,String numinv) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletEquip(aTransaction,numinv);
}

}
