package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.View;

import com.capr.pe.adapters.Adapter_Menu;
import com.capr.pe.beans.Menu_DTO;
import com.capr.pe.maven.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Menu extends ListFragment {

    private Adapter_Menu adapter_menu;

    public static final Fragment_Menu newInstance() {
        return new Fragment_Menu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Menu_DTO> menu_dtos = getMenuItems();
        adapter_menu = new Adapter_Menu(getActivity(),menu_dtos);
        setListAdapter(adapter_menu);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setDivider(null);
        getListView().setDividerHeight(0);
    }

    private ArrayList<Menu_DTO> getMenuItems() {
        ArrayList<Menu_DTO> menu_dtos = new ArrayList<Menu_DTO>();

        try {

            final JSONObject jsonObjectPregunta = new JSONObject();
            jsonObjectPregunta.put("titulo_menu","Preguntas");
            jsonObjectPregunta.put("imagen_id_menu",R.drawable.abc_ic_clear);

            final JSONObject jsonObjectExplorar = new JSONObject();
            jsonObjectExplorar.put("titulo_menu","Explorar");
            jsonObjectExplorar.put("imagen_id_menu",R.drawable.abc_ic_clear);

            final JSONObject jsonObjectListas = new JSONObject();
            jsonObjectListas.put("titulo_menu","Listas");
            jsonObjectListas.put("imagen_id_menu",R.drawable.abc_ic_clear);

            final JSONObject jsonObjectCorp = new JSONObject();
            jsonObjectCorp.put("titulo_menu","Corporativo");
            jsonObjectCorp.put("imagen_id_menu",R.drawable.abc_ic_clear);

            final JSONObject jsonObjectPerfil = new JSONObject();
            jsonObjectPerfil.put("titulo_menu","Perfil");
            jsonObjectPerfil.put("imagen_id_menu",R.drawable.abc_ic_clear);


            Menu_DTO menu_dtoPregunta = new Menu_DTO(jsonObjectPregunta);
            Menu_DTO menu_dtoExplorar = new Menu_DTO(jsonObjectExplorar);
            Menu_DTO menu_dtoListas = new Menu_DTO(jsonObjectListas);
            Menu_DTO menu_dtoCorp = new Menu_DTO(jsonObjectCorp);
            Menu_DTO menu_dtoPerfil = new Menu_DTO(jsonObjectPerfil);

            menu_dtos.add(menu_dtoPregunta);
            menu_dtos.add(menu_dtoExplorar);
            menu_dtos.add(menu_dtoListas);
            menu_dtos.add(menu_dtoCorp);
            menu_dtos.add(menu_dtoPerfil);

        }catch (JSONException e){
            e.printStackTrace();
        }

        return menu_dtos;
    }
}
