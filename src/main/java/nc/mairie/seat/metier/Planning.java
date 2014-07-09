package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Planning
 */
public class Planning extends BasicMetier {
	public String numeroinventaire;
	public String numeroimmatriculation;
	public String codetypeent;
	public String designationtypeent;
	public String libelleentretien;
	public String codeentretien;
	public String dateprev;
	public String datereal;
	public String codeot;
	public String duree;
	public String sinistre;
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
 * Retourne un ArrayList d'objet métier : Planning.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Planning> listerPlanning(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Planning unPlanning = new Planning();
	return unPlanning.getMyPlanningBroker().listerPlanning(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @return java.util.ArrayList
 *//*
public static ArrayList<Planning> listerPlanningProp(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Planning unPlanning = new Planning();
	return unPlanning.getMyPlanningBroker().listerPlanningProp(aTransaction);
}
*/
/**
 * Retourne un Planning.
 * @param aTransaction aTransaction
 * @param code code
 * @return Planning
 * @throws Exception Exception
 */
public static Planning chercherPlanning(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Planning unPlanning = new Planning();
	return unPlanning.getMyPlanningBroker().chercherPlanning(aTransaction, code);
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @param aTransaction aTransaction
 * @param dateFinPrev dateFinPrev
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Planning> listerPlanningAFaire(nc.mairie.technique.Transaction aTransaction,String dateFinPrev) throws Exception{
	Planning unPlanning = new Planning();
	return unPlanning.getMyPlanningBroker().listerPlanningAFaire(aTransaction,dateFinPrev);
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @param aTransaction aTransaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Planning> chercherPlanningOt(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	Planning unPlanning = new Planning();
	return unPlanning.getMyPlanningBroker().chercherPlanningOT(aTransaction,numot);
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @param aTransaction aTransaction
 * @param inv inv
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Planning> chercherPlanningEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	Planning unPlanning = new Planning();
	return unPlanning.getMyPlanningBroker().chercherPlanningEquip(aTransaction,inv);
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * on cherche les numéro équiepements
 * @throws Exception Exception
 */
public static ArrayList<Planning> chercherPlanningAFaire(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Planning unPlanning = new Planning();
	return unPlanning.getMyPlanningBroker().chercherPlanningAFaire(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @param aTransaction aTransaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Planning> listerPlanningEnCours(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	Planning unPlanning = new Planning();
	return unPlanning.getMyPlanningBroker().listerPlanningEnCours(aTransaction,numot);
}

/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @param aTransaction aTransaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Planning> listerPlanningEnCoursAvecOTValideDifferentT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	Planning unPlanning = new Planning();
	return unPlanning.getMyPlanningBroker().listerPlanningEnCoursAvecOTValideDifferentT(aTransaction,numot);
}
/**
 * Retourne un ArrayList d'objet métier : Planning.
 * @param aTransaction aTransaction
 * @param date date
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Planning> listerPlanningEnRetard(nc.mairie.technique.Transaction aTransaction,String date) throws Exception{
	Planning unPlanning = new Planning();
	return unPlanning.getMyPlanningBroker().listerPlanningEnRetard(aTransaction,date);
}

	public String codepep;
/**
 * Constructeur Planning.
 */
public Planning() {
	super();
}
/**
 * Getter de l'attribut numeroinventaire.
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
 * Getter de l'attribut codetypeent.
 * @return String
 */
public String getCodetypeent() {
	return codetypeent;
}
/**
 * Setter de l'attribut codetypeent.
 */
/**
 * @param newCodetypeent newCodetypeent
 */
public void setCodetypeent(String newCodetypeent) { 
	codetypeent = newCodetypeent;
}
/**
 * Getter de l'attribut designationtypeent.
 * @return String
 */
public String getDesignationtypeent() {
	return designationtypeent;
}
/**
 * Setter de l'attribut designationtypeent.
 */
/**
 * @param newDesignationtypeent newDesignationtypeent
 */
public void setDesignationtypeent(String newDesignationtypeent) { 
	designationtypeent = newDesignationtypeent;
}
/**
 * Getter de l'attribut libelleentretien.
 * @return String
 */
public String getLibelleentretien() {
	return libelleentretien;
}
/**
 * Setter de l'attribut libelleentretien.
 */
/**
 * @param newLibelleentretien newLibelleentretien
 */
public void setLibelleentretien(String newLibelleentretien) { 
	libelleentretien = newLibelleentretien;
}
/**
 * Getter de l'attribut codeentretien.
 * @return String
 */
public String getCodeentretien() {
	return codeentretien;
}
/**
 * Setter de l'attribut codeentretien.
 * @param newCodeentretien newCodeentretien
 */
public void setCodeentretien(String newCodeentretien) { 
	codeentretien = newCodeentretien;
}
/**
 * Getter de l'attribut sinistre.
 * @return String
 */
public String getSinistre() {
	return sinistre;
}
/**
 * Setter de l'attribut sinistre.
 */
/**
 * @param newSinistre newSinistre
 */
public void setSinistre(String newSinistre) { 
	sinistre = newSinistre;
}
/**
 * Getter de l'attribut duree.
 * @return String
 */
public String getDuree() {
	return duree;
}
/**
 * Setter de l'attribut duree.
 */
/**
 * @param newDuree newDuree
 */
public void setDuree(String newDuree) { 
	duree = newDuree;
}
/**
 * Getter de l'attribut codeot.
 * @return String
 */
public String getCodeot() {
	return codeot;
}
/**
 * Setter de l'attribut codeot.
 */
/**
 * @param newCodeot newCodeot
 */
public void setCodeot(String newCodeot) { 
	codeot = newCodeot;
}
/**
 * Getter de l'attribut codepep.
 * @return String
 */
public String getCodepep() {
	return codepep;
}
/**
 * Setter de l'attribut codepep.
 */
/**
 * @param newCodepep newCodepep
 */
public void setCodepep(String newCodepep) { 
	codepep = newCodepep;
}
/**
 * Getter de l'attribut datereal.
 * @return String
 */
public String getDatereal() {
	return datereal;
}
/**
 * Setter de l'attribut datereal.
 */
/**
 * @param newDatereal newDatereal
 */
public void setDatereal(String newDatereal) { 
	datereal = newDatereal;
}
/**
 * Getter de l'attribut dateprev.
 * @return String
 */
public String getDateprev() {
	return dateprev;
}
/**
 * Setter de l'attribut dateprev.
 */
/**
 * @param newDateprev newDateprev
 */
public void setDateprev(String newDateprev) { 
	dateprev = newDateprev;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new PlanningBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected PlanningBroker getMyPlanningBroker() {
	return (PlanningBroker)getMyBasicBroker();
}
}
