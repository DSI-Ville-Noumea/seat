package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier TypeEntretien
 */
public class TypeEntretienBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur TypeEntretienBroker.
 */
public TypeEntretienBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.TypeEntretienMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new TypeEntretien() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.TypeEntretienMetier
 */
protected TypeEntretien getMyTypeEntretien() {
	return (TypeEntretien)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_TYPEENT";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODETYPEENT", new BasicRecord("CODETYPEENT", "INTEGER", getMyTypeEntretien().getClass().getField("codetypeent"), "STRING"));
	mappage.put("DESIGNATIONTYPEENT", new BasicRecord("DESIGNATIONTYPEENT", "VARCHAR", getMyTypeEntretien().getClass().getField("designationtypeent"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerTypeEntretien(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierTypeEntretien(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerTypeEntretien(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : TypeEntretien.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerTypeEntretien(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un TypeEntretien.
 * @return TypeEntretien
 */
public TypeEntretien chercherTypeEntretien(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (TypeEntretien)executeSelect(aTransaction,"select * from "+getTable()+" where codetypeent = "+cle+"");
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeTEntretien(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationtypeent) = '"+param.toUpperCase()+"'");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCodeTe(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier code
	return executeCompter(aTransaction, "select max(codetypeent) from "+ getTable());
	
}

}
