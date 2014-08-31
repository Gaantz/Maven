package com.capr.pe.maven;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.capr.pe.fragments.Fragment_Empresa;
import com.capr.pe.fragments.Fragment_Local;
import com.capr.pe.fragments.Fragment_Menu;
import com.capr.pe.fragments.Fragment_Splash;
import com.capr.pe.session.Session_Manager;
import com.capr.pe.util.ObjectSerializer;
import com.google.android.gms.location.LocationClient;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.parse.ParseFacebookUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Maven extends SlidingActivity {

    public SlidingMenu sm_menu;
    public SlidingMenu sm_empresas;

    private LocationClient locationClient;

    protected Fragment_Menu fragment_menu;
    //  protected Fragment_Local fragment_local;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
        setContentView(R.layout.maven);
        setBehindContentView(R.layout.menu_frame);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Splash.newInstance(), "fragment_locales").commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, Fragment_Menu.newInstance()).commit();

        sm_menu = getSlidingMenu();
        sm_menu.setShadowWidthRes(R.dimen.navigation_drawer_width);
        sm_menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm_menu.setFadeDegree(0.35f);
        sm_menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        hideSoftKeyboard();

        sm_empresas = getSlidingMenu();
        sm_empresas.setSecondaryMenu(R.layout.fragment_empresas);
        sm_empresas.setSecondaryShadowDrawable(R.drawable.shadowright);
        sm_empresas.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm_empresas.setMode(SlidingMenu.LEFT);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }
}
