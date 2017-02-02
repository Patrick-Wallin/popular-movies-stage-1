package com.example.piwal.popularmovies;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.piwal.popularmovies.data.MovieData;
import com.squareup.picasso.Picasso;

/**
 * Created by piwal on 1/28/2017.
 */

public class DetailActivityFragment extends Fragment {
    private ImageView mBackDropImageView;
    private ImageView mMoviePosterImageView;

    private TextView mTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mRatingTextView;
    private TextView mOverviewTextView;
    private RatingBar mRatingBar;

    private MovieData mMovie;

    public DetailActivityFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(getString(R.string.intent_object_movie_data))) {
            mMovie = getArguments().getParcelable(getString(R.string.intent_object_movie_data));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail,container,false);

        mBackDropImageView = (ImageView) rootView.findViewById(R.id.backdrop_image_view);
        mMoviePosterImageView = (ImageView) rootView.findViewById(R.id.detail_poster_image_view);

        mTitleTextView = (TextView) rootView.findViewById(R.id.title_text_view);
        mReleaseDateTextView = (TextView) rootView.findViewById(R.id.release_date_text_view);
        mRatingTextView = (TextView) rootView.findViewById(R.id.rating_text_view);
        mOverviewTextView = (TextView) rootView.findViewById(R.id.overview_text_view);

        mRatingBar = (RatingBar) rootView.findViewById(R.id.rating_rating_bar);

        if(mMovie != null) {
            StringBuilder imagePath = new StringBuilder();
            imagePath.append(getContext().getString(R.string.url_themoviedb_base_image));
            if(!imagePath.toString().endsWith("/"))
                imagePath.append("/");
            imagePath.append(getContext().getString(R.string.backdrop_size));
            imagePath.append(mMovie.getBackDrop());
            Picasso.with(getContext())
                    .load(imagePath.toString())
                    .into(mBackDropImageView);

            imagePath.setLength(0);
            imagePath.append(getContext().getString(R.string.url_themoviedb_base_image));
            if(!imagePath.toString().endsWith("/"))
                imagePath.append("/");
            imagePath.append(getContext().getString(R.string.poster_size_detail));
            imagePath.append(mMovie.getMoviePosterPath());
            Picasso.with(getContext())
                    .load(imagePath.toString())
                    .into(mMoviePosterImageView);

            mTitleTextView.setText(mMovie.getMovieOriginalTitle());
            mReleaseDateTextView.setText(mMovie.getReleaseDate());
            mRatingTextView.setText(mMovie.getUserRating()+"/"+getContext().getString(R.string.full_rating_point));
            mOverviewTextView.setText(mMovie.getOverview());
            mRatingBar.setRating((float)Float.valueOf(mMovie.getUserRating()));

            getActivity().setTitle(mMovie.getMovieOriginalTitle());
        }

        return rootView;
    }



}
