package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ProductInfoPage {
			
		private WebDriver driver;
		private ElementUtil eleUti;
		
		private By productHeader = By.cssSelector("div#content h1");
		private By productImages = By.cssSelector("a.thumbnail");	
		private By productMetaData = By.xpath("(//div[@class='col-sm-4']//ul[@class='list-unstyled'])[position()=1]/li");
		private By productPriceData = By.xpath("(//div[@class='col-sm-4']//ul[@class='list-unstyled'])[position()=2]/li");
		
		private Map<String, String> productMap;
		
		public ProductInfoPage(WebDriver driver) {
			this.driver = driver;
			eleUti = new ElementUtil(driver);
		}
		
		public String getProductHeader() {
			return eleUti.dogetElementText(productHeader);
		}
		
		public int getProductImagesCount() {
			return eleUti.waitForElementsVisible(productImages, TimeUtil.DEFAULT_TIME_OUT).size();
		}
		
		
		public Map<String, String> getProductInformation() {
			productMap = new HashMap<String, String>();
			//productMap = new LinkedMap<String, String>(); ordered list
			//productMap = new TreeMap<String, String>(); alphabetical order
			
			getProductMetaData();
			getProductPriceData();
			System.out.println(productMap);
			return productMap;
		}
		
		private void getProductMetaData() {
			List<WebElement> metaDataList=eleUti.getElements(productMetaData);
			System.out.println(metaDataList.size());
			for(WebElement e: metaDataList) {
				String meta=e.getText();
				String metaData[]=meta.split(":");
				String metaKey=metaData[0].trim();
				String metaValue=metaData[1].trim();
				productMap.put(metaKey, metaValue);
				
			}
			
		}
		private void getProductPriceData() {
			List<WebElement> metaPriceList=eleUti.getElements(productMetaData);
			System.out.println(metaPriceList.size());
			String price = metaPriceList.get(0).getText().trim();
			String ExTaxPrice = metaPriceList.get(1).getText().trim();
			productMap.put("actualPrice", price);
			productMap.put("actualExtaxPrice", ExTaxPrice);
		}

}
