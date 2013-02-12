package nc.mairie.seat.metier;
/**
 * Objet métier EquipementInfos
 */
public class EquipementInfos extends nc.mairie.technique.BasicMetier {
	public String numeroinventaire;
	public String numeroimmatriculation;
	public String datemiseencirculation;
	public String designationmodele;
	public String designationmarque;
	public String designationtypeequip;
	public String datehorscircuit;
	public String version;
	public String designationcarbu;
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
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerEquipementInfos(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().listerEquipementInfos(aTransaction,tri);
}

/**
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerEquipementInfosActifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().listerEquipementInfosActifs(aTransaction,tri);
}

/**
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerEquipementInfosInactifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().listerEquipementInfosInactifs(aTransaction,tri);
}
/**
 * Retourne un EquipementInfos.
 * @return EquipementInfos
 */
public static java.util.ArrayList chercherListEquipementInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().chercherListEquipementInfos(aTransaction, code);
}
/**
 * Retourne un EquipementInfos.
 * @return EquipementInfos
 */
public static java.util.ArrayList chercherListEquipementInfosTous(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().chercherListEquipementInfosTous(aTransaction, code);
}
/**
 * Retourne un EquipementInfos.
 * @return EquipementInfos
 */
public static EquipementInfos chercherEquipementInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().chercherEquipementInfos(aTransaction, code);
}

/**
 * Retourne un EquipementInfos.
 * @return EquipementInfos
 */
public static EquipementInfos chercherEquipementInfosInvOuImmat(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().chercherEquipementInfosInvOuImmat(aTransaction, code);
}

/**
 * Retourne un booléen.
 * Vérifie que le bpc existe
 * @return BPC
 */
public boolean existeEquipementInfosEquip(nc.mairie.technique.Transaction aTransaction, String param) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().existeEquipementInfosEquip(aTransaction, param);
}

	public String dimension;
	public String dateventeoureforme;
	public String typete;
	public String numinv;
	public String numimmat;
	public String dmes;
	public String dventereforme;
	public String dhorscircuit;
/**
 * Getter de l'attribut numinv.
 */
public String getNuminv() {
	return numinv;
}
/**
 * Setter de l'attribut numinv.
 */
public void setNuminv(String newNuminv) { 
	numinv = newNuminv;
}
/**
 * Getter de l'attribut numimmat.
 */
public String getNumimmat() {
	return numimmat;
}
/**
 * Setter de l'attribut numimmat.
 */
public void setNumimmat(String newNumimmat) { 
	numimmat = newNumimmat;
}
/**
 * Getter de l'attribut dmes.
 */
public String getDmes() {
	return dmes;
}
/**
 * Setter de l'attribut dmes.
 */
public void setDmes(String newDmes) { 
	dmes = newDmes;
}
/**
 * Getter de l'attribut dventereforme.
 */
public String getDventereforme() {
	return dventereforme;
}
/**
 * Setter de l'attribut dventereforme.
 */
public void setDventereforme(String newDventereforme) { 
	dventereforme = newDventereforme;
}
/**
 * Getter de l'attribut dhorscircuit.
 */
public String getDhorscircuit() {
	return dhorscircuit;
}
/**
 * Setter de l'attribut dhorscircuit.
 */
public void setDhorscircuit(String newDhorscircuit) { 
	dhorscircuit = newDhorscircuit;
}
/**
 * Constructeur EquipementInfos.
 */
public EquipementInfos() {
	super();
}
/**
 * Getter de l'attribut numeroinventaire.
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
}
/**
 * Getter de l'attribut numeroimmatriculation.
 */
public String getNumeroimmatriculation() {
	return numeroimmatriculation;
}
/**
 * Setter de l'attribut numeroimmatriculation.
 */
public void setNumeroimmatriculation(String newNumeroimmatriculation) { 
	numeroimmatriculation = newNumeroimmatriculation;
}
/**
 * Getter de l'attribut datemiseencirculation.
 */
public String getDatemiseencirculation() {
	return datemiseencirculation;
}
/**
 * Setter de l'attribut datemiseencirculation.
 */
public void setDatemiseencirculation(String newDatemiseencirculation) { 
	datemiseencirculation = newDatemiseencirculation;
}
/**
 * Getter de l'attribut datehorscircuit.
 */
public String getDatehorscircuit() {
	return datehorscircuit;
}
/**
 * Setter de l'attribut datehorscircuit.
 */
public void setDatehorscircuit(String newDatehorscircuit) { 
	datehorscircuit = newDatehorscircuit;
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
 * Getter de l'attribut dateventeoureforme.
 */
public String getDateventeoureforme() {
	return dateventeoureforme;
}
/**
 * Setter de l'attribut dateventeoureforme.
 */
public void setDateventeoureforme(String newDateventeoureforme) { 
	dateventeoureforme = newDateventeoureforme;
}
/**
 * Getter de l'attribut typete.
 */
public String getTypete() {
	return typete;
}
/**
 * Setter de l'attribut typete.
 */
public void setTypete(String newTypete) { 
	typete = newTypete;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new EquipementInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected EquipementInfosBroker getMyEquipementInfosBroker() {
	return (EquipementInfosBroker)getMyBasicBroker();
}
}
