package com.stimednp.boxofficefavoritecuk.myactivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.stimednp.boxofficefavoritecuk.R;
import com.stimednp.boxofficefavoritecuk.myfragment.FavMoviesFragment;

public class MainActivity extends AppCompatActivity {
    boolean doubleButtonBackExit;
    Toolbar toolbar;
    TextView tvToolbarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String strMovies = getResources().getString(R.string.name_tab1_movies);
        toolbar = findViewById(R.id.main_tollbar);
        tvToolbarMain = findViewById(R.id.tv_toolbar_main);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            setDefaultFragment();
        }
        
        //
        setActionBarToolbar(strMovies);
    }

    private void setActionBarToolbar(String strMovies) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            tvToolbarMain.setText(strMovies);
        }
    }

    private void setDefaultFragment() {
        Fragment fragment = new FavMoviesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        String tapclose = getResources().getString(R.string.tap_to_close);
        if (doubleButtonBackExit) {
            super.onBackPressed();
            return;
        }
        this.doubleButtonBackExit = true;
        Toast.makeText(this, tapclose, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleButtonBackExit = false;
            }
        }, 2000);

    }
}
