package com.hb.dialog.dialog;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.hb.dialog.R;
import com.hb.dialog.model.Item;

import java.util.ArrayList;
import java.util.List;

import top.defaults.view.PickerView;

/**
 * 确认dialog
 */
public class NumberPickerDialog extends Dialog {

    private Context context;
    private ImageView noticeImg;
    private TextView tvMsg;
    private TextView btnOk;
    private TextView btnCancel;
    private PickerView picker;
    private String currentItem = "2019";
    private int type = 0;
    public interface OnBtnClickListener {
        public void ok();

        public void cancel();
    }

    public NumberPickerDialog(Context context,int type) {
        super(context, R.style.CustomerDialogTheme);
        this.context = context;
        this.type = type;
        init();
    }

    /**
     * 设置按钮监听
     *
     * @param listener
     */
    public void setClickListener(final OnBtnClickListener listener) {
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.ok();
                }
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.cancel();
                }
                dismiss();
            }
        });
    }

    /**
     * 设置提示的logo
     *
     * @param logo
     * @return
     */
    public NumberPickerDialog setLogoImg(int logo) {
        if (noticeImg == null) {
            return this;
        }
        noticeImg.setVisibility(View.VISIBLE);
        noticeImg.setImageDrawable(context.getResources().getDrawable(logo));
        return this;
    }

    /**
     * 获取logo
     *
     * @return
     */
    public ImageView getLogoImg() {
        return noticeImg;
    }

    /**
     * 确定按钮
     *
     * @param listener
     * @return
     */
    public NumberPickerDialog setPositiveBtn(View.OnClickListener listener) {
        if (btnOk != null) {
            btnOk.setVisibility(View.VISIBLE);
            btnOk.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 取消按钮
     *
     * @param listener
     * @return
     */
    public NumberPickerDialog setNegativeBtn(View.OnClickListener listener) {
        if (btnCancel != null) {
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setOnClickListener(listener);
        }
        return this;
    }

    public int getNumber(){
        return Integer.parseInt(currentItem);
    }

    /**
     * 初始化dialog
     */
    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_pick_number, null);
        setContentView(layout);
        btnOk = layout.findViewById(R.id.btn_ok);
        btnCancel = layout.findViewById(R.id.btn_cancel);
        noticeImg = (ImageView) layout.findViewById(R.id.img_logo);
        picker = (PickerView) layout.findViewById(R.id.picker);
        final List<Item> items = new ArrayList<>();
        if(type == 0){
            for(int i=2015;i<=2100;i++){
                items.add(new Item(i+""));
            }
        }else if(type == 1){
            for(int i=1;i<=12;i++){
                items.add(new Item(i+""));
            }
        }

        picker.setOnSelectedItemChangedListener(new PickerView.OnSelectedItemChangedListener() {
            @Override
            public void onSelectedItemChanged(PickerView pickerView, int previousPosition, int selectedItemPosition) {
                Item item = items.get(selectedItemPosition);
                currentItem = item.getText();
            }
        });
        picker.setItems(items, new PickerView.OnItemSelectedListener<Item>() {
            @Override
            public void onItemSelected(Item item) {
                currentItem = item.getText();
            }
        });
        Item item = items.get(picker.getSelectedItemPosition());
        currentItem = item.getText();
    }

}
