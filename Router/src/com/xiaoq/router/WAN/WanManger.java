package com.xiaoq.router.WAN;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class WanManger {
	public static final String LAN_IP = "http://192.168.100.1";
	public static final String WAN_EDIT_LINK = "//table[@id='Result']/tbody/tr/td[3]/a";
	public static final String WAN_DELE_LINK = "//table[@id='Result']/tbody/tr/td[4]/a";
	public int count = 0;
	List<WebElement> lanEditList = new ArrayList<WebElement>();
	List<WebElement> lanDelList = new ArrayList<WebElement>();
	WebDriver driver;

	@Test
	public void ExLanEdit2Del() throws InterruptedException {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();

		driver.get(LAN_IP);
		Thread.sleep(2 * 1000);

		WebElement query = driver.findElement(By.id("lpwd"));
		query.sendKeys("admin");

		WebElement btn = driver.findElement(By.id("loginbtn"));
		btn.click();
		Thread.sleep(2 * 1000);

		driver.get(LAN_IP + "/html/wanlist.asp");
		Thread.sleep(2 * 1000);
		
		while(isWebElementExist(driver, By.xpath(WAN_DELE_LINK))){
			WebElement wanDel = driver.findElement(By.xpath(WAN_DELE_LINK));
			wanDel.click();
			count++;
			Thread.sleep(1000);
		}
		Assert.assertEquals(count, 3);
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
