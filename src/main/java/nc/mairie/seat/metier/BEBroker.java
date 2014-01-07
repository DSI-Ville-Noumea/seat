package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier BE
 */
public class BEBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerBE(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierBE(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerBE(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : BE.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerBEOTNumInv(nc.mairie.technique.Transaction aTransaction,String numot, String numInv) throws Exception {
	return executeSelectListe(aTransaction,
			" select * from "+getTable()+" be "+
			" inner join SEAT.V_ENJU enju on enju.noengj = be.noengj "+
			" where numot='"+numot+"' "+
			" and cddep like '%"+numInv+"%'");
}
/**
 * Retourne un BE.
 * @return BE
 */
public boolean existeBE(nc.mairie.technique.Transaction aTransaction, String noot,String exerci,String noengj) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numot = '"+noot+"' and noengj='"+noengj+"'");
}

/**
 * Retourne si un BE existe
 * @return boolean
 */
public boolean existeBEOT(nc.mairie.technique.Transaction aTransaction, String noot) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numot = '"+noot+"'");
}
/**
 * Retourne un BE.
 * @return BE
 */
public BE chercheBE(nc.mairie.technique.Transaction aTransaction, String noot,String noengj) throws Exception {
	return (BE)executeSelect(aTransaction,"select * from "+getTable()+" where numot = '"+noot+"' and noengj='"+noengj+"'");
}
/**
 * Constructeur BEBroker.
 */
public BEBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BEMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new BE() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BEMetier
 */
protected BE getMyBE() {
	return (BE)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_BENGAGEMENT";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODCOL", new BasicRecord("CODCOL", "NUMERIC", getMyBE().getClass().getField("codcol"), "STRING"));
	mappage.put("NUMOT", new BasicRecord("NUMOT", "NUMERIC", getMyBE().getClass().getField("numot"), "STRING"));
	mappage.put("EXERCI", new BasicRecord("EXERCI", "NUMERIC", getMyBE().getClass().getField("exerci"), "STRING"));
	mappage.put("CODBUD", new BasicRecord("CODBUD", "NUMERIC", getMyBE().getClass().getField("codbud"), "STRING"));
	mappage.put("NOENGJ", new BasicRecord("NOENGJ", "VARCHAR", getMyBE().getClass().getField("noengj"), "STRING"));
	return mappage;
}
}
