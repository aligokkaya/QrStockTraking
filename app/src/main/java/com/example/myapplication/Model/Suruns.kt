package com.example.myapplication.Model

class Suruns {
    var id: Int = 0
    var barcode: String = ""
    var UrunAdi: String = ""
    var AlisFiyat: Double = 0.0
    var SatisFiyat: Double = 0.0
    var tutar: Double = 0.0

    constructor(barcode: String,UrunAdi: String,AlisFiyat: Double,SatisFiyat: Double ,tutar: Double
    ) {

        this.barcode = barcode
        this.UrunAdi = UrunAdi
        this.AlisFiyat = AlisFiyat.toDouble()
        this.SatisFiyat = SatisFiyat.toDouble()
        this.tutar=tutar.toDouble()
    }
}
