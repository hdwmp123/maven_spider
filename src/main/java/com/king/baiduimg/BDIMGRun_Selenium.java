package com.king.baiduimg;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import com.king.util.BeanUtil;
import com.king.util.GlobalContext;

/**
 * 
 * @Title: YuanMingYuanRun_Selenium.java
 * @Package com.king.qiushibaike
 * @Description: 此方式可解决请求响应为移动端问题
 * @author hdwmp123@163.com
 * @date 2014-12-18 下午5:23:59
 * @version V1.0
 */
public class BDIMGRun_Selenium {
    static Logger LOG = BeanUtil.getLOG(BDIMGRun_Selenium.class);

    static String[] filenames = { "灰头麦鸡", "燕子", "燕鸥", "牛背鹭", "环颈雉", "琵嘴鸭", "白琵鹭", "白眼潜鸭", "白腰草鹬", "白骨顶", "矶鹬", "红嘴鸥",
            "红腹滨鹬", "绿头鸭", "绿鹭", "翘鼻麻鸭", "苍鹭", "豆雁", "赤膀鸭", "赤颈鸭", "赤麻鸭", "遗鸥", "金斑鸻", "金眶鸻", "针尾沙锥", "针尾鸭", "银鸥",
            "长嘴剑鸻", "须浮鸥", "鸳鸯", "鹊鸭", "鹤鹬", "黑水鸡", "黑鹳" };
    static String[] filenames2 = {"黑水鸡"};

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", GlobalContext.CHROME_DRIVER);
        runWeb();
    }

    private static void runWeb() {
        String dirBase = GlobalContext.DIR_BAIDU_IMG;
        for (String folderName : filenames2) {
            LOG.info("-------------------开始下载：" + folderName + "--------------------");
            WebDriver driver = new ChromeDriver();
            try {
                openPage(folderName, driver);
                String imgUrl = null;
                WebElement next = null;
                for (int i = 0; i < 500; i++) {
                    imgUrl = getImgUrl(driver);
                    BeanUtil.saveWebFileT(imgUrl, dirBase + File.separator + folderName + File.separator,
                            String.valueOf(i), "jpg");
                    next = driver.findElement(By.cssSelector(".img-next"));
                    next.click();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                LOG.info("-------------------结束下载：" + folderName + "--------------------");
                driver.quit();
            } catch (Exception e) {

            } finally {
                driver.quit();
            }
        }
    }

    private static void openPage(String foldName, WebDriver driver) {
        String url = "https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1512700026542_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%s";
        driver.get(String.format(url, foldName));
        //
        try {
            Thread.sleep(500 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement a = driver.findElements(By.className("imgitem")).get(0).findElement(By.tagName("a"));
        driver.get(a.getAttribute("href"));
        try {
            Thread.sleep(500 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getImgUrl(WebDriver driver) {
        String imgUrl;
        WebElement srcPic = driver.findElement(By.id("srcPic"));
        WebElement img = srcPic.findElement(By.tagName("img"));
        imgUrl = img.getAttribute("src");
        return imgUrl;
    }

}
