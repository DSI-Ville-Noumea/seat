package nc.mairie.seat.metier;

import nc.mairie.seat.process.Outils;
import nc.mairie.technique.Services;
/**
 * Objet métier Modeles
 */
public class Modeles extends nc.mairie.technique.BasicMetier {
	public String codemodele;
	public String nbpneuavant;
	public String nbpneuarriere;
	public String nbessieux;
	public String capacitereservoir;
	public String codemarque;
	public String designationmodele;
	public String codepneu;
	public String puissance;
	public String codete;
	public String codecompteur;
	public String version;
	public String codecarburant;
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
 * Retourne un ArrayList d'objet métier : Modeles.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerModeles(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().listerModeles(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Modeles.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerModelesMT(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().listerModelesMT(aTransaction);
}
/**
 * Retourne un Modeles.
 * @return Modeles
 */
public static Modeles chercherModeles(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().chercherModeles(aTransaction, code);
}
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */
public boolean creerModeles(nc.mairie.technique.Transaction aTransaction,String designation,String version)  throws Exception {
	//RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
//	on ajoute le code du modèle
	int nouvcodemodele = nouvModeles(aTransaction);
	setCodemodele(String.valueOf(nouvcodemodele));
	int compte = existeModeles(aTransaction,getCodemodele(),designation,version,getCodecarburant());
	if(compte==0){
	
		//Creation du Modeles
		return getMyModelesBroker().creerModeles(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce modèle est déjà enregistré.");
		return false;
	}
}
/*vérifie que les champs sont bien renseignés
 * Date : 01/09/05
 * autheur : NC
 */
public boolean controleChamps(nc.mairie.technique.Transaction aTransaction )  throws Exception {
	if((getCapacitereservoir()!=null)&&(!getCapacitereservoir().equals(""))){
		setCapacitereservoir(Outils.enleveEspace(getCapacitereservoir()));
		if(!Services.estNumerique(getCapacitereservoir())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour la capacité du réservoir.");
			return false;
		}
	}
	if((getNbessieux()!=null)&&(!getNbessieux().equals(""))){
		setNbessieux(Outils.enleveEspace(getNbessieux()));
		if(!Services.estNumerique(getNbessieux())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le nombre d'essieux.");
			return false;
		}
	}
	if((getNbpneuarriere()!=null)&&(!getNbpneuarriere().equals(""))){
		setNbpneuarriere(Outils.enleveEspace(getNbpneuarriere()));
		if(!Services.estNumerique(getNbpneuarriere())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le nombre de pneu arrière.");
			return false;
		}
	}
	if((getNbpneuavant()!=null)&&(!getNbpneuavant().equals(""))){
		setNbpneuavant(Outils.enleveEspace(getNbpneuavant()));
		if(!Services.estNumerique(getNbpneuavant())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour le nombre de pneu avant.");
			return false;
		}
	}
	if((getPuissance()!=null)&&(!getPuissance().equals(""))){
		setPuissance(Outils.enleveEspace(getPuissance()));
		if(!Services.estNumerique(getPuissance())){
			aTransaction.declarerErreur("Vous devez saisir des chiffres pour la puissance.");
			return false;
		}
	}
	return true;
}
/**
 * Methode modifierObjetMetier qui retourne
 * true ou false
 */
public boolean modifierModeles(nc.mairie.technique.Transaction aTransaction,String param,String version) throws Exception {
	//RG : controle des champs
	controleChamps(aTransaction);
	if(aTransaction.isErreur()){
		return false;
	}
	int compte =existeModeles(aTransaction,getCodemodele(),param,version,getCodecarburant()); 
	if (compte==0){
		//Modification du Modeles
		return getMyModelesBroker().modifierModeles(aTransaction);
	}else{
		aTransaction.declarerErreur("Ce modèle est déjà enregistré.");
		return false;
	}
}
/**
 * Methode supprimerObjetMetier qui retourne
 * true ou false
 */
public boolean supprimerModeles(nc.mairie.technique.Transaction aTransaction) throws Exception{
	// si utilisé on ne peut pas supprimer
	Equipement unEquipement = new Equipement();
	if (unEquipement.existeEquipementModele(aTransaction,getCodemodele())){
		aTransaction.declarerErreur("Ce modèle est utilisé par un équipement.La suppression n'est pas possible");
		return false;
	}
	PeBase unPeBase = new PeBase();
	if (unPeBase.existePeBaseModele(aTransaction,getCodemodele())){
		aTransaction.declarerErreur("Ce modèle est utilisé par un Plan d'entretien de base.La suppression n'est pas possible.");
		return false;
	}
//	Suppression de l'Modeles
	return getMyModelesBroker().supprimerModeles(aTransaction);
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvModeles(nc.mairie.technique.Transaction aTransaction) throws Exception{

	//	recherche du dernier codepneu
	int nouvcodeModele = getMyModelesBroker().nouvModeles(aTransaction);
	
	//si pas trouvé
	if (nouvcodeModele == -1) {
		//fonctionnellement normal: table vide
		nouvcodeModele = 1;
	} else {
		nouvcodeModele++;
	}
	
	return nouvcodeModele;
	
}

/**
 * Retourne un ArrayList d'objet métier : Modeles.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerModelesMarque(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().listerModelesMarque(aTransaction, cle);
}

public static java.util.ArrayList listerModelesMarqueMT(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().listerModelesMarque(aTransaction, cle);
}


public static java.util.ArrayList listerModelesLib(nc.mairie.technique.Transaction aTransaction, String modele) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().listerModelesLib(aTransaction, modele);
}


/**
 * Retourne un booléen.
 * Vérifie si existe
 * @return true ou false
 */
public int existeModeles(nc.mairie.technique.Transaction aTransaction,String code, String param, String version,String codecarbu) throws Exception{
	Modeles uneModeles = new Modeles();
	return uneModeles.getMyModelesBroker().existeModeles(aTransaction,code, param, version,codecarbu);
}

/**
 * Constructeur Modeles.
 */
public Modeles() {
	super();
}
/**
 * Getter de l'attribut codemodele.
 */
public String getCodemodele() {
	return codemodele;
}
/**
 * Setter de l'attribut codemodele.
 */
public void setCodemodele(String newCodemodele) { 
	codemodele = newCodemodele;
}
/**
 * Getter de l'attribut nbpneuavant.
 */
public String getNbpneuavant() {
	return nbpneuavant;
}
/**
 * Setter de l'attribut nbpneuavant.
 */
public void setNbpneuavant(String newNbpneuavant) { 
	nbpneuavant = newNbpneuavant;
}
/**
 * Getter de l'attribut nbpneuarriere.
 */
public String getNbpneuarriere() {
	return nbpneuarriere;
}
/**
 * Setter de l'attribut nbpneuarriere.
 */
public void setNbpneuarriere(String newNbpneuarriere) { 
	nbpneuarriere = newNbpneuarriere;
}
/**
 * Getter de l'attribut nbessieux.
 */
public String getNbessieux() {
	return nbessieux;
}
/**
 * Setter de l'attribut nbessieux.
 */
public void setNbessieux(String newNbessieux) { 
	nbessieux = newNbessieux;
}
/**
 * Getter de l'attribut capacitereservoir.
 */
public String getCapacitereservoir() {
	return capacitereservoir;
}
/**
 * Setter de l'attribut capacitereservoir.
 */
public void setCapacitereservoir(String newCapacitereservoir) { 
	capacitereservoir = newCapacitereservoir;
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
 * Getter de l'attribut designationmodele.
 */
public String getDesignationmodele() {
	return designationmodele;
}
/**
 * Setter de l'attribut designationmodele.
 */
public void setDesignationmodele(String newDesignationmodele) { 
	designationmodele = newDesignationmodele;
}
/**
 * Getter de l'attribut codepneu.
 */
public String getCodepneu() {
	return codepneu;
}
/**
 * Setter de l'attribut codepneu.
 */
public void setCodepneu(String newCodepneu) { 
	codepneu = newCodepneu;
}
/**
 * Getter de l'attribut puissance.
 */
public String getPuissance() {
	return puissance;
}
/**
 * Setter de l'attribut puissance.
 */
public void setPuissance(String newPuissance) { 
	puissance = newPuissance;
}
/**
 * Getter de l'attribut codete.
 */
public String getCodete() {
	return codete;
}
/**
 * Setter de l'attribut codete.
 */
public void setCodete(String newCodete) { 
	codete = newCodete;
}
/**
 * Getter de l'attribut version.
 */
public String getVersion() {
	return version;
}
/**
 * Setter de l'attribut version.
 */
public void setVersion(String newVersion) { 
	version = newVersion;
}
/**
 * Getter de l'attribut codecarburant.
 */
public String getCodecarburant() {
	return codecarburant;
}
/**
 * Setter de l'attribut codecarburant.
 */
public void setCodecarburant(String newCodecarburant) { 
	codecarburant = newCodecarburant;
}
/**
 * Getter de l'attribut codecompteur.
 */
public String getCodecompteur() {
	return codecompteur;
}
/**
 * Setter de l'attribut codecompteur.
 */
public void setCodecompteur(String newCodecompteur) { 
	codecompteur = newCodecompteur;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new ModelesBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected ModelesBroker getMyModelesBroker() {
	return (ModelesBroker)getMyBasicBroker();
}

/**
 * Retourne un booléen.
 * Vérifie que le modèle existe avec des paramètres de clé étrangères
 * @return Modeles
 */
public boolean existeModeleTEquip(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().existeModeleTEquip(aTransaction, param);
}
public boolean existeModeleMarque(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().existeModeleMarques(aTransaction, param);
}
public boolean existeModeleCarbu(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().existeModeleCarbu(aTransaction, param);
}
public boolean existeModeleCompteur(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().existeModeleCompteur(aTransaction, param);
}
public boolean existeModelePneu(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	Modeles unModeles = new Modeles();
	return unModeles.getMyModelesBroker().existeModelePneu(aTransaction, param);
}

}
