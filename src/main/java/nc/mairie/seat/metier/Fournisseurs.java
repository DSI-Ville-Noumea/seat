package nc.mairie.seat.metier;
/**
 * Objet métier Fournisseurs
 */
public class Fournisseurs extends nc.mairie.technique.BasicMetier {
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
public String getIdetbs() {
	return idetbs;
}
/**
 * Setter de l'attribut idetbs.
 */
public void setIdetbs(String newIdetbs) { 
	idetbs = newIdetbs;
}
/**
 * Getter de l'attribut enscom.
 */
public String getEnscom() {
	return enscom;
}
/**
 * Setter de l'attribut enscom.
 */
public void setEnscom(String newEnscom) { 
	enscom = newEnscom;
}
/**
 * Getter de l'attribut ridet.
 */
public String getRidet() {
	return ridet;
}
/**
 * Setter de l'attribut ridet.
 */
public void setRidet(String newRidet) { 
	ridet = newRidet;
}
/**
 * Getter de l'attribut siret.
 */
public String getSiret() {
	return siret;
}
/**
 * Setter de l'attribut siret.
 */
public void setSiret(String newSiret) { 
	siret = newSiret;
}
/**
 * Getter de l'attribut cdtyad.
 */
public String getCdtyad() {
	return cdtyad;
}
/**
 * Setter de l'attribut cdtyad.
 */
public void setCdtyad(String newCdtyad) { 
	cdtyad = newCdtyad;
}
/**
 * Getter de l'attribut notier.
 */
public String getNotier() {
	return notier;
}
/**
 * Setter de l'attribut notier.
 */
public void setNotier(String newNotier) { 
	notier = newNotier;
}
/**
 * Getter de l'attribut idadre.
 */
public String getIdadre() {
	return idadre;
}
/**
 * Setter de l'attribut idadre.
 */
public void setIdadre(String newIdadre) { 
	idadre = newIdadre;
}
/**
 * Getter de l'attribut cdtyed.
 */
public String getCdtyed() {
	return cdtyed;
}
/**
 * Setter de l'attribut cdtyed.
 */
public void setCdtyed(String newCdtyed) { 
	cdtyed = newCdtyed;
}
/**
 * Getter de l'attribut cdctlc.
 */
public String getCdctlc() {
	return cdctlc;
}
/**
 * Setter de l'attribut cdctlc.
 */
public void setCdctlc(String newCdctlc) { 
	cdctlc = newCdctlc;
}
/**
 * Getter de l'attribut cdetac.
 */
public String getCdetac() {
	return cdetac;
}
/**
 * Setter de l'attribut cdetac.
 */
public void setCdetac(String newCdetac) { 
	cdetac = newCdetac;
}
/**
 * Getter de l'attribut cdnajd.
 */
public String getCdnajd() {
	return cdnajd;
}
/**
 * Setter de l'attribut cdnajd.
 */
public void setCdnajd(String newCdnajd) { 
	cdnajd = newCdnajd;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected nc.mairie.technique.BasicBroker definirMyBroker() { 
	return new FournisseursBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
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
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerFournisseurs(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Fournisseurs unFournisseurs = new Fournisseurs();
	return unFournisseurs.getMyFournisseursBroker().listerFournisseurs(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : Fournisseurs.
 * @return java.util.ArrayList
 */
public static java.util.ArrayList listerFournisseursNom(nc.mairie.technique.Transaction aTransaction,String param) throws Exception{
	Fournisseurs unFournisseurs = new Fournisseurs();
	return unFournisseurs.getMyFournisseursBroker().listerFournisseursNom(aTransaction,param);
}

/**
 * Retourne un Fournisseurs.
 * @return Fournisseurs
 */
public static Fournisseurs chercherFournisseurs(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Fournisseurs unFournisseurs = new Fournisseurs();
	return unFournisseurs.getMyFournisseursBroker().chercherFournisseurs(aTransaction, code);
}
}
