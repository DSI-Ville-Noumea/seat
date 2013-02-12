package nc.mairie.seat.metier;
/**
 * Objet métier ModeleInfos
 */
public class ModeleInfos extends nc.mairie.technique.BasicMetier {
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
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerModeleInfos(nc.mairie.technique.Transaction aTransaction) throws Exception{
	ModeleInfos unModeleInfos = new ModeleInfos();
	return unModeleInfos.getMyModeleInfosBroker().listerModeleInfos(aTransaction);
}
/**
 * Retourne un ModeleInfos.
 * @return ModeleInfos
 */
public static ModeleInfos chercherModeleInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	ModeleInfos unModeleInfos = new ModeleInfos();
	return unModeleInfos.getMyModeleInfosBroker().chercherModeleInfos(aTransaction, code);
}

/**
 * Retourne un ModelesInfos.
 * @return ModelesInfos
 */
public static java.util.ArrayList chercherListModeleInfosTous(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
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
 * Constructeur ModeleInfos.
 */
public ModeleInfos() {
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
 * Getter de l'attribut dimension.
 */
public String getDimension() {
	return dimension;
}
/**
 * Setter de l'attribut dimension.
 */
public void setDimension(String newDimension) { 
	dimension = newDimension;
}
/**
 * Getter de l'attribut designationtypeequip.
 */
public String getDesignationtypeequip() {
	return designationtypeequip;
}
/**
 * Setter de l'attribut designationtypeequip.
 */
public void setDesignationtypeequip(String newDesignationtypeequip) { 
	designationtypeequip = newDesignationtypeequip;
}
/**
 * Getter de l'attribut designationcompteur.
 */
public String getDesignationcompteur() {
	return designationcompteur;
}
/**
 * Setter de l'attribut designationcompteur.
 */
public void setDesignationcompteur(String newDesignationcompteur) { 
	designationcompteur = newDesignationcompteur;
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
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new ModeleInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected ModeleInfosBroker getMyModeleInfosBroker() {
	return (ModeleInfosBroker)getMyBasicBroker();
}

/**
 * Retourne un booléen.
 * Vérifie que le bpc existe
 * @return BPC
 */
public boolean existeModeleInfos(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	ModeleInfos unModeleInfos = new ModeleInfos();
	return unModeleInfos.getMyModeleInfosBroker().existeModeleInfos(aTransaction, param);
}

}
