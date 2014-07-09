package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Fre_OT
 */
public class Fre_OT extends BasicMetier {
	public String codefournisseur;
	public String numeroot;
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
 * Retourne un ArrayList d'objet métier : Fre_OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Fre_OT> listerFre_OT(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Fre_OT unFre_OT = new Fre_OT();
	return unFre_OT.getMyFre_OTBroker().listerFre_OT(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : Fre_OT.
 * @param aTransaction aTransaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Fre_OT> listerFre_OTOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	Fre_OT unFre_OT = new Fre_OT();
	return unFre_OT.getMyFre_OTBroker().listerFre_OTOT(aTransaction,numot);
}

/**
 * Retourne un Fre_OT.
 * @param aTransaction aTransaction
 * @param numot numot
 * @param codeF codeF
 * @return Fre_OT
 * @throws Exception Exception
 */
public static Fre_OT chercherFre_OT(nc.mairie.technique.Transaction aTransaction, String numot,String codeF) throws Exception{
	Fre_OT unFre_OT = new Fre_OT();
	return unFre_OT.getMyFre_OTBroker().chercherFre_OT(aTransaction, numot, codeF);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param unOT unOT
 * @param unFre unFre
 * @return boolean 
 * @throws Exception Exception
 */

public boolean creerFre_OT(nc.mairie.technique.Transaction aTransaction,OT unOT,Fournisseurs unFre )  throws Exception {
	//controle si null
	if (null == unOT.getNumeroot()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","OT"));
		return false;
	}
	if (null == unFre.getIdetbs()){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Fournisseurs"));
		return false;
	}
	//controle si pas déjà enregistré
	if(existeFre_OT(aTransaction,unOT.getNumeroot(),unFre.getIdetbs())){
		aTransaction.declarerErreur("Ce fournisseur a déjà été enregistré pour cet OT.");
		return false;
	}
	
	//on renseigne les clés étrangères
	setCodefournisseur(unFre.getIdetbs());
	setNumeroot(unOT.getNumeroot());
	
	//Creation du Fre_OT
	return getMyFre_OTBroker().creerFre_OT(aTransaction);
}

/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean modifierFre_OT(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du Fre_OT
	return getMyFre_OTBroker().modifierFre_OT(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean supprimerFre_OT(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'Fre_OT
	return getMyFre_OTBroker().supprimerFre_OT(aTransaction);
}

public boolean existeFre_OT(nc.mairie.technique.Transaction aTransaction,String numot,String codeFre) throws Exception{
	//Suppression de l'Fre_OT
	return getMyFre_OTBroker().existeFre_OT(aTransaction,numot,codeFre);
}

/**
 * Constructeur Fre_OT.
 */
public Fre_OT() {
	super();
}
/**
 * Getter de l'attribut codefournisseur.
 */
/**
 * @return String
 */
public String getCodefournisseur() {
	return codefournisseur;
}
/**
 * Setter de l'attribut codefournisseur.
 */
/**
 * @param newCodefournisseur newCodefournisseur
 */
public void setCodefournisseur(String newCodefournisseur) { 
	codefournisseur = newCodefournisseur;
}
/**
 * Getter de l'attribut numeroot.
 */
/**
 * @return String
 */
public String getNumeroot() {
	return numeroot;
}
/**
 * Setter de l'attribut numeroot.
 */
/**
 * @param newNumeroot newNumeroot
 */
public void setNumeroot(String newNumeroot) { 
	numeroot = newNumeroot;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new Fre_OTBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected Fre_OTBroker getMyFre_OTBroker() {
	return (Fre_OTBroker)getMyBasicBroker();
}
}
