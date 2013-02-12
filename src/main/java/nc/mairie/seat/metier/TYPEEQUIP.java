package nc.mairie.seat.metier;
/**
 * Objet métier TYPEEQUIP
 */
public class TYPEEQUIP extends nc.mairie.technique.BasicMetier {
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
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerTYPEEQUIP(nc.mairie.technique.Transaction aTransaction) throws Exception{
	TYPEEQUIP unTYPEEQUIP = new TYPEEQUIP();
	return unTYPEEQUIP.getMyTYPEEQUIPBroker().listerTYPEEQUIP(aTransaction);
}
/**
 * Retourne un TYPEEQUIP.
 * @return TYPEEQUIP
 */
public static TYPEEQUIP chercherTYPEEQUIP(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	TYPEEQUIP unTYPEEQUIP = new TYPEEQUIP();
	return unTYPEEQUIP.getMyTYPEEQUIPBroker().chercherTYPEEQUIP(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
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
 * @author : Coralie NICOLAS
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
 * @return true ou false
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
 */
public String getCodete() {
	return codete;
}
/**
 * Setter de l'attribut codete.
 */
public void setCodete(String newCodete) { 
	codete = newCodete;
}
/**
 * Getter de l'attribut designationte.
 */
public String getDesignationte() {
	return designationte;
}
/**
 * Setter de l'attribut designationte.
 */
public void setDesignationte(String newDesignationte) { 
	designationte = newDesignationte;
}
/**
 * Getter de l'attribut typete.
 */
public String getTypete() {
	return typete;
}
/**
 * Setter de l'attribut typete.
 */
public void setTypete(String newTypete) { 
	typete = newTypete;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new TYPEEQUIPBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected TYPEEQUIPBroker getMyTYPEEQUIPBroker() {
	return (TYPEEQUIPBroker)getMyBasicBroker();
}
}
