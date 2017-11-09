package com.king.yilite;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

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
public class YLTRun_Jsoup {
    static Logger LOG = BeanUtil.getLOG(YLTRun_Jsoup.class);

    public static void main(String[] args) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    runWeb();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        BeanUtil.runThread(run, "");

    }

    private static void runWeb() throws IOException {
        Document driver = null;
        String url = "http://www.ylt1956.com/yiliteyuanyuanxilie.shtml";
        //String url = "http://www.ylt1956.com/yiliteyuanyuanxilie-2.shtml";
        int imgCount = 0;
        String imgUrl = null;
        //
        String dirBase = GlobalContext.DIR_YI_LI_TE;
        String tempDir = null;
        driver = Jsoup.connect(String.format(url)).timeout(60 * 1000).get();
        //
        //
        Elements proList = driver.getElementsByClass("p_list_dl");
        if (proList == null) {
            return;
        }
        imgCount = proList.size();
        LOG.info(String.format("商品数量:%s", imgCount));
        int index = 1;
        for (Element pro : proList) {
            Element a = pro.select("a").get(0);
            Element img = pro.select("img").get(0);
            String href = a.attr("href");
            String name = img.attr("alt");
            String src = img.attr("src");
            imgUrl = src;

            System.out.println(href);
            System.out.println(src);
            System.out.println(name);
            tempDir = dirBase + BeanUtil.fixFileName(name) + "/";
            BeanUtil.createDir(tempDir);
            BeanUtil.saveWebFileT(imgUrl, tempDir, index + "", "jpg");
            //
            driver = Jsoup.connect(String.format(href)).timeout(60 * 1000).get();
            String pdesc = driver.getElementsByClass("pdesc").get(0).text();
            System.out.println(pdesc);
            BeanUtil.saveTxtFile(pdesc, tempDir, index + "", "txt");
            System.out.println("--------------------------------------");
        }
        LOG.info("---------------------------------------");
    }
}
