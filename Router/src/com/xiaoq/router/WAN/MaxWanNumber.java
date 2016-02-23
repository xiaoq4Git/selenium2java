package com.xiaoq.router.WAN;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class MaxWanNumber {
	public static final String WAN_LINK = "//table[@id='Result']/tbody/tr/td/a";
	public static final String LAN_IP = "192.168.100.1";

	WebDriver driver;
	int count = 0;
	
	@Test
	public void testPPPoEWAN() throws InterruptedException {

		driver = new FirefoxDriver();
		driver.manage().window().maximize();

		driver.get("http://" + LAN_IP);
		Thread.sleep(2 * 1000);

		WebElement query = driver.findElement(By.id("lpwd"));
		query.sendKeys("admin");

		WebElement btn = driver.findElement(By.id("loginbtn"));
		btn.click();
		Thread.sleep(2 * 1000);

		driver.get("http://" + LAN_IP + "/html/wanlist.asp");
		Thread.sleep(2 * 1000);
		
		while(isWebElementExist(driver, By.id("addnew")) && count < 4){
			count++;
			WebElement addnew = driver.findElement(By.id("addnew"));
			addnew.click();
			Thread.sleep(1000);
			
			Select type = new Select(driver.findElement(By.id("linetype")));
			type.selectByValue("dhcp");
			
			WebElement okbtn = driver.findElement(By.id("savebtn"));
			okbtn.click();
			Thread.sleep(2000);
		}
		
		List<WebElement> list = driver.findElements(By.xpath(WAN_LINK));
		Assert.assertEquals(list.size(), 4);
	 }
	
	@AfterClass
	public void tearDown(){
		if(driver != null){
			driver.quit();
		}
	}
	
	private boolean isWebElementExist(WebDriver driver, By selector){
		try{
			driver.findElement(selector);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
}
