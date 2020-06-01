package com.rubbish.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hb.dialog.dialog.LoadingDialog;
import com.rubbish.R;
import com.rubbish.adapter.RecyclerViewadapter;
import com.rubbish.adapter.Rubbish2Adapter;
import com.rubbish.model.DataBean;
import com.rubbish.model.Rubbish2;
import com.rubbish.model.WasteSorting;
import com.rubbish.model.WasteSortingDB;
import com.rubbish.utils.GlobalFunction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_item)
public final class AskFragment extends Fragment {
    private String TAG = this.getClass().getSimpleName();
    @ViewInject(R.id.iv_search)
    ImageView ivSearch;
    @ViewInject(R.id.lv_info)
    ListView lv_info;
    @ViewInject(R.id.et_key)
    EditText etKey;
    @ViewInject(R.id.recycler_view)
    RecyclerView recycler_view;

    private TextView tv1, tv2;
    RecyclerViewadapter recyclerViewadapter;
    private ArrayAdapter adapter;
    private List<WasteSorting> datas = new ArrayList<>();
    WasteSortingDB wasteSortingDB;
    ArrayList<DataBean> lists = new ArrayList<>();
    String[] dataStr;
    int item = 1;

    public static AskFragment newInstance() {
        AskFragment fragment = new AskFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);

        initView();
        initData();
    }

    private void initData() {
        lists = new ArrayList<>();
        lists.add(new DataBean("厨余(湿)垃圾", Color.GREEN, "    厨余垃圾是指居民日常生活及食品加工、饮食服务、单位供餐等活动中产生的垃圾  "));
        lists.add(new DataBean("其他垃圾", Color.CYAN, "    其他垃圾指危害比较少，可是没有再次利用的价值的垃圾，如建筑垃圾，生活垃圾等"));

        lists.add(new DataBean("可回收物", Color.BLUE, "    可回收物就是可以再生循环的垃圾。本身或材质可再利用的纸类、硬纸板、玻璃、塑料、金属、塑料包装"));
        lists.add(new DataBean("有害垃圾", Color.YELLOW, "    有害垃圾含有对人体健康有害的重金属、有毒的物质或者对环境造成现实危害或者潜在危害的废弃物"));


        LinearLayoutManager m = new LinearLayoutManager(getContext());
        m.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(m);
        recyclerViewadapter = new RecyclerViewadapter(lists, getContext());
        recycler_view.setAdapter(recyclerViewadapter);
        recyclerViewadapter.setOnItemClickListener(new RecyclerViewadapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                item = position + 1;
                datas.clear();
                datas = wasteSortingDB.query_type(item + "");
                dataStr = new String[datas.size()];
                for (int i = 0; i < datas.size(); i++) {
                    dataStr[i] = datas.get(i).rubbishName + ":" + datas.get(i).rubbishCategory;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, dataStr);
                        lv_info.setAdapter(adapter);
                    }
                });
            }
        });

        datas = wasteSortingDB.query_type(item + "");
        dataStr = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            dataStr[i] = datas.get(i).rubbishName + ":" + datas.get(i).rubbishCategory;
        }
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, dataStr);
        lv_info.setAdapter(adapter);

    }

    private void initView() {
        wasteSortingDB = new WasteSortingDB(getContext());
        ivSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
    }

    private void search() {
        String word = etKey.getText().toString();
        if (TextUtils.isEmpty(word)) {
            GlobalFunction.showToast(getActivity(), "请输入关键字");
            return;
        }
        datas.clear();
        datas = wasteSortingDB.query_name_type(word , item + "");
        dataStr = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++) {
            dataStr[i] = datas.get(i).rubbishName + ":" + datas.get(i).rubbishCategory;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, dataStr);
                lv_info.setAdapter(adapter);
            }
        });
    }


    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

}
