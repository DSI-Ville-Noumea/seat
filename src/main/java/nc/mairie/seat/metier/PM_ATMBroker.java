package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PM_ATM
 */
public class PM_ATMBroker extends BasicBroker {
/**
 * Constructeur PM_ATMBroker.
 * @param aMetier BasicMetier
 */
public PM_ATMBroker(PM_ATM aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PM_ATMMetier
 */
@Override
protected PM_ATM definirMyMetier() {
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
	return "F_PM_ATM";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMFICHE", new BasicRecord("NUMFICHE", "INTEGER", getMyPM_ATM().getClass().getField("numfiche"), "STRING"));
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "INTEGER", getMyPM_ATM().getClass().getField("matricule"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerPM_ATM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierPM_ATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerPM_ATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PM_ATM.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<PM_ATM> listerPM_ATM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PM_ATM.
 * @param aTransaction Transaction
 * @param cle cle
 * @return PM_ATM
 * @throws Exception Exception
 */
public PM_ATM chercherPM_ATM(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PM_ATM)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param numfiche numfiche
 * @param nomatr nomatr
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existePM_ATM(nc.mairie.technique.Transaction aTransaction, String numfiche,String nomatr) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numfiche="+numfiche+" and matricule="+nomatr);
}

// liste des pm_atm pour un numfiche
public ArrayList<PM_ATM> listerPM_ATM_FPM(nc.mairie.technique.Transaction aTransaction,String numfiche) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numfiche="+numfiche);
}

}
