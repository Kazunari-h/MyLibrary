package jp.ac.hal.ths35033.mylibrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BookDetailActivity extends ActionBarActivity {

    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        book = (Book) getIntent().getSerializableExtra("book");

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
            Drawable drawable = getApplicationContext().getResources().getDrawable(R.color.color1);
            actionBar.setBackgroundDrawable(drawable);
            actionBar.show();
        }

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

        title.setText(book.getTitle());
        titleKana.setText(book.getTitleKana());
        author.setText(book.getAuthor());
        authorKana.setText(book.getAuthorKana());
        publisher.setText(book.getPublisherName());
        itemCaption.setText(book.getItemCaption());

        lending.setText(book.getLending());
        rate.setText(book.getRate() + "%");
        update.setText(book.getUpdate());

        if(book.getHaveFlg() == 0){
            haveFlg.setText("所持中");
        }else{
            haveFlg.setText("貸出中");
        }

        Button jump = (Button)findViewById(R.id.jumppage);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpWeb(book.getItemURL());
            }
        });
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
                    .setMessage(book.title + "を削除します。")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(BookDetailActivity.this,"OK",Toast.LENGTH_SHORT).show();
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
}
