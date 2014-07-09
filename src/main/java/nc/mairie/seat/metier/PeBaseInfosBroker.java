package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PeBaseInfos
 */
public class PeBaseInfosBroker extends BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : PeBaseInfos.
 * @param aTransaction Transaction
 * @param modele modele
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<PeBaseInfos> listerPeBaseInfos(nc.mairie.technique.Transaction aTransaction,String modele) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where codemodele="+modele+"");
}
/**
 * Retourne un PeBaseInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return PeBaseInfos
 * @throws Exception Exception
 */
public PeBaseInfos chercherPeBaseInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PeBaseInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+"");
}
/**
 * Constructeur PeBaseInfosBroker.
 * @param aMetier BasicMetier
 */
public PeBaseInfosBroker(PeBaseInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PeBaseInfosMetier
 */
@Override
protected PeBaseInfos definirMyMetier() {
	return new PeBaseInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PeBaseInfosMetier
 */
protected PeBaseInfos getMyPeBaseInfos() {
	return (PeBaseInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_PEBASEINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEENTRETIEN", new BasicRecord("CODEENTRETIEN", "INTEGER", getMyPeBaseInfos().getClass().getField("codeentretien"), "STRING"));
	mappage.put("LIBELLEENTRETIEN", new BasicRecord("LIBELLEENTRETIEN", "VARCHAR", getMyPeBaseInfos().getClass().getField("libelleentretien"), "STRING"));
	mappage.put("INTERVALLE", new BasicRecord("INTERVALLE", "INTEGER", getMyPeBaseInfos().getClass().getField("intervalle"), "STRING"));
	mappage.put("CODETI", new BasicRecord("CODETI", "INTEGER", getMyPeBaseInfos().getClass().getField("codeti"), "STRING"));
	mappage.put("DUREE", new BasicRecord("DUREE", "INTEGER", getMyPeBaseInfos().getClass().getField("duree"), "STRING"));
	mappage.put("COMMENTAIRE", new BasicRecord("COMMENTAIRE", "VARCHAR", getMyPeBaseInfos().getClass().getField("commentaire"), "STRING"));
	mappage.put("CODETD", new BasicRecord("CODETD", "VARCHAR", getMyPeBaseInfos().getClass().getField("codetd"), "STRING"));
	mappage.put("DESIGNATION", new BasicRecord("DESIGNATION", "CHAR", getMyPeBaseInfos().getClass().getField("designation"), "STRING"));
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyPeBaseInfos().getClass().getField("codemodele"), "STRING"));
	mappage.put("DESIGNATIONMARQUE", new BasicRecord("DESIGNATIONMARQUE", "VARCHAR", getMyPeBaseInfos().getClass().getField("designationmarque"), "STRING"));
	mappage.put("DESIGNATIONMODELE", new BasicRecord("DESIGNATIONMODELE", "VARCHAR", getMyPeBaseInfos().getClass().getField("designationmodele"), "STRING"));
	mappage.put("DESIGNATIONTYPEEQUIP", new BasicRecord("DESIGNATIONTYPEEQUIP", "VARCHAR", getMyPeBaseInfos().getClass().getField("designationtypeequip"), "STRING"));
	mappage.put("DESACTIVE", new BasicRecord("DESACTIVE", "VARCHAR", getMyPeBaseInfos().getClass().getField("desactive"), "STRING"));
	mappage.put("DATEDESACTIVATION", new BasicRecord("DATEDESACTIVATION", "DATE", getMyPeBaseInfos().getClass().getField("datedesactivation"), "DATE"));
	return mappage;
}
}
