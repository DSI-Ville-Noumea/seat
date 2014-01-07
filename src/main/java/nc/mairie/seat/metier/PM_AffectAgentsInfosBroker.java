package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier PM_AffectAgentsInfos
 */
public class PM_AffectAgentsInfosBroker extends nc.mairie.technique.BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : PM_AffectAgentsInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPM_AffectAgentsInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
public ArrayList listerPM_AffectAgentInfosScePMatDate(nc.mairie.technique.Transaction aTransaction,String servi,String inv, String date)throws Exception {
//	 on prend le code du service et pas le code du sous service
	servi = servi.substring(0,3);
	if(Services.estUneDate(date)){
		date = Services.formateDateInternationale(date);
	}else{
		return new ArrayList<PM_AffectAgentsInfos>();
	}
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where matricule in(select nomatr from mairie.spmtsr where codesce like '"+servi+"%' and ddeb>='"+date+"' and pminv='"+inv+"')");
}
/**
 * Retourne un PM_AffectAgentsInfos.
 * @return PM_AffectAgentsInfos
 */
public PM_AffectAgentsInfos chercherPM_AffectAgentsInfos(nc.mairie.technique.Transaction aTransaction, String servi) throws Exception {
	return (PM_AffectAgentsInfos)executeSelect(aTransaction,"select * from "+getTable()+" where codesce = "+servi+"");
}

public ArrayList chercherPM_AffectAgentsInfosSce(nc.mairie.technique.Transaction aTransaction, String servi) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codesce = "+servi+"");
}

public ArrayList chercherPM_AffectAgentsInfosScePM(nc.mairie.technique.Transaction aTransaction,String inv, String servi) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv='"+inv+"' and matricule in(select nomatr from mairie.spmtsr where servi like '"+servi+"%') order by ddeb desc, dfin");
}

/**
 * Constructeur PM_AffectAgentsInfosBroker.
 */
public PM_AffectAgentsInfosBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_AffectAgentsInfosMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new PM_AffectAgentsInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_AffectAgentsInfosMetier
 */
protected PM_AffectAgentsInfos getMyPM_AffectAgentsInfos() {
	return (PM_AffectAgentsInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_PM_AFFAGENTSINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "INTEGER", getMyPM_AffectAgentsInfos().getClass().getField("matricule"), "STRING"));
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyPM_AffectAgentsInfos().getClass().getField("pminv"), "STRING"));
	mappage.put("DDEB", new BasicRecord("DDEB", "DATE", getMyPM_AffectAgentsInfos().getClass().getField("ddeb"), "DATE"));
	mappage.put("DFIN", new BasicRecord("DFIN", "DATE", getMyPM_AffectAgentsInfos().getClass().getField("dfin"), "DATE"));
	mappage.put("HDEB", new BasicRecord("HDEB", "INTEGER", getMyPM_AffectAgentsInfos().getClass().getField("hdeb"), "STRING"));
	mappage.put("HFIN", new BasicRecord("HFIN", "INTEGER", getMyPM_AffectAgentsInfos().getClass().getField("hfin"), "STRING"));
	mappage.put("HDEBMN", new BasicRecord("HDEBMN", "INTEGER", getMyPM_AffectAgentsInfos().getClass().getField("hdebmn"), "STRING"));
	mappage.put("HFINMN", new BasicRecord("HFINMN", "INTEGER", getMyPM_AffectAgentsInfos().getClass().getField("hfinmn"), "STRING"));
	mappage.put("PMSERIE", new BasicRecord("PMSERIE", "VARCHAR", getMyPM_AffectAgentsInfos().getClass().getField("pmserie"), "STRING"));
	mappage.put("CODESCE", new BasicRecord("CODESCE", "VARCHAR", getMyPM_AffectAgentsInfos().getClass().getField("codesce"), "STRING"));
	return mappage;
}
}
