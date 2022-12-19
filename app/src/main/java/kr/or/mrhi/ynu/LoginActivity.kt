package kr.or.mrhi.ynu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kr.or.mrhi.ynu.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun viewOnClick(view: View) {
        when(view.id) {
            R.id.btnLoginLogin -> {
                val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
                val id = binding.edtLoginId.text.toString()
                val password = binding.edtLoginPassword.text.toString()
                binding.edtLoginId.setText("")
                binding.edtLoginPassword.setText("")
                var flag = true

                if (id.isEmpty() || password.isEmpty()) {
                    Snackbar.make(binding.root, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    flag = false
                }

                if (!dbHelper.selectLogin(id, password)) {
                    Log.d("kr.or.mrhi.ynu", "${dbHelper.selectLogin(id, password)}")
                    flag = false
                }

                if (flag) {
                    val intent = Intent(this, ManageActivity::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(binding.root, "아이디와 비밀번호를 확인 후 다시 로그인해주십시오.", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.btnLoginRegister -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }

            R.id.btnLoginManage -> {
                val intent = Intent(this, ManageActivity::class.java)
                startActivity(intent)
            }
        }
    }
}