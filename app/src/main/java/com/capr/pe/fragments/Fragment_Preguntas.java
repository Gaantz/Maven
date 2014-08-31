package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.capr.pe.beans.Local_DTO;
import com.capr.pe.beans.Pregunta_DTO;
import com.capr.pe.dialog.Dialog_Categoria;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Preguntas;
import com.capr.pe.util.Util_Fonts;
import com.capr.pe.views.View_Pregunta;

import java.util.ArrayList;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Preguntas extends Fragment implements View.OnClickListener {

    private LinearLayout linearLayout;
    private int page = 1;

    public static final Fragment_Preguntas newInstance() {
        return new Fragment_Preguntas();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pregunta, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeActionBar();
        linearLayout = (LinearLayout) getView().findViewById(R.id.containet_preguntas);

        Operation_Preguntas operation_preguntas = new Operation_Preguntas(getActivity());
        operation_preguntas.getPreguntas();
        operation_preguntas.setInterface_obtener_preguntas(new Operation_Preguntas.Interface_Obtener_Preguntas() {
            @Override
            public void getPreguntas(boolean status, ArrayList<Pregunta_DTO> pregunta_dtos, String mensaje) {
                if (status) {
                    for (int i = 0; i < pregunta_dtos.size(); i++) {
                        View_Pregunta view_pregunta = new View_Pregunta(getActivity(), pregunta_dtos.get(i));
                        view_pregunta.setTag(pregunta_dtos.get(i));
                        linearLayout.addView(view_pregunta);
                    }
                }
            }
        });

        //((TextView) getView().findViewById(R.id.btn_cargar_mas)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        page++;
    }

    private void changeActionBar() {

        getView().findViewById(R.id.imgopenmenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Maven) getActivity()).sm_menu.toggle();
            }
        });

        Button btnpreguntar = (Button) getView().findViewById(R.id.btnpreguntar);
        btnpreguntar.setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        btnpreguntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Categoria dialog_categoria = new Dialog_Categoria(getActivity());
                dialog_categoria.show();
                //((Maven)getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Preguntar.newInstance()).addToBackStack("a").commit();
            }
        });
    }
}
