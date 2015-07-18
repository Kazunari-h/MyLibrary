package jp.ac.hal.ths35033.mylibrary;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class InfoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAction();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getAction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getAction(){
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

}
