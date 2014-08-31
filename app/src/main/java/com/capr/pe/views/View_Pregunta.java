package com.capr.pe.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capr.pe.beans.Local_DTO;
import com.capr.pe.beans.Pregunta_DTO;
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
import org.w3c.dom.Text;

/**
 * Created by Gantz on 5/07/14.
 */
public class View_Pregunta extends RelativeLayout implements View.OnClickListener{

    private Pregunta_DTO pregunta_dto;

    public View_Pregunta(Context context, Pregunta_DTO pregunta_dto) {
        super(context);
        this.pregunta_dto = pregunta_dto;
        initView();
    }

    public View_Pregunta(Context context, AttributeSet attrs, Pregunta_DTO pregunta_dto) {
        super(context, attrs);
        this.pregunta_dto = pregunta_dto;
        initView();
    }

    public View_Pregunta(Context context, AttributeSet attrs, int defStyle, Pregunta_DTO pregunta_dto) {
        super(context, attrs, defStyle);
        this.pregunta_dto = pregunta_dto;
        initView();
    }

    private void initView(){
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.view_pregunta,this,true);

        try {
            JSONObject jsonObject = pregunta_dto.getJsonObject();

            TextView nombreusuario = (TextView)findViewById(R.id.nombreusuario);
            TextView tiempopregunta = (TextView)findViewById(R.id.tiempopregunta);
            TextView cantidadrespuesta = (TextView)findViewById(R.id.cantidadrespuesta);
            TextView pregunta = (TextView)findViewById(R.id.textopregunta);

            ImageView imagenusuario = (ImageView)findViewById(R.id.imgusuariopregunta);
            ImageView imagencategoriapregunta = (ImageView)findViewById(R.id.imgcategoriapregunta);

            int resourceid = Util_Categorias.getImageCateogry(Integer.parseInt(jsonObject.getString("idCategoria")));
            Picasso.with(getContext()).load(resourceid).centerCrop().fit().transform(new RoundedTransformation(65, 0)).into(imagencategoriapregunta);

            /*
            SET FONTS
             */
            nombreusuario.setTypeface(Util_Fonts.setPNALight(getContext()));
            tiempopregunta.setTypeface(Util_Fonts.setPNACursivaLight(getContext()));
            pregunta.setTypeface(Util_Fonts.setPNALight(getContext()));
            cantidadrespuesta.setTypeface(Util_Fonts.setPNACursivaLight(getContext()));

            nombreusuario.setText(jsonObject.getString("NombreUsuario"));
            tiempopregunta.setText("No hay en el WS");
            cantidadrespuesta.setText(jsonObject.getString("totalRespuestas") + " respuesta(s).");
            pregunta.setText(jsonObject.getString("pregunta"));

        }catch (JSONException e){
            e.printStackTrace();
        }

        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((Maven)getContext()).getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Detalle_Local.newInstance((Local_DTO) getTag()),"fragment_informacion").addToBackStack("a").commit();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
