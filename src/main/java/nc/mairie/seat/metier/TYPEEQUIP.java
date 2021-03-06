package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier TYPEEQUIP
 */
public class TYPEEQUIP extends BasicMetier {
	public String codete;
	public String designationte;
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
 * Retourne un ArrayList d'objet métier : TYPEEQUIP.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<TYPEEQUIP> listerTYPEEQUIP(nc.mairie.technique.Transaction aTransaction) throws Exception{
	TYPEEQUIP unTYPEEQUIP = new TYPEEQUIP();
	return unTYPEEQUIP.getMyTYPEEQUIPBroker().listerTYPEEQUIP(aTransaction);
}
/**
 * Retourne un TYPEEQUIP.
 * @param aTransaction aTransaction
 * @param code code
 * @return TYPEEQUIP
 * @throws Exception Exception
 */
public static TYPEEQUIP chercherTYPEEQUIP(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	TYPEEQUIP unTYPEEQUIP = new TYPEEQUIP();
	return unTYPEEQUIP.getMyTYPEEQUIPBroker().chercherTYPEEQUIP(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param param param
 * @param type type
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerTYPEEQUIP(nc.mairie.technique.Transaction aTransaction,String param,String type)  throws Exception {
	if(!existeTEquip(aTransaction,param,type)){
	//	on ajoute le code du type d'équipement
		int nouvcodete = nouvTE(aTransaction);
		setCodete(String.valueOf(nouvcodete));
		//Creation du TYPEEQUIP
		return getMyTYPEEQUIPBroker().creerTYPEEQUIP(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type d'équipement est déjà enregistré.");
		return false;
	}
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param param param
 * @param type type
 * @return boolean
 * @throws Exception Exception
 */
public boolean modifierTYPEEQUIP(nc.mairie.technique.Transaction aTransaction,String param,String type) throws Exception {
	if(!existeTEquip(aTransaction,param,type)){
		//Modification du TYPEEQUIP
		return getMyTYPEEQUIPBroker().modifierTYPEEQUIP(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type d'équipement est déjà enregistré.");
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
public boolean supprimerTYPEEQUIP(nc.mairie.technique.Transaction aTransaction) throws Exception{
	// si le type d'équipement est déjà utilisé on ne peut pas supprimer
	Modeles unModele = new Modeles();
	if (!unModele.existeModeleTEquip(aTransaction,getCodete())){
//		Suppression de l'TYPEEQUIP
		return getMyTYPEEQUIPBroker().supprimerTYPEEQUIP(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type d'équipement est utilisé pour un modèle.La suppression n'est pas possible.");
		return false;
	}
	
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * author : Coralie NICOLAS
 */
public int nouvTE(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	int nouvcodeTe = getMyTYPEEQUIPBroker().nouvTE(aTransaction);
	
	//si pas trouvé
	if (nouvcodeTe == -1) {
		//fonctionnellement normal: table vide
		nouvcodeTe = 1;
	} else {
		nouvcodeTe++;
	}
	
	return nouvcodeTe;
	
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @param aTransaction aTransaction
 * @param param param
 * @param type type
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeTEquip(nc.mairie.technique.Transaction aTransaction, String param,String type) throws Exception{
	TYPEEQUIP unTe = new TYPEEQUIP();
	return unTe.getMyTYPEEQUIPBroker().existeTEquip(aTransaction, param,type);
}
	public String typete;
/**
 * Constructeur TYPEEQUIP.
 */
public TYPEEQUIP() {
	super();
}
/**
 * Getter de l'attribut codete.
 * @return String
 */
public String getCodete() {
	return codete;
}
/**
 * Setter de l'attribut codete.
 */
/**
 * @param newCodete newCodete
 */
public void setCodete(String newCodete) { 
	codete = newCodete;
}
/**
 * Getter de l'attribut designationte.
 * @return String
 */
public String getDesignationte() {
	return designationte;
}
/**
 * Setter de l'attribut designationte.
 */
/**
 * @param newDesignationte newDesignationte
 */
public void setDesignationte(String newDesignationte) { 
	designationte = newDesignationte;
}
/**
 * Getter de l'attribut typete.
 * @return String
 */
public String getTypete() {
	return typete;
}
/**
 * Setter de l'attribut typete.
 */
/**
 * @param newTypete newTypete
 */
public void setTypete(String newTypete) { 
	typete = newTypete;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new TYPEEQUIPBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected TYPEEQUIPBroker getMyTYPEEQUIPBroker() {
	return (TYPEEQUIPBroker)getMyBasicBroker();
}
}
