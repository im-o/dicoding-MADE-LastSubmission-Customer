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
    public static final String TAG = DetailsMovieActivity.class.getSimpleName();
    public static final String EXTRA_MOVIE = "extra_movie";
    //    public static final String EXTRA_WHERE_FROM = "extra_where_from";
    public static final String EXTRA_POSITION = "extra_position";
//    public static final int REQUEST_ADD = 100;
//    public static final int RESULT_DELETE = 301;
//    public static final int RESULT_ADD = 101;

    private Toolbar toolbarDetails;
    private TextView tvMovieTitle, tvMovieDesc, tvMovieRelease, tvMovieRating, tvMovieVoteCount;
    private ImageView imgViewFromUrl, imgViewBg;
    private CardView cardViewDetails;
    private FloatingActionButton fabFavoriteFalse;
//    private CoordinatorLayout containerCoord;

    //    private boolean isCheckFavorite;
//    private String keyFavorite = "my_key"; //savepreference
//    private String mySavePref = "my_savepref_favorite";
//    private String strMsgSuccessInsert;
//    private String strMsgSuccessDelete;
//    String whereFrom = "";

    private String movieTitle, movieDesc, movieRelease, movieRating, movieVoteCount, movieUrlPhoto, movieUrlBg;
    private int moviesId;
    //    private String tvShowTitle, tvShowDesc, tvShowRelease, tvShowRating, tvShowVoteCount, tvShowUrlPhoto, tvShowUrlBg;
    //
//    private Uri uri;
    //    private ContentResolver resolver;
    private int position;

//    private MoviesModel moviesModel;


//    private MoviesHelper moviesHelper;
//    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        //inisial

//        containerCoord = findViewById(R.id.container_coordinator_detail);
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
        fabFavoriteFalse = findViewById(R.id.fab_favorite_false);
        fabFavoriteFalse.setOnClickListener(this);
//        strMsgSuccessInsert = getResources().getString(R.string.str_msg_add_fav);
//        strMsgSuccessDelete = getResources().getString(R.string.str_msg_delete_fav);
        collapse.setExpandedTitleColor(Color.argb(0, 0, 0, 0));


//        checkingFavorite();

//        whereFrom = getIntent().getStringExtra(EXTRA_WHERE_FROM);
//        moviesModel = new MoviesModel();
//        moviesHelper = MoviesHelper.getInstance(getApplicationContext());
//        moviesHelper.open();

        MoviesModel moviesModel;
//        if (whereFrom.equals(FavMoviesAdapter.TAG) || (whereFrom.equals(FavMoviesFragment.TAG))) {
        moviesModel = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (moviesModel != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        } else {
            moviesModel = new MoviesModel();
//            }
        }


//         Secara fungsionalitas masih sama akan tetapi kita tidak menggunakan obyek Parcelable untuk ditampilkan di dalam
//         DetailsMovieActivity, melainkan menggunakan Uri untuk ambil data kembali dari  ContentProvider


//        if (whereFrom.equals(FavMoviesAdapter.TAG) || (whereFrom.equals(FavMoviesFragment.TAG))) {
//        uri = getIntent().getData();
//        Log.d(TAG, "DEMoviesByTitle URI 1 : " + uri);
//        if (uri != null) {
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//            if (cursor != null) {
//                if (cursor.moveToFirst()) moviesModel = new MoviesModel(cursor);
//                cursor.close();
//            }
//        }        //callmethod
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
//        String whereFrom = getIntent().getStringExtra(EXTRA_WHERE_FROM);
        String pathImg = "https://image.tmdb.org/t/p/w500";
        cardViewDetails.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_scale_animation));

//        if (whereFrom.equals(FavMoviesAdapter.TAG) || (whereFrom.equals(FavMoviesFragment.TAG))) { //for details MoviesAdapter from dbroom
        MoviesModel moviesModel = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (moviesModel != null) {
            moviesId = moviesModel.getId();
            movieTitle = moviesModel.getTitle();
//                keyFavorite = movieTitle; //key
            movieDesc = moviesModel.getOverview();
            movieRelease = moviesModel.getRelease_date();
            movieRating = moviesModel.getVote_average().toString();
            movieVoteCount = moviesModel.getVote_count();
            movieUrlPhoto = moviesModel.getPoster_path();
            movieUrlBg = moviesModel.getBackdrop_path();

            AllOtherMethod allOtherMethod = new AllOtherMethod();
            String movieDate = allOtherMethod.changeFormatDate(movieRelease);
            String movieYearRelease = allOtherMethod.getLastYear(movieDate);

            tvMovieTitle.setText(String.format(movieTitle + " (%s)", movieYearRelease));
            tvMovieDesc.setText(movieDesc);
            tvMovieRelease.setText(movieDate);
            tvMovieRating.setText(movieRating);
            tvMovieVoteCount.setText(movieVoteCount);
            Glide.with(getApplicationContext()).load(pathImg + movieUrlPhoto).into(imgViewFromUrl);
            Glide.with(getApplicationContext()).load(pathImg + movieUrlBg).into(imgViewBg);
        }
