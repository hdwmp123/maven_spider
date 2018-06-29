package com.king.yhy;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Dao;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.slf4j.Logger;

import com.king.db.DaoUtil;
import com.king.util.BeanUtil;
import com.king.yhy.model.GGPoint;

/**
 * 
 * @ClassName: YiHeYuanRun_Jsoup
 * @Description: 颐和园景点信息
 * @author wangmipeng
 * @date 2018年5月24日 下午4:38:53
 *
 */
public class YiHeYuanRun_Jsoup {
    static Logger LOG = BeanUtil.getLOG(YiHeYuanRun_Jsoup.class);
    
    public static void main(String[] args) {
        Dao dao = DaoUtil.getDao();
        String[] types = new String[] { "geo_toilet##5",// 卫生间
                "geo_snacks_restaurant##7", // 小吃
                "geo_food_restaurant##7",// 美食
                "geo_gift_store##6",// 商店
                "geo_mun_pos##2",// 出口
                "geo_pier_pos##4",// 码头
                "geo_scenic_pos##1", // 景点
        };
        List<GGPoint> points = new ArrayList<GGPoint>();
        for (int j = 0; j < types.length; j++) {
            String fm = "http://summerpalace-china.com:8090/geoserver/yhypark/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=yhypark:%s&outputFormat=application/json";
            String[] typeArr = types[j].split("##");
            String typeStr = typeArr[0];
            int typeInt = Integer.parseInt(typeArr[1]);
            String url = String.format(fm, typeStr);
            Response response = Http.get(url);
            String result = response.getContent();
            JSONObject json = JSONObject.fromObject(result);
            JSONArray features = json.getJSONArray("features");
            for (int i = 0; i < features.size(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject pp = feature.getJSONObject("properties");
                System.out.println(pp);
                //
                GGPoint po = new GGPoint();
                //
                if ("geo_toilet".equals(typeStr)) {
                    po.setTitle(pp.getString("toiletname"));
                    po.setDetails(String.format("是否分男女：%s##残疾人间（个）：%s##空调（台）：%s##洗手池：%s##", 
                            pp.getString("sexclass"),
                            pp.getString("disabilityNum"),
                            pp.getString("airNum"),
                            pp.getString("sinkNum")));
                } else if ("geo_snacks_restaurant".equals(typeStr)) {
                    po.setTitle(pp.getString("srname"));
                    po.setDetails(String.format("餐厅名称：%s##餐厅位置：%s##开门时间：%s##关门时间：%s##商品种类：%s##", 
                            pp.getString("srname"),
                            pp.getString("srlocation"),
                            pp.getString("opentime"),
                            pp.getString("closetime"),
                            pp.getString("dtype")));
                } else if ("geo_food_restaurant".equals(typeStr)) {
                    po.setTitle(pp.getString("frname"));
                    po.setDetails(String.format("餐厅名称：%s##餐厅位置：%s##开门时间：%s##关门时间：%s##菜品种类：%s##", 
                            pp.getString("frname"),
                            pp.getString("frlocation"),
                            pp.getString("opentime"),
                            pp.getString("closetime"),
                            pp.getString("dtype")));
                } else if ("geo_gift_store".equals(typeStr)) {
                    po.setTitle(pp.getString("gsname"));
                    po.setDetails(String.format("礼物店名称：%s##礼物店位置：%s##开门时间：%s##关门时间：%s##礼物种类：%s##", 
                            pp.getString("gsname"),
                            pp.getString("gslocation"),
                            pp.getString("opentime"),
                            pp.getString("closetime"),
                            pp.getString("dtype")));
                } else if ("geo_mun_pos".equals(typeStr)) {
                    po.setTitle(pp.getString("munname"));
                    po.setDetails(String.format("开门时间：%s##关门时间：%s##是否有停车场：%s##是否有地铁：%s##交通路线：%s##", 
                            pp.getString("opentime"),
                            pp.getString("closetime"),
                            pp.getString("parklot"),
                            pp.getString("subway"),
                            pp.getString("trafficline")));
                } else if ("geo_pier_pos".equals(typeStr)) {
                    po.setTitle(pp.getString("piername"));
                    po.setDetails(String.format("游船类型：%s##游船价格：%s##营业时间：%s##", 
                            pp.getString("piertype"),
                            pp.getString("pierprice"),
                            pp.getString("businesstime")));
                } else if ("geo_scenic_pos".equals(typeStr)) {
                    po.setTitle(pp.getString("scenicname"));
                    String information_url = pp.getString("information_url");
                    if(StringUtils.isNotBlank(information_url) && !"null".equals(information_url)){
                        po.setDetails("http://www.summerpalace-china.com"+information_url);
                    } else {
                        po.setDetails(po.getTitle());
                    }
                    String imgurl = pp.getString("imgurl");
                    if(StringUtils.isNotBlank(imgurl) && !"null".equals(imgurl)){
                        po.setImg("http://www.summerpalace-china.com"+imgurl);
                    }
                    String musicurl = pp.getString("musicurl");
                    if(StringUtils.isNotBlank(musicurl) && !"null".equals(musicurl)){
                        po.setAudio("http://www.summerpalace-china.com"+musicurl);
                    }
                }
                if(StringUtils.isBlank(po.getImg())){
                    po.setImg("https://www.abcchengyu.cn/gugong/static/images/platform1/img/author.jpg");
                }
                po.setTitle_en(po.getTitle());
                po.setDetails_en(po.getDetails());
                po.setPlatform_id(2);
                po.setType(typeInt);
                po.setLocation(pp.getString("lat") + "," + pp.getString("lng"));
                //
                points.add(po);
            }
            System.out.println("------------------------------");
        }
        //dao.clear(GGPoint.class);
        dao.fastInsert(points);
        System.out.println("OK");
    }
}
