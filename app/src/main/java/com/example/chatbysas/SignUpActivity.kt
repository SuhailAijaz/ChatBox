package com.example.chatbysas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var mauth:FirebaseAuth
    private lateinit var mdbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        title="SIGNUP ACTIVITY"
        // Initialize the Firebase
        mauth=FirebaseAuth.getInstance()

        val image=findViewById<ImageView>(R.id.signup_image)
        val edname=findViewById<EditText>(R.id.signupname)
        val edemail=findViewById<EditText>(R.id.signemail)
        val edpswd=findViewById<EditText>(R.id.signuppassword)
        val btnSignup=findViewById<Button>(R.id.signup_btn)

        btnSignup.setOnClickListener(){
            val signname=edname.text.toString()
            val signemail=edemail.text.toString()
            val signpswd= edpswd.text.toString()

            signup(signname,signemail,signpswd)
        }
    }

    private fun signup(name:String,email: String, signpswd:String) {

        mauth.createUserWithEmailAndPassword(email,signpswd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUsertoDatabase(name,email,mauth.currentUser?.uid!!)
                    val intent=Intent(this@SignUpActivity,LoginActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"Fill Details Carefully",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUsertoDatabase(name: String,email: String, uid: String?) {
mdbRef=FirebaseDatabase.getInstance().getReference()
        mdbRef.child("user").child(uid!!).setValue(User(name,email,uid!!))

    }
}