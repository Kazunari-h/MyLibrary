package jp.ac.hal.ths35033.mylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hirosawak on 2015/07/12.
 */
public class GridBookAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<Book> bookList;

    GridBookAdapter(Context context){
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        convertView = layoutInflater.inflate(R.layout.grid_item,parent,false);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.itemImage);
        ((TextView)convertView.findViewById(R.id.title)).setText(bookList.get(position).getTitle());
        ((TextView)convertView.findViewById(R.id.lendingText)).setText(bookList.get(position).getRate()+"%");
        return convertView;
    }
}
