package com.capr.pe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.util.Util_Fonts;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

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
        return inflater.inflate(R.layout.fragment_corporativo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActionBar();
        initStyles();
    }

    private void initStyles() {
        ((TextView) getView().findViewById(R.id.fragment_corporativo_corporativo)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.txtmensajecorporativo)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
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