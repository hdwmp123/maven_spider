package com.king.baidubaike;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import com.king.baidubaike.model.BaiKe;
import com.king.baidubaike.model.BaiKeDirectory;
import com.king.db.DaoUtil;
import com.king.util.BeanUtil;
import com.king.util.GlobalContext;
/**
 * 
* @Title: UUMTIMGRun_Selenium.java 
* @Package com.king.qiushibaike 
* @Description: 此方式可解决请求响应为移动端问题
* @author hdwmp123@163.com
* @date 2014-12-18 下午5:23:59 
* @version V1.0
 */
/**
 * 
* @Title: UUMTIMGRun_Selenium.java 
* @Package com.king.qiushibaike 
* @Description: 此方式可解决请求响应为移动端问题
* @author hdwmp123@163.com
* @date 2014-12-18 下午5:23:59 
* @version V1.0
 */
public class BDBK_Selenium {
    static Logger LOG = BeanUtil.getLOG(BDBK_Selenium.class);
    static Dao dao = DaoUtil.getDao();
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",GlobalContext.CHROME_DRIVER);
        dao.clear(BaiKe.class);
        dao.clear(BaiKeDirectory.class);
        File file = new File("C:/Users/ThinkPad User/Desktop/鸟监控/星海湖鸟的种类");
        if(file.isDirectory()){
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                File img = listFiles[i];
                String name = img.getName().replace(".jpg", "");
                System.out.println(name);
                if ("凤头".equals(name)) {
                    continue;
                }
                runWeb(name);
            }
        }
    }

    private static void runWeb(String theName) {
        //检测是否存在
        List<BaiKe> query = dao.query(BaiKe.class, Cnd.where("name", "=", theName));
        if(query!=null && query.size()>0){
            System.out.println("数据已经存在："  + theName);
            return;
        }
        WebDriver driver = new ChromeDriver();
        String url = "http://baike.baidu.com/item/%s";
        //
        String dirBase = GlobalContext.DIR_BAIDU_BAIKE;
        driver.get(String.format(url, theName));
        //
        BaiKe baiKe = new BaiKe();
        //
        System.out.println("----------------------------------------------------------------");
        String name = driver.findElement(By.tagName("h1")).getText();
        System.out.println("名称#" + name);
        
        //
        baiKe.setName(name);
        System.out.println("----------------------------------------------------------------");
        String jianjie = driver.findElement(By.cssSelector(".para")).getText();
        System.out.println("简介#"+jianjie);
        baiKe.setSummary(jianjie);
        System.out.println("----------------------------------------------------------------");
        List<WebElement> xiangxi_name = driver.findElements(By.cssSelector("dt.basicInfo-item.name"));
        List<WebElement> xiangxi_value = driver.findElements(By.cssSelector("dd.basicInfo-item.value"));
        for (int i = 0; i < xiangxi_name.size(); i++) {
            WebElement nameEL = xiangxi_name.get(i);
            WebElement valueEL = xiangxi_value.get(i);
            System.out.println(nameEL.getText() + ":" + valueEL.getText());
            setValue(baiKe,nameEL.getText(),valueEL.getText());
        }
        WebElement imgEL = null;
        try {
            imgEL = driver.findElement(By.cssSelector("div.summary-pic > a > img"));
        } catch (Exception e) {
            LOG.error("图片未取到");
            try {
                imgEL = driver.findElement(By.cssSelector("div.album-wrap img"));
            } catch (Exception e2) {
                LOG.error("图片完全未取到");
            }
        }
        String imgURL = null;
        if (imgEL != null) {
            imgURL = imgEL.getAttribute("src");
            baiKe.setImage(imgURL);
        }
//        System.out.println("----------------------------------------------------------------");
//        List<WebElement> mulu = driver.findElements(By.cssSelector("h2.title-text"));
//        for (int i = 0; i < mulu.size(); i++) {
//            WebElement itme = mulu.get(i);
//            List<WebElement> mulu_item = itme.findElements(By.cssSelector("div.para"));
//            System.out.println(""+mulu_item.size());
//            System.out.println(itme.getText());
//            System.out.println("----------------------------------------------------------------");
//        }
//        System.out.println("----------------------------------------------------------------");
//        List<WebElement> mulu_item = driver.findElements(By.cssSelector("div.para"));
//        for (int i = 0; i < mulu_item.size(); i++) {
//            WebElement itme = mulu_item.get(i);
//            System.out.println(itme.getText());
//            System.out.println("---------------------------------------");
//        }
        dao.insert(baiKe);
        BaiKeDirectory  baiKeDirectory = null;
        List<WebElement> titles = driver.findElements(By.cssSelector(".main-content > div, .main-content > ul"));
        String firstName = null;
        for (WebElement webElement : titles) {
            if("para-title level-2".equals(webElement.getAttribute("class"))) {
                firstName = webElement.findElement(By.cssSelector(".title-text")).getText();
                System.out.println("--------------------主目录：" + firstName + "--------------------------------");
                if (baiKeDirectory == null) {//第一次
                    baiKeDirectory = new BaiKeDirectory();
                } else {
                    dao.insert(baiKeDirectory);
                    baiKeDirectory = new BaiKeDirectory();
                }
                baiKeDirectory.setName(firstName);
                baiKeDirectory.setBaike_id(baiKe.getId());
            }
            if("para-title level-3".equals(webElement.getAttribute("class"))) {
                String text = webElement.findElement(By.cssSelector(".title-text")).getText();
                System.out.println("--------------------子目录:" + text + "--------------------------------");
                if (baiKeDirectory.getSub_name() != null) {
                    dao.insert(baiKeDirectory);
                    baiKeDirectory = new BaiKeDirectory();
                }
                baiKeDirectory.setName(firstName);
                baiKeDirectory.setBaike_id(baiKe.getId());
                baiKeDirectory.setSub_name(text);
            }
            if("para".equals(webElement.getAttribute("class"))) {
                String text2 = webElement.getText();
                if(baiKeDirectory.getContent()==null){
                    baiKeDirectory.setContent(text2);
                } else {
                    baiKeDirectory.setContent(baiKeDirectory.getContent() + text2);
                }
                System.out.println(text2);
            }
            if("ul".equals(webElement.getTagName())) {
                String attribute = webElement.getAttribute("outerHTML");
                if(baiKeDirectory.getContent()==null){
                    baiKeDirectory.setContent(attribute);
                } else {
                    baiKeDirectory.setContent(baiKeDirectory.getContent() + attribute);
                }
                System.out.println(attribute);
            }
        }
        //
        BeanUtil.createDir(dirBase);
        if (StringUtils.isNotBlank(imgURL)) {
            BeanUtil.saveWebFileT(imgURL, dirBase, "-1", "jpg");
        }
        System.out.println("---------------------------------------");
        try {
            Thread.sleep(500 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void setValue(BaiKe baiKe, String name, String value) {
        name = name.replace(" ", "");
        if ("中文学名".equals(name)) {
            baiKe.setChinese_name(value);
        } else if ("拉丁学名".equals(name)) {
            baiKe.setLatin_name(value);
        } else if ("别称".equals(name)) {
            baiKe.setAlias(value);
        } else if ("界".equals(name)) {
            baiKe.setCommunity(value);
        } else if ("门".equals(name)) {
            baiKe.setDoor(value);
        } else if ("亚门".equals(name)) {
            baiKe.setSub_door(value);
        } else if ("纲".equals(name)) {
            baiKe.setGang(value);
        } else if ("亚纲".equals(name)) {
            baiKe.setSubclass(value);
        } else if ("目".equals(name)) {
            baiKe.setHead(value);
        } else if ("科".equals(name)) {
            baiKe.setSection(value);
        } else if ("属".equals(name)) {
            baiKe.setGenus(value);
        } else if ("种".equals(name)) {
            baiKe.setSpecies(value);
        } else if ("分布区域".equals(name)) {
            baiKe.setDistribution_area(value);
        } else if ("命名者及年代".equals(name)) {
            baiKe.setNanmed_and_chronicle(value);
        } else if ("英文名称".equals(name)) {
            if(baiKe.getEnglish_name()==null){
                baiKe.setEnglish_name(value);
            } else {
                baiKe.setEnglish_name(baiKe.getEnglish_name() + "#" + value);
            }
            
        }
    }

}
