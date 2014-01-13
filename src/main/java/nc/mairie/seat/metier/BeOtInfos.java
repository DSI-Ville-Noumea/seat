package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier BeOtInfos
 */ 
public class BeOtInfos extends BasicMetier {
	public String numeroot;
	public String dateentree;
	public String datesortie;
	public String compteur;
	public String valide;
	public String commentaire;
	public String codcol;
	public String exerci;
	public String codbud;
	public String noengj;
/**
 * Constructeur BeOtInfos.
 */
public BeOtInfos() {
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
 * Getter de l'attribut codcol.
 */
public String getCodcol() {
	return codcol;
}
/**
 * Setter de l'attribut codcol.
 */
public void setCodcol(String newCodcol) { 
	codcol = newCodcol;
}
/**
 * Getter de l'attribut exerci.
 */
public String getExerci() {
	return exerci;
}
/**
 * Setter de l'attribut exerci.
 */
public void setExerci(String newExerci) { 
	exerci = newExerci;
}
/**
 * Getter de l'attribut codbud.
 */
public String getCodbud() {
	return codbud;
}
/**
 * Setter de l'attribut codbud.
 */
public void setCodbud(String newCodbud) { 
	codbud = newCodbud;
}
/**
 * Getter de l'attribut noengj.
 */
public String getNoengj() {
	return noengj;
}
/**
 * Setter de l'attribut noengj.
 */
public void setNoengj(String newNoengj) { 
	noengj = newNoengj;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new BeOtInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected BeOtInfosBroker getMyBeOtInfosBroker() {
	return (BeOtInfosBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : BeOtInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<BeOtInfos> listerBeOtInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	BeOtInfos unBeOtInfos = new BeOtInfos();
	return unBeOtInfos.getMyBeOtInfosBroker().listerBeOtInfos(aTransaction);
}
/**
 * Retourne un BeOtInfos.
 * @return BeOtInfos
 */
public static BeOtInfos chercherBeOtInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	BeOtInfos unBeOtInfos = new BeOtInfos();
	return unBeOtInfos.getMyBeOtInfosBroker().chercherBeOtInfos(aTransaction, code);
}

/**
 * Retourne un ArrayList d'objet métier : BeOtInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<BeOtInfos> listerBeOtInfosOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	BeOtInfos unBeOtInfos = new BeOtInfos();
	return unBeOtInfos.getMyBeOtInfosBroker().listerBeOtInfosOT(aTransaction,numot);
}

/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @return java.util.ArrayList
 */
public static int cumuleMontantPiecesOtInfosBE(nc.mairie.technique.Transaction aTransaction,String numot, String numinv) throws Exception{
	BeOtInfos unBeOtInfos = new BeOtInfos();
	return unBeOtInfos.getMyBeOtInfosBroker().cumuleMontantPiecesOtInfosBE(aTransaction,numot, numinv);
}

}
