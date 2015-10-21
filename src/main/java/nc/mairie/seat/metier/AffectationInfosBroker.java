package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier AffectationInfos
 */
public class AffectationInfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : AffectationInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AffectationInfos> listerAffectationInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable());
}
/**
 * Retourne un AffectationInfos.
 * @param aTransaction Transaction
 * @param matr matr
 * @param inv inv
 * @return AffectationInfos
 * @throws Exception Exception
 */
public ArrayList<AffectationInfos> chercherAffectationInfosAgentEquip(nc.mairie.technique.Transaction aTransaction, String matr,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where matricule = "+matr+" and numeroinventaire='"+inv+"'");
}

/**
 * Retourne un AffectationInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return AffectationInfos
 * @throws Exception Exception
 */
public ArrayList<AffectationInfos> chercherAffectationInfosAgent(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where matricule = "+cle);
}
/**
 * Retourne un AffectationInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @return AffectationInfos
 * @throws Exception Exception
 */
public ArrayList<AffectationInfos> chercherAffectationInfosEquip(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+inv+"'");
}

/**
 * Retourne un ArrayList d'objet métier : AffectationInfos.
 * @param aTransaction Transaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AffectationInfos> chercherListAffectationInfosEquip(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+param+"'");
}
/**
 * Retourne un ArrayList d'objet métier : AffectationInfos.
 * @param aTransaction Transaction
 * @param param param
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<AffectationInfos> chercherListAffectationInfosSce(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	String codeservice = param.substring(0,3);//OK
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeservice like '"+codeservice+"%'");
}

/**
 * Retourne un AffectationInfos.
 * @param aTransaction Transaction
 * @param servi servi
 * @return AffectationInfos
 * @throws Exception Exception
 */
public ArrayList<AffectationInfos> chercherListAgentEquipSce(nc.mairie.technique.Transaction aTransaction, String servi) throws Exception {
	String param = servi.substring(0,3);//OK	
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeservice like '"+param+"%'");
}

/**
 * Constructeur AffectationInfosBroker.
 * @param aMetier BasicMetier
 */
public AffectationInfosBroker(AffectationInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AffectationInfosMetier
 */
protected AffectationInfos definirMyMetier() {
	return new AffectationInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AffectationInfosMetier
 */
protected AffectationInfos getMyAffectationInfos() {
	return (AffectationInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "V_AFFECTATIONEQUIPINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyAffectationInfos().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("NUMEROIMMATRICULATION", new BasicRecord("NUMEROIMMATRICULATION", "VARCHAR", getMyAffectationInfos().getClass().getField("numeroimmatriculation"), "STRING"));
	mappage.put("DATEMISEENCIRCULATION", new BasicRecord("DATEMISEENCIRCULATION", "DATE", getMyAffectationInfos().getClass().getField("datemiseencirculation"), "DATE"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAffectationInfos().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAffectationInfos().getClass().getField("prenom"), "STRING"));
	mappage.put("DATEDEBUT", new BasicRecord("DATEDEBUT", "DATE", getMyAffectationInfos().getClass().getField("datedebut"), "DATE"));
	mappage.put("DATEFIN", new BasicRecord("DATEFIN", "DATE", getMyAffectationInfos().getClass().getField("datefin"), "DATE"));
	mappage.put("HDEB", new BasicRecord("HDEB", "INTEGER", getMyAffectationInfos().getClass().getField("hdeb"), "STRING"));
	mappage.put("HFIN", new BasicRecord("HFIN", "INTEGER", getMyAffectationInfos().getClass().getField("hfin"), "STRING"));
	mappage.put("HDEBMN", new BasicRecord("HDEBMN", "INTEGER", getMyAffectationInfos().getClass().getField("hdebmn"), "STRING"));
	mappage.put("HFINMN", new BasicRecord("HFINMN", "INTEGER", getMyAffectationInfos().getClass().getField("hfinmn"), "STRING"));
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "NUMERIC", getMyAffectationInfos().getClass().getField("matricule"), "STRING"));
	mappage.put("LISERV", new BasicRecord("LISERV", "CHAR", getMyAffectationInfos().getClass().getField("liserv"), "STRING"));
	mappage.put("DDEBUT", new BasicRecord("DDEBUT", "DATE", getMyAffectationInfos().getClass().getField("ddebut"), "DATE"));
	mappage.put("DFIN", new BasicRecord("DFIN", "DATE", getMyAffectationInfos().getClass().getField("dfin"), "DATE"));
	mappage.put("CODESERVICE", new BasicRecord("CODESERVICE", "VARCHAR", getMyAffectationInfos().getClass().getField("codeservice"), "STRING"));
	mappage.put("DATDEB", new BasicRecord("DATDEB", "NUMERIC", getMyAffectationInfos().getClass().getField("datdeb"), "STRING"));
	mappage.put("DATFIN", new BasicRecord("DATFIN", "NUMERIC", getMyAffectationInfos().getClass().getField("datfin"), "STRING"));
	return mappage;
}
}
