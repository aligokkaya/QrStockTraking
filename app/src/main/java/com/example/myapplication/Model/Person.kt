package com.example.myapplication.Model

 class Person{
    var id : Int = 0
    var barcode : String= ""
    var UrunAdi : String= ""
    var AlisFiyat : Double = 0.0
    var SatisFiyat : Double = 0.0
    var Note : String = ""
    var tutar: Double =0.0

    constructor(barcode: String, UrunAdi: String, AlisFiyat: Float, SatisFiyat: Float, Note:String)
    {

        this.barcode=barcode
        this.UrunAdi=UrunAdi
        this.AlisFiyat = AlisFiyat.toDouble()
        this.SatisFiyat=SatisFiyat.toDouble()
        this.Note=Note

    }

    constructor()
    {

    }
}