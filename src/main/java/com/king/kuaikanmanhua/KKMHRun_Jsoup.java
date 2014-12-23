package com.king.kuaikanmanhua;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

import com.king.util.BeanUtail;
import com.king.util.GlobalContext;
/**
 * 
* @Title: KKMHRun_Jsoup.java 
* @Package com.king.kuaikanmanhua 
* @Description: 速度快
* @author hdwmp123@163.com
* @date 2014-12-18 下午5:22:36 
* @version V1.0
 */
public class KKMHRun_Jsoup {
	static Logger LOG = BeanUtail.getLOG(KKMHRun_Selenium.class);
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
		BeanUtail.runThread(run, "");
			
	}

	private static void runWeb() throws IOException {
		Document  driver = null;
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
			driver = Jsoup.connect(String.format(url, pageIndex)).timeout(3*1000).get();
			pageIndex++;
			//
			topic_name =driver.getElementsByClass("topic-name").get(0).text();
			comic_name = driver.getElementsByClass("comic-name").get(0).text();
			date = driver.getElementsByClass("date").get(0).text();
			author = driver.getElementsByClass("author").get(0).text();
			LOG.info(topic_name);
			LOG.info(comic_name);
			LOG.info(date);
			LOG.info(author);
			tempDir = dirBase + BeanUtail.fixFileName(topic_name) + "/" + BeanUtail.fixFileName(comic_name) + "/";
			BeanUtail.createDir(tempDir) ;
			//
			Elements imgs = driver.select(".comic-content > img");
			if (imgs == null) {
				break;
			}
			imgCount = imgs.size();
			LOG.info(String.format("图片数量:%s", imgCount));
			int index = 1;
			for (Element img : imgs) {
				imgUrl = img.attr("src");
				BeanUtail.saveWebFileT(imgUrl, tempDir, index);
				index ++;
			}
			LOG.info("---------------------------------------");
		}
	}
}
