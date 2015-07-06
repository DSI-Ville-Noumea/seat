package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Compteur
 */
public class Compteur extends BasicMetier {
	public String codecompteur;
	public String designationcompteur;
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
 * Retourne un ArrayList d'objet métier : Compteur.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Compteur> listerCompteur(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Compteur unCompteur = new Compteur();
	return unCompteur.getMyCompteurBroker().listerCompteur(aTransaction);
}
/**
 * Retourne un Compteur.
 * @param aTransaction aTransaction
 * @param code code
 * @return Compteur
 * @throws Exception Exception
 */
public static Compteur chercherCompteur(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Compteur unCompteur = new Compteur();
	return unCompteur.getMyCompteurBroker().chercherCompteur(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param param param
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerCompteur(nc.mairie.technique.Transaction aTransaction,String param)  throws Exception {
	if (!existeCompteur(aTransaction,param)){
	//	on ajoute le code du compteur
		int nouvcodecompteur = nouvCompteur(aTransaction);
		setCodecompteur(String.valueOf(nouvcodecompteur));
		//Creation du Compteur
		return getMyCompteurBroker().creerCompteur(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce compteur est déjà enregistré.");
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
public boolean modifierCompteur(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	if (!existeCompteur(aTransaction,param)){
		//Modification du Compteur
		return getMyCompteurBroker().modifierCompteur(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce compteur est déjà enregistré.");
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
public boolean supprimerCompteur(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	 si déjà utilisé on ne peut pas supprimer
	Modeles unModele = new Modeles();
	if (!unModele.existeModeleCompteur(aTransaction,getCodecompteur())){
		//Suppression de l'Compteur
		return getMyCompteurBroker().supprimerCompteur(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce compteur est utilisé pour un modèle.La suppression n'est pas possible.");
		return false;
	}
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * author : Coralie NICOLAS
 */
public int nouvCompteur(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codeCompteur
	int nouvcodeCompteur = getMyCompteurBroker().nouvCompteur(aTransaction);
	
	//si pas trouvé
	if (nouvcodeCompteur == -1) {
		//fonctionnellement normal: table vide
		nouvcodeCompteur = 1;
	} else {
		nouvcodeCompteur++;
	}
	
	return nouvcodeCompteur;
	
}
/**
 * Retourne un booléen.
 * Vérifie si existe
 * @param aTransaction aTransaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeCompteur(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Compteur unCompteur = new Compteur();
	return unCompteur.getMyCompteurBroker().existeCompteur(aTransaction, param);
}
/**
 * Constructeur Compteur.
 */
public Compteur() {
	super();
}
/**
 * Getter de l'attribut codecompteur.
 */
/**
 * @return String
 */
public String getCodecompteur() {
	return codecompteur;
}
/**
 * Setter de l'attribut codecompteur.
 */
/**
 * @param newCodecompteur newCodecompteur
 */
public void setCodecompteur(String newCodecompteur) { 
	codecompteur = newCodecompteur;
}
/**
 * Getter de l'attribut designationcompteur.
 */
/**
 * @return String
 */
public String getDesignationcompteur() {
	return designationcompteur;
}
/**
 * Setter de l'attribut designationcompteur.
 */
/**
 * @param newDesignationcompteur newDesignationcompteur
 */
public void setDesignationcompteur(String newDesignationcompteur) { 
	designationcompteur = newDesignationcompteur;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new CompteurBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected CompteurBroker getMyCompteurBroker() {
	return (CompteurBroker)getMyBasicBroker();
}
}
