package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier ModeleInfos
 */
public class ModeleInfos extends BasicMetier {
	public String designationmodele;
	public String designationmarque;
	public String designationtypeequip;
	public String codemodele;
	public String nbpneuavant;
	public String nbpneuarriere;
	public String nbessieux;
	public String capacitereservoir;
	public String puissance;
	public String version;
	public String designationcarbu;
	public String dimension;
	public String designationcompteur;
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
 * Retourne un ArrayList d'objet métier : ModeleInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<ModeleInfos> listerModeleInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	ModeleInfos unModeleInfos = new ModeleInfos();
	return unModeleInfos.getMyModeleInfosBroker().listerModeleInfos(aTransaction);
}
/**
 * Retourne un ModeleInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return ModeleInfos
 * @throws Exception Exception
 */
public static ModeleInfos chercherModeleInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	ModeleInfos unModeleInfos = new ModeleInfos();
	return unModeleInfos.getMyModeleInfosBroker().chercherModeleInfos(aTransaction, code);
}

/**
 * Retourne un ModelesInfos.
 * @param aTransaction aTransaction
 * @param param param
 * @return ModelesInfos
 * @throws Exception Exception
 */
public static ArrayList<ModeleInfos> chercherListModeleInfosTous(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	ModeleInfos unModeleInfos = new ModeleInfos();
	return unModeleInfos.getMyModeleInfosBroker().chercherListModeleInfosTous(aTransaction, param);
}

	public String codemarque;
	public String codepneu;
	public String codete;
	public String codecarburant;
	public String codecompteur;
/**
 * Getter de l'attribut codemarque.
 */
/**
 * @return String
 */
public String getCodemarque() {
	return codemarque;
}
/**
 * Setter de l'attribut codemarque.
 */
/**
 * @param newCodemarque newCodemarque
 */
public void setCodemarque(String newCodemarque) { 
	codemarque = newCodemarque;
}
/**
 * Getter de l'attribut codepneu.
 */
/**
 * @return String
 */
public String getCodepneu() {
	return codepneu;
}
/**
 * Setter de l'attribut codepneu.
 */
/**
 * @param newCodepneu newCodepneu
 */
public void setCodepneu(String newCodepneu) { 
	codepneu = newCodepneu;
}
/**
 * Getter de l'attribut codete.
 */
/**
 * @return String
 */
public String getCodete() {
	return codete;
}
/**
 * Setter de l'attribut codete.
 */
/**
 * @param newCodete newCodete
 */
public void setCodete(String newCodete) { 
	codete = newCodete;
}
/**
 * Getter de l'attribut codecarburant.
 */
/**
 * @return String
 */
public String getCodecarburant() {
	return codecarburant;
}
/**
 * Setter de l'attribut codecarburant.
 */
/**
 * @param newCodecarburant newCodecarburant
 */
public void setCodecarburant(String newCodecarburant) { 
	codecarburant = newCodecarburant;
}
/**
 * Getter de l'attribut codecompteur.
 */
/**
 * @return String
 */
public String getCodecompteur() {
	return codecompteur;
}
/**
 * Setter de l'attribut codecompteur.
 */
/**
 * @param newCodecompteur newCodecompteur
 */
public void setCodecompteur(String newCodecompteur) { 
	codecompteur = newCodecompteur;
}
/**
 * Constructeur ModeleInfos.
 */
public ModeleInfos() {
	super();
}
/**
 * Getter de l'attribut codemodele.
 */
/**
 * @return String
 */
public String getCodemodele() {
	return codemodele;
}
/**
 * Setter de l'attribut codemodele.
 */
/**
 * @param newCodemodele newCodemodele
 */
public void setCodemodele(String newCodemodele) { 
	codemodele = newCodemodele;
}
/**
 * Getter de l'attribut designationmodele.
 */
/**
 * @return String
 */
public String getDesignationmodele() {
	return designationmodele;
}
/**
 * Setter de l'attribut designationmodele.
 */
/**
 * @param newDesignationmodele newDesignationmodele
 */
public void setDesignationmodele(String newDesignationmodele) { 
	designationmodele = newDesignationmodele;
}
/**
 * Getter de l'attribut designationmarque.
 */
/**
 * @return String
 */
public String getDesignationmarque() {
	return designationmarque;
}
/**
 * Setter de l'attribut designationmarque.
 */
/**
 * @param newDesignationmarque newDesignationmarque
 */
