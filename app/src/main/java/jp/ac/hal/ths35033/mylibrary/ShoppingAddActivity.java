package jp.ac.hal.ths35033.mylibrary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ShoppingAddActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_add);

        Button btn1 = (Button)findViewById(R.id.shopBtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed1 = (EditText)findViewById(R.id.shopEdit1);
                EditText ed2 = (EditText)findViewById(R.id.shopEdit2);
                if (ed1.toString() != null || !ed1.toString().equals("")){
                    insert(ed1.toString(),ed2.toString());
                    Intent intent = new Intent(ShoppingAddActivity.this,ShoppingListActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ShoppingAddActivity.this,"未入力項目があります",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void insert(String title, String shop) {
        MySQLiteOpenHelper helper = null;
        SQLiteDatabase db = null;
        try {
            helper = new MySQLiteOpenHelper(this);
            db = helper.getWritableDatabase();
            SQLiteStatement stmt = db.compileStatement("insert into shop_table(title,shop) values (?,?);");
            stmt.bindString(1,title);
            stmt.bindString(2,shop);
            stmt.execute();
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            if (db != null) {
                db.close();
            }
            if (helper != null){
                helper.close();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_add, menu);
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
