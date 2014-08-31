package com.capr.pe.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.capr.pe.beans.Local_DTO;
import com.capr.pe.beans.Usuario_DTO;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Logeo;
import com.capr.pe.operation.Operation_Registro;
import com.capr.pe.session.Session_Manager;
import com.capr.pe.util.Util_Fonts;
import com.facebook.LoginActivity;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Gantz on 25/07/14.
 */
public class Fragment_Entrar extends Fragment implements View.OnClickListener {

    private ProgressDialog progressDialog;

    public static Fragment_Entrar newInstance() {
        return new Fragment_Entrar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrar, container, false);

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

        Session session = Session.getActiveSession();
        if (session == null) {
            session = new Session(getActivity());
        }
        Session.setActiveSession(session);

        initStyles();

        getView().findViewById(R.id.btnlogeo).setOnClickListener(this);
        getView().findViewById(R.id.btnfacebook).setOnClickListener(this);
        getView().findViewById(R.id.txtregistro).setOnClickListener(this);

         /*
        Back Button Fragment
         */
        Fragment_Entrar.this.getView().setFocusableInTouchMode(true);
        Fragment_Entrar.this.getView().requestFocus();
        Fragment_Entrar.this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(Fragment_Entrar.this);
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
        getView().findViewById(R.id.imgmenucerrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((Maven)getActivity()).getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(Fragment_Entrar.this);
                trans.commit();
                manager.popBackStack();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnlogeo:
                ((Maven) getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Login.newInstance(), "fragment_login").addToBackStack("a").commit();
                break;

            case R.id.btnfacebook:
                loginFacebook();
                break;

            case R.id.txtregistro:
                ((Maven) getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Registro.newInstance(), "fragment_registro").addToBackStack("a").commit();
                break;
        }
    }

    private void initStyles() {
        ((Button) getView().findViewById(R.id.btnfacebook)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.txtregistro)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.fragment_central_project)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        ((TextView) getView().findViewById(R.id.fragment_entrar_titulo)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
    }

    private void loginFacebook() {
        progressDialog = ProgressDialog.show(getActivity(), null, "Comprobando...", true);
        List<String> permissions = Arrays.asList("email",
                                                 "public_profile",
                                                 "user_friends",
                                                 "user_about_me",
                                                 "user_birthday",
                                                 "user_relationships",
                                                 "user_location",
                                                 "user_likes");
        ParseFacebookUtils.logIn(permissions, getActivity(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.e("Facebook", err.getMessage());
                } else if (user.isNew()) {
                    makeMeRequest();
                } else {
                    makeMeRequest();
                }
            }
        });
    }

    private void makeMeRequest() {
        Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(GraphUser user, Response response) {
                        if (user != null) {

                            final String nombre = (String) user.getProperty("first_name");
                            String apellido = user.getLastName();
                            String sexo = (String) user.getProperty("gender");


                            String nacimiento = "00/00/00";
                            if (user.getBirthday() != null) {
                                nacimiento = user.getBirthday();
                            }

                            String email = (String) user.asMap().get("email");
                            String password = "";

                            String nick = "Maven";

                            if ((String) user.getProperty("middle_name") != null) {
                                nick = (String) user.getProperty("middle_name");
                            }

                            final String facebookId = user.getId();

                            try {

                                JSONObject jsonObjectUsuario = new JSONObject();
                                jsonObjectUsuario.put("nombre", nombre);
                                jsonObjectUsuario.put("apellido", apellido);
                                jsonObjectUsuario.put("sexo", sexo);
                                jsonObjectUsuario.put("nacimiento", nacimiento);
                                jsonObjectUsuario.put("email", email);
                                jsonObjectUsuario.put("password", password);
                                jsonObjectUsuario.put("nick", nick);
                                jsonObjectUsuario.put("facebook_id", facebookId);
                                jsonObjectUsuario.put("tipo_registro", "2");

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
                                            progressDialog = ProgressDialog.show(getActivity(), null, "Ingresando...", true);
                                            Operation_Logeo operation_logeo = new Operation_Logeo(getActivity());
                                            operation_logeo.logearUsuarioFacebook(facebookId);
                                            operation_logeo.setInterface_operation_logeo(new Operation_Logeo.Interface_Operation_Logeo() {
                                                @Override
                                                public void logearUsuario(boolean state, Usuario_DTO usuario_dto, String mensaje) {
                                                    progressDialog.hide();
                                                    try {
                                                        new Session_Manager(getActivity()).crearSession(usuario_dto);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Toast.makeText(getActivity(), "Bienvenido " + nombre, Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        request.executeAsync();
    }
}
