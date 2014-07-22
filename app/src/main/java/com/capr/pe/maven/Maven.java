package com.capr.pe.maven;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.capr.pe.fragments.Fragment_Detalle_Local;
import com.capr.pe.fragments.Fragment_Local;
import com.capr.pe.fragments.Fragment_Menu;
import com.google.android.gms.location.LocationClient;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.lang.reflect.Field;
import java.util.List;


public class Maven extends SlidingActivity {

    private LocationClient locationClient;

    protected Fragment_Menu fragment_menu;
    protected Fragment_Local fragment_local;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.maven);
        setBehindContentView(R.layout.menu_frame);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, Fragment_Local.newInstance(),"fragment_locales").commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, Fragment_Menu.newInstance()).commit();
        } else {
            fragment_menu = (Fragment_Menu) this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
            fragment_local = (Fragment_Local) this.getSupportFragmentManager().findFragmentById(R.id.container);
        }

        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.navigation_drawer_width);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        hideSoftKeyboard();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount() > 0){
            fragmentManager.popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
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

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }
}
