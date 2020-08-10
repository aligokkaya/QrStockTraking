package com.example.myapplication.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.myapplication.Model.Person
import com.example.myapplication.Model.Suruns
import kotlin.collections.ArrayList


class DBHelper(var context: Context, name : String?,factory: SQLiteDatabase.CursorFactory?,version: Int) :
SQLiteOpenHelper(context,dbname,factory,version){

companion object{

    private  val dbname = "VTKayit"
    val DBtablo1 = "Urunler"
    val DBtablo2="SatilanUrunler"
    val id = "id"
    val barcode = "barcode"
    val Urunadi = "urunad"
    val AlisFiyat= "AlisFiyat"
    val SatisFiyat = "SatisFiyat"
    val Note = "Note"
    val tutar="tutar"


    //val DBTanblotwo ="Satılanlar"

}
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =("CREATE TABLE ${DBtablo1}(" +
                "$id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$barcode VARCHAR(256)," +
                "$Urunadi STRING," +
                "$AlisFiyat DOUBLE," +
                "$SatisFiyat DOUBLE," +
                "$Note TEXT NOT NULL)");
        val createTable2 =("CREATE TABLE ${DBtablo2}(" +
                "$id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$barcode VARCHAR(256)," +
                "$Urunadi STRING," +
                "$AlisFiyat DOUBLE," +
                "$SatisFiyat DOUBLE," +
                "$tutar TEXT NOT NULL)");

        db?.execSQL(createTable)
        db?.execSQL(createTable2)
           }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS"+ DBtablo1)
        db?.execSQL("DROP TABLE IF EXISTS"+ DBtablo2)
        onCreate(db!!)
    }

    fun getOneName(name: String): Person? {
        val db = this.writableDatabase
        val selectQuery =  "SELECT  * FROM $DBtablo1 WHERE $barcode = ?"
        db.rawQuery(selectQuery, arrayOf(name)).use { // .use requires API 16
            if (it.moveToFirst()) {
                val result = Person()
                result.id = it.getInt(it.getColumnIndex(id))
                result.barcode = it.getString(it.getColumnIndex(barcode))
                return result
            }
        }
        return null
    }
    fun querr(barkot: String): ArrayList<Person> {
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM $DBtablo1 WHERE $barcode = ?"
        db.rawQuery(selectQuery, arrayOf(barkot)).use {
            val gelen = ArrayList<Person>()
            if (it.moveToFirst()) {
                val result=Person()
                result.id = it.getInt(it.getColumnIndex(id))
                result.UrunAdi= it.getString(it.getColumnIndex(Urunadi))
                result.barcode = it.getString(it.getColumnIndex(barcode))
                result.SatisFiyat= it.getString(it.getColumnIndex(SatisFiyat)).toDouble()
                gelen.add(result)
            }
            return gelen
        }
    }


    fun readData(mCtx:Context) : ArrayList<Person> {

        val db = this.readableDatabase
        val query = "SELECT * FROM $DBtablo1"
        val result = db.rawQuery(query, null)
        val lstPerson = ArrayList<Person>()
        if(result.count==0)
        {
            Toast.makeText(mCtx,"Ürün Bulunamadı!!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            result.moveToFirst()
            while(!result.isAfterLast())
            {
                val gelen=Person()
                gelen.id = result.getString(result.getColumnIndex(id)).toInt()
                gelen.barcode = result.getString(result.getColumnIndex(barcode)).toString()
                gelen.UrunAdi = result.getString(result.getColumnIndex(Urunadi)).toString()
                gelen.AlisFiyat = result.getString(result.getColumnIndex(AlisFiyat)).toDouble()
                gelen.SatisFiyat = result.getString(result.getColumnIndex(SatisFiyat)).toDouble()
                gelen.Note = result.getString(result.getColumnIndex(Note)).toString()
                lstPerson.add(gelen)
                result.moveToNext()
            }
            Toast.makeText(mCtx,"${result.count.toString()} Başarılı",Toast.LENGTH_SHORT).show()
        }
        result.close()
        db.close()
        return lstPerson
    }


    fun addPerson(person: Person) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(barcode, person.barcode)
        cv.put(Urunadi,person.UrunAdi)
        cv.put(AlisFiyat, person.AlisFiyat)
        cv.put(SatisFiyat, person.SatisFiyat)
        cv.put(Note, person.Note)
        var result= db.insert(DBtablo1,null,cv)
        if (result==-1.toLong())
        {
            Toast.makeText(context,"Başarısız oldu..",Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context,"Başarılı!!!!!",Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
    fun addPerson2(person: Suruns) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(barcode, person.barcode)
        cv.put(Urunadi,person.UrunAdi)
        cv.put(AlisFiyat, person.AlisFiyat)
        cv.put(SatisFiyat, person.SatisFiyat)
        cv.put(tutar,person.tutar)
        var result= db.insert(DBtablo2,null,cv)
        if (result==-1.toLong())
        {
            Toast.makeText(context,"Başarısız oldu..",Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context,"Başarılı!!!!!",Toast.LENGTH_SHORT).show()
        }
        db.close()
    }


    fun delete(ide:Int):Boolean {
      val qry = "Delete From $DBtablo1 where $id = $ide"
        val db=this.writableDatabase
        var result : Boolean = false

        try{
            var cursor = db.execSQL(qry)
            result = true
        }catch (e : Exception)
        {
            Log.e(ContentValues.TAG,"silme gerçekleşmedi")
        }
        db.close()
        return result
    }

    fun updateCustomer(idi:String, barcode : String, Urunad: String, SFiyati: String): Boolean {
        val db =this.writableDatabase
        val database=DBHelper
        val cv =ContentValues()
        var result : Boolean = false
        cv.put(database.id,idi)
        cv.put(database.barcode,barcode)
        cv.put(database.Urunadi,Urunad)
        cv.put(database.SatisFiyat,SFiyati.toDouble())
        try {
             db.update(DBtablo1, cv, "${database.id} = ?", arrayOf(idi) )
              result =true
         }catch (e : Exception){
            Log.e(ContentValues.TAG,"Güncelleme gerçekleşmedi")
             result= false
        }
        return true
    }
}



