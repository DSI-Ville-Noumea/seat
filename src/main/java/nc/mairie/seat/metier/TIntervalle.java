package nc.mairie.seat.metier;
/**
 * Objet métier TIntervalle
 */
public class TIntervalle extends nc.mairie.technique.BasicMetier {
	public String codeti;
	public String designation;
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
 * Retourne un ArrayList d'objet métier : TIntervalle.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerTIntervalle(nc.mairie.technique.Transaction aTransaction) throws Exception{
	TIntervalle unTIntervalle = new TIntervalle();
	return unTIntervalle.getMyTIntervalleBroker().listerTIntervalle(aTransaction);
}
/**
 * Retourne un TIntervalle.
 * @return TIntervalle
 */
public static TIntervalle chercherTIntervalle(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	TIntervalle unTIntervalle = new TIntervalle();
	return unTIntervalle.getMyTIntervalleBroker().chercherTIntervalle(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerTIntervalle(nc.mairie.technique.Transaction aTransaction,String param)  throws Exception {
	if(!existeTIntervalle(aTransaction,param)){
		//	on ajoute le code de la marque
		int nouvcodeTi = nouvTintervalle(aTransaction);
		setCodeti(String.valueOf(nouvcodeTi));
		//Creation du TIntervalle
		return getMyTIntervalleBroker().creerTIntervalle(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type d'intervalle est déjà enregistré.");
		return false;
	}
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierTIntervalle(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	if(!existeTIntervalle(aTransaction,param)){
		//Modification du TIntervalle
		return getMyTIntervalleBroker().modifierTIntervalle(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce type d'intervalle est déjà enregistré.");
		return false;
	}
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerTIntervalle(nc.mairie.technique.Transaction aTransaction) throws Exception{
	// si déjà utilisé pas de suppression
	PeBase unPeBase = new PeBase();
	if (unPeBase.existePeBaseTint(aTransaction,getCodeti())){
		aTransaction.declarerErreur("Ce type d'intervalle est utilisé par un plan d'entretien de base.La suppression n'est pas possible");
		return false;
	}
	//Suppression de l'TIntervalle
	return getMyTIntervalleBroker().supprimerTIntervalle(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvTintervalle(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	int nouvcodeTi = getMyTIntervalleBroker().nouvTIntervalle(aTransaction);
	
	//si pas trouvé
	if (nouvcodeTi == -1) {
		//fonctionnellement normal: table vide
		nouvcodeTi = 1;
	} else {
		nouvcodeTi++;
	}
	
	return nouvcodeTi;
	
}

/**
 * Constructeur TIntervalle.
 */
public TIntervalle() {
	super();
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
 * Getter de l'attribut designation.
 */
public String getDesignation() {
	return designation;
}
/**
 * Setter de l'attribut designation.
 */
public void setDesignation(String newDesignation) { 
	designation = newDesignation;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new TIntervalleBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected TIntervalleBroker getMyTIntervalleBroker() {
	return (TIntervalleBroker)getMyBasicBroker();
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existeTIntervalle(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	TIntervalle unTIntervalle = new TIntervalle();
	return unTIntervalle.getMyTIntervalleBroker().existeTIntervalle(aTransaction, param);
}

}
