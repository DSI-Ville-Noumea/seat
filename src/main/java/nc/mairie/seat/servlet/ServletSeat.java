/*
 * Créé le 28 avr. 2005
 *
 * TODO Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre - Préférences - Java - Style de code - Modèles de code
 */
package nc.mairie.seat.servlet;

import nc.mairie.robot.Robot;
import nc.mairie.seat.robot.RobotSeat;
import nc.mairie.servlets.Frontale;

/**
 * @author ssi
 *
 * TODO Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre - Préférences - Java - Style de code - Modèles de code
 */
public class ServletSeat extends Frontale {

	/* (non-Javadoc)
	 * @see nc.mairie.servlets.Frontale#getServletRobot()
	 */
	@Override
	protected Robot getServletRobot() { 
		return new RobotSeat(); 
	}
	
	@Override
	public void init() {
		super.init();
		System.out.println(" - BASE DE DONNEE SUR "+getMesParametres().get("HOST_SGBD"));
	}
	
	@Override
	public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
		super.doPost(request, response);
	}

}
