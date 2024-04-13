package com.vhp.code.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	//test jenkins
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
