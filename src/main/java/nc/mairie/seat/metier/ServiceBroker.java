package nc.mairie.seat.metier;

import java.util.ArrayList;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Service
 */
public class ServiceBroker extends BasicBroker {
/**
 * Constructeur ServiceBroker.
 * @param aMetier BasicMetier
 */
public ServiceBroker(Service aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ServiceMetier
 */
@Override
protected Service definirMyMetier() {
	return new Service() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.ServiceMetier
 */
protected Service getMyService() {
	return (Service)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "MAIRIE_SISERVNW";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("SERVI", new BasicRecord("SERVI", "CHAR", getMyService().getClass().getField("servi"), "STRING"));
	mappage.put("LISERV", new BasicRecord("LISERV", "CHAR", getMyService().getClass().getField("liserv"), "STRING"));
	mappage.put("LI22", new BasicRecord("LI22", "CHAR", getMyService().getClass().getField("li22"), "STRING"));
	mappage.put("CODACT", new BasicRecord("CODACT", "CHAR", getMyService().getClass().getField("codact"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : Service.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<Service> listerService(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where liserv<>'' and codact<>'I' order by liserv");
}
/**
 * Retourne un Service.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Service
 * @throws Exception Exception
 */
public Service chercherService(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	//MODIF OF ICI 5 mars 2010
	//return (Service)executeSelect(aTransaction,"select * from "+getTable()+" where SERVI = '"+cle+"' and codact<>'I'");
	return (Service)executeSelect(aTransaction,"select * from "+getTable()+" where SERVI = '"+cle+"'");
}
/**
 * Retourne un Service.
 * @param aTransaction Transaction
 * @param cle cle
 * @return Service
 * @throws Exception Exception
 */
public Service chercherServiceActifAvecAcronyme(nc.mairie.technique.Transaction aTransaction, String cle) throws Exception {
	return (Service)executeSelect(aTransaction,"select * from "+getTable()+" where SIGLE = '"+cle+"' and codact <> 'I'");
}

/**
 * Retourne un arrayList Service.
 * @param aTransaction Transaction
 * @param param param
 * @return Service
 * @throws Exception Exception
 */
public ArrayList<Service> chercherListServiceTous(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where CODACT<>'I' and (upper(liserv) like '"+param.toUpperCase()+"%' or servi like'"+param.toUpperCase()+"%') order by liserv");
}
/**
 * Retourne un arrayList Service.
 * @param aTransaction Transaction
 * @param param param
 * @return Service
 * @throws Exception Exception
 */
public ArrayList<Service> chercherListServiceEquip(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeSelectListe(aTransaction,"select distinct(liserv),servi from "+getTable()+", f_aff_sce where servi = codeservice and dfin ='0001-01-01' and codact<>'I' and (upper(liserv) like '"+param.toUpperCase()+"%' or codeservice like '"+param.toUpperCase()+"%') order by liserv");
}

public ArrayList<Service> chercherListServiceAccro(nc.mairie.technique.Transaction aTransaction, String param) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" where upper(sigle) like '"+param.toUpperCase()+"%' order by servi");
}

}
