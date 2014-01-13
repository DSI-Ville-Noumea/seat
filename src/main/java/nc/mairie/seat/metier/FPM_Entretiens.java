package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier FPM_Entretiens
 */
public class FPM_Entretiens extends BasicMetier {
	public String numfiche;
	public String codeentretien;
	public String date;
	public String commentaire;
/**
 * Constructeur FPM_Entretiens.
 */
public FPM_Entretiens() {
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
 * Getter de l'attribut codeentretien.
 */
public String getCodeentretien() {
	return codeentretien;
}
/**
 * Setter de l'attribut codeentretien.
 */
public void setCodeentretien(String newCodeentretien) { 
	codeentretien = newCodeentretien;
}
/**
 * Getter de l'attribut date.
 */
public String getDate() {
	return date;
}
/**
 * Setter de l'attribut date.
 */
public void setDate(String newDate) { 
	date = newDate;
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
	return new FPM_EntretiensBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected FPM_EntretiensBroker getMyFPM_EntretiensBroker() {
	return (FPM_EntretiensBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : FPM_Entretiens.
 * @return java.util.ArrayList
 */
public static ArrayList<FPM_Entretiens> listerFPM_Entretiens(nc.mairie.technique.Transaction aTransaction) throws Exception{
	FPM_Entretiens unFPM_Entretiens = new FPM_Entretiens();
	return unFPM_Entretiens.getMyFPM_EntretiensBroker().listerFPM_Entretiens(aTransaction);
}
/**
 * Retourne un FPM_Entretiens.
 * @return FPM_Entretiens
 */
public static FPM_Entretiens chercherFPM_Entretiens(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	FPM_Entretiens unFPM_Entretiens = new FPM_Entretiens();
	return unFPM_Entretiens.getMyFPM_EntretiensBroker().chercherFPM_Entretiens(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerFPM_Entretiens(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	//Creation du FPM_Entretiens
	return getMyFPM_EntretiensBroker().creerFPM_Entretiens(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierFPM_Entretiens(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du FPM_Entretiens
	return getMyFPM_EntretiensBroker().modifierFPM_Entretiens(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerFPM_Entretiens(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'FPM_Entretiens
	return getMyFPM_EntretiensBroker().supprimerFPM_Entretiens(aTransaction);
}
}
