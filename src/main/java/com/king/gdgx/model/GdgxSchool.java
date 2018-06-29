package com.king.gdgx.model;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Next;
import org.nutz.dao.entity.annotation.Table;

@Table("gdgx_school")
public class GdgxSchool {
    @Id
    @Next
    private int id;
    private String name;
    private int in_order;
    private float totale_score;
    private float rcpy_score;
    private float kxyj_score;
    private String type;
    private String area;
    private String location;
    private String detail_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIn_order() {
        return in_order;
    }

    public void setIn_order(int in_order) {
        this.in_order = in_order;
    }

    public float getTotale_score() {
        return totale_score;
    }

    public void setTotale_score(float totale_score) {
        this.totale_score = totale_score;
    }

    public float getRcpy_score() {
        return rcpy_score;
    }

    public void setRcpy_score(float rcpy_score) {
        this.rcpy_score = rcpy_score;
    }

    public float getKxyj_score() {
        return kxyj_score;
    }

    public void setKxyj_score(float kxyj_score) {
        this.kxyj_score = kxyj_score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

}
