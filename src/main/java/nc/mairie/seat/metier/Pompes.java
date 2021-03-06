package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Pompes
 */
public class Pompes extends BasicMetier {
	public String num_pompe;
	public String libelle_pompe;
	public String commentaire_pompe;
/**
 * Constructeur Pompes.
 */
public Pompes() {
	super();
}
/**
 * Getter de l'attribut num_pompe.
 * @return String
 */
public String getNum_pompe() {
	return num_pompe;
}
/**
 * Setter de l'attribut num_pompe.
 */
/**
 * @param newNum_pompe newNum_pompe
 */
public void setNum_pompe(String newNum_pompe) { 
	num_pompe = newNum_pompe;
}
/**
 * Getter de l'attribut libelle_pompe.
 * @return String
 */
public String getLibelle_pompe() {
	return libelle_pompe;
}
/**
 * Setter de l'attribut libelle_pompe.
 */
/**
 * @param newLibelle_pompe newLibelle_pompe
 */
public void setLibelle_pompe(String newLibelle_pompe) { 
	libelle_pompe = newLibelle_pompe;
}
/**
 * Getter de l'attribut commentaire_pompe.
 * @return String
 */
public String getCommentaire_pompe() {
	return commentaire_pompe;
}
/**
 * Setter de l'attribut commentaire_pompe.
 */
/**
 * @param newCommentaire_pompe newCommentaire_pompe
 */
public void setCommentaire_pompe(String newCommentaire_pompe) { 
	commentaire_pompe = newCommentaire_pompe;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PompesBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PompesBroker getMyPompesBroker() {
	return (PompesBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : Pompes.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Pompes> listerPompes(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Pompes unPompes = new Pompes();
	return unPompes.getMyPompesBroker().listerPompes(aTransaction);
}
/**
 * Retourne un Pompes.
 * @param aTransaction aTransaction
 * @param code code
 * @return Pompes
 * @throws Exception Exception
 */
public static Pompes chercherPompes(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Pompes unPompes = new Pompes();
	return unPompes.getMyPompesBroker().chercherPompes(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param libelle libelle
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPompes(nc.mairie.technique.Transaction aTransaction,String libelle)  throws Exception {
	if (!existePompes(aTransaction,libelle)){
		//on ajoute le code de pompe
		int nouvCodePompe = nouvCodePompe(aTransaction);
		setNum_pompe(String.valueOf(nouvCodePompe));
//		Creation du Pompes
		return getMyPompesBroker().creerPompes(aTransaction);
	}else{
		aTransaction.declarerErreur("Cette pompe est déjà enregistrée.");
		return false;
	}
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 * @param aTransaction aTransaction
 * @param libelle libelle
 * @param num_pompe num_pompe
 * @return boolean
 * @throws Exception Exception
 */
public boolean modifierPompes(nc.mairie.technique.Transaction aTransaction,String libelle,String num_pompe) throws Exception {
	if(!existePompesTout(aTransaction,libelle,num_pompe)){
		//Modification du Pompes
		return getMyPompesBroker().modifierPompes(aTransaction);
	}else{
		aTransaction.declarerErreur("Cette pompe est déjà enregistrée.");
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
public boolean supprimerPompes(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	 si la pompe  est déjà utilisée on ne peut pas supprimer
	BPC unBPC = new BPC();
	if (!unBPC.existeBPCPompes(aTransaction,getNum_pompe())){
		Carburant unCarbu = new Carburant();
		if(!unCarbu.existeCarburantPompes(aTransaction,getNum_pompe())){
			//	Suppression de l'Pompes
			return getMyPompesBroker().supprimerPompes(aTransaction);
		}else{
			aTransaction.declarerErreur("Cette pompe est affectée à un carburant. La suppression n'est pas possible.");
			return false;
		}
	}else{
		aTransaction.declarerErreur("Cette pompe est utilisée pour un BPC.La suppression n'est pas possible.");
		return false;
	}
}

public int nouvCodePompe(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	int nouvcodepompe = getMyPompesBroker().nouvCodePompes(aTransaction);
	
	//si pas trouvé
	if (nouvcodepompe == -1) {
		//fonctionnellement normal: table vide
		nouvcodepompe= 1;
	} else {
		nouvcodepompe++;
	}
	
	return nouvcodepompe;
}

/*
 * vérifie que la pompe n'existe pas
 * author nicco81
 *
 */
public boolean existePompes(nc.mairie.technique.Transaction aTransaction,String libelle) throws Exception{
	Pompes unePompe = new Pompes();
	return unePompe.getMyPompesBroker().existePompes(aTransaction,libelle);
}

public boolean existePompesTout(nc.mairie.technique.Transaction aTransaction,String libelle,String commentaire) throws Exception{
	Pompes unePompe = new Pompes();
	return unePompe.getMyPompesBroker().existePompesTout(aTransaction,libelle,commentaire);
}

}
