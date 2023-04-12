package com.example.springboot_vue.pojo.goods;

import java.util.ArrayList;

public class ReturnType {
    private int id;
    private int pageSize;
    private int pageNo;
    private int total;
    private int totalPage;
    private ArrayList<AttrsList> attrsList;
    private ArrayList<Goods> goodsList;
    private ArrayList<String> trademarkList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<AttrsList> getAttrsList() {
        return attrsList;
    }

    public void setAttrsList(ArrayList<AttrsList> attrsList) {
        this.attrsList = attrsList;
    }

    public ArrayList<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(ArrayList<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public ArrayList<String> getTrademarkList() {
        return trademarkList;
    }

    public void setTrademarkList(ArrayList<String> trademarkList) {
        this.trademarkList = trademarkList;
    }
}
