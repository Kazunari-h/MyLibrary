package jp.ac.hal.ths35033.mylibrary;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CookAddActivity extends Activity {

    private static final int REQUEST_GALLERY = 0;
    private ImageView imgView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (ImageView) findViewById(R.id.imgview_id);
        // ギャラリー呼び出し
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    // 新規フォルダを作成し、画像ファイルを保存する
    private void createFolderSaveImage(Bitmap imageToSave, String fileName) {
        // 新しいフォルダへのパス
        String folderPath = Environment.getExternalStorageDirectory()
                + "/NewFolder/";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // NewFolderに保存する画像のパス
        File file = new File(folder, fileName);
        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // これをしないと、新規フォルダは端末をシャットダウンするまで更新されない
            showFolder(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ContentProviderに新しいイメージファイルが作られたことを通知する
    private void showFolder(File path) throws Exception {
        try {
            ContentValues values = new ContentValues();
            ContentResolver contentResolver = getApplicationContext()
                    .getContentResolver();
            values.put(Images.Media.MIME_TYPE, "image/jpeg");
            values.put(Images.Media.DATE_MODIFIED,
                    System.currentTimeMillis() / 1000);
            values.put(Images.Media.SIZE, path.length());
            values.put(Images.Media.TITLE, path.getName());
            values.put(Images.Media.DATA, path.getPath());
            contentResolver.insert(Media.EXTERNAL_CONTENT_URI, values);
        } catch (Exception e) {
            throw e;
        }
    }

    // ギャラリーから戻って来たときに実行される
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                InputStream in = getContentResolver().openInputStream(
                        data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                // 新しいフォルダにギャラリーから選ん画像を保存
                createFolderSaveImage(img, "Image.jpg");
                in.close();
                // サイズ変更せずにエリアの中央に表示
                imgView.setScaleType(ImageView.ScaleType.CENTER);
                // ギャラリーで選んだ画像を表示
                imgView.setImageBitmap(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}