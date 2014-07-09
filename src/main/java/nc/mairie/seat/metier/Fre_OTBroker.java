package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Fre_OT
 */
public class Fre_OTBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerFre_OT(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierFre_OT(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerFre_OT(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Fre_OT.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Fre_OT> listerFre_OT(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}

/**
 * Retourne un ArrayList d'objet métier : Fre_OT.
 * @param aTransaction Transaction
 * @param numot numot
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Fre_OT> listerFre_OTOT(nc.mairie.technique.Transaction aTransaction,String numot) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numot="+numot);
}

/**
 * Retourne un Fre_OT.
 * @param aTransaction Transaction
 * @param numot numot
 * @param codeF codeF
 * @return Fre_OT
 * @throws Exception Exception
 */
public Fre_OT chercherFre_OT(nc.mairie.technique.Transaction aTransaction, String numot,String codeF) throws Exception {
	return (Fre_OT)executeSelect(aTransaction,"select * from "+getTable()+" where numeroot = "+numot+" and codefournisseur = "+codeF);
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param numot numot
 * @param codefre codefre
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeFre_OT(nc.mairie.technique.Transaction aTransaction, String numot,String codefre) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroot = "+numot+" and codefournisseur = "+codefre);
}

/**
 * Constructeur Fre_OTBroker.
 * @param aMetier BasicMetier
 */
public Fre_OTBroker(Fre_OT aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.Fre_OTMetier
 */
@Override
protected Fre_OT definirMyMetier() {
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
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEFOURNISSEUR", new BasicRecord("CODEFOURNISSEUR", "NUMERIC", getMyFre_OT().getClass().getField("codefournisseur"), "STRING"));
	mappage.put("NUMEROOT", new BasicRecord("NUMEROOT", "NUMERIC", getMyFre_OT().getClass().getField("numeroot"), "STRING"));
	return mappage;
}
}
