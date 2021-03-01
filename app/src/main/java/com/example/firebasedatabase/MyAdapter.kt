package com.example.firebasedatabase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasedatabase.databinding.ItemBinding
import com.google.firebase.firestore.QueryDocumentSnapshot

data class Item(val id: String, val name: String, val price: Int, val cart: String) {
    constructor(doc: QueryDocumentSnapshot) :
            this(doc.id, doc["name"].toString(), doc["price"].toString().toIntOrNull() ?: 0, doc["cart"].toString())

}

class MyViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(private val context: Context, private var items: List<Item>)
    : RecyclerView.Adapter<MyViewHolder>() {

    /*
    fun interface OnItemClickListener {
        fun onItemClick(name: String)
    }

     */

    private var itemClickListener: AdapterView.OnItemClickListener? = null

    fun setOnItemClickListener(listener: () -> Unit) {
        //itemClickListener = listener
    }

    fun updateList(newList: List<Item>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemBinding = ItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.binding.name.text = item.name
        if(item.cart.equals("true"))
            holder.binding.inCart.text = "in Cart"
        else
            holder.binding.inCart.text = " "
        holder.binding.name.setOnClickListener {
            val intent = Intent(holder.binding.name?.context, ItemActivity::class.java)
            intent.putExtra("ID", item.id)
            intent.putExtra("이름", item.name)
            intent.putExtra("가격", item.price.toString())
            intent.putExtra("카트", item.cart)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = items.size
}
