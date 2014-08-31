package com.capr.pe.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.beans.Local_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Locales_Cercanos;
import com.capr.pe.util.Util_Fonts;
import com.capr.pe.views.View_Empresa_Life;
import com.capr.pe.views.View_Local;

import java.util.ArrayList;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Resultado_Busquedas_Life extends Fragment implements View.OnClickListener {

    private LinearLayout linearLayout;
    private int page = 1;

    private ArrayList<Local_DTO> local_dtos;
    private String busqueda;
    private Empresa_DTO empresa_dto;

    public static final Fragment_Resultado_Busquedas_Life newInstance(ArrayList<Local_DTO> local_dtos,String busqueda,Empresa_DTO empresa_dto) {
        Fragment_Resultado_Busquedas_Life fragment_resultado_busquedas = new Fragment_Resultado_Busquedas_Life();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("local_dtos", local_dtos);
        bundle.putString("busqueda", busqueda);
        bundle.putSerializable("empresa_dto", empresa_dto);
        fragment_resultado_busquedas.setArguments(bundle);
        return fragment_resultado_busquedas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSoftKeyboard();
        local_dtos = getArguments().getParcelableArrayList("local_dtos");
        busqueda = getArguments().getString("busqueda");
        empresa_dto = (Empresa_DTO) getArguments().getSerializable("empresa_dto");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_busqueda_life, container, false);
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
        ((TextView)getView().findViewById(R.id.txtcriterio)).setText(busqueda);
        ((TextView)getView().findViewById(R.id.txtcriterio)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        linearLayout = (LinearLayout) getView().findViewById(R.id.container_locales);

        View_Empresa_Life view_empresa_life = new View_Empresa_Life(getActivity(),empresa_dto);
        ((LinearLayout) getView().findViewById(R.id.framecabecerabusqueda)).addView(view_empresa_life);

        for (int i = 0; i < local_dtos.size(); i++) {
            Local_DTO local_dto = local_dtos.get(i);
            View_Local view_local = new View_Local(getActivity(), local_dto);
            view_local.setTag(local_dto);
            linearLayout.addView(view_local);
        }

        /*
        Button Cerrar
         */
        getView().findViewById(R.id.acb_img_cerrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(Fragment_Resultado_Busquedas_Life.this);
                trans.commit();
                manager.popBackStack();
            }
        });
        ((TextView)getView().findViewById(R.id.txtbusqueda)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
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

    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }
}
