package nc.mairie.seat.metier;
import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Equip
 */
public class Equip extends BasicMetier {
	public String numinv;
	public String numimmat;
	public String dmes;
	public String dventereforme;
	public String dhorscircuit;
	public String prix;
	public String reserve;
	public String codemodele;
	public String dgarantie;
/**
 * Constructeur Equip.
 */
public Equip() {
	super();
}
/**
 * Getter de l'attribut numinv.
 */
/**
 * @return String
 */
public String getNuminv() {
	return numinv;
}
/**
 * Setter de l'attribut numinv.
 */
/**
 * @param newNuminv newNuminv
 */
public void setNuminv(String newNuminv) { 
	numinv = newNuminv;
}
/**
 * Getter de l'attribut numimmat.
 */
/**
 * @return String
 */
public String getNumimmat() {
	return numimmat;
}
/**
 * Setter de l'attribut numimmat.
 */
/**
 * @param newNumimmat newNumimmat
 */
public void setNumimmat(String newNumimmat) { 
	numimmat = newNumimmat;
}
/**
 * Getter de l'attribut dmes.
 */
/**
 * @return String
 */
public String getDmes() {
	return dmes;
}
/**
 * Setter de l'attribut dmes.
 */
/**
 * @param newDmes newDmes
 */
public void setDmes(String newDmes) { 
	dmes = newDmes;
}
/**
 * Getter de l'attribut dventereforme.
 */
/**
 * @return String
 */
public String getDventereforme() {
	return dventereforme;
}
/**
 * Setter de l'attribut dventereforme.
 */
/**
 * @param newDventereforme newDventereforme
 */
public void setDventereforme(String newDventereforme) { 
	dventereforme = newDventereforme;
}
/**
 * Getter de l'attribut dhorscircuit.
 */
/**
 * @return String
 */
public String getDhorscircuit() {
	return dhorscircuit;
}
/**
 * Setter de l'attribut dhorscircuit.
 */
/**
 * @param newDhorscircuit newDhorscircuit
 */
public void setDhorscircuit(String newDhorscircuit) { 
	dhorscircuit = newDhorscircuit;
}
/**
 * Getter de l'attribut prix.
 */
/**
 * @return String
 */
public String getPrix() {
	return prix;
}
/**
 * Setter de l'attribut prix.
 */
/**
 * @param newPrix newPrix
 */
public void setPrix(String newPrix) { 
	prix = newPrix;
}
/**
 * Getter de l'attribut reserve.
 */
/**
 * @return String
 */
public String getReserve() {
	return reserve;
}
/**
 * Setter de l'attribut reserve.
 */
/**
 * @param newReserve newReserve
 */
public void setReserve(String newReserve) { 
	reserve = newReserve;
}
/**
 * Getter de l'attribut codemodele.
 */
/**
 * @return String
 */
public String getCodemodele() {
	return codemodele;
}
/**
 * Setter de l'attribut codemodele.
 */
/**
 * @param newCodemodele newCodemodele
 */
public void setCodemodele(String newCodemodele) { 
	codemodele = newCodemodele;
}
/**
 * Getter de l'attribut dgarantie.
 */
/**
 * @return String
 */
public String getDgarantie() {
	return dgarantie;
}
/**
 * Setter de l'attribut dgarantie.
 */
/**
 * @param newDgarantie newDgarantie
 */
public void setDgarantie(String newDgarantie) { 
	dgarantie = newDgarantie;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new EquipBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
* @return BasicBroker
*/
protected EquipBroker getMyEquipBroker() {
	return (EquipBroker)getMyBasicBroker();
}
/**
* Renvoie une chaîne correspondant à la valeur de cet objet.
* @return une représentation sous forme de chaîne du destinataire
*/
@Override
public String toString() {
	// Insérez ici le code pour finaliser le destinataire
	// Cette implémentation transmet le message au super. Vous pouvez remplacer ou compléter le message.
	return super.toString();
}
}
