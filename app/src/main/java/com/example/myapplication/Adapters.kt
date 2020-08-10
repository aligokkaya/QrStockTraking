package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DBHelper.DBHelper
import com.example.myapplication.Model.Person
import kotlinx.android.synthetic.main.lo_customer_update.view.*
import kotlinx.android.synthetic.main.lo_customers.view.UrunAdi
import kotlinx.android.synthetic.main.lo_customers.view.barcode
import kotlinx.android.synthetic.main.lo_customers.view.SFiyat
import kotlinx.android.synthetic.main.lo_customers.view.btnDelete
import kotlinx.android.synthetic.main.lo_customers.view.btnUpdate
import java.util.ArrayList


class CustomerAdapter(mCtx: Context, val customers: ArrayList<Person>) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>(){


    val mCtx=mCtx
    lateinit var db:DBHelper

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val barkod=itemView.barcode
        val Urunad =itemView.UrunAdi
        val SFiyat=itemView.SFiyat
        val btnUpdate= itemView.btnUpdate
        val btnDelete =itemView.btnDelete

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerAdapter.ViewHolder {

        val v =LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return ViewHolder(v)

        }

    override fun getItemCount(): Int {
        return  customers.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val custumer:Person =customers[position]

        holder.barkod.text=custumer.barcode
        holder.Urunad.text=custumer.UrunAdi
        holder.SFiyat.text=custumer.SatisFiyat.toString()

        holder.btnDelete.setOnClickListener {
            val customerName = custumer.UrunAdi

            var alert=AlertDialog.Builder(mCtx).setTitle("Dikkat")
                .setMessage("Bu $customerName ürünü silinsin mi ?")
                .setPositiveButton("Evet",DialogInterface.OnClickListener{dialog, which ->
                    if (UrunListesi.dbHandler.delete(custumer.id))
                            {
                                customers.removeAt(position)
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position,customers.size)
                                Toast.makeText(mCtx,"Ürün silindi",Toast.LENGTH_SHORT).show()
                            }
                    else
                    {
                        Toast.makeText(mCtx,"Silinemedi",Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("Hayır",DialogInterface.OnClickListener { dialog, which ->  })
                .setIcon(R.drawable.ic_warning_black_24dp).show()
        }
        holder.btnUpdate.setOnClickListener{

            val inf=LayoutInflater.from(mCtx)
            val view =inf.inflate(R.layout.lo_customer_update,null)

            val txtbarcode : TextView = view.findViewById(R.id.editupBarcode)
            val txtname : TextView = view.findViewById(R.id.editupAdı)
            val txtsatis : TextView= view.findViewById(R.id.editupSatis)

            txtbarcode.text=custumer.barcode
            txtname.text=custumer.UrunAdi
            txtsatis.text=custumer.SatisFiyat.toString()

            val builder = AlertDialog.Builder(mCtx)
                .setTitle("Güncelleme").setView(view)
                .setPositiveButton("Güncelle",DialogInterface.OnClickListener {dialog, which ->
                    val isUpdate = UrunListesi.dbHandler.updateCustomer(custumer.id.toString(),
                        view.editupBarcode.text.toString(),
                        view.editupAdı.text.toString(),
                        view.editupSatis.text.toString())

                    if (isUpdate==true){

                        Toast.makeText(mCtx,"Günellendi",Toast.LENGTH_SHORT).show()

                    }
                    else
                    {
                        Toast.makeText(mCtx,"Güncellenmedi",Toast.LENGTH_SHORT).show()
                    }

                }).setNegativeButton("Çıkış",DialogInterface.OnClickListener { dialog, which ->

                })

            val alert =builder.create()
            alert.show()

        }
    }


}