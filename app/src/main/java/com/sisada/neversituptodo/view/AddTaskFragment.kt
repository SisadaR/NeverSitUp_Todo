package com.sisada.neversituptodo.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.core.graphics.toColor
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.sisada.neversituptodo.R
import com.sisada.neversituptodo.databinding.FragmentAddTaskBinding
import com.sisada.neversituptodo.etc.DATE_FORMAT
import com.sisada.neversituptodo.etc.TIME_FORMAT

import java.util.*


class AddTaskFragment : DialogFragment()  {

    private lateinit var binding : FragmentAddTaskBinding
    private lateinit var selectedDate:Date
    private lateinit var selectedTime:Date

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

        binding.toggleGroupCategory.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if(isChecked){
                group.findViewById<Button>(checkedId).setBackgroundColor(  resources.getColor( R.color.white))
                Log.d("chackedID","chackedID:$checkedId")
            }
            else{
                group.findViewById<Button>(checkedId).setBackgroundColor(  resources.getColor( R.color.gray))
                Log.d("chackedID","UN-chackedID:$checkedId")
                // Toast.makeText(context!!,"deselect $checkedId",Toast.LENGTH_SHORT).show()
            }
        }
        binding.toggleGroupCategory.check( R.id.button_toggle_cat_1)

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
                TimePickerDialog.OnTimeSetListener{ timePickerView, hour, minute->

                    var time = Calendar.getInstance(TimeZone.getDefault())
                    time.set(Calendar.HOUR,hour)
                    time.set(Calendar.MINUTE,minute)
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

        return view
        //return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onStart() {

        super.onStart()

        val width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350f , resources.displayMetrics)
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width.toInt(), height)
    }

    private fun setupUI(){

        val calendar = Calendar.getInstance(TimeZone.getDefault())
        selectedDate = calendar.time
        binding.labelPickerDate.text = SimpleDateFormat(DATE_FORMAT).format(selectedDate)

        val time = Calendar.getInstance(TimeZone.getDefault())
        time.set(Calendar.HOUR,0)
        time.set(Calendar.MINUTE,0)
        selectedTime = time.time
        binding.labelPickerTime.text = SimpleDateFormat(TIME_FORMAT).format(selectedTime)

    }
}