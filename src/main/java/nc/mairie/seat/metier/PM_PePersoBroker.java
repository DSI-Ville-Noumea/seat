package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
/**
 * Broker de l'Objet métier PM_PePerso
 */
public class PM_PePersoBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PM_PePersoBroker.
 */
public PM_PePersoBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_PePersoMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
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
	return "SEAT.F_PMPE_PERSO";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
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
 */
public boolean creerPM_PePerso(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPM_PePerso(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPM_PePerso(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PM_PePerso.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPM_PePerso(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PM_PePerso.
 * @return PM_PePerso
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
public java.util.ArrayList chercherPmPePersoFiche(nc.mairie.technique.Transaction aTransaction, String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche);
}

public PM_PePerso chercherPePersoEquipEntPrevu(nc.mairie.technique.Transaction aTransaction, String inv,String ent) throws Exception {
	return (PM_PePerso)executeSelect(aTransaction,"select * from "+getTable()+" where  codepmpep in (select max(codepmpep) from "+getTable()+" where pminv = '"+inv+"' and codeentretien="+ent+" and dreal='0001-01-01')");
}

public java.util.ArrayList chercherPmPePersoPasFaitFPM(nc.mairie.technique.Transaction aTransaction, String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche = "+numfiche+" and dreal='0001-01-01'");
}

// tous les entretiens pour un petit matériel
public java.util.ArrayList listerPM_PePersoPm(nc.mairie.technique.Transaction aTransaction,String pminv,String ddeb) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv='"+pminv+"' and dprev between '"+Services.formateDateInternationale(ddeb)+"' and '"+Services.formateDateInternationale(Services.ajouteJours(Services.dateDuJour(),1))+"'");
}

public java.util.ArrayList listerPM_PePersoPmFait(nc.mairie.technique.Transaction aTransaction,String pminv,String tri) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dreal <> '0001-01-01' and pminv='"+pminv+"' order by "+tri+"");
}

public java.util.ArrayList listerPM_PePersoPMateriel(nc.mairie.technique.Transaction aTransaction,String pminv) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv='"+pminv+"'");
}

}
