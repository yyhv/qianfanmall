package com.lyqf.qianfanmall.admin.dto;

import com.lyqf.qianfanmall.db.domain.QianfanmallGoods;
import com.lyqf.qianfanmall.db.domain.QianfanmallGoodsAttribute;
import com.lyqf.qianfanmall.db.domain.QianfanmallGoodsProduct;
import com.lyqf.qianfanmall.db.domain.QianfanmallGoodsSpecification;

public class GoodsAllinone {
    QianfanmallGoods goods;
    QianfanmallGoodsSpecification[] specifications;
    QianfanmallGoodsAttribute[] attributes;
    QianfanmallGoodsProduct[] products;

    public QianfanmallGoods getGoods() {
        return goods;
    }

    public void setGoods(QianfanmallGoods goods) {
        this.goods = goods;
    }

    public QianfanmallGoodsProduct[] getProducts() {
        return products;
    }

    public void setProducts(QianfanmallGoodsProduct[] products) {
        this.products = products;
    }

    public QianfanmallGoodsSpecification[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(QianfanmallGoodsSpecification[] specifications) {
        this.specifications = specifications;
    }

    public QianfanmallGoodsAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(QianfanmallGoodsAttribute[] attributes) {
        this.attributes = attributes;
    }

}
