package com.codekolih.producciontablet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.clases.Imprentas;
import java.util.ArrayList;
import java.util.List;

public class AdapterImprentas extends RecyclerView.Adapter<AdapterImprentas.NoteViewHolder> {

    private List<Imprentas> notes;
    private OnNoteSelectedListener onNoteSelectedListener;
    private OnNoteDetailListener onDetailListener;
    private Context context;

    public AdapterImprentas() {
        this.notes = new ArrayList<>();

    }

    public AdapterImprentas(List<Imprentas> notes) {
        this.notes = notes;

    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View elementoTitular = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_imprentas, parent, false);

        context = elementoTitular.getContext();
        return new NoteViewHolder(elementoTitular);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder view, int pos) {
        view.bind(notes.get(pos));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public List<Imprentas> getNotes() {
        return notes;
    }

    public void setNotes(List<Imprentas> notes) {
        this.notes = notes;
    }

    public void setFilter(List<Imprentas> notes){
        this.notes.addAll(notes);
      //  notifyDataSetChanged();
    }


    public void setOnNoteSelectedListener(OnNoteSelectedListener onNoteSelectedListener) {
        this.onNoteSelectedListener = onNoteSelectedListener;
    }

    public void setOnDetailListener(OnNoteDetailListener onDetailListener) {
        this.onDetailListener = onDetailListener;
    }


    public interface OnNoteSelectedListener {
        void onClick(Imprentas note);
    }

    public interface OnNoteDetailListener {
        void onDetail(Imprentas note);
    }

    public Imprentas getposicionactual(int position) {
        return notes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre;

        public NoteViewHolder(View item) {
            super(item);

            nombre = (TextView) item.findViewById(R.id.item_produccion_txt_nombre);

        }

        public void bind(final Imprentas imprenta) {

            nombre.setText((imprenta.getNombreMaquina()));


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNoteSelectedListener != null) {
                        onNoteSelectedListener.onClick(imprenta);
                    }
                }
            });
        }
    }

}
