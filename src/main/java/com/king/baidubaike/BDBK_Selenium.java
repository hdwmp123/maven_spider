package com.king.baidubaike;

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
* @Title: BDIMGRun_Selenium.java 
* @Package com.king.qiushibaike 
* @Description: 此方式可解决请求响应为移动端问题
* @author hdwmp123@163.com
* @date 2014-12-18 下午5:23:59 
* @version V1.0
 */
public class BDBK_Selenium {
    static Logger LOG = BeanUtil.getLOG(BDBK_Selenium.class);
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",GlobalContext.CHROME_DRIVER);
        runWeb();
    }

    private static void runWeb() {
        WebDriver driver = new ChromeDriver();
        String url = "http://baike.baidu.com/item/%s";
        String imgUrl = null;
        //
        String dirBase = GlobalContext.DIR_BAIDU_BAIKE;
        String tempDir = null;
        driver.get(String.format(url, "丹顶鹤"));
        //
        System.out.println("----------------------------------------------------------------");
        String name = driver.findElement(By.tagName("h1")).getText();
        System.out.println("名称#" + name);
        System.out.println("----------------------------------------------------------------");
        String jianjie = driver.findElement(By.cssSelector(".para")).getText();
        System.out.println("简介#"+jianjie);
        System.out.println("----------------------------------------------------------------");
        List<WebElement> xiangxi_name = driver.findElements(By.cssSelector("dt.basicInfo-item.name"));
        List<WebElement> xiangxi_value = driver.findElements(By.cssSelector("dd.basicInfo-item.value"));
        for (int i = 0; i < xiangxi_name.size(); i++) {
            WebElement nameEL = xiangxi_name.get(i);
            WebElement valueEL = xiangxi_value.get(i);
            System.out.println(nameEL.getText() + ":" + valueEL.getText());
        }
        System.out.println("----------------------------------------------------------------");
        List<WebElement> mulu = driver.findElements(By.cssSelector("h2.title-text"));
        for (int i = 0; i < mulu.size(); i++) {
            WebElement itme = mulu.get(i);
            List<WebElement> mulu_item = itme.findElements(By.cssSelector("div.para"));
            System.out.println(""+mulu_item.size());
            System.out.println(itme.getText());
            System.out.println("----------------------------------------------------------------");
        }
        System.out.println("----------------------------------------------------------------");
        List<WebElement> mulu_item = driver.findElements(By.cssSelector("div.para"));
        for (int i = 0; i < mulu_item.size(); i++) {
            WebElement itme = mulu_item.get(i);
            System.out.println(itme.getText());
            System.out.println("---------------------------------------");
        }
        //
//        List<WebElement> divs = driver.findElements(By.cssSelector("div.article"));
//        for (WebElement div : divs) {
//            List<WebElement> imgs = div.findElements(By.cssSelector("div.thumb >a >img"));
//            if(imgs != null && imgs.size() > 0){
//                List<WebElement> authors = div.findElements(By.cssSelector("div.author > a"));
//                if(authors != null && authors.size() > 0){
//                    author = authors.get(1).getText();
//                }
//                System.out.println(String.format("author:%s", author));
//                tempDir = dirBase + BeanUtil.fixFileName(author) + "/";
//                BeanUtil.createDir(tempDir);
//                imgUrl =  imgs.get(0).getAttribute("src");
//                BeanUtil.saveWebFileT(imgUrl, tempDir, "1", "jpg");
//            }
//        }
        System.out.println("---------------------------------------");
        try {
            Thread.sleep(500 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

}
