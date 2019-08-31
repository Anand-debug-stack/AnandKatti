package com.crm.redis.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.db.util.AwbInfoUtil;
import com.db.util.GetDBConnection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class RedisDAO {
	private static Logger LOGGER = Logger.getLogger(GetDBConnection.class);

	Connection omsConn;
	PreparedStatement getHeaderInfo = null;
	// the jedis connection pool..
	private static JedisPool pool = null;

	public RedisDAO(Connection omsConn) throws SQLException {
		this.getHeaderInfo = omsConn.prepareStatement(AwbInfoUtil.hearder_info_sms);
		this.pool = new JedisPool(AwbInfoUtil.redisHost, AwbInfoUtil.redisPort);
	}

	public String addHash() {
LOGGER.info("logger methodis executing");
		String key = "Header";

		try {
			ResultSet headerinfo = getHeaderInfo.executeQuery();
			while (headerinfo.next()) {
                Integer AWB_ID = headerinfo.getInt("AWB_ID");

				String courier_name = headerinfo.getString("COURIER_NAME");
				String trackingId = headerinfo.getString("TRACKING_ID");
				LOGGER.info("logger methodis executing");
				Jedis jedis = pool.getResource();
				
				try {
					Map<String, String> map = new HashMap<>();
					map.put("AWB_ID", AWB_ID.toString());
					map.put("COURIER_NAME", courier_name);
					map.put("TRACKING_ID", trackingId);
					jedis.hmset(key, map);
				
					
					System.out.println(jedis.hgetAll(key));
					System.out.println(jedis.hmget(key,"AWB_ID","COURIER_NAME","TRACKING_ID"));
					
					
					
					/* Map<String, String> retrieveMap = jedis.hgetAll(key);
			            for (String keyMap : retrieveMap.keySet()) {
			                System.out.println(keyMap + " " + retrieveMap.get(keyMap));
			            }*/
					
					 
					
					key = key + AWB_ID.toString();
					LOGGER.info("inserting data into the redis");

				} catch (JedisException e) {
					// if something wrong happen, return it back to the pool
					if (null != jedis) {
						pool.returnBrokenResource(jedis);
						jedis = null;
					}
				} finally {
					/// it's important to return the Jedis instance to the pool
					/// once you've finished using it
					if (null != jedis)
						pool.returnResource(jedis);
				}

			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error("exception in the oracle database" + e);
			e.printStackTrace();
		}
		return key;
	}

	
	
	public void Upadate(String Key) {
		Jedis jedis = pool.getResource();

		try {
			// save to redis
			Long update = jedis.hset(Key, "AWB_ID", "708");
			if (update == 1) {
				LOGGER.info("upadtes successfully");
			}

		} catch (JedisException e) {
			// if something wrong happen, return it back to the pool
			if (null != jedis) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			/// it's important to return the Jedis instance to the pool once
			/// you've finished using it
			if (null != jedis)
				pool.returnResource(jedis);
		}

	}

	public void delete(String Key) {

		Jedis jedis = pool.getResource();

		try {
			jedis.del(Key);
			LOGGER.info("Key is deleted");

		} catch (JedisException e) {
			// if something wrong happen, return it back to the pool
			if (null != jedis) {
				pool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			/// it's important to return the Jedis instance to the pool once
			/// you've finished using it
			if (null != jedis)
				pool.returnResource(jedis);
		}

	}

}

/*
 * LOGGER.info("JEDIS IS completd");
 * 
 * //after saving the data, lets retrieve them to be sure that it has really
 * added in redis String retrieveMap = jedis.hget(key+AWB_ID.toString(),
 * "COURIER_NAME"); System.out.println(retrieveMap); Gson g = new Gson();
 * Tracking p = g.fromJson(retrieveMap, Tracking.class);
 * 
 * System.out.println(p.getCOURIER_NAME());
 * 
 * 
 * 
 * 
 * System.out.println( jedis.hgetAll(key+AWB_ID.toString()));
 * 
 * 
 * 
 * System.out.println(retrieveMap); /* for (String keyMap :
 * retrieveMap.keySet()) { System.out.println(keyMap + " " +
 * retrieveMap.get(keyMap)); }
 */
