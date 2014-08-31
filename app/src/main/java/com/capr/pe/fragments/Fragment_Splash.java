package com.capr.pe.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.database.Database_Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Empresas;
import com.capr.pe.session.Session_Manager;
import com.capr.pe.util.ObjectSerializer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Gantz on 28/08/14.
 */
public class Fragment_Splash extends Fragment {

    protected ArrayList<String> strings = new ArrayList<String>();

    public static final Fragment_Splash newInstance() {
        return new Fragment_Splash();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (new Session_Manager(getActivity()).isLogin()) {
            final Database_Maven database_maven = new Database_Maven(getActivity());
            Operation_Empresas operation_empresas = new Operation_Empresas(getActivity());
            operation_empresas.getMisEmpresasLife();
            operation_empresas.setInterface_operation_mis_empresas(new Operation_Empresas.Interface_Operation_Mis_Empresas() {
                @Override
                public void getMisEmpresasLife(boolean status, ArrayList<Empresa_DTO> empresa_dtos, String mensaje) {
                    if (status) {
                        for (int i = 0; i < empresa_dtos.size(); i++) {
                            try {
                                Empresa_DTO empresa_dto = new Empresa_DTO();
                                empresa_dto.set_id(i);
                                empresa_dto.setEmpresaId(empresa_dtos.get(i).getJsonObject().getString("EmpresaId"));
                                database_maven.addEmpresaId(empresa_dto);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i < database_maven.getAllEmpresaIds().size(); i++) {
                            Log.e("EMPRESAS USUSARIO", database_maven.getAllEmpresaIds().get(i).getEmpresaId());
                        }
                    }
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Local.newInstance(), "fragment_locales").commit();
                }
            });
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Local.newInstance(), "fragment_locales").commit();
                }
            }, 1500);
        }

    }

    public void addEmpresaId(String id) {
        strings.add(id);
        SharedPreferences prefs = getActivity().getSharedPreferences("corporativo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            editor.putString("empresa_ids", ObjectSerializer.serialize(strings));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();
    }
}
