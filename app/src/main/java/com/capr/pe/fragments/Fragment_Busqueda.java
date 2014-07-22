package com.capr.pe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.capr.pe.adapters.Adapter_Categoria;
import com.capr.pe.adapters.Adapter_Distrito;
import com.capr.pe.beans.Categoria_DTO;
import com.capr.pe.beans.Distrito_DTO;
import com.capr.pe.beans.Local_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Categorias;
import com.capr.pe.operation.Operation_Distritos;
import com.capr.pe.operation.Operation_Locales_Cercanos;
import com.capr.pe.views.View_Categoria;
import com.capr.pe.views.View_Distrito;
import com.capr.pe.views.View_Local;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Busqueda extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, Operation_Distritos.Interface_Operation_Distritos, Operation_Categorias.Interface_Operation_Categorias {

    private EditText edtcategorias;
    private EditText edtdistritos;

    private ListView listaCategorias;
    private ListView listaDistritos;

    private Operation_Distritos operation_distritos;
    private Operation_Categorias operation_categorias;

    private boolean flagcategoria = false;
    private boolean flagdistrito = false;

    public static final Fragment_Busqueda newInstance() {
        return new Fragment_Busqueda();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        operation_categorias = new Operation_Categorias(getActivity());
        operation_distritos = new Operation_Distritos(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_busquedas, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edtcategorias = (EditText) getView().findViewById(R.id.edt_buscar_categoria);
        edtdistritos = (EditText) getView().findViewById(R.id.edt_buscar_distrito);

        listaCategorias = (ListView) getView().findViewById(R.id.lista_categorias);
        listaDistritos = (ListView) getView().findViewById(R.id.lista_distritos);

        edtcategorias.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = edtcategorias.getText().toString().toLowerCase(Locale.getDefault());
                ((Adapter_Categoria) listaCategorias.getAdapter()).filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtdistritos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = edtdistritos.getText().toString().toLowerCase(Locale.getDefault());
                ((Adapter_Distrito) listaDistritos.getAdapter()).filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtcategorias.setOnFocusChangeListener(this);
        edtdistritos.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtcategorias, InputMethodManager.SHOW_IMPLICIT);

        if (v.equals(edtcategorias)) {
            if (hasFocus) {
                if(!flagcategoria){
                    operation_categorias.getCategoryes();
                    operation_categorias.setInterface_operation_categorias(this);
                }else{
                    listaCategorias.setVisibility(View.VISIBLE);
                }
            } else {
                listaCategorias.setVisibility(View.GONE);
            }
        } else if (v.equals(edtdistritos)) {
            if (hasFocus) {
                if(!flagdistrito){
                    operation_distritos.getDistritos();
                    operation_distritos.setInterface_operation_distritos(this);
                }else{

                    listaDistritos.setVisibility(View.VISIBLE);
                }
            } else {
                listaDistritos.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getCategoryes(boolean status, ArrayList<Categoria_DTO> categoria_dtos) {
        if (status) {
            Adapter_Categoria adapter_categoria = new Adapter_Categoria(getActivity(), categoria_dtos);
            listaCategorias.setAdapter(adapter_categoria);
            listaCategorias.setVisibility(View.VISIBLE);
            flagcategoria = true;
        }
    }

    @Override
    public void getDistritos(boolean status, ArrayList<Distrito_DTO> distrito_dtos) {
        if (status) {
            Adapter_Distrito adapter_categoria = new Adapter_Distrito(getActivity(), distrito_dtos);
            listaDistritos.setAdapter(adapter_categoria);
            listaDistritos.setVisibility(View.VISIBLE);
            flagdistrito = true;
        }
    }
}
