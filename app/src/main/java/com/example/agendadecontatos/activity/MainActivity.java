package com.example.agendadecontatos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    /**
     * Trata o evento da Tela de Detalhes
     */
    ActivityResultLauncher<Intent> startActivityIntent  = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        atualizarListaContatos("");
                    }
                }
            });

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

        editarContato();
        atualizarListaContatos("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.adicionar_contato) {
            Intent intent = new Intent(getApplicationContext(), DetalheActivity.class);
            startActivityIntent.launch(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void verificarListaSemContatos() {
        if (this.contatos.size() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
    }

    private void atualizarListaContatos(String nomeContato) {
        contatos.clear();
        if (nomeContato.isEmpty()) {
            contatos.addAll(this.contatoDAO.buscarTodosContatos());
        }
        recyclerView.getAdapter().notifyDataSetChanged();
        verificarListaSemContatos();
    }

    private void editarContato() {
        contatoAdapter.setClickListener(new ContatoAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final Contato contato = contatos.get(position);

                Intent intent = new Intent(getApplicationContext(), DetalheActivity.class);
                intent.putExtra("contato", contato);
                startActivityIntent.launch(intent);
            }
        });
    }
}