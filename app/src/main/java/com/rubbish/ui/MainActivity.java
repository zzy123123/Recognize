package com.rubbish.ui;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rubbish.R;
import com.rubbish.fragment.AskFragment;
import com.rubbish.fragment.MyFragment;
import com.rubbish.fragment.HomeFragment;
import com.rubbish.model.TabEntity;
import com.rubbish.ui.base.ManageFragmentActivity;
import com.rubbish.utils.GlobalFunction;
import com.rubbish.utils.ImportDB;
import com.rubbish.utils.ScreenManager;
import com.rubbish.widgets.BottomBarLayout;
import com.rubbish.widgets.PopupMenu;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends ManageFragmentActivity {
    @ViewInject(R.id.pager_view)
    ViewPager mViewPager;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.bottom_nav)
    BottomBarLayout bottomBarLayout;
    private List<TabEntity> tabEntityList;
    private String[] tabText = {"识别", "检索", "设置"};
    private int[] normalIcon = {R.mipmap.recognize_unselected, R.mipmap.query_unselected, R.mipmap.my_unselected};
    private int[] selectIcon = {R.mipmap.recognize_selected,  R.mipmap.query_selected,R.mipmap.my_selected};

    private int normalTextColor = Color.parseColor("#8A8A8A");
    private int selectTextColor = Color.parseColor("#128EE8");
    private long exitTime;
    private HomeFragment homeFragment;
    private AskFragment askFragment;
    private MyFragment myFragment;
    private List<Fragment> fragments;// viewpager中适配的 item
    private PopupMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        new ImportDB(this).copyDatabase();
    }


    @Override
    protected void initView() {
        tabEntityList = new ArrayList<>();
        for (int i = 0; i < tabText.length; i++) {
            TabEntity item = new TabEntity();
            item.setText(tabText[i]);
            item.setNormalIconId(normalIcon[i]);
            item.setSelectIconId(selectIcon[i]);
            tabEntityList.add(item);
        }
        bottomBarLayout.setNormalTextColor(normalTextColor);
        bottomBarLayout.setSelectTextColor(selectTextColor);
        bottomBarLayout.setTabList(tabEntityList);

        bottomBarLayout.setOnItemClickListener(new BottomBarLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mViewPager.setCurrentItem(position);
            }
        });
        fragments = new ArrayList<>();
        homeFragment = HomeFragment.newInstance();
        fragments.add(homeFragment);
        askFragment = AskFragment.newInstance();
        fragments.add(askFragment);
        myFragment = MyFragment.newInstance();
        fragments.add(myFragment);
        ItemPagerAdapter adapter = new ItemPagerAdapter(getSupportFragmentManager());
        adapter.setFragments(fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    if (positionOffset < 0.5) {
                        // 文字颜色变化

                    } else {
                        // 滑动到一半后，上一页的边框由lvse变为灰色，，下一页边框保持绿色不变
                        View view = bottomBarLayout.getLinearLayout(position + 1);
                        bottomBarLayout.showTab(position + 1, view);
                        setPos(position+1);
                    }
                } else {
                    if (position - 1 >= 0) {
                        View view = bottomBarLayout.getLinearLayout(position);
                        bottomBarLayout.showTab(position, view);
                        setPos(position);
                    } else {
                        View view = bottomBarLayout.getLinearLayout(0);
                        bottomBarLayout.showTab(0, view);
                        setPos(0);
                    }
                }

            }

            @Override
            public void onPageSelected(int position) {
                View view = bottomBarLayout.getLinearLayout(position);
                bottomBarLayout.showTab(position, view);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setPos(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (mViewPager.getCurrentItem()) {
            case 0:
                homeFragment.onActivityResult(requestCode,resultCode,data);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPos(int pos) {
        switch (pos) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;

        }
        title.setText(tabText[pos]);
    }

    /**
     * viewpager适配器
     */
    class ItemPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments = new ArrayList<Fragment>();
        private FragmentManager fm;

        public ItemPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void setFragments(List<Fragment> fragments) {
            if (this.fragments != null) {
                FragmentTransaction ft = fm.beginTransaction();
                for (Fragment f : this.fragments) {
                    ft.remove(f);
                }
                ft.commitAllowingStateLoss();
                ft = null;
                fm.executePendingTransactions();
            }
            this.fragments = fragments;
            notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            Object obj = super.instantiateItem(container, position);
            return obj;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                GlobalFunction.showToast(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                ScreenManager.getScreenManager().popActivity();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }

        ;
    };

}
