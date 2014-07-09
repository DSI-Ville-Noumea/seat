package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier BeFpmInfos
 */
public class BeFpmInfos extends BasicMetier {
	public String numfiche;
	public String pminv;
	public String dentree;
	public String dsortie;
	public String valide;
	public String commentaire;
	public String codcol;
	public String exerci;
	public String codbud;
	public String noengj;
/**
 * Constructeur BeFpmInfos.
 */
public BeFpmInfos() {
	super();
}
/**
 * Getter de l'attribut numfiche.
 */
/**
 * @return String
 */
public String getNumfiche() {
	return numfiche;
}
/**
 * Setter de l'attribut numfiche.
 */
/**
 * @param newNumfiche newNumfiche
 */
public void setNumfiche(String newNumfiche) { 
	numfiche = newNumfiche;
}
/**
 * Getter de l'attribut pminv.
 */
/**
 * @return String
 */
public String getPminv() {
	return pminv;
}
/**
 * Setter de l'attribut pminv.
 */
/**
 * @param newPminv newPminv
 */
public void setPminv(String newPminv) { 
	pminv = newPminv;
}
/**
 * Getter de l'attribut dentree.
 */
/**
 * @return String
 */
public String getDentree() {
	return dentree;
}
/**
 * Setter de l'attribut dentree.
 */
/**
 * @param newDentree newDentree
 */
public void setDentree(String newDentree) { 
	dentree = newDentree;
}
/**
 * Getter de l'attribut dsortie.
 */
/**
 * @return String
 */
public String getDsortie() {
	return dsortie;
}
/**
 * Setter de l'attribut dsortie.
 */
/**
 * @param newDsortie newDsortie
 */
public void setDsortie(String newDsortie) { 
	dsortie = newDsortie;
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
 * Getter de l'attribut codcol.
 */
/**
 * @return String
 */
public String getCodcol() {
	return codcol;
}
/**
 * Setter de l'attribut codcol.
 */
/**
 * @param newCodcol newCodcol
 */
public void setCodcol(String newCodcol) { 
	codcol = newCodcol;
}
/**
 * Getter de l'attribut exerci.
 */
/**
 * @return String
 */
public String getExerci() {
	return exerci;
}
/**
 * Setter de l'attribut exerci.
 */
/**
 * @param newExerci newExerci
 */
public void setExerci(String newExerci) { 
	exerci = newExerci;
}
/**
 * Getter de l'attribut codbud.
 * @return codbud
 */
public String getCodbud() {
	return codbud;
}
/**
 * Setter de l'attribut codbud.
 * @param newCodbud newCodbud
 */
public void setCodbud(String newCodbud) { 
	codbud = newCodbud;
}
/**
 * Getter de l'attribut noengj.
 * @return noengj 
 */
public String getNoengj() {
	return noengj;
}
/**
 * Setter de l'attribut noengj.
 * @param newNoengj newNoengj
 */
public void setNoengj(String newNoengj) { 
	noengj = newNoengj;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new BeFpmInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected BeFpmInfosBroker getMyBeFpmInfosBroker() {
	return (BeFpmInfosBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : BeFpmInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<BeFpmInfos> listerBeFpmInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	BeFpmInfos unBeFpmInfos = new BeFpmInfos();
	return unBeFpmInfos.getMyBeFpmInfosBroker().listerBeFpmInfos(aTransaction);
}
/**
 * Retourne un BeFpmInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return BeFpmInfos
 * @throws Exception Exception
 */
public static BeFpmInfos chercherBeFpmInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	BeFpmInfos unBeFpmInfos = new BeFpmInfos();
	return unBeFpmInfos.getMyBeFpmInfosBroker().chercherBeFpmInfos(aTransaction, code);
}

// recherche pour la numfiche passée en param
public static ArrayList<BeFpmInfos> listerBeFpmInfosFpm(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	BeFpmInfos unBeFpmInfos = new BeFpmInfos();
	return unBeFpmInfos.getMyBeFpmInfosBroker().listerBeFpmInfosFpm(aTransaction,numfiche);
}


/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @param aTransaction aTransaction
 * @param numot numot
 * @param numinv numinv
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static int cumuleMontantBeFpmInfosBE(nc.mairie.technique.Transaction aTransaction,String numot, String numinv) throws Exception{
	BeFpmInfos unBeFpmInfos = new BeFpmInfos();
	return unBeFpmInfos.getMyBeFpmInfosBroker().cumuleMontantBeFpmInfosBE(aTransaction,numot, numinv);
}
}
