 package br.com.pernambuconews

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

class MainActivity : AppCompatActivity(), Callback {

    lateinit var listView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
    val listItens = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = LinearLayoutManager(this)

        listView = findViewById(R.id.rv) as RecyclerView
        listView.layoutManager = layout

        adapter = ItemAdapter(listItens, this)
        listView.adapter = adapter

        //PkRSS.with(this).load("https://rss.tecmundo.com.br/feed").callback(this).async()
        PkRSS.with(this).load("https://pox.globo.com/rss/g1/pernambuco/").callback(this).async()
        PkRSS.with(this).load("https://www.jornaldocomercio.com/_conteudo/ultimas_noticias/rss.xml").callback(this).async()
    }

    override fun onLoaded(newArticles: MutableList<Article>?) {
        listItens.clear()
        newArticles?.mapTo(listItens) {
            //Item(it.title, it.date, it.source, it.enclosure.url)
            Item(it.title, it.date, it.source)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onLoadFailed() {

    }

    override fun onPreload() {

    }

    //data class Item(val titulo: String, val data: Long, val link: Uri, val imagem: String)
    data class Item(val titulo: String, val pubDate: Long, val link: Uri)
}
