package com.capr.pe.util;

import com.capr.pe.maven.R;

/**
 * Created by Gantz on 16/07/14.
 */
public class Util_Categorias {

    public static int getImageCateogry(int id_categoria) {

        int categorias[] = {
                R.drawable.otros,//
                R.drawable.diversion,
                R.drawable.comida,
                R.drawable.bar,
                R.drawable.ropa,
                R.drawable.viajes,
                R.drawable.cat_desc,//
                R.drawable.markets,
                R.drawable.grifos,
                R.drawable.salud,
                R.drawable.diversion,//
        };
        return categorias[id_categoria];
    }
}
