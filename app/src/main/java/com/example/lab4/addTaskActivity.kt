package com.example.lab4

import TaskViewModel
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab4.databinding.ActivityAddTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class addTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskViewModel(this)

        val priorityOptions = arrayOf("High", "Medium", "Low")
        val priorityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priorityOptions)
        binding.addPriority.adapter = priorityAdapter

        binding.saveButton.setOnClickListener{
            val title = binding.addTitle.text.toString()
            val content = binding.addContent.text.toString()
            val priority = binding.addPriority.selectedItem.toString()
            val year = binding.deadlineDatePicker.year
            val month = binding.deadlineDatePicker.month
            val dayOfMonth = binding.deadlineDatePicker.dayOfMonth
            val deadline = Calendar.getInstance()
            deadline.set(year, month, dayOfMonth)
            val task = Task(0, title, content, priority, deadline.time)
            CoroutineScope(Dispatchers.Main).launch{
                db.insertTask(task)
            }
            finish()
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()
        }
    }
}