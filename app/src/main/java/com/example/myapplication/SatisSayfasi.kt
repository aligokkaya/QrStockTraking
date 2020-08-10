package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DBHelper.DBHelper
import com.example.myapplication.Model.Person
import kotlinx.android.synthetic.main.activity_satis_sayfasi.*
import kotlinx.android.synthetic.main.activity_satis_sayfasi.refresh


class SatisSayfasi : AppCompatActivity() {


    companion object{
        lateinit var dbHandler:DBHelper


    }

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satis_sayfasi)


        dbHandler= DBHelper(this,null,null,1)
        viewCustomers()
        satisYap.setOnClickListener{
            val intent = Intent(applicationContext,Barkodiki::class.java)
                    startActivity(intent)
        }
       refresh.setOnRefreshListener {
            viewCustomers()
            refresh.isRefreshing=false
        }
    }

    @SuppressLint("WrongConstant")
    private fun viewCustomers()
    {
        try {
            val bundle: Bundle? = intent.extras
            val barkot = bundle!!.getString("barcode")
            val customerList= dbHandler.querr(barkot.toString())
            val rviki : RecyclerView = findViewById(R.id.rviki)
           // val contiki=dbHandler.getOneName(barkot.toString())

            rviki.layoutManager= LinearLayoutManager(this, LinearLayout.VERTICAL,false) as RecyclerView.LayoutManager
            rviki.adapter=StsAdapter(this,customerList)
             }
        catch (exception: Exception) {
            Toast.makeText(applicationContext, "Satiş Listesi", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onResume() {

        viewCustomers()
        super.onResume()

    }


}
