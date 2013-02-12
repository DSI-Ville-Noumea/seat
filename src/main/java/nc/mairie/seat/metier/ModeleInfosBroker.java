package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
/**
 * Broker de l'Objet métier ModeleInfos
 */
public class ModeleInfosBroker extends nc.mairie.technique.BasicBroker {
/**
 * Retourne un ArrayList d'objet métier : ModeleInfos.
 * @return java.util.ArrayList
 */
public java.util.ArrayList listerModeleInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by designationmodele");
}
/**
 * Retourne un ModeleInfos.
 * @return ModeleInfos
 */
public ModeleInfos chercherModeleInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (ModeleInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODEMODELE = "+cle+"");
}

/**
 * Retourne un arrayList ModeleInfos.
 * @return ModeleInfos
 */
public java.util.ArrayList chercherListModeleInfosTous(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(designationmodele) like '"+param.toUpperCase()+"%' order by designationmodele");
}

/**
 * Constructeur ModeleInfosBroker.
 */
public ModeleInfosBroker(nc.mairie.technique.BasicMetier aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ModeleInfosMetier
 */
@Override
protected nc.mairie.technique.BasicMetier definirMyMetier() {
	return new ModeleInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ModeleInfosMetier
 */
protected ModeleInfos getMyModeleInfos() {
	return (ModeleInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "SEAT.V_MODELEINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable mappage = new java.util.Hashtable();
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyModeleInfos().getClass().getField("codemodele"), "STRING"));
	mappage.put("DESIGNATIONMODELE", new BasicRecord("DESIGNATIONMODELE", "CHAR", getMyModeleInfos().getClass().getField("designationmodele"), "STRING"));
	mappage.put("DESIGNATIONMARQUE", new BasicRecord("DESIGNATIONMARQUE", "CHAR", getMyModeleInfos().getClass().getField("designationmarque"), "STRING"));
	mappage.put("DESIGNATIONCARBU", new BasicRecord("DESIGNATIONCARBU", "CHAR", getMyModeleInfos().getClass().getField("designationcarbu"), "STRING"));
	mappage.put("DIMENSION", new BasicRecord("DIMENSION", "CHAR", getMyModeleInfos().getClass().getField("dimension"), "STRING"));
	mappage.put("DESIGNATIONTYPEEQUIP", new BasicRecord("DESIGNATIONTYPEEQUIP", "CHAR", getMyModeleInfos().getClass().getField("designationtypeequip"), "STRING"));
	mappage.put("DESIGNATIONCOMPTEUR", new BasicRecord("DESIGNATIONCOMPTEUR", "CHAR", getMyModeleInfos().getClass().getField("designationcompteur"), "STRING"));
	mappage.put("NBPNEUARRIERE", new BasicRecord("NBPNEUARRIERE", "INTEGER", getMyModeleInfos().getClass().getField("nbpneuarriere"), "STRING"));
	mappage.put("NBESSIEUX", new BasicRecord("NBESSIEUX", "INTEGER", getMyModeleInfos().getClass().getField("nbessieux"), "STRING"));
	mappage.put("CAPACITERESERVOIR", new BasicRecord("CAPACITERESERVOIR", "INTEGER", getMyModeleInfos().getClass().getField("capacitereservoir"), "STRING"));
	mappage.put("NBPNEUAVANT", new BasicRecord("NBPNEUAVANT", "INTEGER", getMyModeleInfos().getClass().getField("nbpneuavant"), "STRING"));
	mappage.put("PUISSANCE", new BasicRecord("PUISSANCE", "INTEGER", getMyModeleInfos().getClass().getField("puissance"), "STRING"));
	mappage.put("VERSION", new BasicRecord("VERSION", "CHAR", getMyModeleInfos().getClass().getField("version"), "STRING"));
	return mappage;
}

/**
 * Retourne un booléen.
 * Vérifie si le modèleinfos existe déjà
 * @return BPC
 */
public boolean existeModeleInfos(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where codemodele = "+param+"");
}

}
