package com.crm.redis;

import java.sql.Connection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.crm.redis.DAO.RedisDAO;
import com.db.util.Constant;
import com.db.util.GetDBConnection;

public class Rediscache {

	private static final Logger LOGGER = LogManager.getLogger(Rediscache.class.getName());

	public static void main(String[] args) {
		
        
		GetDBConnection getDBConnection = new GetDBConnection();
		Connection omsConn = null;
	
	try{
		// Loading OMS the DB properties
			getDBConnection.loadProperties(Constant.Database.OMS);
         // Calling for OMS DB Connection
			omsConn = getDBConnection.getConnection();
			//RedisDAO redisdao=new RedisDAO(omsConn);
			//String reidskey=redisdao.addHash();
			
			//redisdao.Upadate(reidskey);
			
			//redisdao.delete(reidskey);
			
	}catch(Exception e){
		LOGGER.error("Exception"+e);
		
	}finally {
		GetDBConnection.closeConnection(omsConn);
	}
	
	
	}
}
