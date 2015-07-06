package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.Services;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier BPC
 */
public class BPCBroker extends BasicBroker {
/**
 * Methode creerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean
 * @throws Exception Exception
 */
public boolean creerBPC(nc.mairie.technique.Transaction aTransaction)  throws Exception{
	return creer(aTransaction);
}
/**
 * Methode modifierObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean modifierBPC(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return modifier(aTransaction);
}
/**
 * Methode supprimerObjetMetierBroker qui retourne
 * true ou false
 * @param aTransaction Transaction
 * @return boolean 
 * @throws java.lang.Exception java.lang.Exception
 */
public boolean supprimerBPC(nc.mairie.technique.Transaction aTransaction) throws java.lang.Exception {
	return supprimer(aTransaction);
}
/**
 * Retourne un ArrayList d'objet métier : BPC.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<BPC> listerBPC(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+"");
}
/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param cle cle
 * @return BPC
 * @throws Exception Exception
 */
public BPC chercherBPC(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where NUMEROBPC = "+cle+"");
}
/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param date date
 * @param inv inv
 * @return BPC
 * @throws Exception Exception
 */
public BPC chercherBPCPrecEquipDate(nc.mairie.technique.Transaction aTransaction,String date, String inv) throws Exception {
	//return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire='"+inv+"' and date in (select max(date) from "+getTable()+" where numeroinventaire='"+inv+"'");
	
	if(date!=null){
		date = Services.formateDateInternationale(date);
		return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+inv+"'and date in (select max(date) from "+getTable()+" where date<'"+date+"' and numeroinventaire='"+inv+"') order by valeurcompteur desc");
	}else{
		return new BPC();
	}
	
}

/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param param param
 * @param inv inv
 * @return BPC
 * @throws Exception Exception
 */
public BPC chercherBPCDerOT(nc.mairie.technique.Transaction aTransaction, String param,String inv) throws Exception {
	if (param==null){
		return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where date<>'0001-01-01' and numeroinventaire = '"+inv+"'");
	}else{
		if(!Services.estUneDate(param)){
			aTransaction.declarerErreur("La date n'est pas correcte.");
			return new BPC();
		}
		String date = Services.formateDateInternationale(param);
		return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where date < '"+date+"' and date<>'0001-01-01' and numeroinventaire = '"+inv+"' and numerobpc in (select max(numerobpc) from "+getTable()+" where date < '"+date+"' and date<>'0001-01-01' and numeroinventaire = '"+inv+"')");
	}
}

/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param date date
 * @param inv inv
 * @return BPC
 * @throws Exception Exception
 */
public BPC chercherBPCSuivEquipDate(nc.mairie.technique.Transaction aTransaction, String date,String inv) throws Exception {
	if(date!=null){
		date = Services.formateDateInternationale(date);
		return (BPC)executeSelect(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+inv+"' and date in (select min(date) from "+getTable()+" where date>'"+date+"' and numeroinventaire = '"+inv+"')");
	}else{
		return new BPC();
	}
	
}

/**
 * Retourne un booléen.
 * Vérifie si le BPC existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return BPC
 * @throws Exception Exception
 */
public boolean existeBPC(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where NUMEROBPC = "+param+"");
}

/**
 * Retourne un BPC.
 * @param aTransaction Transaction
 * @param cle cle
 * @return BPC
 * @throws Exception Exception
 */
public ArrayList<BPC> listerBPCInventaire(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where NUMEROINVENTAIRE = '"+cle+"'");
}

public ArrayList<BPC> listeBPCEquipAnnee(nc.mairie.technique.Transaction aTransaction, String cle,String date) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where NUMEROINVENTAIRE = '"+cle+"' and date>'"+date+"'");
}

/* On recherche le code max pour pouvoir l'incrémenter lors de la création d'un objet
 * author : Coralie NICOLAS
 */
