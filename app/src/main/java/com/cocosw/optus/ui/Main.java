package com.cocosw.optus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.View;

import com.cocosw.framework.core.ListFragment;
import com.cocosw.framework.core.SinglePaneActivity;
import com.cocosw.framework.view.adapter.CocoAdapter;
import com.cocosw.framework.view.adapter.TypeListAdapter;

import java.util.ArrayList;
import java.util.List;

public class Main extends SinglePaneActivity<Main.SelectorFragment> {

    @Override
    protected Fragment onCreatePane() {
        return Fragment.instantiate(this,SelectorFragment.class.getName());
    }

    public static class SelectorFragment extends ListFragment<Pair<String,Class>> {

        @Override
        protected CocoAdapter<Pair<String, Class>> createAdapter(List<Pair<String, Class>> list) throws Exception {
            return new TypeListAdapter<Pair<String, Class>>(getActivity(),android.R.layout.simple_list_item_1) {


                @Override
                protected int[] getChildViewIds() {
                    return new int[]{android.R.id.text1};
                }

                @Override
                protected void update(int position, Pair<String, Class> item) {
                    setText(0,item.first);
                }
            };
        }

        @Override
        protected void init(View view, Bundle bundle) throws Exception {
        }

        @Override
        protected void onItemClick(Pair<String, Class> pair, int i, long l, View view) {
            startActivity(new Intent(context,pair.second));
        }

        @Override
        public List<Pair<String, Class>> pendingData(Bundle bundle) throws Exception {
            List<Pair<String, Class>> out = new ArrayList<>();
            out.add(new Pair<String, Class>("Scenario 1",ScenarioOne.class));
            out.add(new Pair<String, Class>("Scenario 2",ScenarioTwo.class));
            return out;
        }

        @Override
        protected boolean reloadNeeded(Bundle savedInstanceState) {
            return true;
        }
    }
}


