/*
 * Créé le 28 avr. 2005
 *
 */
package nc.mairie.seat.servlet;

import nc.mairie.robot.Robot;
import nc.mairie.seat.robot.RobotSeat;
import nc.mairie.servlets.Frontale;

/**
 * author ssi
 *
 */
public class ServletSeat extends Frontale {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6676500516715817275L;

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