public int nouvBPC(nc.mairie.technique.Transaction aTransaction) throws Exception{

//	recherche du dernier numerobpc
	return executeCompter(aTransaction, "select max(numerobpc) from "+ getTable());
	
}
/**
 * Retourne un ArrayList d'objet métier : BPC.
 * @param aTransaction Transaction
 * @param cle cle
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<BPC> listerBPCEquipement(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+cle+"'");
}

/**
 * Constructeur BPCBroker.
 * @param aMetier BasicMetier
 */
public BPCBroker(BPC aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BPCMetier
 */
protected BPC definirMyMetier() {
	return new BPC() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.BPCMetier
 */
protected BPC getMyBPC() {
	return (BPC)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "F_BPC";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMEROBPC", new BasicRecord("NUMEROBPC", "INTEGER", getMyBPC().getClass().getField("numerobpc"), "STRING"));
	mappage.put("DATE", new BasicRecord("DATE", "DATE", getMyBPC().getClass().getField("date"), "DATE"));
	mappage.put("HEURE", new BasicRecord("HEURE", "VARCHAR", getMyBPC().getClass().getField("heure"), "STRING"));
	mappage.put("VALEURCOMPTEUR", new BasicRecord("VALEURCOMPTEUR", "INTEGER", getMyBPC().getClass().getField("valeurcompteur"), "STRING"));
	mappage.put("NUMEROPOMPE", new BasicRecord("NUMEROPOMPE", "INTEGER", getMyBPC().getClass().getField("numeropompe"), "STRING"));
	mappage.put("QUANTITE", new BasicRecord("QUANTITE", "INTEGER", getMyBPC().getClass().getField("quantite"), "STRING"));
	mappage.put("MODEDEPRISE", new BasicRecord("MODEDEPRISE", "INTEGER", getMyBPC().getClass().getField("modedeprise"), "STRING"));
	mappage.put("NUMEROINVENTAIRE", new BasicRecord("NUMEROINVENTAIRE", "VARCHAR", getMyBPC().getClass().getField("numeroinventaire"), "STRING"));
	return mappage;
}

/**
 * Retourne un booléen.
 * Vérifie si le modèleinfos existe déjà
 * @param aTransaction Transaction
 * @param param param
 * @return BPC
 * @throws Exception Exception
 */
public boolean existeBPCEquip(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeroinventaire = '"+param+"'");
}
public boolean existeBPCModePrise(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where modedeprise = "+param+"");
}

public boolean existeBPCPompe(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeTesteExiste(aTransaction,"select * from "+getTable()+" where numeropompe = "+param+"");
}
/**
 * Retourne un ArrayList d'objet métier : BPCInfosCompletes.
 * @param aTransaction Transaction
 * @param inv inv
 * @param periode periode
 * @param servi servi
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<BPC> listerBPCParams(nc.mairie.technique.Transaction aTransaction,String inv,String periode, String servi) throws Exception {
	
	String where = "";
	if (!"".equals(servi)) {
		where+=	"left join f_aff_sce aff_sce on aff_sce.numeroinventaire = bpc.numeroinventaire and codeservice = '"+servi+"' and date >= aff_sce.ddebut and (date < aff_sce.dfin or aff_sce.dfin = '0001-01-01')"+
				"left join f_pmaff_sc pmaff_sc on pmaff_sc.pminv = bpc.numeroinventaire and siserv = '"+servi+"' and date >= pmaff_sc.ddebut and (date < pmaff_sc.dfin or pmaff_sc.dfin = '0001-01-01')";
		where+="where not (aff_sce is null and pmaff_sc.siserv is null)";
	}
	
	if(!inv.equals("")){
		where+=" and bpc."+inv;
	}
	
	if(!periode.equals("")){
		where += " and "+periode;
	}
	
	if (where.startsWith(" and"))
		where = " where "+where.substring(4);

	return executeSelectListe(aTransaction,"select bpc.* from "+getTable()+" bpc "+where+" order by date desc");
}
}
