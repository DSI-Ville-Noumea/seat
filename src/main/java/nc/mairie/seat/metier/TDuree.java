package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier TDuree
 */
public class TDuree extends BasicMetier {
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
 * @return String
 */
public String getCodetd() {
	return codetd;
}
/**
 * Setter de l'attribut codetd.
 */
/**
 * @param newCodetd newCodetd
 */
public void setCodetd(String newCodetd) { 
	codetd = newCodetd;
}
/**
 * Getter de l'attribut designationduree.
 * @return String
 */
public String getDesignationduree() {
	return designationduree;
}
/**
 * Setter de l'attribut designationduree.
 */
/**
 * @param newDesignationduree newDesignationduree
 */
public void setDesignationduree(String newDesignationduree) { 
	designationduree = newDesignationduree;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new TDureeBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
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
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<TDuree> listerTDuree(nc.mairie.technique.Transaction aTransaction) throws Exception{
	TDuree unTDuree = new TDuree();
	return unTDuree.getMyTDureeBroker().listerTDuree(aTransaction);
}
/**
 * Retourne un TDuree.
 * @param aTransaction aTransaction
 * @param code code
 * @return TDuree
 * @throws Exception Exception
 */
public static TDuree chercherTDuree(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	TDuree unTDuree = new TDuree();
	return unTDuree.getMyTDureeBroker().chercherTDuree(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param param param
 * @return boolean
 * @throws Exception Exception
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
 * @param aTransaction aTransaction
 * @param param param
 * @return boolean
 * @throws Exception Exception
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
 * @param aTransaction aTransaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean supprimerTDuree(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'TDuree
	return getMyTDureeBroker().supprimerTDuree(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
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
 * @param aTransaction aTransaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeTDuree(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	TDuree unTDuree = new TDuree();
	return unTDuree.getMyTDureeBroker().existeTDuree(aTransaction, param);
}

}
