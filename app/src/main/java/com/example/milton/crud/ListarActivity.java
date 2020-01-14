package com.example.milton.crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.addAll;

public class ListarActivity extends AppCompatActivity {


    private ListView listView;
    private AccessDB accessDB;
    private List<Aluno> alunos;
    private List<Aluno> alunosConsultados = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        listView = findViewById(R.id.lista_alunos);
        accessDB = new AccessDB(this);
        alunos = accessDB.obterAlunos();
        alunosConsultados.addAll(alunos);
        AlunoAdapter adapter = new AlunoAdapter(alunosConsultados, this);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }


    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);
        SearchView sv = (SearchView)menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                pesquisaAluno(s);
                return false;
            }
        });
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void pesquisaAluno (String nome){

        alunosConsultados.clear();
        for(Aluno a : alunos ) {
            if (a.getNome().toLowerCase().contains(nome.toLowerCase())){
            alunosConsultados.add(a);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item){

        AdapterView.AdapterContextMenuInfo menuInfo  =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
       final Aluno alunoExcluir = alunosConsultados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder( this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excuir o aluno")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        alunosConsultados.remove(alunoExcluir);
                        alunos.remove(alunoExcluir);
                        accessDB.excluir(alunoExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void cadastrar(MenuItem item){

        Intent intencao = new Intent(this,MainActivity.class);
        startActivity(intencao);
    }


    public void atualizar (MenuItem item){

        AdapterView.AdapterContextMenuInfo menuInfo  =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoAtualizar = alunosConsultados.get(menuInfo.position);
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("aluno", alunoAtualizar);
        startActivity(it);
    }


    @Override
    public void onResume(){

        super.onResume();
        alunos = accessDB.obterAlunos();
        alunosConsultados.clear();
        alunosConsultados.addAll(alunos);
        listView.invalidateViews();
    }
}
