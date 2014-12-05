package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PM_Affectation_Sce_Infos
 */
public class PM_Affectation_Sce_InfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : PM_Affectation_Sce_Infos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<PM_Affectation_Sce_Infos> listerPM_Affectation_Sce_Infos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PM_Affectation_Sce_Infos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return PM_Affectation_Sce_Infos
 * @throws Exception Exception
 */
public PM_Affectation_Sce_Infos chercherPM_Affectation_Sce_Infos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PM_Affectation_Sce_Infos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
public ArrayList<PM_Affectation_Sce_Infos> chercherListPM_Affectation_Sce_InfosPm(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv = '"+param+"'");
}
public PM_Affectation_Sce_Infos chercherPM_Affectation_Sce_InfosCourantPm(nc.mairie.technique.Transaction aTransaction, String inv,String date) throws Exception {
	return  (PM_Affectation_Sce_Infos)executeSelect(aTransaction, "select * from "+getTable()+" where pminv = '"+inv+"' and ddebut<='"+date+"' and dfin='0001-01-01'");
}

public PM_Affectation_Sce_Infos chercherPM_Affectation_Sce_InfosCourantPmEnCours(nc.mairie.technique.Transaction aTransaction, String inv) throws Exception {
	return  (PM_Affectation_Sce_Infos)executeSelect(aTransaction, "select * from "+getTable()+" where pminv = '"+inv+"' and dfin='0001-01-01'");
}

public ArrayList<PM_Affectation_Sce_Infos> listerPmAffectationSceInfosAgent(nc.mairie.technique.Transaction aTransaction,String nomatr) throws Exception {
	if (nomatr.equals("")){
		aTransaction.declarerErreur("Le numéro d'agent n'a pas été renseigné");
		ArrayList<PM_Affectation_Sce_Infos> listVide = new ArrayList<PM_Affectation_Sce_Infos>();
		return listVide;
	}
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dfin='0001-01-01' and nomatr="+nomatr);
}

public ArrayList<PM_Affectation_Sce_Infos> chercherPmAffectationSceInfosService(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return  executeSelectListe(aTransaction,"select * from "+getTable()+" where siserv like '"+cle+"%' and dfin = '0001-01-01'");
}

/**
 * Constructeur PM_Affectation_Sce_InfosBroker.
 * @param aMetier BasicMetier
 */
public PM_Affectation_Sce_InfosBroker(PM_Affectation_Sce_Infos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_Affectation_Sce_InfosMetier
 */
@Override
protected PM_Affectation_Sce_Infos definirMyMetier() {
	return new PM_Affectation_Sce_Infos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_Affectation_Sce_InfosMetier
 */
protected PM_Affectation_Sce_Infos getMyPM_Affectation_Sce_Infos() {
	return (PM_Affectation_Sce_Infos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "V_PM_AFF_SCE_INFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyPM_Affectation_Sce_Infos().getClass().getField("pminv"), "STRING"));
	mappage.put("PMSERIE", new BasicRecord("PMSERIE", "VARCHAR", getMyPM_Affectation_Sce_Infos().getClass().getField("pmserie"), "STRING"));
	mappage.put("DMES", new BasicRecord("DMES", "DATE", getMyPM_Affectation_Sce_Infos().getClass().getField("dmes"), "DATE"));
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyPM_Affectation_Sce_Infos().getClass().getField("codemodele"), "STRING"));
	mappage.put("DESIGNATIONMODELE", new BasicRecord("DESIGNATIONMODELE", "VARCHAR", getMyPM_Affectation_Sce_Infos().getClass().getField("designationmodele"), "STRING"));
	mappage.put("DESIGNATIONMARQUE", new BasicRecord("DESIGNATIONMARQUE", "VARCHAR", getMyPM_Affectation_Sce_Infos().getClass().getField("designationmarque"), "STRING"));
	mappage.put("CODEMARQUE", new BasicRecord("CODEMARQUE", "INTEGER", getMyPM_Affectation_Sce_Infos().getClass().getField("codemarque"), "STRING"));
	mappage.put("CODETE", new BasicRecord("CODETE", "INTEGER", getMyPM_Affectation_Sce_Infos().getClass().getField("codete"), "STRING"));
	mappage.put("DESIGNATIONTYPEEQUIP", new BasicRecord("DESIGNATIONTYPEEQUIP", "VARCHAR", getMyPM_Affectation_Sce_Infos().getClass().getField("designationtypeequip"), "STRING"));
	mappage.put("TYPETE", new BasicRecord("TYPETE", "VARCHAR", getMyPM_Affectation_Sce_Infos().getClass().getField("typete"), "STRING"));
	mappage.put("SISERV", new BasicRecord("SISERV", "VARCHAR", getMyPM_Affectation_Sce_Infos().getClass().getField("siserv"), "STRING"));
	mappage.put("DDEBUT", new BasicRecord("DDEBUT", "DATE", getMyPM_Affectation_Sce_Infos().getClass().getField("ddebut"), "DATE"));
	mappage.put("DFIN", new BasicRecord("DFIN", "DATE", getMyPM_Affectation_Sce_Infos().getClass().getField("dfin"), "DATE"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "INTEGER", getMyPM_Affectation_Sce_Infos().getClass().getField("nomatr"), "STRING"));
	return mappage;
}
}
