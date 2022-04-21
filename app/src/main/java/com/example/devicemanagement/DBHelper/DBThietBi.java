package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBThietBi extends SQLiteOpenHelper {
    public DBThietBi(@Nullable Context context) {
        super(context, "DBThietBi", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE thietbi (matb TEXT PRIMARY KEY, tentb TEXT, xuatxu TEXT, soluong TEXT, " +
                "maloai TEXT, FOREIGN KEY(maloai) REFERENCES Loaithietbi(maloai) ON DELETE CASCADE ON UPDATE NO ACTION)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS thietbi");
        onCreate(db);
    }
}
