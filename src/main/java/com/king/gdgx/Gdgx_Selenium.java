package com.king.gdgx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import com.king.db.DaoUtil;
import com.king.gdgx.model.GdgxSchool;
import com.king.gdgx.model.GdgxSchoolDetail;
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
/**
 * 
* @Title: YuanMingYuanRun_Selenium.java 
* @Package com.king.qiushibaike 
* @Description: 此方式可解决请求响应为移动端问题
* @author hdwmp123@163.com
* @date 2014-12-18 下午5:23:59 
* @version V1.0
 */
public class Gdgx_Selenium {
    static Logger LOG = BeanUtil.getLOG(Gdgx_Selenium.class);
    static Dao dao = DaoUtil.getDao();
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver",GlobalContext.CHROME_DRIVER);
        dao.clear(GdgxSchool.class);
        File file = new File("C:/Users/ThinkPad User/Desktop/Img/GDGX/静态数据.text");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str = null;
        List<GdgxSchool> schools = new ArrayList<GdgxSchool>();
        while ((str = reader.readLine()) != null) {
             System.out.println(str);//此时str就保存了一行字符串
             String[] split = str.split("#");
             GdgxSchool item = new GdgxSchool();
             item.setIn_order(NumberUtils.toInt(split[0]));
             item.setName(split[1]);
             item.setDetail_url(split[2]);
             item.setTotale_score(NumberUtils.toFloat(split[3]));
             item.setRcpy_score(NumberUtils.toFloat(split[4]));
             item.setKxyj_score(NumberUtils.toFloat(split[5]));
             item.setType(split[6]);
             item.setArea(split[7]);
             schools.add(item);
        }
        dao.insert(schools);
        for (int i = 0; i < schools.size(); i++) {
            GdgxSchool item = schools.get(i);
            //runWeb(item);
        }
    }

    private static void runWeb(GdgxSchool item) {
        //检测是否存在
        List<GdgxSchoolDetail> query = dao.query(GdgxSchoolDetail.class, Cnd.where("name", "=", item.getName()));
        if(query!=null && query.size()>0){
            System.out.println("数据已经存在："  + item.getName());
            return;
        }
        WebDriver driver = new ChromeDriver();
        //
        String dirBase = GlobalContext.DIR_GDGX;
        driver.get(item.getDetail_url());
        //
        GdgxSchoolDetail detail = new GdgxSchoolDetail();
        //
        System.out.println("----------------------------------------------------------------");
        String name = driver.findElement(By.cssSelector(".schoolName > strong")).getText();
        System.out.println("名称#" + name);
        
        //
        detail.setName(name);
        System.out.println("----------------------------------------------------------------");
        try {
            WebElement more  = driver.findElement(By.cssSelector("span.read-more > a"));
            if(more!=null){
                more.click();
            }
        } catch (Exception e) {
        }
        
        String jj = driver.findElement(By.cssSelector("div.intro")).getText();
        System.out.println("简介#"+jj);
        detail.setJj(jj);
        System.out.println("----------------------------------------------------------------");
        List<WebElement> xiangxi_name = driver.findElements(By.cssSelector("li.biItem > span.t"));
        List<WebElement> xiangxi_value = driver.findElements(By.cssSelector("li.biItem > div.c"));
        for (int i = 0; i < xiangxi_name.size(); i++) {
            WebElement nameEL = xiangxi_name.get(i);
            WebElement valueEL = xiangxi_value.get(i);
            System.out.println(nameEL.getText() + ":" + valueEL.getText());
            setValue(detail,nameEL.getText(),valueEL.getText());
        }
        WebElement imgEL = null;
        try {
            imgEL = driver.findElement(By.cssSelector("div.schoolLogo > img"));
        } catch (Exception e) {
            LOG.error("图片未取到");
        }
        String imgURL = null;
        if (imgEL != null) {
            imgURL = imgEL.getAttribute("src");
            detail.setLogo(imgURL);
        }
        // String jyqk =
        // driver.findElement(By.cssSelector("div.txt")).getText();
        // detail.setJyqk(jyqk);
        dao.insert(detail);
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
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void setValue(GdgxSchoolDetail baiKe, String name, String value) {
        name = name.replace(" ", "");
        if ("创建时间".equals(name)) {
            baiKe.setCjsj(value);
        } else if ("重点学科".equals(name)) {
            baiKe.setZdxk(value);
        } else if ("隶属于".equals(name)) {
            baiKe.setLsy(value);
        } else if ("学校类型".equals(name)) {
            baiKe.setXxlx(value);
        } else if ("学生人数".equals(name)) {
            baiKe.setXsrs(value);
        } else if ("博士点个数".equals(name)) {
            baiKe.setBsdgs(value);
        } else if ("院士人数".equals(name)) {
            baiKe.setYsrs(value);
        } else if ("硕士点个数".equals(name)) {
            baiKe.setSsdgs(value);
        }
    }

}
