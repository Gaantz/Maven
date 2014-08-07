package com.capr.pe.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.capr.pe.beans.Usuario_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Logeo;
import com.capr.pe.operation.Operation_Registro;
import com.capr.pe.session.Session_Manager;
import com.capr.pe.util.Util_Fonts;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 25/07/14.
 */
public class Fragment_Login extends Fragment {

    private ProgressDialog progressDialog;

    public static Fragment_Login newInstance() {
        return new Fragment_Login();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initStyles();

        getView().findViewById(R.id.btningresar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = ((EditText) getView().findViewById(R.id.edtmail)).getText().toString();
                String password = ((EditText) getView().findViewById(R.id.edtcontrasenia)).getText().toString();

                progressDialog = ProgressDialog.show(getActivity(), null, "Ingresando...", true);

                Operation_Logeo operation_logeo = new Operation_Logeo(getActivity());
                operation_logeo.logearUsuarioMaven(email,password);
                operation_logeo.setInterface_operation_logeo(new Operation_Logeo.Interface_Operation_Logeo() {
                    @Override
                    public void logearUsuario(boolean state, Usuario_DTO usuario_dto,String mensaje) {
                        progressDialog.hide();
                        if(state){
                            try {
                                new Session_Manager(getActivity()).crearSession(usuario_dto);
                                Toast.makeText(getActivity(), "Bienvenido", Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(getActivity(), mensaje ,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

                 /*
        Back Button Fragment
         */
        Fragment_Login.this.getView().setFocusableInTouchMode(true);
        Fragment_Login.this.getView().requestFocus();
        Fragment_Login.this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(Fragment_Login.this);
                    trans.commit();
                    manager.popBackStack();
                    return true;
                }
                return false;
            }
        });

        /*
        Button Cerrar
         */
        getView().findViewById(R.id.acb_img_cerrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(Fragment_Login.this);
                trans.commit();
                manager.popBackStack();
            }
        });
    }


    private void initStyles() {
        ((Button) getView().findViewById(R.id.btningresar)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((EditText) getView().findViewById(R.id.edtmail)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((EditText) getView().findViewById(R.id.edtcontrasenia)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((TextView) getView().findViewById(R.id.fragment_login_ingresa_con_tu_cuenta)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
    }

}
