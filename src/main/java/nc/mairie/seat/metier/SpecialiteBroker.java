package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Specialite
 */
public class SpecialiteBroker extends BasicBroker {
/**
 * Constructeur SpecialiteBroker.
 * @param aMetier BasicMetier
 */
public SpecialiteBroker(Specialite aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.SpecialiteMetier
 */
@Override
protected Specialite definirMyMetier() {
	return new Specialite() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.SpecialiteMetier
 */
protected Specialite getMySpecialite() {
	return (Specialite)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_SPECIALITE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODESPECIALITE", new BasicRecord("CODESPECIALITE", "INTEGER", getMySpecialite().getClass().getField("codespecialite"), "STRING"));
	mappage.put("LIBELLESPE", new BasicRecord("LIBELLESPE", "VARCHAR", getMySpecialite().getClass().getField("libellespe"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerSpecialite(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierSpecialite(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerSpecialite(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Specialite.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Specialite> listerSpecialite(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Specialite.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Specialite
 * @throws Exception Exception
 */
public Specialite chercherSpecialite(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Specialite)executeSelect(aTransaction,"select * from "+getTable()+" where codespecialite = "+cle+"");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeSpecialite(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(libellespe) = '"+param.toUpperCase()+"'");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * author : Coralie NICOLAS
 */
public int nouvCodeSpec(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier code
	return executeCompter(aTransaction, "select max(codespecialite) from "+ getTable());
	
}

}
