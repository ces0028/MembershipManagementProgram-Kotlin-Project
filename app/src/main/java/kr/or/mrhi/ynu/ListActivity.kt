package kr.or.mrhi.ynu

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kr.or.mrhi.ynu.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    lateinit var customAdapter: CustomAdapter
    var dataList = mutableListOf<MemberData>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
        dataList = dbHelper.selectMemberAll()

        val linearLayoutManager = LinearLayoutManager(this)
        customAdapter = CustomAdapter(dataList)
        binding.memberRecylcerView.layoutManager = linearLayoutManager
        binding.memberRecylcerView.adapter = customAdapter

        binding.btnListReturn.setOnClickListener {
            finish()
        }

        binding.btnListAdd.setOnClickListener {
            val dialog = CustomDialogRegister(binding.root.context)
            dialog.showDialog()
        }
    }

    fun refreshRecyclerViewAdd(memberData: MemberData) : Boolean {
        var flag = false
        val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
        dataList.add(memberData)
        customAdapter.notifyItemInserted(dataList.size)

        if (dbHelper.insertMember(memberData)) {
            flag = true
        }
        return flag
    }

    fun refreshRecyclerViewDrop(memberData: MemberData, position: Int) {
        val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
        Snackbar.make(binding.root, "${memberData.name}님의 회원 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        dbHelper.deleteMember(memberData.id)
        dataList.remove(memberData)
        customAdapter.notifyItemRemoved(position)
    }

    fun refreshRecyclerViewChange(memberData: MemberData, position: Int) {
        val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
        Snackbar.make(binding.root, "${memberData.name}님의 회원정보가 수정되었습니다", Toast.LENGTH_SHORT).show()
        dbHelper.updateMember(memberData)
        dataList.set(position, memberData)
        customAdapter.notifyItemChanged(position)
    }
}
