package br.com.livroandroid.carros.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.domain.Carro;
import br.com.livroandroid.carros.fragments.CarroFragment;
import br.com.livroandroid.carros.fragments.CarrosFragment;

public class CarroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);

        setUpToolbar();
        Carro c = Parcels.unwrap(getIntent().getParcelableExtra("carro"));
        getSupportActionBar().setTitle(c.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            CarroFragment frag = new CarroFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.CarroFragment, frag).commit();
        }
    }
}
