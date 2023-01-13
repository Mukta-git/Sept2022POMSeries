package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	//Page class - classic example of encapsulation of java
	
		private WebDriver driver;
		private ElementUtil eleUtil;
		
		//private by locators
		
		
		private By emailId= By.id("input-email");
		private By password= By.id("input-password");	
		private By loginBtn= By.xpath("//input[@value='Login']");
		
		private By forgotPwdLink=By.linkText("Forgotten Password");
		private By registerLink=By.linkText("Register");
	//	private By logoutLink=By.linkText("Logout");
		
		// Page constructor
		public LoginPage(WebDriver driver) {
			this.driver=driver;
			eleUtil= new ElementUtil(driver);
		}
		
		// Page actions
		@Step("getting login page title...")
		public String getLoginPageTitle() {
			return eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_TIME_OUT);
		}
		
		public String getLoginPageUrl() {
			return eleUtil.waitForUrlContains(AppConstants.LOGIN_PAGE_FRACTION_URL, TimeUtil.DEFAULT_TIME_OUT);
		}
		
		
		public boolean isForgotPwdLinkExists() {
			return eleUtil.doIsDisplayed(forgotPwdLink);
		}
		
		public AccountsPage doLogin(String un, String pwd) {
			System.out.println("Credentials"+ un +" "+pwd);
			eleUtil.waitForElementVisible(emailId, TimeUtil.DEFAULT_TIME_OUT).sendKeys(un);;
			eleUtil.dosendKeys(password, pwd);
			eleUtil.doclick(loginBtn);
			return new AccountsPage(driver);
		}
		
		public RegisterPage navigateToRegisterPage() {
			eleUtil.doclick(registerLink);
			return new RegisterPage(driver);
		}
		
}



