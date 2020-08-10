package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DBHelper.DBHelper
import kotlinx.android.synthetic.main.activity_urun_listesi.*



class UrunListesi : AppCompatActivity() {

    companion object{
      lateinit var dbHandler:DBHelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_urun_listesi)
            dbHandler= DBHelper(this,null,null,1)
            viewCustomers()
            fab.setOnClickListener{
            val i = Intent(this,Main3Activity::class.java)
            startActivity(i)
            }
            refresh.setOnRefreshListener {
            viewCustomers()
            refresh.isRefreshing=false
        }
    }

    @SuppressLint("WrongConstant")
    private fun viewCustomers()
    {
        val customerList= dbHandler.readData(this)
        val adapter =CustomerAdapter(this,customerList)
        val rv : RecyclerView = findViewById(R.id.rv)
        rv.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL,false) as RecyclerView.LayoutManager
        rv.adapter=adapter
    }

    override fun onResume() {

        viewCustomers()
        super.onResume()

    }

}
