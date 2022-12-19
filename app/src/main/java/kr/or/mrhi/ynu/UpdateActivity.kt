package kr.or.mrhi.ynu

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kr.or.mrhi.ynu.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    var member: MemberData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnModifySearchId.setOnClickListener {
            val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
            val id = binding.edtModifyId.text.toString()
            var flag = true

            if (id.isEmpty()) {
                Snackbar.make(binding.root, "먼저 아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
                flag = false
            }

            if (flag) {
                member = dbHelper.selectId(id)
                if (member != null) {
                    binding.edtModifyPassword.setText(member?.password)
                    binding.edtModifyName.setText(member?.name)
                    binding.tvModifyPosition.text = member?.position
                    binding.tvModifyDepartment.text = member?.department
                    binding.edtModifyPhone.setText(member?.phone)
                    binding.edtModifyEmail.setText(member?.email)
                    binding.edtModifyAddress.setText(member?.address)
                } else {
                    Snackbar.make(binding.root, "해당 아이디를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvModifyPosition.setOnClickListener {
            val branch = arrayOf<String>("재학생", "졸업생", "교직원", "기타")
            val eventHandler = object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, answer: Int) {
                    if(answer == DialogInterface.BUTTON_NEGATIVE){
                        binding.tvModifyPosition.text = ""
                    }
                }
            }
            AlertDialog.Builder(this).run {
                setSingleChoiceItems(branch, 0, object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, index: Int) {
                        binding.tvModifyPosition.text = branch[index]
                    }
                })
                setPositiveButton("확인", null)
                setNegativeButton("닫기", eventHandler)
                setCancelable(false)
                show()
            }
        }

        binding.tvModifyDepartment.setOnClickListener {
            val department = arrayOf<String>(
                "경제금융대학", "공과대학", "사범대학", "사회과학대학",
                "의과대학", "인문과학대학",  "자연과학대학", "소속없음")
            val eventHandler = object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, answer: Int) {
                    if(answer == DialogInterface.BUTTON_NEGATIVE){
                        binding.tvModifyDepartment.text = ""
                    }
                }
            }
            AlertDialog.Builder(this).run {
                setSingleChoiceItems(department, 0, object: DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, index: Int) {
                        binding.tvModifyDepartment.text = department[index]
                    }
                })
                setPositiveButton("확인", null)
                setNegativeButton("닫기", eventHandler)
                setCancelable(false)
                show()
            }
        }

        binding.btnModifyModify.setOnClickListener {
            val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
            val password = binding.edtModifyPassword.text.toString()
            val name = binding.edtModifyName.text.toString()
            val position = binding.tvModifyPosition.text.toString()
            val department = binding.tvModifyDepartment.text.toString()
            val phone = binding.edtModifyPhone.text.toString()
            val email = binding.edtModifyEmail.text.toString()
            val address = binding.edtModifyAddress.text.toString()
            var flag = true

            if (!(password.isNotBlank() && name.isNotBlank() && position.isNotBlank() && department.isNotBlank()
                        && phone.isNotBlank() && email.isNotBlank() && address.isNotBlank())) {
                flag = false
            }

            if (member != null && flag) {
                member!!.password = password
                member!!.name = name
                member!!.position = position
                member!!.department = department
                member!!.phone = phone
                member!!.email = email
                member!!.address = address

                flag = dbHelper.updateMember(member)
                if (flag) {
                    Snackbar.make(binding.root, "회원정보가 정상적으로 수정되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(binding.root, "회원정보를 수정하는 데 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(binding.root, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            finish()
        }

        binding.btnModifyReturn.setOnClickListener {
            finish()
        }
    }
}