package jp.ac.hal.ths35033.mylibrary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class BookAddActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);

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

    public void AccessDatabase(){

        GridView gv = new GridView(this);
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
                List<String> list = new ArrayList<>();
                //csr.getCOunt()でcsrの要素数を取得し、そのよう素数分繰り返す。
                for(int i = 0; i < csr.getCount(); i++){
                    //カラムの名前が順番に配列strsに格納される。
                    String[] strs = csr.getColumnNames();
                    StringBuilder strbParts = new StringBuilder();

                    for(int s = 0; s < strs.length; s++){
                        if(strs[s].equals("title")){
                            list.add(csr.getString(s));
                            System.out.println("a");
                        }
                    }
                    //strbTotalに\nを連結させている。
                    strbTotal.append(strbParts.append("\n"));
                    //csrの次の行を撮りに行く。
                    csr.moveToNext();
                }
                //データベースの取得が終わったら、csrを閉じる。
                csr.close();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
                gv.setAdapter(adapter);
                //データベースをクローズさせる。
                db.close();

            }catch(Exception er){
                Log.e("err", "SQLException:" + er.toString());
            }

        }else{
            Log.d("err","データが入っていません。");
        }


//        Cursor c = db.query("book_table", new String[] {
//                    "_id",
//                    "title",
//                    "titleKana",
//                    "author",
//                    "authorKana",
//                    "publisherName",
//                    "size",
//                    "itemCaption",
//                    "salesDate",
//                    "itemPrice",
//                    "itemURL",
//                    "smallImageURL",
//                    "haveFlg",
//                    "lending",
//                    "rate",
//                    "update"
//                },
//                null, null, null, null, null);
//        boolean isEof = c.moveToFirst();
//        while (isEof) {
//            GridView gv = new GridView(this);
//
//            isEof = c.moveToNext();
//        }
//        c.close();
//        db.close();

    }
}
