package com.example.chatbysas
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterClass(val context: Context,val userlist:ArrayList<User>):RecyclerView.Adapter<AdapterClass.InnerClass>() {
    class InnerClass(itemview: View):RecyclerView.ViewHolder(itemview) {
        val textView:TextView
        init {
            textView=itemview.findViewById(R.id.textView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        return InnerClass(LayoutInflater.from(parent.context).inflate(R.layout.users_layout,parent,false))
    }

    override fun onBindViewHolder(holder: InnerClass, position: Int) {
        val currentUser=userlist[position]
        holder.textView.text=currentUser.email

        holder.itemView.setOnClickListener(){
             val intent= Intent(context,ChatActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("name",currentUser.email)
            intent.putExtra("uid",currentUser.uid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

return userlist.size
    }

}