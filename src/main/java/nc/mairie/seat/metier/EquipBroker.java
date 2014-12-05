package nc.mairie.seat.metier;

import nc.mairie.technique.BasicRecord;
import nc.mairie.technique.BasicBroker;

/**
 * Broker de l'Objet métier Equip
 */
public class EquipBroker extends BasicBroker {
/**
 * Constructeur EquipBroker.
 * @param aMetier BasicMetier
 */
public EquipBroker(Equip aMetier) {
	super(aMetier);
}
/**
 * @return JavaSource/nc.mairie.seat.metier.EquipMetier
 */
@Override
protected Equip definirMyMetier() {
	return new Equip() ;
}
/**
 * @return JavaSource/nc.mairie.seat.metier.EquipMetier
 */
protected Equip getMyEquip() {
	return (Equip)getMyBasicMetier();
}
/**
 * Retourne le nom de la table.
 */
@Override
protected java.lang.String definirNomTable() {
	return "F_EQUIP";
}
/**
 * Retourne le mappage de chaque colonne de la table.
 */
@Override
protected java.util.Hashtable<String, BasicRecord> definirMappageTable() throws NoSuchFieldException {
	java.util.Hashtable<String, BasicRecord> mappage = new java.util.Hashtable<String, BasicRecord>();
	mappage.put("NUMINV", new BasicRecord("NUMINV", "VARCHAR", getMyEquip().getClass().getField("numinv"), "STRING"));
	mappage.put("NUMIMMAT", new BasicRecord("NUMIMMAT", "VARCHAR", getMyEquip().getClass().getField("numimmat"), "STRING"));
	mappage.put("DMES", new BasicRecord("DMES", "DATE", getMyEquip().getClass().getField("dmes"), "DATE"));
	mappage.put("DVENTEREFORME", new BasicRecord("DVENTEREFORME", "DATE", getMyEquip().getClass().getField("dventereforme"), "DATE"));
	mappage.put("DHORSCIRCUIT", new BasicRecord("DHORSCIRCUIT", "DATE", getMyEquip().getClass().getField("dhorscircuit"), "DATE"));
	mappage.put("PRIX", new BasicRecord("PRIX", "INTEGER", getMyEquip().getClass().getField("prix"), "STRING"));
	mappage.put("RESERVE", new BasicRecord("RESERVE", "VARCHAR", getMyEquip().getClass().getField("reserve"), "STRING"));
	mappage.put("CODEMODELE", new BasicRecord("CODEMODELE", "INTEGER", getMyEquip().getClass().getField("codemodele"), "STRING"));
	mappage.put("DGARANTIE", new BasicRecord("DGARANTIE", "INTEGER", getMyEquip().getClass().getField("dgarantie"), "STRING"));
	return mappage;
}
}
