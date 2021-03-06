package com.example.milton.crud;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AccessDB {

    private ConexaoDB conexaodb;
    private SQLiteDatabase banco;

    public AccessDB (Context context){

        conexaodb = new ConexaoDB(context);
        banco = conexaodb.getWritableDatabase();
    }

    public long inserir (Aluno aluno ){

        ContentValues values = new ContentValues();
        values.put("nome",aluno.getNome());
        values.put("cpf",aluno.getCPF());
        values.put("telefone",aluno.getTelefone());
        return banco.insert("aluno",null,values);
    }

    public List<Aluno> obterAlunos(){

        List<Aluno> alunos = new ArrayList<>();
        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone"},null,
                null, null, null, null);
        while(cursor.moveToNext()){
            Aluno aluno =  new Aluno();
            aluno.setId(cursor.getInt(0));
            aluno.setNome(cursor.getString(1));
            aluno.setCPF(cursor.getString(2));
            aluno.setTelefone(cursor.getString(3));
            alunos.add(aluno);
        }
        return alunos;
    }

    public void  excluir (Aluno aluno){

        banco.delete("aluno", "id = ?", new String[]{aluno.getId().toString()});
    }

    public void atualizar(Aluno aluno){

        ContentValues values = new ContentValues();
        values.put("nome",aluno.getNome());
        values.put("cpf",aluno.getCPF());
        values.put("telefone",aluno.getTelefone());
        banco.update("aluno", values, "id = ?", new String[]{aluno.getId().toString()});

    }

}
