package nc.mairie.seat.metier;
import java.util.ArrayList;

import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Fournisseurs
 */
public class Fournisseurs extends BasicMetier {
	public String idetbs;
	public String enscom;
	public String ridet;
	public String siret;
	public String cdtyad;
	public String notier;
	public String idadre;
	public String cdtyed;
	public String cdctlc;
	public String cdetac;
	public String cdnajd;
/**
 * Constructeur Fournisseurs.
 */
public Fournisseurs() {
	super();
}
/**
 * Getter de l'attribut idetbs.
 */
/**
 * @return String
 */
public String getIdetbs() {
	return idetbs;
}
/**
 * Setter de l'attribut idetbs.
 */
/**
 * @param newIdetbs newIdetbs
 */
public void setIdetbs(String newIdetbs) { 
	idetbs = newIdetbs;
}
/**
 * Getter de l'attribut enscom.
 */
/**
 * @return String
 */
public String getEnscom() {
	return enscom;
}
/**
 * Setter de l'attribut enscom.
 */
/**
 * @param newEnscom newEnscom
 */
public void setEnscom(String newEnscom) { 
	enscom = newEnscom;
}
/**
 * Getter de l'attribut ridet.
 */
/**
 * @return String
 */
public String getRidet() {
	return ridet;
}
/**
 * Setter de l'attribut ridet.
 */
/**
 * @param newRidet newRidet
 */
public void setRidet(String newRidet) { 
	ridet = newRidet;
}
/**
 * Getter de l'attribut siret.
 */
/**
 * @return String
 */
public String getSiret() {
	return siret;
}
/**
 * Setter de l'attribut siret.
 */
/**
 * @param newSiret newSiret
 */
public void setSiret(String newSiret) { 
	siret = newSiret;
}
/**
 * Getter de l'attribut cdtyad.
 */
/**
 * @return String
 */
public String getCdtyad() {
	return cdtyad;
}
/**
 * Setter de l'attribut cdtyad.
 */
/**
 * @param newCdtyad newCdtyad
 */
public void setCdtyad(String newCdtyad) { 
	cdtyad = newCdtyad;
}
/**
 * Getter de l'attribut notier.
 */
/**
 * @return String
 */
public String getNotier() {
	return notier;
}
/**
 * Setter de l'attribut notier.
 */
/**
 * @param newNotier newNotier
 */
public void setNotier(String newNotier) { 
	notier = newNotier;
}
/**
 * Getter de l'attribut idadre.
 */
/**
 * @return String
 */
public String getIdadre() {
	return idadre;
}
/**
 * Setter de l'attribut idadre.
 */
/**
 * @param newIdadre newIdadre
 */
public void setIdadre(String newIdadre) { 
	idadre = newIdadre;
}
/**
 * Getter de l'attribut cdtyed.
 */
/**
 * @return String
 */
public String getCdtyed() {
	return cdtyed;
}
/**
 * Setter de l'attribut cdtyed.
 */
/**
 * @param newCdtyed newCdtyed
 */
public void setCdtyed(String newCdtyed) { 
	cdtyed = newCdtyed;
}
/**
 * Getter de l'attribut cdctlc.
 */
/**
 * @return String
 */
public String getCdctlc() {
	return cdctlc;
}
/**
 * Setter de l'attribut cdctlc.
 */
/**
 * @param newCdctlc newCdctlc
 */
public void setCdctlc(String newCdctlc) { 
	cdctlc = newCdctlc;
}
/**
 * Getter de l'attribut cdetac.
 */
/**
 * @return String
 */
public String getCdetac() {
	return cdetac;
}
/**
 * Setter de l'attribut cdetac.
 */
/**
 * @param newCdetac newCdetac
 */
public void setCdetac(String newCdetac) { 
	cdetac = newCdetac;
}
/**
 * Getter de l'attribut cdnajd.
 */
/**
 * @return String
 */
public String getCdnajd() {
	return cdnajd;
}
/**
 * Setter de l'attribut cdnajd.
 */
/**
 * @param newCdnajd newCdnajd
 */
public void setCdnajd(String newCdnajd) { 
	cdnajd = newCdnajd;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new FournisseursBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected FournisseursBroker getMyFournisseursBroker() {
	return (FournisseursBroker)getMyBasicBroker();
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
 * Retourne un ArrayList d'objet métier : Fournisseurs.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Fournisseurs> listerFournisseurs(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Fournisseurs unFournisseurs = new Fournisseurs();
	return unFournisseurs.getMyFournisseursBroker().listerFournisseurs(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : Fournisseurs.
 * @param aTransaction aTransaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public static ArrayList<Fournisseurs> listerFournisseursNom(nc.mairie.technique.Transaction aTransaction,String param) throws Exception{
	Fournisseurs unFournisseurs = new Fournisseurs();
	return unFournisseurs.getMyFournisseursBroker().listerFournisseursNom(aTransaction,param);
}

/**
 * Retourne un Fournisseurs.
 * @param aTransaction aTransaction
 * @param code code
 * @return Fournisseurs
 * @throws Exception Exception
 */
public static Fournisseurs chercherFournisseurs(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Fournisseurs unFournisseurs = new Fournisseurs();
	return unFournisseurs.getMyFournisseursBroker().chercherFournisseurs(aTransaction, code);
}
}
