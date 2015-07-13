package jp.ac.hal.ths35033.mylibrary;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class BookAddActivity extends FragmentActivity implements FragmentTabHost.OnTabChangeListener,BookEdit1Fragment.OnFragmentInteractionListener {

    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);
        book = (Book) getIntent().getSerializableExtra("book");


        // FragmentTabHost を取得する
        FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.container);

        TabHost.TabSpec tabSpec1, tabSpec2, tabSpec3;

        // TabSpec を生成する
        tabSpec1 = tabHost.newTabSpec("直接入力");
        tabSpec1.setIndicator("直接入力");
        // TabHost に追加
        tabHost.addTab(tabSpec1, BookEdit1Fragment.class, null);

        // TabSpec を生成する
        tabSpec2 = tabHost.newTabSpec("検索");
        tabSpec2.setIndicator("検索");
        // TabHost に追加
        tabHost.addTab(tabSpec2, BookEdit2Fragment.class, null);

        // TabSpec を生成する
        tabSpec3 = tabHost.newTabSpec("ISBNリーダー");
        tabSpec3.setIndicator("ISBNリーダー");
        // TabHost に追加
        tabHost.addTab(tabSpec3, BookEdit2Fragment.class, null);

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
