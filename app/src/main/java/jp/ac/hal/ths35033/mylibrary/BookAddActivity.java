package jp.ac.hal.ths35033.mylibrary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BookAddActivity extends ActionBarActivity
        implements FragmentTabHost.OnTabChangeListener,
            View.OnTouchListener,
            BookEdit1Fragment.OnFragmentInteractionListener,
            BookEdit2Fragment.OnFragmentInteractionListener,
            BookEdit3Fragment.OnFragmentInteractionListener {

    FragmentTabHost tabHost;
    Book book;
    float lastTouchX;
    float currentX;
    int target = 0;
    public String fileName = "";
    public Bitmap img;

    private static final int REQUEST_GALLERY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        book = (Book) getIntent().getSerializableExtra("book");
        getAction();

        // FragmentTabHost を取得する
        tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setOnTouchListener(this);
        tabHost.setup(this, getSupportFragmentManager(), R.id.container);

        TabHost.TabSpec tabSpec1, tabSpec2, tabSpec3;

        // TabSpec を生成する
        tabSpec1 = tabHost.newTabSpec("直接入力");
        tabSpec1.setIndicator("直接入力");

        FragmentManager fm = getFragmentManager();
        FragmentTransaction t = fm.beginTransaction();

        BookEdit1Fragment bookEdit1Fragment = new BookEdit1Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
        // TabHost に追加
        if (book != null && book.title != null ){
            tabHost.addTab(tabSpec1, bookEdit1Fragment.getClass(), bundle);
        }else {
            tabHost.addTab(tabSpec1, bookEdit1Fragment.getClass(), null);
        }

        // TabSpec を生成する
        tabSpec2 = tabHost.newTabSpec("検索");
        tabSpec2.setIndicator("検索");
        // TabHost に追加
        tabHost.addTab(tabSpec2, BookEdit2Fragment.class, null);

        // TabSpec を生成する
        tabSpec3 = tabHost.newTabSpec("ISBNリーダー");
        tabSpec3.setIndicator("ISBNリーダー");

        BookEdit3Fragment bookEdit3Fragment = new BookEdit3Fragment();
        // TabHost に追加
        tabHost.addTab(tabSpec3, BookEdit3Fragment.class, null);

        if (getIntent().getIntExtra("key", 0) != 0){
            tabHost.setCurrentTab((Integer)getIntent().getIntExtra("key",0));
        }else{
            tabHost.setCurrentTab(0);
        }
        // リスナー登録
        tabHost.setOnTabChangedListener(this);

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAction();
    }

    public void getAction(){
        ActionBar actionBar = this.getSupportActionBar();

        if (book != null && book.title != null) {
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
        getMenuInflater().inflate(R.menu.menu_book_add, menu);

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

    @Override
    public void onTabChanged(String tabId) {
        System.out.println("******************************"+tabId);

        if (tabId.equals("検索")){
            target = 1;
        }else if (tabId.equals("直接入力")){
            target = 0;
        }else {
            target = 2;
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastTouchX = event.getX();
                break;

            case MotionEvent.ACTION_UP:
                currentX = event.getX();
                if (lastTouchX < currentX - 100) {
                    //前に戻る動作
                    target--;
                    if (target < 0){
                        target = 2;
                    }
                    System.out.println(target);
                    tabHost.setCurrentTab(target);
                }
                if (lastTouchX > currentX + 100) {
                    //次に移動する動作
                    target = ++target % 3;
                    System.out.println(target);
                    tabHost.setCurrentTab(target);
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                currentX = event.getX();
                if (lastTouchX < currentX - 100) {
                    //前に戻る動作
                    target--;
                    if (target < 0){
                        target = 2;
                    }
                    tabHost.setCurrentTab(target);
                }
                if (lastTouchX > currentX + 100) {
                    //次に移動する動作
                    target = ++target % 3;
                    tabHost.setCurrentTab(target);
                }
                break;
        }
        return true;
    }

    public void move(){
        Intent intent = new Intent(this, CameraPreviewActivity.class);
        startActivity(intent);
    }

    public void apiAccess(String keyword){
        Intent intent = new Intent(this, BookResultListActivity.class);
        intent.putExtra("keyword",keyword);
        startActivity(intent);
    }

    public void insertDispTran(Book b){
        if (book.getSmallImageURL() != null){
            b.setSmallImageURL(book.getSmallImageURL());
        }
        Intent intent = new Intent(this,BookAddCompleteActivity.class);
        intent.putExtra("book", b);
        startActivity(intent);
    }

    public void updateDispTran(Book b){
        if (book.getSmallImageURL() != null){
            b.setSmallImageURL(book.getSmallImageURL());
        }
        Intent intent = new Intent(this,BookAddCompleteActivity.class);
        intent.putExtra("book", b);
        intent.putExtra("update", "update");
        startActivity(intent);
        finish();
    }

    public void uploadImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                img = BitmapFactory.decodeStream(in);
                in.close();
            }catch (Exception e){

            }
        }
    }

    public String saveBitmap(Bitmap saveImage) throws IOException {
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
        fileName = fileNameDate.format(mDate) + ".jpg";
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

        return fileName;
    }
}

