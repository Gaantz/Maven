package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Local_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Locales_Cercanos;
import com.capr.pe.util.Util_Fonts;
import com.capr.pe.views.View_Local;

import java.util.ArrayList;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Resultado_Busquedas extends Fragment implements View.OnClickListener {

    private LinearLayout linearLayout;
    private int page = 1;

    private ArrayList<Local_DTO> local_dtos;

    public static final Fragment_Resultado_Busquedas newInstance(ArrayList<Local_DTO> local_dtos) {
        Fragment_Resultado_Busquedas fragment_resultado_busquedas = new Fragment_Resultado_Busquedas();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("local_dtos",local_dtos);
        fragment_resultado_busquedas.setArguments(bundle);
        return fragment_resultado_busquedas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        local_dtos = getArguments().getParcelableArrayList("local_dtos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeActionBar();
        linearLayout = (LinearLayout) getView().findViewById(R.id.container_locales);

        for (int i = 0; i < local_dtos.size(); i++) {
            Local_DTO local_dto = local_dtos.get(i);
            View_Local view_local = new View_Local(getActivity(), local_dto);
            view_local.setTag(local_dto);
            linearLayout.addView(view_local);
        }

        ((Button) getView().findViewById(R.id.btn_cargar_mas)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        page++;

        Operation_Locales_Cercanos operation_locales_cercanos = new Operation_Locales_Cercanos(getActivity());
        operation_locales_cercanos.setPage(page);
        operation_locales_cercanos.getLocalesCercanos();
        operation_locales_cercanos.setInterface_operation_locales_cercanos(new Operation_Locales_Cercanos.Interface_Operation_Locales_Cercanos() {
            @Override
            public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages) {
                for (int i = 0; i < local_dtos.size(); i++) {
                    Local_DTO local_dto = local_dtos.get(i);
                    View_Local view_local = new View_Local(getActivity(), local_dto);
                    view_local.setTag(local_dto);
                    linearLayout.addView(view_local);
                }
            }
        });
    }

    private void changeActionBar() {

        getView().findViewById(R.id.imgopenmenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Maven) getActivity()).sm_menu.toggle();
            }
        });

        TextView edtbuscar = (TextView) getView().findViewById(R.id.edt_buscar_categoria);
        edtbuscar.setTypeface(Util_Fonts.setPNALight(getActivity()));
        edtbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Maven) getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Busqueda.newInstance(), "fragment_busqueda").addToBackStack("a").commit();
            }
        });
    }
}
