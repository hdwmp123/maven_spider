package com.king.qiushibaike;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import com.king.util.BeanUtil;
import com.king.util.GlobalContext;
/**
 * 
* @Title: QiuShiBaiKe_Selenium.java 
* @Package com.king.qiushibaike 
* @Description: 此方式可解决请求响应为移动端问题
* @author hdwmp123@163.com
* @date 2014-12-18 下午5:23:59 
* @version V1.0
 */
public class QiuShiBaiKe_Selenium {
	static Logger LOG = BeanUtil.getLOG(QiuShiBaiKe_Selenium.class);
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",GlobalContext.CHROME_DRIVER);
		runWeb();
	}

	private static void runWeb() {
		WebDriver driver = new ChromeDriver();
		int pageIndex = 1;
		String[] urls = new String[]{
				"http://www.qiushibaike.com/8hr/page/%s",//热门
				"http://www.qiushibaike.com/hot/page/%s",//24小时内
				"http://www.qiushibaike.com/week/page/%s",//7天内
				"http://www.qiushibaike.com/month/page/%s",//30天内
				"http://www.qiushibaike.com/imgrank/page/%s",//真相-硬菜
				"http://www.qiushibaike.com/pic/page/%s",//真相-时令
				"http://www.qiushibaike.com/late/page/%s",//最新
				"http://www.qiushibaike.com/history/page/%s",//穿越
		};
		int divCount = 0;
		String author = null;
		String imgUrl = null;
		//
		String dirBase = GlobalContext.DIR_QIU_SHI_BAI_KE;
		String dirBaseAll = GlobalContext.DIR_QIU_SHI_BAI_KE_ALL;
		String tempDir = null;
		for (String url : urls) {
			while (true) {
				driver.get(String.format(url, pageIndex));
				pageIndex++;
				//
				List<WebElement> divs = driver.findElements(By.cssSelector("div.article"));
				if (divs == null||divs.size()==0) {
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
						tempDir = dirBase + BeanUtil.fixFileName(author) + "/";
						BeanUtil.createDir(tempDir);
						BeanUtil.createDir(dirBaseAll);
						imgUrl =  imgs.get(0).getAttribute("src");
						BeanUtil.saveWebFileT(imgUrl, tempDir, "1", "jpg");
						BeanUtil.saveWebFileT(imgUrl, dirBaseAll, "-1", "jpg");
					}
				}
				LOG.info("---------------------------------------");
				try {
					Thread.sleep(500 * 3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		driver.quit();
	}

}
