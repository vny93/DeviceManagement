package com.example.devicemanagement.Adapter;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.devicemanagement.Controller.PhongHocActivity;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.Entity.PhongHoc;
import com.example.devicemanagement.R;

import java.util.ArrayList;

public class AdapterPhongHoc extends ArrayAdapter<PhongHoc> {
    Context context;
    int resource;
    ArrayList<PhongHoc> data;
    DBPhongHoc dbPhongHoc;

    Button btnLuu, btnThoat, btnKhongXoa, btnDongYXoa;
    EditText txtMaPhong, txtLoaiPhong, txtTang;
    TextView tvTieuDe, tvConfirmXoa;

    public AdapterPhongHoc(@NonNull Context context, int resource, @NonNull ArrayList<PhongHoc> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    public void setFilterList(ArrayList<PhongHoc> filter){
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
        TextView tvMaPhong = convertView.findViewById(R.id.tvMaPhong);
        TextView tvLoaiPhong = convertView.findViewById(R.id.tvLoaiPhong);
        TextView tvTang = convertView.findViewById(R.id.tvTang);
        ImageButton imbSua = convertView.findViewById(R.id.imbSua);
        ImageButton imbXoa = convertView.findViewById(R.id.imbXoa);
        PhongHoc phongHoc = data.get(position);
        tvMaPhong.setText(phongHoc.getMaPhong());
        tvLoaiPhong.setText(phongHoc.getLoaiPhong());
        tvTang.setText(phongHoc.getTang());
        imbSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaPhongHoc(Gravity.CENTER, phongHoc.getMaPhong(), phongHoc.getLoaiPhong(), phongHoc.getTang());
            }
        });

        imbXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaPhongHoc(Gravity.CENTER, phongHoc.getMaPhong());
            }
        });
        return convertView;
    }

    private void suaPhongHoc(int gravity, String maPhong, String loaiPhong, String tang) {
        dbPhongHoc = new DBPhongHoc(context);
        String ma = maPhong; // m?? ph??ng c???n s???a

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_them_sua_ph);

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
            dialog.setCancelable(false);
        }

        btnLuu = dialog.findViewById(R.id.btnLuu);
        btnThoat = dialog.findViewById(R.id.btnThoat);
        txtMaPhong = dialog.findViewById(R.id.txtMaPhong);
        txtLoaiPhong = dialog.findViewById(R.id.txtLoaiPhong);
        txtTang = dialog.findViewById(R.id.txtTang);
        tvTieuDe = dialog.findViewById(R.id.tvTieuDe);

        tvTieuDe.setText("CH???NH S???A PH??NG");

        txtMaPhong.setText(maPhong);
        txtLoaiPhong.setText(loaiPhong);
        txtTang.setText(tang);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maPhong = txtMaPhong.getText().toString();
                String loaiPhong = txtLoaiPhong.getText().toString();
                String tang = txtTang.getText().toString();
                if(maPhong.equals("")){
                    Toast.makeText(context, "M?? ph??ng kh??ng ???????c ????? tr???ng!", Toast.LENGTH_SHORT);
                    return;
                }
                if(loaiPhong.equals("")){
                    Toast.makeText(context, "Lo???i ph??ng kh??ng ???????c ????? tr???ng!", Toast.LENGTH_SHORT);
                    return;
                }if(tang.equals("")){
                    Toast.makeText(context, "T???ng kh??ng ???????c ????? tr???ng!", Toast.LENGTH_SHORT);
                    return;
                }
                thongBaoThanhCong(Gravity.CENTER,"C???p nh???t th??ng tin th??nh c??ng!");
                dbPhongHoc.suaPhongHoc(new PhongHoc(maPhong, loaiPhong, tang), ma);
                dialog.dismiss();
                ((PhongHocActivity)context).loadListView(dbPhongHoc);
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void xoaPhongHoc(int gravity, String maPhong) {
        dbPhongHoc = new DBPhongHoc(context);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_xoa_ph);

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
            dialog.setCancelable(false);
        }

        btnKhongXoa = dialog.findViewById(R.id.btnKhongXoa);
        btnDongYXoa = dialog.findViewById(R.id.btnDongYXoa);
        tvConfirmXoa = dialog.findViewById(R.id.tvConfirmXoa);

        tvConfirmXoa.setText("B???n c?? th???t s??? mu???n x??a " + maPhong + "kh??ng?");

        btnDongYXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thongBaoThanhCong(Gravity.CENTER,"X??a th??nh c??ng ph??ng "+maPhong+"!");
                dbPhongHoc.xoaPhongHoc(maPhong);
                dialog.dismiss();
                ((PhongHocActivity)context).loadListView(dbPhongHoc);
            }
        });

        btnKhongXoa.setOnClickListener(new View.OnClickListener() {
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
