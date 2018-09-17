package com.king.baidubaike.model;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("om_baike_directory")
public class BaiKeDirectory {
    @Id
    private Long id;
    private Long baike_id;
    private String name;// '目录名称',
    private String sub_name;// '子目录',
    @ColDefine(type = ColType.TEXT)
    private String content;// '内容',
    @ColDefine(width = 1024)
    private String image;// '图片地址，以@号割',

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBaike_id() {
        return baike_id;
    }

    public void setBaike_id(Long baike_id) {
        this.baike_id = baike_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
