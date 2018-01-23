package priv.linjb.common.util.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {

	public static int num=5;
	public static int getInt(){
		
		return num;
	}
	public static void main(String[] args) {
		Logger LOGGER = Logger.getLogger(Log.class);
		PropertyConfigurator.configure(".\\config\\log4j.properties");
		
		
		LOGGER.info("test:" + getInt());
		
	}
}
