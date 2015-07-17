package jp.ac.hal.ths35033.mylibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hirosawak on 2015/07/12.
 */
public class GridBookAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<Book> bookList;

    private Map<Integer,String> map ;

    GridBookAdapter(Context context){
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        map = new HashMap<Integer, String>();
        map.put(0,"なし");
        map.put(1,"単行本");
        map.put(2,"文庫");
        map.put(3,"新書");
        map.put(4,"全集/双書");
        map.put(5,"事典/辞典");
        map.put(6,"図鑑");
        map.put(7,"絵本");
        map.put(8,"カセット/CD");
        map.put(9,"コミック");
        map.put(10,"その他");
    }

    public void setBooKList(ArrayList<Book> bookList){
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bookList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1:単行本 2:文庫 3:新書 4:全集・双書 5:事・辞典 6:図鑑 7:絵本 8:カセット/CD 9:コミック 10:その他

        Book book = bookList.get(position);

        convertView = layoutInflater.inflate(R.layout.grid_item,parent,false);
        ((TextView)convertView.findViewById(R.id.category)).setText(map.get(bookList.get(position).getSize()));
        ((TextView)convertView.findViewById(R.id.title)).setText(bookList.get(position).getTitle());
        ((TextView)convertView.findViewById(R.id.lendingText)).setText(bookList.get(position).getRate() + "%");
        if (!book.getSmallImageURL().isEmpty()){
            try {
                ((ImageView)convertView.findViewById(R.id.itemImage)).setImageBitmap(loadBitmap(book));
                System.out.println(book.getSmallImageURL());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return convertView;
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
