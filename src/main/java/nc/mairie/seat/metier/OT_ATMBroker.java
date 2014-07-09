package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier OT_ATM
 */
public class OT_ATMBroker extends BasicBroker {
/**
 * Constructeur OT_ATMBroker.
 * @param aMetier BasicMetier
 */
public OT_ATMBroker(OT_ATM aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.OT_ATMMetier
 */
@Override
protected OT_ATM definirMyMetier() {
	return new OT_ATM() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.OT_ATMMetier
 */
protected OT_ATM getMyOT_ATM() {
	return (OT_ATM)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_OT_ATM";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("MATRICULE", new BasicRecord("MATRICULE", "NUMERIC", getMyOT_ATM().getClass().getField("matricule"), "STRING"));
	mappage.put("NUMOT", new BasicRecord("NUMOT", "NUMERIC", getMyOT_ATM().getClass().getField("numot"), "STRING"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerOT_ATM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierOT_ATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerOT_ATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : OT_ATM.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OT_ATM> listerOT_ATM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un ArrayList d'objet métier : OT_ATM.
 * @param aTransaction Transaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<OT_ATM> listerOT_ATMOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numot="+numot);
}

/**
 * Retourne un OT_ATM.
 * @param aTransaction Transaction
 * @param matr matr
 * @return OT_ATM
 * @throws Exception Exception
 */
public OT_ATM chercherOT_ATM(nc.mairie.technique.Transaction aTransaction, String matr) throws Exception {
	return (OT_ATM)executeSelect(aTransaction,"select * from "+getTable()+" where matricule = "+matr+"");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param numot numot
 * @param nomatr nomatr
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeOT_ATM(nc.mairie.technique.Transaction aTransaction, String numot,String nomatr) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numot="+numot+" and matricule="+nomatr);
}

}
