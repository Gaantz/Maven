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
import com.capr.pe.maven.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gantz on 20/07/14.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class View_Categoria extends LinearLayout {

    private Categoria_DTO categoria_dto;

    public View_Categoria(Context context, AttributeSet attrs, int defStyle, Categoria_DTO categoria_dto) {
        super(context, attrs, defStyle);
        this.categoria_dto = categoria_dto;
        initView();
    }

    public View_Categoria(Context context, AttributeSet attrs, Categoria_DTO categoria_dto) {
        super(context, attrs);
        this.categoria_dto = categoria_dto;
        initView();
    }

    public View_Categoria(Context context, Categoria_DTO categoria_dto) {
        super(context);
        this.categoria_dto = categoria_dto;
        initView();
    }

    private void initView() {
        /*
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.item_categoria, this, true);

        try {

            JSONObject jsonObject = categoria_dto.getJsonObject();

            TextView txtnombrecategoria = (TextView) findViewById(R.id.txt_nombre_categoria_busqueda);
            TextView txtcantidadcategoria = (TextView) findViewById(R.id.txt_cantidad_categoria_busqueda);
            ImageView imgcategoria = (ImageView) findViewById(R.id.img_categoria_local);

            txtnombrecategoria.setText(jsonObject.getString("Nombre"));
            //txtcantidadcategoria.setText(jsonObject.getString("FechaCreacion"));
//            imgcategoria.setImageResource(getImageCategory(Integer.parseInt(jsonObject.getString("Id"))));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
    }

    private int getImageCategory(int id) {
        ArrayList<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.cat_entre);
        images.add(R.drawable.cat_comida);
        images.add(R.drawable.cat_bar);
        images.add(R.drawable.cat_ropa);
        images.add(R.drawable.cat_viajes);
        images.add(R.drawable.cat_desc);
        images.add(R.drawable.cat_market);
        images.add(R.drawable.cat_comida);
        images.add(R.drawable.cat_ropa);
        images.add(R.drawable.cat_desc);

        return images.get(id);
    }
}
