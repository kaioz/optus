package com.cocosw.optus.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cocosw.accessory.views.CocoBundle;
import com.cocosw.framework.core.Base;
import com.cocosw.framework.core.BaseFragment;
import com.cocosw.optus.R;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Project: Optus
 * Created by LiaoKai(soarcn) on 2016/6/25.
 */
public class ScenarioOne extends Base<Void> implements TabLayout.OnTabSelectedListener {

    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    @Bind(R.id.text_field)
    TextView mTextField;
    @Bind(R.id.buttons_section)
    LinearLayout mButtonsSection;

    @Override
    public int layoutId() {
        return R.layout.scenario_one;
    }

    @Override
    protected void init(Bundle bundle) throws Exception {
        initTab();
        initViewpager();
    }

    private void initViewpager() {
        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Page.getInstance(ScenarioOne.this, position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
    }

    // For point 5
    @OnClick({R.id.blue_button, R.id.yellow_button, R.id.red_button})
    void onColorButtonClicked(AppCompatButton button) {
        if (button.getSupportBackgroundTintList() != null)
            mButtonsSection.setBackgroundColor(button.getSupportBackgroundTintList().getDefaultColor());
    }

    // For point 1
    private void initTab() {
        mTabs.setOnTabSelectedListener(this);
        Observable.from(getResources().getStringArray(R.array.tabitems)).map(new Func1<String, TabLayout.Tab>() {
            @Override
            public TabLayout.Tab call(String s) {
                return mTabs.newTab().setText(s);
            }
        }).forEach(new Action1<TabLayout.Tab>() {
            @Override
            public void call(TabLayout.Tab tab) {
                mTabs.addTab(tab);
            }
        });
    }

    // For point 4
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() < 4)
            mTextField.setText(tab.getText());
        else
            mTextField.setText("");
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }


    public static class Page extends BaseFragment<Void> implements View.OnClickListener {

        private static final String PAGENUM = "_pagenum";
        @Bind(R.id.pagenum)
        TextView mPagenum;
        private String number;

        public static Fragment getInstance(Context context, int position) {
            return Fragment.instantiate(context, Page.class.getName(), new CocoBundle().put(Page.PAGENUM, String.valueOf(position)).getBundle());
        }

        @Override
        public int layoutId() {
            return R.layout.fragment_page;
        }

        @Override
        protected void setupUI(View view, Bundle bundle) throws Exception {
            number = getArguments().getString(PAGENUM,"--");
            mPagenum.setText(number);
            mPagenum.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Snackbar.make(v,number,Snackbar.LENGTH_LONG).show();
        }
    }
}
