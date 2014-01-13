package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PMateriel
 */
public class PMaterielBroker extends BasicBroker {
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPMateriel(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PMateriel.
 * @return java.util.ArrayList
 */
public ArrayList<PMateriel> listerPMateriel(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un PMateriel.
 * @return PMateriel
 */
public PMateriel chercherPMateriel(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PMateriel)executeSelect(aTransaction,"select * from "+getTable()+" where pminv = '"+cle+"'");
}
public boolean existePMaterielFre(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codefre = "+param+"");
}
/**
 * Constructeur PMaterielBroker.
 */
public PMaterielBroker(PMateriel aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PMaterielMetier
 */
@Override
protected PMateriel definirMyMetier() {
	return new PMateriel() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PMaterielMetier
 */
protected PMateriel getMyPMateriel() {
	return (PMateriel)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.F_PMATERIEL";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyPMateriel().getClass().getField("pminv"), "STRING"));
	mappage.put("PMSERIE", new BasicRecord("PMSERIE", "VARCHAR", getMyPMateriel().getClass().getField("pmserie"), "STRING"));
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyPMateriel().getClass().getField("codemodele"), "STRING"));
	mappage.put("CODEFRE", new BasicRecord("CODEFRE", "INTEGER", getMyPMateriel().getClass().getField("codefre"), "STRING"));
	mappage.put("DGARANTIE", new BasicRecord("DGARANTIE", "INTEGER", getMyPMateriel().getClass().getField("dgarantie"), "STRING"));
	mappage.put("RESERVE", new BasicRecord("RESERVE", "CHAR", getMyPMateriel().getClass().getField("reserve"), "STRING"));
	mappage.put("DMES", new BasicRecord("DMES", "DATE", getMyPMateriel().getClass().getField("dmes"), "DATE"));
	mappage.put("DMHS", new BasicRecord("DMHS", "DATE", getMyPMateriel().getClass().getField("dmhs"), "DATE"));
	mappage.put("PRIX", new BasicRecord("PRIX", "INTEGER", getMyPMateriel().getClass().getField("prix"), "STRING"));
	mappage.put("DACHAT", new BasicRecord("DACHAT", "DATE", getMyPMateriel().getClass().getField("dachat"), "DATE"));
	return mappage;
}
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPMateriel(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPMateriel(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Retourne un booléen.
 * Vérifie si  existe déjà
 * @return true ou false
 */
public boolean existePMateriel(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where upper(pminv) = '"+param.toUpperCase()+"'");
}

public int chercherPmSerie(nc.mairie.technique.Transaction aTransaction, String cle,String inv) throws Exception {
	return executeCompter(aTransaction,"select count(pmserie) from "+getTable()+" where pmserie = '"+cle+"' and pminv<>'"+inv+"'");
}

}
