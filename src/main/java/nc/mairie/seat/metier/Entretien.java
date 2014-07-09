package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Entretien
 */
public class Entretien extends BasicMetier {
	public String codeentretien;
	public String intervalle;
	public String commentaire;
	public String duree;
	public String libelleentretien;
	public String typeentretien;
	public String codeti;
/**
 * Getter de l'attribut intervalle.
 */
/**
 * @return String
 */
public String getIntervalle() {
	return intervalle;
}
/**
 * Setter de l'attribut intervalle.
 */
/**
 * @param newIntervalle newIntervalle
 */
public void setIntervalle(String newIntervalle) { 
	intervalle = newIntervalle;
}
/**
 * Getter de l'attribut typeentretien.
 */
/**
 * @return String
 */
public String getTypeentretien() {
	return typeentretien;
}
/**
 * Setter de l'attribut typeentretien.
 */
/**
 * @param newTypeentretien newTypeentretien
 */
public void setTypeentretien(String newTypeentretien) { 
	typeentretien = newTypeentretien;
}
/**
 * Getter de l'attribut codeti.
 */
/**
 * @return String
 */
public String getCodeti() {
	return codeti;
}
/**
 * Setter de l'attribut codeti.
 */
/**
 * @param newCodeti newCodeti
 */
public void setCodeti(String newCodeti) { 
	codeti = newCodeti;
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
 * Retourne un ArrayList d'objet métier : Entretien.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Entretien> listerEntretien(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Entretien unEntretien = new Entretien();
	return unEntretien.getMyEntretienBroker().listerEntretien(aTransaction);
}
/**
 * Retourne un Entretien.
 * @param aTransaction aTransaction
 * @param code code
 * @return Entretien
 * @throws Exception Exception
 */
public static Entretien chercherEntretien(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Entretien unEntretien = new Entretien();
	return unEntretien.getMyEntretienBroker().chercherEntretien(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param param param
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerEntretien(nc.mairie.technique.Transaction aTransaction,String param )  throws Exception {
	if (!existeEntretien(aTransaction,param)){
//		on ajoute le code de la marque
		int nouvcodeEntretien = nouvEntretien(aTransaction);
		setCodeentretien(String.valueOf(nouvcodeEntretien));
		//Creation du Entretien
		return getMyEntretienBroker().creerEntretien(aTransaction);
	}else{
		aTransaction.declarerErreur("Cet entretien est déjà enregistré.");
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
public boolean modifierEntretien(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	if (!existeEntretien(aTransaction,param)){
		//Modification du Entretien
		return getMyEntretienBroker().modifierEntretien(aTransaction);
	}else{
		aTransaction.declarerErreur("Cet entretien est déjà enregistré.");
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
public boolean supprimerEntretien(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//Suppression de l'Entretien
	return getMyEntretienBroker().supprimerEntretien(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvEntretien(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	int nouvcodeEntretien = getMyEntretienBroker().nouvEntretien(aTransaction);
	
	//si pas trouvé
	if (nouvcodeEntretien == -1) {
		//fonctionnellement normal: table vide
		nouvcodeEntretien = 1;
	} else {
		nouvcodeEntretien++;
	}
	
	return nouvcodeEntretien;
	
}

/**
 * Getter de l'attribut duree.
 */
/**
 * @return String
 */
public String getDuree() {
	return duree;
}
/**
 * Setter de l'attribut duree.
 */
/**
 * @param newDuree newDuree
 */
public void setDuree(String newDuree) { 
	duree = newDuree;
}
/**
 * Getter de l'attribut commentaire.
 */
/**
 * @return String
 */
public String getCommentaire() {
	return commentaire;
}
/**
 * Setter de l'attribut commentaire.
 */
/**
 * @param newCommentaire newCommentaire
 */
public void setCommentaire(String newCommentaire) { 
	commentaire = newCommentaire;
}
/**
 * Constructeur Entretien.
 */
public Entretien() {
	super();
}
/**
 * Getter de l'attribut codeentretien.
 */
/**
 * @return String
 */
public String getCodeentretien() {
	return codeentretien;
}
/**
 * Setter de l'attribut codeentretien.
 */
/**
 * @param newCodeentretien newCodeentretien
 */
public void setCodeentretien(String newCodeentretien) { 
	codeentretien = newCodeentretien;
}
/**
 * Getter de l'attribut libelleentretien.
 */
/**
 * @return String
 */
public String getLibelleentretien() {
	return libelleentretien;
}
/**
 * Setter de l'attribut libelleentretien.
 */
/**
 * @param newLibelleentretien newLibelleentretien
 */
public void setLibelleentretien(String newLibelleentretien) { 
	libelleentretien = newLibelleentretien;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new EntretienBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected EntretienBroker getMyEntretienBroker() {
	return (EntretienBroker)getMyBasicBroker();
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @param aTransaction aTransaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeEntretien(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Entretien unEntretien = new Entretien();
	return unEntretien.getMyEntretienBroker().existeEntretien(aTransaction, param);
}

}
