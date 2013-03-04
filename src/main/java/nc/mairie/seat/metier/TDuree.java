package nc.mairie.seat.metier;
/**
 * Objet métier TDuree
 */
public class TDuree extends nc.mairie.technique.BasicMetier {
	public String codetd;
	public String designationduree;
/**
 * Constructeur TDuree.
 */
public TDuree() {
	super();
}
/**
 * Getter de l'attribut codetd.
 */
public String getCodetd() {
	return codetd;
}
/**
 * Setter de l'attribut codetd.
 */
public void setCodetd(String newCodetd) { 
	codetd = newCodetd;
}
/**
 * Getter de l'attribut designationduree.
 */
public String getDesignationduree() {
	return designationduree;
}
/**
 * Setter de l'attribut designationduree.
 */
public void setDesignationduree(String newDesignationduree) { 
	designationduree = newDesignationduree;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new TDureeBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected TDureeBroker getMyTDureeBroker() {
	return (TDureeBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : TDuree.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerTDuree(nc.mairie.technique.Transaction aTransaction) throws Exception{
	TDuree unTDuree = new TDuree();
	return unTDuree.getMyTDureeBroker().listerTDuree(aTransaction);
}
/**
 * Retourne un TDuree.
 * @return TDuree
 */
public static TDuree chercherTDuree(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	TDuree unTDuree = new TDuree();
	return unTDuree.getMyTDureeBroker().chercherTDuree(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerTDuree(nc.mairie.technique.Transaction aTransaction,String param)  throws Exception {
	if(!existeTDuree(aTransaction,param)){
	//	on ajoute le code du pneu
		int nouvcode = nouvCodeTDuree(aTransaction);
		setCodetd(String.valueOf(nouvcode));
		//Creation du TDuree
		return getMyTDureeBroker().creerTDuree(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type de durée est déjà enregistré.");
		return false;
	}
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierTDuree(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	if(!existeTDuree(aTransaction,param)){
		//Modification du TDuree
		return getMyTDureeBroker().modifierTDuree(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type de durée est déjà enregistré.");
		return false;
	}
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerTDuree(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'TDuree
	return getMyTDureeBroker().supprimerTDuree(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
* 
 */
public int nouvCodeTDuree(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier code
	int nouvcodeTD = getMyTDureeBroker().nouvCodeTDuree(aTransaction);
	
	//si pas trouvé
	if (nouvcodeTD == -1) {
		//fonctionnellement normal: table vide
		nouvcodeTD = 1;
	} else {
		nouvcodeTD++;
	}
	
	return nouvcodeTD;	
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existeTDuree(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	TDuree unTDuree = new TDuree();
	return unTDuree.getMyTDureeBroker().existeTDuree(aTransaction, param);
}

}
