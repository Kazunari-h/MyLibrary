package jp.ac.hal.ths35033.mylibrary;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        TextView rate       = (TextView)findViewById(R.id.rate);
        TextView update     = (TextView)findViewById(R.id.updateddate);
        TextView lending    = (TextView)findViewById(R.id.lending);
        TextView haveFlg    = (TextView)findViewById(R.id.haveFlg);
        TextView size       = (TextView)findViewById(R.id.size);
        ImageView imageView = (ImageView)findViewById(R.id.imageD);

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

        Button jump = (Button)findViewById(R.id.jumppage);
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
