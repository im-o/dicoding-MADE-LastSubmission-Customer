package com.stimednp.boxofficefavoritecuk.myactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_delete_favorite){
            Intent intent = getPackageManager().getLaunchIntentForPackage(getResources().getString(R.string.package_main_app));
            if (intent != null) {
                showDialogMove(intent);
            } else {
                Toast.makeText(this, getString(R.string.str_msg_notf), Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogMove(final Intent intent) {
        String strDelete = getResources().getString(R.string.str_title_move);
        String strMsg = getResources().getString(R.string.str_msg_move);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle(strDelete)
                .setMessage(strMsg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        }, 400);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create().show();
    }
}
