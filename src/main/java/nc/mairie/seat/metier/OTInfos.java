package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier OTInfos
 */
public class OTInfos extends BasicMetier {
	public String numeroot;
	public String dateentree;
	public String datesortie;
	public String compteur;
	public String valide;
	public String commentaire;
	public String numeroinventaire;
	public String numeroimmatriculation;
/**
 * Constructeur OTInfos.
 */
public OTInfos() {
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new OTInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected OTInfosBroker getMyOTInfosBroker() {
	return (OTInfosBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : OTInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<OTInfos> listerOTInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OTInfos unOTInfos = new OTInfos();
	return unOTInfos.getMyOTInfosBroker().listerOTInfos(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OTInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<OTInfos> listerOTInfosAValider(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OTInfos unOTInfos = new OTInfos();
	return unOTInfos.getMyOTInfosBroker().listerOTInfosAValider(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : OTInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<OTInfos> listerOTInfosEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	OTInfos unOTInfos = new OTInfos();
	return unOTInfos.getMyOTInfosBroker().listerOTInfosEquip(aTransaction,inv);
}
/**
 * Retourne un OTInfos.
 * @return OTInfos
 */
public static OTInfos chercherOTInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	OTInfos unOTInfos = new OTInfos();
	return unOTInfos.getMyOTInfosBroker().chercherOTInfos(aTransaction, code);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static ArrayList<OTInfos> listerOTInfosValide(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OTInfos unOTInfos = new OTInfos();
	return unOTInfos.getMyOTInfosBroker().listerOTInfosValide(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : OT.
 * @return java.util.ArrayList
 */
public static ArrayList<OTInfos> listerOTInfosEncours(nc.mairie.technique.Transaction aTransaction) throws Exception{
	OTInfos unOTInfos = new OTInfos();
	return unOTInfos.getMyOTInfosBroker().listerOTInfosEncours(aTransaction);
}

}
