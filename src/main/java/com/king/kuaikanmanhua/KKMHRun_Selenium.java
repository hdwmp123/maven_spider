package com.king.kuaikanmanhua;

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
* @Title: KKMHRun_Selenium.java 
* @Package com.king.kuaikanmanhua 
* @Description: 速度慢
* @author hdwmp123@163.com
* @date 2014-12-18 下午5:22:50 
* @version V1.0
 */
public class KKMHRun_Selenium {
	static Logger LOG = BeanUtail.getLOG(KKMHRun_Selenium.class);
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",GlobalContext.CHROME_DRIVER);
		runWeb();
	}

	private static void runWeb() {
		WebDriver driver = new ChromeDriver();
		int pageIndex = 1;
		String url = "http://www.kuaikanmanhua.com/comics/%s";
		int imgCount = 0;
		String topic_name = null;
		String comic_name = null;
		String date = null;
		String author = null;
		String imgUrl = null;
		//
		String dirBase = GlobalContext.DIR_KUAI_KAN_MAN_HUA;
		String tempDir = null;
		while (true) {
			driver.get(String.format(url, pageIndex));
			pageIndex++;
			//
			topic_name = driver.findElement(By.className("topic-name")).getText();
			comic_name = driver.findElement(By.className("comic-name")).getText();
			date = driver.findElement(By.className("date")).getText();
			author = driver.findElement(By.className("author")).getText();
			LOG.info(topic_name);
			LOG.info(comic_name);
			LOG.info(date);
			LOG.info(author);
			tempDir = dirBase + BeanUtail.fixFileName(topic_name) + "/" + BeanUtail.fixFileName(comic_name) + "/";
			BeanUtail.createDir(tempDir) ;
			//
			List<WebElement> imgs = driver.findElements(By.cssSelector(".comic-content > img"));
			if (imgs == null) {
				break;
			}
			imgCount = imgs.size();
			LOG.info(String.format("图片数量:%s", imgCount));
			int index = 1;
			for (WebElement img : imgs) {
				imgUrl = img.getAttribute("src");
				BeanUtail.saveWebFileT(imgUrl, tempDir, index);
				index ++;
			}
			LOG.info("---------------------------------------");
		}
		driver.quit();
	}

}
