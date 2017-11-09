package com.king.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import com.king.util.BeanUtil;
import com.king.util.GlobalContext;

public class Demo_Selenium {
	static Logger LOG = BeanUtil.getLOG(Demo_Selenium.class);

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",GlobalContext.CHROME_DRIVER);
		run();
	}

	private static void run() {
		WebDriver driver = new ChromeDriver();
		String url = "http://www.baidu.com";
		//
		driver.get(url);
		//
		String js = "var _hmt = _hmt || [];"+
					"(function() {"+
					"  var hm = document.createElement('script');"+
					"  hm.src = '//hm.baidu.com/hm.js?fe2c2bea77a7057d39250d05528d64de';"+
					"  var s = document.getElementsByTagName('script')[0]; "+
					"  s.parentNode.insertBefore(hm, s);"+
					"})();"+
					"document.getElementById('kw').value='hello';"+ 
					"document.getElementById('one').innerHTML='one';";
		((JavascriptExecutor)driver).executeScript(js);
		//
		String kw = driver.findElement(By.id("kw")).getAttribute("value");
		String one = driver.findElement(By.id("one")).getText();
		LOG.info(kw);
		LOG.info(one);
		LOG.info("---------------------------------------");
		driver.quit();
	}
}
