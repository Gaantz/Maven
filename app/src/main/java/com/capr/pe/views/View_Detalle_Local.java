package com.capr.pe.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capr.pe.beans.Local_DTO;
import com.capr.pe.fragments.Fragment_Information;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.ws.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 5/07/14.
 */
public class View_Detalle_Local extends RelativeLayout {

    private Local_DTO local_dto;

    public View_Detalle_Local(Context context, Local_DTO local_dto) {
        super(context);
        this.local_dto = local_dto;
        initView();
    }

    public View_Detalle_Local(Context context, AttributeSet attrs, Local_DTO local_dto) {
        super(context, attrs);
        this.local_dto = local_dto;
        initView();
    }

    public View_Detalle_Local(Context context, AttributeSet attrs, int defStyle, Local_DTO local_dto) {
        super(context, attrs, defStyle);
        this.local_dto = local_dto;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.view_ubicacion, this, true);

        try {
            JSONObject jsonObject = local_dto.getJsonObject();

            ((TextView) findViewById(R.id.txt_direccion)).setText(jsonObject.getString("Direccion"));

            String telefono = jsonObject.getString("Telefono");
            if(telefono.equals("0")){
                ((TextView) findViewById(R.id.txt_telefono)).setText("No disponible");
            }else{
                ((TextView) findViewById(R.id.txt_telefono)).setText(telefono);
            }

            String horario = jsonObject.getString("Horario");
            if(horario.equals("")){
                ((TextView) findViewById(R.id.txt_horario)).setText("No disponible");
            }else{
                ((TextView) findViewById(R.id.txt_horario)).setText(horario);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
