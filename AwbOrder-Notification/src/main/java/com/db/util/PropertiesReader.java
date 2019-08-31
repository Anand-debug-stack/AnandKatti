package com.db.util;


import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PropertiesReader {
	private static final Logger  logger = LogManager.getLogger(PropertiesReader.class.getName());
	private final static Properties configProp = new Properties();
	
	   private PropertiesReader()
	   {
	      //Private constructor to restrict new instances
		  logger.info("Accessing db.properties from resources");
	      InputStream in = this.getClass().getClassLoader().getResourceAsStream("db.properties");
	      
	      try {
	          configProp.load(in);
	          
	      } catch (Exception e) {
	    	  logger.error("Properties path is not correct"+e);
	      }
	   }
	   static{
		   new PropertiesReader();
		   logger.info("-----------------All the loaded properties---------------");
		   for(String str:PropertiesReader.getAllPropertyNames()){
			   logger.info(str);
		   }
			   
	   }	 
	 
	   public static String getProperty(String key){
	      return configProp.getProperty(key);
	   }
	    
	   private static Set<String> getAllPropertyNames(){
	      return configProp.stringPropertyNames();
	      }
	    
	   public boolean containsKey(String key){
	      return configProp.containsKey(key);
	   }

	
}
