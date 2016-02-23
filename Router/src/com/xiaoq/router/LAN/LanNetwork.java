package com.xiaoq.router.LAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LanNetwork {
	public static final String LAN1_LINK = "//table[@id='Result']/tbody/tr[1]/td/a";
	public static final String LAN_IP = "192.168.100.1";

	WebDriver driver;

	@BeforeTest
	public void testReset() throws IOException, InterruptedException {
		String currentIP = null;
		Process p = Runtime.getRuntime().exec("cmd /c ipconfig");
		p.waitFor();

		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		while ((line = br.readLine()) != null) {
			if (line.contains("默认网关")) {
				Matcher mat = pat.matcher(line);
				if (mat.find()) {
					currentIP = mat.group();
				}
				break;
			}
		}

		driver = new FirefoxDriver();
		driver.get("http://" + currentIP);
		Thread.sleep(2*1000);

		WebElement query = driver.findElement(By.id("lpwd"));
		query.sendKeys("admin");

		WebElement btn = driver.findElement(By.id("loginbtn"));
		btn.click();
		Thread.sleep(2 * 1000);

		driver.get("http://" + currentIP + "/html/reset.asp");
		Thread.sleep(1000);
		driver.findElement(By.id("resetbtn")).click();
		Thread.sleep(60 * 1000);
		driver.quit();
		
		Runtime.getRuntime().exec("cmd /c ipconfig/release");
		Process p1 = Runtime.getRuntime().exec("cmd /c ipconfig/renew");
		p1.waitFor();
	}

	@Test
	public void testLAN() throws InterruptedException {

		driver = new FirefoxDriver();
		driver.manage().window().maximize();

		driver.get("http://192.168.20.1");
		Thread.sleep(2*1000);

		WebElement query = driver.findElement(By.id("lpwd"));
		query.sendKeys("admin");

		WebElement btn = driver.findElement(By.id("loginbtn"));
		btn.click();
		Thread.sleep(2 * 1000);

		driver.get("http://192.168.20.1/html/lanlist.asp");
		Thread.sleep(2 * 1000);

		WebElement edit = driver.findElement(By.xpath(LAN1_LINK));
		edit.click();
		Thread.sleep(5 * 100);

		WebElement lanAddr = driver.findElement(By.id("lanip"));
		lanAddr.clear();
		lanAddr.sendKeys(LAN_IP);

		WebElement dhcpEnable = driver.findElement(By.id("enabledhcp"));
		if (!dhcpEnable.isEnabled()) {
			dhcpEnable.click();
		}

		WebElement dhcpStartAddr = driver.findElement(By.id("startip"));
		dhcpStartAddr.clear();
		dhcpStartAddr.sendKeys("192.168.100.10");

		WebElement dhcpEndAddr = driver.findElement(By.id("endip"));
		dhcpEndAddr.clear();
		dhcpEndAddr.sendKeys("192.168.100.250");

		WebElement showDNS = driver.findElement(By.id("enabledns"));
		showDNS.click();

		WebElement mainDNS = driver.findElement(By.id("SDNS1"));
		mainDNS.clear();
		mainDNS.sendKeys("119.6.6.6");

		WebElement backupDNS = driver.findElement(By.id("SDNS2"));
		backupDNS.clear();
		backupDNS.sendKeys("114.114.114.114");

		WebElement okBtn = driver.findElement(By.id("savebtn"));
		okBtn.click();
		Thread.sleep(5 * 100);

		driver.close();
	}

	@AfterClass
	public void tearDown() throws IOException, InterruptedException {
		Process p1 = Runtime.getRuntime().exec("cmd /c ipconfig/release");
		p1.waitFor();

		Process p2 = Runtime.getRuntime().exec("cmd /c ipconfig/renew");
		p2.waitFor();

		Process p3 = Runtime.getRuntime().exec("cmd /c ipconfig");
		p3.waitFor();

		BufferedReader br = new BufferedReader(new InputStreamReader(p3.getInputStream()));
		String line;
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		while ((line = br.readLine()) != null) {
			if (line.contains("默认网关")) {
				Matcher mat = pat.matcher(line);
				if (mat.find()) {
					Assert.assertEquals(mat.group(), LAN_IP);
				} else {
					Assert.assertEquals(false, "NO DHCPServer");
				}
				break;
			}
		}
	}
}
