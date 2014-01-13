package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PM_Affecter_Sce
 */
public class PM_Affecter_SceBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PM_Affecter_Sce.
 * @return java.util.ArrayList
 */
public ArrayList<PM_Affecter_Sce> listerPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PM_Affecter_Sce.
 * @return PM_Affecter_Sce
 */

public PM_Affecter_Sce chercherPM_Affecter_Sce(nc.mairie.technique.Transaction aTransaction, String inv, String servi,String date,String dfin) throws Exception {
	return (PM_Affecter_Sce)executeSelect(aTransaction,"select * from "+getTable()+" where pminv = '"+inv+"' and siserv='"+servi+"' and ddebut='"+date+"' and dfin='"+dfin+"'");
}

public PM_Affecter_Sce chercherListerPmAffecter_ServicePmSce(nc.mairie.technique.Transaction aTransaction,String param,String servi) throws Exception {
	return (PM_Affecter_Sce)executeSelect(aTransaction,"select * from "+getTable()+" where pminv = '"+param+"' and siserv = '"+servi+"' and dfin = '0001-01-01'");
}

public ArrayList<PM_Affecter_Sce> chercherListerPmAffecter_ServicePm(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv = '"+param+"'");
}

/**
 * Constructeur PM_Affecter_SceBroker.
 */
public PM_Affecter_SceBroker(PM_Affecter_Sce aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_Affecter_SceMetier
 */
@Override
protected PM_Affecter_Sce definirMyMetier() {
	return new PM_Affecter_Sce() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_Affecter_SceMetier
 */
protected PM_Affecter_Sce getMyPM_Affecter_Sce() {
	return (PM_Affecter_Sce)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_PMAFF_SC";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("SISERV", new BasicRecord("SISERV", "VARCHAR", getMyPM_Affecter_Sce().getClass().getField("siserv"), "STRING"));
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyPM_Affecter_Sce().getClass().getField("pminv"), "STRING"));
	mappage.put("DDEBUT", new BasicRecord("DDEBUT", "DATE", getMyPM_Affecter_Sce().getClass().getField("ddebut"), "DATE"));
	mappage.put("DFIN", new BasicRecord("DFIN", "DATE", getMyPM_Affecter_Sce().getClass().getField("dfin"), "DATE"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "INTEGER", getMyPM_Affecter_Sce().getClass().getField("nomatr"), "STRING"));
	return mappage;
}
}
