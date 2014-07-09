package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier TIntervalle
 */
public class TIntervalle extends BasicMetier {
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
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<TIntervalle> listerTIntervalle(nc.mairie.technique.Transaction aTransaction) throws Exception{
	TIntervalle unTIntervalle = new TIntervalle();
	return unTIntervalle.getMyTIntervalleBroker().listerTIntervalle(aTransaction);
}
/**
 * Retourne un TIntervalle.
 * @param aTransaction aTransaction
 * @param code code
 * @return TIntervalle
 * @throws Exception Exception
 */
public static TIntervalle chercherTIntervalle(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	TIntervalle unTIntervalle = new TIntervalle();
	return unTIntervalle.getMyTIntervalleBroker().chercherTIntervalle(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param param param
 * @return boolean
 * @throws Exception Exception
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
 * @param aTransaction aTransaction
 * @param param param
 * @return boolean
 * @throws Exception Exception
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
 * @param aTransaction aTransaction
 * @return boolean
 * @throws Exception Exception
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
 * Getter de l'attribut designation.
 * @return String
 */
public String getDesignation() {
	return designation;
}
/**
 * Setter de l'attribut designation.
 */
/**
 * @param newDesignation newDesignation
 */
public void setDesignation(String newDesignation) { 
	designation = newDesignation;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new TIntervalleBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected TIntervalleBroker getMyTIntervalleBroker() {
	return (TIntervalleBroker)getMyBasicBroker();
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @param aTransaction aTransaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeTIntervalle(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	TIntervalle unTIntervalle = new TIntervalle();
	return unTIntervalle.getMyTIntervalleBroker().existeTIntervalle(aTransaction, param);
}

}
