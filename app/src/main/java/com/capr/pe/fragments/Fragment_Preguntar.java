package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Local_DTO;
import com.capr.pe.dialog.Dialog_Categoria;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Locales_Cercanos;
import com.capr.pe.util.Util_Fonts;
import com.capr.pe.views.View_Local;

import java.util.ArrayList;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Preguntar extends Fragment implements View.OnClickListener {

    private LinearLayout linearLayout;
    private int page = 1;

    public static final Fragment_Preguntar newInstance() {
        return new Fragment_Preguntar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preguntar, container, false);

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
        //changeActionBar();

        Dialog_Categoria dialog_categoria = new Dialog_Categoria(getActivity());
        dialog_categoria.show();
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
                ((Maven)getActivity()).sm_menu.toggle();
            }
        });

        TextView edtbuscar = (TextView)getView().findViewById(R.id.edt_buscar_categoria);
        edtbuscar.setTypeface(Util_Fonts.setPNALight(getActivity()));
        edtbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Maven)getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Busqueda.newInstance(),"fragment_busqueda").addToBackStack("a").commit();
            }
        });
    }
}
