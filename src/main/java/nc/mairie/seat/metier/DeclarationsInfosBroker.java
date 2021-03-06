package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier DeclarationsInfos
 */
public class DeclarationsInfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : DeclarationsInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<DeclarationsInfos> listerDeclarationsInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable());
}

/**
 * Retourne un ArrayList d'objet métier : DeclarationsInfos.
 * @param aTransaction Transaction
 * @param inv inv
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<DeclarationsInfos> listerDeclarationsInfosEquip(nc.mairie.technique.Transaction aTransaction,String inv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numinv='"+inv+"'");
}

/**
 * Retourne un ArrayList d'objet métier : DeclarationsInfos.
 * @param aTransaction Transaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<DeclarationsInfos> listerDeclarationsInfosOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeot="+numot);
}

/**
 * Retourne un DeclarationsInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return DeclarationsInfos
 * @throws Exception Exception
 */
public DeclarationsInfos chercherDeclarationsInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (DeclarationsInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle);
}
// recherche des déclarations pour un service et un équipement à partir d'une date
public ArrayList<DeclarationsInfos> listerDeclarationsInfosSce(nc.mairie.technique.Transaction aTransaction,String sce,String ddeb,String numinv,String dfin) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codeservice = '"+sce+"' and date>='"+Services.formateDateInternationale(ddeb)+"' and numinv='"+numinv+"' and date<='"+Services.formateDateInternationale(dfin)+"'");
}

/**
 * Constructeur DeclarationsInfosBroker.
 * @param aMetier BasicMetier
 */
public DeclarationsInfosBroker(DeclarationsInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.DeclarationsInfosMetier
 */
protected DeclarationsInfos definirMyMetier() {
	return new DeclarationsInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.DeclarationsInfosMetier
 */
protected DeclarationsInfos getMyDeclarationsInfos() {
	return (DeclarationsInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "V_DECLINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyDeclarationsInfos().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyDeclarationsInfos().getClass().getField("prenom"), "STRING"));
	mappage.put("DATEENTREE", new BasicRecord("DATEENTREE", "DATE", getMyDeclarationsInfos().getClass().getField("dateentree"), "DATE"));
	mappage.put("DATESORTIE", new BasicRecord("DATESORTIE", "DATE", getMyDeclarationsInfos().getClass().getField("datesortie"), "DATE"));
	mappage.put("NUMEROOT", new BasicRecord("NUMEROOT", "NUMERIC", getMyDeclarationsInfos().getClass().getField("numeroot"), "STRING"));
	mappage.put("COMPTEUR", new BasicRecord("COMPTEUR", "INTEGER", getMyDeclarationsInfos().getClass().getField("compteur"), "STRING"));
	mappage.put("VALIDE", new BasicRecord("VALIDE", "CHAR", getMyDeclarationsInfos().getClass().getField("valide"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "LONG VARCHAR", getMyDeclarationsInfos().getClass().getField("commentaire"), "STRING"));
	mappage.put("CODEDEC", new BasicRecord("CODEDEC", "NUMERIC", getMyDeclarationsInfos().getClass().getField("codedec"), "STRING"));
	mappage.put("DATE", new BasicRecord("DATE", "DATE", getMyDeclarationsInfos().getClass().getField("date"), "DATE"));
	mappage.put("ANOMALIES", new BasicRecord("ANOMALIES", "LONG VARCHAR", getMyDeclarationsInfos().getClass().getField("anomalies"), "STRING"));
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "NUMERIC", getMyDeclarationsInfos().getClass().getField("matricule"), "STRING"));
	mappage.put("NUMINV", new BasicRecord("NUMINV", "VARCHAR", getMyDeclarationsInfos().getClass().getField("numinv"), "STRING"));
	mappage.put("NUMEROIMMATRICULATION", new BasicRecord("NUMEROIMMATRICULATION", "VARCHAR", getMyDeclarationsInfos().getClass().getField("numeroimmatriculation"), "STRING"));
	mappage.put("DATEMISEENCIRCULATION", new BasicRecord("DATEMISEENCIRCULATION", "DATE", getMyDeclarationsInfos().getClass().getField("datemiseencirculation"), "DATE"));
	mappage.put("PRIXACHAT", new BasicRecord("PRIXACHAT", "INTEGER", getMyDeclarationsInfos().getClass().getField("prixachat"), "STRING"));
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyDeclarationsInfos().getClass().getField("codemodele"), "STRING"));
	mappage.put("DUREEGARANTIE", new BasicRecord("DUREEGARANTIE", "INTEGER", getMyDeclarationsInfos().getClass().getField("dureegarantie"), "STRING"));
	mappage.put("CODESERVICE", new BasicRecord("CODESERVICE", "VARCHAR", getMyDeclarationsInfos().getClass().getField("codeservice"), "STRING"));
	return mappage;
}
}
