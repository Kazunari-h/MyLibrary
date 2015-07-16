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
        if (title !=null) {
            return title;
        }else{
            return "";

        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleKana() {
        if (titleKana !=null) {
            return titleKana;
        }else{
            return "";
        }
    }

    public void setTitleKana(String titleKana) {
        this.titleKana = titleKana;
    }

    public String getAuthor() {
        if (author !=null) {
            return author;
        }else{
            return "";
        }

    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorKana() {
        if (authorKana !=null) {
            return authorKana;
        }else{
            return "";
        }
    }

    public void setAuthorKana(String authorKana) {
        this.authorKana = authorKana;
    }

    public String getPublisherName() {
        if (publisherName !=null) {
            return publisherName;
        }else{
            return "";
        }
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getItemCaption() {
        if (itemCaption !=null) {
            return itemCaption;
        }else{
            return "";
        }
    }

    public void setItemCaption(String itemCaption) {
        this.itemCaption = itemCaption;
    }

    public String getSalesDate() {
        if (salesDate !=null) {
            return salesDate;
        }else{
            return "";
        }
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    public String getItemURL() {
        if (itemURL !=null) {
            return itemURL;
        }else{
            return "";
        }
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }

    public String getSmallImageURL() {
        if (smallImageURL !=null) {
            return smallImageURL;
        }else{
            return "";
        }
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getLending() {
        if (lending !=null) {
            return lending;
        }else{
            return "";
        }
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
        if (isbn !=null) {
            return isbn;
        }else{
            return "";
        }
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
