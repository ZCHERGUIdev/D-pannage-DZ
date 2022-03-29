package com.zcdev.dpannagedz.data.dao


import android.location.Location
import android.util.Log
import com.parse.*
import com.zcdev.dpannagedz.data.Models.Drivers

class driverDao {

 /*   fun getRecords(rec: Order){
        var query = ParseQuery.getQuery<ParseObject>(Order::class.java.simpleName)
        query.whereEqualTo("customerUsername",rec.customerUsername)
        query.findInBackground ({ objs, e ->
            if (e==null){
                var item= mutableListOf<Order>()
                for (i in 0..item.size)
                {
                    Log.i("APP",item[i].customerUsername.toString())
                }

            }else
            {
                Log.e("APP",e.message)
            }
        })


    }*/


   /* fun getPendingRecordsByDriverUserName(d: Drivers, callback:(listOfRecord:MutableList<Order>)->Unit) {
        var query = ParseQuery.getQuery<ParseObject>(Driver::class.java.simpleName)

        query.findInBackground ({ objs, e ->
            if (e == null) {
                var item = mutableListOf<Drivers>()
                objs.mapTo(item){toSingleRecord(it)}
                callback(item)
            } else {
                Log.e("APP",e.message)
            }
        })

    }
    fun getNearbyRecords(driver: Drivers, callback:(listofRecords:MutableList<Drivers>)->Unit) {

        var query = ParseQuery.getQuery<ParseObject>(Drivers::class.java.simpleName)

        query.setLimit(100)
        query.findInBackground ({ objs, e ->
            if (e == null) {
                var item = mutableListOf<Order>()
                objs.mapTo(item){toSingleRecord(it)}

                callback(item)
            } else {
                Log.e("APP",e.message)
            }
        })

    }*/


    fun getNearbyDrivers(location: Location,callback:(listofRecords:MutableList<Drivers>)->Unit) {
          // location is current client location
        var query = ParseQuery.getQuery<ParseObject>("Drivers")
      /*  var dstninklm= p0.distanceInKilometersTo(location)
        var utiliseddistence=Math.round(dstninklm * 10).toDouble() / 10*/
      //  query.whereLessThan("location",location)
        query.setLimit(100)
        query.findInBackground ({ objs, e ->
            if (e == null) {
                var item = mutableListOf<Drivers>()
                 objs.mapTo(item){toSingleRecordDriver(it)}
                 callback(item)
            } else {
                Log.e("APP",e.message.toString())
            }
        })

    }

    private fun toSingleRecordDriver(parseDriver: ParseObject?): Drivers {
        var driver=Drivers()
        driver.ObjectId=parseDriver!!.objectId
        driver.driverUserName=parseDriver.getString("driverUserName")
        driver.driverUserEmail=parseDriver.getString("driverUserEmail")
        driver.driverPhone=parseDriver.getString("driverPhone")
        driver.location=parseDriver.getParseGeoPoint("location")
        driver.wilaya=parseDriver.getString("wilaya")
        return driver
    }

}




