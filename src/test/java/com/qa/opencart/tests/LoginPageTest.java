package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.AppErrors;

public class LoginPageTest extends BaseTest {
	
	@Test(priority=1)
	public void loginPageTitleTest() {
		String actTitle=loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE, AppErrors.NO_TITLE_MATCHED);
	}
	
	
	@Test(priority=2)
	public void loginPageUrlTest() {
		String actUrl=loginPage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_FRACTION_URL), AppErrors.NO_URL_MATCHED);
	}
	
	@Test(priority=3)
	public void forgotPwdLinkExistsTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExists());
	}
	
	@Test(priority=4)
	public void loginTest() {
		
		accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutExists(), AppErrors.LOGIN_UNSUCCESSFUL);
	}

}
