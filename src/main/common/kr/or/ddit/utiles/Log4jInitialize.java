package kr.or.ddit.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInitialize {
	public static void init(){
		URL log4jURL =  Log4jInitialize.class.getResource("/kr/or/ddit/log4j/config/log4j.properties");
		
		Properties properties = new Properties();
		
		try {
			properties.load(new FileInputStream(new File(log4jURL.toURI()))); // 이렇게 하면 읽어온다.
			PropertyConfigurator.configure(properties);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
