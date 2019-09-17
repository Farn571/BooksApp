package com.farn.booksapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.farn.booksapp.R
import com.farn.booksapp.models.Volume
import com.farn.booksapp.models.VolumeResponse
import kotlinx.android.synthetic.main.activity_volumes.*


class VolumesListActivity : AppCompatActivity(), VolumesListAdapter.OnVolumeClickListener {

    lateinit var volumesViewModel: VolumesViewModel
    lateinit var volumesRecyclerView: RecyclerView
    private lateinit var recyclerAdapter: VolumesListAdapter
    private lateinit var searchView : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volumes)
        recyclerViewInit()
        fetchData()
    }

    fun fetchData(){
        volumesViewModel = ViewModelProviders.of(this).get(VolumesViewModel::class.java)
        volumesViewModel.getVolumes(volumesViewModel.lastSearchQuery)

        val observer = Observer<VolumeResponse> {  response ->
            response?.let {
                if (it.error != null) {

                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }
                if (it.volumes.isNotEmpty()) {
                    progressBar.visibility = View.GONE
                    volumesRecyclerView.visibility = View.VISIBLE
                    recyclerAdapter.setData(it.volumes)
                    recyclerAdapter.notifyItemRangeChanged(0, recyclerAdapter.itemCount)
                }
            }
        }

        volumesViewModel.volumesLiveData.observe(this, observer )
    }



    fun recyclerViewInit(){
        recyclerAdapter = VolumesListAdapter(this)
        volumesRecyclerView = findViewById(R.id.volumes_recycler_view)
        volumesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        volumesRecyclerView.adapter = recyclerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val myActionMenuItem = menu?.findItem(R.id.action_search)
        searchView = myActionMenuItem?.actionView as SearchView
        searchView.setQuery("", true)
        searchView.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI

        if (!volumesViewModel.searchViewCondition.isEmpty()) {
            searchView.isIconified = false
            searchView.setQuery(volumesViewModel.searchViewCondition, true);
            searchView.clearFocus();
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                progressBar.visibility = View.VISIBLE
                volumesRecyclerView.visibility = View.GONE
                volumesViewModel.getVolumes(query)
                searchView.clearFocus()
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    override fun onVolumeClick(volume: Volume) {
        val intent = Intent(this, VolumeDetailActivity::class.java)
        intent.putExtra("volumeDetail", volume )
        startActivity(intent)
    }

    override fun onPause() {
        volumesViewModel.searchViewCondition = searchView.query.toString()
        super.onPause()

    }
}
