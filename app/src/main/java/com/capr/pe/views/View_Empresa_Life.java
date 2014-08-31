package com.capr.pe.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.database.Database_Maven;
import com.capr.pe.dialog.Dialog_Empresa;
import com.capr.pe.dialog.Dialog_Life;
import com.capr.pe.fragments.Fragment_Corporativo;
import com.capr.pe.fragments.Fragment_Local_Life;
import com.capr.pe.image.ImageLoader;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Empresas;
import com.capr.pe.util.Util_Fonts;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 20/07/14.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class View_Empresa_Life extends LinearLayout implements View.OnClickListener, View.OnLongClickListener,Dialog_Empresa.Interface_Dialog_Empresa  {

    private Empresa_DTO empresa_dto;

    public View_Empresa_Life(Context context, Empresa_DTO empresa_dto) {
        super(context);
        this.empresa_dto = empresa_dto;
        initView();
    }

    public View_Empresa_Life(Context context, AttributeSet attrs, Empresa_DTO empresa_dto) {
        super(context, attrs);
        this.empresa_dto = empresa_dto;
        initView();
    }

    public View_Empresa_Life(Context context, AttributeSet attrs, int defStyle, Empresa_DTO empresa_dto) {
        super(context, attrs, defStyle);
        this.empresa_dto = empresa_dto;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_empresa, this, true);

        try {

            JSONObject jsonObject = empresa_dto.getJsonObject();

            TextView nombreempresa = (TextView) findViewById(R.id.nombreempresa);
            TextView descipcionempresa = (TextView) findViewById(R.id.descripcionempresa);
            ImageView imagenempresa = (ImageView) findViewById(R.id.imgempresa);
            LinearLayout fondoempresa = (LinearLayout) findViewById(R.id.itemfondoempresa);

            nombreempresa.setText(jsonObject.getString("Nombre"));
            descipcionempresa.setText("No existe");
            //descipcionempresa.setText(jsonObject.getString("subtitle"));
            fondoempresa.setBackgroundColor(Color.parseColor("#" + jsonObject.getString("background_android")));

            String urlempresa = jsonObject.getString("Logo");
            if(urlempresa!=null){
                ImageLoader.getInstance(getContext()).displayImage(urlempresa,imagenempresa);
            }


            nombreempresa.setTypeface(Util_Fonts.setPNASemiBold(getContext()));
            descipcionempresa.setTypeface(Util_Fonts.setPNALight(getContext()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        ((Maven) getContext()).getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Local_Life.newInstance(empresa_dto), "fragment_informacion").addToBackStack("a").commit();
    }

    @Override
    public boolean onLongClick(View v) {
        Dialog_Empresa dialog_empresa = new Dialog_Empresa(getContext());
        dialog_empresa.setInterface_dialog_empresa(View_Empresa_Life.this);
        dialog_empresa.show();
        return true;
    }

    public void isClickable(boolean clickable) {
        if(clickable){
            setOnClickListener(this);
            setOnLongClickListener(this);
        }
    }

    @Override
    public void onAcepted(Dialog_Empresa dialog_cupon) {
        dialog_cupon.hide();
        try {
            final String empresaId = empresa_dto.getJsonObject().getString("EmpresaId");
            Operation_Empresas operation_empresas = new Operation_Empresas(getContext());
            operation_empresas.removeEmpresaLife(empresaId);
            operation_empresas.setInterface_operation_eliminar_empresa(new Operation_Empresas.Interface_Operation_Eliminar_Empresa() {
                @Override
                public void removeEmpresaLife(String mensaje) {
                    Database_Maven database_maven = new Database_Maven(getContext());
                    database_maven.deleteEmpresaId(empresaId);

                    Toast.makeText(getContext(),mensaje,Toast.LENGTH_SHORT).show();
                    View_Empresa_Life.this.setVisibility(GONE);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
