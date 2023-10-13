package com.example.agendadecontatos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendadecontatos.R;
import com.example.agendadecontatos.model.Contato;

import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder> {

    private List<Contato> contatos;
    private Context context;
    private static ItemClickListener clickListener;

    public ContatoAdapter(List<Contato> contatos, Context context) {
        this.contatos = contatos;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        Contato contato = contatos.get(position);
        holder.nome.setText(contato.getNome());
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.contato_celula, parent, false);
        return new ContatoViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return this.contatos.size();
    }

    public class ContatoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView nome;

        ContatoViewHolder(View view) {
            super(view);
            nome = view.findViewById(R.id.tv_nome);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
