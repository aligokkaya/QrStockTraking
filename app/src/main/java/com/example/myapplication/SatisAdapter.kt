package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DBHelper.DBHelper
import com.example.myapplication.Model.Person
import kotlinx.android.synthetic.main.lo_customers.view.*
import java.util.ArrayList

class StsAdapter(mCtx: Context, val  custo : ArrayList<Person>) : RecyclerView.Adapter<StsAdapter.ViewHolder>() {

    val mCtx=mCtx
    lateinit var db:DBHelper

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val barkod=itemView.barcode
        val Urunad =itemView.UrunAdi
        val SFiyat=itemView.SFiyat
        val btnDelete =itemView.btnDelete

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StsAdapter.ViewHolder {

        val v =LayoutInflater.from(parent.context).inflate(R.layout.satis_layout,parent,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return  custo.size

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val custumer: Person = custo[position]
            holder.barkod.text = custumer.barcode
            holder.Urunad.text = custumer.UrunAdi
            holder.SFiyat.text = custumer.SatisFiyat.toString()
        /*    val contiki=db.getOneName(name)

           val person =Person(
               holder.barkod.text.toString(),
               holder.Urunad.text.toString(),
               holder.SFiyat.text.toString().toFloat(),
              // holder.Alis.text.toString().toFloat(),
               holder.Urunad.text.toString()
           )
        db.addPerson(person)*/
           holder.btnDelete.setOnClickListener {

        }
    }
}
