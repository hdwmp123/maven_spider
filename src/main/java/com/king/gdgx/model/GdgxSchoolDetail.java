package com.king.gdgx.model;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Next;
import org.nutz.dao.entity.annotation.Table;

@Table("gdgx_school_detail")
public class GdgxSchoolDetail {
    @Id
    @Next
    private int id;
    private int school_id;
    private String name;
    private String logo;
    private String jj;
    private String cjsj;
    private String zdxk;
    private String lsy;
    private String xxlx;
    private String xsrs;
    private String bsdgs;
    private String ysrs;
    private String ssdgs;
    private String jyqk;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getJj() {
        return jj;
    }

    public void setJj(String jj) {
        this.jj = jj;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getZdxk() {
        return zdxk;
    }

    public void setZdxk(String zdxk) {
        this.zdxk = zdxk;
    }

    public String getLsy() {
        return lsy;
    }

    public void setLsy(String lsy) {
        this.lsy = lsy;
    }

    public String getXxlx() {
        return xxlx;
    }

    public void setXxlx(String xxlx) {
        this.xxlx = xxlx;
    }

    public String getXsrs() {
        return xsrs;
    }

    public void setXsrs(String xsrs) {
        this.xsrs = xsrs;
    }

    public String getBsdgs() {
        return bsdgs;
    }

    public void setBsdgs(String bsdgs) {
        this.bsdgs = bsdgs;
    }

    public String getYsrs() {
        return ysrs;
    }

    public void setYsrs(String ysrs) {
        this.ysrs = ysrs;
    }

    public String getSsdgs() {
        return ssdgs;
    }

    public void setSsdgs(String ssdgs) {
        this.ssdgs = ssdgs;
    }

    public String getJyqk() {
        return jyqk;
    }

    public void setJyqk(String jyqk) {
        this.jyqk = jyqk;
    }

}
