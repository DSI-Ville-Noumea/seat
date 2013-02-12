package nc.mairie.seat.metier;
/**
 * Objet métier BeFpmInfos
 */
public class BeFpmInfos extends nc.mairie.technique.BasicMetier {
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
 * Getter de l'attribut pminv.
 */
public String getPminv() {
	return pminv;
}
/**
 * Setter de l'attribut pminv.
 */
public void setPminv(String newPminv) { 
	pminv = newPminv;
}
/**
 * Getter de l'attribut dentree.
 */
public String getDentree() {
	return dentree;
}
/**
 * Setter de l'attribut dentree.
 */
public void setDentree(String newDentree) { 
	dentree = newDentree;
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
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new BeFpmInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
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
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerBeFpmInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	BeFpmInfos unBeFpmInfos = new BeFpmInfos();
	return unBeFpmInfos.getMyBeFpmInfosBroker().listerBeFpmInfos(aTransaction);
}
/**
 * Retourne un BeFpmInfos.
 * @return BeFpmInfos
 */
public static BeFpmInfos chercherBeFpmInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	BeFpmInfos unBeFpmInfos = new BeFpmInfos();
	return unBeFpmInfos.getMyBeFpmInfosBroker().chercherBeFpmInfos(aTransaction, code);
}

// recherche pour la numfiche passée en param
public static java.util.ArrayList listerBeFpmInfosFpm(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	BeFpmInfos unBeFpmInfos = new BeFpmInfos();
	return unBeFpmInfos.getMyBeFpmInfosBroker().listerBeFpmInfosFpm(aTransaction,numfiche);
}


/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @return java.util.ArrayList
 */
public static int cumuleMontantBeFpmInfosBE(nc.mairie.technique.Transaction aTransaction,String numot, String numinv) throws Exception{
	BeFpmInfos unBeFpmInfos = new BeFpmInfos();
	return unBeFpmInfos.getMyBeFpmInfosBroker().cumuleMontantBeFpmInfosBE(aTransaction,numot, numinv);
}
}
