package com.sisada.neversituptodo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sisada.neversituptodo.R
import com.sisada.neversituptodo.databinding.FragmentListTaskBinding
import com.sisada.neversituptodo.model.Task

class ListTaskFragment : Fragment() {


    var adapter: TaskItemListAdapter? = null
    var items: ArrayList<Task> = ArrayList()
    var recyclerViewListItem : RecyclerView?= null


    lateinit var binding: FragmentListTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListTaskBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerViewListItem = view.findViewById(R.id.listview_task)

        return  view
    }

    override fun onStart() {
        super.onStart()


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //  initFakeTasks()
        setupRecyclerView()

    }

    private fun setupRecyclerView() {

        adapter = TaskItemListAdapter(items, ::onListItemClicked)

        var layoutManager =  LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerViewListItem?.layoutManager = layoutManager

        var dividerItemDecoration = DividerItemDecoration(
            recyclerViewListItem?.context,
            LinearLayoutManager.VERTICAL
        )
        

        recyclerViewListItem?.addItemDecoration(dividerItemDecoration)

        recyclerViewListItem?.adapter = adapter


        adapter!!.notifyDataSetChanged()

    }


    fun removeTask(task:Task){

    }

    fun updateTask(task:Task){

    }

    fun onListItemClicked(task: Task){


    }

    private fun initFakeTasks(){
        items = arrayListOf(
            Task(
                0,
                "id1",
                false,
                "2021-02-06T03:30:38.623Z",
                "sample task",
                "601c380f6688e00017f9e003",
                "2021-02-06T03:30:38.623Z"
            ),
            Task(
                0,
                "id2",
                true,
                "2021-02-06T03:30:38.623Z",
                "sample task number two",
                "601c380f6688e00017f9e003",
                "2021-02-06T03:30:38.623Z"
            )
        )

    }
}