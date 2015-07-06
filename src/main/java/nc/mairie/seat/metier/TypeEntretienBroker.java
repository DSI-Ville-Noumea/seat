package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier TypeEntretien
 */
public class TypeEntretienBroker extends BasicBroker {
/**
 * Constructeur TypeEntretienBroker.
 * @param aMetier BasicMetier
 */
public TypeEntretienBroker(TypeEntretien aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.TypeEntretienMetier
 */
@Override
protected TypeEntretien definirMyMetier() {
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
	return "F_TYPEENT";
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
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerTypeEntretien(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierTypeEntretien(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerTypeEntretien(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : TypeEntretien.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<TypeEntretien> listerTypeEntretien(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un TypeEntretien.
 * @param aTransaction Transaction
 * @param cle cle
 * @return TypeEntretien
 * @throws Exception Exception
 */
public TypeEntretien chercherTypeEntretien(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (TypeEntretien)executeSelect(aTransaction,"select * from "+getTable()+" where codetypeent = "+cle+"");
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeTEntretien(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationtypeent) = '"+param.toUpperCase()+"'");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * author : Coralie NICOLAS
 */
public int nouvCodeTe(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier code
	return executeCompter(aTransaction, "select max(codetypeent) from "+ getTable());
	
}

}
