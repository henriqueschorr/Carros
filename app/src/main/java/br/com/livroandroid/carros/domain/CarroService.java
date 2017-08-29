package br.com.livroandroid.carros.domain;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.livroandroid.carros.R;
import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.HttpHelper;
import livroandroid.lib.utils.XMLUtils;

import static android.content.ContentValues.TAG;
import static livroandroid.lib.fragment.NavigationDrawerFragment.LOG_ON;

/**
 * Created by Henrique on 15/08/2017.
 */

public class CarroService {

    private static final String URL = "http://www.livroandroid.com.br/livro/carros/carros_{tipo}.json";

    public static List<Carro> getCarros(Context context, int tipo) throws IOException{
//        String json = readFile(context, tipo);
        String tipoString = getTipo(tipo);
        String url = URL.replace("{tipo}", tipoString);

        HttpHelper http = new HttpHelper();
        String json = http.doGet(url);
        List<Carro> carros = parserJSON(context, json);
        return carros;
    }

    private static String getTipo(int tipo){
        if(tipo == R.string.classicos){
            return "classicos";
        } else if(tipo == R.string.esportivos){
            return "esportivos";
        }
        return "luxo";
    }

    private static String readFile(Context context, int tipo) throws IOException{
        if(tipo == R.string.classicos){
            return FileUtils.readRawFileString(context, R.raw.carros_classicos, "UTF-8");
        } else if (tipo == R.string.esportivos){
            return FileUtils.readRawFileString(context, R.raw.carros_esportivos, "UTF-8");
        }
        return FileUtils.readRawFileString(context, R.raw.carros_luxo, "UTF-8");
    }

    private static List<Carro> parserJSON(Context context, String json){
        List<Carro> carros = new ArrayList<Carro>();

        try{
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("carros");
            JSONArray jsonCarros = obj.getJSONArray("carro");

            for(int i=0; i<jsonCarros.length(); i++){
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();

                c.nome = jsonCarro.optString("nome");
                c.desc = jsonCarro.optString("desc");
                c.urlFoto = jsonCarro.optString("url_foto");
                c.urlInfo = jsonCarro.optString("url_info");
                c.urlVideo = jsonCarro.optString("url_video");
                c.latitude = jsonCarro.optString("latitude");
                c.longitude = jsonCarro.optString("longitude");
                carros.add(c);
            }
        } catch (JSONException e){}
        return carros;
    }
}
