package jp.ac.hal.ths35033.mylibrary;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookEdit1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookEdit1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookEdit1Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Book book = null;

    private BookAddActivity bookAddActivity;

    private OnFragmentInteractionListener mListener;

    Button button ;
    EditText titleEdit ;
    EditText authorEdit ;
    EditText pubEdit ;
    EditText saleEdit ;
    EditText captEdit ;
    Button uploadBtn;
    RadioGroup radioGroup;
    boolean upFlg = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookEdit1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookEdit1Fragment newInstance(String param1, String param2) {
        BookEdit1Fragment fragment = new BookEdit1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BookEdit1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view   = inflater.inflate(R.layout.fragment_book_edit1, container, false);
        button      = (Button)view.findViewById(R.id.addButton);
        titleEdit   = (EditText) view.findViewById(R.id.titleEdit);
        authorEdit  = (EditText) view.findViewById(R.id.authorEdit);
        pubEdit     = (EditText) view.findViewById(R.id.publisherNameEdit);
        saleEdit    = (EditText) view.findViewById(R.id.salesDateEdit);
        captEdit    = (EditText) view.findViewById(R.id.itemCaptionEdit);
        uploadBtn   = (Button) view.findViewById(R.id.itemImage);
        radioGroup  = (RadioGroup) view.findViewById(R.id.groupRadio);
        radioGroup.check(0);

        if (getArguments().getSerializable("book") != null){
            book = (Book)getArguments().getSerializable("book");
            titleEdit.setText(book.getTitle());
            authorEdit.setText(book.getAuthor());
            pubEdit.setText(book.getPublisherName());
            saleEdit.setText(book.getSalesDate());
            captEdit.setText(book.getItemCaption());
            button.setText("更新");
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book b = book;
                //登録ボタン押したら
                if (book != null) {
                    //データベース更新
                    b.setTitle(titleEdit.getText().toString());
                    b.setAuthor(authorEdit.getText().toString());
                    b.setPublisherName(pubEdit.getText().toString());
                    b.setSalesDate(saleEdit.getText().toString());
                    b.setItemCaption(captEdit.getText().toString());

                    try {
                        if (upFlg){
                            b.setSmallImageURL(bookAddActivity.saveBitmap(bookAddActivity.img));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    int value = 0;
                    switch (checkedId) {
                        case R.id.radioButton0:
                            value = 0;
                            break;
                        case R.id.radioButton1:
                            value = 1;
                            break;
                        case R.id.radioButton2:
                            value = 2;
                            break;
                        case R.id.radioButton3:
                            value = 3;
                            break;
                        case R.id.radioButton4:
                            value = 4;
                            break;
                        case R.id.radioButton5:
                            value = 5;
                            break;
                        case R.id.radioButton6:
                            value = 6;
                            break;
                        case R.id.radioButton7:
                            value = 7;
                            break;
                        case R.id.radioButton8:
                            value = 8;
                            break;
                        case R.id.radioButton9:
                            value = 9;
                            break;
                        case R.id.radioButton10:
                            value = 10;
                            break;
                    }

                    b.setSize(value);

                    if (!b.getTitle().isEmpty() && !b.getAuthor().isEmpty()){
                        setEditClear();
                        bookAddActivity.updateDispTran(b);
                    }else{
                        //エラー処理
                    }

                }else{
                    //データベース新規登録
                    b = new Book();
                    b.setTitle(titleEdit.getText().toString());
                    b.setAuthor(authorEdit.getText().toString());
                    b.setPublisherName(pubEdit.getText().toString());
                    b.setSalesDate(saleEdit.getText().toString());
                    b.setItemCaption(captEdit.getText().toString());

                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    int value = 0;
                    switch (checkedId) {
                        case R.id.radioButton0:
                            value = 0;
                            break;
                        case R.id.radioButton1:
                            value = 1;
                            break;
                        case R.id.radioButton2:
                            value = 2;
                            break;
                        case R.id.radioButton3:
                            value = 3;
                            break;
                        case R.id.radioButton4:
                            value = 4;
                            break;
                        case R.id.radioButton5:
                            value = 5;
                            break;
                        case R.id.radioButton6:
                            value = 6;
                            break;
                        case R.id.radioButton7:
                            value = 7;
                            break;
                        case R.id.radioButton8:
                            value = 8;
                            break;
                        case R.id.radioButton9:
                            value = 9;
                            break;
                        case R.id.radioButton10:
                            value = 10;
                            break;
                    }

                    b.setSize(value);

                    try {
                        if (upFlg){
                            b.setSmallImageURL(bookAddActivity.saveBitmap(bookAddActivity.img));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (!b.getTitle().isEmpty() && !b.getAuthor().isEmpty()){
                        setEditClear();
                        bookAddActivity.insertDispTran(b);
                    }else{
                        //エラー処理
                    }
                }
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAddActivity.uploadImage();
                upFlg = true;
                uploadBtn.setBackgroundColor(getResources().getColor(R.color.color2));
                uploadBtn.setText("アップロード済み");
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            bookAddActivity = (BookAddActivity)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void setEditClear(){
        book = null;
        titleEdit.setText("");
        authorEdit.setText("");
        pubEdit.setText("");
        saleEdit.setText("");
        captEdit.setText("");
        button.setText("登録");
    }

}
