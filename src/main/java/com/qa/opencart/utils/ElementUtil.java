package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	//create webelement
	
	
	public ElementUtil(WebDriver driver) {
		this.driver=driver;
		jsUtil=new JavaScriptUtil(driver);
	}
	
	public WebElement getElement(By locator) {
		WebElement ele=driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(ele);	
		}
		
		return ele;
		
		
	}
	
	public boolean doIsDisplayed(By locator) {
		return driver.findElement(locator).isDisplayed();
		
		
	}
	public void dosendKeys(By locator,String value) {
		WebElement ele=getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}
	
	public void doclick(By locator ) {
		getElement(locator).click();
	}
	
	public String dogetElementText(By locator) {
		return getElement(locator).getText();
	}
	
	public String geAttributeValue(By locator, String name) {
		return getElement(locator).getAttribute(name);
	}
	
public List<WebElement> getElements(By locator) {
		
		return driver.findElements(locator);
	}
	
	public int totalElementCount(By locator) {
		return getElements(locator).size();
	}
	
	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList= driver.findElements(locator);
		List<String> eleTextList= new ArrayList<String>();
		
			for(WebElement e: eleList) {
			String text= e.getText();
			eleTextList.add(text);
			//System.out.println(text);
			}
			return eleTextList;
	}
	
	public void doClickOnElement(By locator, String linkText) {
		List<WebElement> linksList = driver.findElements(locator);
		System.out.println("total links : " + linksList.size());

		for (WebElement e : linksList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(linkText)) {
				e.click();
				break;
			}
		}
	}

//Actions class
	public void doActionSendKeys(By locator, String value) {
		Actions Act=new Actions(driver);
		Act.sendKeys(getElement(locator),value ).build().perform();
	}
	
	public void doActionClick(By locator) {
		Actions Act=new Actions(driver);
		Act.click(getElement(locator)).click().build().perform();
		
	}

//******* Select Operations for Drop Downs******

public void doSelectDropDownByIndex(By locator, int index) {
	Select select = new Select(getElement(locator));
	select.selectByIndex(index);
}

public void doSelectDropDownByValue(By locator, String value) {
	Select select = new Select(getElement(locator));
	select.selectByValue(value);
}

public void doSelectDropDownByVisibleText(By locator, String value) {
	Select select = new Select(getElement(locator));
	select.selectByVisibleText(value);
}
public void doSelectDropDownValue(By locator, String value) {
	
	
	Select select= new Select(getElement(locator));
	
	List<WebElement> optionList=select.getOptions();
	System.out.println(optionList.size());
	
	for(WebElement e: optionList) {
		String text=e.getText();
		System.out.println(text);
		if(text.equals(value)) {
			e.click();
			break;
		}
	}
}

public boolean isSingleElementExist(By locator) {
	int count=getElements(locator).size();
	if(count==1) {
		return true;
	}
	else return false;
}

public void selectSubMenu(String htmltag, String parentMenu, String childMenu) throws InterruptedException {
	
	By parentMenuLocator=By.xpath("//"+htmltag+"[text()='"+parentMenu+"']");
	By childmenuLocator= By.xpath("//"+htmltag+"[text()='"+childMenu+"']");
	
	WebElement parentMenuElement= getElement(parentMenuLocator);
	Actions act= new Actions(driver);
	act.moveToElement(parentMenuElement).build().perform();
	Thread.sleep(2000);
	
	doclick(childmenuLocator);
	
	
}
public boolean isMultipleElementExist(By locator) {
	int count=getElements(locator).size();
	if(count>1) {
		return true;
	}
	else return false;
}

public boolean isMultipleElementExist(By locator, int expcount) {
	int count=getElements(locator).size();
	if(count==expcount) {
		return true;
	}
	else return false;
}

public boolean isTwoElementExist(By locator) {
	int count=getElements(locator).size();
	if(count==2) {
		return true;
	}
	else return false;
}


//*********************************************Wait Utils********************




public String waitForTitleContains(String titleFractionValue, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	if(wait.until(ExpectedConditions.titleContains(titleFractionValue))) {
		return driver.getTitle();
	}
	else {
		System.out.println("expected title is not visible...");
		return null;
	}
}

public String waitForTitleIs(String titleVal, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	if(wait.until(ExpectedConditions.titleIs(titleVal))) {
		return driver.getTitle();
	}
	else {
		System.out.println("expected title is not visible...");
		return null;
	}
}


public String waitForUrlContains(String urlFractionValue, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	if(wait.until(ExpectedConditions.urlContains(urlFractionValue))) {
		return driver.getCurrentUrl();
	}
	else {
		System.out.println("expected url is not visible...");
		return null;
	}
}

public String waitForUrlIs(String urlValue, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	if(wait.until(ExpectedConditions.urlToBe(urlValue))) {
		return driver.getCurrentUrl();
	}
	else {
		System.out.println("expected url is not visible...");
		return null;
	}
}

