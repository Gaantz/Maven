package com.capr.pe.util;

import com.capr.pe.maven.R;

/**
 * Created by Gantz on 16/07/14.
 */
public class Util_Categorias {

    public static int getImageCateogry(int id_categoria) {

        int categorias[] = {
                R.drawable.cat_bar,
                R.drawable.cat_entre,
                R.drawable.cat_comida,
                R.drawable.cat_ropa,
                R.drawable.cat_market,
                R.drawable.cat_viajes,
                R.drawable.cat_desc};
        return categorias[id_categoria];
    }
}
