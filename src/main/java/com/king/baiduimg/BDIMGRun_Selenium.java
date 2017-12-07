package com.king.baiduimg;

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
public class BDIMGRun_Selenium {
    static Logger LOG = BeanUtil.getLOG(BDIMGRun_Selenium.class);

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", GlobalContext.CHROME_DRIVER);
        runWeb();
    }

    private static void runWeb() {
        WebDriver driver = new ChromeDriver();
        String url = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%BB%91%E7%BF%85%E9%95%BF%E8%84%9A%E9%B9%AC&step_word=&hs=0&pn=0&spn=2&di=3463812600&pi=0&rn=1&tn=baiduimagedetail&is=4118912903%2C1400194148&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=3182610698%2C4264073662&os=3421364060%2C814686414&simid=0%2C0&adpicid=0&lpn=0&ln=1988&fr=&fmq=1512286281562_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=17&oriquery=&objurl=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fd833c895d143ad4b8c75f62e88025aafa50f0680.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fijw1stgj_z%26e3Bkwt17_z%26e3Bv54AzdH3FvfAzdH3Fjc0u9caua9bmk9bwndc9jljjm0bllkj9&gsm=0&rpstart=0&rpnum=0";
        String imgUrl = null;
        //
        String dirBaseAll = GlobalContext.DIR_BAIDU_IMG;
        driver.get(url);
        //
        try {
            Thread.sleep(500 * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> divs = driver.findElements(By.cssSelector("div.img-box > img"));
        for (WebElement div : divs) {
            BeanUtil.createDir(dirBaseAll);
            imgUrl = div.getAttribute("src");
            System.err.println(imgUrl);
            BeanUtil.saveWebFileT(imgUrl, dirBaseAll, "-1", "jpg");
        }
        LOG.info("---------------------------------------");
        driver.quit();
    }

}
