package com.xiaoq.router.LAN;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class MaxLanNumber {
	public static final String LAN_IP = "http://192.168.100.1";
	WebDriver driver;

	@Test
	public void testMaxLanNumber() throws InterruptedException {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();

		driver.get(LAN_IP);
		Thread.sleep(2 * 1000);

		WebElement query = driver.findElement(By.id("lpwd"));
		query.sendKeys("admin");

		WebElement btn = driver.findElement(By.id("loginbtn"));
		btn.click();
		Thread.sleep(2 * 1000);

		driver.get(LAN_IP + "/html/lanlist.asp");
		Thread.sleep(2 * 1000);

		for (int i = 2; i <= 7; i++) {
			WebElement addBtn = driver.findElement(By.id("addnew"));
			addBtn.click();
			Thread.sleep(1 * 1000);

			WebElement lanAddr = driver.findElement(By.id("lanip"));
			lanAddr.clear();
			lanAddr.sendKeys("192.168.10" + i + ".1");
			
			WebElement maskAddr = driver.findElement(By.id("netmask"));
			maskAddr.clear();
			maskAddr.sendKeys("255.255.255.0");
			
			WebElement okBtn = driver.findElement(By.id("savebtn"));
			okBtn.click();
			Thread.sleep(1*1000);
		}
		
		Assert.assertFalse(isWebElementExist(driver, By.id("addnew")));
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


