package br.com.livroandroid.carros.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.activity.CarroActivity;
import br.com.livroandroid.carros.adapter.CarroAdapter;
import br.com.livroandroid.carros.domain.Carro;
import br.com.livroandroid.carros.domain.CarroService;
import livroandroid.lib.fragment.*;

public class CarrosFragment extends BaseFragment {
    protected RecyclerView recyclerView;
    private int tipo;
    private List<Carro> carros;

    public static CarrosFragment newInstance(int tipo){
        Bundle args = new Bundle();
        args.putInt("tipo", tipo);
        CarrosFragment f = new CarrosFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.tipo = getArguments().getInt("tipo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_carros, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle b){
            super.onActivityCreated(b);
            taskCarros();
    }

    private void taskCarros(){
//       new Thread(){
//           @Override
//           public void run(){
//               try {
//                   carros = CarroService.getCarros(getContext(), tipo);
//
//                   getActivity().runOnUiThread(new Runnable() {
//                       @Override
//                       public void run() {
//                           recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
//                           Toast.makeText(getActivity(), "AH", Toast.LENGTH_SHORT).show();
//                       }
//                   });
//               } catch (IOException e){
//                   Log.e("livro", e.getMessage(), e);
//               }
//           }
//       }.start();

        new GetCarrosTask().execute();
    }

    private CarroAdapter.CarroOnClickListener onClickCarro(){
        return new CarroAdapter.CarroOnClickListener(){
            @Override
            public void onClickCarro(View view, int idx){
                Carro c = carros.get(idx);
                Intent intent = new Intent(getContext(), CarroActivity.class);
                intent.putExtra("carro", Parcels.wrap(c));
                startActivity(intent);
            }
        };
    }

    private class GetCarrosTask extends AsyncTask<Void, Void, List<Carro>>{
        @Override
        protected List<Carro> doInBackground(Void... params){
            try{
                return CarroService.getCarros(getContext(), tipo);
            } catch (IOException e){
                alert("Erro: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Carro> carros){
            if(carros != null){
                CarrosFragment.this.carros = carros;
                recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
            }
        }
    }
}
