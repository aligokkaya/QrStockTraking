package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
    }
    fun oku(view: View) {
        val intent = Intent(applicationContext,BarkodOku::class.java)
        startActivity(intent)
    }
    fun urunler(view: View) {
        val intent = Intent(applicationContext,UrunListesi::class.java)
        startActivity(intent)
    }
    fun satis(view : View) {
        val intent = Intent(applicationContext,SatisSayfasi::class.java)
        startActivity(intent)
    }



}
