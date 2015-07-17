package jp.ac.hal.ths35033.mylibrary;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BookDetailActivity extends ActionBarActivity {

    Book book;
    Map<Integer,String> map ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        book = (Book) getIntent().getSerializableExtra("book");

        map = new HashMap<Integer, String>();
        map.put(0,"なし");
        map.put(1,"単行本");
        map.put(2,"文庫");
        map.put(3,"新書");
        map.put(4,"全集/双書");
        map.put(5,"事典/辞典");
        map.put(6,"図鑑");
        map.put(7,"絵本");
        map.put(8,"カセット/CD");
        map.put(9,"コミック");
        map.put(10,"その他");

        TextView title      = (TextView)findViewById(R.id.title);
        TextView titleKana  = (TextView)findViewById(R.id.titleKana);
        TextView author     = (TextView)findViewById(R.id.author);
        TextView authorKana = (TextView)findViewById(R.id.authorKana);
        TextView publisher  = (TextView)findViewById(R.id.publisherName);
        TextView itemCaption= (TextView)findViewById(R.id.itemCaption);
        final TextView rate       = (TextView)findViewById(R.id.rate);
        final TextView update     = (TextView)findViewById(R.id.updateddate);
        TextView lending    = (TextView)findViewById(R.id.lending);
        TextView haveFlg    = (TextView)findViewById(R.id.haveFlg);
        TextView size       = (TextView)findViewById(R.id.size);
        TextView salesDate  = (TextView)findViewById(R.id.salesDate);
        TextView itemPrice  = (TextView)findViewById(R.id.itemPrice);
        ImageView imageView = (ImageView)findViewById(R.id.imageD);

        LinearLayout FlgHaveFlg = (LinearLayout)findViewById(R.id.FlgHaveFlg);
        LinearLayout Flglending = (LinearLayout)findViewById(R.id.Flglending);
        LinearLayout Flgrate    = (LinearLayout)findViewById(R.id.Flgrate);

        FlgHaveFlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMsg();
            }
        });

        Flglending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMsg();
            }
        });

        Flgrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //テキスト入力を受け付けるビューを作成します。
                final EditText editView = new EditText(BookDetailActivity.this);
                new AlertDialog.Builder(BookDetailActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("進行度")
                        .setMessage("読み進めた割合を％で入力してください。")
                        .setView(editView)
                        .setNegativeButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    rateUpdate(Integer.parseInt(editView.getText().toString()));
                                    Toast.makeText(BookDetailActivity.this,"更新しました。",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BookDetailActivity.this, BookListActivity.class);
                                    startActivity(intent);
                                    finish();
                                }catch (NumberFormatException e){
                                    Toast.makeText(BookDetailActivity.this,"数値を入力してください。",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setPositiveButton("Cancel", null)
                        .show();
            }
        });

        title.setText(book.getTitle());
        titleKana.setText(book.getTitleKana());
        author.setText(book.getAuthor());
        authorKana.setText(book.getAuthorKana());
        publisher.setText(book.getPublisherName());
        itemCaption.setText(book.getItemCaption());

        lending.setText(book.getLending());
        rate.setText(book.getRate() + "%");
        update.setText(book.getUpdate());

        if (!book.getSmallImageURL().isEmpty()){
            try {
                imageView.setImageBitmap(loadBitmap(book));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if(book.getHaveFlg() == 0){
            haveFlg.setText("所持中");
        }else{
            haveFlg.setText("貸出中");
        }

        size.setText(map.get(book.getSize()));

        salesDate.setText(book.getSalesDate());
        String price = "";
        if (book.getItemPrice() >= 1000){
            price = "¥" + ((int)book.getItemPrice()/1000) + "," + book.getItemPrice() % 1000 ;
        }else {
            price = "¥" + book.getItemPrice();
        }
        itemPrice.setText(price);

        Button jump = (Button)findViewById(R.id.jumppage);
        if (book.getItemURL().isEmpty()){
            jump.setVisibility(View.INVISIBLE);
        }
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpWeb(book.getItemURL());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ActionBarの取得
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setTitle(book.title);
        actionBar.setSubtitle(book.author);
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

    public void dialogMsg(){
        //テキスト入力を受け付けるビューを作成します。
        final EditText editView = new EditText(BookDetailActivity.this);
        new AlertDialog.Builder(BookDetailActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("貸出フラグを更新します。")
                .setMessage("貸出した相手の名前を入力してください。")
                .setView(editView)
                .setNegativeButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        update(editView.getText().toString());
                        Intent intent = new Intent(BookDetailActivity.this,BookListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setPositiveButton("Cancel", null)
                .show();
    }

    public void update(String text) {
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
            //所持フラグ
            val.put("haveFlg", 1);
            //貸出
            val.put("lending", text);
            //更新日
            val.put("updateddate", sdf.format(date).toString());
            //update
            db.update("book_table", val, "_id=" + book.get_id(), null);

            Toast.makeText(this,"更新が完了しました。",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            //テキストメッセージを書き換える処理
            Toast.makeText(this,"エラーが発生しました。",Toast.LENGTH_SHORT).show();

        } finally{
            if (db != null) {
                db.close();
            }
            if (helper != null){
                helper.close();
            }
        }
    }

    public void rateUpdate(int num) {
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
            val.put("rate", num);
            //update
            db.update("book_table", val, "_id=" + book.get_id(), null);

            Toast.makeText(this,"更新が完了しました。",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            //テキストメッセージを書き換える処理
            Toast.makeText(this,"エラーが発生しました。",Toast.LENGTH_SHORT).show();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_detail, menu);
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
        } else if(id == android.R.id.home){
            finish();
            return true;
        } else if(id == R.id.action_line){
            String appId = "jp.naver.line.android";
            try {
                PackageManager pm = getPackageManager();
                ApplicationInfo appInfo = pm.getApplicationInfo(appId, PackageManager.GET_META_DATA);
                //インストールされてたら、Lineへ
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("line://msg/text/" + "『 "+ book.title +" 』って本が面白いよ！"));
                startActivity(intent);
            } catch(PackageManager.NameNotFoundException e) {
                //インストールされてなかったら、インストールを要求する
            }
            return true;
        } else if (id == R.id.action_edit) {
            Intent intent = new Intent(this,BookAddActivity.class);
            intent.putExtra("book", book);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_del) {
            new AlertDialog.Builder(this)
                    .setTitle(getText(R.string.action_del))
                    .setMessage(book.getTitle() + "を削除します。")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(BookDetailActivity.this, "OK", Toast.LENGTH_SHORT).show();
                            MySQLiteOpenHelper dbh = null;
                            SQLiteDatabase db = null;
                            try {
                                dbh = new MySQLiteOpenHelper(BookDetailActivity.this);
                                db = dbh.getWritableDatabase();
                                //delete
                                db.delete("book_table", "_id=?", new String[]{String.valueOf(book.get_id())});
                            } finally {
                                //終了
                                if (db != null) {
                                    db.close();
                                }
                                if (dbh != null) {
                                    dbh.close();
                                }
                            }
                            finish();
                        }
                    })
                    .setPositiveButton("Cancel", null)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void jumpWeb(String url){
        Uri uri = Uri.parse(url);
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    public Bitmap loadBitmap(Book book) throws IOException {
        final String SAVE_DIR = "/MyPhoto/";
        File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);

        String AttachName = file.getAbsolutePath() + "/" + book.getSmallImageURL();

        try {
            FileInputStream in = new FileInputStream(AttachName);
            return BitmapFactory.decodeStream(in);
        } catch(IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
