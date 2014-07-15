package com.capr.pe.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.capr.pe.beans.Menu_DTO;
import com.capr.pe.maven.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Gantz on 5/07/14.
 */
public class Adapter_Menu extends BaseAdapter {

    private Context context;
    private ArrayList<Menu_DTO> menu_dtos;
    private LayoutInflater inflater;

    public Adapter_Menu(Context context, ArrayList<Menu_DTO> menu_dtos) {
        this.context = context;
        this.menu_dtos = menu_dtos;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menu_dtos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return menu_dtos.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        Menu_DTO menu_dto = menu_dtos.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_menu, parent, false);
            holder = new Holder();

            holder.imagenMenu = (ImageView) convertView.findViewById(R.id.img_menu);
            holder.txtNombre = (TextView) convertView.findViewById(R.id.txt_menu);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        try {
            JSONObject jsonObject = menu_dto.getJsonObjectMenu();

            holder.txtNombre.setText(jsonObject.getString("titulo_menu"));
            holder.imagenMenu.setImageResource(jsonObject.getInt("imagen_id_menu"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class Holder {
        ImageView imagenMenu;
        TextView txtNombre;
    }
}
