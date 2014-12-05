package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PM_PePerso
 */
public class PM_PePersoBroker extends BasicBroker {
/**
 * Constructeur PM_PePersoBroker.
 * @param aMetier BasicMetier
 */
public PM_PePersoBroker(PM_PePerso aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_PePersoMetier
 */
@Override
protected PM_PePerso definirMyMetier() {
	return new PM_PePerso() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_PePersoMetier
 */
protected PM_PePerso getMyPM_PePerso() {
	return (PM_PePerso)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_PMPE_PERSO";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEPMPEP", new BasicRecord("CODEPMPEP", "INTEGER", getMyPM_PePerso().getClass().getField("codepmpep"), "STRING"));
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyPM_PePerso().getClass().getField("pminv"), "STRING"));
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyPM_PePerso().getClass().getField("numfiche"), "STRING"));
	mappage.put("CODEENTRETIEN", new BasicRecord("CODEENTRETIEN", "INTEGER", getMyPM_PePerso().getClass().getField("codeentretien"), "STRING"));
	mappage.put("DPREV", new BasicRecord("DPREV", "DATE", getMyPM_PePerso().getClass().getField("dprev"), "DATE"));
	mappage.put("DREAL", new BasicRecord("DREAL", "DATE", getMyPM_PePerso().getClass().getField("dreal"), "DATE"));
	mappage.put("DUREE", new BasicRecord("DUREE", "VARCHAR", getMyPM_PePerso().getClass().getField("duree"), "STRING"));
	mappage.put("SINISTRE", new BasicRecord("SINISTRE", "VARCHAR", getMyPM_PePerso().getClass().getField("sinistre"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "VARCHAR", getMyPM_PePerso().getClass().getField("commentaire"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPM_PePerso(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierPM_PePerso(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerPM_PePerso(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PM_PePerso.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<PM_PePerso> listerPM_PePerso(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PM_PePerso.
 * @param aTransaction Transaction
 * @param cle cle
 * @return PM_PePerso
 * @throws Exception Exception
 */
public PM_PePerso chercherPM_PePerso(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PM_PePerso)executeSelect(aTransaction,"select * from "+getTable()+" where codepmpep = "+cle+"");
}

//max code
public int nouvPmPePerso(nc.mairie.technique.Transaction aTransaction) throws Exception{
//	recherche du dernier code
	return executeCompter(aTransaction, "select max(codepmpep) from "+ getTable());
}

// on cherche les entretiens de la fiche d'entretiens
public ArrayList<PM_PePerso> chercherPmPePersoFiche(nc.mairie.technique.Transaction aTransaction, String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche);
}

public PM_PePerso chercherPePersoEquipEntPrevu(nc.mairie.technique.Transaction aTransaction, String inv,String ent) throws Exception {
	return (PM_PePerso)executeSelect(aTransaction,"select * from "+getTable()+" where  codepmpep in (select max(codepmpep) from "+getTable()+" where pminv = '"+inv+"' and codeentretien="+ent+" and dreal='0001-01-01')");
}

public ArrayList<PM_PePerso> chercherPmPePersoPasFaitFPM(nc.mairie.technique.Transaction aTransaction, String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche+" and dreal='0001-01-01'");
}

// tous les entretiens pour un petit matériel
public ArrayList<PM_PePerso> listerPM_PePersoPm(nc.mairie.technique.Transaction aTransaction,String pminv,String ddeb) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv='"+pminv+"' and dprev between '"+Services.formateDateInternationale(ddeb)+"' and '"+Services.formateDateInternationale(Services.ajouteJours(Services.dateDuJour(),1))+"'");
}

public ArrayList<PM_PePerso> listerPM_PePersoPmFait(nc.mairie.technique.Transaction aTransaction,String pminv,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dreal <> '0001-01-01' and pminv='"+pminv+"' order by "+tri+"");
}

public ArrayList<PM_PePerso> listerPM_PePersoPMateriel(nc.mairie.technique.Transaction aTransaction,String pminv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv='"+pminv+"'");
}

}
