package com.example.agendadecontatos.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.agendadecontatos.R;
import com.example.agendadecontatos.adapter.ContatoAdapter;
import com.example.agendadecontatos.data.ContatoDAO;
import com.example.agendadecontatos.model.Contato;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContatoDAO contatoDAO;
    private RecyclerView recyclerView;
    private List<Contato> contatos = new ArrayList<>();
    private TextView tvEmpty;
    private ContatoAdapter contatoAdapter;
    private SearchView searchView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contatoDAO = new ContatoDAO(this);
        tvEmpty = findViewById(R.id.empty_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.contatos_rv);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);

        contatoAdapter = new ContatoAdapter(this.contatos, this);
        recyclerView.setAdapter(contatoAdapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetalheActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        updateUI(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                updateUI(null);
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {

            }
        }
        if (requestCode == 3) {

        }
    }

    public void updateUI(String nomeContato) {
        contatos.clear();
        if (nomeContato == null) {
            contatos.addAll(this.contatoDAO.buscarTodosContatos());
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}