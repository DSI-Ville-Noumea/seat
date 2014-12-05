package nc.mairie.seat.metier;

import java.util.ArrayList;
import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier DeclarationAgentEquip
 */
public class DeclarationAgentEquipBroker extends BasicBroker {
/**
 * Constructeur DeclarationAgentEquipBroker.
 * @param aMetier BasicMetier
 */
public DeclarationAgentEquipBroker(DeclarationAgentEquip aMetier) {
	super(aMetier);
}
/**
 * @return src/nc.mairie.seat.metier.DeclarationAgentEquipMetier
 */
protected DeclarationAgentEquip definirMyMetier() {
	return new DeclarationAgentEquip() ;
}
/**
 * @return src/nc.mairie.seat.metier.DeclarationAgentEquipMetier
 */
protected DeclarationAgentEquip getMyDeclarationAgentEquip() {
	return (DeclarationAgentEquip)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
protected java.lang.String definirNomTable() {
	return "V_DECLARATION_AGENT_EQUIP";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("CODEDEC", new BasicRecord("CODEDEC", "NUMERIC", getMyDeclarationAgentEquip().getClass().getField("codedec"), "STRING"));
	mappage.put("DATE", new BasicRecord("DATE", "DATE", getMyDeclarationAgentEquip().getClass().getField("date"), "DATE"));
	mappage.put("IMMAT", new BasicRecord("IMMAT", "VARCHAR", getMyDeclarationAgentEquip().getClass().getField("immat"), "STRING"));
	mappage.put("DECLARANT", new BasicRecord("DECLARANT", "VARCHAR", getMyDeclarationAgentEquip().getClass().getField("declarant"), "STRING"));
	mappage.put("CODEOT", new BasicRecord("CODEOT", "NUMERIC", getMyDeclarationAgentEquip().getClass().getField("codeot"), "STRING"));
	mappage.put("CODESERVICE", new BasicRecord("CODESERVICE", "VARCHAR", getMyDeclarationAgentEquip().getClass().getField("codeservice"), "STRING"));
	return mappage;
}
/**
 * Retourne un ArrayList d'objet métier : DeclarationAgentEquip.
 * @param aTransaction Transaction
 * @return java.util.ArrayList
 * @throws Exception Exception
 */
public ArrayList<DeclarationAgentEquip> listerDeclarationAgentEquip(nc.mairie.technique.Transaction aTransaction) throws Exception {
	return executeSelectListe(aTransaction,"select * from "+getTable()+" order by \"DATE\" desc, codedec desc");
}
}
