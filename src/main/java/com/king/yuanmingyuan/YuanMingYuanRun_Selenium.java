package com.king.yuanmingyuan;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Dao;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import com.king.db.DaoUtil;
import com.king.util.BeanUtil;
import com.king.util.GlobalContext;
import com.king.uumnt.UUMTIMGRun_Selenium;
import com.king.yuanmingyuan.model.SqlTable;

/**
 * 
 * @Title: UUMTIMGRun_Selenium.java
 * @Package com.king.qiushibaike
 * @Description: 此方式可解决请求响应为移动端问题
 * @author hdwmp123@163.com
 * @date 2014-12-18 下午5:23:59
 * @version V1.0
 */
public class YuanMingYuanRun_Selenium {
    
    static Dao dao = DaoUtil.getDao();
    static Logger LOG = BeanUtil.getLOG(UUMTIMGRun_Selenium.class);

    static String[] urls1 = { 
        "http://www.yuanmingyuanpark.cn/cgll/zyjd/ymy/fhjq/",
        "http://www.yuanmingyuanpark.cn/cgll/zyjd/ymy/jzjq/",
        "http://www.yuanmingyuanpark.cn/cgll/zyjd/ymy/jzjqzb/",
        
        "http://www.yuanmingyuanpark.cn/cgll/zyjd/ccy/",
        "http://www.yuanmingyuanpark.cn/cgll/zyjd/ccy/index_1.html",
        
        "http://www.yuanmingyuanpark.cn/cgll/zyjd/qcy/",
        };
    static String[] area = { 
        "圆明园#1",
        "圆明园#1",
        "圆明园#1",
        
        "长春园#2",
        "长春园#2",
        
        "绮春园#3",
        };
    static String[] urls = urls1;//new String[] { urls1[0] };

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", GlobalContext.CHROME_DRIVER);
        runWeb();
    }

    private static void runWeb() {
        dao.clear(SqlTable.class);
        for (int i = 0; i < urls.length; i++) {
            loop(area[i], urls[i]);
        }
    }

    private static void loop(String area, String url) {
        String dirBase = GlobalContext.DIR_YUAN_MING_YUAN;
        WebDriver driver = new ChromeDriver();
        WebDriver driverContent = null;
        try {
            openPage(url, driver);
            //获取图片，名称和url
            List<WebElement> as = driver.findElements(By.cssSelector(".addNewJs > div i a"));
            if(CollectionUtils.isEmpty(as)){
                as = driver.findElements(By.cssSelector(".zu001 > div i a"));
            }
            List<WebElement> images = driver.findElements(By.cssSelector(".addNewJs > div p img"));
            if(CollectionUtils.isEmpty(images)){
                images = driver.findElements(By.cssSelector(".zu001 > div p img"));
            }
            System.err.println(String.format("图片：%s,连接：%s", images.size(),as.size()));
            for (int i = 0; i < as.size(); i++) {
                WebElement a = as.get(i);
                String href = a.getAttribute("href");
                String name = a.getText();
                if(StringUtils.isBlank(name)){
                    continue;
                }
                WebElement image = images.get(i);
                String imageSrc = image.getAttribute("src");
                String[] areaArr = area.split("#");
                BeanUtil.saveWebFileT(imageSrc, dirBase + File.separator 
                        + areaArr[0] + File.separator + name + File.separator , "1" , "jpg");
//                System.out.println(name);
//                System.out.println(href);
//                System.out.println(imageSrc);
                //
                driverContent = new ChromeDriver();
                openPage(href, driverContent);
                List<WebElement> contents = driverContent.findElements(By.cssSelector(".Custom_UnionStyle span"));
                StringBuffer details = new StringBuffer();
                for (int j = 0; j < contents.size(); j++) {
                    WebElement item = contents.get(j);
                    String content = item.getText();
                    if(StringUtils.isBlank(content)){
                        continue;
                    }
//                  System.out.println(content);
                    details.append(content).append("##");
                }
//                System.out.println(details);
//                System.out.println("----------------------");
                String sql = String.format("INSERT INTO gg_point (`platform_id`,`type`,`title`, `img`, `details`, `location`) VALUES (8,'%s','%s','%s','%s','%s');",areaArr[1], name,imageSrc,details,"0,0");
                System.out.println(sql);
                SqlTable sqlBean = new SqlTable();
                sqlBean.setSql_str(sql);
                dao.insert(sqlBean);
                driverContent.quit();
            }
        } catch (Exception e) {
        } finally {
            driver.quit();
            driverContent.quit();
        }

    }

    private static void openPage(String url, WebDriver driver) {
        driver.get(url);
    }
}
