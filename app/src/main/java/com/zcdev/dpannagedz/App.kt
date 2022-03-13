package com.zcdev.dpannagedz

import android.app.Application
import com.parse.Parse
import com.parse.ParseACL

class App: Application(){
    override fun onCreate() {
        super.onCreate()

        Parse.enableLocalDatastore(this)
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.app_id))
                .clientKey(getString(R.string.client_key))
                .server(getString(R.string.server))
                .build()
        )
        var parseAcl= ParseACL()
        parseAcl.publicWriteAccess=true
        parseAcl.publicReadAccess=true
        ParseACL.setDefaultACL(parseAcl,true)
    }
}