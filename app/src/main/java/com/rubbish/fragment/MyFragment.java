package com.rubbish.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rubbish.R;
import com.rubbish.ui.AboutActivity;
import com.rubbish.ui.GameActivity;
import com.rubbish.utils.GlobalVariables;
import com.rubbish.utils.SettingUtils;
import com.rubbish.widgets.RoundImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.my)
public final class MyFragment extends Fragment {
    @ViewInject(R.id.rl_about)
    RelativeLayout rlAbout;
    @ViewInject(R.id.rl_game)
    RelativeLayout rlGame;
    @ViewInject(R.id.nickname)
    private TextView tvName;
    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
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
        initView();
    }

    private void initView() {
//        String ava = GlobalVariables.BASE_URL1+SettingUtils.getPreferencesString(getActivity(),SettingUtils.AVATAR,"");
        String name = SettingUtils.getPreferencesString(getActivity(),SettingUtils.NAME,"");
//        Glide.with(getActivity()).load(ava).into(avatar);
        tvName.setText(name);

        rlAbout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toAbout();
            }
        });
        rlGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toGames();
            }
        });
    }


    //todo 跳转到小游戏
    private void toGames() {
        Intent intent1 = new Intent(getActivity(), GameActivity.class);
        startActivity(intent1);
    }

    //todo 跳转到关于
    private void toAbout() {
        Intent intent1 = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent1);
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
        initView();
    }

}
