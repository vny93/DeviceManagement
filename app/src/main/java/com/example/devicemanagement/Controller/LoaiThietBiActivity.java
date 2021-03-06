package com.example.devicemanagement.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devicemanagement.Adapter.AdapterLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.R;

import java.util.ArrayList;
import java.util.Locale;

public class LoaiThietBiActivity extends AppCompatActivity {
    ListView lvLTB;
    ImageButton imbThemLTB, imbBack;
    SearchView svLTB;
    Button btnLuu, btnThoat;
    EditText txtTenLTB, txtMaLTB;

    AdapterLoaiThietBi adapterLoaiThietBi;
    ArrayList<LoaiThietBi> DSLTB;
    DBLoaiThietBi dbLoaiThietBi;

    ArrayList<LoaiThietBi> filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_thiet_bi);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dbLoaiThietBi = new DBLoaiThietBi(this);

        loadListView(dbLoaiThietBi);

//        dbLoaiThietBi.themLTB(new LoaiThietBi("CS", "Chiếu sáng"));
//        dbLoaiThietBi.themLTB(new LoaiThietBi("DC", "Dụng cụ dạy học"));
//        dbLoaiThietBi.themLTB(new LoaiThietBi("DH", "Điều hòa"));
//        dbLoaiThietBi.themLTB(new LoaiThietBi("DT", "Điện tử"));

        loadListView(dbLoaiThietBi);

        imbThemLTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themLTB(Gravity.CENTER, dbLoaiThietBi);
            }
        });
        imbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        svLTB.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getFilter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getFilter(s);
                return false;
            }
        });
    }

    private void getFilter(String s){
        filter = new ArrayList<>();
        for (LoaiThietBi e: DSLTB) {
            if(e.getTenLoai().toLowerCase().contains(s.toLowerCase())){
                filter.add(e);
            }
        }
        adapterLoaiThietBi.setFilterList(filter);
        if(filter.isEmpty()){
            //Toast.makeText(this, "Không có dữ liệu để hiển thị", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Không có dữ liệu để hiển thị!")
                    .setCancelable(true)
                    .show();
        }
    }

    public void loadListView(DBLoaiThietBi dbLoaiThietBi){
        DSLTB = dbLoaiThietBi.layDanhSachLTB();
        adapterLoaiThietBi = new AdapterLoaiThietBi(this, R.layout.layout_loai_thiet_bi, DSLTB);
        lvLTB.setAdapter(adapterLoaiThietBi);
    }

    private void themLTB(int gravity, DBLoaiThietBi bdDbLoaiThietBi){
        final Dialog dialog = new Dialog(LoaiThietBiActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_themltb);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //click ra bên ngoài để tắt dialog
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(false);
        }

        btnLuu = dialog.findViewById(R.id.btnLuu);
        btnThoat = dialog.findViewById(R.id.btnThoat);
        txtMaLTB = dialog.findViewById(R.id.txtMaLTB);
        txtTenLTB = dialog.findViewById(R.id.txtTenLTB);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maLTB = txtMaLTB.getText().toString();
                String tenLTB = txtTenLTB.getText().toString();
                if(maLTB.equals("")){
                    Toast.makeText(LoaiThietBiActivity.this, "Mã loại thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                if(tenLTB.equals("")){
                    Toast.makeText(LoaiThietBiActivity.this, "Tên loại thiết bị không được để trống!", Toast.LENGTH_SHORT);
                    return;
                }
                thongBaoThanhCong(Gravity.CENTER,"Thêm thiết bị "+tenLTB+" thành công!");
                dbLoaiThietBi.themLTB(new LoaiThietBi(maLTB, tenLTB));
                loadListView(dbLoaiThietBi);
                dialog.dismiss();
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

    private void setControl() {
        lvLTB = findViewById(R.id.lvLTB);
        imbThemLTB = findViewById(R.id.imbThemLTB);
        svLTB = findViewById(R.id.svLTB);
        imbBack = findViewById(R.id.imbBack);
    }
    private void thongBaoThanhCong(int gravity, String text) {
        //xử lý vị trí của dialog
        final Dialog dialog = new Dialog(this);
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

        //click ra bên ngoài để tắt dialog
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