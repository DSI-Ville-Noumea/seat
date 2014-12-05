package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier BPCPMInfos
 */
public class BPCPMInfosBroker extends BasicBroker {
/**
 * Constructeur BPCPMInfosBroker.
 * @param aMetier BasicMetier
 */
public BPCPMInfosBroker(BPCPMInfos aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BPCPMInfosMetier
 */
@Override
protected BPCPMInfos definirMyMetier() {
	return new BPCPMInfos() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BPCPMInfosMetier
 */
protected BPCPMInfos getMyBPCPMInfos() {
	return (BPCPMInfos)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "V_BPCPMINFOS";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMEROBPC", new BasicRecord("NUMEROBPC", "INTEGER", getMyBPCPMInfos().getClass().getField("numerobpc"), "STRING"));
	mappage.put("DATE", new BasicRecord("DATE", "DATE", getMyBPCPMInfos().getClass().getField("date"), "DATE"));
	mappage.put("HEURE", new BasicRecord("HEURE", "VARCHAR", getMyBPCPMInfos().getClass().getField("heure"), "STRING"));
	mappage.put("VALEURCOMPTEUR", new BasicRecord("VALEURCOMPTEUR", "INTEGER", getMyBPCPMInfos().getClass().getField("valeurcompteur"), "STRING"));
	mappage.put("NUMEROPOMPE", new BasicRecord("NUMEROPOMPE", "INTEGER", getMyBPCPMInfos().getClass().getField("numeropompe"), "STRING"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyBPCPMInfos().getClass().getField("quantite"), "STRING"));
	mappage.put("MODEDEPRISE", new BasicRecord("MODEDEPRISE", "INTEGER", getMyBPCPMInfos().getClass().getField("modedeprise"), "STRING"));
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyBPCPMInfos().getClass().getField("numeroinventaire"), "STRING"));
	mappage.put("PMSERIE", new BasicRecord("PMSERIE", "VARCHAR", getMyBPCPMInfos().getClass().getField("pmserie"), "STRING"));
	mappage.put("DMES", new BasicRecord("DMES", "DATE", getMyBPCPMInfos().getClass().getField("dmes"), "DATE"));
	mappage.put("SISERV", new BasicRecord("SISERV", "INTEGER", getMyBPCPMInfos().getClass().getField("siserv"), "STRING"));
	mappage.put("DDEBUT", new BasicRecord("DDEBUT", "DATE", getMyBPCPMInfos().getClass().getField("ddebut"), "DATE"));
	mappage.put("DFIN", new BasicRecord("DFIN", "DATE", getMyBPCPMInfos().getClass().getField("dfin"), "DATE"));
	mappage.put("LISERV", new BasicRecord("LISERV", "CHAR", getMyBPCPMInfos().getClass().getField("liserv"), "STRING"));
	mappage.put("NOMATR", new BasicRecord("NOMATR", "INTEGER", getMyBPCPMInfos().getClass().getField("nomatr"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : BPCPMInfos.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<BPCPMInfos> listerBPCPMInfos(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" with ur");
}
/**
 * Retourne un BPCPMInfos.
 * @param aTransaction Transaction
 * @param cle cle
 * @return BPCPMInfos
 * @throws Exception Exception
 */
public BPCPMInfos chercherBPCPMInfos(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (BPCPMInfos)executeSelect(aTransaction,"select * from "+getTable()+" where CODE = "+cle+" with ur");
}
/**
 * Retourne un ArrayList d'objet métier : BPCInfosCompletes.
 * @param aTransaction Transaction
 * @param inv inv
 * @param servi servi
 * @param periode periode
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<BPCPMInfos> listerBPCPMInfosParams(nc.mairie.technique.Transaction aTransaction,String inv,String servi,String periode) throws Exception {
	if(!inv.equals("")){
		if(!periode.equals("")){
			periode = "and "+periode;
			if(!servi.equals("")){
				servi = "and "+servi;
			}
		}else{
			if(!servi.equals("")){
				servi = "and "+servi;
			}
		}
	}else{
		if(!periode.equals("")){
			if(!servi.equals("")){
				servi = "and "+servi;
			}
		}
	}
	
	return executeSelectListe(aTransaction,"select distinct(numerobpc),date,heure,valeurcompteur, numeropompe, quantite, modedeprise, numeroinventaire, pmserie, siserv, ddebut, dfin, liserv,dmes from "+getTable()+" where "+inv+periode+servi+" order by date with ur");
}
}
