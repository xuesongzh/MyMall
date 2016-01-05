package com.zxsong.mymall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.zxsong.mymall.fragment.HomeFragment;
import com.zxsong.mymall.widget.FragmentTabHost;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);

        mTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

        TabHost.TabSpec tabSpec = mTabHost.newTabSpec("home");

        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        img.setBackgroundResource(R.mipmap.icon_home);
        text.setText("主页");

        tabSpec.setIndicator(view);

        mTabHost.addTab(tabSpec, HomeFragment.class,null);
    }


}
