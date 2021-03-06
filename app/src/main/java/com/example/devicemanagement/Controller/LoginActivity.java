package com.example.devicemanagement.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.devicemanagement.DBHelper.DBChiTietSD;
import com.example.devicemanagement.DBHelper.DBLoaiThietBi;
import com.example.devicemanagement.DBHelper.DBNhanVien;
import com.example.devicemanagement.DBHelper.DBPhongHoc;
import com.example.devicemanagement.DBHelper.DBThietBi;
import com.example.devicemanagement.Entity.LoaiThietBi;
import com.example.devicemanagement.Entity.NhanVien;
import com.example.devicemanagement.R;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class LoginActivity extends AppCompatActivity {
    private DBNhanVien dbNhanVien;

    EditText txtUser, txtPass;
    Button btnLogin;
    TextView tvForgotPass, tvTieuDeQL;

    ArrayList<NhanVien> DSNV = new ArrayList<>();
    NhanVien nhanVienDangNhap = new NhanVien();

    final String mail = "thuhango0204@gmail.com";
    final String password = "tvchhnsxhegolohs";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        startAnimation();
        setEvent();
    }

    private void startAnimation() {
        Animation animation = new AnimationUtils().loadAnimation(this, R.anim.anim);
        tvTieuDeQL.setAnimation(animation);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEvent() {
        dbNhanVien = new DBNhanVien(this);
//        dbNhanVien.themNhanVien(new NhanVien("NV01", "Ng?? Thu H??", "02042000", "thuhango0204@gmail.com", "123"));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String tenDangNhap = txtUser.getText().toString().trim();
                String matKhau = txtPass.getText().toString().trim();
                if (tenDangNhap.equals("")) {
//                    Toast.makeText(LoginActivity.this, "T??n ????ng nh???p kh??ng ???????c ????? tr???ng!", Toast.LENGTH_SHORT).show();
                    thongBao(Gravity.CENTER, "T??n ????ng nh???p kh??ng ???????c ????? tr???ng!");
                    return;
                }
                if (matKhau.equals("")) {
//                    Toast.makeText(LoginActivity.this, "M???t kh???u kh??ng ???????c ????? tr???ng!", Toast.LENGTH_SHORT).show();
                    thongBao(Gravity.CENTER, "M???t kh???u kh??ng ???????c ????? tr???ng!");
                    return;
                }
                nhanVienDangNhap = dbNhanVien.xetDangNhap(tenDangNhap, matKhau);
                if (nhanVienDangNhap != null) {
                    Toast.makeText(LoginActivity.this, "????NG NH???P TH??NH C??NG!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    //s??? d???ng bundle g???i d??? li???u c???a object nh??n vi??n
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("thong_tin_nv", nhanVienDangNhap);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
//                    Toast.makeText(LoginActivity.this, "XEM L???I T??N ????NG NH???P V?? M???T KH???U", Toast.LENGTH_SHORT).show();
                    thongBao(Gravity.CENTER, "Xem l???i t??n ????ng nh???p v?? m???t kh???u");
                }
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String tenDangNhap = txtUser.getText().toString().trim();
                if (tenDangNhap.equals("")) {
//                    Toast.makeText(LoginActivity.this, "B???n c???n nh???p t??n ????ng nh???p!", Toast.LENGTH_SHORT).show();
                    thongBao(Gravity.CENTER, "B???n c???n nh???p t??n ????ng nh???p!");
                    return;
                }
                NhanVien nhanVien = dbNhanVien.xetGuiMail(tenDangNhap);

                if (nhanVien != null) {
//                    thongBao(Gravity.CENTER, "VUI L??NG ?????I, H??? TH???NG ??ANG X??? L??");
                    String matKhauMoi = taoMatKhau();
                    dbNhanVien.suaMatKhau(nhanVien.getTenDangNhap(), matKhauMoi);
                    tvForgotPass.setEnabled(false);
//                    Toast.makeText(LoginActivity.this, "VUI L??NG ?????I, H??? TH???NG ??ANG X??? L??", Toast.LENGTH_SHORT).show();

                    guiMail(nhanVien.getMail(), matKhauMoi);
                } else
                    thongBao(Gravity.CENTER, "T??n ????ng nh???p kh??ng t???n t???i");
//                    Toast.makeText(LoginActivity.this, "T??N ????NG NH???P KH??NG T???N T???I!", Toast.LENGTH_SHORT).show();
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void thongBao(int gravity, String noiDung){
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_thongbao_dangnhap);

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

        TextView tvThongBao = dialog.findViewById(R.id.tvThongbao_DangNhap);
        tvThongBao.setText(noiDung);
        dialog.show();
    }

    public String taoMatKhau() {
        Random generator = new Random();
        int value = generator.nextInt((999999 - 100000) + 1) + 100000;
        return value + "";
    }

    private void guiMail(String mailToSend, String matKhauMoi) {
        Properties pros = new Properties();
        pros.put("mail.smtp.auth", "true");
        pros.put("mail.smtp.starttls.enable", "true");
        pros.put("mail.smtp.host", "smtp.gmail.com");
        pros.put("mail.smtp.port", "587");
        Session session = Session.getInstance(pros, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailToSend));
            message.setSubject("APP QU???N L?? THI???T B??? TR?????NG H???C - L???Y L???I M???T KH???U");
            message.setText("M???t kh???u m???i c???a b???n l??: " + matKhauMoi);
            Transport.send(message);
//            Toast.makeText(LoginActivity.this, "M???t kh???u m???i ???? ???????c g???i v??o mail!", Toast.LENGTH_SHORT).show();
            thongBao(Gravity.CENTER, "M???t kh???u m???i ???? ???????c g???i v??o mail!");

        } catch (MessagingException e) {
            Log.e("L???i", e.getMessage());
        }
        tvForgotPass.setEnabled(true);
    }

    private void setControl() {
        txtUser = findViewById(R.id.txtUser);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        tvTieuDeQL = findViewById(R.id.tvTieuDeQL);
    }
}