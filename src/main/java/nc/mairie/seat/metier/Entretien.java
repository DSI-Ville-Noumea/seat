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
public String getIntervalle() {
	return intervalle;
}
/**
 * Setter de l'attribut intervalle.
 */
public void setIntervalle(String newIntervalle) { 
	intervalle = newIntervalle;
}
/**
 * Getter de l'attribut typeentretien.
 */
public String getTypeentretien() {
	return typeentretien;
}
/**
 * Setter de l'attribut typeentretien.
 */
public void setTypeentretien(String newTypeentretien) { 
	typeentretien = newTypeentretien;
}
/**
 * Getter de l'attribut codeti.
 */
public String getCodeti() {
	return codeti;
}
/**
 * Setter de l'attribut codeti.
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
 * @return java.util.ArrayList
 */
public static ArrayList<Entretien> listerEntretien(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Entretien unEntretien = new Entretien();
	return unEntretien.getMyEntretienBroker().listerEntretien(aTransaction);
}
/**
 * Retourne un Entretien.
 * @return Entretien
 */
public static Entretien chercherEntretien(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Entretien unEntretien = new Entretien();
	return unEntretien.getMyEntretienBroker().chercherEntretien(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
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
public String getDuree() {
	return duree;
}
/**
 * Setter de l'attribut duree.
 */
public void setDuree(String newDuree) { 
	duree = newDuree;
}
/**
 * Getter de l'attribut commentaire.
 */
public String getCommentaire() {
	return commentaire;
}
/**
 * Setter de l'attribut commentaire.
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
public String getCodeentretien() {
	return codeentretien;
}
/**
 * Setter de l'attribut codeentretien.
 */
public void setCodeentretien(String newCodeentretien) { 
	codeentretien = newCodeentretien;
}
/**
 * Getter de l'attribut libelleentretien.
 */
public String getLibelleentretien() {
	return libelleentretien;
}
/**
 * Setter de l'attribut libelleentretien.
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
*/
protected EntretienBroker getMyEntretienBroker() {
	return (EntretienBroker)getMyBasicBroker();
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existeEntretien(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Entretien unEntretien = new Entretien();
	return unEntretien.getMyEntretienBroker().existeEntretien(aTransaction, param);
}

}
