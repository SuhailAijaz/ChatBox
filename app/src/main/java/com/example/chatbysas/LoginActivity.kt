package com.example.chatbysas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var  auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth=FirebaseAuth.getInstance()
        title="LOGIN ACTIVITY"
        id()

        auth=FirebaseAuth.getInstance()
    }

    private fun id() {
        val loginImage=findViewById<ImageView>(R.id.login_image)
        val edemail=findViewById<EditText>(R.id.edit_enmail)
        val edpass=findViewById<EditText>(R.id.edit_pswrd)
        val BtnLogin=findViewById<Button>(R.id.login_Button)
        val signbtn=findViewById<Button>(R.id.signup_btn)
        signbtn.setOnClickListener(){
            val intent=Intent(this@LoginActivity,SignUpActivity::class.java)
            startActivity(intent)
        }
        BtnLogin.setOnClickListener(){
            val email=edemail.text.toString()
            val password=edpass.text.toString()
            login(email,password)
        }
        }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext,""+email,Toast.LENGTH_SHORT).show()
                    val intent= Intent(this@LoginActivity,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }


    }
}
