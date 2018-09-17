package com.king.baidubaike;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;

import com.king.baidubaike.model.BaiKe;
import com.king.baidubaike.model.BaiKeDirectory;
import com.king.db.DaoUtil;
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
public class BDBK_Selenium {
    static Logger LOG = BeanUtil.getLOG(BDBK_Selenium.class);
    static Dao dao = DaoUtil.getDao();
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",GlobalContext.CHROME_DRIVER);
//        dao.create(BaiKe.class, true);
//        dao.create(BaiKeDirectory.class, true);
//        dao.clear(BaiKe.class);
//        dao.clear(BaiKeDirectory.class);
//        File file = new File("C:/Users/ThinkPad User/Desktop/鸟监控/星海湖鸟的种类");
//        if(file.isDirectory()){
//            File[] listFiles = file.listFiles();
//            for (int i = 0; i < listFiles.length; i++) {
//                File img = listFiles[i];
//                String name = img.getName().replace(".jpg", "");
//                System.out.println(name);
//                if ("凤头".equals(name)) {
//                    continue;
//                }
//                runWeb(name);
//            }
//        }
        String [] birds = new String  []{
                "小䴙䴘",
                "凤头䴙䴘",
                "黑颈䴙䴘",
                "卷羽鹈鹕",
                "普通鸬鹚",
                "苍鹭",
                "草鹭",
                "大白鹭",
                "中白鹭",
                "白鹭",
                "牛背鹭",
                "池鹭",
                "绿鹭",
                "夜鹭",
                "黄斑苇鳽",
                "紫背苇鳽",
                "栗苇鳽",
                "大麻鳽",
                "黑鹳",
                "东方白鹳",
                "白琵鹭",
                "疣鼻天鹅",
                "大天鹅",
                "小天鹅",
                "鸿雁",
                "豆雁",
                "白额雁",
                "灰雁",
                "赤麻鸭",
                "翘鼻麻鸭",
                "鸳鸯",
                "赤颈鸭",
                "罗文鸭",
                "赤膀鸭",
                "花脸鸭",
                "绿翅鸭",
                "绿头鸭",
                "斑嘴鸭",
                "针尾鸭",
                "白眉鸭",
                "琵嘴鸭",
                "赤嘴潜鸭",
                "红头潜鸭",
                "青头潜鸭",
                "白眼潜鸭",
                "凤头潜鸭",
                "鹊鸭",
                "斑头秋沙鸭",
                "普通秋沙鸭",
                "鹗",
                "凤头蜂鹰",
                "黑鸢",
                "白尾海雕",
                "秃鹫",
                "短趾雕",
                "白腹鹞",
                "白尾鹞",
                "鹊鹞",
                "赤腹鹰",
                "日本松雀鹰",
                "雀鹰",
                "苍鹰",
                "灰脸鵟鹰",
                "普通鵟",
                "大鵟",
                "毛脚鵟",
                "乌雕",
                "草原雕",
                "白肩雕",
                "金雕",
                "靴隼雕",
                "黄爪隼",
                "红隼",
                "红脚隼",
                "灰背隼",
                "燕隼",
                "猎隼",
                "游隼",
                "石鸡",
                "斑翅山鹑",
                "日本鹌鹑",
                "勺鸡",
                "褐马鸡",
                "环颈雉",
                "黄脚三趾鹑",
                "白枕鹤",
                "灰鹤",
                "白头鹤",
                "普通秧鸡",
                "白胸苦恶鸟",
                "小田鸡",
                "红胸田鸡",
                "董鸡",
                "黑水鸡",
                "骨顶鸡",
                "大鸨",
                "鹮嘴鹬",
                "黑翅长脚鹬",
                "反嘴鹬",
                "普通燕鸻",
                "凤头麦鸡",
                "灰头麦鸡",
                "金鸻",
                "长嘴剑鸻",
                "金眶鸻",
                "环颈鸻",
                "蒙古沙鸻",
                "铁嘴沙鸻",
                "东方鸻",
                "丘鹬",
                "孤沙雉",
                "扇尾沙雉",
                "黑尾塍鹬",
                "白腰杓鹬",
                "鹤鹬",
                "红脚鹬",
                "青脚鹬",
                "泽鹬",
                "白腰草鹬",
                "林鹬",
                "矶鹬",
                "青脚滨鹬",
                "长趾滨鹬",
                "尖尾滨鹬",
                "弯嘴滨鹬",
                "红颈滨鹬",
                "黑腹滨鹬",
                "黑尾鸥",
                "银鸥",
                "红嘴鸥",
                "普通燕鸥",
                "白额燕鸥",
                "须浮鸥",
                "毛腿沙鸡",
                "岩鸽",
                "山斑鸠",
                "灰斑鸠",
                "火斑鸠",
                "珠颈斑鸠",
                "大鹰鹃",
                "大杜鹃",
                "四声杜鹃",
                "东方中杜鹃",
                "噪鹃",
                "领角鸮",
                "红角鸮",
                "雕鸮",
                "灰林鸮",
                "纵纹腹小鸮",
                "鹰鸮",
                "长耳鸮",
                "短耳鸮",
                "普通夜鹰",
                "白喉针尾雨燕",
                "普通雨燕",
                "白腰雨燕",
                "普通翠鸟",
                "蓝翡翠",
                "冠鱼狗",
                "三宝鸟",
                "戴胜",
                "蚁鴷",
                "星头啄木鸟",
                "棕腹啄木鸟",
                "大斑啄木鸟",
                "灰头绿啄木鸟",
                "蒙古百灵",
                "大短趾百灵",
                "短趾百灵",
                "凤头百灵",
                "云雀",
                "角百灵",
                "崖沙燕",
                "岩燕",
                "家燕",
                "金腰燕",
                "毛脚燕",
                "山鹡鸰",
                "黄头鹡鸰",
                "白鹡鸰",
                "黄鹡鸰",
                "灰鹡鸰",
                "田鹨",
                "布氏鹨",
                "树鹨",
                "红喉鹨",
                "水鹨",
                "黄腹鹨",
                "长尾山椒鸟",
                "白头鹎",
                "太平鸟",
                "小太平鸟",
                "牛头伯劳",
                "红尾伯劳",
                "契尾伯劳",
                "黑枕黄鹂",
                "黑卷尾",
                "发冠卷尾",
                "八哥",
                "北椋鸟",
                "丝光椋鸟",
                "灰椋鸟",
                "紫翅椋鸟",
                "松鸦",
                "灰喜鹊",
                "红嘴蓝鹊",
                "喜鹊",
                "星鸦",
                "红嘴山鸦",
                "达乌里寒鸦",
                "秃鼻乌鸦",
                "小嘴乌鸦",
                "大嘴乌鸦",
                "褐河乌",
                "鹪鹩",
                "领岩鹨",
                "棕眉山岩鹨",
                "红喉歌鸲",
                "蓝喉歌鸲",
                "蓝歌鸲",
                "红尾歌鸲",
                "红胁蓝尾鸲",
                "北红尾鸲",
                "红尾水鸲",
                "白顶溪鸲",
                "黑喉石即",
                "白顶即",
                "白喉矶鸫",
                "蓝矶鸫",
                "紫啸鸫",
                "白眉地鸫",
                "虎斑地鸫",
                "灰背鸫",
                "褐头鸫",
                "白眉鸫",
                "赤劲鸫",
                "红尾鸫",
                "斑鸫",
                "乌鸫",
                "宝兴歌鸫",
                "灰纹鹟",
                "乌鹟",
                "北灰鹟",
                "白眉姬鹟",
                "绿背姬鹟",
                "红喉姬鹟",
                "白腹蓝姬鹟",
                "寿带",
                "山噪鹛",
                "棕头鸦雀",
                "棕扇尾莺",
                "山鹛",
                "鳞头树莺",
                "远东树莺",
                "矛斑蝗莺",
                "小蝗莺",
                "黑眉苇莺",
                "东方大苇莺",
                "厚嘴苇莺",
                "褐柳莺",
                "巨嘴柳莺",
                "黄腰柳莺",
                "云南柳莺",
                "黄眉柳莺",
                "淡眉柳莺",
                "极北柳莺",
                "双斑绿柳莺",
                "淡脚柳莺",
                "冕柳莺",
                "冠纹柳莺",
                "戴菊",
                "红胁绣眼鸟",
                "暗绿绣眼鸟",
                "中华攀雀",
                "银喉长尾山雀",
                "沼泽山雀",
                "褐头山雀",
                "煤山雀",
                "黄腹山雀",
                "大山雀",
                "普通鳾",
                "黑头鳾",
                "红翅旋壁雀",
                "欧亚旋木雀",
                "山麻雀",
                "麻雀",
                "苍头燕雀",
                "燕雀",
                "普通朱雀",
                "红眉朱雀",
                "北朱雀",
                "红交嘴雀",
                "白腰朱顶雀",
                "黄雀",
                "金翅雀",
                "锡嘴雀",
                "黑尾蜡嘴雀",
                "黑头蜡嘴雀",
                "长尾雀",
                "白头鵐",
                "灰眉岩鵐/戈氏岩鵐",
                "三道眉草鵐",
                "白眉鵐",
                "栗耳鵐",
                "小鵐",
                "黄眉鵐",
                "田鵐",
                "黄喉鵐",
                "黄胸鵐",
                "栗鵐",
                "灰头鵐",
                "红颈苇鵐",
                "苇鵐",
                "芦鵐",
                "铁爪鵐",
        };
        for (int i = 0; i < birds.length; i++) {
            String name = birds[i];
            runWeb(name);
        }
    }

    private static void runWeb(String theName) {
        //检测是否存在
        List<BaiKe> query = dao.query(BaiKe.class, Cnd.where("name", "=", theName));
        if(query!=null && query.size()>0){
            System.out.println("数据已经存在："  + theName);
            return;
        }
        WebDriver driver = new ChromeDriver();
        String url = "http://baike.baidu.com/item/%s";
        //
        String dirBase = GlobalContext.DIR_BAIDU_BAIKE;
        driver.get(String.format(url, theName));
        //
        BaiKe baiKe = new BaiKe();
        //
        System.out.println("----------------------------------------------------------------");
        String name = driver.findElement(By.tagName("h1")).getText();
        System.out.println("名称#" + name);
        
        //
        baiKe.setName(name);
        System.out.println("----------------------------------------------------------------");
        String jianjie = driver.findElement(By.cssSelector(".para")).getText();
        System.out.println("简介#"+jianjie);
        baiKe.setSummary(jianjie);
        System.out.println("----------------------------------------------------------------");
        List<WebElement> xiangxi_name = driver.findElements(By.cssSelector("dt.basicInfo-item.name"));
        List<WebElement> xiangxi_value = driver.findElements(By.cssSelector("dd.basicInfo-item.value"));
        for (int i = 0; i < xiangxi_name.size(); i++) {
            WebElement nameEL = xiangxi_name.get(i);
            WebElement valueEL = xiangxi_value.get(i);
            System.out.println(nameEL.getText() + ":" + valueEL.getText());
            setValue(baiKe,nameEL.getText(),valueEL.getText());
        }
        WebElement imgEL = null;
        try {
            imgEL = driver.findElement(By.cssSelector("div.summary-pic > a > img"));
        } catch (Exception e) {
            LOG.error("图片未取到");
            try {
                imgEL = driver.findElement(By.cssSelector("div.album-wrap img"));
            } catch (Exception e2) {
                LOG.error("图片完全未取到");
            }
        }
        String imgURL = null;
        if (imgEL != null) {
            imgURL = imgEL.getAttribute("src");
            baiKe.setImage(imgURL);
        }
//        System.out.println("----------------------------------------------------------------");
//        List<WebElement> mulu = driver.findElements(By.cssSelector("h2.title-text"));
//        for (int i = 0; i < mulu.size(); i++) {
//            WebElement itme = mulu.get(i);
//            List<WebElement> mulu_item = itme.findElements(By.cssSelector("div.para"));
//            System.out.println(""+mulu_item.size());
//            System.out.println(itme.getText());
//            System.out.println("----------------------------------------------------------------");
//        }
//        System.out.println("----------------------------------------------------------------");
//        List<WebElement> mulu_item = driver.findElements(By.cssSelector("div.para"));
//        for (int i = 0; i < mulu_item.size(); i++) {
//            WebElement itme = mulu_item.get(i);
//            System.out.println(itme.getText());
//            System.out.println("---------------------------------------");
//        }
        dao.insert(baiKe);
        BaiKeDirectory  baiKeDirectory = null;
        List<WebElement> titles = driver.findElements(By.cssSelector(".main-content > div, .main-content > ul"));
        String firstName = null;
        for (WebElement webElement : titles) {
            if("para-title level-2".equals(webElement.getAttribute("class"))) {
                firstName = webElement.findElement(By.cssSelector(".title-text")).getText();
                System.out.println("--------------------主目录：" + firstName + "--------------------------------");
                if (baiKeDirectory == null) {//第一次
                    baiKeDirectory = new BaiKeDirectory();
                } else {
                    dao.insert(baiKeDirectory);
                    baiKeDirectory = new BaiKeDirectory();
                }
                baiKeDirectory.setName(firstName);
                baiKeDirectory.setBaike_id(baiKe.getId());
            }
            if (baiKeDirectory == null) {
                driver.quit();
                LOG.warn("数据不存在");
                return;
            }
            if("para-title level-3".equals(webElement.getAttribute("class"))) {
                String text = webElement.findElement(By.cssSelector(".title-text")).getText();
                System.out.println("--------------------子目录:" + text + "--------------------------------");
                if (StringUtils.isNotBlank(baiKeDirectory.getSub_name())) {
                    dao.insert(baiKeDirectory);
                    baiKeDirectory = new BaiKeDirectory();
                }
                baiKeDirectory.setName(firstName);
                baiKeDirectory.setBaike_id(baiKe.getId());
                baiKeDirectory.setSub_name(text);
            }
            if("para".equals(webElement.getAttribute("class"))) {
                String text2 = webElement.getText();
                if(StringUtils.isBlank(baiKeDirectory.getContent())){
                    baiKeDirectory.setContent(text2);
                } else {
                    baiKeDirectory.setContent(baiKeDirectory.getContent() + text2);
                }
                System.out.println(text2);
            }
            if("ul".equals(webElement.getTagName())) {
                String attribute = webElement.getAttribute("outerHTML");
                if(StringUtils.isBlank(baiKeDirectory.getContent())){
                    baiKeDirectory.setContent(attribute);
                } else {
                    baiKeDirectory.setContent(baiKeDirectory.getContent() + attribute);
                }
                System.out.println(attribute);
            }
        }
        //
        BeanUtil.createDir(dirBase);
        if (StringUtils.isNotBlank(imgURL)) {
            BeanUtil.saveWebFileT(imgURL, dirBase, "-1", "jpg");
        }
        System.out.println("---------------------------------------");
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void setValue(BaiKe baiKe, String name, String value) {
        name = name.replace(" ", "");
        if ("中文学名".equals(name)) {
            baiKe.setChinese_name(value);
        } else if ("拉丁学名".equals(name)) {
            baiKe.setLatin_name(value);
        } else if ("别称".equals(name)) {
            baiKe.setAlias(value);
        } else if ("界".equals(name)) {
            baiKe.setCommunity(value);
        } else if ("门".equals(name)) {
            baiKe.setDoor(value);
        } else if ("亚门".equals(name)) {
            baiKe.setSub_door(value);
        } else if ("纲".equals(name)) {
            baiKe.setGang(value);
        } else if ("亚纲".equals(name)) {
            baiKe.setSubclass(value);
        } else if ("目".equals(name)) {
            baiKe.setHead(value);
        } else if ("科".equals(name)) {
            baiKe.setSection(value);
        } else if ("属".equals(name)) {
            baiKe.setGenus(value);
        } else if ("种".equals(name)) {
            baiKe.setSpecies(value);
        } else if ("分布区域".equals(name)) {
            baiKe.setDistribution_area(value);
        } else if ("命名者及年代".equals(name)) {
            baiKe.setNanmed_and_chronicle(value);
        } else if ("英文名称".equals(name)) {
            if(baiKe.getEnglish_name()==null){
                baiKe.setEnglish_name(value);
            } else {
                baiKe.setEnglish_name(baiKe.getEnglish_name() + "#" + value);
            }
            
        }
    }

}
