package nc.mairie.seat.metier;
/**
 * Objet métier TypeEntretien
 */
public class TypeEntretien extends nc.mairie.technique.BasicMetier {
	public String codetypeent;
	public String designationtypeent;
/**
 * Constructeur TypeEntretien.
 */
public TypeEntretien() {
	super();
}
/**
 * Getter de l'attribut codetypeent.
 */
public String getCodetypeent() {
	return codetypeent;
}
/**
 * Setter de l'attribut codetypeent.
 */
public void setCodetypeent(String newCodetypeent) { 
	codetypeent = newCodetypeent;
}
/**
 * Getter de l'attribut designationtypeent.
 */
public String getDesignationtypeent() {
	return designationtypeent;
}
/**
 * Setter de l'attribut designationtypeent.
 */
public void setDesignationtypeent(String newDesignationtypeent) { 
	designationtypeent = newDesignationtypeent;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new TypeEntretienBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected TypeEntretienBroker getMyTypeEntretienBroker() {
	return (TypeEntretienBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : TypeEntretien.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerTypeEntretien(nc.mairie.technique.Transaction aTransaction) throws Exception{
	TypeEntretien unTypeEntretien = new TypeEntretien();
	return unTypeEntretien.getMyTypeEntretienBroker().listerTypeEntretien(aTransaction);
}
/**
 * Retourne un TypeEntretien.
 * @return TypeEntretien
 */
public static TypeEntretien chercherTypeEntretien(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	TypeEntretien unTypeEntretien = new TypeEntretien();
	return unTypeEntretien.getMyTypeEntretienBroker().chercherTypeEntretien(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerTypeEntretien(nc.mairie.technique.Transaction aTransaction,String param)  throws Exception {
	if(!existeTEntretien(aTransaction,param)){
//		on ajoute le code du pneu
		int nouvcodete = nouvCodeTe(aTransaction);
		setCodetypeent(String.valueOf(nouvcodete));
//		Creation du TypeEntretien
		return getMyTypeEntretienBroker().creerTypeEntretien(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type d'entretien est déjà enregistré.");
		return false;
	}
	
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierTypeEntretien(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	if(!existeTEntretien(aTransaction,param)){
		//	Modification du TypeEntretien
		return getMyTypeEntretienBroker().modifierTypeEntretien(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type d'entretien est déjà enregistré.");
		return false;
	}
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerTypeEntretien(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	 si  utilisé pas de suppression
	PePerso unPePerso = new PePerso();
	if (!unPePerso.existePePersoTEnt(aTransaction,getCodetypeent())){
		//Suppression de l'TypeEntretien
		return getMyTypeEntretienBroker().supprimerTypeEntretien(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type d'entretien est utilisé pour un plan d'entretien personnalisé.La suppression n'est pas possible.");
		return false;
	}
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existeTEntretien(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	TypeEntretien unTypeEntretien = new TypeEntretien();
	return unTypeEntretien.getMyTypeEntretienBroker().existeTEntretien(aTransaction, param);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodeTe(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier codepneu
	int nouvcodete = getMyTypeEntretienBroker().nouvCodeTe(aTransaction);
	//si pas trouvé
	if (nouvcodete == -1) {
		//fonctionnellement normal: table vide
		nouvcodete = 1;
	} else {
		nouvcodete++;
	}
	return nouvcodete;
}

}
