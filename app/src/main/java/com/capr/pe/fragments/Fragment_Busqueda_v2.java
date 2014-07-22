package com.capr.pe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.capr.pe.adapters.Adapter_Categoria;
import com.capr.pe.adapters.Adapter_Distrito;
import com.capr.pe.beans.Categoria_DTO;
import com.capr.pe.beans.Distrito_DTO;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Categorias;
import com.capr.pe.operation.Operation_Distritos;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Busqueda_v2 extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, Operation_Distritos.Interface_Operation_Distritos, Operation_Categorias.Interface_Operation_Categorias {

    private EditText edtcategorias;
    private EditText edtdistritos;

    public static final Fragment_Busqueda_v2 newInstance() {
        return new Fragment_Busqueda_v2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_busquedas_v2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        edtcategorias = (EditText) getView().findViewById(R.id.edt_buscar_categoria);
        edtdistritos = (EditText) getView().findViewById(R.id.edt_buscar_distrito);

        edtcategorias.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = edtcategorias.getText().toString().toLowerCase(Locale.getDefault());
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
        if (v.equals(edtcategorias)) {
            if (hasFocus) {

            } else {

            }
        } else if (v.equals(edtdistritos)) {
            if (hasFocus) {

            } else {

            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getCategoryes(boolean status, ArrayList<Categoria_DTO> categoria_dtos) {
        if (status) {

        }
    }

    @Override
    public void getDistritos(boolean status, ArrayList<Distrito_DTO> distrito_dtos) {
        if (status) {

        }
    }
}
