package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier PM_ATM
 */
public class PM_ATMBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur PM_ATMBroker.
 */
public PM_ATMBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_ATMMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new PM_ATM() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_ATMMetier
 */
protected PM_ATM getMyPM_ATM() {
	return (PM_ATM)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_PM_ATM";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyPM_ATM().getClass().getField("numfiche"), "STRING"));
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "INTEGER", getMyPM_ATM().getClass().getField("matricule"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPM_ATM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPM_ATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPM_ATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PM_ATM.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerPM_ATM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PM_ATM.
 * @return PM_ATM
 */
public PM_ATM chercherPM_ATM(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PM_ATM)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existePM_ATM(nc.mairie.technique.Transaction aTransaction, String numfiche,String nomatr) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numfiche="+numfiche+" and matricule="+nomatr);
}

// liste des pm_atm pour un numfiche
public java.util.ArrayList listerPM_ATM_FPM(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche="+numfiche);
}

}
