package jp.ac.hal.ths35033.mylibrary;

import java.io.Serializable;

/**
 * Created by hirosawak on 2015/07/12.
 */
public class Book implements Serializable {

    int _id;
    //種別
    int size;
    // 進行度
    int rate;
    //所持フラグ
    int haveFlg;
    // 価格
    int itemPrice;
    // タイトル
    String title;
    // タイトルカナ
    String titleKana;
    // 著者
    String author;
    // 著者カナ
    String authorKana;
    // 出版社
    String publisherName;
    // 説明
    String itemCaption;
    // ISBN
    String isbn;
    //発売日
    String salesDate;
    // URL
    String itemURL;
    // image
    String smallImageURL;
    //貸出
    String lending;
    //更新日
    String update;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getHaveFlg() {
        return haveFlg;
    }

    public void setHaveFlg(int haveFlg) {
        this.haveFlg = haveFlg;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleKana() {
        return titleKana;
    }

    public void setTitleKana(String titleKana) {
        this.titleKana = titleKana;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorKana() {
        return authorKana;
    }

    public void setAuthorKana(String authorKana) {
        this.authorKana = authorKana;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getItemCaption() {
        return itemCaption;
    }

    public void setItemCaption(String itemCaption) {
        this.itemCaption = itemCaption;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getLending() {
        return lending;
    }

    public void setLending(String lending) {
        this.lending = lending;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
