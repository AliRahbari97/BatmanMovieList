package com.example.batmanmovieslist.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.batmanmovieslist.R
import com.example.batmanmovieslist.Util.Constant.BASE_URL
import com.example.batmanmovieslist.Util.Util.checkForInternet
import com.example.kotlinvolleytest1.Movie_Adapter
import com.example.kotlinvolleytest1.Movie_DataModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException


// !!!!Please pay attention!!!!
// First of all thanks for your time for reviewing my code;
// Because of the my time limit in this week i didn't have much time for this project and the result are not my ideal.
// I wanted to code this project with MVVM, Retrofit and Coroutines and started another project with these before this one
// but unfortunately time limit didn't allow me for complete that in the way that should be and i made it a simple way (I Can show you the another mentioned project code in our meeting)

class MainActivity : AppCompatActivity() {
    private var requestQueue: RequestQueue? = null
    private val list = ArrayList<Movie_DataModel>()
    private lateinit var adapter: Movie_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)


        if (checkForInternet(this)) {
           getData()
        } else {
            Toast.makeText(this, "Network Disconnected", Toast.LENGTH_SHORT).show()
        }

    }

    //Server Connection and set recyclerview
    private fun getData() {
        val url = BASE_URL +"?apikey=3e974fca&s=batman"
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val jsonArray = response.getJSONArray("Search")
                for (i in 0 until jsonArray.length()) {
                    val employee = jsonArray.getJSONObject(i)


                    val name = employee.getString("Title")
                    val year = employee.getString("Year")
                    val id = employee.getString("imdbID")
                    val image = employee.getString("Poster")

                    list.add(Movie_DataModel(name, year, id, image))


                }


                recyclerview.layoutManager = LinearLayoutManager(this)
                adapter = Movie_Adapter(list,this@MainActivity)
                recyclerview.addItemDecoration(
                    DividerItemDecoration(
                        recyclerview.context,
                        (recyclerview.layoutManager as LinearLayoutManager).orientation
                    )
                )
                recyclerview.adapter = adapter

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace()
            Toast.makeText(this,error.message, Toast.LENGTH_LONG).show()
        })
        requestQueue?.add(request)
    }
}