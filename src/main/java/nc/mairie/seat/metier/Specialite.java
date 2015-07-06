package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Specialite
 */
public class Specialite extends BasicMetier {
	public String codespecialite;
	public String libellespe;
/**
 * Constructeur Specialite.
 */
public Specialite() {
	super();
}
/**
 * Getter de l'attribut codespecialite.
 * @return String
 */
public String getCodespecialite() {
	return codespecialite;
}
/**
 * Setter de l'attribut codespecialite.
 */
/**
 * @param newCodespecialite newCodespecialite
 */
public void setCodespecialite(String newCodespecialite) { 
	codespecialite = newCodespecialite;
}
/**
 * Getter de l'attribut libellespe.
 * @return String
 */
public String getLibellespe() {
	return libellespe;
}
/**
 * Setter de l'attribut libellespe.
 */
/**
 * @param newLibellespe newLibellespe
 */
public void setLibellespe(String newLibellespe) { 
	libellespe = newLibellespe;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new SpecialiteBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected SpecialiteBroker getMySpecialiteBroker() {
	return (SpecialiteBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : Specialite.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Specialite> listerSpecialite(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Specialite unSpecialite = new Specialite();
	return unSpecialite.getMySpecialiteBroker().listerSpecialite(aTransaction);
}
/**
 * Retourne un Specialite.
 * @param aTransaction aTransaction
 * @param code code
 * @return Specialite
 * @throws Exception Exception
 */
public static Specialite chercherSpecialite(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Specialite unSpecialite = new Specialite();
	return unSpecialite.getMySpecialiteBroker().chercherSpecialite(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param param param
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerSpecialite(nc.mairie.technique.Transaction aTransaction,String param )  throws Exception {
	if(!existeSpecialite(aTransaction,param)){
//		on ajoute le code du pneu
		int nouvcode = nouvCodeSpe(aTransaction);
		setCodespecialite(String.valueOf(nouvcode));
//		Creation du Specialite
		return getMySpecialiteBroker().creerSpecialite(aTransaction);
	}else{
		aTransaction.declarerErreur("Cette spécialité est déjà enregistrée.");
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
public boolean modifierSpecialite(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	if(!existeSpecialite(aTransaction,param)){
//		Modification du Specialite
		return getMySpecialiteBroker().modifierSpecialite(aTransaction);
	}else{
		aTransaction.declarerErreur("Cette spécialité est déjà enregistrée.");
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
public boolean supprimerSpecialite(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	 si  utilisé pas de suppression
	AgentsATM unAgentsATM = new AgentsATM();
	if (!unAgentsATM.existeAgentsATMSpe(aTransaction,getCodespecialite())){
//		Suppression de l'Specialite
		return getMySpecialiteBroker().supprimerSpecialite(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type d'entretien est utilisé pour un plan d'entretien personnalisé.La suppression n'est pas possible.");
		return false;
	}
	
	
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @param aTransaction aTransaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeSpecialite(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Specialite unSpecialite = new Specialite();
	return unSpecialite.getMySpecialiteBroker().existeSpecialite(aTransaction, param);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * author : Coralie NICOLAS
 */
public int nouvCodeSpe(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier codepneu
	int nouvcode = getMySpecialiteBroker().nouvCodeSpec(aTransaction);
	//si pas trouvé
	if (nouvcode == -1) {
		//fonctionnellement normal: table vide
		nouvcode = 1;
	} else {
		nouvcode++;
	}
	return nouvcode;
}

}
