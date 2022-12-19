package kr.or.mrhi.ynu

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kr.or.mrhi.ynu.databinding.CustomDialogModifyDetailBinding
import kr.or.mrhi.ynu.databinding.CustomDialogShowDetailBinding
import kr.or.mrhi.ynu.databinding.ItemMemberBinding

class CustomAdapter(val dataList: MutableList<MemberData>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {
    lateinit var showDialog : AlertDialog
    lateinit var modifyDialog : AlertDialog

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val customViewHolder = CustomViewHolder(binding)

        customViewHolder.itemView.setOnClickListener {
            val showDetailBinding = CustomDialogShowDetailBinding.inflate(LayoutInflater.from(parent.context))
            val builderShow = AlertDialog.Builder(parent.context)
            val itemPosition = customViewHolder.adapterPosition
            val member = dataList.get(itemPosition)
            showDetailBinding.tvShowId.text = member.id
            showDetailBinding.tvShowPassword.text = member.password
            showDetailBinding.tvShowName.text = member.name
            showDetailBinding.tvShowPosition.text = member.position
            showDetailBinding.tvShowDepartment.text = member.department
            showDetailBinding.tvShowPhone.text = member.phone
            showDetailBinding.tvShowEmail.text = member.email
            showDetailBinding.tvShowAddress.text = member.address
            builderShow.setView(showDetailBinding.root)
            showDialog = builderShow.create()
            showDialog.setCanceledOnTouchOutside(false)
            showDialog.setCancelable(false)
            showDialog.show()

            showDetailBinding.btnShowClose.setOnClickListener {
                showDialog.dismiss()
            }

            showDetailBinding.btnShowModify.setOnClickListener {
                val showModifyBinding = CustomDialogModifyDetailBinding.inflate(LayoutInflater.from(parent.context))
                showModifyBinding.tvDialogModifyId.text = member.id
                showModifyBinding.edtDialogModifyPassword.setText(member.password)
                showModifyBinding.edtDialogModifyName.setText(member.name)
                showModifyBinding.tvDialogModifyPosition.text = member.position
                showModifyBinding.tvDialogModifyDepartment.text = member.department
                showModifyBinding.edtDialogModifyPhone.setText(member.phone)
                showModifyBinding.edtDialogModifyEmail.setText(member.email)
                showModifyBinding.edtDialogModifyAddress.setText(member.address)
                builderShow.setView(showModifyBinding.root)
                modifyDialog = builderShow.create()
                modifyDialog.setCanceledOnTouchOutside(false)
                modifyDialog.setCancelable(false)
                modifyDialog.show()


                showModifyBinding.tvDialogModifyPosition.setOnClickListener {
                    val branch = arrayOf<String>("재학생", "졸업생", "교직원", "기타")
                    val eventHandler = object: DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, answer: Int) {
                            if(answer == DialogInterface.BUTTON_NEGATIVE){
                                showModifyBinding.tvDialogModifyPosition.text = ""
                            }
                        }
                    }
                    android.app.AlertDialog.Builder(parent.context).run {
                        setSingleChoiceItems(branch, 0, object: DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, index: Int) {
                                showModifyBinding.tvDialogModifyPosition.text = branch[index]
                            }
                        })
                        setPositiveButton("확인", null)
                        setNegativeButton("닫기", eventHandler)
                        setCancelable(false)
                        show()
                    }
                }

                showModifyBinding.tvDialogModifyDepartment.setOnClickListener {
                    val department = arrayOf<String>(
                        "경제금융대학", "공과대학", "사범대학", "사회과학대학",
                        "의과대학", "인문과학대학",  "자연과학대학", "소속없음")
                    val eventHandler = object: DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, answer: Int) {
                            if(answer == DialogInterface.BUTTON_NEGATIVE){
                                showModifyBinding.tvDialogModifyDepartment.text = ""
                            }
                        }
                    }
                    android.app.AlertDialog.Builder(parent.context).run {
                        setSingleChoiceItems(department, 0, object: DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, index: Int) {
                                showModifyBinding.tvDialogModifyDepartment.text = department[index]
                            }
                        })
                        setPositiveButton("확인", null)
                        setNegativeButton("닫기", eventHandler)
                        setCancelable(false)
                        show()
                    }
                }

                showModifyBinding.btnDialogModifySave.setOnClickListener {
                    val id = showModifyBinding.tvDialogModifyId.text.toString()
                    val password = showModifyBinding.edtDialogModifyPassword.text.toString()
                    val name = showModifyBinding.edtDialogModifyName.text.toString()
                    val position = showModifyBinding.tvDialogModifyPosition.text.toString()
                    val department = showModifyBinding.tvDialogModifyDepartment.text.toString()
                    val phone = showModifyBinding.edtDialogModifyPhone.text.toString()
                    val email = showModifyBinding.edtDialogModifyEmail.text.toString()
                    val address = showModifyBinding.edtDialogModifyAddress.text.toString()
                    val memberData = MemberData(id, password, name, position, department, phone, email, address)
                    (parent.context as ListActivity).refreshRecyclerViewChange(memberData, itemPosition)

                    showDetailBinding.tvShowId.text = id
                    showDetailBinding.tvShowPassword.text = password
                    showDetailBinding.tvShowName.text = name
                    showDetailBinding.tvShowPosition.text = position
                    showDetailBinding.tvShowDepartment.text = department
                    showDetailBinding.tvShowPhone.text = phone
                    showDetailBinding.tvShowEmail.text = email
                    showDetailBinding.tvShowAddress.text = address

                    modifyDialog.dismiss()
                }

                showModifyBinding.btnDialogModifyClose.setOnClickListener {
                    modifyDialog.dismiss()
                }
            }
        }

        customViewHolder.itemView.setOnLongClickListener {
            val eventHandler = object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, answer: Int) {
                    if (answer == DialogInterface.BUTTON_POSITIVE){
                        val position: Int = customViewHolder.adapterPosition
                        val member = dataList.get(position)
                        (parent.context as ListActivity).refreshRecyclerViewDrop(member, position)
                    }
                }
            }
            AlertDialog.Builder(parent.context).run {
                setMessage("해당 회원정보를 삭제하시겠습니까?")
                setPositiveButton("삭제", eventHandler)
                setNegativeButton("취소", null)
                show()
            }
            true
        }
        return customViewHolder
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val binding = holder.binding
        val member = dataList.get(position)
        var image = 0
        binding.tvMemberId.text = member.id
        binding.tvMemberName.text = member.name
        binding.tvMemberPosition.text = member.position
        binding.tvMemberDepartment.text = member.department
        image = if (member.position == "재학생") {
            R.drawable.ic_student
        } else if (member.position == "졸업생") {
            R.drawable.ic_alumni
        } else if (member.position == "교직원") {
            R.drawable.ic_professor
        } else {
            R.drawable.ic_other
        }
        binding.ivPicture.setImageResource(image)
    }

    class CustomViewHolder(val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root)
}