package com.stimednp.boxofficefavoritecuk.myactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stimednp.boxofficefavoritecuk.R;
import com.stimednp.boxofficefavoritecuk.mydbmodel.MoviesModel;
import com.stimednp.boxofficefavoritecuk.myutils.AllOtherMethod;

public class DetailsMovieActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";

    private Toolbar toolbarDetails;
    private TextView tvMovieTitle, tvMovieDesc, tvMovieRelease, tvMovieRating, tvMovieVoteCount;
    private ImageView imgViewFromUrl, imgViewBg;
    private CardView cardViewDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        //inisial

        toolbarDetails = findViewById(R.id.toolbar_detail);
        CollapsingToolbarLayout collapse = findViewById(R.id.collapse_toolbar_detail);
        tvMovieTitle = findViewById(R.id.tv_title_detail);
        tvMovieDesc = findViewById(R.id.tv_desc_detail);
        tvMovieRelease = findViewById(R.id.tv_release_date_detail);
        tvMovieRating = findViewById(R.id.tv_rating_detail);
        tvMovieVoteCount = findViewById(R.id.tv_vote_count_detail);
        imgViewFromUrl = findViewById(R.id.img_movie_photo_detail);
        imgViewBg = findViewById(R.id.img_bg_detail);
        cardViewDetails = findViewById(R.id.card_view_img_detail);
        FloatingActionButton fabMoveApp = findViewById(R.id.fab_move_app);
        fabMoveApp.setOnClickListener(this);
        collapse.setExpandedTitleColor(Color.argb(0, 0, 0, 0));

        //callmethod
        setActionBarToolbar();
        getDataParceable();
    }

    private void setActionBarToolbar() {
        setSupportActionBar(toolbarDetails);
        toolbarDetails.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbarDetails.setOverflowIcon(getDrawable(R.drawable.ic_more_vert_black_24dp));
        toolbarDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataParceable() { //this is get DATA when click
        String pathImg = "https://image.tmdb.org/t/p/w500";
        cardViewDetails.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_scale_animation));
        MoviesModel moviesModel = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (moviesModel != null) {
            String movieTitle = moviesModel.getTitle();
            String movieDesc = moviesModel.getOverview();
            String movieRelease = moviesModel.getRelease_date();
            String movieRating = moviesModel.getVote_average().toString();
            String movieVoteCount = moviesModel.getVote_count();
            String movieUrlPhoto = moviesModel.getPoster_path();
            String movieUrlBg = moviesModel.getBackdrop_path();

            AllOtherMethod allOtherMethod = new AllOtherMethod();
            String movieDate = allOtherMethod.changeFormatDate(movieRelease);
            String movieYearRelease = allOtherMethod.getLastYear(movieDate);

            tvMovieTitle.setText(String.format(movieTitle + " (%s)", movieYearRelease));
            tvMovieDesc.setText(movieDesc);
            tvMovieRelease.setText(movieDate);
            tvMovieRating.setText(movieRating);
            tvMovieVoteCount.setText(movieVoteCount);
            if (movieUrlPhoto != null) {
                Glide.with(getApplicationContext()).load(pathImg + movieUrlPhoto).into(imgViewFromUrl);
            }
            if (movieUrlBg != null) {
                Glide.with(getApplicationContext()).load(pathImg + movieUrlBg).into(imgViewBg);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(getResources().getString(R.string.package_main_app));
        if (intent != null) {
            showDialogMove(intent);
        } else {
            Toast.makeText(this, getString(R.string.str_msg_notf), Toast.LENGTH_SHORT).show();
        }
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