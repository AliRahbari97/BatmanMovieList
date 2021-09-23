package com.example.batmanmovieslist.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.batmanmovieslist.R
import com.example.batmanmovieslist.Util.Constant.BASE_URL
import com.example.batmanmovieslist.Util.Util
import com.example.kotlinvolleytest1.Rating_Adapter
import com.example.kotlinvolleytest1.Rating_DataModel
import kotlinx.android.synthetic.main.activity_moviedetails.*
import org.json.JSONException

class MovieDetails_Activity : AppCompatActivity() {

    private var requestQueue: RequestQueue? = null
    private val ratingList = ArrayList<Rating_DataModel>()
    private lateinit var adapter: Rating_Adapter
    var id: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moviedetails)

        id = intent.getStringExtra("id").toString() //Movie Id
        requestQueue = Volley.newRequestQueue(this)

        Toast.makeText(this,"Please wait...",Toast.LENGTH_SHORT).show()

        if (Util.checkForInternet(this)) {
            getMovieDetails()
        } else {
            Toast.makeText(this, "Network Disconnected", Toast.LENGTH_SHORT).show()
        }

    }


    //Server Connection and set recyclerview
    private fun getMovieDetails() {
        val url = BASE_URL + "?apikey=3e974fca&i=" + id
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {


                Glide.with(this@MovieDetails_Activity)
                    .load(response.getString("Poster"))
                    .into(imageView)

                var rate = response.getString("imdbRating").toFloat()
                ratingBar.rating = rate / 3.toFloat()

                title_txtvw.text = response.getString("Title")
                year_txtvw.text = response.getString("Year")
                rate_txtvw.text = response.getString("Rated")
                runtime_txtvw.text = response.getString("Runtime")
                director_txtvw.text = response.getString("Director")
                actors_txtvw.text = response.getString("Actors")
                language_txtvw.text = response.getString("Language")
                awards_txtvw.text = response.getString("Awards")
                boxOffice_txtvw.text = response.getString("BoxOffice")
                genre_txtvw.text = response.getString("Genre")

                val jsonArray = response.getJSONArray("Ratings")
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val source = obj.getString("Source")
                    val value = obj.getString("Value")
                    ratingList.add(Rating_DataModel(source, value))
                }


                ratings_rclrvw.layoutManager = LinearLayoutManager(this)
                adapter = Rating_Adapter(ratingList)
                ratings_rclrvw.addItemDecoration(
                    DividerItemDecoration(
                        ratings_rclrvw.context,
                        (ratings_rclrvw.layoutManager as LinearLayoutManager).orientation
                    )
                )
                ratings_rclrvw.adapter = adapter

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace()
            Toast.makeText(this,error.message,Toast.LENGTH_LONG).show()
        })
        requestQueue?.add(request)
    }
}