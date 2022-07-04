package com.example.chatbysas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var mauth:FirebaseAuth
 private lateinit var  madapter:AdapterClass
 private lateinit var userlist:ArrayList<User>
 private lateinit var mdbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mauth=FirebaseAuth.getInstance()
        mdbRef=FirebaseDatabase.getInstance().getReference()

        userlist=ArrayList()
        madapter=AdapterClass(this,userlist)

        val userRecyclerView=findViewById<RecyclerView>(R.id.recyclermain)

        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.adapter=madapter

        mdbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
            userlist.clear()
            for(postsnapshot in snapshot.children){
            val currentUser=postsnapshot.getValue(User::class.java)
                if(mauth.currentUser?.uid!=currentUser?.uid) {
                    userlist.add(currentUser!!)
                }
}
            madapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                 }

        })

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuitems,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
if(item.itemId==R.id.logout){
    mauth.signOut()
    val intent=Intent(this@MainActivity,LoginActivity::class.java)
    finish()
    startActivity(intent)
    return true
}
        return true
    }
    }