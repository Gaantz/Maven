package com.capr.pe.dialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.capr.pe.adapters.Adapter_Categoria;
import com.capr.pe.adapters.Adapter_Categoria_Pregunta;
import com.capr.pe.beans.Categoria_DTO;
import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Categorias;
import com.capr.pe.util.Util_Fonts;

import java.util.ArrayList;

/**
 * Created by Gantz on 7/08/14.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Dialog_Categoria extends AlertDialog {


    public Dialog_Categoria(Context context, int theme) {
        super(context, theme);
        initDialog();
    }

    public Dialog_Categoria(Context context) {
        super(context);
        initDialog();
    }

    public Dialog_Categoria(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialog();
    }

    private void initDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.dialog_categoria, null);
        setView(view);

        final GridView grillacategorias= (GridView) view.findViewById(R.id.gridcategorias);

        Operation_Categorias operation_categorias = new Operation_Categorias(getContext());
        operation_categorias.getCategoryes();
        operation_categorias.setInterface_operation_categorias(new Operation_Categorias.Interface_Operation_Categorias() {
            @Override
            public void getCategoryes(boolean status, ArrayList<Categoria_DTO> categoria_dtos) {
                Adapter_Categoria_Pregunta adapter_categoria = new Adapter_Categoria_Pregunta(getContext(),categoria_dtos);
                grillacategorias.setAdapter(adapter_categoria);
            }
        });
    }
}
