package com.zcdev.dpannagedz.data.Models

import com.parse.ParseGeoPoint

class Drivers {
    /*
    * driverName
    * phonenumber
    * location
    * */
    var driverUserName:String? =null
    var driverUserEmail:String? =null
    var driverPhone:String? =null
    var ObjectId:String? =null
//    var password:String? =null
    var location: ParseGeoPoint?=null
    var wilaya: String?=null
    constructor(){

    }


    constructor(u:String,e:String,ph:String,id:String,dlat:Double,dlang:Double,w:String){
        this.driverUserName=u
        this.driverUserEmail=e
        this.driverPhone=ph
        this.ObjectId=id
        this.location=ParseGeoPoint(dlat,dlang)
        this.wilaya=w
    }


}