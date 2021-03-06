package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier AffectationAgentInfos
 */
public class AffectationAgentInfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : AffectationAgentInfos.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @throws Exception Exception
 */
public ArrayList<AffectationAgentInfos> listerAffectationAgentInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable());
}
/**
 * Retourne un AffectationAgentInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return AffectationAgentInfos
 * @throws Exception Exception
 */
public AffectationAgentInfos chercherAffectationAgentInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (AffectationAgentInfos)executeSelect(aTransaction,"select * from "+getTable()+" where nomatr = "+cle);
}

/**
 * Retourne un ArrayList d'objet métier : AffectationAgentInfos.
 * @return java.util.ArrayList
 * @param aTransaction Transaction
 * @param param param
 * @throws Exception Exception
 */
public ArrayList<AffectationAgentInfos> chercherListAffectationsSce(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	// on prend le code du service et pas le code du sous service
	param = param.substring(0,3); //OK
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where matricule in(select nomatr from mairie_spmtsr where servi like '"+param+"%') order by datedebut desc, datefin desc");
}

public ArrayList<AffectationAgentInfos> chercherListAffectationsSceEquip(nc.mairie.technique.Transaction aTransaction,String param,String numinv) throws Exception {
	// on prend le code du service et pas le code du sous service
	param = param.substring(0,3); //OK
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire='"+numinv+"' and matricule in(select nomatr from mairie_spmtsr where servi like '"+param+"%') order by datedebut desc, datefin desc");
}
/**
 * Retourne un ArrayList d'objet métier : AffectationAgentInfos.
 * @return java.util.ArrayList
 * on recherche les affectations des agents pour un équipement
 * @param aTransaction Transaction
 * @param servi servi
 * @param inv inv
 * @param date date
 * @throws Exception Exception
 */
public ArrayList<AffectationAgentInfos> listAffectationsSceEquipDate(nc.mairie.technique.Transaction aTransaction,String servi,String inv, String date) throws Exception {
	// on prend le code du service et pas le code du sous service
	servi = servi.substring(0,3); //OK
	if(Services.estUneDate(date)){
		date = Services.formateDateInternationale(date);
	}else{
		return new ArrayList<AffectationAgentInfos>();
	}
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where matricule in(select nomatr from mairie_spmtsr where servi like '"+servi+"%' and datedebut>='"+date+"' and numeroinventaire='"+inv+"')");
}

/**
 * Constructeur AffectationAgentInfosBroker.
 * @param aMetier BAsicMetier
 */
public AffectationAgentInfosBroker(AffectationAgentInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AffectationAgentInfosMetier
 */
@Override
protected AffectationAgentInfos definirMyMetier() {
	return new AffectationAgentInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.AffectationAgentInfosMetier
 */
protected AffectationAgentInfos getMyAffectationAgentInfos() {
	return (AffectationAgentInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "V_AFFECTATIONSAGENTINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "NUMERIC", getMyAffectationAgentInfos().getClass().getField("matricule"), "STRING"));
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyAffectationAgentInfos().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("DATEDEBUT", new BasicRecord("DATEDEBUT", "DATE", getMyAffectationAgentInfos().getClass().getField("datedebut"), "DATE"));
	mappage.put("DATEFIN", new BasicRecord("DATEFIN", "DATE", getMyAffectationAgentInfos().getClass().getField("datefin"), "DATE"));
	mappage.put("NOM", new BasicRecord("NOM", "CHAR", getMyAffectationAgentInfos().getClass().getField("nom"), "STRING"));
	mappage.put("PRENOM", new BasicRecord("PRENOM", "CHAR", getMyAffectationAgentInfos().getClass().getField("prenom"), "STRING"));
	mappage.put("DATEMISEENCIRCULATION", new BasicRecord("DATEMISEENCIRCULATION", "DATE", getMyAffectationAgentInfos().getClass().getField("datemiseencirculation"), "DATE"));
	mappage.put("DATEVENTEOUREFORME", new BasicRecord("DATEVENTEOUREFORME", "DATE", getMyAffectationAgentInfos().getClass().getField("dateventeoureforme"), "DATE"));
	mappage.put("HDEB", new BasicRecord("HDEB", "INTEGER", getMyAffectationAgentInfos().getClass().getField("hdeb"), "STRING"));
	mappage.put("HFIN", new BasicRecord("HFIN", "INTEGER", getMyAffectationAgentInfos().getClass().getField("hfin"), "STRING"));
	mappage.put("HFINMN", new BasicRecord("HFINMN", "INTEGER", getMyAffectationAgentInfos().getClass().getField("hfinmn"), "STRING"));
	mappage.put("HDEBMN", new BasicRecord("HDEBMN", "INTEGER", getMyAffectationAgentInfos().getClass().getField("hdebmn"), "STRING"));
	mappage.put("CODESERVICE", new BasicRecord("CODESERVICE", "VARCHAR", getMyAffectationAgentInfos().getClass().getField("codeservice"), "STRING"));
	return mappage;
}
}
