package nc.mairie.seat.metier;
/**
 * Objet métier OTComplet
 */
public class OTComplet extends nc.mairie.technique.BasicMetier {
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
 * Getter de l'attribut numerobc.
 */
public String getNumerobc() {
	return numerobc;
}
/**
 * Setter de l'attribut numerobc.
 */
public void setNumerobc(String newNumerobc) { 
	numerobc = newNumerobc;
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
 * Getter de l'attribut numeroinventaire.
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
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
 * Getter de l'attribut dateventeoureforme.
 */
public String getDateventeoureforme() {
	return dateventeoureforme;
}
/**
 * Setter de l'attribut dateventeoureforme.
 */
public void setDateventeoureforme(String newDateventeoureforme) { 
	dateventeoureforme = newDateventeoureforme;
}
/**
 * Getter de l'attribut datehorscircuit.
 */
public String getDatehorscircuit() {
	return datehorscircuit;
}
/**
 * Setter de l'attribut datehorscircuit.
 */
public void setDatehorscircuit(String newDatehorscircuit) { 
	datehorscircuit = newDatehorscircuit;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new OTCompletBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
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
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerOTComplet(nc.mairie.technique.Transaction aTransaction, String and) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTComplet(aTransaction, and);
}
/**
 * Retourne un OTComplet.
 * @return OTComplet
 */
public static OTComplet chercherOTComplet(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().chercherOTComplet(aTransaction, code);
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerOTCompletDeclarationsValide(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletDeclarationsValide(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerOTCompletDeclarationsEncours(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletDeclarationsEncours(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerOTCompletEncours(nc.mairie.technique.Transaction aTransaction, String and) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletEncours(aTransaction, and);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerOTCompletAValider(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletAValider(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerOTCompletValide(nc.mairie.technique.Transaction aTransaction, String and) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletValide(aTransaction, and);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerOTCompletEquip(nc.mairie.technique.Transaction aTransaction,String numinv) throws Exception{
	OTComplet unOTComplet = new OTComplet();
	return unOTComplet.getMyOTCompletBroker().listerOTCompletEquip(aTransaction,numinv);
}

}
