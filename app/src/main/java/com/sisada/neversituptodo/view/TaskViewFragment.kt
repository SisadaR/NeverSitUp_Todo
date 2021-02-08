package com.sisada.neversituptodo.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sisada.neversituptodo.R
import com.sisada.neversituptodo.databinding.FragmentTaskViewBinding
import com.sisada.neversituptodo.etc.BUNDLE_KEY_TASK
import com.sisada.neversituptodo.etc.DATE_FORMAT_SHORT
import com.sisada.neversituptodo.etc.TIME_FORMAT
import com.sisada.neversituptodo.model.Task
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class TaskViewFragment :  DialogFragment()  {

    interface onTaskViewDialogCloseListener{
        fun taskViewDialogClose(task: Task)
        fun taskViewDialogDeleteTask(task: Task)
    }

    private lateinit var binding : FragmentTaskViewBinding
    private lateinit var taskViewDialogCloseListener: onTaskViewDialogCloseListener
    private lateinit var activeTask:Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            taskViewDialogCloseListener = activity as onTaskViewDialogCloseListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onTaskViewDialogCloseListener")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTaskViewBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUI()
        bindingFunctions()
        return view
    }

    private fun bindingFunctions() {
        binding.buttonMarkDone.setOnClickListener {
            binding.buttonMarkUndone.visibility = View.VISIBLE
            binding.buttonMarkDone.visibility = View.GONE
            activeTask.completed = true
        }

        binding.buttonMarkUndone.setOnClickListener {
            binding.buttonMarkUndone.visibility = View.GONE
            binding.buttonMarkDone.visibility = View.VISIBLE
            activeTask.completed = false
        }

        binding.buttonDelete.setOnClickListener {
            taskViewDialogCloseListener.taskViewDialogDeleteTask(activeTask)
            dismiss()
        }
    }

    private fun setupUI(){

        val bundle = arguments
        val task = bundle?.getParcelable<Task>(BUNDLE_KEY_TASK)
        activeTask = task!!
        binding.tvTaskTitle.text = "Task"
        binding.editTextShowTask.setText(task?.description)

        val zonedDateTime = Instant.parse(task?.createdAt).atZone(ZoneId.systemDefault())
        binding.tvShowTaskDate.text = DateTimeFormatter.ofPattern(DATE_FORMAT_SHORT).format(
            zonedDateTime
        )
        binding.tvShowTaskTime.text = DateTimeFormatter.ofPattern(TIME_FORMAT).format(zonedDateTime)

        binding.buttonMarkDone.visibility = if(task!!.completed) View.GONE else View.VISIBLE
        binding.buttonMarkUndone.visibility = if(task!!.completed) View.VISIBLE else View.GONE
    }

    private fun updateActiveTask()
    {
        activeTask.completed = binding.buttonMarkDone.visibility != View.VISIBLE
        activeTask.description =  binding.editTextShowTask.text.toString()
    }


    override fun onCancel(dialog: DialogInterface) {
        this.updateActiveTask()
        taskViewDialogCloseListener.taskViewDialogClose(activeTask)
        super.onCancel(dialog)
    }



}