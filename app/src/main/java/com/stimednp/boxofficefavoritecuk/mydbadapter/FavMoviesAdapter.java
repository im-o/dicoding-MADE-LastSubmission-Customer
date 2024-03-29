package com.stimednp.boxofficefavoritecuk.mydbadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stimednp.boxofficefavoritecuk.R;
import com.stimednp.boxofficefavoritecuk.mydbmodel.MoviesModel;
import com.stimednp.boxofficefavoritecuk.myutils.AllOtherMethod;

import java.util.ArrayList;

/**
 * Created by rivaldy on 8/21/2019.
 */

public class FavMoviesAdapter extends RecyclerView.Adapter<FavMoviesAdapter.MoviesmViewHolder> {
    private static final String TAG = FavMoviesAdapter.class.getSimpleName();
    private Activity mActivity;
    private ArrayList<MoviesModel> moviesModelList = new ArrayList<>();

    public FavMoviesAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void setListMoviesm(ArrayList<MoviesModel> listMoviesModel) {
        this.moviesModelList.clear();
        this.moviesModelList.addAll(listMoviesModel);
        notifyDataSetChanged();
    }

    //click custome
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(MoviesModel moviesModel);
    }

    @NonNull
    @Override
    public FavMoviesAdapter.MoviesmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_favorite, parent, false);
        return new MoviesmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavMoviesAdapter.MoviesmViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(moviesModelList.get(holder.getAdapterPosition()));
            }
        });
        holder.bind(moviesModelList.get(position));
    }

    @Override
    public int getItemCount() {
        if (moviesModelList == null) {
            return 0;
        } else {
            return moviesModelList.size();
        }
    }

    class MoviesmViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewImg, cardViewDesc, cardViewRating;
        TextView tvTitle, tvRelease, tvRating, tvDesc;
        ImageView imgvPoster;
        RecyclerView recyclerView;

        MoviesmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvRelease = itemView.findViewById(R.id.tv_item_release);
            tvRating = itemView.findViewById(R.id.tv_item_rating);
            tvDesc = itemView.findViewById(R.id.tv_item_desc);
            imgvPoster = itemView.findViewById(R.id.img_item_photo);
            cardViewImg = itemView.findViewById(R.id.card_img);
            cardViewDesc = itemView.findViewById(R.id.card_view_desc);
            cardViewRating = itemView.findViewById(R.id.card_view_rating);
            recyclerView = itemView.findViewById(R.id.rv_tab_movies_room);
        }

        void bind(MoviesModel movieItems) {
            String pathImg = "https://image.tmdb.org/t/p/w300_and_h450_bestv2";
            String title = movieItems.getTitle();
            String release = movieItems.getRelease_date();
            String voteValue = movieItems.getVote_average().toString();
            String overView = movieItems.getOverview();
            String imgUrl = movieItems.getPoster_path();

            AllOtherMethod allOtherMethod = new AllOtherMethod();
            String myDate = allOtherMethod.changeFormatDate(release);
            tvTitle.setText(title);
            tvRating.setText(voteValue);
            tvDesc.setText(overView);
            tvRelease.setText(myDate);
            if (imgUrl != null) {
                Glide.with(mActivity)
                        .load(pathImg + imgUrl)
                        .into(imgvPoster);
            }
        }
    }
}
