package com.stimednp.boxofficefavoritecuk.myfragment;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.stimednp.boxofficefavoritecuk.R;
import com.stimednp.boxofficefavoritecuk.myactivity.DetailsMovieActivity;
import com.stimednp.boxofficefavoritecuk.mydb.LoadFavMoviesCallback;
import com.stimednp.boxofficefavoritecuk.mydbadapter.FavMoviesAdapter;
import com.stimednp.boxofficefavoritecuk.mydbmodel.MoviesModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.stimednp.boxofficefavoritecuk.mydb.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.stimednp.boxofficefavoritecuk.mymapcursor.MappingHelper.mapCursorToArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMoviesFragment extends Fragment implements LoadFavMoviesCallback {
    private FavMoviesAdapter favMoviesAdapter;
    private FavMoviesFragment.DataObserver myObserver;
    private RecyclerView recyclervFavMovies;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private SwipeRefreshLayout refreshLayoutMovie;
    private TextView textViewEmpty;
    private ArrayList<MoviesModel> list;

    public FavMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclervFavMovies = view.findViewById(R.id.rv_tab_movies_room);
        textViewEmpty = view.findViewById(R.id.tv_movies_empty);
        refreshLayoutMovie = view.findViewById(R.id.swipe_scroll_movie_room);

        recyclervFavMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclervFavMovies.setHasFixedSize(true);
        favMoviesAdapter = new FavMoviesAdapter(this.getActivity());
        recyclervFavMovies.setAdapter(favMoviesAdapter);

        checkingMovieList();

        //method
        evetListener();

        if (savedInstanceState == null) {
            new LoadMoviesAsync(getContext(), this).execute();
        } else {
            list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favMoviesAdapter.setListMoviesm(list);
            }
        }
    }

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshLayoutMovie.setRefreshing(true);
            }
        });
    }

    private void checkingMovieList() {
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, getContext());
        new LoadMoviesAsync(getContext(), this).execute();
    }

    @Override
    public void postExecute(Cursor cursor) {
        list = mapCursorToArrayList(cursor);
        if (getContext() != null) {
            getContext().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        }
        if (list.size() > 0) {
            textViewEmpty.setVisibility(View.GONE);
            favMoviesAdapter.setListMoviesm(list);
        } else {
            textViewEmpty.setVisibility(View.VISIBLE);
            favMoviesAdapter.setListMoviesm(list);
        }
        recyclervFavMovies.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade_transition_animation));
        refreshLayoutMovie.setRefreshing(false);
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavMoviesCallback> weakCallback;

        LoadMoviesAsync(Context context, LoadFavMoviesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI,
                    null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            try {
                new LoadMoviesAsync(context, (LoadFavMoviesCallback) context).execute();
            } catch (Exception ignored) {
            }
        }
    }

    private void evetListener() {
        favMoviesAdapter.setOnItemClickCallback(new FavMoviesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MoviesModel moviesModel) {
                Intent intent = new Intent(getActivity(), DetailsMovieActivity.class);
                intent.putExtra(DetailsMovieActivity.EXTRA_MOVIE, moviesModel);
                startActivity(intent);
            }
        });
        refreshLayoutMovie.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshLayoutMovie.isRefreshing()) {
                            checkingMovieList();
                            refreshLayoutMovie.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });
    }
}
