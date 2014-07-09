package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier ModePrise
 */
public class ModePriseBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws Exception Exception
 */
public boolean creerModePrise(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierModePrise(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerModePrise(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : ModePrise.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<ModePrise> listerModePrise(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un ModePrise.
 * @param aTransaction Transaction
 * @param cle cle
 * @return ModePrise
 * @throws Exception Exception
 */
public ModePrise chercherModePrise(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (ModePrise)executeSelect(aTransaction,"select * from "+getTable()+" where CODEMODEPRISE = "+cle+"");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * @author : Coralie NICOLAS
 */
public int nouvModePrise(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier code
	return executeCompter(aTransaction, "select max(codemodeprise) from "+ getTable());
	
}

/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return true ou false
 * @throws Exception Exception
 */
public boolean existeModePrise(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(designationmodeprise) = '"+param.toUpperCase()+"'");
}

/**
 * Constructeur ModePriseBroker.
 * @param aMetier BasicMetier
 */
public ModePriseBroker(ModePrise aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ModePriseMetier
 */
@Override
protected ModePrise definirMyMetier() {
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
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEMODEPRISE", new BasicRecord("CODEMODEPRISE", "INTEGER", getMyModePrise().getClass().getField("codemodeprise"), "STRING"));
	mappage.put("DESIGNATIONMODEPRISE", new BasicRecord("DESIGNATIONMODEPRISE", "VARCHAR", getMyModePrise().getClass().getField("designationmodeprise"), "STRING"));
	return mappage;
}
}
