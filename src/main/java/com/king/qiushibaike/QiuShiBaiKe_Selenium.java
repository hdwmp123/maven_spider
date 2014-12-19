package com.king.qiushibaike;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import com.king.util.BeanUtail;
import com.king.util.GlobalContext;
/**
 * 
* @Title: QiuShiBaiKe_Selenium.java 
* @Package com.king.qiushibaike 
* @Description: 此方式可解决移动端响应问题
* @author hdwmp123@163.com
* @date 2014-12-18 下午5:23:59 
* @version V1.0
 */
public class QiuShiBaiKe_Selenium {
	static Logger LOG = BeanUtail.getLOG(QiuShiBaiKe_Selenium.class);
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				GlobalContext.CHROME_DRIVER);
		run();
	}

	private static void run() {
		WebDriver driver = new ChromeDriver();
		int pageIndex = 1;
		//String url = "http://www.qiushibaike.com/8hr/page/%s";
		//String url = "http://www.qiushibaike.com/late/page/%s";
		String url = "http://www.qiushibaike.com/month/page/%s";
		int divCount = 0;
		String content = null;
		String author = null;
		String imgUrl = null;
		//
		String dirBase = GlobalContext.DIR_QIU_SHI_BAI_KE;
		String tempDir = null;
		boolean result ;
		while (true) {
			driver.get(String.format(url, pageIndex));
			pageIndex++;
			//
			List<WebElement> divs = driver.findElements(By.cssSelector("div.article"));
			if (divs == null) {
				break;
			}
			divCount = divs.size();
			LOG.info(String.format("笑话数量:%s", divCount));
			for (WebElement div : divs) {
				List<WebElement> imgs = div.findElements(By.cssSelector("div.thumb >a >img"));
				if(imgs != null && imgs.size() > 0){
					List<WebElement> authors = div.findElements(By.cssSelector("div.author > a"));
					if(authors != null && authors.size() > 0){
						author = authors.get(1).getText();
					}
					LOG.info(String.format("author:%s", author));
					tempDir = dirBase + BeanUtail.fixFileName(author) + "/";
					result = BeanUtail.createDir(tempDir) ;
					LOG.info(String.format("create dir:%s", result));
					imgUrl =  imgs.get(0).getAttribute("src");
					LOG.info(imgUrl);
					result = BeanUtail.saveWebFileT(imgUrl, tempDir, -1);
					LOG.info(String.format("save file:%s", result));
				}
			}
			LOG.info("---------------------------------------");
			try {
				Thread.sleep(500 * 3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		driver.quit();
	}

}
