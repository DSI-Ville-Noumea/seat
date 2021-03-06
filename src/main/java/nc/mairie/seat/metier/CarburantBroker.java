package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Carburant
 */
public class CarburantBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerCarburant(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierCarburant(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerCarburant(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Carburant.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Carburant> listerCarburant(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationcarbu");
}
/**
 * Retourne un Carburant.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Carburant
 * @throws Exception Exception
 */
public Carburant chercherCarburant(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Carburant)executeSelect(aTransaction,"select * from "+getTable()+" where CODECARBU = "+cle+"");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * author : Coralie NICOLAS
 */
public int nouvCarburant(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codecarbu
	return executeCompter(aTransaction, "select max(codecarbu) from "+ getTable());
	
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeCarburant(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationcarbu) = '"+param.toUpperCase()+"'");
}

public boolean existeCarburantPompes(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where num_pompe_atm = "+param);
}


/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @param num_pompe num_pompe
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeCarburantTout(nc.mairie.technique.Transaction aTransaction, String param,String num_pompe) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationcarbu) = '"+param.toUpperCase()+"' and num_pompe_atm="+num_pompe);
}

/**
 * Constructeur CarburantBroker.
 * @param aMetier BasicMetier
 */
public CarburantBroker(Carburant aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.CarburantMetier
 */
@Override
protected Carburant definirMyMetier() {
	return new Carburant() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.CarburantMetier
 */
protected Carburant getMyCarburant() {
	return (Carburant)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_CARBURANT";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODECARBU", new BasicRecord("CODECARBU", "INTEGER", getMyCarburant().getClass().getField("codecarbu"), "STRING"));
	mappage.put("DESIGNATIONCARBU", new BasicRecord("DESIGNATIONCARBU", "VARCHAR", getMyCarburant().getClass().getField("designationcarbu"), "STRING"));
	mappage.put("NUM_POMPE_ATM", new BasicRecord("NUM_POMPE_ATM", "NUMERIC", getMyCarburant().getClass().getField("num_pompe_atm"), "STRING"));
	return mappage;
}
}
