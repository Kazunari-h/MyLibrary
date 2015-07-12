package jp.ac.hal.ths35033.mylibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

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
                        + "size integer, "
                        + "itemCaption text, "
                        + "salesDate text, "
                        + "itemPrice integer, "
                        + "itemURL text, "
                        + "smallImageURL text, "
                        + "haveFlg integer, "
                        + "lending text, "
                        + "rate integer, "
                        + "updateddate text "
                        + " );" );

        SQLiteStatement stmt = db.compileStatement("insert into book_table(title,author) values (?, ?);");
        stmt.bindString(1, "PHP");
        stmt.bindString(2, "kazu");
        stmt.execute();

        stmt.bindString(1, "HTML");
        stmt.bindString(2, "hiro");
        stmt.execute();

        stmt.bindString(1, "HTML");
        stmt.bindString(2, "aki");
        stmt.execute();


    }


    /**
     * アプリケーションの更新などによって、データベースのバージョンが上がった場合に実行される処理
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}