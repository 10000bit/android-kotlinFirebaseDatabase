package com.example.firebasedatabase


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebasedatabase.databinding.ActivityItemBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.item.*

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("items")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.name.text = intent.getStringExtra("이름")
        binding.price.text = intent.getStringExtra("가격")
        val id = intent.getStringExtra("ID")
        val cart = intent.getStringExtra("카트")

        if(cart.toString().equals("true"))
            binding.button.setText("Remove from cart")
        else if(cart.toString().equals("false"))
            binding.button.setText("Add to cart")

        binding.button.setOnClickListener {
            if(binding.button.getText().toString().equals("Add to cart")) {
                binding.button.setText("Remove from cart")
                itemsCollectionRef.document(id.toString()).update("cart", "true")
                    .addOnSuccessListener {  }
            }
            else if(binding.button.getText().toString().equals("Remove from cart")) {
                binding.button.setText("Add to cart")
                itemsCollectionRef.document(id.toString()).update("cart", "false")
                    .addOnSuccessListener {  }
            }
        }


    }

}