//        }
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

//    private void setMovies() { //Insert
//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_MOVIE, moviesModel);
//        intent.putExtra(EXTRA_POSITION, position);
//
//        MoviesModel moviesModel = new MoviesModel();
//        moviesModel.setId(moviesId);
//        moviesModel.setTitle(movieTitle);
//        moviesModel.setRelease_date(movieRelease);
//        moviesModel.setVote_average(Double.parseDouble(movieRating));
//        moviesModel.setVote_count(movieVoteCount);
//        moviesModel.setOverview(movieDesc);
//        moviesModel.setPoster_path(movieUrlPhoto);
//        moviesModel.setBackdrop_path(movieUrlBg);
//
//        ContentValues values = new ContentValues();
//        values.put(ID, moviesId);
//        values.put(COLUMN_TITLE, movieTitle);
//        values.put(COLUMN_RELEASE_DATE, movieRelease);
//        values.put(COLUMN_VOTE_AVERAGE, movieRating);
//        values.put(COLUMN_VOTE_COUNT, movieVoteCount);
//        values.put(COLUMN_OVERVIEW, movieDesc);
//        values.put(COLUMN_POSTER_PATH, movieUrlPhoto);
//        values.put(COLUMN_BACK_PATH, movieUrlBg);
//
//        getContentResolver().insert(CONTENT_URI, values);
//        showSnackBar(movieTitle + " " + strMsgSuccessInsert);
//        setResult(RESULT_ADD, intent);
//    }

//    private void deleteMovies() { //delete
//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_POSITION, 0);
//        getContentResolver().delete(uri, null, null);
//        showSnackBar(movieTitle + " " + strMsgSuccessDelete);
//        setResult(RESULT_DELETE, intent);
//    }
//
//    private void showSnackBar(String msg) {
//        Snackbar snackbar = Snackbar.make(containerCoord, msg, Snackbar.LENGTH_SHORT);
//        snackbar.show();
//    }

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.fab_favorite_false) {
//            setIsFavorite();
//        }
//    }

//    private void tesPref(boolean isFavor) {
//        SharedPreferences sharedPreferences = this.getSharedPreferences(mySavePref, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean(keyFavorite, isFavor);
//        editor.apply();
//    }
//
//    private boolean radRef() {
//        SharedPreferences mSharedPreferences = this.getSharedPreferences(mySavePref, Context.MODE_PRIVATE);
//        return mSharedPreferences.getBoolean(keyFavorite, false);
//    }
//
//    private void setIsFavorite() {
//        String whereFrom = getIntent().getStringExtra(EXTRA_WHERE_FROM);
//        if (isCheckFavorite) {
//            boolean isFavorite = false;
//            tesPref(isFavorite);
//            checkingFavorite();
//            //delete
//            if ((whereFrom.equals(FavMoviesFragment.TAG) || (whereFrom.equals(FavMoviesAdapter.TAG)) || (whereFrom.equals(MovieItemsAdapter.TAG)))) {
//                deleteMovies(); //deleteMovies
//            } else if ((whereFrom.equals(TvShowItemsAdapter.TAG)) || (whereFrom.equals(TvShowAdapter.TAG))) {
//                deleteTvShowByTitle(); //deleteTvShow
//            }
//
//        } else {
//            boolean isFavorite = true;
//            tesPref(isFavorite);
//            checkingFavorite();
//            //insert
//            if ((whereFrom.equals(FavMoviesFragment.TAG) || (whereFrom.equals(FavMoviesAdapter.TAG)) || (whereFrom.equals(MovieItemsAdapter.TAG)))) {
//                setMovies(); //insertMovies
//            } else if ((whereFrom.equals(TvShowItemsAdapter.TAG)) || (whereFrom.equals(TvShowAdapter.TAG))) {
//                setTvShows(); //insertTvShow
//            }
//        }
//    }

//    private void checkingFavorite() {
//        boolean isFavorite = radRef();
//        if (isFavorite) {
//            fabFavoriteFalse.setImageResource(R.drawable.ic_favorite_true_24dp);
//            isCheckFavorite = true;
//        } else {
//            fabFavoriteFalse.setImageResource(R.drawable.ic_favorite_border_before);
//            isCheckFavorite = false;
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        moviesHelper.close();
//    }


//    private void deleteMovies() { //delete
//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_POSITION, 0);
//        getContentResolver().delete(uri, null, null);
//        showSnackBar(movieTitle + " " + strMsgSuccessDelete);
//        setResult(RESULT_DELETE, intent);
//        finish();
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_item, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.item_delete_favorite){
//            showDialogMenu();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void showDialogMenu() {
//        String strDelete = getResources().getString(R.string.delete);
//        String strMsg = getResources().getString(R.string.str_msg);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
//        builder.setTitle(strDelete)
//                .setMessage(strMsg)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                deleteMovies();
//                            }
//                        }, 1000);
//                    }
//                })
//                .setNegativeButton(android.R.string.cancel, null)
//                .create().show();
//    }

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