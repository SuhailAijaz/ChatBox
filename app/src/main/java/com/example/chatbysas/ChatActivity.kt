package com.example.chatbysas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
   private lateinit var chatAdapterClass:ChatAdapterClass
   private lateinit var messageList:ArrayList<com.example.chatbysas.Message>
   private lateinit var mdbref:DatabaseReference
   private lateinit var chatRecyclerview:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mdbref=FirebaseDatabase.getInstance().getReference()

        messageList= ArrayList()
        chatAdapterClass= ChatAdapterClass(messageList)
        // create a unique room for sender and reciever uniquely,so msg privtzd
        var recieverRoom:String?=null
        var senderRoom:String?=null


        val chatRecyclerview=findViewById<RecyclerView>(R.id.recyclerChatActivity)
        val messagebox=findViewById<EditText>(R.id.edchat)
        val sendButton=findViewById<ImageView>(R.id.chatSendButton)

        val namee= intent.getStringExtra("name")
        val recieverUid=intent.getStringExtra("uid")

        val senderUid=FirebaseAuth.getInstance().currentUser?.uid
        senderRoom=recieverUid +senderUid
        recieverRoom=senderUid +recieverUid


        supportActionBar?.title =namee

//        messageList= ArrayList()
//        chatAdapterClass= ChatAdapterClass(messageList)
        chatRecyclerview.layoutManager=LinearLayoutManager(this)
        chatRecyclerview.adapter=chatAdapterClass

        // logic to add database to recyclerview

        mdbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for(postSnapSHot in snapshot.children){
                        val message=postSnapSHot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    chatAdapterClass.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

// adding msg to the database
        sendButton.setOnClickListener{
        val message=messagebox.text.toString()
            val messageObject=Message(message,senderUid)

            mdbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mdbref.child("chats").child(recieverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messagebox.setText("")
        }



    }
}