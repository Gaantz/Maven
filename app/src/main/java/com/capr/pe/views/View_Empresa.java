package com.capr.pe.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Categoria_DTO;
import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.dialog.Dialog_Life;
import com.capr.pe.fragments.Fragment_Corporativo;
import com.capr.pe.image.ImageLoader;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.util.Util_Fonts;
import com.capr.pe.ws.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gantz on 20/07/14.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class View_Empresa extends LinearLayout implements View.OnClickListener, Dialog_Life.Interface_Dialog_Life {

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
        setOnClickListener(this);

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
            if (urlempresa != null) {
                ImageLoader.getInstance(getContext()).displayImage(urlempresa, imagenempresa);
            }

            nombreempresa.setTypeface(Util_Fonts.setPNASemiBold(getContext()));
            descipcionempresa.setTypeface(Util_Fonts.setPNALight(getContext()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Dialog_Life dialog_life = new Dialog_Life(getContext(), empresa_dto);
        dialog_life.setInterface_dialog_life(this);
        dialog_life.show();
    }

    @Override
    public void updateView(boolean state) {
        if (state) {
            ((Maven) getContext()).sm_menu.toggle();
            ((Maven) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Corporativo.newInstance(), "").commit();
        }
    }
}
