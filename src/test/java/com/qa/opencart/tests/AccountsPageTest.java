package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.AppErrors;

public class AccountsPageTest extends BaseTest{
	
	
@BeforeClass
	public void AccSetup() {
	accPage= loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
}


@Test(priority=1)
public void accPageTitleTest() {
	String actTitle=accPage.getAccPageTitle();
	Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE, AppErrors.NO_TITLE_MATCHED);
}


@Test(priority=2)
public void accPageUrlTest() {
	String actUrl=accPage.getAccPageUrl();
	Assert.assertTrue(actUrl.contains(AppConstants.ACCOUNTS_PAGE_FRACTION_URL), AppErrors.NO_URL_MATCHED);
}

@Test(priority=3)
public void searchLinkExistsTest() {
	Assert.assertTrue(accPage.isSearchExists());
}

@Test(priority=4)
public void logoutLinkExistsTest() {
	Assert.assertTrue(accPage.isLogoutExists());
}

@Test
public void accPageHeaderTest() {
	List<String>actHeaderList= accPage.getAccountsPageSectionHeaders();
	Assert.assertEquals(actHeaderList, AppConstants.EXP_ACCOUNTS_PAGE_HEADER_LIST);
}

@DataProvider
public Object[][] getProductName() {
	return new Object[][] {
		{"Macbook"},
		{"iMac"},
		{"Samsung"}
	};
}

//TDD
@Test(dataProvider="getProductName")
public void productSearchTest(String productName) {
	//String productName= "Macbook";
	resultsPage=accPage.performSearch(productName);
	String actTitle=resultsPage.getSearchPageTitle(productName);
	System.out.println("Actual Title contains "+productName);
	softAssert.assertEquals(actTitle, AppConstants.RESULTS_PAGE_TITLE+" "+productName);
	Assert.assertTrue(resultsPage.getSearchProductCount()>0);
}
	
}
