package com.capr.pe.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.beans.Categoria_DTO;
import com.capr.pe.beans.Distrito_DTO;
import com.capr.pe.maven.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gantz on 20/07/14.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class View_Distrito extends LinearLayout {

    private Distrito_DTO distrito_dto;

    public View_Distrito(Context context, AttributeSet attrs, int defStyle, Distrito_DTO distrito_dto) {
        super(context, attrs, defStyle);
        this.distrito_dto = distrito_dto;
        initView();
    }

    public View_Distrito(Context context, AttributeSet attrs, Distrito_DTO distrito_dto) {
        super(context, attrs);
        this.distrito_dto = distrito_dto;
        initView();
    }

    public View_Distrito(Context context, Distrito_DTO distrito_dto) {
        super(context);
        this.distrito_dto = distrito_dto;
        initView();
    }

    private void initView() {
        /*
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_distrito, this, true);

        try {

            JSONObject jsonObject = distrito_dto.getJsonObject();

            TextView txtnombredistirto = (TextView) findViewById(R.id.txt_distrito_busquedas);

            txtnombredistirto.setText(jsonObject.getString("c_name_ubigeo"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
    }
}
