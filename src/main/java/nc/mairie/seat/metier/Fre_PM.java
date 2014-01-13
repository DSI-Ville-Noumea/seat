package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Fre_PM
 */
public class Fre_PM extends BasicMetier {
	public String codefre;
	public String libellefre;
	public String observationsfre;
	public String contact;
/**
 * Constructeur Fre_PM.
 */
public Fre_PM() {
	super();
}
/**
 * Getter de l'attribut codefre.
 */
public String getCodefre() {
	return codefre;
}
/**
 * Setter de l'attribut codefre.
 */
public void setCodefre(String newCodefre) { 
	codefre = newCodefre;
}
/**
 * Getter de l'attribut libellefre.
 */
public String getLibellefre() {
	return libellefre;
}
/**
 * Setter de l'attribut libellefre.
 */
public void setLibellefre(String newLibellefre) { 
	libellefre = newLibellefre;
}
/**
 * Getter de l'attribut observationsfre.
 */
public String getObservationsfre() {
	return observationsfre;
}
/**
 * Setter de l'attribut observationsfre.
 */
public void setObservationsfre(String newObservationsfre) { 
	observationsfre = newObservationsfre;
}
/**
 * Getter de l'attribut contact.
 */
public String getContact() {
	return contact;
}
/**
 * Setter de l'attribut contact.
 */
public void setContact(String newContact) { 
	contact = newContact;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new Fre_PMBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected Fre_PMBroker getMyFre_PMBroker() {
	return (Fre_PMBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : Fre_PM.
 * @return java.util.ArrayList
 */
public static ArrayList<Fre_PM> listerFre_PM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Fre_PM unFre_PM = new Fre_PM();
	return unFre_PM.getMyFre_PMBroker().listerFre_PM(aTransaction);
}
/**
 * Retourne un Fre_PM.
 * @return Fre_PM
 */
public static Fre_PM chercherFre_PM(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Fre_PM unFre_PM = new Fre_PM();
	return unFre_PM.getMyFre_PMBroker().chercherFre_PM(aTransaction, code);
}
/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvFre_PM(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier codeCarburant
	int nouvcodeFre_PM = getMyFre_PMBroker().nouvFre_PM(aTransaction);
	
	//si pas trouvé
	if (nouvcodeFre_PM == -1) {
		//fonctionnellement normal: table vide
		nouvcodeFre_PM = 1;
	} else {
		nouvcodeFre_PM++;
	}
	return nouvcodeFre_PM;
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerFre_PM(nc.mairie.technique.Transaction aTransaction, String param,String observation,String contact)  throws Exception {
	if (!existeFre_PM(aTransaction,param,observation,contact)){
		//	on ajoute le code du carburant
			int nouvcodeFre_PM = nouvFre_PM(aTransaction);
			setCodefre(String.valueOf(nouvcodeFre_PM));
			//Creation du Fre_PM
			return getMyFre_PMBroker().creerFre_PM(aTransaction);
		}else{
			aTransaction.declarerErreur("Ce fournisseur de petit matériel est déjà enregistré.");
			return false;
		}
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierFre_PM(nc.mairie.technique.Transaction aTransaction,String param,String observation,String contact) throws Exception {
	if (!existeFre_PM(aTransaction,param,observation,contact)){
		//	Modification du Fre_PM
		return getMyFre_PMBroker().modifierFre_PM(aTransaction);
	}else{
		aTransaction.declarerErreur("Un fournisseur de petit matériel de même libellé existe dans la base.");
		return false;
	}
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerFre_PM(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	 si déjà utilisé on ne peut pas supprimer
	PMateriel unPMateriel = new PMateriel();
	if (!unPMateriel.existePMaterielFre(aTransaction,getCodefre())){
		///Suppression de l'Fre_PM
		return getMyFre_PMBroker().supprimerFre_PM(aTransaction);
	}else{
		aTransaction.declarerErreur("Au moins un petit matériel est rattaché à ce fournisseur est donc impossible.");
		return false;
	}
	
}
/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existeFre_PM(nc.mairie.technique.Transaction aTransaction, String param,String observation,String contact) throws Exception{
	Fre_PM unFre_PM = new Fre_PM();
	return unFre_PM.getMyFre_PMBroker().existeFre_PM(aTransaction, param,observation,contact);
}
}
