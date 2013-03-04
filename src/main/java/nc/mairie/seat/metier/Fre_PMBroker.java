package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Fre_PM
 */
public class Fre_PMBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur Fre_PMBroker.
 */
public Fre_PMBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Fre_PMMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Fre_PM() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Fre_PMMetier
 */
protected Fre_PM getMyFre_PM() {
	return (Fre_PM)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_FOURNISSEURS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODEFRE", new BasicRecord("CODEFRE", "INTEGER", getMyFre_PM().getClass().getField("codefre"), "STRING"));
	mappage.put("LIBELLEFRE", new BasicRecord("LIBELLEFRE", "CHAR", getMyFre_PM().getClass().getField("libellefre"), "STRING"));
	mappage.put("OBSERVATIONSFRE", new BasicRecord("OBSERVATIONSFRE", "CHAR", getMyFre_PM().getClass().getField("observationsfre"), "STRING"));
	mappage.put("CONTACT", new BasicRecord("CONTACT", "CHAR", getMyFre_PM().getClass().getField("contact"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerFre_PM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierFre_PM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerFre_PM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Fre_PM.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerFre_PM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Fre_PM.
 * @return Fre_PM
 */
public Fre_PM chercherFre_PM(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Fre_PM)executeSelect(aTransaction,"select * from "+getTable()+" where CODEFRE = "+cle+"");
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeFre_PM(nc.mairie.technique.Transaction aTransaction, String param,String observation,String contact) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(libellefre) = '"+param.toUpperCase()+"'and upper(observationsfre)='"+observation.toUpperCase()+"' and upper(contact)='"+contact.toUpperCase()+"'");
}
/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
* 
 */
public int nouvFre_PM(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codefre
	return executeCompter(aTransaction, "select max(codefre) from "+ getTable());
	
}
}
