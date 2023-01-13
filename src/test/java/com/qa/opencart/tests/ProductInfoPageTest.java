package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoPageSetUp() {
		accPage= loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		
	}
	

	
	@DataProvider
	public Object[][] getProdTestData() {
		Object regData[][]=ExcelUtil.getTestData(AppConstants.PRODUCT_SHEET_NAME);
		return regData;
	}
//	public Object[][] getProductTestData() {
//		return new Object[][] {
//			{"Macbook","MacBook Pro"},
//			{"Macbook","MacBook Air"},
//			{"iMac","iMac"},
//			{"Samsung","Samsung SyncMaster 941BW"},
//		};
//	}
	
	@Test(dataProvider="getProdTestData")
	public void productHeaderTest(String searchkey, String mainProductName) {
		resultsPage=accPage.performSearch(searchkey);
		infoPage=resultsPage.selectProduct(mainProductName);
		String actHeader=infoPage.getProductHeader();
		Assert.assertEquals(actHeader,mainProductName);
	
	}
//	public void productHeaderTest(String searchkey, String mainProductName) {
//		resultsPage=accPage.performSearch(searchkey);
//		infoPage=resultsPage.selectProduct(mainProductName);
//		String actHeader=infoPage.getProductHeader();
//		Assert.assertEquals(actHeader,mainProductName);
//	}
	
	
	@DataProvider
	public Object[][] getProductImageTestData() {
		return new Object[][] {
			{"Macbook","MacBook Pro",4},
			{"Macbook","MacBook Air",4},
			{"iMac","iMac",3},
			{"Samsung","Samsung SyncMaster 941BW",1},
		};
	}
	
	
	@Test(dataProvider="getProductImageTestData")
	public void productImagesTest(String searchkey, String mainProductName, int imageCount) {
		resultsPage=accPage.performSearch(searchkey);
		infoPage=resultsPage.selectProduct(mainProductName);
		int actImagesCount=infoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount,imageCount);
	}
	
	@Test
	public void getMetaDataTest() {
		resultsPage=accPage.performSearch("Macbook");
		infoPage=resultsPage.selectProduct("MacBook Pro");
		Map<String, String>actProductMap=infoPage.getProductInformation();
		softAssert.assertEquals(actProductMap.get("Brand"),"Apple");
		softAssert.assertEquals(actProductMap.get("Reward Points"),"800");
		softAssert.assertAll();
		
		
				
	}
}
