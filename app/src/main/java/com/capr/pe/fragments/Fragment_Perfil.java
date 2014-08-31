package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capr.pe.beans.Usuario_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.session.Session_Manager;
import com.capr.pe.util.Util_Fonts;

import org.json.JSONException;

/**
 * Created by Gantz on 31/07/14.
 */
public class Fragment_Perfil extends Fragment {

    public static final Fragment_Perfil newInstance() {
        return new Fragment_Perfil();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initStyles();

        Session_Manager session_manager = new Session_Manager(getActivity());
        try {
            Usuario_DTO usuario_dto = new Usuario_DTO();
            usuario_dto.setJsonObject(session_manager.getSession().getJsonObject());
            ((TextView) getView().findViewById(R.id.txtnombreusuario)).setText(usuario_dto.getJsonObject().getString("nombre"));
            ((TextView) getView().findViewById(R.id.txtsexousuario)).setText(usuario_dto.getJsonObject().getString("sexo"));
            ((TextView) getView().findViewById(R.id.txtcorreousuario)).setText(usuario_dto.getJsonObject().getString("Email"));
            ((TextView) getView().findViewById(R.id.txtcumpleaniosusuario)).setText(usuario_dto.getJsonObject().getString("nacimiento"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((TextView) getView().findViewById(R.id.cerrarsessionusuario)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Session_Manager(getActivity()).cerrarSession();
            }
        });

        getView().findViewById(R.id.imgopenmenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Maven) getActivity()).sm_menu.toggle();
            }
        });
    }

    private void initStyles() {
        ((TextView) getView().findViewById(R.id.fragment_perfil_perfil)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.txtnombreusuario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.txtsexousuario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.txtcorreousuario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.txtcumpleaniosusuario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.txtgeneralusuario)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.txtciudadusuario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.txtlogeofacebookusuario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.txtlogeonosotrosususario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.txtcompartirusuario)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.txtconfacebookusuario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.contwitterusuario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.txtseguridadusuario)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.cambiarcontraseñausuario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.cerrarsessionusuario)).setTypeface(Util_Fonts.setPNALight(getActivity()));
    }
}
