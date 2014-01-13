package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PiecesFpmInfos
 */
public class PiecesFpmInfos extends BasicMetier {
	public String codepiece;
	public String designationpiece;
	public String pu;
	public String numfiche;
	public String numpiece;
	public String dsortie;
	public String quantite;
	public String prix;
/**
 * Constructeur PiecesFpmInfos.
 */
public PiecesFpmInfos() {
	super();
}
/**
 * Getter de l'attribut codepiece.
 */
public String getCodepiece() {
	return codepiece;
}
/**
 * Setter de l'attribut codepiece.
 */
public void setCodepiece(String newCodepiece) { 
	codepiece = newCodepiece;
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
 * Getter de l'attribut dsortie.
 */
public String getDsortie() {
	return dsortie;
}
/**
 * Setter de l'attribut dsortie.
 */
public void setDsortie(String newDsortie) { 
	dsortie = newDsortie;
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
	return new PiecesFpmInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected PiecesFpmInfosBroker getMyPiecesFpmInfosBroker() {
	return (PiecesFpmInfosBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : PiecesFpmInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<PiecesFpmInfos> listerPiecesFpmInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PiecesFpmInfos unPiecesFpmInfos = new PiecesFpmInfos();
	return unPiecesFpmInfos.getMyPiecesFpmInfosBroker().listerPiecesFpmInfos(aTransaction);
}
/**
 * Retourne un PiecesFpmInfos.
 * @return PiecesFpmInfos
 */
public static PiecesFpmInfos chercherPiecesFpmInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PiecesFpmInfos unPiecesFpmInfos = new PiecesFpmInfos();
	return unPiecesFpmInfos.getMyPiecesFpmInfosBroker().chercherPiecesFpmInfos(aTransaction, code);
}

/**
 * Retourne un ArrayList d'objet métier : PiecesInfos.
 * @return java.util.ArrayList
 */
public static ArrayList<PiecesFpmInfos> chercherFpmPiecesInfosFpm(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	PiecesFpmInfos unPiecesInfos = new PiecesFpmInfos();
	return unPiecesInfos.getMyPiecesFpmInfosBroker().chercherPiecesFpmInfosFpm(aTransaction,numfiche);
}

/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @return java.util.ArrayList
 */
public static int cumuleMontantFpmInfos(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	PiecesFpmInfos unPiecesFpmInfos = new PiecesFpmInfos();
	return unPiecesFpmInfos.getMyPiecesFpmInfosBroker().cumuleMontantFpmInfos(aTransaction,numot);
}

}
