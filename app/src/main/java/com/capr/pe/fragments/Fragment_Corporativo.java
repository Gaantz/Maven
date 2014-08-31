package com.capr.pe.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Empresas;
import com.capr.pe.util.ObjectSerializer;
import com.capr.pe.util.Util_Fonts;
import com.capr.pe.views.View_Empresa;
import com.capr.pe.views.View_Empresa_Life;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gantz on 31/07/14.
 */
public class Fragment_Corporativo extends Fragment implements View.OnClickListener {

    public static final Fragment_Corporativo newInstance() {
        return new Fragment_Corporativo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_corporativo, container, false);

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
        initActionBar();
        initStyles();

        final LinearLayout frameempty = (LinearLayout) getView().findViewById(R.id.frameempy);
        final LinearLayout frameempresas = (LinearLayout) getView().findViewById(R.id.frameempresas);
        final LinearLayout framegeneral = (LinearLayout) getView().findViewById(R.id.frameempresageneral);

        Operation_Empresas operation_empresas = new Operation_Empresas(getActivity());
        operation_empresas.getMisEmpresasLife();
        operation_empresas.setInterface_operation_mis_empresas(new Operation_Empresas.Interface_Operation_Mis_Empresas() {
            @Override
            public void getMisEmpresasLife(boolean status, ArrayList<Empresa_DTO> empresa_dtos, String mensaje) {
                if (status) {
                    frameempty.setVisibility(View.GONE);
                    frameempresas.setVisibility(View.VISIBLE);
                    framegeneral.setBackground(getActivity().getResources().getDrawable(R.drawable.fondo_maven));

                    for (int i = 0; i < empresa_dtos.size(); i++) {
                        View_Empresa_Life view_empresa = new View_Empresa_Life(getActivity(), empresa_dtos.get(i));
                        view_empresa.isClickable(true);
                        frameempresas.addView(view_empresa);
                    }
                } else {
                    frameempty.setVisibility(View.VISIBLE);
                    frameempresas.setVisibility(View.GONE);

                    framegeneral.setBackgroundColor(getActivity().getResources().getColor(R.color.verde_maven));
                }
            }
        });
    }

    private void initStyles() {
        ((TextView) getView().findViewById(R.id.fragment_corporativo_corporativo)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.txtmensajecorporativo)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));

        final LinearLayout frameempty = (LinearLayout) getView().findViewById(R.id.frameempy);
        final LinearLayout framegeneral = (LinearLayout) getView().findViewById(R.id.frameempresageneral);
        frameempty.setVisibility(View.VISIBLE);
        framegeneral.setBackgroundColor(getActivity().getResources().getColor(R.color.verde_maven));
    }

    private void initActionBar() {
        getView().findViewById(R.id.imgopenmenu).setOnClickListener(this);
        getView().findViewById(R.id.imgmasmenu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgopenmenu:
                ((Maven)getActivity()).sm_menu.toggle();
                break;
            case R.id.imgmasmenu:
                ((Maven)getActivity()).getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
                ((Maven)getActivity()).getSlidingMenu().showSecondaryMenu(true);
                ((Maven)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frame_empresas, Fragment_Empresa.newInstance()).commit();
                break;
        }
    }
}