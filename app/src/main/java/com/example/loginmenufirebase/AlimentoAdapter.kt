package com.example.loginmenufirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlimentoAdapter(
    private val alimentos: List<Alimento>,
    private val onItemChecked: (Alimento) -> Unit
) : RecyclerView.Adapter<AlimentoAdapter.AlimentoViewHolder>() {

    class AlimentoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.txtNombreAlimento)
        val precio: TextView = view.findViewById(R.id.txtPrecioAlimento)
        val checkBox: CheckBox = view.findViewById(R.id.checkboxAlimento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlimentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_itemmenu, parent, false)
        return AlimentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlimentoViewHolder, position: Int) {
        val alimento = alimentos[position]
        holder.nombre.text = alimento.nombre
        holder.precio.text = "$${alimento.precio}"
        holder.checkBox.isChecked = alimento.seleccionado

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            alimento.seleccionado = isChecked
            onItemChecked(alimento)
        }
    }

    override fun getItemCount(): Int = alimentos.size
}
