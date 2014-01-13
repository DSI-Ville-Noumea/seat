package nc.mairie.seat.metier;


import nc.mairie.technique.BasicBroker;
import nc.mairie.technique.BasicMetier;

/**
 * Objet métier Agents
 */
public class Agents extends BasicMetier implements AgentInterface {
	public String idindi;
	public String nomatr;
	public String nom;
	public String prenom;
	public String nomjfi;
	public String datnai;
	public String lieuna;
	public String cddesi;
	public String sexe;
	public String nation;
	public String cdfami;
	public String ninsee;
	public String dattit;
	public String datdec;
	public String cdregl;
	public String idadrs;
	public String idcpte;
	public String teldom;
	public String noport;
	public String bister;
	public String lidopu;
	public String lirue;
	public String bp;
	public String licare;
	public String cdvill;
	public String livill;
	public String cdbanq;
	public String cdguic;
	public String nocpte;
	public String clerib;
	public String cdelec;
	public String datemb;
	public String cdetud;
/**
 * Constructeur Agents.
 */
public Agents() {
	super();
}
/**
 * Getter de l'attribut idindi.
 */
public String getIdindi() {
	return idindi;
}
/**
 * Setter de l'attribut idindi.
 */
public void setIdindi(String newIdindi) { 
	idindi = newIdindi;
}
/**
 * Getter de l'attribut nomatr.
 */
public String getNomatr() {
	return nomatr;
}
/**
 * Setter de l'attribut nomatr.
 */
public void setNomatr(String newNomatr) { 
	nomatr = newNomatr;
}
/**
 * Getter de l'attribut nom.
 */
public String getNom() {
	return nom;
}
/**
 * Setter de l'attribut nom.
 */
public void setNom(String newNom) { 
	nom = newNom;
}
/**
 * Getter de l'attribut prenom.
 */
public String getPrenom() {
	return prenom;
}
/**
 * Setter de l'attribut prenom.
 */
public void setPrenom(String newPrenom) { 
	prenom = newPrenom;
}
/**
 * Getter de l'attribut nomjfi.
 */
public String getNomjfi() {
	return nomjfi;
}
/**
 * Setter de l'attribut nomjfi.
 */
public void setNomjfi(String newNomjfi) { 
	nomjfi = newNomjfi;
}
/**
 * Getter de l'attribut datnai.
 */
public String getDatnai() {
	return datnai;
}
/**
 * Setter de l'attribut datnai.
 */
public void setDatnai(String newDatnai) { 
	datnai = newDatnai;
}
/**
 * Getter de l'attribut lieuna.
 */
public String getLieuna() {
	return lieuna;
}
/**
 * Setter de l'attribut lieuna.
 */
public void setLieuna(String newLieuna) { 
	lieuna = newLieuna;
}
/**
 * Getter de l'attribut cddesi.
 */
public String getCddesi() {
	return cddesi;
}
/**
 * Setter de l'attribut cddesi.
 */
public void setCddesi(String newCddesi) { 
	cddesi = newCddesi;
}
/**
 * Getter de l'attribut sexe.
 */
public String getSexe() {
	return sexe;
}
/**
 * Setter de l'attribut sexe.
 */
public void setSexe(String newSexe) { 
	sexe = newSexe;
}
/**
 * Getter de l'attribut nation.
 */
public String getNation() {
	return nation;
}
/**
 * Setter de l'attribut nation.
 */
public void setNation(String newNation) { 
	nation = newNation;
}
/**
 * Getter de l'attribut cdfami.
 */
public String getCdfami() {
	return cdfami;
}
/**
 * Setter de l'attribut cdfami.
 */
public void setCdfami(String newCdfami) { 
	cdfami = newCdfami;
}
/**
 * Getter de l'attribut ninsee.
 */
public String getNinsee() {
	return ninsee;
}
/**
 * Setter de l'attribut ninsee.
 */
public void setNinsee(String newNinsee) { 
	ninsee = newNinsee;
}
/**
 * Getter de l'attribut dattit.
 */
public String getDattit() {
	return dattit;
}
/**
 * Setter de l'attribut dattit.
 */
public void setDattit(String newDattit) { 
	dattit = newDattit;
}
/**
 * Getter de l'attribut datdec.
 */
public String getDatdec() {
	return datdec;
}
/**
 * Setter de l'attribut datdec.
 */
public void setDatdec(String newDatdec) { 
	datdec = newDatdec;
}
/**
 * Getter de l'attribut cdregl.
 */
public String getCdregl() {
	return cdregl;
}
/**
 * Setter de l'attribut cdregl.
 */
public void setCdregl(String newCdregl) { 
	cdregl = newCdregl;
}
/**
 * Getter de l'attribut idadrs.
 */
public String getIdadrs() {
	return idadrs;
}
/**
 * Setter de l'attribut idadrs.
 */
public void setIdadrs(String newIdadrs) { 
	idadrs = newIdadrs;
}
/**
 * Getter de l'attribut idcpte.
 */
public String getIdcpte() {
	return idcpte;
}
/**
 * Setter de l'attribut idcpte.
 */
public void setIdcpte(String newIdcpte) { 
	idcpte = newIdcpte;
}
/**
 * Getter de l'attribut teldom.
 */
public String getTeldom() {
	return teldom;
}
/**
 * Setter de l'attribut teldom.
 */
public void setTeldom(String newTeldom) { 
	teldom = newTeldom;
}
/**
 * Getter de l'attribut noport.
 */
public String getNoport() {
	return noport;
}
/**
 * Setter de l'attribut noport.
 */
public void setNoport(String newNoport) { 
	noport = newNoport;
}
/**
 * Getter de l'attribut bister.
 */
public String getBister() {
	return bister;
}
/**
 * Setter de l'attribut bister.
 */
public void setBister(String newBister) { 
	bister = newBister;
}
/**
 * Getter de l'attribut lidopu.
 */
public String getLidopu() {
	return lidopu;
}
/**
 * Setter de l'attribut lidopu.
 */
public void setLidopu(String newLidopu) { 
	lidopu = newLidopu;
}
/**
 * Getter de l'attribut lirue.
 */
public String getLirue() {
	return lirue;
}
/**
 * Setter de l'attribut lirue.
 */
public void setLirue(String newLirue) { 
	lirue = newLirue;
}
/**
 * Getter de l'attribut bp.
 */
public String getBp() {
	return bp;
}
/**
 * Setter de l'attribut bp.
 */
public void setBp(String newBp) { 
	bp = newBp;
}
/**
 * Getter de l'attribut licare.
 */
public String getLicare() {
	return licare;
}
/**
 * Setter de l'attribut licare.
 */
public void setLicare(String newLicare) { 
	licare = newLicare;
}
/**
 * Getter de l'attribut cdvill.
 */
public String getCdvill() {
	return cdvill;
}
/**
 * Setter de l'attribut cdvill.
 */
public void setCdvill(String newCdvill) { 
	cdvill = newCdvill;
}
/**
 * Getter de l'attribut livill.
 */
public String getLivill() {
	return livill;
}
/**
 * Setter de l'attribut livill.
 */
public void setLivill(String newLivill) { 
	livill = newLivill;
}
/**
 * Getter de l'attribut cdbanq.
 */
public String getCdbanq() {
	return cdbanq;
}
/**
 * Setter de l'attribut cdbanq.
 */
public void setCdbanq(String newCdbanq) { 
	cdbanq = newCdbanq;
}
/**
 * Getter de l'attribut cdguic.
 */
public String getCdguic() {
	return cdguic;
}
/**
 * Setter de l'attribut cdguic.
 */
public void setCdguic(String newCdguic) { 
	cdguic = newCdguic;
}
/**
 * Getter de l'attribut nocpte.
 */
public String getNocpte() {
	return nocpte;
}
/**
 * Setter de l'attribut nocpte.
 */
public void setNocpte(String newNocpte) { 
	nocpte = newNocpte;
}
/**
 * Getter de l'attribut clerib.
 */
public String getClerib() {
	return clerib;
}
/**
 * Setter de l'attribut clerib.
 */
public void setClerib(String newClerib) { 
	clerib = newClerib;
}
/**
 * Getter de l'attribut cdelec.
 */
public String getCdelec() {
	return cdelec;
}
/**
 * Setter de l'attribut cdelec.
 */
public void setCdelec(String newCdelec) { 
	cdelec = newCdelec;
}
/**
 * Getter de l'attribut datemb.
 */
public String getDatemb() {
	return datemb;
}
/**
 * Setter de l'attribut datemb.
 */
public void setDatemb(String newDatemb) { 
	datemb = newDatemb;
}
/**
 * Getter de l'attribut cdetud.
 */
public String getCdetud() {
	return cdetud;
}
/**
 * Setter de l'attribut cdetud.
 */
public void setCdetud(String newCdetud) { 
	cdetud = newCdetud;
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
@Override
protected BasicBroker definirMyBroker() { 
	return new AgentsBroker(this); 
}
/**
 Methode à définir dans chaque objet Métier pour instancier un Broker 
*/
protected AgentsBroker getMyAgentsBroker() {
	return (AgentsBroker)getMyBasicBroker();
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
/**
 * Retourne un ArrayList d'objet métier : Agents.
 * @return java.util.ArrayList
 */
public static ArrayList<Agents> listerAgents(nc.mairie.technique.Transaction aTransaction) throws Exception{
	Agents unAgents = new Agents();
	return unAgents.getMyAgentsBroker().listerAgents(aTransaction);
}
/**
 * Retourne un Agents.
 * @return Agents
 */
public static Agents chercherAgents(nc.mairie.technique.Transaction aTransaction, String code) throws Exception{
	Agents unAgents = new Agents();
	return unAgents.getMyAgentsBroker().chercherAgents(aTransaction, code);
}
/**
 * Retourne un Agents.
 * @return Agents
 */
public static ArrayList<Agents> listerAgentsNom(nc.mairie.technique.Transaction aTransaction, String nom) throws Exception{
	Agents unAgents = new Agents();
	return unAgents.getMyAgentsBroker().listerAgentsNom(aTransaction, nom);
}

}