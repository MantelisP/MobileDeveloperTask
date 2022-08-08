package com.example.mobiledevelopertask

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dog_ticket.view.*

import java.io.InputStream



class MainActivity : AppCompatActivity() {
    var adapter:dogAdapter?=null
    var listOfDogs = ArrayList<Dog>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val linksOfDogs = readJSONFromAsset()?.replace("[","")
            ?.replace("]","")
            ?.replace("\"","")
            ?.replace("\n","")
            ?.replace("\r","")
            ?.replace(" ","")
            ?.split(",")
            ?.toTypedArray()


        for(i in 0..linksOfDogs!!.size -1)
        {
            listOfDogs.add(Dog(sumOfDigits(linksOfDogs.get(i)),linksOfDogs.get(i)))
        }
        adapter= dogAdapter(this,listOfDogs)
        gvListDogs.adapter=adapter

    }
    class dogAdapter: BaseAdapter {
        var listOfDogs = ArrayList<Dog>()
        var context:Context? =null
        constructor(context: Context,listOfDogs: ArrayList<Dog>):super(){
            this.context=context
            this.listOfDogs = listOfDogs
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val dog = listOfDogs[position]
            var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var dogView = inflater.inflate(R.layout.dog_ticket,null)
            var allView = inflater.inflate(R.layout.activity_main,null)
            dogView.tvText.text = dog.name
            Picasso.get()
                .load(dog.link)
                .fit()
                .centerInside()
                .into(dogView.ivDogImage)
            dogView.ivDogImage.setOnClickListener {
                val intent = Intent(context, dogZoom::class.java)
                intent.putExtra("link",dog.link!!)
                context!!.startActivity(intent)
            }
            return dogView
        }

        override fun getItem(position: Int): Any {
            return listOfDogs[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listOfDogs.size
        }
    }
    fun readJSONFromAsset(): String? {
        val json: String?
        try {
            val  inputStream:InputStream = assets.open("linksOfDogs.json")
            json = inputStream.bufferedReader().use{it.readText()}
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }
    fun sumOfDigits(string: String): String {
        val regex = Regex("[^0-9]")
        val result = regex.replace(string, "")
        var count = 0
        val sumOfStr = result.sumOf {
            count++
            it.toString().toInt()
        }
        return sumOfStr.toString()
    }

}

