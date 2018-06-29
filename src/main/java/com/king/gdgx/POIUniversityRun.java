package com.king.gdgx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Dao;

import com.king.db.DaoUtil;
import com.king.gdgx.model.POIUniversity;

public class POIUniversityRun {
    static Dao dao = DaoUtil.getDao();
    public static void main(String[] args) throws IOException {
        File file = new File(
                "C:/Users/ThinkPad User/Desktop/Img/GDGX/poi_university.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str = null;
        List<POIUniversity> schools = new ArrayList<POIUniversity>();
        while ((str = reader.readLine()) != null) {
            System.out.println(str);// 此时str就保存了一行字符串
            String[] split = str.split("##");
            if (split.length != 3) {
                System.out.println(String.format("数据不符合规范%s", split));
                continue;
            }
            POIUniversity item = new POIUniversity();
            item.setName(split[0]);
            item.setLat(split[1]);
            item.setLng(split[2]);
            schools.add(item);
        }
        dao.insert(schools);
        System.out.println("OK");
    }
}
