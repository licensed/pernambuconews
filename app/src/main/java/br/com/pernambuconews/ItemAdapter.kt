package br.com.pernambuconews

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import kotlin.collections.ArrayList


class ItemAdapter(val list: ArrayList<MainActivity.Item>, val context: Context): RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_list, parent, false)
        val ivh = ItemViewHolder(v)
        return ivh
    }

    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        holder?.titulo?.text = list[position].titulo
        //holder?.autor?.text = list[position].autor
        holder?.data?.text = SimpleDateFormat("dd/MM/yyyy H:m:s", Locale("pt", "BR")).format(Date(list[position].pubDate))
        holder?.btnVerMais?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, list[position].link)
            context.startActivity(intent)
        }

        //DownloadImageTask(holder?.imagem!!).execute(list[position].imagem)
        //DownloadImageTask(holder?.linkFoto!!).execute(list[position].linkFoto)
    }

    override fun getItemCount(): Int = list.size
}

