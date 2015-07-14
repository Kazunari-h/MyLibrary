package jp.ac.hal.ths35033.mylibrary;

/**
 * Created by hirosawak on 2015/07/14.
 */
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;


public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final String mTag;
    private final Class<T> mClass;

    /**
     * コンストラクタ
     * @param activity
     * @param tag
     * @param clz
     */
    public TabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
        //FragmentManagerからFragmentを探す。  2012/12/11 追記
        mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
    }

    /**
     * @brief タブが選択されたときの処理
     */
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        //ftはnullなので使用できない
        if (mFragment == null) {
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            FragmentManager fm = mActivity.getFragmentManager();
            fm.beginTransaction().add(R.id.container, mFragment, mTag).commit();
        } else {
            //detachされていないときだけattachするよう変更   2012/12/11　変更
            //FragmentManager fm = mActivity.getFragmentManager();
            //fm.beginTransaction().attach(mFragment).commit();
            if (mFragment.isDetached()) {
                FragmentManager fm = mActivity.getFragmentManager();
                fm.beginTransaction().attach(mFragment).commit();
            }
        }
    }
    /**
     * @brief 　タブの選択が解除されたときの処理
     */
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            FragmentManager fm = mActivity.getFragmentManager();
            fm.beginTransaction().detach(mFragment).commit();
        }
    }
    /**
     * @brief タブが2度目以降に選択されたときの処理
     */
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
}