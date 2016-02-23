package com.xiaoq.router.LAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class ExLanNetwork {
	public static final String LAN_IP = "http://192.168.100.1";
	public static final String EXLAN1_IP = "192.168.101.1";
	
	WebDriver driver;
	
	@Test
	public void testExLan() throws InterruptedException{
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		
		driver.get(LAN_IP);
		
		WebElement query = driver.findElement(By.id("lpwd"));
		query.sendKeys("admin");
		
		WebElement btn = driver.findElement(By.id("loginbtn"));
		btn.click();
		Thread.sleep(3*1000); 
		
		driver.get(LAN_IP + "/html/lanlist.asp");
		Thread.sleep(3*1000);
		
		WebElement addBtn = driver.findElement(By.id("addnew"));
		addBtn.click();
		Thread.sleep(1*1000);
		
		WebElement lanAddr = driver.findElement(By.id("lanip"));
		lanAddr.clear();
		lanAddr.sendKeys(EXLAN1_IP);
		
		WebElement maskAddr = driver.findElement(By.id("netmask"));
		maskAddr.clear();
		maskAddr.sendKeys("255.255.255.0");
		
		WebElement dhcpEnable2 = driver.findElement(By.id("enabledhcp"));
		dhcpEnable2.click();
		Thread.sleep(1*1000);
		
		WebElement dhcpStartAddr = driver.findElement(By.id("startip"));
		dhcpStartAddr.clear();
		dhcpStartAddr.sendKeys("192.168.101.10");
		
		WebElement dhcpEndAddr = driver.findElement(By.id("endip"));
		dhcpEndAddr.clear();
		dhcpEndAddr.sendKeys("192.168.101.250");
		
		WebElement okBtn = driver.findElement(By.id("savebtn"));
		okBtn.click();
		Thread.sleep(1*1000);
		
		driver.quit();
	}
	
	@AfterClass
	public void clearDown() throws IOException{
		Process p = Runtime.getRuntime().exec("cmd /c ping " + EXLAN1_IP);
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		StringBuffer sb = new StringBuffer();
		
		while((line = br.readLine()) != null){
			sb.append(line);
		}
		
		Assert.assertTrue(sb.toString().contains("×Ö½Ú=32"));
		
		if(driver != null){
			driver.quit();
		}
	}
}
