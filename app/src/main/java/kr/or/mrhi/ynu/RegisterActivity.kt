package kr.or.mrhi.ynu

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kr.or.mrhi.ynu.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

         binding.edtRegisterPasswordCheck.setOnFocusChangeListener { view, hasFocus ->
             val pw1 = binding.edtRegisterPassword.text.toString()
             val pw2 = binding.edtRegisterPasswordCheck.text.toString()
             if (!hasFocus && pw1.isNotEmpty() && pw2.isNotEmpty() && pw1 != pw2) {
                 Toast.makeText(this, "비밀번호가 다릅니다.\n확인 후 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
             }
         }

        binding.tvRegisterPosition.setOnClickListener {
            val branch = arrayOf<String>("재학생", "졸업생", "교직원", "기타")
            val eventHandler = object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, answer: Int) {
                    if(answer == DialogInterface.BUTTON_NEGATIVE){
                        binding.tvRegisterPosition.text = ""
                    }
                }
            }
            AlertDialog.Builder(this).run {
                setSingleChoiceItems(branch, 0, object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, index: Int) {
                        binding.tvRegisterPosition.setText(branch[index])
                    }
                })
                setPositiveButton("확인", null)
                setNegativeButton("닫기", eventHandler)
                setCancelable(false)
                show()
            }
        }

        binding.tvRegisterDepartment.setOnClickListener {
            val department = arrayOf<String>(
                "경제금융대학", "공과대학", "사범대학", "사회과학대학",
                "의과대학", "인문과학대학",  "자연과학대학", "소속없음")
            val eventHandler = object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, answer: Int) {
                    if(answer == DialogInterface.BUTTON_NEGATIVE){
                        binding.tvRegisterDepartment.text = ""
                    }
                }
            }
            AlertDialog.Builder(this).run {
                setSingleChoiceItems(department, 0, object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, index: Int) {
                        binding.tvRegisterDepartment.text = department[index]
                    }
                })
                setPositiveButton("확인", null)
                setNegativeButton("닫기", eventHandler)
                setCancelable(false)
                show()
            }
        }

        binding.btnRegisterRegister.setOnClickListener {
            val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
            val id = binding.edtRegisterId.text.toString()
            val password = binding.edtRegisterPassword.text.toString()
            val name = binding.edtRegisterName.text.toString()
            val passwordCheck = binding.edtRegisterPasswordCheck.text.toString()
            val position = binding.tvRegisterPosition.text.toString()
            val department = binding.tvRegisterDepartment.text.toString()
            val phone = binding.edtRegisterPhone.text.toString()
            val email = binding.edtRegisterEmail.text.toString()
            val address = binding.edtRegisterAddress.text.toString()
            var flag = true

            if (id.isEmpty() || password.isEmpty() || passwordCheck.isEmpty() ||
                name.isEmpty() || department.isEmpty() || position.isEmpty() ||
                phone.isEmpty() || email.isEmpty() || address.isEmpty()
            ) {
                Snackbar.make(binding.root, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show()
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

            if (flag) {
                val member = MemberData(id, password, name, position, department, phone, email, address)
                if (!(dbHelper.insertMember(member))) {
                    Snackbar.make(binding.root, "DB에 등록되는 과정중에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    flag = false
                }
            }

            if (flag) {
                finish()
            }
        }

        binding.btnRegisterReturn.setOnClickListener {
            finish()
        }
    }
}