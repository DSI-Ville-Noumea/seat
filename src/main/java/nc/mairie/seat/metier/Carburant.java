package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Carburant
 */
public class Carburant extends BasicMetier {
	public String codecarbu;
	public String designationcarbu;
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
 * Retourne un ArrayList d'objet métier : Carburant.
 * @return java.util.ArrayList
 */
public static ArrayList<Carburant> listerCarburant(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Carburant unCarburant = new Carburant();
	return unCarburant.getMyCarburantBroker().listerCarburant(aTransaction);
}
/**
 * Retourne un Carburant.
 * @return Carburant
 */
public static Carburant chercherCarburant(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Carburant unCarburant = new Carburant();
	return unCarburant.getMyCarburantBroker().chercherCarburant(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerCarburant(nc.mairie.technique.Transaction aTransaction,String param)  throws Exception {
	if (!existeCarburant(aTransaction,param)){
	//	on ajoute le code du carburant
		int nouvcodeCarburant = nouvCarburant(aTransaction);
		setCodecarbu(String.valueOf(nouvcodeCarburant));
		//Creation du Carburant
		return getMyCarburantBroker().creerCarburant(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce carburant est déjà enregistré.");
		return false;
	}
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierCarburant(nc.mairie.technique.Transaction aTransaction,String param,String num_pompe) throws Exception {
	if (!existeCarburantTout(aTransaction,param,num_pompe)){
		//	Modification du Carburant
		return getMyCarburantBroker().modifierCarburant(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce carburant est déjà enregistré.");
		return false;
	}
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerCarburant(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	 si déjà utilisé on ne peut pas supprimer
	Modeles unModele = new Modeles();
	if (!unModele.existeModeleCarbu(aTransaction,getCodecarbu())){
		//Suppression de l'Carburant
		return getMyCarburantBroker().supprimerCarburant(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce carburant est utilisé pour un modèle.La suppression n'est pas possible.");
		return false;
	}
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCarburant(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier codeCarburant
	int nouvcodeCarburant = getMyCarburantBroker().nouvCarburant(aTransaction);
	
	//si pas trouvé
	if (nouvcodeCarburant == -1) {
		//fonctionnellement normal: table vide
		nouvcodeCarburant = 1;
	} else {
		nouvcodeCarburant++;
	}
	return nouvcodeCarburant;
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existeCarburant(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Carburant unCarburant = new Carburant();
	return unCarburant.getMyCarburantBroker().existeCarburant(aTransaction, param);
}
/*
 * si doublon par rapport à tous les champs
 */
public boolean existeCarburantTout(nc.mairie.technique.Transaction aTransaction, String param,String num_pompe) throws Exception{
	Carburant unCarburant = new Carburant();
	return unCarburant.getMyCarburantBroker().existeCarburantTout(aTransaction, param,num_pompe);
}

	public String num_pompe_atm;
/**
 * Constructeur Carburant.
 */
public Carburant() {
	super();
}
/**
 * Getter de l'attribut codecarbu.
 */
public String getCodecarbu() {
	return codecarbu;
}
/**
 * Setter de l'attribut codecarbu.
 */
public void setCodecarbu(String newCodecarbu) { 
	codecarbu = newCodecarbu;
}
/**
 * Getter de l'attribut designationcarbu.
 */
public String getDesignationcarbu() {
	return designationcarbu;
}
/**
 * Setter de l'attribut designationcarbu.
 */
public void setDesignationcarbu(String newDesignationcarbu) { 
	designationcarbu = newDesignationcarbu;
}
/**
 * Getter de l'attribut num_pompe_atm.
 */
public String getNum_pompe_atm() {
	return num_pompe_atm;
}
/**
 * Setter de l'attribut num_pompe_atm.
 */
public void setNum_pompe_atm(String newNum_pompe_atm) { 
	num_pompe_atm = newNum_pompe_atm;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new CarburantBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected CarburantBroker getMyCarburantBroker() {
	return (CarburantBroker)getMyBasicBroker();
}

public boolean existeCarburantPompes(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Carburant unCarburant = new Carburant();
	return unCarburant.getMyCarburantBroker().existeCarburantPompes(aTransaction, param);
}

}
