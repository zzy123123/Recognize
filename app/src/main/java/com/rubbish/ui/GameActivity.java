package com.rubbish.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.dialog.dialog.ConfirmDialog;
import com.hb.dialog.dialog.NoticeDialog;
import com.rubbish.R;
import com.rubbish.model.WasteSorting;
import com.rubbish.model.WasteSortingDB;
import com.rubbish.ui.base.ManageActivity;
import com.rubbish.utils.GlobalFunction;
import com.rubbish.utils.GlobalVariables;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

@ContentView(R.layout.activity_game)
public class GameActivity extends ManageActivity {
    @ViewInject(R.id.tv_q)
    TextView tv_q;
    @ViewInject(R.id.tv_a1)
    TextView tv_a1;
    @ViewInject(R.id.tv_a2)
    TextView tv_a2;
    @ViewInject(R.id.tv_a3)
    TextView tv_a3;
    @ViewInject(R.id.tv_a4)
    TextView tv_a4;

    int select_ans = 0;
    int successTimes = 0;
    WasteSortingDB wasteSortingDB;
    ArrayList<WasteSorting> wasteSortings;
    WasteSorting wasteSorting;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        x.view().inject(this);
        wasteSortingDB = new WasteSortingDB(GameActivity.this);
        wasteSortings = wasteSortingDB.query(null);
        initData();
    }

    private void initData() {
        if (wasteSortings.size() == 0) {

            tv_q.setText("无题");
        } else {
            int index = random.nextInt(wasteSortings.size());
            wasteSorting = wasteSortings.get(index);
            tv_q.setText(wasteSorting.rubbishName + "是_________?");
            tv_a1.setBackgroundColor(Color.WHITE);
            tv_a2.setBackgroundColor(Color.WHITE);
            tv_a3.setBackgroundColor(Color.WHITE);
            tv_a4.setBackgroundColor(Color.WHITE);
            select_ans = 0;
        }

    }


    private void showPhotoDialog() {
        ConfirmDialog confirmDialog = new ConfirmDialog(GameActivity.this);
        confirmDialog = confirmDialog.setTitle("答题结束");
        confirmDialog = confirmDialog.setMsg("本次答题一共答对" + successTimes + "道题。" + wasteSorting.rubbishName+"是什么垃圾快去查查吧！");
        confirmDialog.setClickListener(new ConfirmDialog.OnBtnClickListener() {
            @Override
            public void ok() {
                GameActivity.this.finish();
            }

            @Override
            public void cancel() {
                GameActivity.this.finish();
            }
        });
        confirmDialog.show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
//        builder.setTitle("答题结束");
//        builder.setMessage("回答错误！！！");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                GameActivity.this.finish();
//            }
//        });
//
//        builder.show();

    }

    public void onOkClick(View view) {
        if (wasteSorting.rubbishChoose == select_ans) {
            successTimes++;
            Toast.makeText(GameActivity.this, "回答正确!!!", Toast.LENGTH_SHORT).show();
            initData();
        } else {
            showPhotoDialog();
        }
    }

    public void onA1Click(View view) {
        select_ans = 1;
        tv_a1.setBackgroundColor(Color.BLUE);
        tv_a2.setBackgroundColor(Color.WHITE);
        tv_a3.setBackgroundColor(Color.WHITE);
        tv_a4.setBackgroundColor(Color.WHITE);
    }

    public void onA2Click(View view) {

        select_ans = 2;
        tv_a2.setBackgroundColor(Color.BLUE);
        tv_a1.setBackgroundColor(Color.WHITE);
        tv_a3.setBackgroundColor(Color.WHITE);
        tv_a4.setBackgroundColor(Color.WHITE);
    }

    public void onA3Click(View view) {

        select_ans = 3;
        tv_a3.setBackgroundColor(Color.BLUE);
        tv_a2.setBackgroundColor(Color.WHITE);
        tv_a1.setBackgroundColor(Color.WHITE);
        tv_a4.setBackgroundColor(Color.WHITE);
    }

    public void onA4Click(View view) {

        select_ans = 4;
        tv_a4.setBackgroundColor(Color.BLUE);
        tv_a2.setBackgroundColor(Color.WHITE);
        tv_a3.setBackgroundColor(Color.WHITE);
        tv_a1.setBackgroundColor(Color.WHITE);
    }
}
