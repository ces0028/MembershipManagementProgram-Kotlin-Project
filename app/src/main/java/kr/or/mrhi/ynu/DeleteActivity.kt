package kr.or.mrhi.ynu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kr.or.mrhi.ynu.databinding.ActivityDeleteBinding

class DeleteActivity : AppCompatActivity() {
    lateinit var binding: ActivityDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDeleteDelete.setOnClickListener {
            val id = binding.edtDeleteId.text.toString()
            var flag = true

            val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)

            if (id.isEmpty()) {
                Snackbar.make(binding.root, "먼저 아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
                flag = false
            }

            if (!dbHelper.deleteMember(id)) {
                flag = false
            }

            if (flag) {
                Snackbar.make(binding.root, "회원 정보를 삭제했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Snackbar.make(binding.root, "회원 정보를 삭제하는데 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDeleteReturn.setOnClickListener {
             finish()
        }
    }
}