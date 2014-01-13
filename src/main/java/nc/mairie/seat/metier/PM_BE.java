package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier PM_BE
 */
public class PM_BE extends BasicMetier {
	public String numfiche;
	public String codcol;
	public String exerci;
	public String codbud;
	public String noengj;
/**
 * Constructeur PM_BE.
 */
public PM_BE() {
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
	return new PM_BEBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected PM_BEBroker getMyPM_BEBroker() {
	return (PM_BEBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : PM_BE.
 * @return java.util.ArrayList
 */
public static ArrayList<PM_BE> listerPM_BE(nc.mairie.technique.Transaction aTransaction) throws Exception{
	PM_BE unPM_BE = new PM_BE();
	return unPM_BE.getMyPM_BEBroker().listerPM_BE(aTransaction);
}
/**
 * Retourne un PM_BE.
 * @return PM_BE
 */
public static PM_BE chercherPM_BE(nc.mairie.technique.Transaction aTransaction, String numfiche, String noengj) throws Exception{
	PM_BE unPM_BE = new PM_BE();
	return unPM_BE.getMyPM_BEBroker().chercherPM_BE(aTransaction, numfiche,noengj);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerPM_BE(nc.mairie.technique.Transaction aTransaction,FPM unPMatFiche,ENGJU unEnju )  throws Exception {
		// controle si null
		if (null==unPMatFiche){
			aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","fiche d'entretien du petit matériel"));
			return false;
		}
		if (null==unEnju){
			aTransaction.declarerErreur(nc.mairie.technique.MairieMessages.getMessage("ERR999","Engagement"));
			return false;
		}
		//RG : controle si l'enju a déjà été enregistré pour cet FPM
		if(existeBE(aTransaction,unPMatFiche.getNumfiche(),unEnju.getNoengj())){
			aTransaction.declarerErreur("L'engagement juridique ("+unEnju.getNoengj()+") est déjà enregistré pour cet FPM("+unPMatFiche.getNumfiche()+").");
			return false;
		}
		//String codeBe = unEnju.getCodcoll()+unEnju.getExerci()+unEnju.getCodbudg()+unEnju.getNoengju();
		//initialisation des clés étrangères
		setExerci(unEnju.getExerci());
		setNoengj(unEnju.getNoengj());
		setNumfiche(unPMatFiche.getNumfiche());
		setCodcol("100");
		setCodbud("9");
	//Creation du PM_BE
	return getMyPM_BEBroker().creerPM_BE(aTransaction);
}
public boolean existeBE(nc.mairie.technique.Transaction aTransaction, String noot,String noengj) throws Exception{
	//BE unBE = new BE();
	return getMyPM_BEBroker().existeBE(aTransaction, noot,noengj);
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierPM_BE(nc.mairie.technique.Transaction aTransaction) throws Exception {
	//Modification du PM_BE
	return getMyPM_BEBroker().modifierPM_BE(aTransaction);
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerPM_BE(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'PM_BE
	return getMyPM_BEBroker().supprimerPM_BE(aTransaction);
}

// liste des BE pour la fpm
public static ArrayList<PM_BE> listerPM_BE_FPM(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception{
	PM_BE unPM_BE = new PM_BE();
	return unPM_BE.getMyPM_BEBroker().listerPM_BE_FPM(aTransaction,numfiche);
}

}
