package com.example.milton.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexaoDB extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 1;

    public ConexaoDB( Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table aluno(id integer primary key autoincrement, " +
                "nome varchar(50),cpf varchar(50), telefone varchar (50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
