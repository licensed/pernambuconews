package br.com.pernambuconews

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val titulo = view.findViewById(R.id.titulo) as TextView
    //val autor = view.findViewById(R.id.autor) as TextView
    val data = view.findViewById(R.id.data) as TextView
    val imagem = view.findViewById(R.id.imagem) as ImageView
    val btnVerMais = view.findViewById(R.id.btnVerMais) as Button
}