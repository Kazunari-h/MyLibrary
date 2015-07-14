package jp.ac.hal.ths35033.mylibrary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;



public class BookAddActivity extends ActionBarActivity
        implements FragmentTabHost.OnTabChangeListener,
            BookEdit1Fragment.OnFragmentInteractionListener,
            BookEdit2Fragment.OnFragmentInteractionListener,
            BookEdit3Fragment.OnFragmentInteractionListener {

    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        book = (Book) getIntent().getSerializableExtra("book");

        Toast.makeText(this,book.getTitle(),Toast.LENGTH_LONG).show();

        // ActionBarの設定
        if (savedInstanceState == null) {
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
            actionBar.show();
        }


        // FragmentTabHost を取得する
        FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
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
        tabHost.addTab(tabSpec1, bookEdit1Fragment.getClass(), bundle);

        // TabSpec を生成する
        tabSpec2 = tabHost.newTabSpec("検索");
        tabSpec2.setIndicator("検索");
        // TabHost に追加
        tabHost.addTab(tabSpec2, BookEdit2Fragment.class, null);

        // TabSpec を生成する
        tabSpec3 = tabHost.newTabSpec("ISBNリーダー");
        tabSpec3.setIndicator("ISBNリーダー");
        // TabHost に追加
        tabHost.addTab(tabSpec3, BookEdit3Fragment.class, null);

        // リスナー登録
        tabHost.setOnTabChangedListener(this);
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
        System.out.println("******************************");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
