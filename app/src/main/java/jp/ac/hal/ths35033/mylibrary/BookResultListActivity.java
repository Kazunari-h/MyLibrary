package jp.ac.hal.ths35033.mylibrary;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookResultListActivity extends ActionBarActivity {

    ListView listView;

    Map<String , Integer> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_result_list);

        map = new HashMap<>();

        map.put("",0);
        map.put("単行本",1);
        map.put("文庫",2);
        map.put("新書",3);
        map.put("全集・双書",4);
        map.put("事・辞典",5);
        map.put("図鑑",6);
        map.put("絵本",7);
        map.put("カセット,CD",8);
        map.put("コミック",9);
        map.put("ムックその他",10);

        String rakutenApiUri = "https://app.rakuten.co.jp/services/api/BooksBook/Search/20130522?" +
                "format=json&"+
                "size=0&" +
                "sort=sales&" +
                "hits=30&" +
                "applicationId=" +
                "?";


        if (getIntent().getStringExtra("keyword") != null){
            rakutenApiUri = rakutenApiUri + "&title="+getIntent().getStringExtra("keyword");
        }

        if (getIntent().getStringExtra("ISBN") != null){
            rakutenApiUri = rakutenApiUri + "&isbn="+getIntent().getStringExtra("ISBN");
        }

        listView = (ListView)findViewById(R.id.listView01);

        AsyncJsonLoader asyncJsonLoader = new AsyncJsonLoader(new AsyncJsonLoader.AsyncCallback() {
            // 実行前
            public void preExecute() {
            }
            // 実行後
            public void postExecute(JSONObject result) {
                if (result == null) {
                    showLoadError(); // エラーメッセージを表示
                    return;
                }
                try {
                    // 各 ATND イベントのタイトルを配列へ格納
                    ArrayList<Book> bookList = new ArrayList<>();
                    JSONArray eventArray = result.getJSONArray("Items");
                    for (int i = 0; i < eventArray.length(); i++) {
                        JSONObject eventObj = eventArray.getJSONObject(i);
                        JSONObject event = eventObj.getJSONObject("Item");

                        Book book = new Book();
                        book.setTitle(event.getString("title"));
                        System.out.println(book.getTitle());
                        book.setTitleKana(event.getString("titleKana"));
                        book.setAuthor(event.getString("author"));
                        book.setAuthorKana(event.getString("authorKana"));
                        book.setPublisherName(event.getString("publisherName"));
                        Integer integer = map.get(event.getString("size"));
                        if (integer != null) {
                            book.setSize(integer);
                        }else{
                            book.setSize(0);
                        }
                        book.setIsbn(event.getString("isbn"));
                        book.setItemCaption(event.getString("itemCaption"));
                        book.setSalesDate(event.getString("salesDate"));
                        book.setItemPrice(event.getInt("itemPrice"));
                        book.setItemURL(event.getString("itemUrl"));
                        book.setSmallImageURL(event.getString("smallImageUrl"));

                        bookList.add(book);
                    }

                    ListBookAdapter bookAdapter = new ListBookAdapter(BookResultListActivity.this);
                    bookAdapter.setBooKList(bookList);
                    // ListView にアダプタをセット
                    listView.setAdapter(bookAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    showLoadError(); // エラーメッセージを表示
                }
            }
            // 実行中
            public void progressUpdate(int progress) {
            }
            // キャンセル
            public void cancel() {
            }
        });
        // 処理を実行
        asyncJsonLoader.execute(rakutenApiUri);

    }

    // エラーメッセージ表示
    private void showLoadError() {
        Toast toast = Toast.makeText(this, "データを取得できませんでした。", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_result_list, menu);
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
}
