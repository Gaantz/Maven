package com.capr.pe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.capr.pe.beans.Local_DTO;
import com.capr.pe.maven.R;
import com.capr.pe.util.Util_Categorias;
import com.capr.pe.ws.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gantz on 22/07/14.
 */
public class Adapter_Local  {

    /*
    private ArrayList<Local_DTO> local_dtos;
    private LayoutInflater inflater;
    private Context context;

    public Adapter_Local(Context context, ArrayList<Local_DTO> local_dtos) {
        this.context = context;
        this.local_dtos = local_dtos;
    }

    @Override
    public int getCount() {
        return local_dtos.size();
    }

    @Override
    public Object getItem(int position) {
        return local_dtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        Local_DTO local_dto = local_dtos.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_locales_v2, parent, false);
            holder = new Holder();

            holder.txtnombrelocal = (TextView) convertView.findViewById(R.id.txt_nombre_local);
            holder.txtdireccionlocal = (TextView) convertView.findViewById(R.id.txt_direccion_local);
            holder.txtcategorialocal = (TextView) convertView.findViewById(R.id.txt_categoria_local);
            holder.txtdistantcialocal = (TextView) convertView.findViewById(R.id.txt_distancia_local);
            holder.imglocal = (ImageView) convertView.findViewById(R.id.img_categoria_local);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        try {
            JSONObject jsonObject = local_dto.getJsonObject();

            holder.txtnombrelocal.setText(jsonObject.getString("NombreLocal"));
            holder.txtdireccionlocal.setText(jsonObject.getString("Direccion"));
            holder.txtcategorialocal.setText(jsonObject.getString("NombreCategoria"));
            holder.txtdistantcialocal.setText(jsonObject.getString("Distancia"));

            int idresimagencategoria = Util_Categorias.getImageCateogry(Integer.parseInt(jsonObject.getString("idCategoria")));
            Picasso.with(context).load(idresimagencategoria).centerCrop().fit().transform(new RoundedTransformation(65, 0)).into(holder.imglocal);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    static class Holder {
        TextView txtnombrelocal;
        TextView txtdireccionlocal;
        TextView txtcategorialocal;
        TextView txtdistantcialocal;

        ImageView imglocal;
    }
    */
}
