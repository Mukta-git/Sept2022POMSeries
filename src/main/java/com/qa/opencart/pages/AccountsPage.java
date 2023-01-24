 package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class AccountsPage {
	
	
	WebDriver driver;
	private ElementUtil eleUtil;
	
	private By search= By.name("search");
	private By searchIcon= By.xpath(" //div[@id='search']//button");
	private By logoutLink= By.linkText("Logout");
	private By accHeaders=By.xpath(" //div[@id='content']//h2");
	
	public AccountsPage(WebDriver driver) {
		this.driver=driver;
		eleUtil= new ElementUtil(driver);
	}
	
	
	public String getAccPageTitle() {
		return eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
	}
	
	public String getAccPageUrl() {
		return eleUtil.waitForUrlContains(AppConstants.ACCOUNTS_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
	}
	
	public boolean isSearchExists() {
		return eleUtil.waitForElementVisible(search, TimeUtil.DEFAULT_TIME_OUT).isDisplayed();
	}
	
	
	public boolean isLogoutExists() {
		return eleUtil.doIsDisplayed(logoutLink);
	}

	
	public List<String> getAccountsPageSectionHeaders() {
	List<WebElement> secHeaderList=eleUtil.waitForElementsVisible(accHeaders, TimeUtil.DEFAULT_TIME_OUT);
	List<String>secHeaderValueList=new ArrayList<String>();
	
	for(WebElement e: secHeaderList) {
		String text= e.getText();
		secHeaderValueList.add(text);
	}
	return secHeaderValueList;
	}


	public ProductResultsPage performSearch(String productName) {
		System.out.println("Product search for" +productName);
		if(isSearchExists()) {
			eleUtil.dosendKeys(search, productName);
			eleUtil.doclick(searchIcon);
			return new ProductResultsPage(driver);
		}
		return null;
		
	}


}
