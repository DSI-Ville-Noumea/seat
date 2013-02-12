package nc.mairie.seat.metier;
/**
 * Objet métier Marques
 */
public class Marques extends nc.mairie.technique.BasicMetier {
	public String codemarque;
	public String designationmarque;
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
 * Retourne un ArrayList d'objet métier : Marques.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerMarques(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Marques unMarques = new Marques();
	return unMarques.getMyMarquesBroker().listerMarques(aTransaction);
}
/**
 * Retourne un Marques.
 * @return Marques
 */
public static Marques chercherMarques(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Marques unMarques = new Marques();
	return unMarques.getMyMarquesBroker().chercherMarques(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerMarques(nc.mairie.technique.Transaction aTransaction,String param)  throws Exception {
	if(!existeMarques(aTransaction,param)){
	//	on ajoute le code de la marque
		int nouvcodemarque = nouvMarques(aTransaction);
		setCodemarque(String.valueOf(nouvcodemarque));
		//Creation du Marques
		return getMyMarquesBroker().creerMarques(aTransaction);
	}else{
		aTransaction.declarerErreur("Cette marque est déjà enregistrée.");
		return false;
	}
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierMarques(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	if (!existeMarques(aTransaction,param)){
		//Modification du Marques
		return getMyMarquesBroker().modifierMarques(aTransaction);
	}else{
		aTransaction.declarerErreur("Cette marque est déjà enregistrée.");
		return false;
	}
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerMarques(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	 si la marque est déjà utilisé on ne peut pas supprimer
	Modeles unModele = new Modeles();
	if (!unModele.existeModeleMarque(aTransaction,getCodemarque())){
		//Suppression de l'Marques
		return getMyMarquesBroker().supprimerMarques(aTransaction);
	}else{
		aTransaction.declarerErreur("Cette marque est utilisée pour un modèle.La suppression n'est pas possible.");
		return false;
	}
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvMarques(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	int nouvcodeMarque = getMyMarquesBroker().nouvMarques(aTransaction);
	
	//si pas trouvé
	if (nouvcodeMarque == -1) {
		//fonctionnellement normal: table vide
		nouvcodeMarque = 1;
	} else {
		nouvcodeMarque++;
	}
	
	return nouvcodeMarque;
	
}

/**
 * Retourne un ArrayList d'objet métier : Marques.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerMarquesModele(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Marques unMarques = new Marques();
	return unMarques.getMyMarquesBroker().listerMarquesModele(aTransaction);
}

public static java.util.ArrayList listerMarquesModeleMT(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Marques unMarques = new Marques();
	return unMarques.getMyMarquesBroker().listerMarquesModeleMT(aTransaction);
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existeMarques(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Marques unMarques = new Marques();
	return unMarques.getMyMarquesBroker().existeMarques(aTransaction, param);
}

/**
 * Constructeur Marques.
 */
public Marques() {
	super();
}
/**
 * Getter de l'attribut codemarque.
 */
public String getCodemarque() {
	return codemarque;
}
/**
 * Setter de l'attribut codemarque.
 */
public void setCodemarque(String newCodemarque) { 
	codemarque = newCodemarque;
}
/**
 * Getter de l'attribut designationmarque.
 */
public String getDesignationmarque() {
	return designationmarque;
}
/**
 * Setter de l'attribut designationmarque.
 */
public void setDesignationmarque(String newDesignationmarque) { 
	designationmarque = newDesignationmarque;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new MarquesBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected MarquesBroker getMyMarquesBroker() {
	return (MarquesBroker)getMyBasicBroker();
}
}
