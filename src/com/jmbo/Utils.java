package com.jmbo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Utils {
	final static Logger logger = Logger.getLogger(Utils.class);
	private static String pathNameArray[] = { "c:\\tmp\\deploy.properties", "/properties/deploy.properties" };
	static Connection crunchifyConn = null;
	static PreparedStatement crunchifyPrepareStat = null;

	private static String myOS = whichOs();
//	String hostname;
//	String ipv4;
//	String deploydate;

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
//			Properties prop = new Properties();
			logger.info("properties cargado");

			// load a properties file
//			prop.load(input);
//			hostname = prop.getProperty("hostname");
//			ipv4 = prop.getProperty("ipv4");
//			deploydate = prop.getProperty("deploydate");
//
//			// get the property value and print it out
//			HtmlTableBuilder htb = new HtmlTableBuilder("Propiedades de despliegue:", true, 3, 2);
//			htb.addTableHeader("Propertie", "value");
//			htb.addRowValues("hostname", hostname);
//			htb.addRowValues("ipv4", ipv4);
//			htb.addRowValues("deploydate", deploydate);
			String table = ch(file);
			return table;
		} catch (IOException ex) {
			logger.info("error cargando fichero properties: " + ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	private String ch(File initialFile) {
		String table = null;
		try {
			InputStream targetStream = new FileInputStream(initialFile);
			table = readFromInputStream(targetStream);
			System.out.println("Table:" + table);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return table;
	}

	private String readFromInputStream(InputStream inputStream) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		String deploydate = null;
		resultStringBuilder.append(" OS:" + getDetailOs() + "<br>");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
//			            resultStringBuilder.append(line).append("\n");
				String date=null;
				String propertie=null;
				String value=null;
				try {
					int indexdate = line.indexOf('=');
					int indexpropertie = line.indexOf('=', indexdate + 1);

					date = line.substring(0, indexdate);
					propertie = line.substring(indexdate, indexpropertie);
					value = line.substring(indexpropertie);
				} catch (Exception e) {
					if (date != null) {
						date="error";
					}
					if (propertie != null) {
						propertie="error";
					}
					if (value != null) {
						value="error";
					}
				}
				if (deploydate == null) {

					deploydate = date;
					resultStringBuilder.append("<b>");
					resultStringBuilder.append(date);
					resultStringBuilder.append("</b>");
					resultStringBuilder.append(HtmlTableBuilder.TABLE_START_BORDER);
				} else if (!deploydate.equals(date)) {
					resultStringBuilder.append(HtmlTableBuilder.TABLE_END);
					resultStringBuilder.append("<b>");
					resultStringBuilder.append(date);
					resultStringBuilder.append("</b>");
					resultStringBuilder.append(HtmlTableBuilder.TABLE_START_BORDER);
					deploydate = date;
				}
				resultStringBuilder.append(HtmlTableBuilder.ROW_START);
				resultStringBuilder.append(HtmlTableBuilder.getRow(propertie, value));
				resultStringBuilder.append(HtmlTableBuilder.ROW_END);
			}
			resultStringBuilder.append(HtmlTableBuilder.HEADER_END);
		}
		return resultStringBuilder.toString();
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

	public static String getDetailOs() {
		return System.getProperty("os.name").toLowerCase();
	}

	// Simple log utility
	private static void getTablePropertiesHTML(String string) {

		logger.info(string);

	}

}
