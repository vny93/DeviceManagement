package com.example.devicemanagement.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBLoaiThietBi extends SQLiteOpenHelper {
    public DBLoaiThietBi(@Nullable Context context) {
        super(context, "DBLoaiThietBi", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE loaithietbi (maloai TEXT PRIMARY KEY, tenloai TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS loaithietbi");
        onCreate(db);
    }
}
