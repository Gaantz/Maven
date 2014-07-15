package com.capr.pe.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capr.pe.beans.Local_DTO;
import com.capr.pe.fragments.Fragment_Information;
import com.capr.pe.fragments.Fragment_Local;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.ws.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 5/07/14.
 */
public class View_Local extends RelativeLayout implements View.OnClickListener{

    private Local_DTO local_dto;

    public View_Local(Context context, Local_DTO local_dto) {
        super(context);
        this.local_dto = local_dto;
        initView();
    }

    public View_Local(Context context, AttributeSet attrs, Local_DTO local_dto) {
        super(context, attrs);
        this.local_dto = local_dto;
        initView();
    }

    public View_Local(Context context, AttributeSet attrs, int defStyle, Local_DTO local_dto) {
        super(context, attrs, defStyle);
        this.local_dto = local_dto;
        initView();
    }

    private void initView(){
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_list_locales, this, true);

        try {
            JSONObject jsonObject = local_dto.getJsonObject();

            ((TextView)findViewById(R.id.txt_nombre_local)).setText(jsonObject.getString("NombreLocal"));
            ((TextView)findViewById(R.id.txt_direccion_local)).setText(jsonObject.getString("Direccion"));
            ((TextView)findViewById(R.id.txt_categoria_local)).setText(jsonObject.getString("NombreCategoria"));

            double distance = round(Double.parseDouble(jsonObject.getString("Distancia")), 2);
            ((TextView)findViewById(R.id.txt_distancia_local)).setText(String.valueOf(distance) + " mts.");

            Log.e("LogoEmpresa",jsonObject.getString("LogoEmpresa"));
            //Picasso.with(getContext()).load(jsonObject.getString("LogoEmpresa")).centerCrop().fit().transform(new RoundedTransformation(65, 0)).into(((ImageView) findViewById(R.id.img_categoria_local)));

        }catch (JSONException e){
            e.printStackTrace();
        }

        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((Maven)getContext()).getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Information.newInstance((Local_DTO)getTag()),"frag_detalle_local").addToBackStack("a").commit();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
