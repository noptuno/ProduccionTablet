package com.codekolih.producciontablet.adapter;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.clases.Bobinas;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        view.bind(notes.get(pos),pos);
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
        void onClick(Bobinas note,int p);
    }

    public interface OnNoteDetailListener {
        void onDetail(Bobinas note);
    }


    public Bobinas getposicionactual(int position) {
        return notes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView ProveedorNombre, txtDefectuosaKg,DefectuosaKg, Lote,Ancho,TipoMaterialId,EsAbiertaoCerrada,NombreTipoMaterial;
        private LinearLayout ly_ProveedorNombre,ly_NombreTipoMaterial, ly_DefectuosaKg, ly_Lote,ly_Ancho,ly_TipoMaterialId,ly_EsAbiertaoCerrada;
        private int pos;

        public NoteViewHolder(View item) {
            super(item);

            ly_ProveedorNombre = (LinearLayout) item.findViewById(R.id.ly_ProveedorNombre);
            ly_NombreTipoMaterial = (LinearLayout) item.findViewById(R.id.ly_NombreTipoMaterial);
            ly_DefectuosaKg = (LinearLayout) item.findViewById(R.id.ly_DefectuosaKg);
            ly_Lote = (LinearLayout) item.findViewById(R.id.ly_Lote);
            ly_Ancho = (LinearLayout) item.findViewById(R.id.ly_Ancho);
            ly_EsAbiertaoCerrada= (LinearLayout) item.findViewById(R.id.ly_EsAbiertaoCerrada);

            ProveedorNombre = (TextView) item.findViewById(R.id.item_bobina_txt_ProveedorNombre);


            txtDefectuosaKg = item.findViewById(R.id.txt_bobina_txt_DefectuosaKg);


            DefectuosaKg = (TextView) item.findViewById(R.id.item_bobina_txt_DefectuosaKg);
            Lote = (TextView) item.findViewById(R.id.item_bobina_txt_Lote);
            Ancho = (TextView) item.findViewById(R.id.item_bobina_txt_Ancho);
            NombreTipoMaterial = (TextView) item.findViewById(R.id.item_bobina_txt_NombreTipoMaterial);
            EsAbiertaoCerrada= (TextView) item.findViewById(R.id.item_bobina_txt_EsAbiertaoCerrada);


            for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

                if ("Ancho".equals(entry.getKey())){
                    ly_Ancho.setVisibility(parseInt(entry.getValue()));
                }  else if ("EsAbiertaoCerrada".equals(entry.getKey())){
                    ly_EsAbiertaoCerrada.setVisibility(parseInt(entry.getValue()));
                } else if ("DefectuosaKg".equals(entry.getKey())){
                    ly_DefectuosaKg.setVisibility(parseInt(entry.getValue()));
                }
            }

           Tareas tarea_Seleccionada = TareaSingleton.SingletonInstance().getTarea();

            Log.e("Tipotarea de Tareas",tarea_Seleccionada.getTipoMaquinaId()+"");

            if (TareaSingleton.SingletonInstance().getTipomaquinaid()== 4){
                txtDefectuosaKg.setText("Metros o kilos de bobina");
            }

            Log.e("Tipotarea de Singleton",TareaSingleton.SingletonInstance().getTipomaquinaid()+"");

        }

        public void bind(final Bobinas bobina, int poss) {


            this.pos = poss;

            Log.e("Boniba",bobina.getProveedorNombre());

            ProveedorNombre.setText((bobina.getProveedorNombre()));
            DefectuosaKg.setText((String.format("%s", bobina.getDefectuosaKg())));
            Lote.setText((String.format("%s", bobina.getLote())));
            Ancho.setText((String.format("%s", bobina.getAncho())));

            NombreTipoMaterial.setText((String.format("%s", bobina.getNombreTipoMaterial())));
            EsAbiertaoCerrada.setText((String.format("%s", bobina.getEsAbiertaoCerrada())));

            if (bobina.getEsAbiertaoCerrada().equals("A")){

                EsAbiertaoCerrada.setText("ABIERTA");

            }else if(bobina.getEsAbiertaoCerrada().equals("C")){

                EsAbiertaoCerrada.setText("CERRADA");

            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNoteSelectedListener != null) {
                        onNoteSelectedListener.onClick(bobina,getAdapterPosition());
                    }
                }
            });
        }
    }

}
