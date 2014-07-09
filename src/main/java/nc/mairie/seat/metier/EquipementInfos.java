package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier EquipementInfos
 */
public class EquipementInfos extends BasicMetier {
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
 * @param aTransaction aTransaction
 * @param tri tri
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<EquipementInfos> listerEquipementInfos(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().listerEquipementInfos(aTransaction,tri);
}

/**
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @param aTransaction aTransaction
 * @param tri tri
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<EquipementInfos> listerEquipementInfosActifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().listerEquipementInfosActifs(aTransaction,tri);
}

/**
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @param aTransaction aTransaction
 * @param tri tri
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<EquipementInfos> listerEquipementInfosInactifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().listerEquipementInfosInactifs(aTransaction,tri);
}
/**
 * Retourne un EquipementInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return EquipementInfos
 * @throws Exception Exception
 */
public static ArrayList<EquipementInfos> chercherListEquipementInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().chercherListEquipementInfos(aTransaction, code);
}
/**
 * Retourne un EquipementInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return EquipementInfos
 * @throws Exception Exception
 */
public static ArrayList<EquipementInfos> chercherListEquipementInfosTous(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().chercherListEquipementInfosTous(aTransaction, code);
}
/**
 * Retourne un EquipementInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return EquipementInfos
 * @throws Exception Exception
 */
public static EquipementInfos chercherEquipementInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().chercherEquipementInfos(aTransaction, code);
}

/**
 * Retourne un EquipementInfos.
 * @param aTransaction aTransaction
 * @param code code
 * @return EquipementInfos
 * @throws Exception Exception
 */
public static EquipementInfos chercherEquipementInfosInvOuImmat(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	EquipementInfos unEquipementInfos = new EquipementInfos();
	return unEquipementInfos.getMyEquipementInfosBroker().chercherEquipementInfosInvOuImmat(aTransaction, code);
}

/**
 * Retourne un booléen.
 * Vérifie que le bpc existe
 * @param aTransaction aTransaction
 * @param param param
 * @return BPC
 * @throws Exception Exception
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
/**
 * @return String
 */
public String getNuminv() {
	return numinv;
}
/**
 * Setter de l'attribut numinv.
 */
/**
 * @param newNuminv newNuminv
 */
public void setNuminv(String newNuminv) { 
	numinv = newNuminv;
}
/**
 * Getter de l'attribut numimmat.
 */
/**
 * @return String
 */
public String getNumimmat() {
	return numimmat;
}
/**
 * Setter de l'attribut numimmat.
 */
/**
 * @param newNumimmat newNumimmat
 */
public void setNumimmat(String newNumimmat) { 
	numimmat = newNumimmat;
}
/**
 * Getter de l'attribut dmes.
 */
/**
 * @return String
 */
public String getDmes() {
	return dmes;
}
/**
 * Setter de l'attribut dmes.
 */
/**
 * @param newDmes newDmes
 */
public void setDmes(String newDmes) { 
	dmes = newDmes;
}
/**
 * Getter de l'attribut dventereforme.
 */
/**
 * @return String
 */
public String getDventereforme() {
	return dventereforme;
}
/**
 * Setter de l'attribut dventereforme.
 */
/**
 * @param newDventereforme newDventereforme
 */
public void setDventereforme(String newDventereforme) { 
	dventereforme = newDventereforme;
}
/**
 * Getter de l'attribut dhorscircuit.
 */
/**
 * @return String
 */
public String getDhorscircuit() {
	return dhorscircuit;
}
/**
 * Setter de l'attribut dhorscircuit.
 */
/**
 * @param newDhorscircuit newDhorscircuit
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
/**
 * @return String
 */
public String getNumeroinventaire() {
	return numeroinventaire;
}
/**
 * Setter de l'attribut numeroinventaire.
 */
/**
 * @param newNumeroinventaire newNumeroinventaire
 */
public void setNumeroinventaire(String newNumeroinventaire) { 
	numeroinventaire = newNumeroinventaire;
}
/**
 * Getter de l'attribut numeroimmatriculation.
 */
/**
 * @return String
 */
public String getNumeroimmatriculation() {
	return numeroimmatriculation;
}
/**
 * Setter de l'attribut numeroimmatriculation.
 */
/**
 * @param newNumeroimmatriculation newNumeroimmatriculation
 */
public void setNumeroimmatriculation(String newNumeroimmatriculation) { 
	numeroimmatriculation = newNumeroimmatriculation;
}
/**
 * Getter de l'attribut datemiseencirculation.
 */
/**
 * @return String
 */
public String getDatemiseencirculation() {
	return datemiseencirculation;
}
/**
 * Setter de l'attribut datemiseencirculation.
 */
/**
 * @param newDatemiseencirculation newDatemiseencirculation
 */
public void setDatemiseencirculation(String newDatemiseencirculation) { 
	datemiseencirculation = newDatemiseencirculation;
}
/**
 * Getter de l'attribut datehorscircuit.
 */
/**
 * @return String
 */
public String getDatehorscircuit() {
	return datehorscircuit;
}
/**
 * Setter de l'attribut datehorscircuit.
 */
/**
 * @param newDatehorscircuit newDatehorscircuit
 */
public void setDatehorscircuit(String newDatehorscircuit) { 
	datehorscircuit = newDatehorscircuit;
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
 * Getter de l'attribut dateventeoureforme.
 */
/**
 * @return String
 */
public String getDateventeoureforme() {
	return dateventeoureforme;
}
/**
 * Setter de l'attribut dateventeoureforme.
 */
/**
 * @param newDateventeoureforme newDateventeoureforme
 */
public void setDateventeoureforme(String newDateventeoureforme) { 
	dateventeoureforme = newDateventeoureforme;
}
/**
 * Getter de l'attribut typete.
 */
/**
 * @return String
 */
public String getTypete() {
	return typete;
}
/**
 * Setter de l'attribut typete.
 */
/**
 * @param newTypete newTypete
 */
public void setTypete(String newTypete) { 
	typete = newTypete;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new EquipementInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected EquipementInfosBroker getMyEquipementInfosBroker() {
	return (EquipementInfosBroker)getMyBasicBroker();
}
}
