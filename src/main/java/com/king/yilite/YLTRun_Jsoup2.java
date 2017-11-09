package com.king.yilite;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.king.util.BeanUtil;
import com.king.util.GlobalContext;

/**
 * 
 * @Title: YLTRun_Jsoup.java
 * @Package com.king.kuaikanmanhua
 * @Description: 速度快
 * @author hdwmp123@163.com
 * @date 2014-12-18 下午5:22:36
 * @version V1.0
 */
public class YLTRun_Jsoup2 {
    static Logger LOG = BeanUtil.getLOG(YLTRun_Jsoup2.class);

    public static void main(String[] args) {
        try {
            getHref();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getHref() throws IOException {
         final String [] href = new String []{
                 "https://h5.youzan.com/v2/goods/jccwjd4y",
                 "https://h5.youzan.com/v2/goods/16f6z1d1e",
                 "https://h5.youzan.com/v2/goods/14mhfsmni",
                 "https://h5.youzan.com/v2/goods/o71zpth7",
                 "https://h5.youzan.com/v2/goods/kdcp1bie",
                 "https://h5.youzan.com/v2/goods/eq9rigrm",
                 "https://h5.youzan.com/v2/goods/2mtedbjx",
                 "https://h5.youzan.com/v2/goods/m5hlx6h2",
                 "https://h5.youzan.com/v2/goods/14a2vwe0z",
                 "https://h5.youzan.com/v2/goods/1aoyjj8fk",
                 "https://h5.youzan.com/v2/goods/16hsi7c6v",
                 "https://h5.youzan.com/v2/goods/1drdgkhsu",
                 "https://h5.youzan.com/v2/goods/tl9tmm9e",
                 "https://h5.youzan.com/v2/goods/a058xodb",
                 "https://h5.youzan.com/v2/goods/vr4y2n4h",
                 "https://h5.youzan.com/v2/goods/1z0nc2hr",
                 "https://h5.youzan.com/v2/goods/63fc4nyz",
                 "https://h5.youzan.com/v2/goods/kor07vse",
                 "https://h5.youzan.com/v2/goods/kpnvey4h",
                 "https://h5.youzan.com/v2/goods/ogbeabxy",
                 "https://h5.youzan.com/v2/goods/b1hv5g8g"
         };
         for ( int i = 0; i < href.length; i++) {
             final int index = i; 
             Runnable run = new Runnable() {
                 @Override
                 public void run() {
                     try {
                         runWeb(href[index]);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
             };
             BeanUtil.runThread(run, "");
             
        }
    }

    private static void runWeb(String url) throws IOException {
        // String url =
        // "https://detail.youzan.com/show/goods?alias=jccwjd4y&reft=1510055021930&spm=f45257714&sf=wx_menu";
        int imgCount = 0;
        String imgUrl = null;
        int index = 1;
        //
        String dirBase = GlobalContext.DIR_YI_LI_TE;
        String tempDir = null;
        String title = null;
        System.out.println("############################################");
        /** HtmlUnit请求web页面 */
        WebClient wc = getClient();
        HtmlPage page = wc.getPage(url);
        DomNodeList<DomElement> links = page.getElementsByTagName("img");
        if (links == null) {
            return;
        }
        title = page.getTitleText();
        imgCount = links.size();
        LOG.info(String.format("图片数量:%s", imgCount));
        for (DomElement link : links) {
            imgUrl = link.getAttribute("src");
            System.out.println(imgUrl);
            tempDir = dirBase + BeanUtil.fixFileName(title) + "/";
            BeanUtil.createDir(tempDir);
            BeanUtil.saveWebFileT(imgUrl, tempDir, index + "", "jpg");
            index++;
            //
            System.out.println("--------------------------------------");
        }
        LOG.info("---------------------------------------");
    }

    private static WebClient getClient() {
        WebClient wc = new WebClient(BrowserVersion.CHROME);
        wc.getOptions().setUseInsecureSSL(true);
        wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(true); // 禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        wc.getOptions().setTimeout(100000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        wc.getOptions().setDoNotTrackEnabled(true);
        return wc;
    }
}
