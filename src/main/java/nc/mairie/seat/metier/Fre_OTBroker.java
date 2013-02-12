package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Fre_OT
 */
public class Fre_OTBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerFre_OT(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierFre_OT(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerFre_OT(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Fre_OT.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerFre_OT(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}

/**
 * Retourne un ArrayList d'objet métier : Fre_OT.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerFre_OTOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numot="+numot);
}

/**
 * Retourne un Fre_OT.
 * @return Fre_OT
 */
public Fre_OT chercherFre_OT(nc.mairie.technique.Transaction aTransaction, String numot,String codeF) throws Exception {
	return (Fre_OT)executeSelect(aTransaction,"select * from "+getTable()+" where numeroot = "+numot+" and codefournisseur = "+codeF);
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeFre_OT(nc.mairie.technique.Transaction aTransaction, String numot,String codefre) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroot = "+numot+" and codefournisseur = "+codefre);
}

/**
 * Constructeur Fre_OTBroker.
 */
public Fre_OTBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Fre_OTMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new Fre_OT() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Fre_OTMetier
 */
protected Fre_OT getMyFre_OT() {
	return (Fre_OT)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_OT_FRE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODEFOURNISSEUR", new BasicRecord("CODEFOURNISSEUR", "NUMERIC", getMyFre_OT().getClass().getField("codefournisseur"), "STRING"));
	mappage.put("NUMEROOT", new BasicRecord("NUMEROOT", "NUMERIC", getMyFre_OT().getClass().getField("numeroot"), "STRING"));
	return mappage;
}
}
