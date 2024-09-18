package com.example.loginmenufirebase

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Menucomida : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlimentoAdapter
    private val alimentosSeleccionados = mutableListOf<Alimento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menucomida)

        // Configurar el Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.toolbar_title)



        recyclerView = findViewById(R.id.recyclerViewMenu)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val alimentos = mutableListOf<Alimento>()



        // Leer alimentos desde Firebase
        val database = FirebaseDatabase.getInstance().getReference("menu")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Recorremos las categorías "drinks" y "food"
                for (category in listOf("drinks", "food")) {
                    val categorySnapshot = snapshot.child(category)
                    for (data in categorySnapshot.children) {
                        val nombre = data.child("item").getValue(String::class.java)
                        val precio = data.child("price").getValue(Double::class.java)
                        if (nombre != null && precio != null) {
                            alimentos.add(Alimento(nombre, precio))
                        }
                    }
                }

                adapter = AlimentoAdapter(alimentos) { alimento ->
                    if (alimento.seleccionado) {
                        alimentosSeleccionados.add(alimento)
                    } else {
                        alimentosSeleccionados.remove(alimento)
                    }
                }
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Menucomida, "Error al cargar el menú", Toast.LENGTH_SHORT).show()
            }
        })

        val btnConfirmOrder = findViewById<Button>(R.id.btnConfirmOrder)
        btnConfirmOrder.setOnClickListener {
            if (alimentosSeleccionados.isNotEmpty()) {
                // Crear un Intent y pasar los alimentos seleccionados
                val intent = Intent(this, FacturaActivity::class.java)
                intent.putParcelableArrayListExtra("alimentosSeleccionados", ArrayList(alimentosSeleccionados))
                startActivity(intent)
            } else {
                Toast.makeText(this, "No ha seleccionado ningún alimento", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
