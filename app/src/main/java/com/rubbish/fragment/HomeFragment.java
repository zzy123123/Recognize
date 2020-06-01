package com.rubbish.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rubbish.R;
import com.rubbish.adapter.RubbishAdapter;
import com.rubbish.model.Rubbish;
import com.rubbish.utils.DividerItemDecoration;
import com.rubbish.utils.GlobalFunction;
import com.rubbish.utils.GlobalVariables;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;

@ContentView(R.layout.recognize)
public final class HomeFragment extends Fragment   {
    @ViewInject(R.id.tv_ok)
    private TextView tvOk;
    private List<Rubbish> datas = new ArrayList<>();
    private int page = 1;
    private int rows = 10;
    @ViewInject(R.id.avatar)
    private ImageView avatar;
    private String strImagePath;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        avatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoDialog();
            }
        });
        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                recognize();
            }
        });
    }

    //列表对话框
    public void openlistdialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String[] rubbishs = new String[datas.size()];
        int i = 0;
        for (Rubbish r :
                datas) {

            rubbishs[i] = r.getName() + ":" + GlobalFunction.getRubbishName(r.getLajitype());
            Log.e("11111111111" , "getLajitype:" + r.getLajitype());
            Log.e("11111111111" , "getKeyword:" + r.getName());
            Log.e("11111111111" , "getLajitip:" + r.getLajitip());
            Log.e("11111111111" , "getTrust:" + r.getTrust());
            i++;
        }

        builder.setTitle("搜索");
        builder.setItems(rubbishs, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
//                Toast.makeText(getContext(), "当前条目：" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //创建，显示
        AlertDialog d = builder.create();
        d.show();

    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    openlistdialog();
                    break;
                case 1:
                    GlobalFunction.showToast(getActivity(), msg.obj.toString());
                    break;
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 拍照或者从相册选取文件
     */
    private void showPhotoDialog() {
        final AlertDialog dlg = new AlertDialog.Builder(getActivity()).create();
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.fx_dialog_social_main);
        TextView tvTakePhoto = (TextView) window.findViewById(R.id.tv_content1);
        tvTakePhoto.setText(getText(R.string.take_picture));
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.dismiss();
                strImagePath = GlobalFunction.getFolder(GlobalVariables.TEMP, "") + System.currentTimeMillis()
                        + ".jpg";
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File outFile = new File(strImagePath);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, GlobalVariables.TAKE_PICTURE);
                } else {
                    GlobalFunction.showToast(getActivity(),
                            getText(R.string.sdcrad_not_mount).toString());
                }
            }
        });
        TextView tvAlbum = (TextView) window.findViewById(R.id.tv_content2);
        tvAlbum.setText(getText(R.string.from_album));
        tvAlbum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.dismiss();
                GalleryConfig config = new GalleryConfig.Build()
                        .limitPickPhoto(1)
                        .singlePhoto(true)
                        .build();
                GalleryActivity.openActivity(getActivity(),
                        GlobalVariables.PICK_PHOTO, config);
            }
        });
    }

    private void recognize(){
        if(TextUtils.isEmpty(strImagePath)){
            GlobalFunction.showToast(getActivity(),"请选择图片");
            return;
        }
        page=1;
        datas.clear();
//        adapter.notifyDataSetChanged();
        String img = GlobalFunction.imageToBase64(strImagePath);
        String url = "http://api.tianapi.com/txapi/imglajifenlei/index";
        RequestParams params = new RequestParams(url);
        params.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        params.addQueryStringParameter("key", GlobalVariables.KEY);
        params.addBodyParameter("img", img);
//        params.addQueryStringParameter("mode", "1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject js = new JSONObject(result);
                    int code = js.optInt("code");
                    if (code == 200) {
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<List<Rubbish>>() {
                        }.getType();
                        String t = js.optString("newslist");
                        List<Rubbish> infos = gson.fromJson(t, type);
                        if (infos != null && infos.size() > 0) {
                            datas.addAll(infos);
                        }

                        handler.sendEmptyMessage(0);
                    } else {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = js.opt("message");
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = "内部异常。";
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = "内部异常。";
                handler.sendMessage(msg);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 如果是直接从相册获取
            case GlobalVariables.PICK_PHOTO:
                if (resultCode != RESULT_OK) {
                    break;
                }
                if (data != null) {
                    final List<String> result = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
                    Luban.with(getActivity())
                            .load(result)                                   // 传人要压缩的图片列表
                            .ignoreBy(100)                                  // 忽略不压缩图片的大小
                            .setTargetDir(GlobalFunction.getFolder(GlobalVariables.TEMP, ""))                        // 设置压缩后文件存储位置
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    strImagePath = file.getAbsolutePath();
                                    Glide.with(getActivity()).load(strImagePath).into(avatar);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                }
                            }).launch();    //启动压缩
                } else {
                    GlobalFunction.showToast(getActivity(), "获取图片失败" +
                            "");
                }
                break;
            // 如果是调用相机拍照时
            case GlobalVariables.TAKE_PICTURE:
                // Uri uri = data.getData();
                // String strImage = uri.getEncodedPath();
                if (resultCode != RESULT_OK) {
                    // GlobalFunction.showToast(this, R.string.get_picture_failed);
                    break;
                }
                File file = new File(strImagePath);
                if (file.exists()) {
                    Luban.with(getActivity())
                            .load(file)                                   // 传人要压缩的图片列表
                            .ignoreBy(100)                                  // 忽略不压缩图片的大小
                            .setTargetDir(GlobalFunction.getFolder(GlobalVariables.TEMP, ""))                        // 设置压缩后文件存储位置
                            .setCompressListener(new OnCompressListener() { //设置回调
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    strImagePath = file.getAbsolutePath();
                                    Glide.with(getActivity()).load(strImagePath).into(avatar);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                }
                            }).launch();    //启动压缩
                } else {
                    GlobalFunction.showToast(getActivity(), "获取图片失败");
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
