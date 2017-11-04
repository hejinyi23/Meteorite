package com.firelotus.meteorite.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firelotus.meteorite.R;
import com.firelotus.meteorite.ui.content.SubFragment;
import com.firelotus.meteoritelibrary.base.BaseActivity;
import com.orhanobut.logger.Logger;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.tv_content) TextView tv_content;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.tabmain_viewPager) ViewPager viewPager;
    @BindView(R.id.tabmain_indicator) FixedIndicatorView indicator;

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    @Override
    protected int getLayoutId() {
        //setSupportActionBar(false);
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        Log.d("test","==>>initView");
        setSupportActionBar(toolbar);
        //showContentView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                AboutActivity.start(getApplication());
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        float unSelectSize = 16;
        float selectSize = unSelectSize * 1.2f;

        int selectColor = Color.WHITE;
        int unSelectColor = Color.DKGRAY;
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));

        viewPager.setOffscreenPageLimit(4);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        inflate = LayoutInflater.from(getApplicationContext());

        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面
        // 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            tv_content.setText("camera");
        } else if (id == R.id.nav_about) {
            tv_content.setText("gallery");
            AboutActivity.start(getApplicationContext());
        } else if (id == R.id.nav_login) {
            tv_content.setText("slideshow");
        } else if (id == R.id.nav_set) {
            tv_content.setText("manage");
        } else if (id == R.id.nav_comments) {
            tv_content.setText("share");
        } else if (id == R.id.nav_logout) {
            tv_content.setText("send");
        }
        Logger.d(tv_content.getText().toString());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"每日推荐", "福利", "Android", "IOS"};
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_main, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            SubFragment subFragment = new SubFragment();
            Bundle bundle = new Bundle();
            bundle.putString(SubFragment.INTENT_STRING_TABNAME, tabNames[position]);
            bundle.putInt(SubFragment.INTENT_INT_POSITION, position);
            subFragment.setArguments(bundle);
            return subFragment;
            /*SecondLayerFragment subFragment = new SecondLayerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(SubFragment.INTENT_STRING_TABNAME, tabNames[position]);
            bundle.putInt(SubFragment.INTENT_INT_POSITION, position);
            subFragment.setArguments(bundle);
            return subFragment;*/
        }
    }

}