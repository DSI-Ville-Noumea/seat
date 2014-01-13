package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier ModePrise
 */
public class ModePrise extends BasicMetier {
	public String codemodeprise;
	public String designationmodeprise;
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
 * Retourne un ArrayList d'objet métier : ModePrise.
 * @return java.util.ArrayList
 */
public static ArrayList<ModePrise> listerModePrise(nc.mairie.technique.Transaction aTransaction) throws Exception{
	ModePrise unModePrise = new ModePrise();
	return unModePrise.getMyModePriseBroker().listerModePrise(aTransaction);
}
/**
 * Retourne un ModePrise.
 * @return ModePrise
 */
public static ModePrise chercherModePrise(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	ModePrise unModePrise = new ModePrise();
	return unModePrise.getMyModePriseBroker().chercherModePrise(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerModePrise(nc.mairie.technique.Transaction aTransaction,String param)  throws Exception {
	if(!existeModeprise(aTransaction,param)){
		//on ajoute le code 
		int nouvcodemodeprise = nouvModePrise(aTransaction);
		setCodemodeprise(String.valueOf(nouvcodemodeprise));
		//Creation du ModePrise
		return getMyModePriseBroker().creerModePrise(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce mode de prise de carburant est déjà enregistré.");
		return false;
	}
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierModePrise(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	if(!existeModeprise(aTransaction,param)){
//		Modification du ModePrise
		return getMyModePriseBroker().modifierModePrise(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce mode de prise de carburant est déjà enregistré.");
		return false;
	}
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerModePrise(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//si utilisé pas de suppression
	BPC unBPC = new BPC();
	if (!unBPC.existeBPCModePrise(aTransaction,getCodemodeprise())){
		//Suppression de l'ModePrise
		return getMyModePriseBroker().supprimerModePrise(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce mode de prise est utilisé par un BPC. La suppression n'est pas possible.");
		return false;
	}
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvModePrise(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	int nouvcodeModePrise = getMyModePriseBroker().nouvModePrise(aTransaction);
	
	//si pas trouvé
	if (nouvcodeModePrise == -1) {
		//fonctionnellement normal: table vide
		nouvcodeModePrise = 1;
	} else {
		nouvcodeModePrise++;
	}
	
	return nouvcodeModePrise;
}

/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public boolean existeModeprise(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	ModePrise unMp = new ModePrise();
	return unMp.getMyModePriseBroker().existeModePrise(aTransaction, param);
}

/**
 * Constructeur ModePrise.
 */
public ModePrise() {
	super();
}
/**
 * Getter de l'attribut codemodeprise.
 */
public String getCodemodeprise() {
	return codemodeprise;
}
/**
 * Setter de l'attribut codemodeprise.
 */
public void setCodemodeprise(String newCodemodeprise) { 
	codemodeprise = newCodemodeprise;
}
/**
 * Getter de l'attribut designationmodeprise.
 */
public String getDesignationmodeprise() {
	return designationmodeprise;
}
/**
 * Setter de l'attribut designationmodeprise.
 */
public void setDesignationmodeprise(String newDesignationmodeprise) { 
	designationmodeprise = newDesignationmodeprise;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new ModePriseBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected ModePriseBroker getMyModePriseBroker() {
	return (ModePriseBroker)getMyBasicBroker();
}
}
