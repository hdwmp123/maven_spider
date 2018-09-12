package com.king.yuanmingyuan.model;

import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Next;
import org.nutz.dao.entity.annotation.Table;

@Table("sql_table")
public class SqlTable {
    @Id
    @Next
    private int id;
    private String sql_str;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSql_str() {
        return sql_str;
    }

    public void setSql_str(String sql_str) {
        this.sql_str = sql_str;
    }

}
