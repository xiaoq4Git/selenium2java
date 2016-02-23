package com.xiaoq.router.LAN;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class LanManger {
	public static final String LAN_IP = "http://192.168.100.1";
	public static final String LAN_EDIT_LINK = "//table[@id='Result']//tr/td[3]/a";
	public static final String LAN_DELE_LINK = "//table[@id='Result']//tr/td[4]/a";
	public int count = 1;
	List<WebElement> lanEditList = new ArrayList<WebElement>();
	List<WebElement> lanDelList = new ArrayList<WebElement>();
	WebDriver driver;

	@Test
	public void ExLanEdit2Del() throws InterruptedException {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();

		driver.get(LAN_IP);
		Thread.sleep(3 * 1000);

		WebElement query = driver.findElement(By.id("lpwd"));
		query.sendKeys("admin");

		WebElement btn = driver.findElement(By.id("loginbtn"));
		btn.click();
		Thread.sleep(2 * 1000);

		driver.get(LAN_IP + "/html/lanlist.asp");
		Thread.sleep(2 * 1000);

		lanEditList = driver.findElements(By.xpath(LAN_EDIT_LINK));

		for (int i = 2; i <= lanEditList.size(); i++) {
			driver.findElement(By.xpath("//table[@id='Result']//tr[" + i
					+ "]/td[3]/a")).click();
			Thread.sleep(1000);

			WebElement lanAddr = driver.findElement(By.id("lanip"));
			lanAddr.clear();
			lanAddr.sendKeys("10.0.1" + count + ".1");

			WebElement dhcpEnable2 = driver.findElement(By.id("enabledhcp"));
			if(!dhcpEnable2.isSelected()){
				dhcpEnable2.click();
				Thread.sleep(5 * 100);
			}

			WebElement dhcpStartAddr = driver.findElement(By.id("startip"));
			dhcpStartAddr.clear();
			dhcpStartAddr.sendKeys("10.0.1" + count + ".10");

			WebElement dhcpEndAddr = driver.findElement(By.id("endip"));
			dhcpEndAddr.clear();
			dhcpEndAddr.sendKeys("10.0.1" + count + ".250");
			Thread.sleep(5 * 100);

			WebElement okBtn = driver.findElement(By.id("savebtn"));
			okBtn.click();
			count++;
			Thread.sleep(5 * 100);
		}
	}
	
	@AfterClass
	public void tearDown() throws InterruptedException{
		lanDelList = driver.findElements(By.xpath(LAN_DELE_LINK));
		for (int i = 0; i < lanDelList.size(); i++) {
			driver.findElement(By.xpath("//table[@id='Result']//tr[2]/td[4]/a")).click();
			Thread.sleep(2000);
		}
		Assert.assertFalse(isWebElementExist(driver, By.xpath("//table[@id='Result']//tr[2]/td[4]/a")));
		
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
