package com.capr.pe.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capr.pe.beans.Cupon_DTO;
import com.capr.pe.fragments.Fragment_Detalle_Cupon;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.ws.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 5/07/14.
 */
public class View_Cupon extends RelativeLayout implements View.OnClickListener{

    private Cupon_DTO cupon_dto;
    private String nombre_local;

    public View_Cupon(Context context, Cupon_DTO cupon_dto) {
        super(context);
        this.cupon_dto = cupon_dto;
        initView();
    }

    public View_Cupon(Context context, AttributeSet attrs, Cupon_DTO cupon_dto) {
        super(context, attrs);
        this.cupon_dto = cupon_dto;
        initView();
    }

    public View_Cupon(Context context, AttributeSet attrs, int defStyle, Cupon_DTO cupon_dto) {
        super(context, attrs, defStyle);
        this.cupon_dto = cupon_dto;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.view_cupon, this, true);
        setTag(cupon_dto);
        setOnClickListener(this);

        try {
            JSONObject jsonObject = cupon_dto.getJsonObject();

            //Tipo de Beneficios
            String tipoBeneficio = jsonObject.getString("Beneficio");
            if(tipoBeneficio.equals("0")){
                setDetailCupon(R.color.color_blanco,jsonObject);
                ((LinearLayout)findViewById(R.id.card_tipo_cupon)).setBackgroundResource(R.drawable.holo_flat_button_azul_claro);
            }else if(tipoBeneficio.equals("1")){
                setDetailCupon(R.color.color_blanco,jsonObject);
                ((LinearLayout)findViewById(R.id.card_tipo_cupon)).setBackgroundResource(R.drawable.holo_flat_button_verde_claro);
            }

            Picasso.with(getContext()).load(jsonObject.getString("Foto")).centerCrop().fit().transform(new RoundedTransformation(65, 0)).into(((ImageView) findViewById(R.id.img_empresa_cupon)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDetailCupon(int color,JSONObject jsonObject) throws JSONException{
        String nombreCupon = jsonObject.getString("Nombre");
        TextView textViewName = (TextView) findViewById(R.id.txt_nombre_cupon);
        textViewName.setTextColor(getResources().getColor(color));
        if(nombreCupon.equals("0")){
            textViewName.setText("No disponible");
        }else{
            textViewName.setText(nombreCupon);
        }

        String descripcion = jsonObject.getString("Descripcion");
        TextView textViewDesc = (TextView) findViewById(R.id.txt_descripcion_cupon);
        textViewDesc.setTextColor(getResources().getColor(color));
        if(descripcion.equals("")){
            textViewDesc.setText("No disponible");
        }else{
            textViewDesc.setText(descripcion);
        }
    }

    @Override
    public void onClick(View v) {
        ((Maven)getContext()).getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Detalle_Cupon.newInstance((Cupon_DTO) getTag(),nombre_local),"frag_detalle_local").addToBackStack("a").commit();
    }

    public void setNombre_local(String nombre_local) {
        this.nombre_local = nombre_local;
    }
}
