package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier TYPEEQUIP
 */
public class TYPEEQUIPBroker extends BasicBroker {
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
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerTYPEEQUIP(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierTYPEEQUIP(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerTYPEEQUIP(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : TYPEEQUIP.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<TYPEEQUIP> listerTYPEEQUIP(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationtypeequip");
}
/**
 * Retourne un TYPEEQUIP.
 * @param aTransaction Transaction
 * @param cle cle
 * @return TYPEEQUIP
 * @throws Exception Exception
 */
public TYPEEQUIP chercherTYPEEQUIP(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (TYPEEQUIP)executeSelect(aTransaction,"select * from "+getTable()+" where CODETYPEEQUIP = "+cle+"");
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @param type type
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeTEquip(nc.mairie.technique.Transaction aTransaction, String param,String type) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationtypeequip) = '"+param.toUpperCase()+"' and upper(typete)='"+type+"'");
}

/**
 * Constructeur TYPEEQUIPBroker.
 * @param aMetier BasicMetier
 */
public TYPEEQUIPBroker(TYPEEQUIP aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.TYPEEQUIPMetier
 */
@Override
protected TYPEEQUIP definirMyMetier() {
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
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODETYPEEQUIP", new BasicRecord("CODETYPEEQUIP", "INTEGER", getMyTYPEEQUIP().getClass().getField("codete"), "STRING"));
	mappage.put("DESIGNATIONTYPEEQUIP", new BasicRecord("DESIGNATIONTYPEEQUIP", "VARCHAR", getMyTYPEEQUIP().getClass().getField("designationte"), "STRING"));
	mappage.put("TYPETE", new BasicRecord("TYPETE", "VARCHAR", getMyTYPEEQUIP().getClass().getField("typete"), "STRING"));
	return mappage;
}
}
