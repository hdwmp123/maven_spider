package com.king.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {
	
	static Logger LOG = BeanUtil.getLOG(BeanUtil.class);
	
    public static <T> Logger getLOG(Class<T> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static boolean createDir(String path) {
        File dir = new File(path);
        boolean result = false;
        LOG.info(String.format("dir path:%s", path));
        if (!dir.exists()) {
            result = dir.mkdirs();
            LOG.info(String.format("##create dir:%s##", result));
        } else {
        	LOG.info("dir exists");
        	result = true;
        }
        
        return result;
    }
    /**
     * 保存web文件(img)
     * @param webUrl web文件地址
     * @param saveDir 保存目录
     * @param index 文件索引
     * @return
     */
    public static boolean saveWebFileT(final String webUrl,final String saveDir,final int index) {
    	Runnable run = new Runnable() {
            @Override
            public void run() {
                saveWebFile(webUrl, saveDir, index);
            }
        };
        BeanUtil.runThread(run, "");
        return true;
    }
    /**
     * 保存web文件(img)
     * @param webUrl web文件地址
     * @param saveDir 保存目录
     * @param index 文件索引
     * @return
     */
    public static boolean saveWebFile(final String webUrl,String saveDir,int index) {
    	LOG.info(String.format("webUrl:%s", webUrl));
    	LOG.info(String.format("saveDir:%s", saveDir));
        try {
            // new一个文件对象用来保存图片，默认保存当前工程根目录
            String fileName = index==-1 ? webUrl.substring(webUrl.lastIndexOf("/")) : index + ".jpg";
            File imageFile = new File(saveDir + fileName);
            if(imageFile.exists()){
               LOG.info("file exists");
               return true;
            }
            // new一个URL对象
            URL url = new URL(webUrl);
            // 打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置请求方式为"GET"
            conn.setRequestMethod("GET");
            // 超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            // 通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(inStream);
            // 创建输出流
            FileOutputStream outStream = new FileOutputStream(imageFile);
            // 写入数据
            outStream.write(data);
            // 关闭输出流
            outStream.close();
            LOG.info("##save file success##");
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 将数据流转换为byte数组
     * @param inStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024*2];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }
    /**
     * 检测是否为null或""
     * 
     * @param str
     * @return
     */
    public static boolean checkStr(Object str) {
        if (str instanceof String) {
            return str != null && !"".equals(str.toString().trim());
        } else {
            return str != null;
        }
    }
    /**
     * 文件名纠错
     * @param fileName
     * @return
     */
    public static String fixFileName(String fileName) {
        if (!checkStr(fileName)) {
            return null;
        }
        String[] filter = new String[]{
                " ",
                "@",
                "#",
                "$",
                "%",
                "^",
                "&",
                "*",
                "(",
                ")",
                "[",
                "]",
                ":",
                "<",
                ">",
                "{",
                "}",
                "?",
                "\\",
                "//",
        };
        for (int i = 0; i < filter.length; i++) {
            while (fileName.contains(filter[i])) {
                fileName = fileName.replace(filter[i], "_");
            }
        }
        int length = fileName.getBytes().length;
        int limitSize = 250;
        if (length > limitSize) {
            int offset = (length - limitSize) % 2 == 0 ? (length - limitSize) : length - limitSize
                    - 1;
            fileName = new String(fileName.getBytes(), offset, limitSize);
        }
        return fileName;
    }
    /**
     * 获取当前时间字符串 格式 yyyy-MM-dd HH:mm:ss
     * 
     * @return
     */
    public static String getCurrentTime() {
        String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()).toString();
        return time;
    }
    /**
     * 启动新线程
     * 
     * @param runnable
     */
    public static void runThread(Runnable runnable, String message) {
    	LOG.info(String.format("start启动新线程：%s,时间：%s", message, getCurrentTime()));
        Thread thread = new Thread(runnable);
        thread.start();
        LOG.info(String.format("end启动新线程：%s,时间：%s", message, getCurrentTime()));
    }
}