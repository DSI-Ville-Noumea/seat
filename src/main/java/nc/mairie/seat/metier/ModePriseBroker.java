package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier ModePrise
 */
public class ModePriseBroker extends nc.mairie.technique.BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerModePrise(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierModePrise(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerModePrise(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : ModePrise.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerModePrise(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un ModePrise.
 * @return ModePrise
 */
public ModePrise chercherModePrise(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (ModePrise)executeSelect(aTransaction,"select * from "+getTable()+" where CODEMODEPRISE = "+cle+"");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
* 
 */
public int nouvModePrise(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier code
	return executeCompter(aTransaction, "select max(codemodeprise) from "+ getTable());
	
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeModePrise(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationmodeprise) = '"+param.toUpperCase()+"'");
}

/**
 * Constructeur ModePriseBroker.
 */
public ModePriseBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ModePriseMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new ModePrise() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ModePriseMetier
 */
protected ModePrise getMyModePrise() {
	return (ModePrise)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_MODEPRISE";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODEMODEPRISE", new BasicRecord("CODEMODEPRISE", "INTEGER", getMyModePrise().getClass().getField("codemodeprise"), "STRING"));
	mappage.put("DESIGNATIONMODEPRISE", new BasicRecord("DESIGNATIONMODEPRISE", "VARCHAR", getMyModePrise().getClass().getField("designationmodeprise"), "STRING"));
	return mappage;
}
}
