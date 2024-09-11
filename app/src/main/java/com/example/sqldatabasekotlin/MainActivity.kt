package com.example.sqldatabasekotlin

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqldatabasekotlin.databinding.ActivityMainBinding
import com.example.sqldatabasekotlin.db.MyDBHelper
import com.example.sqldatabasekotlin.db.TodoTable

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var todos = ArrayList<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val todoAdapter = ArrayAdapter<Todo>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            todos
        )

        val db = MyDBHelper(this).writableDatabase

        binding.lvTodos.adapter = todoAdapter

        // Function to refresh the todo list
        fun refreshTodoList() {
            val todoList = TodoTable.getAllTodos(db) // Get updated todo list from DB
            Log.d("MySQLiteDatabaseKotlin", "Todo list from DB: $todoList")
            todos.clear() // Clear the old list
            todos.addAll(todoList) // Add all new items from DB
            todoAdapter.notifyDataSetChanged() // Notify adapter to refresh UI
        }

        // Set the button click listener to add a new todo
        binding.btnAddTodo.setOnClickListener {
            Log.d("MySQLiteDatabaseKotlin", "btnAddTodo.setOnClickListener : Started")

            val newTodo = Todo(
                binding.etNewTodo.text.toString(),
                false
            )
            TodoTable.insertTodo(db, newTodo) // Insert the new todo into the database

            Log.d("MySQLiteDatabaseKotlin", "btnAddTodo.setOnClickListener : Ended")
            refreshTodoList() // Refresh the list to display the newly added todo
        }

        // Initially refresh the list to show any existing todos
        refreshTodoList()
    }
}
