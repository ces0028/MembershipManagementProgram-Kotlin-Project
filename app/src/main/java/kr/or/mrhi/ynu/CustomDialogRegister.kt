package kr.or.mrhi.ynu

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import kr.or.mrhi.ynu.databinding.CustomDialogRegisterBinding

class CustomDialogRegister(val context: Context) {
    val dialog = Dialog(context)

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDialog() {
        val binding = CustomDialogRegisterBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()

        binding.tvDialogRegisterPosition.setOnClickListener {
            val branch = arrayOf<String>("재학생", "졸업생", "교직원", "기타")
            val eventHandler = object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, answer: Int) {
                    if (answer == DialogInterface.BUTTON_NEGATIVE){
                        binding.tvDialogRegisterPosition.text = ""
                    }
                }
            }
            AlertDialog.Builder(context).run {
                setSingleChoiceItems(branch, 0, object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, index: Int) {
                        binding.tvDialogRegisterPosition.setText(branch[index])
                    }
                })
                setPositiveButton("확인", null)
                setNegativeButton("닫기", eventHandler)
                setCancelable(false)
                show()
            }
        }

        binding.tvDialogRegisterDepartment.setOnClickListener {
            val department = arrayOf<String>(
                "경제금융대학", "공과대학", "사범대학", "사회과학대학",
                "의과대학", "인문과학대학",  "자연과학대학", "소속없음")
            val eventHandler = object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, answer: Int) {
                    if (answer == DialogInterface.BUTTON_NEGATIVE){
                        binding.tvDialogRegisterDepartment.text = ""
                    }
                }
            }
            AlertDialog.Builder(context).run {
                setSingleChoiceItems(department, 0, object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, index: Int) {
                        binding.tvDialogRegisterDepartment.text = department[index]
                    }
                })
                setPositiveButton("확인", null)
                setNegativeButton("닫기", eventHandler)
                setCancelable(false)
                show()
            }
        }

        binding.btnDialogRegisterClose.setOnClickListener {
            dialog.dismiss()
        }

        binding.btnDialogRegisterJoin.setOnClickListener {
            var flag = true
            val dbHelper = DBHelper(context, "memberDB.db", null, 1)

            val id = binding.edtDialogRegisterId.text.toString()
            val password = binding.edtDialogRegisterPassword.text.toString()
            val passwordCheck = binding.edtDialogRegisterPasswordCheck.text.toString()
            val name = binding.edtDialogRegisterName.text.toString()
            val position = binding.tvDialogRegisterPosition.text.toString()
            val department = binding.tvDialogRegisterDepartment.text.toString()
            val phone = binding.edtDialogRegisterPhone.text.toString()
            val email = binding.edtDialogRegisterEmail.text.toString()
            val address = binding.edtDialogRegisterAddress.text.toString()

            binding.edtDialogRegisterPasswordCheck.setOnFocusChangeListener { view, hasFocus ->
                val pw1 = binding.edtDialogRegisterPassword.text.toString()
                val pw2 = binding.edtDialogRegisterPasswordCheck.text.toString()
                if (!hasFocus && pw1.isNotEmpty() && pw2.isNotEmpty() && pw1 != pw2) {
                    Snackbar.make(binding.root, "비밀번호가 다릅니다.\n확인 후 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            if (id.isEmpty() || password.isEmpty() || passwordCheck.isEmpty() ||
                name.isEmpty() || position.isEmpty() || department.isEmpty() ||
                phone.isEmpty() || email.isEmpty() || address.isEmpty()
            ) {
                Snackbar.make(binding.root, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                flag = false
            }

            if (password != passwordCheck) {
                Snackbar.make(binding.root, "비밀번호가 다릅니다.\n확인 후 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                flag = false
            }

            if (dbHelper.selectCheckId(id)) {
                Snackbar.make(binding.root, "해당 아이디는 이미 사용중입니다.", Toast.LENGTH_SHORT).show()
                flag = false
            }

            val member = MemberData(id, password, name, position, department, phone, email, address)
            flag = (context as ListActivity).refreshRecyclerViewAdd(member)

            if (flag) {
                dialog.dismiss()
            } else {
                Snackbar.make(binding.root, "DB에 등록되는 과정중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}