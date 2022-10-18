package com.codekolih.producciontablet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.clases.Produccion_Lista;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduccion extends RecyclerView.Adapter<AdapterProduccion.NoteViewHolder> {

    private List<Produccion_Lista> notes;
    private OnNoteSelectedListener onNoteSelectedListener;
    private OnNoteDetailListener onDetailListener;
    private Context context;

    public AdapterProduccion() {
        this.notes = new ArrayList<>();
    }

    public AdapterProduccion(List<Produccion_Lista> notes) {
        this.notes = notes;
    }


    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View elementoTitular = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_produccion, parent, false);

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

    public List<Produccion_Lista> getNotes() {
        return notes;
    }

    public void setNotes(List<Produccion_Lista> notes) {
        this.notes = notes;
    }

    public void setFilter(List<Produccion_Lista> notes){
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
        void onClick(Produccion_Lista note);
    }

    public interface OnNoteDetailListener {
        void onDetail(Produccion_Lista note);
    }



    public Produccion_Lista getposicionactual(int position) {
        return notes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        public NoteViewHolder(View item) {
            super(item);

            nombre = (TextView) item.findViewById(R.id.item_produccion_txt_tarea);

        }

        public void bind(final Produccion_Lista produccion) {

            nombre.setText((produccion.getUserNameId()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNoteSelectedListener != null) {
                        onNoteSelectedListener.onClick(produccion);
                    }
                }
            });
        }
    }

}
