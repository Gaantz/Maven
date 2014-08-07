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
import com.capr.pe.operation.Operation_Registro;
import com.capr.pe.session.Session_Manager;
import com.capr.pe.util.Util_Fonts;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gantz on 25/07/14.
 */
public class Fragment_Registro extends Fragment {

    private ProgressDialog progressDialog;

    public static Fragment_Registro newInstance() {
        return new Fragment_Registro();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initStyles();

        getView().findViewById(R.id.btnregistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nombre = ((EditText) getView().findViewById(R.id.edtnombre)).getText().toString();
                String apellido = ((EditText) getView().findViewById(R.id.edtapellido)).getText().toString();
                String sexo = ((EditText) getView().findViewById(R.id.edtsexo)).getText().toString();
                String nacimiento = ((EditText) getView().findViewById(R.id.edtcumpleanios)).getText().toString();
                String email = ((EditText) getView().findViewById(R.id.edtmail)).getText().toString();
                String password = ((EditText) getView().findViewById(R.id.edtcontrasenia)).getText().toString();
                String nick = ((EditText) getView().findViewById(R.id.edtnick)).getText().toString();

                try {

                    JSONObject jsonObjectUsuario = new JSONObject();
                    jsonObjectUsuario.put("nombre", nombre);
                    jsonObjectUsuario.put("apellido", apellido);
                    jsonObjectUsuario.put("sexo", sexo);
                    jsonObjectUsuario.put("nacimiento", nacimiento);
                    jsonObjectUsuario.put("email", email);
                    jsonObjectUsuario.put("password", password);
                    jsonObjectUsuario.put("nick", nick);
                    jsonObjectUsuario.put("facebook_id", "");
                    jsonObjectUsuario.put("tipo_registro", "1");

                    progressDialog = ProgressDialog.show(getActivity(), null, "Registrando...", true);

                    Operation_Registro operation_registro = new Operation_Registro(getActivity());
                    operation_registro.setJsonObjectUsuario(jsonObjectUsuario);
                    operation_registro.registrarUsuario();
                    operation_registro.setInterface_operation_registro(new Operation_Registro.Interface_Operation_Registro() {
                        @Override
                        public void registrarUsuario(boolean state, Usuario_DTO usuario_dto, String mensaje) {
                            progressDialog.hide();
                            if (state) {
                                try {
                                    new Session_Manager(getActivity()).crearSession(usuario_dto);
                                    Toast.makeText(getActivity(), "Bienvenido", Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

                         /*
        Back Button Fragment
         */
        Fragment_Registro.this.getView().setFocusableInTouchMode(true);
        Fragment_Registro.this.getView().requestFocus();
        Fragment_Registro.this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(Fragment_Registro.this);
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
                trans.remove(Fragment_Registro.this);
                trans.commit();
                manager.popBackStack();
            }
        });
    }


    private void initStyles() {
        ((Button) getView().findViewById(R.id.btnregistrar)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));

        ((EditText) getView().findViewById(R.id.edtnombre)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((EditText) getView().findViewById(R.id.edtapellido)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((EditText) getView().findViewById(R.id.edtmail)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((EditText) getView().findViewById(R.id.edtcumpleanios)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((EditText) getView().findViewById(R.id.edtsexo)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((EditText) getView().findViewById(R.id.edtcontrasenia)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((EditText) getView().findViewById(R.id.edtnick)).setTypeface(Util_Fonts.setPNALight(getActivity()));
        ((EditText) getView().findViewById(R.id.edtrepcontrasenia)).setTypeface(Util_Fonts.setPNALight(getActivity()));

        ((TextView) getView().findViewById(R.id.fragment_registro_registro)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
    }
}
