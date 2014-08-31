package com.capr.pe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.capr.pe.adapters.Adapter_Categoria;
import com.capr.pe.adapters.Adapter_Distrito;
import com.capr.pe.beans.Categoria_DTO;
import com.capr.pe.beans.Distrito_DTO;
import com.capr.pe.beans.Empresa_DTO;
import com.capr.pe.beans.Local_DTO;
import com.capr.pe.dialog.Dialog_Maven;
import com.capr.pe.maven.Maven;
import com.capr.pe.maven.R;
import com.capr.pe.operation.Operation_Busquedas;
import com.capr.pe.operation.Operation_Categorias;
import com.capr.pe.operation.Operation_Distritos;
import com.capr.pe.operation.Operation_Locales_Cercanos;
import com.capr.pe.util.Util_Fonts;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Gantz on 5/07/14.
 */
public class Fragment_Busqueda_Life extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, Operation_Distritos.Interface_Operation_Distritos, Operation_Categorias.Interface_Operation_Categorias {

    private EditText edtcategorias;
    private EditText edtdistritos;

    private ListView listaCategorias;
    private ListView listaDistritos;

    private Operation_Distritos operation_distritos;
    private Operation_Categorias operation_categorias;

    private boolean flagcategoria = false;
    private boolean flagdistrito = false;

    protected String s = "";
    protected String distrito = "";
    protected String otro = "";
    protected String empresaId = "";

    private Empresa_DTO empresa_dto;

    public static final Fragment_Busqueda_Life newInstance(Empresa_DTO empresa_dto) {
        Fragment_Busqueda_Life fragment_busqueda_life = new Fragment_Busqueda_Life();
        Bundle bundle = new Bundle();
        bundle.putSerializable("empresa_dto", empresa_dto);
        fragment_busqueda_life.setArguments(bundle);
        return fragment_busqueda_life;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        empresa_dto = (Empresa_DTO) getArguments().getSerializable("empresa_dto");

        operation_categorias = new Operation_Categorias(getActivity());
        operation_distritos = new Operation_Distritos(getActivity());

        operation_categorias.getCategoryes();
        operation_categorias.setInterface_operation_categorias(this);
        flagcategoria = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busquedas, container, false);
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

        edtdistritos = (EditText) getView().findViewById(R.id.edt_buscar_distrito);
        edtcategorias = (EditText) getView().findViewById(R.id.edt_buscar_categorias);

        /*
        SET FONTS
         */
        edtcategorias.setTypeface(Util_Fonts.setPNALight(getActivity()));
        edtdistritos.setTypeface(Util_Fonts.setPNALight(getActivity()));

        listaCategorias = (ListView) getView().findViewById(R.id.lista_categorias);
        listaDistritos = (ListView) getView().findViewById(R.id.lista_distritos);

        listaDistritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Distrito_DTO distrito_dto = (Distrito_DTO) listaDistritos.getAdapter().getItem(position);
                String distrito = distrito_dto.getNombredistrito();
                edtdistritos.setText(distrito);
            }
        });
        listaCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria_DTO categoria_dto = (Categoria_DTO) listaCategorias.getAdapter().getItem(position);
                String categoria = categoria_dto.getNombrecategoria();
                edtcategorias.setText(categoria);
            }
        });

        edtcategorias.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = edtcategorias.getText().toString().toLowerCase(Locale.getDefault());
                ((Adapter_Categoria) listaCategorias.getAdapter()).filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtdistritos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = edtdistritos.getText().toString().toLowerCase(Locale.getDefault());
                ((Adapter_Distrito) listaDistritos.getAdapter()).filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtcategorias.setOnFocusChangeListener(this);
        edtdistritos.setOnFocusChangeListener(this);

        ((TextView) getView().findViewById(R.id.btnbuscar)).setTypeface(Util_Fonts.setPNASemiBold(getActivity()));
        getView().findViewById(R.id.btnbuscar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtcategorias.getText().toString().matches("") && edtdistritos.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Ingrese al menos un criterio de busqueda...!", Toast.LENGTH_SHORT).show();
                } else {
                    final Dialog_Maven dialog_maven = new Dialog_Maven(getActivity());
                    dialog_maven.show();

                    s = edtcategorias.getText().toString();
                    distrito = edtdistritos.getText().toString();

                    try {
                        empresaId = empresa_dto.getJsonObject().getString("Id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (!s.matches("") && !distrito.matches("")) {
                        if (distrito.equals("Mi ubicación")) {
                            if (isCategory(s)) {
                                if (isDescuentoOrBeneficio(s)) {
                                    Operation_Busquedas operation_busquedas = new Operation_Busquedas(getActivity());
                                    operation_busquedas.buscarLocalesCercanosLife("", "", distrito,empresaId);
                                    operation_busquedas.setInterface_operation_busquedas(new Operation_Busquedas.Interface_Operation_Busquedas() {
                                        @Override
                                        public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                                            if (status) {
                                                dialog_maven.hide();
                                                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, s + " - " + distrito,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                            }
                                        }
                                    });
                                } else {
                                    Operation_Busquedas operation_busquedas = new Operation_Busquedas(getActivity());
                                    operation_busquedas.buscarLocalesCercanosLife("", s, distrito,empresaId);
                                    operation_busquedas.setInterface_operation_busquedas(new Operation_Busquedas.Interface_Operation_Busquedas() {
                                        @Override
                                        public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                                            if (status) {
                                                dialog_maven.hide();
                                                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, s + " - " + distrito,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                            }
                                        }
                                    });
                                }
                            } else {
                                Operation_Busquedas operation_busquedas = new Operation_Busquedas(getActivity());
                                operation_busquedas.buscarLocalesCercanosLife(s, "", distrito,empresaId);
                                operation_busquedas.setInterface_operation_busquedas(new Operation_Busquedas.Interface_Operation_Busquedas() {
                                    @Override
                                    public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                                        if (status) {
                                            dialog_maven.hide();
                                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, s + " - " + distrito,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                        }
                                    }
                                });
                            }
                        } else {
                            if (isCategory(s)) {
                                if (isDescuentoOrBeneficio(s)) {
                                    Operation_Busquedas operation_busquedas = new Operation_Busquedas(getActivity());
                                    operation_busquedas.buscarLocalesLife("", "", distrito,empresaId);
                                    operation_busquedas.setInterface_operation_busquedas(new Operation_Busquedas.Interface_Operation_Busquedas() {
                                        @Override
                                        public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                                            if (status) {
                                                dialog_maven.hide();
                                                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, s + " - " + distrito,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                            }
                                        }
                                    });
                                } else {
                                    Operation_Busquedas operation_busquedas = new Operation_Busquedas(getActivity());
                                    operation_busquedas.buscarLocalesLife("", s, distrito,empresaId);
                                    operation_busquedas.setInterface_operation_busquedas(new Operation_Busquedas.Interface_Operation_Busquedas() {
                                        @Override
                                        public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                                            if (status) {
                                                dialog_maven.hide();
                                                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, s + " - " + distrito,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                            }
                                        }
                                    });
                                }
                            } else {
                                Operation_Busquedas operation_busquedas = new Operation_Busquedas(getActivity());
                                operation_busquedas.buscarLocalesLife(s, "", distrito,empresaId);
                                operation_busquedas.setInterface_operation_busquedas(new Operation_Busquedas.Interface_Operation_Busquedas() {
                                    @Override
                                    public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                                        if (status) {
                                            dialog_maven.hide();
                                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, s + " - " + distrito,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                        }
                                    }
                                });
                            }
                        }
                    } else {
                        if (!s.matches("")) {
                            if (isCategory(s)) {
                                if (isDescuentoOrBeneficio(s)) {
                                    Operation_Busquedas operation_busquedas = new Operation_Busquedas(getActivity());
                                    operation_busquedas.buscarLocalesLife("", "", "",empresaId);
                                    operation_busquedas.setInterface_operation_busquedas(new Operation_Busquedas.Interface_Operation_Busquedas() {
                                        @Override
                                        public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                                            if (status) {
                                                dialog_maven.hide();
                                                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, s,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                            }
                                        }
                                    });
                                } else {
                                    Operation_Busquedas operation_busquedas = new Operation_Busquedas(getActivity());
                                    operation_busquedas.buscarLocalesLife("", s, "",empresaId);
                                    operation_busquedas.setInterface_operation_busquedas(new Operation_Busquedas.Interface_Operation_Busquedas() {
                                        @Override
                                        public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                                            if (status) {
                                                dialog_maven.hide();
                                                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, s,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                            }
                                        }
                                    });
                                }
                            } else {
                                Operation_Busquedas operation_busquedas = new Operation_Busquedas(getActivity());
                                operation_busquedas.buscarLocalesLife(s, "", "",empresaId);
                                operation_busquedas.setInterface_operation_busquedas(new Operation_Busquedas.Interface_Operation_Busquedas() {
                                    @Override
                                    public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                                        if (status) {
                                            dialog_maven.hide();
                                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, s,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                        }
                                    }
                                });
                            }
                        } else {
                            if (distrito.equals("Mi ubicación")) {
                                Operation_Locales_Cercanos operation_locales_cercanos = new Operation_Locales_Cercanos(getActivity());
                                operation_locales_cercanos.getLocalesCercanos();
                                operation_locales_cercanos.setInterface_operation_locales_cercanos(new Operation_Locales_Cercanos.Interface_Operation_Locales_Cercanos() {
                                    @Override
                                    public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages) {
                                        if (status) {
                                            dialog_maven.hide();
                                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, distrito,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                        }
                                    }
                                });
                            } else {
                                Operation_Busquedas operation_busquedas = new Operation_Busquedas(getActivity());
                                operation_busquedas.buscarLocalesLife("", "", distrito,empresaId);
                                operation_busquedas.setInterface_operation_busquedas(new Operation_Busquedas.Interface_Operation_Busquedas() {
                                    @Override
                                    public void getLocalesCercanos(boolean status, ArrayList<Local_DTO> local_dtos, int pages, String mensaje) {
                                        if (status) {
                                            dialog_maven.hide();
                                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, Fragment_Resultado_Busquedas_Life.newInstance(local_dtos, distrito,empresa_dto), "fragment_busqueda").addToBackStack("a").commit();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });

        /*
        Back Button Fragment
         */
        Fragment_Busqueda_Life.this.getView().setFocusableInTouchMode(true);
        Fragment_Busqueda_Life.this.getView().requestFocus();
        Fragment_Busqueda_Life.this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    FragmentManager manager = ((Maven) getActivity()).getSupportFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.remove(Fragment_Busqueda_Life.this);
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
                FragmentManager manager = ((Maven) getActivity()).getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(Fragment_Busqueda_Life.this);
                trans.commit();
                manager.popBackStack();
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtcategorias, InputMethodManager.SHOW_IMPLICIT);

        if (v.equals(edtcategorias)) {
            if (hasFocus) {
                if (!flagcategoria) {
                    operation_categorias.getCategoryes();
                    operation_categorias.setInterface_operation_categorias(this);
                } else {
                    listaCategorias.setVisibility(View.VISIBLE);
                }
            } else {
                listaCategorias.setVisibility(View.GONE);
            }
        } else if (v.equals(edtdistritos)) {
            if (hasFocus) {
                if (!flagdistrito) {
                    operation_distritos.getDistritos();
                    operation_distritos.setInterface_operation_distritos(this);
                } else {
                    listaDistritos.setVisibility(View.VISIBLE);
                }
            } else {
                listaDistritos.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getCategoryes(boolean status, ArrayList<Categoria_DTO> categoria_dtos) {
        if (status) {
            Adapter_Categoria adapter_categoria = new Adapter_Categoria(getActivity(), categoria_dtos);
            listaCategorias.setAdapter(adapter_categoria);
            listaCategorias.setVisibility(View.VISIBLE);
            flagcategoria = true;
        }
    }

    @Override
    public void getDistritos(boolean status, ArrayList<Distrito_DTO> distrito_dtos) {
        if (status) {
            Adapter_Distrito adapter_categoria = new Adapter_Distrito(getActivity(), distrito_dtos);
            listaDistritos.setAdapter(adapter_categoria);
            listaDistritos.setVisibility(View.VISIBLE);
            flagdistrito = true;
        }
    }

    private void changeActionBar() {
        ImageView imgopenmenu = (ImageView) getView().findViewById(R.id.acb_img_cerrar);
        imgopenmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private boolean isCategory(String s) {
        for (int i = 0; i < listaCategorias.getAdapter().getCount(); i++) {
            Categoria_DTO categoria_dto = (Categoria_DTO) listaCategorias.getItemAtPosition(i);
            if (s.equals(categoria_dto.getNombrecategoria())) {
                return true;
            }
        }
        return false;
    }

    private boolean isDescuentoOrBeneficio(String s) {
        if (s.equals("Beneficio") || s.equals("Descuento")) {
            return true;
        }
        return false;
    }
}
