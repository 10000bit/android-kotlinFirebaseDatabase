package com.example.firebasedatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasedatabase.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var adapter: MyAdapter? = null
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("items")
    private var snapshotListener: ListenerRegistration? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)

        adapter = MyAdapter(this, emptyList())
        adapter?.setOnItemClickListener {
            //queryItem(it)
        }
        binding.recyclerViewItems.adapter = adapter

        updateList()  // list items on recyclerview



    }

    override fun onStart() {
        super.onStart()

        // snapshot listener for all items
        snapshotListener = itemsCollectionRef.addSnapshotListener { snapshot, error ->
        }
    }

    override fun onStop() {
        super.onStop()
        snapshotListener?.remove()
    }

    private fun updateList() {
        itemsCollectionRef.get().addOnSuccessListener {
            val items = mutableListOf<Item>()
            for (doc in it) {
                items.add(Item(doc))
            }
            adapter?.updateList(items)
        }
    }


    companion object {
        const val TAG = "FirestoreActivity"
    }

}