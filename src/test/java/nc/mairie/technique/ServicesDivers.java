package nc.mairie.technique;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Assert;

public class ServicesDivers {

	private static Configuration conf= initConf();

	private static Configuration initConf() {
		try {
			return new PropertiesConfiguration("test.properties");
		} catch (ConfigurationException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
			System.exit(-5);
			return null;
		}
				
	}
	
	private static Connection getUneConnection() throws ClassNotFoundException, SQLException, ConfigurationException {
		
		

		String DRIVER = conf.getString("database.driver");
        Assert.assertNotNull(DRIVER);

        String URL = conf.getString("database.URL");
     	Assert.assertNotNull(URL);

     	String user = conf.getString("database.user");
     	Assert.assertNotNull(user);

     	String password = conf.getString("database.password");
     	Assert.assertNotNull(password);
     	
     	
     	Connection conn = null;
		 
		Class.forName(DRIVER); 
		conn = DriverManager.getConnection(URL, user, password);
		
		conn.setAutoCommit(false);
		 
		return conn;
	}
	
	public static Transaction getUneTransaction() throws ClassNotFoundException, SQLException, ConfigurationException {
		
		return new Transaction(getUneConnection());
		
	}
	
	
}
