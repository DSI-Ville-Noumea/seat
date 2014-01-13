package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PM_BE
 */
public class PM_BEBroker extends BasicBroker {
/**
 * Constructeur PM_BEBroker.
 */
public PM_BEBroker(PM_BE aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_BEMetier
 */
@Override
protected PM_BE definirMyMetier() {
	return new PM_BE() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_BEMetier
 */
protected PM_BE getMyPM_BE() {
	return (PM_BE)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_PM_BE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyPM_BE().getClass().getField("numfiche"), "STRING"));
	mappage.put("CODCOL", new BasicRecord("CODCOL", "INTEGER", getMyPM_BE().getClass().getField("codcol"), "STRING"));
	mappage.put("EXERCI", new BasicRecord("EXERCI", "INTEGER", getMyPM_BE().getClass().getField("exerci"), "STRING"));
	mappage.put("CODBUD", new BasicRecord("CODBUD", "INTEGER", getMyPM_BE().getClass().getField("codbud"), "STRING"));
	mappage.put("NOENGJ", new BasicRecord("NOENGJ", "VARCHAR", getMyPM_BE().getClass().getField("noengj"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPM_BE(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPM_BE(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPM_BE(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PM_BE.
 * @return java.util.ArrayList
 */
public ArrayList<PM_BE> listerPM_BE(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PM_BE.
 * @return PM_BE
 */
public PM_BE chercherPM_BE(nc.mairie.technique.Transaction aTransaction, String numfiche,String noengj) throws Exception {
	return (PM_BE)executeSelect(aTransaction,"select * from "+getTable()+" where numfiche = '"+numfiche+"' and noengj='"+noengj+"'");
}
/**
 * Retourne un BE.
 * @return BE
 */
public boolean existeBE(nc.mairie.technique.Transaction aTransaction, String numfiche,String noengj) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numfiche = '"+numfiche+"' and noengj='"+noengj+"'");
}

// liste des BE pour la FPM
public ArrayList<PM_BE> listerPM_BE_FPM(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche="+numfiche);
}

}
