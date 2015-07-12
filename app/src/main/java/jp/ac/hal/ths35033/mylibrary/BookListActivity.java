package jp.ac.hal.ths35033.mylibrary;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class BookListActivity extends ActionBarActivity {

    GridView gv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        gv = (GridView)findViewById(R.id.grid01);

        // ActionBarの設定
        if (savedInstanceState == null) {
            // ActionBarの取得
            ActionBar actionBar = this.getSupportActionBar();
            // 戻るボタンを表示するかどうか('<' <- こんなやつ)
            actionBar.setDisplayHomeAsUpEnabled(true);
            // タイトルを表示するか
            actionBar.setDisplayShowTitleEnabled(true);
            // iconを表示するか
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.show();
        }

        AccessDatabase();

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridView gv = (GridView)findViewById(R.id.grid01);
                GridView gridView = (GridView) parent;
                // クリックされたアイテムを取得します
                Book item = (Book) gridView.getItemAtPosition(position);
                intentDetail(item);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_book_list, menu);
        return super.onCreateOptionsMenu(menu);
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

    public void intentDetail(Book book){
        Intent intent = new Intent(this,BookDetailActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }

    public void AccessDatabase(){

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        //dbにデータが格納されていれば、この処理が実行される。
        if(db != null){
            try{
                //sql文
                String sql = "SELECT * FROM book_table ORDER BY _id ASC";
                //データベースから取得したデータを、一件ずつCursorに格納する。(配列のようなもの)
                Cursor csr = db.rawQuery(sql,null);
                //データベースのカーソルを先頭に持って来る。
                csr.moveToFirst();
                //文字列を連結させるクラス
                StringBuilder strbTotal = new StringBuilder();
                ArrayList<Book> list = new ArrayList<>();
                for (int i  = 0 ; i < 20 ; i++ ){
                    Book b = new Book();
                    b.setTitle("BOOK"+i);
                    list.add(b);
                }
                //csr.getCOunt()でcsrの要素数を取得し、その要素数分繰り返す。
                for(int i = 0; i < csr.getCount(); i++){
                    //カラムの名前が順番に配列strsに格納される。
                    String[] strs = csr.getColumnNames();
                    StringBuilder strbParts = new StringBuilder();

                    for(int s = 0; s < strs.length; s++){
                        if(strs[s].equals("title")){
                            Book b = new Book();
                            b.setTitle(csr.getString(s));
                            list.add(b);
                        }
                    }
                    //strbTotalに\nを連結させている。
                    strbTotal.append(strbParts.append("\n"));
                    //csrの次の行を撮りに行く。
                    csr.moveToNext();
                }
                //データベースの取得が終わったら、csrを閉じる。
                csr.close();
                GridBookAdapter adapter = new GridBookAdapter(this);
                adapter.setBooKList(list);
                gv.setAdapter(adapter);

                //データベースをクローズさせる。
                db.close();

            }catch(Exception er){
                Log.e("err", "SQLException:" + er.toString());
            }

        }else{
            Log.d("err","データが入っていません。");
        }

    }
}
