package com.xiaoq.router.WAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class StaticWan {
	public static final String WAN1_LINK = "//table[@id='Result']/tbody/tr[1]/td/a";
	public static final String LAN_IP = "192.168.100.1";

	WebDriver driver;

	@BeforeTest
	public void tearUp() throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec("cmd /c ping www.baidu.com");
		p.waitFor();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line;
		StringBuffer sb = new StringBuffer();

		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		Assert.assertTrue(sb.toString().contains("×Ö½Ú=32"));
	}

	@Test
	public void testStaticWAN() throws InterruptedException {

		driver = new FirefoxDriver();
		driver.manage().window().maximize();

		driver.get("http://" + LAN_IP);

		WebElement query = driver.findElement(By.id("lpwd"));
		query.sendKeys("admin");

		WebElement btn = driver.findElement(By.id("loginbtn"));
		btn.click();
		Thread.sleep(3 * 1000);

		driver.get("http://" + LAN_IP + "/html/wanlist.asp");
		Thread.sleep(3 * 1000);

		WebElement edit = driver.findElement(By.xpath(WAN1_LINK));
		edit.click();
		Thread.sleep(5 * 100);
		
		Select type = new Select(driver.findElement(By.id("linetype")));
		type.selectByValue("static");
		
		WebElement ipaddr = driver.findElement(By.id("ip1"));
		ipaddr.clear();
		ipaddr.sendKeys("192.168.5.254");
		
		WebElement ipmask = driver.findElement(By.id("netmask"));
		ipmask.clear();
		ipmask.sendKeys("255.255.255.0");
		
		WebElement gwaddr = driver.findElement(By.id("ip2"));
		gwaddr.clear();
		gwaddr.sendKeys("192.168.5.1");
		
		WebElement dns1 = driver.findElement(By.id("d1"));
		dns1.clear();
		dns1.sendKeys("119.6.6.6");
		
		WebElement dns2 = driver.findElement(By.id("d2"));
		dns2.clear();
		dns2.sendKeys("114.114.114.114");
		
		WebElement okbtn = driver.findElement(By.id("savebtn"));
		okbtn.click();
		Thread.sleep(3000);
	}
	
	@AfterClass
	public void tearDown() throws IOException, InterruptedException{
		Process p = Runtime.getRuntime().exec("cmd /c ping www.baidu.com");
		p.waitFor();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line;
		StringBuffer sb = new StringBuffer();

		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		
		if(driver != null){
			driver.quit();
		}
		
		Assert.assertTrue(sb.toString().contains("×Ö½Ú=32"));
	}
}
