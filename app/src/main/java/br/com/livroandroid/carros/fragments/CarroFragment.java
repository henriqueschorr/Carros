package br.com.livroandroid.carros.fragments;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.domain.Carro;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends BaseFragment {
    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        carro = Parcels.unwrap(getArguments().getParcelable("carro"));
        TextView tDesc = (TextView) view.findViewById(R.id.tDesc);
        tDesc.setText(carro.desc);
        final ImageView imgView = (ImageView) view.findViewById(R.id.img);
        Picasso.with(getContext()).load(carro.urlFoto).fit().into(imgView);
        return view;
    }

}
