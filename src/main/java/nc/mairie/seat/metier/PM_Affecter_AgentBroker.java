package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.Transaction;

/**
 * Broker de l'Objet métier PM_Affecter_Agent
 */
public class PM_Affecter_AgentBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPM_Affecter_Agent(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierPM_Affecter_Agent(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerPM_Affecter_Agent(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PM_Affecter_Agent.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<PM_Affecter_Agent> listerPM_Affecter_Agent(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PM_Affecter_Agent.
 * @param aTransaction Transaction
 * @param cle cle
 * @return PM_Affecter_Agent
 * @throws Exception Exception
 */
public PM_Affecter_Agent chercherPM_Affecter_Agent(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PM_Affecter_Agent)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}

public boolean existePmAffecter_Agent(nc.mairie.technique.Transaction aTransaction, String inv,String nomatr,String datedeb,String Hdeb) throws Exception {
	datedeb = Services.formateDateInternationale(datedeb);
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where pminv='"+inv+"' and matricule="+nomatr+" and ddeb='"+datedeb+"' and hdeb = "+Hdeb);
}

public ArrayList<PM_Affecter_Agent> chercherListerPmAffecter_AgentSce(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codesce like '"+param+"%' order by ddeb desc,hdeb desc,hfin desc,hdebmn desc");
}

public ArrayList<PM_Affecter_Agent> chercherListerPmAffecter_AgentPM(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv like '"+param+"%'");
}

public ArrayList<PM_Affecter_Agent> chercherListerPmAffecter_AgentEquipSceEnCours(nc.mairie.technique.Transaction aTransaction,String param,String inv,String date) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codesce like '"+param+"%' and pminv='"+inv+"' and ddeb>='"+date+"' order by ddeb desc,hdeb desc,hfin desc,hdebmn desc");
}

/**
 * Constructeur PM_Affecter_AgentBroker.
 * @param aMetier BasicMetier
 */
public PM_Affecter_AgentBroker(PM_Affecter_Agent aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_Affecter_AgentMetier
 */
@Override
protected PM_Affecter_Agent definirMyMetier() {
	return new PM_Affecter_Agent() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_Affecter_AgentMetier
 */
protected PM_Affecter_Agent getMyPM_Affecter_Agent() {
	return (PM_Affecter_Agent)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_PMAFF_AG";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "INTEGER", getMyPM_Affecter_Agent().getClass().getField("matricule"), "STRING"));
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyPM_Affecter_Agent().getClass().getField("pminv"), "STRING"));
	mappage.put("DDEB", new BasicRecord("DDEB", "DATE", getMyPM_Affecter_Agent().getClass().getField("ddeb"), "DATE"));
	mappage.put("DFIN", new BasicRecord("DFIN", "DATE", getMyPM_Affecter_Agent().getClass().getField("dfin"), "DATE"));
	mappage.put("HDEB", new BasicRecord("HDEB", "INTEGER", getMyPM_Affecter_Agent().getClass().getField("hdeb"), "STRING"));
	mappage.put("HFIN", new BasicRecord("HFIN", "INTEGER", getMyPM_Affecter_Agent().getClass().getField("hfin"), "STRING"));
	mappage.put("HDEBMN", new BasicRecord("HDEBMN", "INTEGER", getMyPM_Affecter_Agent().getClass().getField("hdebmn"), "STRING"));
	mappage.put("HFINMN", new BasicRecord("HFINMN", "INTEGER", getMyPM_Affecter_Agent().getClass().getField("hfinmn"), "STRING"));
	mappage.put("CODESCE", new BasicRecord("CODESCE", "VARCHAR", getMyPM_Affecter_Agent().getClass().getField("codesce"), "STRING"));
	return mappage;
}


/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param inv inv
 * @param datedeb datedeb
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existePM_Affecter_AgentAvantDate(Transaction aTransaction, String inv,String datedeb) throws Exception {
	datedeb = Services.formateDateInternationale(datedeb);
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where pminv='"+inv+"' and (dfin >=  '"+datedeb+"' or dfin = '0001-01-01')");
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param inv inv
 * @param datedeb datedeb
 * @param datefin datefin
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existePM_Affecter_AgentEntreDate(Transaction aTransaction, String inv,String datedeb, String datefin) throws Exception {
	datedeb = Services.formateDateInternationale(datedeb);
	datefin = Services.formateDateInternationale(datefin);
	if (datedeb.compareTo(datefin) < 0 ) {
		return executeTesteExiste(aTransaction,"select * from "+getTable()+" where pminv='"+inv+"' and ((ddeb between '"+datedeb+"' and '"+datefin+"') or (dfin between '"+datedeb+"' and '"+datefin+"'))");
	} else {
		return executeTesteExiste(aTransaction,"select * from "+getTable()+" where pminv='"+inv+"' and ((ddeb between '"+datefin+"' and '"+datedeb+"') or (dfin between '"+datefin+"' and '"+datedeb+"'))");
	}
}


}
