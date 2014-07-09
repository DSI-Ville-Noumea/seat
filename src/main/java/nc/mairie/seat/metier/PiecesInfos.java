package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PiecesInfos
 */
public class PiecesInfos extends BasicMetier {
	public String codepiece;
	public String designationpiece;
	public String pu;
	public String datesortie;
	public String quantite;
	public String numot;
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
 * Retourne un ArrayList d'objet métier : PiecesInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PiecesInfos> listerPiecesInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PiecesInfos unPiecesInfos = new PiecesInfos();
	return unPiecesInfos.getMyPiecesInfosBroker().listerPiecesInfos(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : PiecesInfos.
 * @param aTransaction aTransaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<PiecesInfos> chercherPiecesInfosOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	PiecesInfos unPiecesInfos = new PiecesInfos();
	return unPiecesInfos.getMyPiecesInfosBroker().chercherPiecesInfosOT(aTransaction,numot);
}

/**
 * Retourne un PiecesInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return PiecesInfos
 * @throws Exception Exception
 */
public static PiecesInfos chercherPiecesInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PiecesInfos unPiecesInfos = new PiecesInfos();
	return unPiecesInfos.getMyPiecesInfosBroker().chercherPiecesInfos(aTransaction, code);
}
	public String prix;
/**
 * Constructeur PiecesInfos.
 */
public PiecesInfos() {
	super();
}
/**
 * Getter de l'attribut codepiece.
 * @return String
 */
public String getCodepiece() {
	return codepiece;
}
/**
 * Setter de l'attribut codepiece.
 */
/**
 * @param newCodepiece newCodepiece
 */
public void setCodepiece(String newCodepiece) { 
	codepiece = newCodepiece;
}
/**
 * Getter de l'attribut designationpiece.
 * @return String
 */
public String getDesignationpiece() {
	return designationpiece;
}
/**
 * Setter de l'attribut designationpiece.
 */
/**
 * @param newDesignationpiece newDesignationpiece
 */
public void setDesignationpiece(String newDesignationpiece) { 
	designationpiece = newDesignationpiece;
}
/**
 * Getter de l'attribut pu.
 * @return String
 */
public String getPu() {
	return pu;
}
/**
 * Setter de l'attribut pu.
 */
/**
 * @param newPu newPu
 */
public void setPu(String newPu) { 
	pu = newPu;
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
 */
/**
 * @param newDatesortie newDatesortie
 */
public void setDatesortie(String newDatesortie) { 
	datesortie = newDatesortie;
}
/**
 * Getter de l'attribut quantite.
 * @return String
 */
public String getQuantite() {
	return quantite;
}
/**
 * Setter de l'attribut quantite.
 */
/**
 * @param newQuantite newQuantite
 */
public void setQuantite(String newQuantite) { 
	quantite = newQuantite;
}
/**
 * Getter de l'attribut numot.
 * @return String
 */
public String getNumot() {
	return numot;
}
/**
 * Setter de l'attribut numot.
 */
/**
 * @param newNumot newNumot
 */
public void setNumot(String newNumot) { 
	numot = newNumot;
}
/**
 * Getter de l'attribut prix.
 * @return String
 */
public String getPrix() {
	return prix;
}
/**
 * Setter de l'attribut prix.
 */
/**
 * @param newPrix newPrix
 */
public void setPrix(String newPrix) { 
	prix = newPrix;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PiecesInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PiecesInfosBroker getMyPiecesInfosBroker() {
	return (PiecesInfosBroker)getMyBasicBroker();
}
}
