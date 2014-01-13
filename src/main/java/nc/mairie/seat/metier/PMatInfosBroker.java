package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier PMatInfos
 */
public class PMatInfosBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean creerPMatInfos(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 */
public boolean modifierPMatInfos(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 */
public boolean supprimerPMatInfos(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : PMatInfos.
 * @return java.util.ArrayList
 */
public ArrayList<PMatInfos> listerPMatInfos(nc.mairie.technique.Transaction aTransaction,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by "+param);
}
/**
 * Retourne un ArrayList d'objet métier : PMatInfos.
 * @return java.util.ArrayList
 */
public ArrayList<PMatInfos> listerPMatInfosSansEntretienPlanifie(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"" +
			" where dmes < current date - 1 year" +
			" and not exists (select pminv from SEAT.F_PMPE_PERSO" +
			" where SEAT.V_PMATINFOS.pminv = SEAT.F_PMPE_PERSO.pminv and dprev between current date - 1 year and current date + 1 day)");
}
/**
 * Retourne un ArrayList d'objet métier : PMatInfos.
 * @return java.util.ArrayList
 */
public ArrayList<PMatInfos> listerPMatInfosRecherche(nc.mairie.technique.Transaction aTransaction,String pminv,String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where pminv like '"+pminv+"%' order by "+param);
}
/**
 * Retourne un PMatInfos.
 * @return PMatInfos
 */
public PMatInfos chercherPMatInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (PMatInfos)executeSelect(aTransaction,"select * from "+getTable()+" where char(pminv) = '"+cle+"'");
}

/**
 * Retourne un ArrayList d'objet métier : EquipementInfosActifs.
 * @return java.util.ArrayList
 */
public ArrayList<PMatInfos> listerPMatInfosActifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception {
	if(tri.equals("")){
		tri="pminv";
	}
//	on cherche la date du jour
	String datedujour = Services.dateDuJour();
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dmhs = '0001-01-01' or dmhs >'"+Services.formateDateInternationale(datedujour)+"' order by "+tri  );
}
/**
 * Retourne un ArrayList d'objet métier : EquipementInfos.
 * @return java.util.ArrayList
 */
public ArrayList<PMatInfos> listerPMatInfosInactifs(nc.mairie.technique.Transaction aTransaction,String tri) throws Exception {
	if(tri.equals("")){
		tri="pminv";
	}
//	on cherche la date du jour
	String datedujour = Services.dateDuJour();
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where dmhs <> '0001-01-01' and dmhs <='"+Services.formateDateInternationale(datedujour)+"' order by "+tri );
}

public boolean existePMatInfosPm(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where pminv = '"+param+"'");
}
/**
 * Constructeur PMatInfosBroker.
 */
public PMatInfosBroker(PMatInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PMatInfosMetier
 */
protected PMatInfos definirMyMetier() {
	return new PMatInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.PMatInfosMetier
 */
protected PMatInfos getMyPMatInfos() {
	return (PMatInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "SEAT.V_PMATINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("LIBELLEFRE", new BasicRecord("LIBELLEFRE", "CHAR", getMyPMatInfos().getClass().getField("libellefre"), "STRING"));
	mappage.put("OBSERVATIONSFRE", new BasicRecord("OBSERVATIONSFRE", "CHAR", getMyPMatInfos().getClass().getField("observationsfre"), "STRING"));
	mappage.put("CONTACT", new BasicRecord("CONTACT", "CHAR", getMyPMatInfos().getClass().getField("contact"), "STRING"));
	mappage.put("CODEFRE", new BasicRecord("CODEFRE", "INTEGER", getMyPMatInfos().getClass().getField("codefre"), "STRING"));
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyPMatInfos().getClass().getField("codemodele"), "STRING"));
	mappage.put("PMSERIE", new BasicRecord("PMSERIE", "VARCHAR", getMyPMatInfos().getClass().getField("pmserie"), "STRING"));
	mappage.put("DGARANTIE", new BasicRecord("DGARANTIE", "INTEGER", getMyPMatInfos().getClass().getField("dgarantie"), "STRING"));
	mappage.put("RESERVE", new BasicRecord("RESERVE", "CHAR", getMyPMatInfos().getClass().getField("reserve"), "STRING"));
	mappage.put("DMES", new BasicRecord("DMES", "DATE", getMyPMatInfos().getClass().getField("dmes"), "DATE"));
	mappage.put("DMHS", new BasicRecord("DMHS", "DATE", getMyPMatInfos().getClass().getField("dmhs"), "DATE"));
	mappage.put("PRIX", new BasicRecord("PRIX", "INTEGER", getMyPMatInfos().getClass().getField("prix"), "STRING"));
	mappage.put("DACHAT", new BasicRecord("DACHAT", "DATE", getMyPMatInfos().getClass().getField("dachat"), "DATE"));
	mappage.put("DESIGNATIONMODELE", new BasicRecord("DESIGNATIONMODELE", "VARCHAR", getMyPMatInfos().getClass().getField("designationmodele"), "STRING"));
	mappage.put("DESIGNATIONTYPEEQUIP", new BasicRecord("DESIGNATIONTYPEEQUIP", "VARCHAR", getMyPMatInfos().getClass().getField("designationtypeequip"), "STRING"));
	mappage.put("TYPETE", new BasicRecord("TYPETE", "VARCHAR", getMyPMatInfos().getClass().getField("typete"), "STRING"));
	mappage.put("DESIGNATIONMARQUE", new BasicRecord("DESIGNATIONMARQUE", "VARCHAR", getMyPMatInfos().getClass().getField("designationmarque"), "STRING"));
	mappage.put("CODEMARQUE", new BasicRecord("CODEMARQUE", "INTEGER", getMyPMatInfos().getClass().getField("codemarque"), "STRING"));
	mappage.put("CODETYPEEQUIP", new BasicRecord("CODETYPEEQUIP", "INTEGER", getMyPMatInfos().getClass().getField("codetypeequip"), "STRING"));
	mappage.put("PMINV", new BasicRecord("PMINV", "VARCHAR", getMyPMatInfos().getClass().getField("pminv"), "STRING"));
	mappage.put("VERSION", new BasicRecord("VERSION", "VARCHAR", getMyPMatInfos().getClass().getField("version"), "STRING"));
	mappage.put("DESIGNATIONCOMPTEUR", new BasicRecord("DESIGNATIONCOMPTEUR", "VARCHAR", getMyPMatInfos().getClass().getField("designationcompteur"), "STRING"));
	mappage.put("DESIGNATIONCARBU", new BasicRecord("DESIGNATIONCARBU", "VARCHAR", getMyPMatInfos().getClass().getField("designationcarbu"), "STRING"));
	return mappage;
}

public ArrayList<PMatInfos> chercherListPMatInfosTous(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where char(pminv) like '"+cle+"%' OR char(pmserie)  like '"+cle+"%'");
}


}
