package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Pneu
 */
public class Pneu extends BasicMetier {
	public String codepneu;
	public String dimension;
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
 * Retourne un ArrayList d'objet métier : Pneu.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Pneu> listerPneu(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Pneu unPneu = new Pneu();
	return unPneu.getMyPneuBroker().listerPneu(aTransaction);
}
/**
 * Retourne un Pneu.
 * @param aTransaction aTransaction
 * @param code code
 * @return Pneu
 * @throws Exception Exception
 */
public static Pneu chercherPneu(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Pneu unPneu = new Pneu();
	return unPneu.getMyPneuBroker().chercherPneu(aTransaction, code);
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param param param
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPneu(nc.mairie.technique.Transaction aTransaction,String param)  throws Exception {
	if (!existePneu(aTransaction,param)){
		//on ajoute le code du pneu
		int nouvcodepneu = nouvCodePneu(aTransaction);
		setCodepneu(String.valueOf(nouvcodepneu));
		//Creation du Pneu
		return getMyPneuBroker().creerPneu(aTransaction);
	}else{
		aTransaction.declarerErreur("Cette dimension de pneu est déjà enregistrée.");
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
public boolean modifierPneu(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	if(!existePneu(aTransaction,param)){
		//Modification du Pneu
		return getMyPneuBroker().modifierPneu(aTransaction);
	}else{
		aTransaction.declarerErreur("Cette dimension de pneu est déjà enregistrée.");
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
public boolean supprimerPneu(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	 si le type de pneu est déjà utilisé on ne peut pas supprimer
	Modeles unModele = new Modeles();
	if (!unModele.existeModelePneu(aTransaction,getCodepneu())){
		//Suppression de l'Pneu
		return getMyPneuBroker().supprimerPneu(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type de pneu est utilisé pour un modèle.La suppression n'est pas possible.");
		return false;
	}
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodePneu(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	int nouvcodepneu = getMyPneuBroker().nouvCodePneu(aTransaction);
	
	//si pas trouvé
	if (nouvcodepneu == -1) {
		//fonctionnellement normal: table vide
		nouvcodepneu = 1;
	} else {
		nouvcodepneu++;
	}
	
	return nouvcodepneu;
	
}
/**
 * Retourne un booléen.
 * Vérifie si existe
 * @param aTransaction aTransaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existePneu(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Pneu unPneu = new Pneu();
	return unPneu.getMyPneuBroker().existePneu(aTransaction, param);
}
/**
 * Constructeur Pneu.
 */
public Pneu() {
	super();
}
/**
 * Getter de l'attribut codepneu.
 * @return String
 */
public String getCodepneu() {
	return codepneu;
}
/**
 * Setter de l'attribut codepneu.
 */
/**
 * @param newCodepneu newCodepneu
 */
public void setCodepneu(String newCodepneu) { 
	codepneu = newCodepneu;
}
/**
 * Getter de l'attribut dimension.
 * @return String
 */
public String getDimension() {
	return dimension;
}
/**
 * Setter de l'attribut dimension.
 */
/**
 * @param newDimension newDimension
 */
public void setDimension(String newDimension) { 
	dimension = newDimension;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PneuBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PneuBroker getMyPneuBroker() {
	return (PneuBroker)getMyBasicBroker();
}
}
