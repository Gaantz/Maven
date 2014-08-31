package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.beans.Local_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Locales_Cercanos;
import com.capr.pe.operation.Operation_Locales_Life;
import com.capr.pe.util.Util_Fonts;
import com.capr.pe.views.View_Empresa_Life;
import com.capr.pe.views.View_Local;
import com.capr.pe.views.View_Local_Life;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Local_Life extends Fragment implements View.OnClickListener {

    private LinearLayout linearLayout;
    private int page = 1;
    private Empresa_DTO empresa_dto;

    public static final Fragment_Local_Life newInstance(Empresa_DTO empresa_dto) {
        Fragment_Local_Life fragment_local_life = new Fragment_Local_Life();
        Bundle bundle = new Bundle();
        bundle.putSerializable("empresa_dto",empresa_dto);
        fragment_local_life.setArguments(bundle);
        return fragment_local_life;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        empresa_dto = (Empresa_DTO) getArguments().getSerializable("empresa_dto");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_local_life, container, false);

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
        linearLayout = (LinearLayout) getView().findViewById(R.id.container_locales);
        getView().findViewById(R.id.framecargando).setVisibility(View.VISIBLE);

        View_Empresa_Life view_empresa_life = new View_Empresa_Life(getActivity(),empresa_dto);
        ((LinearLayout) getView().findViewById(R.id.container_header_empresa)).addView(view_empresa_life);

        try {
            String empresaId = empresa_dto.getJsonObject().getString("EmpresaId");
            Operation_Locales_Life operation_locales_life = new Operation_Locales_Life(getActivity());
            operation_locales_life.getLocalesCercanos(empresaId);
            operation_locales_life.setInterface_operation_locales_life(new Operation_Locales_Life.Interface_Operation_Locales_Life() {
                @Override
                public void getLocalesLife(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                    if(status){
                        getView().findViewById(R.id.framecargando).setVisibility(View.GONE);
                        for (int i = 0; i < local_dtos.size(); i++) {
                            Local_DTO local_dto = local_dtos.get(i);
                            View_Local_Life view_local_life = new View_Local_Life(getActivity(), local_dto);
                            view_local_life.setTag(local_dto);
                            linearLayout.addView(view_local_life);
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getView().findViewById(R.id.btn_cargar_mas).setOnClickListener(this);
        getView().findViewById(R.id.btn_cargar_mas).setVisibility(View.GONE);

                /*
        Button Cerrar
         */
        getView().findViewById(R.id.imgcerrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(Fragment_Local_Life.this);
                trans.commit();
                manager.popBackStack();
            }
        });
    }

    @Override
    public void onClick(View v) {
        page++;

        Operation_Locales_Life operation_locales_cercanos = new Operation_Locales_Life(getActivity());
        //operation_locales_cercanos.setPage(page);
        //operation_locales_cercanos.getLocalesCercanos(empresaId);
        operation_locales_cercanos.setInterface_operation_locales_life(new Operation_Locales_Life.Interface_Operation_Locales_Life() {
            @Override
            public void getLocalesLife(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                for (int i = 0; i < local_dtos.size(); i++) {
                    Local_DTO local_dto = local_dtos.get(i);
                    View_Local_Life view_local_life = new View_Local_Life(getActivity(), local_dto);
                    view_local_life.setTag(local_dto);
                    linearLayout.addView(view_local_life);
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
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Busqueda_Life.newInstance(empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
            }
        });
    }
}
