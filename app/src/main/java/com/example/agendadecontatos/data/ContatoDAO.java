package com.example.agendadecontatos.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agendadecontatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContatoDAO(Context context) {
        this.dbHelper = new SQLiteHelper(context);
    }

    public List<Contato> buscarTodosContatos() {
        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor;
        String[] cols = new String[] {SQLiteHelper.KEY_ID, SQLiteHelper.KEY_NAME,
                SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL};
        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, null, null,
                null, null, SQLiteHelper.KEY_NAME);

        while (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contatos.add(contato);
        }
        cursor.close();
        database.close();
        return contatos;
    }

    public List<Contato> buscarContato(String nome) {
        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor;
        String[] cols = new String[] {SQLiteHelper.KEY_ID, SQLiteHelper.KEY_NAME,
                SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL};
        String where = SQLiteHelper.KEY_NAME + " like ?";
        String[] argWhere = new String[]{nome + " %"};
        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where, argWhere,
                null, null, SQLiteHelper.KEY_NAME);

        while (cursor.moveToNext()) {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contatos.add(contato);
        }
        cursor.close();
        database.close();
        return contatos;
    }

    public void salvarContato(Contato contato) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NAME, contato.getNome());
        values.put(SQLiteHelper.KEY_EMAIL, contato.getEmail());
        values.put(SQLiteHelper.KEY_FONE, contato.getFone());

        if (contato.getId() > 0) {
            database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "=" + contato.getId(), null);
        } else {
            database.insert(SQLiteHelper.DATABASE_TABLE, null, values);
        }
        database.close();
    }

    public void apagarContato(Contato contato) {
        database = dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.DATABASE_TABLE, SQLiteHelper.KEY_ID + "=" + contato.getId(), null);
        database.close();
    }
}
