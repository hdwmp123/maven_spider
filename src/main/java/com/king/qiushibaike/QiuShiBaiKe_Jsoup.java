package com.king.qiushibaike;

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
* @Title: QiuShiBaiKe_Jsoup.java 
* @Package com.king.qiushibaike 
* @Description: 响应结果为移动端
* @author hdwmp123@163.com
* @date 2014-12-18 下午5:23:00 
* @version V1.0
 */
public class QiuShiBaiKe_Jsoup {
	static Logger LOG = BeanUtail.getLOG(QiuShiBaiKe_Jsoup.class);
	public static void main(String[] args) {
		try {
			run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void run() throws IOException {
		Document  driver = null;
		int pageIndex = 1;
		String url = "http://www.qiushibaike.com/8hr/page/%s";
		int divCount = 0;
		String content = null;
		String author = null;
		String imgUrl = null;
		//
		String dirBase = GlobalContext.DIR_QIU_SHI_BAI_KE;
		String tempDir = null;
		
		boolean result ;
		while (true) {
			driver = Jsoup.connect(String.format(url, pageIndex)).timeout(3*1000).get();
			pageIndex++;
			//
			Elements divs = driver.select("div.article");
			if (divs == null) {
				break;
			}
			divCount = divs.size();
			LOG.info(String.format("笑话数量:%s", divCount));
			int index = 1;
			for (Element div : divs) {
				//
				author = div.select("div.author > a").get(1).text();
				tempDir = dirBase + BeanUtail.fixFileName(author) + "/";
				result = BeanUtail.createDir(tempDir) ;
				LOG.info(String.format("create dir:%s", result));
				imgUrl = div.select("img").get(1).attr("src");
				LOG.info(imgUrl);
				//result = BeanUtail.saveWebFileT(imgUrl, tempDir, index);
				index ++;
				LOG.info(String.format("save file:%s", result));
			}
			LOG.info("---------------------------------------");
		}
	}
}
