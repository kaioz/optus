package com.cocosw.optus.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cocosw.accessory.views.complex.BetterViewAnimator;
import com.cocosw.framework.core.Base;
import com.cocosw.framework.view.adapter.TypeListAdapter;
import com.cocosw.optus.R;
import com.cocosw.optus.model.Route;
import com.cocosw.optus.service.OptusService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Project: Optus
 * Created by LiaoKai(soarcn) on 2016/6/25.
 */
public class ScenarioTwo extends Base<Void> implements AdapterView.OnItemSelectedListener {

    private static final String FRAGMENT_NAME = "_fragment";
    @Bind(R.id.name)
    Spinner mName;

    @Bind(R.id.trending_animator)
    BetterViewAnimator mTrendingAnimator;
    @Bind(R.id.transport)
    TextView mTransport;
    private NameAdapter nameAdapter;
    private MainFragment fragment;


    @Override
    public int layoutId() {
        return R.layout.scenario_two;
    }

    @OnClick(R.id.navigate)
    void onNavigate() {
        if (fragment.getCurrentRoute() != null)
            Map.launch(this, fragment.getCurrentRoute().location);
    }

    @Override
    protected void init(Bundle saveBundle) throws Exception {
        mName.setAdapter(nameAdapter = new NameAdapter(this));
        fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_NAME);
        if (fragment == null) {
            fragment = (MainFragment) Fragment.instantiate(this, MainFragment.class.getName());
            getSupportFragmentManager().beginTransaction().add(fragment, FRAGMENT_NAME).commit();
        }
        mName.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh) {
            fragment.refresh();
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        fragment.currentRoute = position;
        fragment.requestRender();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public static class MainFragment extends Fragment {

        private final CompositeSubscription subscriptions = new CompositeSubscription();
        private int displayId = R.id.trending_swipe_refresh;
        private List<Route> repositories = new ArrayList<>();
        private int currentRoute = 0;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
            refresh();
        }


        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            requestRender();
        }

        @Override
        public void onDestroy() {
            subscriptions.unsubscribe();
            super.onDestroy();
        }

        private ScenarioTwo target() {
            return (ScenarioTwo) getActivity();
        }

        private void requestRender() {
            if (getActivity() != null && !getActivity().isFinishing()) {
                target().mTrendingAnimator.setDisplayedChildId(displayId);
                if (displayId == R.id.trending_swipe_refresh) {
                    target().nameAdapter.updateList(repositories);
                    target().nameAdapter.notifyDataSetChanged();
                    target().mName.setSelection(currentRoute);
                    updateTransport(getCurrentRoute());
                }
            }
        }

        private void updateTransport(@Nullable Route currentRoute) {
            if (currentRoute != null) {
                HashMap<String, String> map = currentRoute.fromcentral;
                if (map.isEmpty())
                    target().mTransport.setText(R.string.error_message);
                else {
                    StringBuilder builder = new StringBuilder();
                    for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
                        builder.append(capitalize(entry.getKey())).append(" - ").append(entry.getValue()).append('\n');
                    }
                    target().mTransport.setText(builder);
                }
            }
        }

        private String capitalize(String str) {
            if (!TextUtils.isEmpty(str))
                return str.substring(0, 1).toUpperCase() + str.substring(1);
            else return str;
        }

        private void showRefreshing() {
            displayId = R.id.progress;
            requestRender();
        }

        private void refresh() {
            showRefreshing();
            subscriptions.add(new OptusService().getRoute()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io()) //
                    .subscribe(new Subscriber<Route[]>() {
                        @Override
                        public void onCompleted() {
                            requestRender();
                        }

                        @Override
                        public void onError(Throwable e) {
                            displayId = R.id.trending_error;
                            requestRender();
                            Timber.e(e, "Error");
                        }

                        @Override
                        public void onNext(Route[] listResult) {
                            repositories = Arrays.asList(listResult);
                            displayId = repositories.isEmpty()?R.id.trending_empty:R.id.trending_swipe_refresh;
                        }
                    }));
        }

        @Nullable
        Route getCurrentRoute() {
            if (repositories.isEmpty() || currentRoute > repositories.size() - 1)
                return null;
            else
                return repositories.get(currentRoute);
        }
    }


    private static class NameAdapter extends TypeListAdapter<Route> {

        public NameAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1);
        }

        @Override
        protected int[] getChildViewIds() {
            return new int[]{android.R.id.text1};
        }

        @Override
        protected void update(int position, Route item) {
            setText(0, item.name);
        }
    }
}
