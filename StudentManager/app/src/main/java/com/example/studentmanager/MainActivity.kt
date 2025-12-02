package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private val studentList = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter

    // ActivityResultLauncher để nhận kết quả từ AddStudentActivity
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val student = result.data?.getParcelableExtra<Student>("student")
            if (student != null) {
                studentList.add(student)
                adapter.notifyDataSetChanged()
            }
        }
    }

    // ActivityResultLauncher để nhận kết quả từ DetailActivity (cập nhật)
    private val detailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val student = result.data?.getParcelableExtra<Student>("student")
            val position = result.data?.getIntExtra("position", -1) ?: -1
            if (student != null && position >= 0) {
                studentList[position] = student
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewStudents)

        // Adapter hiển thị danh sách sinh viên
        adapter = StudentAdapter(studentList)
        listView.adapter = adapter

        // Xử lý click vào item để mở DetailActivity
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("student", studentList[position])
            intent.putExtra("position", position)
            detailLauncher.launch(intent)
        }
    }

    // Tạo OptionMenu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Xử lý OptionMenu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                // Mở Activity thêm sinh viên
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Custom Adapter để hiển thị danh sách sinh viên (MSSV và Họ tên)
    private class StudentAdapter(
        private val students: List<Student>
    ) : BaseAdapter() {

        override fun getCount(): Int = students.size

        override fun getItem(position: Int): Student = students[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_student, parent, false)

            val student = students[position]
            val textView = view.findViewById<TextView>(R.id.textViewStudent)

            // Hiển thị MSSV và Họ tên
            textView.text = "${student.mssv} - ${student.hoTen}"

            return view
        }
    }
}