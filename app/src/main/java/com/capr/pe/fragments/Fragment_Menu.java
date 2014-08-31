package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.session.Session_Manager;
import com.capr.pe.util.Util_Fonts;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Menu extends Fragment implements View.OnClickListener {

    public static final Fragment_Menu newInstance() {
        return new Fragment_Menu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_v2, container, false);

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

        LinearLayout optionpreguntas = (LinearLayout) getView().findViewById(R.id.option_preguntas);
        LinearLayout optionexplorar = (LinearLayout) getView().findViewById(R.id.option_explorar);
        LinearLayout optionperfil = (LinearLayout) getView().findViewById(R.id.option_perfil);
        LinearLayout optioncorporativo = (LinearLayout) getView().findViewById(R.id.option_corporativo);

        optionpreguntas.setOnClickListener(this);
        optionexplorar.setOnClickListener(this);
        optionperfil.setOnClickListener(this);
        optioncorporativo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((Maven)getActivity()).getSlidingMenu().setMode(SlidingMenu.LEFT);
        boolean session = new Session_Manager(getActivity()).isLogin();
        switch (v.getId()) {
            case R.id.option_preguntas:
                ((Maven) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Preguntas.newInstance(), "fragment_entrar").commit();
                break;
            case R.id.option_explorar:
                ((Maven) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Local.newInstance(), "fragment_explorar").commit();
                break;
            case R.id.option_perfil:
                if (session) {
                    ((Maven) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Perfil.newInstance(), "fragment_perfil").commit();
                } else {
                    ((Maven) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Entrar.newInstance(), "fragment_entrar").commit();
                }
                break;
            case R.id.option_corporativo:
                if (session) {
                    ((Maven) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Corporativo.newInstance(), "fragment_perfil").commit();
                } else {
                    ((Maven) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Entrar.newInstance(), "fragment_entrar").commit();
                }
                break;
        }
        ((Maven) getActivity()).sm_menu.toggle();
    }

    private void initStyles() {
        ((TextView) getView().findViewById(R.id.fragment_menu_maven)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.fragment_menu_preguntas)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.fragment_menu_explorar)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.fragment_menu_listas)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.fragment_menu_corporativo)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.fragment_menu_perfil)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
    }
}
