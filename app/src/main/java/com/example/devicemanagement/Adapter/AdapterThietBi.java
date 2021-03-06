package com.example.devicemanagement.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Controller.ChiTietThietBiActivity;
import com.example.devicemanagement.Controller.ThietBiActivity;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.ThietBi;
import com.example.devicemanagement.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterThietBi extends ArrayAdapter<ThietBi> {
    Context context;
    int resource;
    ArrayList<ThietBi> data;
    DBThietBi dbThietBi;
    TextView tvMatb,tvTentb,tvSoluong,tvConfirmXoaTB;
    ImageView imvSuaTb,imvXoaTB;
    Button btnHuy, btnXoa;


    public AdapterThietBi(@NonNull Context context, int resource, @NonNull ArrayList<ThietBi> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }
    public void setFilterList(ArrayList<ThietBi> filter){
        this.data = filter;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        tvMatb = convertView.findViewById(R.id.tvMaTB);
        tvTentb = convertView.findViewById(R.id.tvTenTB);
        tvSoluong = convertView.findViewById(R.id.tvSoluongTB);
        imvSuaTb = convertView.findViewById(R.id.imvSuaTB);
        imvXoaTB = convertView.findViewById(R.id.imvXoaTB);
        ThietBi thietBi = data.get(position);
        tvMatb.setText(thietBi.getMaThietBi());
        tvTentb.setText(thietBi.getTenThietBi());
        tvSoluong.setText(thietBi.getSoLuong());
        imvSuaTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChiTietThietBiActivity.class);
                intent.putExtra("thietbi",thietBi);
                context.startActivity(intent);
            }
        });

        imvXoaTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaThietBi(Gravity.CENTER, thietBi.getMaThietBi());
            }
        });
        return convertView;
    }

    private void xoaThietBi(int gravity, String maTB){
        dbThietBi = new DBThietBi(context);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_xoa_thietbi);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //click ra b??n ngo??i ????? t???t dialog
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(true);
        }
        btnXoa = dialog.findViewById(R.id.btnXoaTB);
        btnHuy = dialog.findViewById(R.id.btnHuyTB);
        tvConfirmXoaTB = dialog.findViewById(R.id.tvConfirmXoaTB);
        tvConfirmXoaTB.setText("B???n c?? th???t s??? mu???n x??a "+maTB+" kh??ng?");
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thongBaoThanhCong(Gravity.CENTER, "X??a th??nh c??ng thi???t b??? "+ maTB+"!");
                dbThietBi.xoaThietBi(maTB);
                dialog.dismiss();
                ((ThietBiActivity)context).loadListView(dbThietBi);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void thongBaoThanhCong(int gravity, String text) {
        //x??? l?? v??? tr?? c???a dialog
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_tbthanhcong);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //click ra b??n ngo??i ????? t???t dialog
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(true);
        }
        TextView tvThongBao = dialog.findViewById(R.id.tvThongBao);
        tvThongBao.setText(text);
        dialog.show();
    }
}
