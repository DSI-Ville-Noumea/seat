package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier BeFpmInfos
 */
public class BeFpmInfosBroker extends BasicBroker {
/**
 * Constructeur BeFpmInfosBroker.
 */
public BeFpmInfosBroker(BeFpmInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BeFpmInfosMetier
 */
@Override
protected BeFpmInfos definirMyMetier() {
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
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
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
public ArrayList<BeFpmInfos> listerBeFpmInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
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
public ArrayList<BeFpmInfos> listerBeFpmInfosFpm(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception {
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
