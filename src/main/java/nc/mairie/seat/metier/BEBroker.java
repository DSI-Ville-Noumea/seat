package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier BE
 */
public class BEBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerBE(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierBE(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerBE(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}

/**
 * Retourne un ArrayList d'objet métier : BE.
 * @param aTransaction Transaction
 * @param numot numot
 * @param numInv numInv
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<BE> listerBEOTNumInv(nc.mairie.technique.Transaction aTransaction,String numot, String numInv) throws Exception {
	return executeSelectListe(aTransaction,
			" select * from "+getTable()+" be "+
			" inner join V_ENJU enju on enju.noengj = be.noengj "+
			" where numot='"+numot+"' "+
			" and cddep like '%"+numInv+"%'");
}
/**
 * Retourne un BE.
 * @param aTransaction Transaction
 * @param noot noot
 * @param exerci exerci
 * @param noengj noengj
 * @return BE
 * @throws Exception Exception
 */
public boolean existeBE(nc.mairie.technique.Transaction aTransaction, String noot,String exerci,String noengj) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numot = '"+noot+"' and noengj='"+noengj+"'");
}

/**
 * Retourne si un BE existe
 * @param aTransaction Transaction
 * @param noot noot
 * @return boolean
 * @throws Exception Exception
 */
public boolean existeBEOT(nc.mairie.technique.Transaction aTransaction, String noot) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numot = '"+noot+"'");
}
/**
 * Retourne un BE.
 * @param aTransaction Transaction
 * @param noot noot
 * @param noengj noengj
 * @return BE
 * @throws Exception Exception
 */
public BE chercheBE(nc.mairie.technique.Transaction aTransaction, String noot,String noengj) throws Exception {
	return (BE)executeSelect(aTransaction,"select * from "+getTable()+" where numot = '"+noot+"' and noengj='"+noengj+"'");
}
/**
 * Constructeur BEBroker.
 * @param aMetier BasicMetier
 */
public BEBroker(BE aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BEMetier
 */
@Override
protected BE definirMyMetier() {
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
	return "F_BENGAGEMENT";
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