public void setDesignationmarque(String newDesignationmarque) { 
	designationmarque = newDesignationmarque;
}
/**
 * Getter de l'attribut designationcarbu.
 */
/**
 * @return String
 */
public String getDesignationcarbu() {
	return designationcarbu;
}
/**
 * Setter de l'attribut designationcarbu.
 */
/**
 * @param newDesignationcarbu newDesignationcarbu
 */
public void setDesignationcarbu(String newDesignationcarbu) { 
	designationcarbu = newDesignationcarbu;
}
/**
 * Getter de l'attribut dimension.
 */
/**
 * @return String
 */
public String getDimension() {
	return dimension;
}
/**
 * Setter de l'attribut dimension.
 */
/**
 * @param newDimension newDimension
 */
public void setDimension(String newDimension) { 
	dimension = newDimension;
}
/**
 * Getter de l'attribut designationtypeequip.
 */
/**
 * @return String
 */
public String getDesignationtypeequip() {
	return designationtypeequip;
}
/**
 * Setter de l'attribut designationtypeequip.
 */
/**
 * @param newDesignationtypeequip newDesignationtypeequip
 */
public void setDesignationtypeequip(String newDesignationtypeequip) { 
	designationtypeequip = newDesignationtypeequip;
}
/**
 * Getter de l'attribut designationcompteur.
 */
/**
 * @return String
 */
public String getDesignationcompteur() {
	return designationcompteur;
}
/**
 * Setter de l'attribut designationcompteur.
 */
/**
 * @param newDesignationcompteur newDesignationcompteur
 */
public void setDesignationcompteur(String newDesignationcompteur) { 
	designationcompteur = newDesignationcompteur;
}
/**
 * Getter de l'attribut nbpneuarriere.
 */
/**
 * @return String
 */
public String getNbpneuarriere() {
	return nbpneuarriere;
}
/**
 * Setter de l'attribut nbpneuarriere.
 */
/**
 * @param newNbpneuarriere newNbpneuarriere
 */
public void setNbpneuarriere(String newNbpneuarriere) { 
	nbpneuarriere = newNbpneuarriere;
}
/**
 * Getter de l'attribut nbessieux.
 */
/**
 * @return String
 */
public String getNbessieux() {
	return nbessieux;
}
/**
 * Setter de l'attribut nbessieux.
 */
/**
 * @param newNbessieux newNbessieux
 */
public void setNbessieux(String newNbessieux) { 
	nbessieux = newNbessieux;
}
/**
 * Getter de l'attribut capacitereservoir.
 */
/**
 * @return String
 */
public String getCapacitereservoir() {
	return capacitereservoir;
}
/**
 * Setter de l'attribut capacitereservoir.
 * @param newCapacitereservoir newCapacitereservoir
 */
public void setCapacitereservoir(String newCapacitereservoir) { 
	capacitereservoir = newCapacitereservoir;
}
/**
 * Getter de l'attribut nbpneuavant.
 */
/**
 * @return String
 */
public String getNbpneuavant() {
	return nbpneuavant;
}
/**
 * Setter de l'attribut nbpneuavant.
 */
/**
 * @param newNbpneuavant newNbpneuavant
 */
public void setNbpneuavant(String newNbpneuavant) { 
	nbpneuavant = newNbpneuavant;
}
/**
 * Getter de l'attribut puissance.
 */
/**
 * @return String
 */
public String getPuissance() {
	return puissance;
}
/**
 * Setter de l'attribut puissance.
 */
/**
 * @param newPuissance newPuissance
 */
public void setPuissance(String newPuissance) { 
	puissance = newPuissance;
}
/**
 * Getter de l'attribut version.
 */
/**
 * @return String
 */
public String getVersion() {
	return version;
}
/**
 * Setter de l'attribut version.
 */
/**
 * @param newVersion newVersion
 */
public void setVersion(String newVersion) { 
	version = newVersion;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new ModeleInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected ModeleInfosBroker getMyModeleInfosBroker() {
	return (ModeleInfosBroker)getMyBasicBroker();
}

/**
 * Retourne un booléen.
 * Vérifie que le bpc existe
 * @param aTransaction aTransaction
 * @param param param
 * @return BPC
 * @throws Exception Exception
 */
public boolean existeModeleInfos(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	ModeleInfos unModeleInfos = new ModeleInfos();
	return unModeleInfos.getMyModeleInfosBroker().existeModeleInfos(aTransaction, param);
}

}
