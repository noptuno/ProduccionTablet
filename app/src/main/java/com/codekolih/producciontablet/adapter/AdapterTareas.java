package com.codekolih.producciontablet.adapter;

import static java.lang.Integer.parseInt;

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
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.clases.Produccion_Lista;
import com.codekolih.producciontablet.clases.Tareas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AdapterTareas extends RecyclerView.Adapter<AdapterTareas.NoteViewHolder> {

    private List<Tareas> notes;
    private OnNoteSelectedListener onNoteSelectedListener;
    private OnNoteDetailListener onDetailListener;
    private Context context;
private int contadorgeneral = 0;
    private int posiciones = 0;


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

        Log.e("Posiciones",""+pos);

        view.bind(notes.get(pos),pos);
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
        void onClick(Tareas note,int p);
    }

    public interface OnNoteDetailListener {
        void onDetail(Tareas note);
    }



    public Tareas getposicionactual(int position) {
        return notes.get(position);
    }



    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView serieynumero,concepto,totalcantidad,restante,observaciones,articuloid,item_ultimoEstado;
        private TextView txtproducir,txtrestante;
        private LinearLayout layoutTareas;
        private int pos;

        public NoteViewHolder(View item) {
            super(item);

            serieynumero = (TextView) item.findViewById(R.id.item_tarea_txt_syn);
            articuloid = (TextView) item.findViewById(R.id.item_tarea_txt_articuloid);
            concepto = (TextView) item.findViewById(R.id.item_tarea_txt_concepto);
            totalcantidad = (TextView) item.findViewById(R.id.item_tarea_txt_totalcantidad);
            restante = (TextView) item.findViewById(R.id.item_tarea_txt_restante);

            txtproducir = (TextView) item.findViewById(R.id.item_tarea_txt_nombrecantidad);
                    txtrestante = (TextView) item.findViewById(R.id.item_tarea_txt_nombrecurso);


            observaciones = (TextView) item.findViewById(R.id.item_tarea_txt_obseraciones);
            layoutTareas= (LinearLayout) item.findViewById(R.id.layoutTareas);

            item_ultimoEstado= (TextView) item.findViewById(R.id.item_tarea_ultimoEstado);

        }

        public void bind(final Tareas tarea, int poss) {
            this.pos = poss;

          //  Log.e("cargoRecicler",tarea.toString());
          //  Random random = new Random();

            ColorDiagram a = new ColorDiagram();

           // int color = Color.argb(255,random.nextInt(256),(random.nextInt(256)),(random.nextInt(256)));

           // layoutTareas.setBackgroundColor(a.getColor());
            articuloid.setText(String.format("%s", tarea.getArticuloId()));
            serieynumero.setText(String.format("%s", tarea.getSerieYNro()));
            concepto.setText(tarea.getConcepto());

            float cont = 0;
            if (tarea.getProduccion_Lista().size()>0){
                for (Produccion_Lista lg : tarea.getProduccion_Lista()) {

                    for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {

                        if ("SumMetrosImpresos".equals(entry.getKey())){
                            txtproducir.setText("METROS A PRODUCIR");
                            txtrestante.setText("METROS EN CURSO");
                            if (entry.getValue().equals("0")){
                                cont+= lg.getMetrosImpresos();
                            }
                        }  else if ("SumRollosFabricados".equals(entry.getKey())){

                            txtproducir.setText("ROLLOS A PRODUCIR");
                            txtrestante.setText("ROLLOS EN CURSO");

                            if (entry.getValue().equals("0")){
                                cont+= lg.getRollosFabricdos();
                            }
                        } else if ("SumRollosEmpaquedatos".equals(entry.getKey())){

                            txtproducir.setText("ROLLOS A PRODUCIR");
                            txtrestante.setText("ROLLOS EN CURSO");

                            if (entry.getValue().equals("0")){
                                cont+= lg.getRollosEmpaquetados();
                            }
                        }
                    }
                }

            }
            item_ultimoEstado.setText(tarea.getUltimoEstadoIdAvance());
            totalcantidad.setText(String.format("%s", tarea.getCantidad()));
            restante.setText(String.format("%s", cont));


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (onNoteSelectedListener != null) {

                        onNoteSelectedListener.onClick(tarea,getAdapterPosition());

                    }
                }
            });
        }
    }

}
