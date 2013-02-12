package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier TYPEEQUIP
 */
public class TYPEEQUIPBroker extends nc.mairie.technique.BasicBroker {
/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvTE(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier codepneu
	return executeCompter(aTransaction, "select max(codetypeequip) from "+ getTable());
	
}

/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerTYPEEQUIP(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierTYPEEQUIP(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerTYPEEQUIP(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : TYPEEQUIP.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerTYPEEQUIP(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationtypeequip");
}
/**
 * Retourne un TYPEEQUIP.
 * @return TYPEEQUIP
 */
public TYPEEQUIP chercherTYPEEQUIP(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (TYPEEQUIP)executeSelect(aTransaction,"select * from "+getTable()+" where CODETYPEEQUIP = "+cle+"");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existeTEquip(nc.mairie.technique.Transaction aTransaction, String param,String type) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationtypeequip) = '"+param.toUpperCase()+"' and upper(typete)='"+type+"'");
}

/**
 * Constructeur TYPEEQUIPBroker.
 */
public TYPEEQUIPBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.TYPEEQUIPMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new TYPEEQUIP() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.TYPEEQUIPMetier
 */
protected TYPEEQUIP getMyTYPEEQUIP() {
	return (TYPEEQUIP)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_TYPEEQUIP";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODETYPEEQUIP", new BasicRecord("CODETYPEEQUIP", "INTEGER", getMyTYPEEQUIP().getClass().getField("codete"), "STRING"));
	mappage.put("DESIGNATIONTYPEEQUIP", new BasicRecord("DESIGNATIONTYPEEQUIP", "VARCHAR", getMyTYPEEQUIP().getClass().getField("designationte"), "STRING"));
	mappage.put("TYPETE", new BasicRecord("TYPETE", "VARCHAR", getMyTYPEEQUIP().getClass().getField("typete"), "STRING"));
	return mappage;
}
}
