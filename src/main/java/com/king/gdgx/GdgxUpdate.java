package com.king.gdgx;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import com.king.db.DaoUtil;
import com.king.yhy.model.GGPoint;

public class GdgxUpdate {
    static Dao dao = DaoUtil.getDao();

    public static void main(String[] args) {
        List<GGPoint> query = dao.query(GGPoint.class, Cnd.where("platform_id", "=", 3));
        for (GGPoint ggPoint : query) {
            String img = ggPoint.getImg();
            String icon_path = img.substring(img.lastIndexOf("/")+1, img.length());
            System.out.println(icon_path);
            icon_path = icon_path.replace(".", "_32.");
            System.out.println(icon_path);
            System.out.println("---------------------");
            ggPoint.setIcon_path("/image/gdgx_icon/"+icon_path);
            dao.update(ggPoint);
        }
    }
}
