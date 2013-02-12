package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier BeFpmInfos
 */
public class BeFpmInfosBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur BeFpmInfosBroker.
 */
public BeFpmInfosBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BeFpmInfosMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new BeFpmInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BeFpmInfosMetier
 */
protected BeFpmInfos getMyBeFpmInfos() {
	return (BeFpmInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_BEFPMINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyBeFpmInfos().getClass().getField("numfiche"), "STRING"));
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyBeFpmInfos().getClass().getField("pminv"), "STRING"));
	mappage.put("DENTREE", new BasicRecord("DENTREE", "DATE", getMyBeFpmInfos().getClass().getField("dentree"), "DATE"));
	mappage.put("DSORTIE", new BasicRecord("DSORTIE", "DATE", getMyBeFpmInfos().getClass().getField("dsortie"), "DATE"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "VARCHAR", getMyBeFpmInfos().getClass().getField("valide"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "VARCHAR", getMyBeFpmInfos().getClass().getField("commentaire"), "STRING"));
	mappage.put("CODCOL", new BasicRecord("CODCOL", "INTEGER", getMyBeFpmInfos().getClass().getField("codcol"), "STRING"));
	mappage.put("EXERCI", new BasicRecord("EXERCI", "INTEGER", getMyBeFpmInfos().getClass().getField("exerci"), "STRING"));
	mappage.put("CODBUD", new BasicRecord("CODBUD", "INTEGER", getMyBeFpmInfos().getClass().getField("codbud"), "STRING"));
	mappage.put("NOENGJ", new BasicRecord("NOENGJ", "VARCHAR", getMyBeFpmInfos().getClass().getField("noengj"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : BeFpmInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerBeFpmInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un BeFpmInfos.
 * @return BeFpmInfos
 */
public BeFpmInfos chercherBeFpmInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (BeFpmInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}

// liste pour la numfiche passée en param
public java.util.ArrayList listerBeFpmInfosFpm(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche="+numfiche);
}

/**
 * Retourne un ArrayList d'objet métier : PiecesOtInfos.
 * @return java.util.ArrayList
 */
public int cumuleMontantBeFpmInfosBE(nc.mairie.technique.Transaction aTransaction,String numot, String numinv) throws Exception {
	return executeCompter(aTransaction," select sum(mtlenju)" +
			" from "+getTable()+" be " +
			" inner join SEAT.V_ENJU enju on enju.noengj = be.noengj " +
			" where numfiche="+numot+" " +
			" and cddep like '%"+numinv+"%'");
}

}
