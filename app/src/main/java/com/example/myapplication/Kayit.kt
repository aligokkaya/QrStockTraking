package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.DBHelper.DBHelper
import com.example.myapplication.Model.Person
import kotlinx.android.synthetic.main.activity_kayit.*


class Kayit : AppCompatActivity() {

    internal  lateinit var db:DBHelper
    val context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kayit)
        val bundle: Bundle? = intent.extras
        val barkot = bundle!!.getString("barcode")
        textView3.text=barkot
        val cont= db.getOneName(barkot.toString())
        db= DBHelper(this,null,null,1)
        kayit.setOnClickListener{
            if(editText2.text.toString().length > 0 &&
                editText3.text.toString().length>0 &&
                editText4.text.toString().length>0 &&
                editText6.text.toString().length>0){
            val person= Person(
                textView3.text.toString(),
                editText2.text.toString(),
                editText3.text.toString().toFloat(),
                editText4.text.toString().toFloat(),
                editText6.text.toString())
            db.addPerson(person)
        }
            else
            {
                Toast.makeText(context,"Lütfen Boş olan yerleri doldurunuz...",Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(applicationContext,Main3Activity::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener{
            editText2.text.clear()
            editText3.text.clear()
            editText4.text.clear()
            editText6.text.clear()
        }

    }



}
