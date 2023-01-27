package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public WebDriver initDriver(Properties prop) {
		String browserName= prop.getProperty("browser").trim();
		//highlight=prop.getProperty("highlight").trim();
		optionsManager= new OptionsManager(prop);
		
		if(browserName.equalsIgnoreCase("chrome")) {
			
			//System.setProperty("webdriver.chrome.driver", "C:\\Users\\MuktaSeth\\Downloads\\drivers\\chromedriver.exe");
			//driver=new ChromeDriver(optionsManager.getChromeOptions());
			//tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// remote execution on docker/grid/cloud
				init_remoteDriver("chrome");
			} else {
				// local execution
				//driver=new ChromeDriver(optionsManager.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}	
		
		}
		 else if (browserName.equalsIgnoreCase("firefox")) {
			// System.setProperty("webdriver.gecko.driver","C:\\Users\\MuktaSeth\\Downloads\\drivers\\GeckoDriver.exe");
				if (Boolean.parseBoolean(prop.getProperty("remote"))) {
					// remote execution on docker/grid/cloud
					init_remoteDriver("firefox");
				} else {
					// local execution
					//driver=new FirefoxDriver(optionsManager.getFirefoxOptions());
					tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				}

			}
		else if (browserName.equalsIgnoreCase("edge")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// remote execution on docker/grid/cloud
				init_remoteDriver("edge");
			} else {
				// local execution
			//	driver=new  EdgeDriver(optionsManager.getEdgeOptions());
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}

		}

		else if(browserName.equalsIgnoreCase("safari")) {
			driver=new SafariDriver();
	}
		else {
			System.out.println("Please pass right browsername"+browserName);
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		return getDriver();
		
}
	
	public Properties initProp() {
		
	 prop= new Properties();
	 FileInputStream ip=null;
	 //mvn clean install -Denv="qa"
	 
	 String envName = System.getProperty("env");
	 System.out.println("Running on ----"+ envName);
	 if(envName==null) {
		 System.out.println("No env is given, hence running on QA env");
		 try {
			ip= new FileInputStream("./src/test/resources/config/qa.config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 else {
		 try {
		 switch(envName.toLowerCase()){
			 case "qa":
			
				ip= new FileInputStream("./src/test/resources/config/qa.config.properties");
			
				 break;
			 case "prod":
				 ip= new FileInputStream("./src/test/resources/config/config.properties");
				 break;
				 default:
					 System.out.println("Please pass the right env Name");
					 break;
		 } 
		 }
		 catch (FileNotFoundException e) {
				e.printStackTrace();
			
		  }
	 }
	 try {
		prop.load(ip);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 return prop;
		
	}
	
	private void init_remoteDriver(String browserName) {

		System.out.println("Running test cases on remote grid server: " + browserName);

		if (browserName.equalsIgnoreCase("chrome")) {
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		if (browserName.equalsIgnoreCase("firefox")) {
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		if (browserName.equalsIgnoreCase("edge")) {
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}


	//get the local thread copy of the driver
		public synchronized static WebDriver getDriver() {
			return tlDriver.get();
		}
	
	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;

	}

}