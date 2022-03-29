package com.zcdev.dpannagedz.activities.accounts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.zcdev.dpannagedz.OurDriversActivity
import com.zcdev.dpannagedz.R
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.activity_account.*

import android.text.TextUtils
import kotlinx.android.synthetic.main.verificationcodeview.*

import com.google.firebase.auth.PhoneAuthOptions

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks


class AccountActivity : AppCompatActivity() {
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth
    var verificationId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        supportActionBar!!.hide()
        mAuth = FirebaseAuth.getInstance()



       btnSignupLogin.setOnClickListener {
         //  verify()
           verify.visibility=View.VISIBLE
           registerInfo.visibility=View.GONE

           // below line is for checking weather the user
           // has entered his mobile number or not.
           // below line is for checking weather the user
           // has entered his mobile number or not.
           if (TextUtils.isEmpty(textPhone.getText().toString())) {
               // when mobile number text field is empty
               // displaying a toast message.
               Toast.makeText(
                   this,
                   "Please enter a valid phone number.",
                   Toast.LENGTH_SHORT
               ).show()
           } else {
               // if the text field is not empty we are calling our
               // send OTP method for getting OTP from Firebase.
               val phone = "+213" + textPhone.getText().toString()
               sendVerificationCode(phone)
           }
         /*  var intent=Intent(this,OurDriversActivity::class.java)
           startActivity(intent)*/
    }
//0553783160
        btnValid.setOnClickListener {
            if (TextUtils.isEmpty(icv.inputContent)) {
                // if the OTP text field is empty display
                // a message to user to enter OTP
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            } else {
                // if OTP field is not empty calling
                // method to verify the OTP.
                verifyCode(icv.inputContent);
            }

          /*  var intent=Intent(this,OurDriversActivity::class.java)
            startActivity(intent)*/
        }

    }


    private fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    val i = Intent(this, OurDriversActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    Toast.makeText(this, "pleaze entre valide number", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }


    private fun sendVerificationCode(number: String) {
        // this method is used for getting
        // OTP on user phone number.
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // callback method is called on Phone auth provider.
    private val   // initializing our callbacks for on
    // verification callback method.
            mCallBack: OnVerificationStateChangedCallbacks =
        object : OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s
            }

            // this method is called when user
            // receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
                   // icv.setcon(code)
                    /**/

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.
                    verifyCode(icv.inputContent)
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                // displaying error message with firebase exception.
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }

    // below method is use to verify code from Firebase.
    private fun verifyCode(code: String) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential)
    }
}

