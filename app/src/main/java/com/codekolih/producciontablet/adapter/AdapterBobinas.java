package com.codekolih.producciontablet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.clases.Bobinas;
import com.codekolih.producciontablet.clases.Produccion_Lista;

import java.util.ArrayList;
import java.util.List;

public class AdapterBobinas extends RecyclerView.Adapter<AdapterBobinas.NoteViewHolder> {

    private List<Bobinas> notes;
    private OnNoteSelectedListener onNoteSelectedListener;
    private OnNoteDetailListener onDetailListener;
    private Context context;

    public AdapterBobinas() {
        this.notes = new ArrayList<>();
    }

    public AdapterBobinas(List<Bobinas> notes) {
        this.notes = notes;
    }


    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View elementoTitular = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_bobina, parent, false);

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

    public List<Bobinas> getNotes() {
        return notes;
    }

    public void setNotes(List<Bobinas> notes) {
        this.notes = notes;
    }

    public void setFilter(List<Bobinas> notes){
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
        void onClick(Bobinas note);
    }

    public interface OnNoteDetailListener {
        void onDetail(Bobinas note);
    }


    public Bobinas getposicionactual(int position) {
        return notes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView ProveedorNombre, DefectuosaKg, Lote,Ancho,TipoMaterialId,EsAbiertaoCerrada;
        public NoteViewHolder(View item) {
            super(item);

            ProveedorNombre = (TextView) item.findViewById(R.id.item_bobina_txt_ProveedorNombre);
            DefectuosaKg = (TextView) item.findViewById(R.id.item_bobina_txt_DefectuosaKg);
            Lote = (TextView) item.findViewById(R.id.item_bobina_txt_Lote);
            Ancho = (TextView) item.findViewById(R.id.item_bobina_txt_Ancho);
            TipoMaterialId = (TextView) item.findViewById(R.id.item_bobina_txt_TipoMaterialId);
            EsAbiertaoCerrada= (TextView) item.findViewById(R.id.item_bobina_txt_EsAbiertaoCerrada);


        }

        public void bind(final Bobinas bobina) {

            ProveedorNombre.setText((bobina.getProveedorNombre()));
            DefectuosaKg.setText((""+bobina.getDefectuosaKg()));
            Lote.setText((""+bobina.getLote()));
            Ancho.setText((""+bobina.getAncho()));
            TipoMaterialId.setText((""+bobina.getTipoMaterialId()));
            EsAbiertaoCerrada.setText((""+bobina.getEsAbiertaoCerrada()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNoteSelectedListener != null) {
                        onNoteSelectedListener.onClick(bobina);
                    }
                }
            });
        }
    }

}