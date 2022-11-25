package com.codekolih.producciontablet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.ColorDiagram;
import com.codekolih.producciontablet.clases.Tareas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdapterTareas extends RecyclerView.Adapter<AdapterTareas.NoteViewHolder> {

    private List<Tareas> notes;
    private OnNoteSelectedListener onNoteSelectedListener;
    private OnNoteDetailListener onDetailListener;
    private Context context;

    public AdapterTareas() {
        this.notes = new ArrayList<>();
    }

    public AdapterTareas(List<Tareas> notes) {
        this.notes = notes;
    }


    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View elementoTitular = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note_tareas, parent, false);

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

    public List<Tareas> getNotes() {
        return notes;
    }

    public void setNotes(List<Tareas> notes) {
        this.notes = notes;
    }

    public void setFilter(List<Tareas> notes){
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
        void onClick(Tareas note);
    }

    public interface OnNoteDetailListener {
        void onDetail(Tareas note);
    }





    public Tareas getposicionactual(int position) {
        return notes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView serieynumero,concepto,totalcantidad,restante,observaciones;
        private LinearLayout layoutTareas;
        public NoteViewHolder(View item) {
            super(item);

            serieynumero = (TextView) item.findViewById(R.id.item_tarea_txt_syn);
            concepto = (TextView) item.findViewById(R.id.item_tarea_txt_concepto);
            totalcantidad = (TextView) item.findViewById(R.id.item_tarea_txt_totalcantidad);
            restante = (TextView) item.findViewById(R.id.item_tarea_txt_restante);
            observaciones = (TextView) item.findViewById(R.id.item_tarea_txt_obseraciones);
            layoutTareas= (LinearLayout) item.findViewById(R.id.layoutTareas);


        }

        public void bind(final Tareas tarea) {

          //  Log.e("cargoRecicler",tarea.toString());

          //  Random random = new Random();


            ColorDiagram a = new ColorDiagram();

           // int color = Color.argb(255,random.nextInt(256),(random.nextInt(256)),(random.nextInt(256)));
            layoutTareas.setBackgroundColor(a.getColor());



            serieynumero.setText(""+tarea.getNroDeSobre());
            concepto.setText(tarea.getDescripcion());
            totalcantidad.setText(""+tarea.getMetrosAImprimir());
            restante.setText(""+tarea.getMetrosPorRollo());
            observaciones.setText(tarea.getObservaciones());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNoteSelectedListener != null) {
                        onNoteSelectedListener.onClick(tarea);
                    }
                }
            });
        }
    }

}
