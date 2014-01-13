package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier FPMComplete
 */
public class FPMComplete extends BasicMetier {
	public String numfiche;
	public String pminv;
	public String dentree;
	public String dsortie;
	public String valide;
	public String pmserie;
	public String dmes;
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
 * Retourne un ArrayList d'objet métier : FPMComplete.
 * @return java.util.ArrayList
 */
public static ArrayList<FPMComplete> listerFPMComplete(nc.mairie.technique.Transaction aTransaction) throws Exception{
	FPMComplete unFPMComplete = new FPMComplete();
	return unFPMComplete.getMyFPMCompleteBroker().listerFPMComplete(aTransaction);
}
/**
 * Retourne un FPMComplete.
 * @return FPMComplete
 */
public static FPMComplete chercherFPMComplete(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	FPMComplete unFPMComplete = new FPMComplete();
	return unFPMComplete.getMyFPMCompleteBroker().chercherFPMComplete(aTransaction, code);
}

// liste des encours
public static ArrayList<FPMComplete> listerFPMCompleteEnCours(nc.mairie.technique.Transaction aTransaction) throws Exception{
	FPMComplete unFPMComplete = new FPMComplete();
	return unFPMComplete.getMyFPMCompleteBroker().listerFPMCompleteEnCours(aTransaction);
}

//liste des encours
public static ArrayList<FPMComplete> listerFPMCompleteValide(nc.mairie.technique.Transaction aTransaction) throws Exception{
	FPMComplete unFPMComplete = new FPMComplete();
	return unFPMComplete.getMyFPMCompleteBroker().listerFPMCompleteValide(aTransaction);
}

	public String commentaire;
/**
 * Constructeur FPMComplete.
 */
public FPMComplete() {
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
 * Getter de l'attribut pmserie.
 */
public String getPmserie() {
	return pmserie;
}
/**
 * Setter de l'attribut pmserie.
 */
public void setPmserie(String newPmserie) { 
	pmserie = newPmserie;
}
/**
 * Getter de l'attribut dmes.
 */
public String getDmes() {
	return dmes;
}
/**
 * Setter de l'attribut dmes.
 */
public void setDmes(String newDmes) { 
	dmes = newDmes;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new FPMCompleteBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected FPMCompleteBroker getMyFPMCompleteBroker() {
	return (FPMCompleteBroker)getMyBasicBroker();
}
}
