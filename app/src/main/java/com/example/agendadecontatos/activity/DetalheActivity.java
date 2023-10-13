package com.example.agendadecontatos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.agendadecontatos.R;
import com.example.agendadecontatos.data.ContatoDAO;
import com.example.agendadecontatos.model.Contato;

public class DetalheActivity extends AppCompatActivity {

    private Contato contato;
    private ContatoDAO contatoDAO;
    private EditText etNome;
    private EditText etEmail;
    private EditText etTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar toolbar = findViewById(R.id.toolbar_detalhe);
        setSupportActionBar(toolbar);

        etNome = findViewById(R.id.et_nome);
        etEmail = findViewById(R.id.et_email);
        etTelefone = findViewById(R.id.et_telefone);

        if (getIntent().hasExtra("contato")) {
            this.contato = (Contato) getIntent().getSerializableExtra("Contato");

            etNome.setText(this.contato.getNome());
            etEmail.setText(this.contato.getEmail());
            etTelefone.setText(this.contato.getFone());

            int pos = this.contato.getNome().indexOf(" ");
            if (pos == -1) {
                pos = this.contato.getNome().length();
            }
            setTitle(this.contato.getNome().substring(0, pos));
        }
        this.contatoDAO = new ContatoDAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("contato")) {
            MenuItem item = menu.findItem(R.id.excluir_contato);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.salvar_contato) {
            salvar();
        } else if (item.getItemId() == R.id.excluir_contato) {
            apagar();
        }
        return super.onOptionsItemSelected(item);
    }

    public void salvar() {
        if (this.contato == null) {
            this.contato = new Contato();
        }
        this.contato.setNome(etNome.getText().toString());
        this.contato.setFone(etTelefone.getText().toString());
        this.contato.setEmail(etEmail.getText().toString());

        this.contatoDAO.salvarContato(contato);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void apagar() {
        this.contatoDAO.apagarContato(this.contato);
        Intent intent = new Intent();
        setResult(3, intent);
        finish();
    }
}