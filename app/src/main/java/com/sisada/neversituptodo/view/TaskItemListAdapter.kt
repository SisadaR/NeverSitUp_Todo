package com.sisada.neversituptodo.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sisada.neversituptodo.R
import com.sisada.neversituptodo.etc.DATE_FORMAT_SHORT
import com.sisada.neversituptodo.etc.TIME_FORMAT
import com.sisada.neversituptodo.model.Task
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class TaskItemListAdapter(
    private val listTasks: List<Task>,
    private val onClickCallback: (task: Task) -> Unit
): RecyclerView.Adapter<TaskItemListAdapter.ViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_task,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listTasks[position]
        holder.setup(item)

        with(holder.mView) {
            tag = item
            setOnClickListener{
                onClickCallback(item)
            }
        }
    }

    override fun getItemCount(): Int = listTasks.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        private val imageCategory : ImageView = mView.findViewById(R.id.icon)
        private val tvTitle: TextView = mView.findViewById(R.id.tv_todo_title)
        private val tvDate: TextView = mView.findViewById(R.id.tv_todo_date)
        private val tvTime: TextView = mView.findViewById(R.id.tv_todo_time)
        private val checkBox: CheckBox = mView.findViewById(R.id.checkBox_todo)


        fun setup(task: Task){

            Log.d("ViewHolderSetup", task._id + " " + task.description)
            //current api didn't support category, just leave as it is

            //current api didn't support title, use description
            tvTitle.text = task.description

            //current api didn't have due date and time, for mocking purpose we use createdAt field

            // Instant.parse( "2016-09-12T13:15:17.309Z" ).atZone( ZoneId.systemDefault() ).toString();
            val zonedDateTime = Instant.parse(task.createdAt).atZone(ZoneId.systemDefault())
            tvDate.text = DateTimeFormatter.ofPattern(DATE_FORMAT_SHORT).format(zonedDateTime)
            tvTime.text = DateTimeFormatter.ofPattern(TIME_FORMAT).format(zonedDateTime)

            checkBox.isChecked = task.completed
            if(task.completed)
                displayCheckedMode()
            else
                displayTimeMode()
        }

        fun displayCheckedMode()
        {
            checkBox.visibility = View.VISIBLE
            tvDate.visibility = View.GONE
            tvTime.visibility = View.GONE
        }

        fun displayTimeMode()
        {
            checkBox.visibility = View.GONE
            tvDate.visibility = View.VISIBLE
            tvTime.visibility = View.VISIBLE
        }
    }

}