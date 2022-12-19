package kr.or.mrhi.ynu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kr.or.mrhi.ynu.databinding.ActivityManageBinding

class ManageActivity : AppCompatActivity() {
    lateinit var binding: ActivityManageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun viewOnClick(view: View) {
        when(view.id) {
            R.id.btnManageAdd -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }

            R.id.btnManageDelete -> {
                val intent = Intent(this, DeleteActivity::class.java)
                startActivity(intent)
            }

            R.id.btnManageModify -> {
                val intent = Intent(this, UpdateActivity::class.java)
                startActivity(intent)
            }

            R.id.btnManageList -> {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            }

            R.id.btnManageReturn -> finish()
        }
    }
}