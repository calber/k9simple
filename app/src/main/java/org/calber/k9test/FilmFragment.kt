package org.calber.k9test

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName
import com.kizitonwose.android.disposebag.disposedWith
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.card_film.view.*
import kotlinx.android.synthetic.main.film_fragment.*


private val KEY = "1f8205e9"
private var films: Array<Film> = arrayOf()

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
            api.filmById("tt1285016", 1, KEY),
            api.filmById("tt1448755", 1, KEY),
            api.filmById("tt2015381", 1, KEY),
            api.filmById("tt0499549", 1, KEY),
            api.filmById("tt0091223", 1, KEY),
            api.filmById("tt0412142", 1, KEY),
            api.filmById("tt1367652", 1, KEY)

        )

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        list.layoutManager = layoutManager
        val filmAdapter = FilmAdapter()
        list.adapter = filmAdapter


        Observable.merge(idlist)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ n ->
                Log.d("TAG", n.toString())
                films += n
                filmAdapter.notifyItemInserted(films.size - 1)
            }, { e ->
                Log.d("TAG", e.localizedMessage, e)
            }, {
                Log.d("TAG", "complete")
            })
            .disposedWith(this)

    }

}

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {
    lateinit var context: Context

    class ViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(context).inflate(R.layout.card_film, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = films[position]
        holder.view.title.text = film.title
        holder.view.plot.text = film.plot
        Picasso.get().load(film.poster).into(holder.view.poster)
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

