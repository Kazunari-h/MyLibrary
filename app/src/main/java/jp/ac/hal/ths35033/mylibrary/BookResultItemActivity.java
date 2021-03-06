package jp.ac.hal.ths35033.mylibrary;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BookResultItemActivity extends ActionBarActivity {

    Book book;
    Map<Integer,String> map ;

    ImageView image;
    ImageGetTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_result_item);

        book = (Book) getIntent().getSerializableExtra("book");
        if (book != null) {
            //imageを取得
            image = (ImageView) findViewById(R.id.webImage);
            //画像取得スレッド起動
            task = new ImageGetTask(image);
            task.execute(book.getSmallImageURL());

        }

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
        TextView size       = (TextView)findViewById(R.id.size);
        TextView salesDate  = (TextView)findViewById(R.id.salesDate);
        TextView itemPrice  = (TextView)findViewById(R.id.itemPrice);

        title.setText(book.getTitle());
        titleKana.setText(book.getTitleKana());
        author.setText(book.getAuthor());
        authorKana.setText(book.getAuthorKana());
        publisher.setText(book.getPublisherName());
        itemCaption.setText(book.getItemCaption());

        size.setText(map.get(book.getSize()));
        salesDate.setText(book.getSalesDate());
        String price = "";
        if (book.getItemPrice() >= 1000){
            price = "¥" + ((int)book.getItemPrice()/1000) + "," + book.getItemPrice() % 1000 ;
        }else {
            price = "¥" + book.getItemPrice();
        }
        itemPrice.setText(price);

        Button AddBtn = (Button)findViewById(R.id.AddItem);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDB();
            }
        });
    }

    public void insertDB(){
        Bitmap bmp = ((BitmapDrawable)image.getDrawable()).getBitmap();
        try {
            saveBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this,BookAddCompleteActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ActionBarの取得
        ActionBar actionBar = this.getSupportActionBar();
        if (book != null) {
            actionBar.setTitle(book.title);
            actionBar.setSubtitle(book.author);
        } else {
            actionBar.setTitle("新規登録");
            book = new Book();
        }
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
        getMenuInflater().inflate(R.menu.menu_book_result_item, menu);
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
        book.setSmallImageURL(fileName);
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
