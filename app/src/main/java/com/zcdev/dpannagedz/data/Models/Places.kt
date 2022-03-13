package com.zcdev.dpannagedz.data.Models

import android.location.Location

class Places {
    var id: Int? =null
    var image: Int? =null
    var name: String? =null
    var placeLocation: Location?=null


    constructor(id: Int?, image: Int?, name: String?) {
        this.id = id
        this.image = image
        this.name = name
    }
    constructor(id: Int?, image: Int?, name: String?,lat:Double,long:Double) {
        this.id = id
        this.image = image
        this.name = name
        this.placeLocation= Location(this.name)
        this.placeLocation!!.latitude=lat
        this.placeLocation!!.longitude= long
    }
}