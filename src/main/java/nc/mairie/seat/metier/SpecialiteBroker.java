package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Specialite
 */
public class SpecialiteBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur SpecialiteBroker.
 */
public SpecialiteBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.SpecialiteMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
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
	return "SEAT.F_SPECIALITE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODESPECIALITE", new BasicRecord("CODESPECIALITE", "INTEGER", getMySpecialite().getClass().getField("codespecialite"), "STRING"));
	mappage.put("LIBELLESPE", new BasicRecord("LIBELLESPE", "VARCHAR", getMySpecialite().getClass().getField("libellespe"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerSpecialite(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierSpecialite(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerSpecialite(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Specialite.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerSpecialite(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un Specialite.
 * @return Specialite
 */
public Specialite chercherSpecialite(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Specialite)executeSelect(aTransaction,"select * from "+getTable()+" where codespecialite = "+cle+"");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeSpecialite(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(libellespe) = '"+param.toUpperCase()+"'");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
* 
 */
public int nouvCodeSpec(nc.mairie.technique.Transaction aTransaction) throws Exception{
	//	recherche du dernier code
	return executeCompter(aTransaction, "select max(codespecialite) from "+ getTable());
	
}

}
