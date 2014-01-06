package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier Carburant
 */
public class CarburantBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerCarburant(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierCarburant(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerCarburant(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : Carburant.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerCarburant(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationcarbu");
}
/**
 * Retourne un Carburant.
 * @return Carburant
 */
public Carburant chercherCarburant(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Carburant)executeSelect(aTransaction,"select * from "+getTable()+" where CODECARBU = "+cle+"");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvCarburant(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codecarbu
	return executeCompter(aTransaction, "select max(codecarbu) from "+ getTable());
	
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
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
 * @return true ou false
 */
public boolean existeCarburantTout(nc.mairie.technique.Transaction aTransaction, String param,String num_pompe) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationcarbu) = '"+param.toUpperCase()+"' and num_pompe_atm="+num_pompe);
}

/**
 * Constructeur CarburantBroker.
 */
public CarburantBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.CarburantMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
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
	return "SEAT.F_CARBURANT";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODECARBU", new BasicRecord("CODECARBU", "INTEGER", getMyCarburant().getClass().getField("codecarbu"), "STRING"));
	mappage.put("DESIGNATIONCARBU", new BasicRecord("DESIGNATIONCARBU", "VARCHAR", getMyCarburant().getClass().getField("designationcarbu"), "STRING"));
	mappage.put("NUM_POMPE_ATM", new BasicRecord("NUM_POMPE_ATM", "NUMERIC", getMyCarburant().getClass().getField("num_pompe_atm"), "STRING"));
	return mappage;
}
}
