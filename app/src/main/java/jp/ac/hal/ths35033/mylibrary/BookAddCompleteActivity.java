package jp.ac.hal.ths35033.mylibrary;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BookAddCompleteActivity extends ActionBarActivity {

    Book book;

    TextView Msg;

    final String errMsg = "エラーが発生しました。";
    final String insertMsg = "登録が完了しました。";
    final String updateMsg = "更新が完了しました。";

    Button exit ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add_complete);

        exit = (Button)findViewById(R.id.completeBtn);
        Msg = (TextView)findViewById(R.id.completeMsg);
        book = (Book) getIntent().getSerializableExtra("book");

        if (book == null){
            //結果が受け取れなかった。
            Msg.setText(errMsg);
        }else{
            if (getIntent().getStringExtra("update") != null && !getIntent().getStringExtra("update").isEmpty()){
                //更新
                Toast.makeText(this,getIntent().getStringExtra("update"),Toast.LENGTH_SHORT).show();
                Msg.setText(updateMsg);
                update();
            }else{
                //新規登録
                Msg.setText(insertMsg);
                insert();
            }
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookAddCompleteActivity.this,BookListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void insert() {
        //==== 現在時刻を取得 ====//
        Date date = new Date();
        //==== 表示形式を設定 ====//
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
        MySQLiteOpenHelper helper = null;
        SQLiteDatabase db = null;

        try {
            helper = new MySQLiteOpenHelper(this);
            db = helper.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("insert into book_table(title,titleKana,author,authorKana,publisherName,size,isbn,itemCaption,salesDate,itemPrice,itemURL,smallImageURL,haveFlg,lending,rate,updateddate) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
            //書籍タイトル title
            stmt.bindString(1, book.getTitle());
            //書籍タイトルカナ titleKana
            stmt.bindString(2, book.getTitleKana());
            //著者名	author
            stmt.bindString(3, book.getAuthor());
            //著者名カナ	authorKana
            stmt.bindString(4, book.getAuthorKana());
            //出版社名	publisherName
            stmt.bindString(5, book.getPublisherName());
            //書籍のサイズ	size	1:単行本 2:文庫 3:新書 4:全集・双書 5:事・辞典 6:図鑑 7:絵本 8:カセット,CD 9:コミック 10:その他
            stmt.bindLong(6, book.getSize());
            //ISBNコード(書籍コード)	isbn
            stmt.bindString(7, book.getIsbn());
            //商品説明文	itemCaption
            stmt.bindString(8, book.getItemCaption());
            //発売日	salesDate
            stmt.bindString(9, book.getSalesDate());
            //税込み販売価格	itemPrice
            stmt.bindLong(10, book.getItemPrice());
            //商品URL	itemUrl
            stmt.bindString(11, book.getItemURL());
            //商品画像 	smallImageUrl
            stmt.bindString(12, book.getSmallImageURL());
            //所持フラグ
            stmt.bindLong(13, book.getHaveFlg());
            //貸出
            stmt.bindString(14, book.getLending());
            //進行度
            stmt.bindLong(15, book.getRate());
            //更新日
            stmt.bindString(16,sdf.format(date).toString());
            stmt.execute();

            //テキストメッセージを書き換える処理

        }catch (Exception e){
            e.printStackTrace();
            //テキストメッセージを書き換える処理
            Msg.setText(errMsg);
        } finally{
            if (db != null) {
                db.close();
            }
            if (helper != null){
                helper.close();
            }
        }

    }

    public void update() {
        //==== 現在時刻を取得 ====//
        Date date = new Date();
        //==== 表示形式を設定 ====//
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");

        MySQLiteOpenHelper helper = null;
        SQLiteDatabase db = null;

        try {
            helper = new MySQLiteOpenHelper(this);
            db = helper.getWritableDatabase();

            //更新データ作成
            ContentValues val = new ContentValues();
            //val.put("カラム名", );
            //書籍タイトル title
            val.put("title", book.getTitle());
            System.out.println(book.getTitle());
            //書籍タイトルカナ titleKana
            val.put("titleKana", book.getTitleKana());
            //著者名	author
            val.put("author", book.getAuthor());
            //著者名カナ	authorKana
            val.put("authorKana", book.getAuthorKana());
            //出版社名	publisherName
            val.put("publisherName", book.getPublisherName());
            //書籍のサイズ	size	1:単行本 2:文庫 3:新書 4:全集・双書 5:事・辞典 6:図鑑 7:絵本 8:カセット,CD 9:コミック 10:その他
            val.put("size", book.getSize());
            //ISBNコード(書籍コード)	isbn
            val.put("isbn", book.getIsbn());
            //商品説明文	itemCaption
            val.put("itemCaption", book.getItemCaption());
            //発売日	salesDate
            val.put("salesDate", book.getSalesDate());
            //税込み販売価格	itemPrice
            val.put("itemPrice", book.getItemPrice());
            //商品URL	itemUrl
            val.put("itemUrl", book.getItemURL());
            //商品画像 	smallImageUrl
            val.put("smallImageURL", book.getSmallImageURL());
            //所持フラグ
            val.put("haveFlg", book.getHaveFlg());
            //貸出
            val.put("lending", book.getLending());
            //進行度
            val.put("rate", book.getRate());
            //更新日
            val.put("updateddate", sdf.format(date).toString());

            //update
            db.update("book_table", val, "_id=" + book.get_id(), null);

            //テキストメッセージを書き換える処理


        }catch (Exception e){
            //テキストメッセージを書き換える処理
            Msg.setText(errMsg);
        } finally{
            if (db != null) {
                db.close();
            }
            if (helper != null){
                helper.close();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ActionBarの取得
        ActionBar actionBar = this.getSupportActionBar();
        // 戻るボタンを表示するかどうか('<' <- こんなやつ)
        actionBar.setDisplayHomeAsUpEnabled(true);
        // タイトルを表示するか
        actionBar.setDisplayShowTitleEnabled(true);
        // iconを表示するか
        actionBar.setDisplayShowHomeEnabled(true);
        Drawable drawable = getApplicationContext().getResources().getDrawable(R.color.color1);
        actionBar.setBackgroundDrawable(drawable);
        actionBar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_add_complete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveBitmap(Bitmap saveImage) throws IOException {
        final String SAVE_DIR = "/MyPhoto/";
        File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);
        try{
            if(!file.exists()){
                file.mkdir();
            }
        }catch(SecurityException e){
            e.printStackTrace();
            throw e;
        }

        Date mDate = new Date();
        SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = fileNameDate.format(mDate) + ".jpg";
        String AttachName = file.getAbsolutePath() + "/" + fileName;

        try {
            FileOutputStream out = new FileOutputStream(AttachName);
            saveImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch(IOException e) {
            e.printStackTrace();
            throw e;
        }

        // save index
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put("_data", AttachName);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

}
