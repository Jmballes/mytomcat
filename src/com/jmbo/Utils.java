package com.jmbo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Utils {
	final static Logger logger = Logger.getLogger(Utils.class);
	private static String pathNameArray[] = { "c:\\tmp\\database.properties", "/properties/deploy.properties" };
	static Connection crunchifyConn = null;
	static PreparedStatement crunchifyPrepareStat = null;

	private static String myOS = whichOs();
	String hostname;
	String ipv4;
	String deploydate;

	public String init() {

		try {
			String pathname = pathNameArray[0];
			logger.info("Se intenta cargar file + " + pathname);
			File file = new File(pathname);
			if (!file.exists()) {
				pathname = pathNameArray[1];
				logger.info("Se intenta cargar file + " + pathname);
				file = new File(pathname);
			}
			logger.info("file precargado");
			InputStream input = new FileInputStream(file);
			Properties prop = new Properties();
			logger.info("properties cargado");

			// load a properties file
			prop.load(input);
			hostname = prop.getProperty("hostname");
			ipv4 = prop.getProperty("ipv4");
			deploydate = prop.getProperty("deploydate");

			// get the property value and print it out
			HtmlTableBuilder htb = new HtmlTableBuilder("Propiedades de despliegue:", true, 3, 2);
			htb.addTableHeader("Propertie","value");
			htb.addRowValues("hostname", hostname);
			htb.addRowValues("ipv4", ipv4);
			htb.addRowValues("deploydate", deploydate);
			return htb.build();
		} catch (IOException ex) {
			logger.info("error cargando fichero properties: " + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	private static String OS_WINDOWS = "win";
	private static String OS_MAC = "mac";
	private static String OS_LINUX = "Unix";

	public static String whichOs() {
		String OS = System.getProperty("os.name").toLowerCase();
		logger.info("SO is: " + OS);

		if (OS.contains("win")) {
			return OS_WINDOWS;
		} else if (OS.contains("mac")) {
			return OS_MAC;
		} else if ((OS.contains("nix") || OS.contains("nux") || OS.contains("aix"))) {
			return OS_LINUX;
		} else {
			logger.info("Your OS is not support!!");
		}
		return null;
	}

	// Simple log utility
	private static void getTablePropertiesHTML(String string) {

		logger.info(string);

	}

}