//FW
public Alert waitForAlertPresentAndSwitchWithFluentWait(int timeOut, int intervalTime) {
	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			.withTimeout(Duration.ofSeconds(timeOut))
			.pollingEvery(Duration.ofSeconds(intervalTime))
			.ignoring(NoAlertPresentException.class)
			.withMessage("Alert not found on the page....");
	
	return wait.until(ExpectedConditions.alertIsPresent());
}

//WW
public Alert waitForAlertPresentAndSwitch(int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	return wait.until(ExpectedConditions.alertIsPresent());
}



public String getAlertText(int timeOut) {
	return waitForAlertPresentAndSwitch(timeOut).getText();
}

public void acceptAlert(int timeOut) {
	waitForAlertPresentAndSwitch(timeOut).accept();
}

public void dismissAlert(int timeOut) {
	waitForAlertPresentAndSwitch(timeOut).dismiss();
}

public void alertSendKeys(int timeOut, String value) {
	waitForAlertPresentAndSwitch(timeOut).sendKeys(value);
}


public void waitForFramePresentAndSwitch(int frameIndex, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
}

public void waitForFramePresentAndSwitch(By frameLocator, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
}

public void waitForFramePresentAndSwitch(WebElement frameElement, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
}

public void waitForFramePresentAndSwitch(String nameOrID, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));
}



/**
 * An expectation for checking that an element is present on the DOM of a page. 
 * This does not necessarily mean that the element is visible.
 * @param locator
 * @param timeOut
 * @return
 */
public WebElement waitForElementPresence(By locator, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
}

/**
 * An expectation for checking that an element is present on the DOM of a page and visible. 
 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
 * @param locator
 * @param timeOut
 * @return
 */
public WebElement waitForElementVisible(By locator, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
}

/**
 * An expectation for checking that all elements present on the web page that match the locator are visible. 
 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
 * @param locator
 * @param timeOut
 * default interval time = 500 ms
 * @return
 */
public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
}


/**
 * An expectation for checking that all elements present on the web page that match the locator are visible. 
 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
 * @param locator
 * @param timeOut
 * @param intervalTime
 * @return
 */
public List<WebElement> waitForElementsVisible(By locator, int timeOut, long intervalTime) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
	return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
}




/**
 * An expectation for checking that there is at least one element present on a web page.
 * @param locator
 * @param timeOut
 * @return
 */
public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
}

/**
 * An expectation for checking an element is visible and enabled such that you can click it.
 * @param locator
 * @param timeOut
 */
public void clickElementWhenReady(By locator, int timeOut) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	 wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
}


/**
 * An expectation for checking an element is visible and enabled such that you can click it.
 * @param locator
 * @param timeOut
 */
public void clickElementWhenReady(By locator, int timeOut, int intervalTime) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
	 wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
}


public WebElement waitForElementToBeVisibleWithFluentWait(By locator, int timeOut, int intervalTime) {
	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			.withTimeout(Duration.ofSeconds(timeOut))
			.pollingEvery(Duration.ofSeconds(intervalTime))
			.ignoring(ElementNotInteractableException.class)
			.ignoring(NoSuchElementException.class)
			.ignoring(StaleElementReferenceException.class)
			.withMessage("Element not found on the page....");
	
	return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	
}


public WebElement waitForElementToBeVisible(By locator, int timeOut, int intervalTime) {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(intervalTime));
	wait.ignoring(NoSuchElementException.class)
		.ignoring(ElementNotInteractableException.class)
		.withMessage("Element not found on the page....");
			
	
	return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	
}


//*******************Custom Waits**********************//
public WebElement retryingElement(By locator, int timeOut) {
	WebElement element = null;
	int attempts = 0;
	while (attempts < timeOut) {
		try {
			element = getElement(locator);
			System.out.println("element is found in attempt: " + attempts);
			break;
		} catch (NoSuchElementException e) {
			System.out.println("element is not found in attempt : " + attempts + " for " + locator);
			TimeUtil.applyWait(500);
		}
		
		attempts++;
	}
	
	if(element == null) {
		System.out.println("element is not found....tried for : " + timeOut + " secs " + 
				" with the interval of 500 millisecs");
		throw new FrameworkException("TimeOutException");
	}
	
	return element;

}


public WebElement retryingElement(By locator, int timeOut, int intervalTime) {
	WebElement element = null;
	int attempts = 0;
	while (attempts < timeOut) {
		try {
			element = getElement(locator);
			System.out.println("element is found in attempt: " + attempts);
			break;
		} catch (NoSuchElementException e) {
			System.out.println("element is not found in attempt : " + attempts + " for " + locator);
			TimeUtil.applyWait(intervalTime);
		}
		
		attempts++;
	}
	
	if(element == null) {
		System.out.println("element is not found....tried for : " + timeOut + " secs " + 
				" with the interval of "+ intervalTime  + " secs");
		throw new FrameworkException("TimeOutException");
	}
	
	return element;

}


public void waitForPageLoad(int timeOut) {

	long endTime = System.currentTimeMillis() + timeOut;

	while (System.currentTimeMillis() < endTime) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String pageState = js.executeScript("return document.readyState").toString();
		if (pageState.equals("complete")) {
			System.out.println("page DOM is fully loaded now.....");
			break;
		}
		
	}

}


}