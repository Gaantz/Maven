package com.capr.pe.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Categoria_DTO;
import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.maven.R;
import com.capr.pe.ws.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gantz on 20/07/14.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class View_Empresa extends LinearLayout {

    private Empresa_DTO empresa_dto;

    public View_Empresa(Context context, Empresa_DTO empresa_dto) {
        super(context);
        this.empresa_dto = empresa_dto;
        initView();
    }

    public View_Empresa(Context context, AttributeSet attrs, Empresa_DTO empresa_dto) {
        super(context, attrs);
        this.empresa_dto = empresa_dto;
        initView();
    }

    public View_Empresa(Context context, AttributeSet attrs, int defStyle, Empresa_DTO empresa_dto) {
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
            descipcionempresa.setText(jsonObject.getString("subtitle"));
            fondoempresa.setBackgroundColor(Color.parseColor("#"+jsonObject.getString("background_android")));

            String urlempresa = jsonObject.getString("Logo");
            if(urlempresa != null){
                //Picasso.with(getContext()).load(urlempresa).centerCrop().fit().transform(new RoundedTransformation(65, 0)).into(imagenempresa);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
