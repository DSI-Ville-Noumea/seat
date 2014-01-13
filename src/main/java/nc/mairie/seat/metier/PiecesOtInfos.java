package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PiecesOtInfos
 */
public class PiecesOtInfos extends BasicMetier {
	public String numot;
	public String numpiece;
	public String datesortiepieces;
	public String quantite;
	public String designationpiece;
	public String pu;
	public String dateentree;
	public String datesortie;
	public String compteur;
	public String valide;
	public String numerobc;
	public String commentaire;
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
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<PiecesOtInfos> listerPiecesOtInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PiecesOtInfos unPiecesOtInfos = new PiecesOtInfos();
	return unPiecesOtInfos.getMyPiecesOtInfosBroker().listerPiecesOtInfos(aTransaction);
}
/**
 * Retourne un PiecesOtInfos.
 * @return PiecesOtInfos
 */
public static PiecesOtInfos chercherPiecesOtInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PiecesOtInfos unPiecesOtInfos = new PiecesOtInfos();
	return unPiecesOtInfos.getMyPiecesOtInfosBroker().chercherPiecesOtInfos(aTransaction, code);
}

/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<PiecesOtInfos> listerPiecesOtInfosOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	PiecesOtInfos unPiecesOtInfos = new PiecesOtInfos();
	return unPiecesOtInfos.getMyPiecesOtInfosBroker().listerPiecesOtInfosOT(aTransaction,numot);
}

/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @return java.util.ArrayList
 */
public static int cumuleMontantPiecesOtInfosOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	PiecesOtInfos unPiecesOtInfos = new PiecesOtInfos();
	return unPiecesOtInfos.getMyPiecesOtInfosBroker().cumuleMontantPiecesOtInfosOT(aTransaction,numot);
}

	public String prix;
/**
 * Constructeur PiecesOtInfos.
 */
public PiecesOtInfos() {
	super();
}
/**
 * Getter de l'attribut numot.
 */
public String getNumot() {
	return numot;
}
/**
 * Setter de l'attribut numot.
 */
public void setNumot(String newNumot) { 
	numot = newNumot;
}
/**
 * Getter de l'attribut numpiece.
 */
public String getNumpiece() {
	return numpiece;
}
/**
 * Setter de l'attribut numpiece.
 */
public void setNumpiece(String newNumpiece) { 
	numpiece = newNumpiece;
}
/**
 * Getter de l'attribut datesortiepieces.
 */
public String getDatesortiepieces() {
	return datesortiepieces;
}
/**
 * Setter de l'attribut datesortiepieces.
 */
public void setDatesortiepieces(String newDatesortiepieces) { 
	datesortiepieces = newDatesortiepieces;
}
/**
 * Getter de l'attribut quantite.
 */
public String getQuantite() {
	return quantite;
}
/**
 * Setter de l'attribut quantite.
 */
public void setQuantite(String newQuantite) { 
	quantite = newQuantite;
}
/**
 * Getter de l'attribut designationpiece.
 */
public String getDesignationpiece() {
	return designationpiece;
}
/**
 * Setter de l'attribut designationpiece.
 */
public void setDesignationpiece(String newDesignationpiece) { 
	designationpiece = newDesignationpiece;
}
/**
 * Getter de l'attribut pu.
 */
public String getPu() {
	return pu;
}
/**
 * Setter de l'attribut pu.
 */
public void setPu(String newPu) { 
	pu = newPu;
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
 * Getter de l'attribut prix.
 */
public String getPrix() {
	return prix;
}
/**
 * Setter de l'attribut prix.
 */
public void setPrix(String newPrix) { 
	prix = newPrix;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PiecesOtInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected PiecesOtInfosBroker getMyPiecesOtInfosBroker() {
	return (PiecesOtInfosBroker)getMyBasicBroker();
}
}
