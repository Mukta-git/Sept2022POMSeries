package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class ProductResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	By searchProducts=By.cssSelector("div.product-layout");

	public ProductResultsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil=new ElementUtil(driver);
	}
	
	public String getSearchPageTitle(String productName) {
		return eleUtil.waitForTitleContains(productName, TimeUtil.DEFAULT_TIME_OUT);
		
	}
	
	public int getSearchProductCount() {
		return eleUtil.waitForElementsVisible(searchProducts, TimeUtil.DEFAULT_TIME_OUT).size();
	}
	
	public ProductInfoPage selectProduct(String mainProductName) {
		System.out.println("main product name : " + mainProductName);
		eleUtil.doclick(By.linkText(mainProductName));
		return new ProductInfoPage(driver);
	}
	
}
