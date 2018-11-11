package org.calber.k9test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.annotations.SerializedName
import com.kizitonwose.android.disposebag.disposedWith
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


private val KEY = "1f8205e9"

class FilmFragment : Fragment() {

    companion object {
        fun newInstance() = FilmFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.film_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val api = OmdbApi.create()

        val idlist = listOf(
            api.filmById("tt1285016",1, KEY),
            api.filmById("tt1285017",1, KEY),
            api.filmById("tt1285018",1, KEY),
            api.filmById("tt1285019",1, KEY)
        )

        Observable.merge(idlist)
            .subscribeOn(Schedulers.io())
            .subscribe ({ n ->
                Log.d("TAG", n.toString())
            },{ e ->
                Log.d("TAG", e.localizedMessage,e)
            }, {
                Log.d("TAG", "complete")
            })
            .disposedWith(this)

    }

}

data class Film(
    @SerializedName("Title") val title: String?,
    @SerializedName("Year") val year: String?,
    @SerializedName("Rated") val rated: String?,
    @SerializedName("Released") val released: String?,
    @SerializedName("Runtime") val runtime: String?,
    @SerializedName("Genre") val genre: String?,
    @SerializedName("Director") val director: String?,
    @SerializedName("Writer") val writer: String?,
    @SerializedName("Actors") val actors: String?,
    @SerializedName("Plot") val plot: String?,
    @SerializedName("Language") val language: String?,
    @SerializedName("Country") val country: String?,
    @SerializedName("Awards") val awards: String?,
    @SerializedName("Poster") val poster: String?,
    @SerializedName("Metascore") val metascore: String?,
    @SerializedName("imdbRating") val imdbRating: String?,
    @SerializedName("imdbVotes") val imdbVotes: String?,
    @SerializedName("imdbID") val imdbID: String?,
    @SerializedName("Type") val type: String?,
    @SerializedName("DVD") val dVD: String?,
    @SerializedName("BoxOffice") val boxOffice: String?,
    @SerializedName("Production") val production: String?,
    @SerializedName("Website") val website: String?,
    @SerializedName("Response") val response: String?
)

