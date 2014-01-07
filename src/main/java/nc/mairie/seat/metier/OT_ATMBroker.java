package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier OT_ATM
 */
public class OT_ATMBroker extends nc.mairie.technique.BasicBroker {
/**
 * Constructeur OT_ATMBroker.
 */
public OT_ATMBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.OT_ATMMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
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
 */
public boolean creerOT_ATM(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierOT_ATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerOT_ATM(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : OT_ATM.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerOT_ATM(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un ArrayList d'objet métier : OT_ATM.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerOT_ATMOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numot="+numot);
}

/**
 * Retourne un OT_ATM.
 * @return OT_ATM
 */
public OT_ATM chercherOT_ATM(nc.mairie.technique.Transaction aTransaction, String matr) throws Exception {
	return (OT_ATM)executeSelect(aTransaction,"select * from "+getTable()+" where matricule = "+matr+"");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeOT_ATM(nc.mairie.technique.Transaction aTransaction, String numot,String nomatr) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numot="+numot+" and matricule="+nomatr);
}

}
