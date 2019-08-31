package com.db.util;

public class AwbInfoUtil {
    public static final String hearder_info_sms = "select AWB_ID,COURIER_NAME,TRACKING_ID,CUST_ORDER_NO,SMS_STAUS_CODE,DELIVER_PHONE_NO from OMS_AWB_HEADER_INFO where SMS_STAUS_CODE='3' and status='S'";
	
    public static final String redisHost = "localhost";
    public static final Integer redisPort = 6379;
   }
