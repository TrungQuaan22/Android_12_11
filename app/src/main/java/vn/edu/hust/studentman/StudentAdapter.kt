package vn.edu.hust.studentman

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(
  private var students: MutableList<Student>,
  private val context: Context
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.layout_student_item, parent, false)
    return StudentViewHolder(view)
  }

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.studentName.text = student.studentName
    holder.studentId.text = student.studentId

    // Xử lý nút Edit
    holder.editButton.setOnClickListener {
      showEditDialog(position)
    }

    // Xử lý nút Delete
    holder.deleteButton.setOnClickListener {
      AlertDialog.Builder(context)
        .setTitle("Xóa sinh viên")
        .setMessage("Bạn có chắc chắn muốn xóa sinh viên này không?")
        .setPositiveButton("Xóa") { _, _ ->
          // Lưu tạm thông tin sinh viên bị xóa để khôi phục
          val deletedStudent = students[position]
          val deletedIndex = position

          students.removeAt(position)
          notifyItemRemoved(position)

          // Hiển thị Snackbar với tùy chọn Undo
          Snackbar.make(holder.itemView, "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
            .setAction("Hoàn tác") {
              // Hoàn tác xóa
              students.add(deletedIndex, deletedStudent)
              notifyItemInserted(deletedIndex)
            }.show()
        }
        .setNegativeButton("Hủy", null)
        .show()
    }
  }

  override fun getItemCount(): Int = students.size

  private fun showEditDialog(position: Int) {
    val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)

    // Điền thông tin hiện tại của sinh viên
    editName.setText(students[position].studentName)
    editId.setText(students[position].studentId)

    AlertDialog.Builder(context)
      .setTitle("Chỉnh sửa thông tin sinh viên")
      .setView(dialogView)
      .setPositiveButton("Lưu") { _, _ ->
        // Cập nhật thông tin sinh viên trong danh sách
        students[position].studentName = editName.text.toString()
        students[position].studentId = editId.text.toString()
        notifyItemChanged(position)
      }
      .setNegativeButton("Hủy", null)
      .show()
  }


  class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val studentName: TextView = itemView.findViewById(R.id.text_student_name)
    val studentId: TextView = itemView.findViewById(R.id.text_student_id)
    val editButton: ImageView = itemView.findViewById(R.id.image_edit)
    val deleteButton: ImageView = itemView.findViewById(R.id.image_remove)
  }
}
