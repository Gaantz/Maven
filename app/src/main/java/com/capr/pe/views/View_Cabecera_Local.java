package com.capr.pe.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capr.pe.beans.Local_DTO;
import com.capr.pe.fragments.Fragment_Detalle_Local;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.util.Util_Categorias;
import com.capr.pe.util.Util_Fonts;
import com.capr.pe.ws.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 5/07/14.
 */
public class View_Cabecera_Local extends RelativeLayout {

    private Local_DTO local_dto;

    public View_Cabecera_Local(Context context, Local_DTO local_dto) {
        super(context);
        this.local_dto = local_dto;
        initView();
    }

    public View_Cabecera_Local(Context context, AttributeSet attrs, Local_DTO local_dto) {
        super(context, attrs);
        this.local_dto = local_dto;
        initView();
    }

    public View_Cabecera_Local(Context context, AttributeSet attrs, int defStyle, Local_DTO local_dto) {
        super(context, attrs, defStyle);
        this.local_dto = local_dto;
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_list_locales, this, true);

        try {
            JSONObject jsonObject = local_dto.getJsonObject();

            ((TextView) findViewById(R.id.txt_nombre_local)).setText(jsonObject.getString("NombreLocal"));
            ((TextView) findViewById(R.id.txt_direccion_local)).setText(jsonObject.getString("Direccion"));
            ((TextView) findViewById(R.id.txt_categoria_local)).setText(jsonObject.getString("NombreCategoria"));

            if(jsonObject.isNull("Distancia")){
                findViewById(R.id.txt_distancia_local).setVisibility(GONE);
            }else{
                double distance = round(Double.parseDouble(jsonObject.getString("Distancia")), 2);
                ((TextView) findViewById(R.id.txt_distancia_local)).setText(String.valueOf(distance) + " mts.");
            }

            /*
            SET FONTS
             */
            ((TextView)findViewById(R.id.txt_nombre_local)).setTypeface(Util_Fonts.setPNASemiBold(getContext()));
            ((TextView)findViewById(R.id.txt_direccion_local)).setTypeface(Util_Fonts.setPNALight(getContext()));
            ((TextView)findViewById(R.id.txt_categoria_local)).setTypeface(Util_Fonts.setPNACursivaLight(getContext()));
            ((TextView)findViewById(R.id.txt_distancia_local)).setTypeface(Util_Fonts.setPNALight(getContext()));

            Picasso.with(getContext()).load(Util_Categorias.getImageCateogry(Integer.parseInt(jsonObject.getString("idCategoria")))).centerCrop().fit().transform(new RoundedTransformation(65, 0)).into(((ImageView) findViewById(R.id.img_categoria_local)));

            String logoempresa = jsonObject.getString("LogoEmpresa");
            if (!logoempresa.equals("")) {
                //Picasso.with(getContext()).load(logoempresa).centerCrop().fit().transform(new RoundedTransformation(65, 0)).into(((ImageView) findViewById(R.id.img_categoria_local)));
            }

            JSONArray jsonArray = jsonObject.getJSONArray("ListaCupones");
            for (int i = 0; i < jsonArray.length(); i++) {
                String beneficio = jsonArray.getJSONObject(i).getString("Beneficio");
                if (beneficio.equals("0")) {
                    //((ImageView) findViewById(R.id.img_cupon_verde)).setVisibility(View.VISIBLE);
                } else if (beneficio.equals("1")) {
                    //((ImageView) findViewById(R.id.img_cupon_azul)).setVisibility(View.VISIBLE);
                } else if (beneficio.equals("2")) {
                    //((ImageView) findViewById(R.id.img_cupon_plomo)).setVisibility(View.VISIBLE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
