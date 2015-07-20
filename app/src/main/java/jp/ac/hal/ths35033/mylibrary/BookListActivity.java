package jp.ac.hal.ths35033.mylibrary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class BookListActivity extends ActionBarActivity {

    GridView gv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        gv = (GridView)findViewById(R.id.grid01);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridView gridView = (GridView) parent;
                // クリックされたアイテムを取得します
                Book item = (Book) gridView.getItemAtPosition(position);
                intentDetail(item);
            }
        });

        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                GridView gridView = (GridView) parent;
                // クリックされたアイテムを取得します
                Book item = (Book) gridView.getItemAtPosition(position);
                longMess(item);
                return true;
            }
        });

    }

    public void longMess(Book book){
        final Book item = book;
        new AlertDialog.Builder(this)
                .setTitle(getText(R.string.action_del))
                .setMessage(item.getTitle() + "を削除します。")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MySQLiteOpenHelper dbh = null;
                        SQLiteDatabase db = null;
                        try {
                            dbh = new MySQLiteOpenHelper(BookListActivity.this);
                            db = dbh.getWritableDatabase();
                            //delete
                            int i = db.delete("book_table", "_id=?", new String[]{String.valueOf(item.get_id())});
                            if (i != 0) {
                                Toast.makeText(BookListActivity.this, "削除しました。", Toast.LENGTH_SHORT).show();
                            }
                        } finally {
                            //終了
                            if (db != null) {
                                db.close();
                            }
                            if (dbh != null) {
                                dbh.close();
                            }
                        }
                        Intent intent = new Intent(BookListActivity.this,BookListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setPositiveButton("Cancel", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ActionBarの設定
        // ActionBarの取得
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

        if (getIntent().getIntegerArrayListExtra("category") != null) {
            ArrayList<Integer> categoryNum;
            categoryNum = getIntent().getIntegerArrayListExtra("category");
            String textSql = "";
            for (int i = 0 ; i < categoryNum.size();i++){
                if (i == 0) {
                    textSql = textSql + "size='"+ (categoryNum.get(i)+1) +"'";
                }else{
                    textSql = textSql + " OR size='"+ (categoryNum.get(i)+1) +"'";
                }
            }
            AccessDatabaseCategory(textSql);
        }else if (getIntent().getStringExtra("ISBN") != null){
            String ISBN;
            ISBN = getIntent().getStringExtra("ISBN");
            AccessDatabaseISBN(ISBN);
        }else if (getIntent().getStringExtra("word") != null){
            String keyWord;
            keyWord = getIntent().getStringExtra("word");
            AccessDatabaseKeyword(keyWord);
        }else {
            AccessDatabase();
        }
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
        } else if(id==android.R.id.home){
            finish();
            return true;
        }else if (id == R.id.action_good1){
            Intent intent = new Intent(this,BookAddActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_goods2_item2){
            final String[] items = {"単行本", "文庫", "新書","全集/双書", "事典/辞典", "図鑑", "絵本","カセット/CD","コミック","その他"};
            final ArrayList<Integer> checkedItems = new ArrayList<Integer>();
            new AlertDialog.Builder(this)
                    .setTitle(getText(R.string.action_search_category))
                    .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked) checkedItems.add(which);
                            else checkedItems.remove((Integer) which);
                        }
                    })
                    .setNegativeButton("検索", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(BookListActivity.this,BookListActivity.class);
                            intent.putIntegerArrayListExtra("category", checkedItems);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setPositiveButton("Cancel", null)
                    .show();
            return true;
        }else if (id == R.id.action_goods2_item1){
            //テキスト入力を受け付けるビューを作成します。
            final EditText editView = new EditText(this);
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle(getText(R.string.action_search_word))
                    .setMessage("キーワードを入力してください。")
                    .setView(editView)
                    .setNegativeButton("検索", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(BookListActivity.this,BookListActivity.class);
                            intent.putExtra("word", editView.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setPositiveButton("Cancel", null)
                    .show();
            return true;
        }else if (id == R.id.action_goods2_item3){
            Intent intent = new Intent(BookListActivity.this,CameraPreviewActivity.class);
            intent.putExtra("search", "search");
            startActivity(intent);
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
                GridBookAdapter adapter = new GridBookAdapter(this);
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

    public void AccessDatabaseCategory(String textSql){

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        //dbにデータが格納されていれば、この処理が実行される。
        if(db != null){
            try{
                //sql文
                String sql = "SELECT * FROM book_table WHERE "+textSql+" ORDER BY _id ASC";
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
                GridBookAdapter adapter = new GridBookAdapter(this);
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

    public void AccessDatabaseKeyword(String keyword){

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        //dbにデータが格納されていれば、この処理が実行される。
        if(db != null){
            try{
                //sql文
                String sql = "SELECT * FROM book_table WHERE title LIKE '%' || ? || '%' OR itemCaption LIKE '%' || ? || '%' OR author LIKE '%' || ? || '%' ESCAPE '$'";
                //データベースから取得したデータを、一件ずつCursorに格納する。(配列のようなもの)
                Cursor csr = db.rawQuery(sql,new String[]{keyword,keyword,keyword});
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
                GridBookAdapter adapter = new GridBookAdapter(this);
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

    public void AccessDatabaseISBN(String ISBN){

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        //dbにデータが格納されていれば、この処理が実行される。
        if(db != null){
            try{
                //sql文
                String sql = "SELECT * FROM book_table WHERE isbn=? ";
                //データベースから取得したデータを、一件ずつCursorに格納する。(配列のようなもの)
                Cursor csr = db.rawQuery(sql,new String[]{ISBN});
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
                GridBookAdapter adapter = new GridBookAdapter(this);
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

    //内部ストレージから、画像ファイルを読み込む(Android 用)
    public static final Bitmap loadBitmapLocalStorage(String fileName, Context context) throws IOException, FileNotFoundException {
        BufferedInputStream bis = null;
        try {

            bis = (BufferedInputStream) context.getClass().getClassLoader().getResourceAsStream(fileName);
//            bis = new BufferedInputStream(context.openFileInput(fileName));
            return BitmapFactory.decodeStream(bis);
        } finally {
            try {
                bis.close();
            } catch (Exception e) {
                //IOException, NullPointerException
            }
        }
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

