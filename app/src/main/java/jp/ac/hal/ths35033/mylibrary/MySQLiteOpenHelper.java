package jp.ac.hal.ths35033.mylibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hirosawak on 2015/07/11.
 */


public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    // コンストラクタ
    public MySQLiteOpenHelper( Context context ){
        // 任意のデータベースファイル名と、バージョンを指定する
        super(context, "sample.db", null, 1);
    }


    /**
     * このデータベースを初めて使用する時に実行される処理
     * テーブルの作成や初期データの投入を行う
     */
    @Override
    public void onCreate( SQLiteDatabase db ) {
        // テーブルを作成

        //==== 現在時刻を取得 ====//
        Date date = new Date();

        //==== 表示形式を設定 ====//
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");

        /*
        書籍タイトル title
        書籍タイトルカナ titleKana
        著者名	author
        著者名カナ	authorKana
        出版社名	publisherName
        書籍のサイズ	size	1:単行本 2:文庫 3:新書 4:全集・双書 5:事・辞典 6:図鑑 7:絵本 8:カセット,CD 9:コミック 10:その他
        ISBNコード(書籍コード)	isbn
        商品説明文	itemCaption
        発売日	salesDate
        税込み販売価格	itemPrice
        商品URL	itemUrl
        商品画像 	smallImageUrl

        所持フラグ
        貸出
        進行度
        更新日
         */

        db.execSQL("create table book_table ("
                        + " _id integer primary key autoincrement not null, "
                        + "title text not null, "
                        + "titleKana text, "
                        + "author text, "
                        + "authorKana text, "
                        + "publisherName text, "
                        + "size integer DEFAULT 0, "
                        + "isbn text, "
                        + "itemCaption text, "
                        + "salesDate text, "
                        + "itemPrice integer DEFAULT 0, "
                        + "itemURL text, "
                        + "smallImageURL text, "
                        + "haveFlg integer DEFAULT 0, "
                        + "lending text, "
                        + "rate integer DEFAULT 0, "
                        + "updateddate text "
                        + " );" );

        SQLiteStatement stmt = db.compileStatement("insert into book_table(title,titleKana,author,authorKana,publisherName,size,isbn,itemCaption,salesDate,itemPrice,itemURL,smallImageURL,haveFlg,lending,rate,updateddate) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");

        /*
        title,
        titleKana,
        author,
        authorKana,
        publisherName,
        size,
        isbn,
        itemCaption,
        salesDate,
        itemPrice,
        itemURL,
        smallImageURL,
        haveFlg,
        lending,
        rate,
        updateddate
         */

        //書籍タイトル title
        stmt.bindString(1, "PHP");
        //書籍タイトルカナ titleKana
        stmt.bindString(2, "kazu");
        //著者名	author
        stmt.bindString(3, "広澤和成");
        //著者名カナ	authorKana
        stmt.bindString(4, "ヒロサワカズナリ");
        //出版社名	publisherName
        stmt.bindString(5, "集英社");
        //書籍のサイズ	size	1:単行本 2:文庫 3:新書 4:全集・双書 5:事・辞典 6:図鑑 7:絵本 8:カセット,CD 9:コミック 10:その他
        stmt.bindLong(6, 1);
        //ISBNコード(書籍コード)	isbn
        stmt.bindString(7, "9784897978857");
        //商品説明文	itemCaption
        stmt.bindString(8, "プログラミングは全く初めての人、他の入門書で挫折しちゃった人、つまづく所はみんな同じです。そこを徹底分析したメソッドで、全員ゴールに辿り着ける入門教室が秋葉原にあります。１日でＷｅｂ画面と簡単なＤＢまで作れるようになるとってもユニークな速習コース。その方法を１冊に凝縮した本書なら、いきなりの初心者でも、無理せず楽しくＰＨＰとＭｙＳＱＬのエッセンスを習得できます。");
        //発売日	salesDate
        stmt.bindString(9, "2011/12/22");
        //税込み販売価格	itemPrice
        stmt.bindLong(10, 1900);
        //商品URL	itemUrl
        stmt.bindString(11, "http://www.ric.co.jp/book/contents/book_885.html");
        //商品画像 	smallImageUrl
        stmt.bindString(12, "");
        //所持フラグ
        stmt.bindLong(13, 1);
        //貸出
        stmt.bindString(14, "河西");
        //進行度
        stmt.bindLong(15, 80);
        //更新日
        stmt.bindString(16,sdf.format(date).toString());


        stmt.execute();
        //書籍タイトル title
        stmt.bindString(1, "Ruby");
        //書籍タイトルカナ titleKana
        stmt.bindString(2, "kazu");
        //著者名	author
        stmt.bindString(3, "広澤和成");
        //著者名カナ	authorKana
        stmt.bindString(4, "ヒロサワカズナリ");
        //出版社名	publisherName
        stmt.bindString(5, "集英社");
        //書籍のサイズ	size	1:単行本 2:文庫 3:新書 4:全集・双書 5:事・辞典 6:図鑑 7:絵本 8:カセット,CD 9:コミック 10:その他
        stmt.bindLong(6, 1);
        //ISBNコード(書籍コード)	isbn
        stmt.bindString(7, "9784897978857");
        //商品説明文	itemCaption
        stmt.bindString(8, "プログラミングは全く初めての人、他の入門書で挫折しちゃった人、つまづく所はみんな同じです。そこを徹底分析したメソッドで、全員ゴールに辿り着ける入門教室が秋葉原にあります。１日でＷｅｂ画面と簡単なＤＢまで作れるようになるとってもユニークな速習コース。その方法を１冊に凝縮した本書なら、いきなりの初心者でも、無理せず楽しくＰＨＰとＭｙＳＱＬのエッセンスを習得できます。");
        //発売日	salesDate
        stmt.bindString(9, "2011/12/22");
        //税込み販売価格	itemPrice
        stmt.bindLong(10, 1900);
        //商品URL	itemUrl
        stmt.bindString(11, "http://www.ric.co.jp/book/contents/book_885.html");
        //商品画像 	smallImageUrl
        stmt.bindString(12, "");
        //所持フラグ
        stmt.bindLong(13, 1);
        //貸出
        stmt.bindString(14, "河西");
        //進行度
        stmt.bindLong(15, 80);
        //更新日
        stmt.bindString(16,sdf.format(date).toString());
        stmt.execute();

        //書籍タイトル title
        stmt.bindString(1, "C++");
        //書籍タイトルカナ titleKana
        stmt.bindString(2, "kazu");
        //著者名	author
        stmt.bindString(3, "広澤和成");
        //著者名カナ	authorKana
        stmt.bindString(4, "ヒロサワカズナリ");
        //出版社名	publisherName
        stmt.bindString(5, "集英社");
        //書籍のサイズ	size	1:単行本 2:文庫 3:新書 4:全集・双書 5:事・辞典 6:図鑑 7:絵本 8:カセット,CD 9:コミック 10:その他
        stmt.bindLong(6, 1);
        //ISBNコード(書籍コード)	isbn
        stmt.bindString(7, "9784897978857");
        //商品説明文	itemCaption
        stmt.bindString(8, "プログラミングは全く初めての人、他の入門書で挫折しちゃった人、つまづく所はみんな同じです。そこを徹底分析したメソッドで、全員ゴールに辿り着ける入門教室が秋葉原にあります。１日でＷｅｂ画面と簡単なＤＢまで作れるようになるとってもユニークな速習コース。その方法を１冊に凝縮した本書なら、いきなりの初心者でも、無理せず楽しくＰＨＰとＭｙＳＱＬのエッセンスを習得できます。");
        //発売日	salesDate
        stmt.bindString(9, "2011/12/22");
        //税込み販売価格	itemPrice
        stmt.bindLong(10, 1900);
        //商品URL	itemUrl
        stmt.bindString(11, "http://www.ric.co.jp/book/contents/book_885.html");
        //商品画像 	smallImageUrl
        stmt.bindString(12, "");
        //所持フラグ
        stmt.bindLong(13, 1);
        //貸出
        stmt.bindString(14, "河西");
        //進行度
        stmt.bindLong(15, 80);
        //更新日
        stmt.bindString(16,sdf.format(date).toString());
        stmt.execute();

        //書籍タイトル title
        stmt.bindString(1, "Java");
        //書籍タイトルカナ titleKana
        stmt.bindString(2, "kazu");
        //著者名	author
        stmt.bindString(3, "広澤和成");
        //著者名カナ	authorKana
        stmt.bindString(4, "ヒロサワカズナリ");
        //出版社名	publisherName
        stmt.bindString(5, "集英社");
        //書籍のサイズ	size	1:単行本 2:文庫 3:新書 4:全集・双書 5:事・辞典 6:図鑑 7:絵本 8:カセット,CD 9:コミック 10:その他
        stmt.bindLong(6, 1);
        //ISBNコード(書籍コード)	isbn
        stmt.bindString(7, "9784897978857");
        //商品説明文	itemCaption
        stmt.bindString(8, "プログラミングは全く初めての人、他の入門書で挫折しちゃった人、つまづく所はみんな同じです。そこを徹底分析したメソッドで、全員ゴールに辿り着ける入門教室が秋葉原にあります。１日でＷｅｂ画面と簡単なＤＢまで作れるようになるとってもユニークな速習コース。その方法を１冊に凝縮した本書なら、いきなりの初心者でも、無理せず楽しくＰＨＰとＭｙＳＱＬのエッセンスを習得できます。");
        //発売日	salesDate
        stmt.bindString(9, "2011/12/22");
        //税込み販売価格	itemPrice
        stmt.bindLong(10, 1900);
        //商品URL	itemUrl
        stmt.bindString(11, "http://www.ric.co.jp/book/contents/book_885.html");
        //商品画像 	smallImageUrl
        stmt.bindString(12, "");
        //所持フラグ
        stmt.bindLong(13, 1);
        //貸出
        stmt.bindString(14, "河西");
        //進行度
        stmt.bindLong(15, 80);
        //更新日
        stmt.bindString(16,sdf.format(date).toString());
        stmt.execute();


    }


    /**
     * アプリケーションの更新などによって、データベースのバージョンが上がった場合に実行される処理
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}