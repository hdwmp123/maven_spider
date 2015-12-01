package com.king.youbi;

import com.king.util.BeanUtil;
import com.king.util.GlobalContext;

public class YouBiDownload {
	public static void main(String[] args) {
		String dirBase = GlobalContext.DIR_YOU_BI;
		int index = 200;
		String url = "http://www.ubabytv.com.cn//shop/download.php?id=%s&type=song";
		while (true) {
			BeanUtil.saveWebFile(String.format(url, index), dirBase, index + "",
					"mp3");
			index++;
		}
	}
}
