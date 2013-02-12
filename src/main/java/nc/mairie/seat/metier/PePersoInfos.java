package nc.mairie.seat.metier;
/**
 * Objet métier PePersoInfos
 */
public class PePersoInfos extends nc.mairie.technique.BasicMetier {
	public String codeot;
	public String codeequip;
	public String sinistre;
	public String duree;
	public String commentairete;
	public String intervalle;
	public String codeentretien;
	public String libelleentretien;
	public String codetypeent;
	public String designationtypeent;
	public String codeti;
	public String designationti;
	public String dateprev;
	public String datereal;
	public String codepep;

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
 * Retourne un ArrayList d'objet métier : PePersoInfos.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerPePersoInfosEquip(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	if (tri.equals("")){
		tri = "codepep";
	}
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().listerPePersoInfosEquip(aTransaction,inv,tri);
}
/**
 * Retourne un PePersoInfos.
 * @return PePersoInfos
 */
public static PePersoInfos chercherPePersoInfos(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().chercherPePersoInfos(aTransaction, code);
}
	
/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */

public static java.util.ArrayList listerPePersoInfosFait(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().listerPePersoInfosFait(aTransaction,inv,tri);
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 * on retourne la liste des Ot d'un équipement
 */

public static java.util.ArrayList listerPePersoInfosOTEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception{
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().listerPePersoInfosOTEquip(aTransaction,inv);
}

/**
 * Retourne un ArrayList d'objet métier : PePerso.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList chercherPePersoInfosOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception{
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().chercherPePersoInfosOT(aTransaction,numot);
}

/**
 * Methode creerObjetMetier qui retourne
 * true ou false
 */

public static java.util.ArrayList listerPePersoInfosAFaire(nc.mairie.technique.Transaction aTransaction,String inv,String tri) throws Exception{
	PePersoInfos unPePersoInfos = new PePersoInfos();
	return unPePersoInfos.getMyPePersoInfosBroker().listerPePersoInfosAFaire(aTransaction,inv,tri);
}
	
/**
 * Constructeur PePersoInfos.
 */
public PePersoInfos() {
	super();
}
/**
 * Getter de l'attribut sinistre.
 */
public String getSinistre() {
	return sinistre;
}
/**
 * Setter de l'attribut sinistre.
 */
public void setSinistre(String newSinistre) { 
	sinistre = newSinistre;
}
/**
 * Getter de l'attribut duree.
 */
public String getDuree() {
	return duree;
}
/**
 * Setter de l'attribut duree.
 */
public void setDuree(String newDuree) { 
	duree = newDuree;
}
/**
 * Getter de l'attribut codeot.
 */
public String getCodeot() {
	return codeot;
}
/**
 * Setter de l'attribut codeot.
 */
public void setCodeot(String newCodeot) { 
	codeot = newCodeot;
}
/**
 * Getter de l'attribut commentairete.
 */
public String getCommentairete() {
	return commentairete;
}
/**
 * Setter de l'attribut commentairete.
 */
public void setCommentairete(String newCommentairete) { 
	commentairete = newCommentairete;
}
/**
 * Getter de l'attribut codepep.
 */
public String getCodepep() {
	return codepep;
}
/**
 * Setter de l'attribut codepep.
 */
public void setCodepep(String newCodepep) { 
	codepep = newCodepep;
}
/**
 * Getter de l'attribut intervalle.
 */
public String getIntervalle() {
	return intervalle;
}
/**
 * Setter de l'attribut intervalle.
 */
public void setIntervalle(String newIntervalle) { 
	intervalle = newIntervalle;
}
/**
 * Getter de l'attribut datereal.
 */
public String getDatereal() {
	return datereal;
}
/**
 * Setter de l'attribut datereal.
 */
public void setDatereal(String newDatereal) { 
	datereal = newDatereal;
}
/**
 * Getter de l'attribut codeequip.
 */
public String getCodeequip() {
	return codeequip;
}
/**
 * Setter de l'attribut codeequip.
 */
public void setCodeequip(String newCodeequip) { 
	codeequip = newCodeequip;
}
/**
 * Getter de l'attribut codeentretien.
 */
public String getCodeentretien() {
	return codeentretien;
}
/**
 * Setter de l'attribut codeentretien.
 */
public void setCodeentretien(String newCodeentretien) { 
	codeentretien = newCodeentretien;
}
/**
 * Getter de l'attribut libelleentretien.
 */
public String getLibelleentretien() {
	return libelleentretien;
}
/**
 * Setter de l'attribut libelleentretien.
 */
public void setLibelleentretien(String newLibelleentretien) { 
	libelleentretien = newLibelleentretien;
}
/**
 * Getter de l'attribut codetypeent.
 */
public String getCodetypeent() {
	return codetypeent;
}
/**
 * Setter de l'attribut codetypeent.
 */
public void setCodetypeent(String newCodetypeent) { 
	codetypeent = newCodetypeent;
}
/**
 * Getter de l'attribut designationtypeent.
 */
public String getDesignationtypeent() {
	return designationtypeent;
}
/**
 * Setter de l'attribut designationtypeent.
 */
public void setDesignationtypeent(String newDesignationtypeent) { 
	designationtypeent = newDesignationtypeent;
}
/**
 * Getter de l'attribut codeti.
 */
public String getCodeti() {
	return codeti;
}
/**
 * Setter de l'attribut codeti.
 */
public void setCodeti(String newCodeti) { 
	codeti = newCodeti;
}
/**
 * Getter de l'attribut designationti.
 */
public String getDesignationti() {
	return designationti;
}
/**
 * Setter de l'attribut designationti.
 */
public void setDesignationti(String newDesignationti) { 
	designationti = newDesignationti;
}
/**
 * Getter de l'attribut dateprev.
 */
public String getDateprev() {
	return dateprev;
}
/**
 * Setter de l'attribut dateprev.
 */
public void setDateprev(String newDateprev) { 
	dateprev = newDateprev;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new PePersoInfosBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected PePersoInfosBroker getMyPePersoInfosBroker() {
	return (PePersoInfosBroker)getMyBasicBroker();
}
}
