package com.king.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信图片处理工具
 * 
 * @author zhuang.y
 * 
 */
public class PictureTool {

    protected static Logger logger = LoggerFactory.getLogger(PictureTool.class);

    public static void main(String[] args) throws IOException {
        // test1();
        exe("E:/Spider/YiLiTe");

    }

    private static void exe(String path) {
        File source = new File(path);
        if (source.isDirectory()) {
            File[] listFiles = source.listFiles();
            for (File file : listFiles) {
                String path2 = file.getAbsolutePath();
                if (file.isDirectory()) {
                    System.out.println("path2#" + path2);
                    exe(path2);
                } else {
                    String name = file.getName();
                    System.out.println("name#" + name);
                    if (name.endsWith("1.jpg")) {
                        maigaoPic(path2, path2.substring(0, path2.indexOf(".jpg")) + "SUB.jpg", 0);
                    }
                }
            }
        }
    }

    private static void test1() throws IOException {
        File fileOne = new File("c:\\1.jpg");
        BufferedImage imageFirst = ImageIO.read(fileOne);
        int border = 0;
        imageFirst = crop(imageFirst, 0, 10, 297, 300);
        File outFile = new File("d:\\2.jpg");
        ImageIO.write(imageFirst, "jpg", outFile);// 写图片
    }

    /**
     * 纵向合图的x坐标像素
     */
    private static int y_width = 645;

    /**
     * 标准图片的y坐标像素,920，是一般照片，1099是邮票照片
     */
    private static int y_height = 920;

    /**
     * 裁剪x坐标缩进像素
     */
    private static int x_retract = 50;

    /**
     * 裁剪y坐标缩进像素
     */
    private static int y_retract = 50;

    /**
     * 系统默认图片边框为20
     */
    public static int BORDER = 20;

