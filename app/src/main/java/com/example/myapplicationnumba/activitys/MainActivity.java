package com.example.myapplicationnumba.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.activitys.fragment.FindFragment;
import com.example.myapplicationnumba.activitys.fragment.ManagementFragment;
import com.example.myapplicationnumba.activitys.fragment.MeFragment;
import com.example.myapplicationnumba.base.MyApplication;
/**
 * 主界面：
 * 包含了三个按钮，点击按钮可以切换到不同的fragment
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    protected LinearLayout mMenuMain;
    protected LinearLayout mMenuFind;
    protected LinearLayout mMenuMe;
    protected ManagementFragment collectFragment=new ManagementFragment();//管理
    protected FindFragment mFindFragmenr=new FindFragment();//发现
    protected MeFragment mMeFragment=new MeFragment();//我的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        //销毁闪屏页
        MyApplication.destroyActivity("SplashActivity");
        initView();
        //获取管理类
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_content,collectFragment)
                .hide(collectFragment)
                .add(R.id.container_content,mFindFragmenr)
                .hide(mFindFragmenr)
                .add(R.id.container_content,mMeFragment)
                //事物添加  默认：显示首页  其他页面：隐藏
                //提交
                .commit();
    }
    /**
     * 初始化视图
     */
    public void initView(){
        mMenuMain= (LinearLayout) this.findViewById(R.id.menu_main);
        mMenuFind= (LinearLayout) this.findViewById(R.id.menu_find);
        mMenuMe= (LinearLayout) this.findViewById(R.id.menu_me);
        mMenuMain.setOnClickListener(this);
        mMenuFind.setOnClickListener(this);
        mMenuMe.setOnClickListener(this);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.menu_main://首页
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(collectFragment)
                        .hide(mFindFragmenr)
                        .hide(mMeFragment)
                        .commit();
                break;
            case  R.id.menu_find://发现
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(collectFragment)
                        .show(mFindFragmenr)
                        .hide(mMeFragment)
                        .commit();
                break;
            case  R.id.menu_me://我的
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(collectFragment)
                        .hide(mFindFragmenr)
                        .show(mMeFragment)
                        .commit();
                break;
        }
    }

    /**
     * 双击退出程序
     */
    //--------------使用onKeyDown()干掉他--------------

    //记录用户首次点击返回键的时间
    private long firstTime=0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(MainActivity.this,"再按一次退出程序--->onKeyDown",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
