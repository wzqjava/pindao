package wzq.com.pindao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TabLayout tb;
    ViewPager vp;
    Button btn;
    ArrayList<Fragment> fragmentList =  new ArrayList<Fragment>();
    ArrayList<ChannelBean> channelBeanList =  new ArrayList<ChannelBean>();
    MPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVeiw();
        initData();


    }

    private void initVeiw() {
        tb = (TabLayout) findViewById(R.id.tb);
        vp = (ViewPager) findViewById(R.id.vp);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ChannelActivity提供的打卡自己的方法;
                ChannelActivity.startChannelActivity(MainActivity.this,channelBeanList);
            }
        });

    }

    private void initData() {
        channelBeanList.add(new ChannelBean("热点",true));
        channelBeanList.add(new ChannelBean("北京",true));
        channelBeanList.add(new ChannelBean("房价",true));
        channelBeanList.add(new ChannelBean("旅游",false));
        channelBeanList.add(new ChannelBean("条目1",false));
        channelBeanList.add(new ChannelBean("条目2",false));

        for (int i = 0; i < channelBeanList.size(); i++) {
            if (channelBeanList.get(i).isSelect()) {
                String tabName = channelBeanList.get(i).getName();
                tb.addTab(tb.newTab().setText(tabName));
                if (i == 0) {
                    fragmentList.add(new VP_Fragment1());
                }else if(i == 1){
                    fragmentList.add(new VP_Fragment2());
                }else if(i == 2){
                    fragmentList.add(new VP_Fragment3());
                }else {
                    fragmentList.add(new BlankFragment());
                }
            }
        }

        mPagerAdapter =  new MPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mPagerAdapter);
        tb.setupWithViewPager(vp);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String json = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
        Log.e("wzq",json);
        Type type = new TypeToken<ArrayList<ChannelBean>>() {}.getType();
        Gson gson = new Gson();
        channelBeanList = gson.fromJson(json, type);
        tb.removeAllTabs();
        fragmentList.clear();

        for (int i = 0; i < channelBeanList.size(); i++) {
            ChannelBean channelBean = channelBeanList.get(i);
            if (channelBean.isSelect()) {
                String tabName = channelBeanList.get(i).getName();
                tb.addTab(tb.newTab().setText(tabName));

                if ("热点".equals(channelBean.getName())) {
                    fragmentList.add(new VP_Fragment1());
                }else if("北京".equals(channelBean.getName())){
                    fragmentList.add(new VP_Fragment2());
                }else if("房价".equals(channelBean.getName())){
                    fragmentList.add(new VP_Fragment3());
                }else {
                    fragmentList.add(new BlankFragment());
                }
            }

        }
        //因为集合中的Fragment数量改变了;
        mPagerAdapter.notifyDataSetChanged();


    }

    class MPagerAdapter extends FragmentPagerAdapter {


        public MPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            return channelBeanList.get(position).getName();
        }
    }


}
