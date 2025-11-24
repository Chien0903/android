package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private val studentList = mutableListOf<Pair<String, String>>()
    // Lưu danh sách (Họ tên, MSSV)
    private lateinit var adapter: StudentAdapter

    // ActivityResultLauncher để nhận kết quả từ AddStudentActivity
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val name = result.data?.getStringExtra("name") ?: return@registerForActivityResult
            val mssv = result.data?.getStringExtra("mssv") ?: return@registerForActivityResult
            val position = result.data?.getIntExtra("position", -1) ?: -1

            if (position >= 0) {
                // Chỉnh sửa student
                studentList[position] = Pair(name, mssv)
            } else {
                // Thêm student mới
                studentList.add(Pair(name, mssv))
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewStudents)

        // Adapter hiển thị danh sách sinh viên với nút xóa và sửa
        adapter = StudentAdapter(
            studentList,
            onEditClick = { position ->
                // Xử lý sửa student
                val intent = Intent(this, AddStudentActivity::class.java)
                intent.putExtra("name", studentList[position].first)
                intent.putExtra("mssv", studentList[position].second)
                intent.putExtra("position", position)
                addStudentLauncher.launch(intent)
            },
            onDeleteClick = { position ->
                // Xử lý xóa student
                studentList.removeAt(position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Đã xóa student", Toast.LENGTH_SHORT).show()
            }
        )
        listView.adapter = adapter

        // Đăng ký context menu (vẫn giữ để có thể edit)
        registerForContextMenu(listView)
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

    // Tạo ContextMenu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    // Xử lý ContextMenu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.action_edit -> {
                // Mở Activity chỉnh sửa
                val intent = Intent(this, AddStudentActivity::class.java)
                intent.putExtra("name", studentList[info.position].first)
                intent.putExtra("mssv", studentList[info.position].second)
                intent.putExtra("position", info.position)
                addStudentLauncher.launch(intent)
            }
            R.id.action_remove -> {
                // Xóa sinh viên (cũng có thể dùng context menu)
                studentList.removeAt(info.position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Đã xóa student", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onContextItemSelected(item)
    }

    // Custom Adapter để hiển thị student với nút xóa và sửa
    private class StudentAdapter(
        private val students: List<Pair<String, String>>,
        private val onEditClick: (Int) -> Unit,
        private val onDeleteClick: (Int) -> Unit
    ) : BaseAdapter() {

        override fun getCount(): Int = students.size

        override fun getItem(position: Int): Pair<String, String> = students[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_student, parent, false)

            val student = students[position]
            val textView = view.findViewById<TextView>(R.id.textViewStudent)
            val buttonEdit = view.findViewById<Button>(R.id.buttonEdit)
            val buttonDelete = view.findViewById<Button>(R.id.buttonDelete)

            textView.text = "${student.first} - ${student.second}"

            buttonEdit.setOnClickListener {
                onEditClick(position)
            }

            buttonDelete.setOnClickListener {
                onDeleteClick(position)
            }

            return view
        }
    }
}
result.data