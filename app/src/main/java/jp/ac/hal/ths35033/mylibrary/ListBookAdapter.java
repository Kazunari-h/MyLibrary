package jp.ac.hal.ths35033.mylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hirosawak on 2015/07/17.
 */
public class ListBookAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<Book> bookList;

    private Map<Integer,String> map ;

    ListBookAdapter(Context context){
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
        convertView = layoutInflater.inflate(R.layout.list_search_item,parent,false);

        ((TextView)convertView.findViewById(R.id.BookSize)).setText(map.get(bookList.get(position).getSize()));
        ((TextView)convertView.findViewById(R.id.BookTitle)).setText(bookList.get(position).getTitle());
        ((TextView)convertView.findViewById(R.id.BookPublisher)).setText(bookList.get(position).getPublisherName());
        ((TextView)convertView.findViewById(R.id.BookAuthor)).setText(bookList.get(position).getAuthor());

        int price = bookList.get(position).getItemPrice();
        String setPrice = "";
        if (price >=1000){
            setPrice = "¥" + ((int)price/1000) + "," + price % 1000 ;
        }else{
            setPrice = "¥" + price;
        }

        ((TextView)convertView.findViewById(R.id.BookPrice)).setText(setPrice);
        return convertView;
    }
}