    /**
     * 横向合成图片
     */
    public static void xPic(String first, String second, String out) {
        try {
            /* 1 读取第一张图片 */
            File fileOne = new File(first);
            BufferedImage imageFirst = ImageIO.read(fileOne);
            int width = imageFirst.getWidth();// 图片宽度
            int height = imageFirst.getHeight();// 图片高度
            int[] imageArrayFirst = new int[width * height];// 从图片中读取RGB
            imageArrayFirst = imageFirst.getRGB(0, 0, width, height, imageArrayFirst, 0, width);

            /* 1 对第二张图片做相同的处理 */
            File fileTwo = new File(second);
            BufferedImage imageSecond = ImageIO.read(fileTwo);
            int widthTwo = imageSecond.getWidth();// 图片宽度
            int heightTwo = imageSecond.getHeight();// 图片高度
            int[] imageArraySecond = new int[widthTwo * heightTwo];
            imageArraySecond = imageSecond.getRGB(0, 0, widthTwo, heightTwo, imageArraySecond, 0, widthTwo);

            int h = height;
            if (height < heightTwo) {
                h = heightTwo;
            }

            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width + widthTwo, h, BufferedImage.TYPE_INT_RGB);
            imageResult.setRGB(0, 0, width, height, imageArrayFirst, 0, width);// 设置左半部分的RGB
            imageResult.setRGB(width, 0, widthTwo, heightTwo, imageArraySecond, 0, widthTwo);// 设置右半部分的RGB
            File outFile = new File(out);
            ImageIO.write(imageResult, "jpg", outFile);// 写图片
        } catch (Exception e) {
            logger.error("横向合成图片出错....", e);
        }
    }

    /**
     * 纵向合成图片
     * 
     * @param first
     *            放上面的图片路径
     * @param second
     *            放下面的图片路径
     * @param out
     *            文件输出目录
     * @param border
     *            图片预留边框
     */
    public static boolean yPic(String first, String second, String out, int border) {
        boolean isOk = true;
        try {
            /* 1 读取第一张图片 */

            File fileOne = new File(first);
            BufferedImage imageFirst = ImageIO.read(fileOne);
            int width = imageFirst.getWidth();// 图片宽度
            int height = imageFirst.getHeight();// 图片高度

            /* 2对第二张图片做相同的处理 */

            File fileTwo = new File(second);

            BufferedImage imageSecond = ImageIO.read(fileTwo);
            int widthTwo = imageSecond.getWidth();// 图片宽度
            int heightTwo = imageSecond.getHeight();// 图片高度
            /* 1 读取第一张图片begin */
            int t_height = y_height - heightTwo;
            // 图片是横图，逆时针旋转90度再等比缩放
            if (width > height) {
                imageFirst = rotateImageLeft90(imageFirst);
            }

            // 等比缩放
            imageFirst = resize(imageFirst, y_width, t_height);

            // 缩放后图片的大小
            width = imageFirst.getWidth();// 图片宽度
            height = imageFirst.getHeight();// 图片高度
            // 等比缩放后，图片还是太大，裁剪图片
            boolean a_w, a_h = false;
            if ((a_w = (width > y_width)) || (a_h = (height > t_height))) {
                // 起始位置x,y坐标
                int s_w = 0, s_h = 0;
                // 裁剪x坐标时，缩进属性x_retract
                if (a_w) {
                    int temp = width - y_width;
                    if (temp > x_retract) {
                        temp = x_retract;
                    } else {
                        temp = 0;
                    }
                    s_w = s_w + temp;
                }
                // 裁剪y坐标时，缩进属性y_retract
                if (a_h) {
                    int temp = height - t_height;
                    if (temp > y_retract) {
                        temp = y_retract;
                    } else {
                        temp = 0;
                    }
                    s_h = s_h + temp;
                }
                imageFirst = crop(imageFirst, s_w, s_h, y_width, t_height);
                width = imageFirst.getWidth();
                height = imageFirst.getHeight();
            }

            int[] imageArrayFirst = new int[(width - border) * height];// 从图片中读取RGB
            imageArrayFirst = imageFirst.getRGB(border, 0, (width - border), height, imageArrayFirst, 0,
                    (width - border));

            /* 2对第二张图片做相同的处理begin */
            int[] imageArraySecond = new int[widthTwo * heightTwo];
            imageArraySecond = imageSecond.getRGB(0, 0, widthTwo, heightTwo, imageArraySecond, 0, widthTwo);

            int w = width;
            if (width < widthTwo) {
                w = widthTwo;
            }
            // 图片高度
            int h = height + heightTwo;
            // 生成新图片
            BufferedImage imageResult = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 解决黑色背景,默认的TYPE_INT_RGB都是0，都是黑色的
            Graphics2D g = (Graphics2D) imageResult.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, w, h);// 填充整个屏幕
            g.dispose();

            // 留边框
            imageResult.setRGB(border, 0, (width - border * 2), height, imageArrayFirst, 0, (width - border));// 设置左半部分的RGB
            imageResult.setRGB(0, height, widthTwo, heightTwo, imageArraySecond, 0, widthTwo);// 设置右半部分的RGB

            File outFile = new File(out);
            ImageIO.write(imageResult, "jpg", outFile);// 写图片
        } catch (Exception e) {
            logger.error("纵向合成图片失败....", e);
            isOk = false;
        }
        return isOk;
    }

    /**
     * 全图打印，图片缩放、旋转处理
     * 
     * @param source
     *            待处理的图片
     * @param out
     *            处理后文件输出目录
     * @param border
     *            图片预留边框
     */
    public static boolean maigaoPic(String source, String out, int border) {
        boolean isOk = true;
        try {
            /* 1 读取第一张图片 */

            File fileOne = new File(source);
            BufferedImage imageFirst = ImageIO.read(fileOne);
            int width = imageFirst.getWidth();// 图片宽度
            int height = imageFirst.getHeight();// 图片高度

            // 图片是横图，逆时针旋转90度再等比缩放
            if (width > height) {
                imageFirst = rotateImageLeft90(imageFirst);
            }
            width = imageFirst.getWidth();// 图片宽度
            height = imageFirst.getHeight();// 图片高度s
            System.out.println("width#" + width);
            System.out.println("height#" + height);
            double h_w = ((double) height / (double) width);
            y_width = width / 4;
            y_height = (int) (y_width * h_w);
            System.out.println("h_w#" + h_w);
            System.out.println("y_width#" + y_width);
            System.out.println("y_height#" + y_height);
            // 等比缩放
            imageFirst = resize(imageFirst, y_width, y_height);

            // 缩放后图片的大小
            width = imageFirst.getWidth();// 图片宽度
            height = imageFirst.getHeight();// 图片高度
            // 等比缩放后，图片还是太大，裁剪图片
            // boolean a_w, a_h = false;
            // if ((a_w = (width > y_width)) || (a_h = (height > y_height))) {
            // // 起始位置x,y坐标
            // int s_w = 0, s_h = 0;
            // // 裁剪x坐标时，缩进属性x_retract
            // if (a_w) {
            // int temp = width - y_width;
            // if (temp > x_retract) {
            // temp = x_retract;
            // } else {
            // temp = 0;
            // }
            // s_w = s_w + temp;
            // }
            // // 裁剪y坐标时，缩进属性y_retract
            // if (a_h) {
            // int temp = height - y_height;
            // if (temp > y_retract) {
            // temp = y_retract;
            // } else {
            // temp = 0;
            // }
            // s_h = s_h + temp;
            // }
            // imageFirst = crop(imageFirst, s_w, s_h, y_width, y_height);
            // width = imageFirst.getWidth();
            // height = imageFirst.getHeight();
            // }

            int[] imageArrayFirst = new int[(width - border) * height];// 从图片中读取RGB
            imageArrayFirst = imageFirst.getRGB(border, 0, (width - border), height, imageArrayFirst, 0,
                    (width - border));

            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // 解决黑色背景,默认的TYPE_INT_RGB都是0，都是黑色的
            Graphics2D g = (Graphics2D) imageResult.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);// 填充整个屏幕
            g.dispose();

            // 留边框
            imageResult.setRGB(border, 0, (width - border * 2), height, imageArrayFirst, 0, (width - border));// 设置左半部分的RGB

            File outFile = new File(out);
            ImageIO.write(imageResult, "jpg", outFile);// 写图片
        } catch (IOException e) {
            logger.error("全图打印，图片缩放、旋转处理失败....", e);
            isOk = false;
        }
        return isOk;
    }

    /**
     * 实现图像的等比缩放
     * 
     * @param source
     *            待处理的图片流
     * @param targetW
     *            宽度
     * @param targetH
     *            高度
     * @return
     */
    public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        int width = source.getWidth();// 图片宽度
        int height = source.getHeight();// 图片高度
        return zoomInImage(source, targetW, targetH);
        // 图片宽高都太小时，强制放大图片
        /*
         * if (width < targetW && height < targetH) { return zoomInImage(source,
         * targetW, targetH); } else if ((width < targetW && width == height) ||
         * (height < targetH && width == height)) { return zoomInImage(source,
         * targetW, targetH); } return null;
         */

    }

    /**
     * 按比例裁剪图片
     * 
     * @param source
     *            待处理的图片流
     * @param startX
     *            开始x坐标
     * @param startY
     *            开始y坐标
     * @param endX
     *            结束x坐标
     * @param endY
     *            结束y坐标
     * @return
     */
    public static BufferedImage crop(BufferedImage source, int startX, int startY, int endX, int endY) {
        int width = source.getWidth();
        int height = source.getHeight();

        if (startX <= -1) {
            startX = 0;
        }
        if (startY <= -1) {
            startY = 0;
        }
        if (endX <= -1) {
            endX = width - 1;
        }
        if (endY <= -1) {
            endY = height - 1;
        }
        BufferedImage result = new BufferedImage(endX, endY, source.getType());
        for (int y = startY; y < endY + startY; y++) {
            for (int x = startX; x < endX + startX; x++) {
                int rgb = source.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }

    /**
     * 旋转图片为指定角度
     * 
     * @param bufferedimage
     *            目标图像
     * @param degree
     *            旋转角度
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedimage, final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(h, w, type)).createGraphics())
                .setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2 + (w > h ? (w - h) / 2 : (h - w) / 2));
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 图片左转90度
     * 
     * @param bufferedimage
     * @return
     */
    public static BufferedImage rotateImageLeft90(BufferedImage bufferedimage) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(h, w, type)).createGraphics())
                .setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(270), w / 2, h / 2 + (w - h) / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 图片右转90度
     * 
     * @param bufferedimage
     * @return
     */
    public static BufferedImage rotateImageRight90(BufferedImage bufferedimage) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(h, w, type)).createGraphics())
                .setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(90), w / 2 - (w - h) / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    // 对转
    public File rotateImageOppo(File file) throws Exception {
        BufferedImage bufferedimage = ImageIO.read(file);
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())
                .setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(180), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        ImageIO.write(img, "jpg", file);
        return file;
    }

    /***
     * 图片镜像处理
     * 
     * @param file
     * @param FX
     *            0 为上下反转 1 为左右反转
     * @return
     */
    public void imageMisro(File file, int FX) {
        try {
            BufferedImage bufferedimage = ImageIO.read(file);
            int w = bufferedimage.getWidth();
            int h = bufferedimage.getHeight();
            int[][] datas = new int[w][h];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    datas[j][i] = bufferedimage.getRGB(j, i);
                }
            }
            int[][] tmps = new int[w][h];
            if (FX == 0) {
                for (int i = 0, a = h - 1; i < h; i++, a--) {
                    for (int j = 0; j < w; j++) {
                        tmps[j][a] = datas[j][i];
                    }
                }
            } else if (FX == 1) {
                for (int i = 0; i < h; i++) {
                    for (int j = 0, b = w - 1; j < w; j++, b--) {
                        tmps[b][i] = datas[j][i];
                    }
                }
            }
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    bufferedimage.setRGB(j, i, tmps[j][i]);
                }
            }
            ImageIO.write(bufferedimage, "jpg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对图片进行强制放大或缩小
     * 
     * @param originalImage
     *            原始图片
     * @return
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());

        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    /**
     * 简易图片识别原理
     * 
     * @param img
     *            图片路径
     */
    public static void discernImg(String img) {
        try {
            File fileOne = new File(img);
            BufferedImage bi = ImageIO.read(fileOne);
            // 获取图像的宽度和高度
            int width = bi.getWidth();
            int height = bi.getHeight();

            // 扫描图片
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {// 行扫描
                    int dip = bi.getRGB(j, i);
                    if (dip == -1)
                        System.out.print(" ");
                    else
                        System.out.print("♦");
                }
                System.out.println();// 换行
            }
        } catch (Exception e) {
            logger.error("图片识别出错", e);
        }
    }

}
