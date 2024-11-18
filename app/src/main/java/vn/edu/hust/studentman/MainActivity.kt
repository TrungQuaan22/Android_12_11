package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

  private lateinit var studentAdapter: StudentAdapter
  private lateinit var students: MutableList<Student>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Khởi tạo danh sách sinh viên
    students = mutableListOf(
      Student("Nguyễn Văn A", "12345"),
      Student("Trần Thị B", "67890")
    )

    // Cài đặt RecyclerView
    val recyclerView: RecyclerView = findViewById(R.id.recycler_view_students)
    studentAdapter = StudentAdapter(students, this)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = studentAdapter

    // Xử lý nút Add New
    val addNewButton: Button = findViewById(R.id.btn_add_new)
    addNewButton.setOnClickListener {
      showAddNewDialog()
    }
  }

  private fun showAddNewDialog() {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)

    AlertDialog.Builder(this)
      .setTitle("Thêm sinh viên mới")
      .setView(dialogView)
      .setPositiveButton("Thêm") { _, _ ->
        // Thêm sinh viên mới vào danh sách
        val newStudent = Student(editName.text.toString(), editId.text.toString())
        students.add(newStudent)
        studentAdapter.notifyItemInserted(students.size - 1)
      }
      .setNegativeButton("Hủy", null)
      .show()
  }
}
