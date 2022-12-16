package com.codekolih.producciontablet.adapter;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codekolih.producciontablet.R;
import com.codekolih.producciontablet.aciones.TareaSingleton;
import com.codekolih.producciontablet.clases.Produccion_Lista;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        private TextView UsuarioId, MetrosImpresos, ScrapAjusteProduccion,ScrapAjusteProduccion_Unidades,RollosFabricdos,RollosEmpaquetados;
        private LinearLayout ly_MetrosImpresos,ly_ScrapAjusteProduccion,ly_ScrapAjusteProduccion_Unidades, ly_RollosFabricdos, ly_RollosEmpaquetados;

        private LinearLayout lyp_AnchoFinalRolloYGap,lyp_CantidadPistasImpresas,lyp_CantidadTintas,lyp_ScrapAjusteInicial,lyp_AnchoFinalRollo,lyp_CantidadPistasCortadas,lyp_PistasTroquelUsadas,lyp_UnidadIdScrapInicial;
        private TextView AnchoFinalRolloYGap,CantidadPistasImpresas,CantidadTintas,ScrapAjusteInicial,AnchoFinalRollo,CantidadPistasCortadas,PistasTroquelUsadas,UnidadIdScrapInicial;
        private TextView fecha,hora;
        public NoteViewHolder(View item) {
            super(item);

            ly_MetrosImpresos = (LinearLayout) item.findViewById(R.id.iply_MetrosImpresos);
            ly_ScrapAjusteProduccion = (LinearLayout) item.findViewById(R.id.iply_ScrapAjusteProduccion);
            ly_ScrapAjusteProduccion_Unidades = (LinearLayout) item.findViewById(R.id.iply_ScrapAjusteProduccion_Unidades);
            ly_RollosFabricdos = (LinearLayout) item.findViewById(R.id.iply_RollosFabricdos);
            ly_RollosEmpaquetados= (LinearLayout) item.findViewById(R.id.iply_RollosEmpaquetados);

             fecha = (TextView) item.findViewById(R.id.item_fecha);
            hora = (TextView) item.findViewById(R.id.item_hora);
            MetrosImpresos = (TextView) item.findViewById(R.id.item_produccion_txt_MetrosImpresos);
            ScrapAjusteProduccion = (TextView) item.findViewById(R.id.item_produccion_txt_ScrapAjusteProduccion);
            ScrapAjusteProduccion_Unidades = (TextView) item.findViewById(R.id.item_produccion_txt_ScrapAjusteProduccion_Unidades);
            RollosFabricdos = (TextView) item.findViewById(R.id.item_produccion_txt_RollosFabricdos);
            RollosEmpaquetados= (TextView) item.findViewById(R.id.item_produccion_txt_RollosEmpaquetados);


            //produccio unicavez
            AnchoFinalRolloYGap = (TextView) item.findViewById(R.id.item_produccion_AnchoFinalRolloYGap);
            CantidadPistasImpresas = (TextView) item.findViewById(R.id.item_produccion_CantidadPistasImpresas);
            CantidadTintas = (TextView) item.findViewById(R.id.item_produccion_CantidadTintas);
            ScrapAjusteInicial = (TextView) item.findViewById(R.id.item_produccion_ScrapAjusteInicial);
            AnchoFinalRollo = (TextView) item.findViewById(R.id.item_produccion_AnchoFinalRollo);
            CantidadPistasCortadas = (TextView) item.findViewById(R.id.item_produccion_CantidadPistasCortadas);
            PistasTroquelUsadas = (TextView) item.findViewById(R.id.item_produccion_PistasTroquelUsadas);
            UnidadIdScrapInicial = (TextView) item.findViewById(R.id.item_produccion_UnidadIdScrapInicial);

            lyp_AnchoFinalRolloYGap = (LinearLayout) item.findViewById(R.id.iply_AnchoFinalRolloYGap);
            lyp_CantidadPistasImpresas = (LinearLayout) item.findViewById(R.id.iply_CantidadPistasImpresas);
            lyp_CantidadTintas = (LinearLayout) item.findViewById(R.id.iply_CantidadTintas);
            lyp_ScrapAjusteInicial =(LinearLayout) item.findViewById(R.id.iply__ScrapAjusteInicial);
            lyp_AnchoFinalRollo = (LinearLayout) item.findViewById(R.id.iply_AnchoFinalRollo);
            lyp_CantidadPistasCortadas = (LinearLayout) item.findViewById(R.id.iply_CantidadPistasCortadas);
            lyp_PistasTroquelUsadas = (LinearLayout) item.findViewById(R.id.iply_PistasTroquelUsadas);
            lyp_UnidadIdScrapInicial = (LinearLayout) item.findViewById(R.id.iply_UnidadIdScrapInicial);

            for (Map.Entry<String, String> entry : TareaSingleton.SingletonInstance().getTipoMaquina().entrySet()) {



                if ("SumMetrosImpresos".equals(entry.getKey())){
                    ly_MetrosImpresos.setVisibility(parseInt(entry.getValue()));
                }  else if ("ScrapAjusteInicial".equals(entry.getKey())){
                    ly_ScrapAjusteProduccion.setVisibility(parseInt(entry.getValue()));
                } else if ("UnidadIdScrapInicial".equals(entry.getKey())){
                    ly_ScrapAjusteProduccion_Unidades.setVisibility(parseInt(entry.getValue()));
                }
                else if ("SumRollosFabricados".equals(entry.getKey())){
                    ly_RollosFabricdos.setVisibility(parseInt(entry.getValue()));
                }
                else if ("SumRollosEmpaquedatos".equals(entry.getKey())){
                    ly_RollosEmpaquetados.setVisibility(parseInt(entry.getValue()));
                }



                //produccion unicavez

                else if ("AnchoFinalRolloYGap".equals(entry.getKey())){
                    lyp_AnchoFinalRolloYGap.setVisibility(parseInt(entry.getValue()));
                }
                else if ("CantidadPistasImpresas".equals(entry.getKey())){
                    lyp_CantidadPistasImpresas.setVisibility(parseInt(entry.getValue()));
                }
                else if ("CantidadTintas".equals(entry.getKey())){
                    lyp_CantidadTintas.setVisibility(parseInt(entry.getValue()));
                }
                else if ("ScrapAjusteInicial".equals(entry.getKey())){
                    lyp_ScrapAjusteInicial.setVisibility(parseInt(entry.getValue()));
                }
                else if ("UnidadIdScrapInicial".equals(entry.getKey())){
                    lyp_UnidadIdScrapInicial.setVisibility(parseInt(entry.getValue()));
                }
                else if ("AnchoFinalRollo".equals(entry.getKey())){
                    lyp_AnchoFinalRollo.setVisibility(parseInt(entry.getValue()));
                }
                else if ("CantidadPistasCortadas".equals(entry.getKey())){
                    lyp_CantidadPistasCortadas.setVisibility(parseInt(entry.getValue()));
                }
                else if ("PistasTroquelUsadas".equals(entry.getKey())){
                    lyp_PistasTroquelUsadas.setVisibility(parseInt(entry.getValue()));
                }
            }



        }

        public void bind(final Produccion_Lista produccion) {


            String f = produccion.getFecha().substring(2,10);
            String h = produccion.getFecha().substring(11,16);
            hora.setText((String.format("%s", h)));
            fecha.setText((String.format("%s",  f)));
            MetrosImpresos.setText((String.format("%s", produccion.getMetrosImpresos())));
            ScrapAjusteProduccion.setText((String.format("%s", produccion.getScrapAjusteProduccion())));
            ScrapAjusteProduccion_Unidades.setText((produccion.getScrapAjusteProduccion_Unidades()));
            RollosFabricdos.setText((String.format("%s", produccion.getRollosFabricdos())));
            RollosEmpaquetados.setText((String.format("%s", produccion.getRollosEmpaquetados())));

            //datos tarea unicavez
            AnchoFinalRolloYGap.setText((String.format("%s", produccion.getAnchoFinalRolloYGap())));
            CantidadPistasImpresas.setText((String.format("%s", produccion.getCantidadPistasImpresas())));
            CantidadTintas.setText((String.format("%s", produccion.getCantidadTintas())));
            ScrapAjusteInicial.setText((String.format("%s", produccion.getScrapAjusteInicial())));
            AnchoFinalRollo.setText((String.format("%s", produccion.getAnchoFinalRollo())));
            CantidadPistasCortadas.setText((String.format("%s", produccion.getCantidadPistasCortadas())));
            PistasTroquelUsadas.setText((String.format("%s", produccion.getPistasTroquelUsadas())));
            UnidadIdScrapInicial.setText((String.format("%s", produccion.getScrapAjusteInicial_Unidades())));

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
