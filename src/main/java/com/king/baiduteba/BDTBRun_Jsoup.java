package com.king.baiduteba;

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
 * @Title: BDTBRun_Jsoup.java
 * @Package com.king.baiduteba
 * @Description: 百度贴吧爬图
 * @author hdwmp123@163.com
 * @date 2015-5-21 下午3:54:07
 * @version V1.0
 */
public class BDTBRun_Jsoup {
	static Logger LOG = BeanUtil.getLOG(BDTBRun_Jsoup.class);

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
		int pageIndex = 1;
		String url = "http://tieba.baidu.com/p/3775307501?pn=%s";
		int imgCount = 0;
		String imgUrl = null;
		//
		String dirBase = GlobalContext.DIR_BAIDU_TEBA;
		String tempDir = null;
		boolean exe = true;
		while (exe) {
			driver = Jsoup.connect(String.format(url, pageIndex))
					.timeout(3 * 1000).get();
			pageIndex++;
			//
			tempDir = dirBase;
			BeanUtil.createDir(tempDir);
			//
			Elements imgs = driver.select("cc .BDE_Image");
			if (imgs == null) {
				break;
			}
			imgCount = imgs.size();
			LOG.info(String.format("图片数量:%s", imgCount));
			int index = 1;
			// 微信截图大小
			int width = 422;
			int height = 750;
			for (Element img : imgs) {
				if(img.attr("width").equals(width+"") && img.attr("height").equals(height+"")){
					imgUrl = img.attr("src");
					BeanUtil.saveWebFileT(imgUrl, tempDir, pageIndex + "_" + index, "jpg");
				}
				index++;
			}
			LOG.info("---------------------------------------");
			// exe = false;
		}
	}
}
