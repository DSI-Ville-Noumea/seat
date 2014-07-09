package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier BE
 */
public class BE extends BasicMetier {
	public String numot;
	public String exerci;
	public String noengj;
	public String codcol;
	public String codbud;
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
 * Retourne un ArrayList d'objet métier : BE.
 * @param aTransaction aTransaction
 * @param numot numot
 * @param numInv numInv
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<BE> listerBEOTNumInv(nc.mairie.technique.Transaction aTransaction,String numot, String numInv) throws Exception{
	BE unBE = new BE();
	return unBE.getMyBEBroker().listerBEOTNumInv(aTransaction,numot, numInv);
}
/**
 * Retourne un BE.
 * @param aTransaction aTransaction
 * @param noot noot
 * @param exerci exerci
 * @param noengj noengj
 * @return BE
 * @throws Exception Exception
 */
public boolean existeBE(nc.mairie.technique.Transaction aTransaction, String noot,String exerci,String noengj) throws Exception{
	//BE unBE = new BE();
	return getMyBEBroker().existeBE(aTransaction, noot,exerci,noengj);
}
/**
 * Retourne si un BE existe
 * @param aTransaction aTransaction
 * @param noot noot
 * @return boolean
 * @throws Exception Exception
 */
public static boolean existeBEOT(nc.mairie.technique.Transaction aTransaction, String noot) throws Exception{
	BE unBE = new BE();
	return unBE.getMyBEBroker().existeBEOT(aTransaction, noot);
}

/**
 * Retourne un BE.
 * @param aTransaction aTransaction
 * @param noot noot
 * @param noengj noengj
 * @return BE
 * @throws Exception Exception
 */
public static BE chercheBE(nc.mairie.technique.Transaction aTransaction, String noot,String noengj) throws Exception{
	BE unBE = new BE();
	return unBE.getMyBEBroker().chercheBE(aTransaction, noot,noengj);
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param unOT unOT
 * @param unEnju unEnju
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerBE(nc.mairie.technique.Transaction aTransaction,OT unOT, ENGJU unEnju )  throws Exception {
	// controle si null
	if (null==unOT){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","OT"));
		return false;
	}
	if (null==unEnju){
		aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Engagement"));
		return false;
	}
	//RG : controle si l'enju a déjà été enregistré pour cet OT
	if(existeBE(aTransaction,unOT.getNumeroot(),unEnju.getExerci(),unEnju.getNoengj())){
		aTransaction.declarerErreur("L'engagement juridique ("+unEnju.getNoengj()+") est déjà enregistré pour cet OT("+unOT.getNumeroot()+").");
		return false;
	}
	//String codeBe = unEnju.getCodcoll()+unEnju.getExerci()+unEnju.getCodbudg()+unEnju.getNoengju();
	//initialisation des clés étrangères
	setCodbud("9");
	setCodcol("100");
	setExerci(unEnju.getExerci());
	setNoengj(unEnju.getNoengj());
	setNumot(unOT.getNumeroot());
	//Creation du BE
	return getMyBEBroker().creerBE(aTransaction);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean modifierBE(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du BE
	return getMyBEBroker().modifierBE(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean supprimerBE(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'BE
	return getMyBEBroker().supprimerBE(aTransaction);
}
	
/**
 * Constructeur BE.
 */
public BE() {
	super();
}
/**
 * Getter de l'attribut numot.
 */
/**
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
 * Getter de l'attribut noengj.
 */
/**
 * @return String
 */
public String getNoengj() {
	return noengj;
}
/**
 * Setter de l'attribut noengj.
 */
/**
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
	return new BEBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected BEBroker getMyBEBroker() {
	return (BEBroker)getMyBasicBroker();
}
public String getCodbud() {
	return codbud;
}
public void setCodbud(String codbud) {
	this.codbud = codbud;
}
public String getCodcol() {
	return codcol;
}
public void setCodcol(String codcol) {
	this.codcol = codcol;
}
}
