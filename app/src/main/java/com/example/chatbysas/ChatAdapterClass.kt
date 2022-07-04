package com.example.chatbysas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class ChatAdapterClass( val messagelist:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

val ItemRcv=1
    val ItemSent=2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
return recieveViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recieverlayout,parent,false))
        }
        else
            return sentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.senderlayout,parent,false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass==sentViewHolder::class.java){
val viewholder=holder as sentViewHolder
            holder.sendlayoutTextview.text=messagelist[position].message
        }
        else{                                           // here messagelist[position]=val def and use common in both
val viewholder=holder as recieveViewHolder
            holder.rcvlayoutTextview.text=messagelist[position].message
        }

    }

    override fun getItemCount(): Int {
return messagelist.size
    }

    class sentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sendlayoutTextview: TextView

        init {
            sendlayoutTextview = view.findViewById(R.id.sendlayoutTextview)
        }

    }

    override fun getItemViewType(position: Int): Int {
val currentMessage=messagelist[position]
        if(FirebaseAuth.getInstance()?.currentUser?.uid.equals(currentMessage.senderId)){
            return ItemSent
        }else
            return ItemRcv
    }

    class recieveViewHolder(view:View):RecyclerView.ViewHolder(view){
        val rcvlayoutTextview:TextView
        init {
            rcvlayoutTextview=view.findViewById(R.id.rcvlayoutTextview)

        }

    }

}