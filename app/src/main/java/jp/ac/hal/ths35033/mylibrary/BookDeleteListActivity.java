package jp.ac.hal.ths35033.mylibrary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.sql.SQLException;
import java.util.ArrayList;


public class BookDeleteListActivity extends ActionBarActivity {

    GridView gv ;
    GridBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_delete_list);

        gv = (GridView)findViewById(R.id.grid01);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridView gridView = (GridView) parent;
                // クリックされたアイテムを取得します
                Book item = (Book) gridView.getItemAtPosition(position);
                if (item.isSelected()){
                    item.setSelected(false);
                }else {
                    item.setSelected(true);
                }
                adapter.notifyDataSetChanged();
            }
        });

        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                GridView gridView = (GridView) parent;
                // クリックされたアイテムを取得します
                Book item = (Book) gridView.getItemAtPosition(position);
                if (item.isSelected()){
                    item.setSelected(false);
                }else {
                    item.setSelected(true);
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });
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
                //csr.getCOunt()でcsrの要素数を取得し、その要素数分繰り返す。
                for(int i = 0; i < csr.getCount(); i++){
                    //カラムの名前が順番に配列strsに格納される。
                    String[] strs = csr.getColumnNames();
                    Book b = setBook(csr,strs);
                    list.add(b);
                    //csrの次の行を撮りに行く。
                    csr.moveToNext();
                }
                //データベースの取得が終わったら、csrを閉じる。
                csr.close();
                adapter = new GridBookAdapter(this);
                adapter.setBooKList(list);
                gv.setAdapter(adapter);
                //データベースをクローズさせる。
                db.close();

            }catch(Exception er){
                Log.e("err", "SQLException:" + er.toString());
                er.printStackTrace();
            }
        }else{
            Log.d("err", "データが入っていません。");
        }
    }

    public Book setBook(Cursor csr ,String[] strs) throws SQLException,Exception {
        Book b = new Book();
        for (int s = 0; s < strs.length; s++) {
            switch (strs[s]) {
                case "_id":
                    b.set_id(csr.getInt(s));
                    break;
                case "title":
                    b.setTitle(csr.getString(s));
                    break;
                case "titleKana":
                    b.setTitleKana(csr.getString(s));
                    break;
                case "author":
                    b.setAuthor(csr.getString(s));
                    break;
                case "authorKana":
                    b.setAuthorKana(csr.getString(s));
                    break;
                case "publisherName":
                    b.setPublisherName(csr.getString(s));
                    break;
                case "size":
                    b.setSize(csr.getInt(s));
                    break;
                case "isbn":
                    b.setIsbn(csr.getString(s));
                    break;
                case "itemCaption":
                    b.setItemCaption(csr.getString(s));
                    break;
                case "salesDate":
                    b.setSalesDate(csr.getString(s));
                    break;
                case "itemPrice":
                    b.setItemPrice(csr.getInt(s));
                    break;
                case "itemURL":
                    b.setItemURL(csr.getString(s));
                    break;
                case "smallImageURL":
                    b.setSmallImageURL(csr.getString(s));
                    break;
                case "haveFlg":
                    b.setHaveFlg(csr.getInt(s));
                    break;
                case "lending":
                    b.setLending(csr.getString(s));
                    break;
                case "rate":
                    b.setRate(csr.getInt(s));
                    break;
                case "updateddate":
                    b.setUpdate(csr.getString(s));
                    break;
            }
        }
        return b;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar actionBar = this.getSupportActionBar();
        // 戻るボタンを表示するかどうか('<' <- こんなやつ)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        // タイトルを表示するか
        actionBar.setDisplayShowTitleEnabled(true);
        // iconを表示するか
        actionBar.setDisplayShowHomeEnabled(true);
        Drawable drawable = getApplicationContext().getResources().getDrawable(R.color.color1);
        actionBar.setBackgroundDrawable(drawable);
        actionBar.show();

        AccessDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_delete_list, menu);
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
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
