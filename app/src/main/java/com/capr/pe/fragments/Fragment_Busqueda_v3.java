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
public class Fragment_Busqueda_v3 extends Fragment {

    private EditText edtcategorias;
    private EditText edtdistritos;

    public static final Fragment_Busqueda_v3 newInstance() {
        return new Fragment_Busqueda_v3();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_busquedas_v3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
