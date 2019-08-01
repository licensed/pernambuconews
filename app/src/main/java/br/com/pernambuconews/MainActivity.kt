package br.com.pernambuconews

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Callback {

    lateinit var adapter: RecyclerView.Adapter<ItemViewHolder>
    val listItens = arrayListOf<Item>()

    private var g1 = true
    private var jc = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = LinearLayoutManager(this)

        rv.layoutManager = layout

        adapter = ItemAdapter(listItens, this)
        rv.adapter = adapter

        updateList()
    }

    override fun onLoaded(newArticles: MutableList<Article>?) {
        newArticles?.mapTo(listItens) {
            //Item(it.title, it.date, it.source, it.enclosure.url)
            Item(it.title, it.date, it.source)
        }
        adapter.notifyDataSetChanged()
        progress.visibility = View.GONE
        rv.visibility = View.VISIBLE
    }

    override fun onLoadFailed() {
        progress.visibility = View.GONE
        rv.visibility = View.GONE
        Toast.makeText(this, "Não foi possível carregar a lista de notícias.", Toast.LENGTH_LONG).show()
    }

    override fun onPreload() {
        progress.visibility = View.VISIBLE
        rv.visibility = View.GONE
    }

    //data class Item(val titulo: String, val data: Long, val link: Uri, val imagem: String)
    data class Item(val titulo: String, val pubDate: Long, val link: Uri)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    fun updateList() {
        listItens.clear()
        if (jc && g1) {
            PkRSS.with(this).load("https://pox.globo.com/rss/g1/pernambuco/").callback(this).async()
            PkRSS.with(this).load("https://www.jornaldocomercio.com/_conteudo/ultimas_noticias/rss.xml").callback(this).async()
        } else if (jc) {
            PkRSS.with(this).load("https://www.jornaldocomercio.com/_conteudo/ultimas_noticias/rss.xml").callback(this).async()
        } else if (g1) {
            PkRSS.with(this).load("https://pox.globo.com/rss/g1/pernambuco/").callback(this).async()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (it.itemId) {
                R.id.jc -> {
                    changeCheckVisibility(it)
                    jc = !jc
                }
                R.id.g1 -> {
                    changeCheckVisibility(it)
                    g1 = !g1
                }
                else -> {
                    Log.d("test", "3")
                }
            }
        }
        updateList()
        return super.onOptionsItemSelected(item)
    }

    fun changeCheckVisibility(item: MenuItem) {
        item.isChecked = !item.isChecked
    }
}
