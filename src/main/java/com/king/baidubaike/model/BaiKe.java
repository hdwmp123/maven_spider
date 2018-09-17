package com.king.baidubaike.model;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Next;
import org.nutz.dao.entity.annotation.Table;

@Table("om_baike")
public class BaiKe {
    @Id
    @Next
    private Long id;
    private String name;// 名称
    @ColDefine(type = ColType.TEXT)
    private String summary;// '简介';
    private String chinese_name;// '中文学名';
    private String latin_name;// '拉丁学名';
    private String alias;// '别称';
    private String community;// '界';
    private String door;// '门';
    private String sub_door;
    private String gang;// '纲';
    private String subclass;// '亚纲';
    private String head;// '目';
    private String section;// '科';
    private String genus;// '属';
    private String species;// '种';
    private String distribution_area;// '分布区域';
    private String nanmed_and_chronicle;// '命名者及年代';
    private String english_name;// '英文名称';
    @ColDefine(width = 1024)
    private String image;// 图片地址以##分割

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getChinese_name() {
        return chinese_name;
    }

    public void setChinese_name(String chinese_name) {
        this.chinese_name = chinese_name;
    }

    public String getLatin_name() {
        return latin_name;
    }

    public void setLatin_name(String latin_name) {
        this.latin_name = latin_name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public String getSub_door() {
        return sub_door;
    }

    public void setSub_door(String sub_door) {
        this.sub_door = sub_door;
    }

    public String getGang() {
        return gang;
    }

    public void setGang(String gang) {
        this.gang = gang;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDistribution_area() {
        return distribution_area;
    }

    public void setDistribution_area(String distribution_area) {
        this.distribution_area = distribution_area;
    }

    public String getNanmed_and_chronicle() {
        return nanmed_and_chronicle;
    }

    public void setNanmed_and_chronicle(String nanmed_and_chronicle) {
        this.nanmed_and_chronicle = nanmed_and_chronicle;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
