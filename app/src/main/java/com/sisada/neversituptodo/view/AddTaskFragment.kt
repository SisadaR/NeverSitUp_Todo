package com.sisada.neversituptodo.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.sisada.neversituptodo.R
import com.sisada.neversituptodo.api.Status
import com.sisada.neversituptodo.databinding.FragmentAddTaskBinding
import com.sisada.neversituptodo.model.Task
import com.sisada.neversituptodo.viewmodel.TaskViewModel
import androidx.lifecycle.Observer
import com.sisada.neversituptodo.etc.*
import java.util.*


class AddTaskFragment : DialogFragment() {

    interface onAddTaskListener{
        fun taskAdded(task: Task)
    }


    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var selectedDate: Date
    private lateinit var selectedTime: Date
    private lateinit var addTaskListener: onAddTaskListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            addTaskListener = activity as onAddTaskListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement onAddTaskListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setGravity(Gravity.RIGHT)

        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val view = binding.root


        setupUI()


        return view
        //return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    private fun onClickAddTask() {
        viewModel = TaskViewModel()
        var waitDialog = WaitDialog(activity!!)
        waitDialog.show()

        var taskDesc = binding.editTextTextMultiLine.text.toString()

        viewModel.addTask(SharedInfo.getToken(activity!!), taskDesc).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Log.d("addTask", "Success " + resource.data.toString())
                        waitDialog.dismiss()

                        resource.data?.let { response ->
                            if (response.success) {
                                addTaskListener.taskAdded(response.data)
                                dismiss()
                            } else {
                                AlertDialog.Builder(activity!!)
                                    .setTitle("Something Wrong")
                                    .setMessage("can't add task")
                                    .setPositiveButton(android.R.string.yes, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show()
                            }
                        }

                    }
                    Status.ERROR -> {
                        Log.d("addTask", "ERR")
                        waitDialog.dismiss()

                        AlertDialog.Builder(activity!!)
                            .setTitle("Something Wrong")
                            .setMessage(resource.message)
                            .setPositiveButton(android.R.string.yes, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                    Status.LOADING -> {
                        Log.d("addTask", "Loading")
                    }
                }
            }
        })
    }

    override fun onStart() {

        super.onStart()

        val width =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350f, resources.displayMetrics)
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width.toInt(), height)
    }

    private fun setupUI() {

        val calendar = Calendar.getInstance(TimeZone.getDefault())
        selectedDate = calendar.time
        binding.labelPickerDate.text = SimpleDateFormat(DATE_FORMAT).format(selectedDate)

        val time = Calendar.getInstance(TimeZone.getDefault())
        time.set(Calendar.HOUR, 0)
        time.set(Calendar.MINUTE, 0)
        selectedTime = time.time
        binding.labelPickerTime.text = SimpleDateFormat(TIME_FORMAT).format(selectedTime)

        binding.buttonAddTask.setOnClickListener { onClickAddTask() }

        binding.toggleGroupCategory.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                group.findViewById<Button>(checkedId)
                    .setBackgroundColor(resources.getColor(R.color.white))
                Log.d("chackedID", "chackedID:$checkedId")
            } else {
                group.findViewById<Button>(checkedId)
                    .setBackgroundColor(resources.getColor(R.color.gray))
                Log.d("chackedID", "UN-chackedID:$checkedId")
                // Toast.makeText(context!!,"deselect $checkedId",Toast.LENGTH_SHORT).show()
            }
        }
        binding.toggleGroupCategory.check(R.id.button_toggle_cat_1)

        binding.labelPickerDate.setOnClickListener {

            val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())
            val datePickerOnDataSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
                    binding.labelPickerDate.text = (sdf.format(calendar.time))

                    selectedDate = calendar.time
                }

            val dialog = DatePickerDialog(
                context!!,
                datePickerOnDataSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }

        binding.labelPickerTime.setOnClickListener {


            val timePickerOnDataSetListener =
                TimePickerDialog.OnTimeSetListener { timePickerView, hour, minute ->

                    var time = Calendar.getInstance(TimeZone.getDefault())
                    time.set(Calendar.HOUR, hour)
                    time.set(Calendar.MINUTE, minute)
                    binding.labelPickerDate.text = SimpleDateFormat(TIME_FORMAT).format(time)
                }

            val timePicker = TimePickerDialog(
                context,
                timePickerOnDataSetListener,
                0,
                0,
                false
            )

            timePicker.show()

        }
    }
}