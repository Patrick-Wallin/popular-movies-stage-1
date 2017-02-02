package com.example.piwal.popularmovies.utilities;

import com.example.piwal.popularmovies.data.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piwal on 1/25/2017.
 */

public class OpenMoviesJsonUtils {
    public static List<MovieData> getSimpleMovieDataFromJson(String moviesJson) {
        List<MovieData> parsedMovieData = new ArrayList<>();

        try {
            JSONObject jsonMovie = new JSONObject(moviesJson);

            if(jsonMovie != null) {
                if(jsonMovie.has("results")) {
                    if(jsonMovie.get("results") instanceof JSONArray) {
                        JSONArray arrayResults = jsonMovie.getJSONArray("results");

                        if(arrayResults != null && arrayResults.length() > 0) {
                            for(int i = 0; i < arrayResults.length(); i++) {
                                if(arrayResults.get(i) instanceof JSONObject) {
                                    MovieData data = new MovieData(arrayResults.getJSONObject(i));
                                    parsedMovieData.add(data);
                                }
                            }
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parsedMovieData;
    }
}
