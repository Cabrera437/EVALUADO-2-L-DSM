package com.example.loginmenufirebase

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FacturaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.factura)

        // Recibir los alimentos seleccionados desde el intent
        val alimentosSeleccionados = intent.getParcelableArrayListExtra<Alimento>("alimentosSeleccionados")

        // mostrar los detalles de la factura
        val textViewFactura = findViewById<TextView>(R.id.textViewFactura)
        var total = 0.0

        // Mostrar los alimentos seleccionados y calcular el total
        val facturaDetalles = StringBuilder()
        alimentosSeleccionados?.forEach { alimento ->
            facturaDetalles.append("${alimento.nombre}: $${alimento.precio}\n")
            total += alimento.precio
        }
        facturaDetalles.append("\nTotal a pagar: $${total}")

        textViewFactura.text = facturaDetalles.toString()

        val buttonConfirmarCompra = findViewById<Button>(R.id.button)
        buttonConfirmarCompra.text = "Confirmar Compra" // Cambiar el texto del botón

        buttonConfirmarCompra.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Compra Exitosa")
            builder.setMessage("¡Gracias por su compra! Total: $${total}")
            builder.setPositiveButton("OK") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            builder.show()
        }
    }
}
