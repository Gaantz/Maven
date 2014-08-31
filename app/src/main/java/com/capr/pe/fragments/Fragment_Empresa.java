package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Empresas;
import com.capr.pe.views.View_Empresa;
import com.capr.pe.views.View_Empresa_Life;

import java.util.ArrayList;

/**
 * Created by Gantz on 1/08/14.
 */
public class Fragment_Empresa extends Fragment {

    public static final Fragment_Empresa newInstance(){
        return new Fragment_Empresa();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empresas, container, false);

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

        final LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.frame_empresas);

        Operation_Empresas operation_empresas = new Operation_Empresas(getActivity());
        operation_empresas.getEmpresasLife();
        operation_empresas.setInterface_operation_empresas(new Operation_Empresas.Interface_Operation_Empresas() {
            @Override
            public void getEmpresasLife(boolean status, ArrayList<Empresa_DTO> empresa_dtos, String mensaje) {
                if(status){
                    for (int i = 0; i < empresa_dtos.size(); i++) {
                        View_Empresa view_empresa = new View_Empresa(getActivity(),empresa_dtos.get(i));
                        linearLayout.addView(view_empresa);
                    }
                }else{
                    Toast.makeText(getActivity(),mensaje,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
