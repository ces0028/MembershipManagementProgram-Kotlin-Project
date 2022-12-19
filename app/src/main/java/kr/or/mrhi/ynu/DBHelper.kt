package kr.or.mrhi.ynu

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.sql.SQLException

class DBHelper(val context: Context?, val name: String?, val factory: SQLiteDatabase.CursorFactory?, val version: Int)
    : SQLiteOpenHelper(context, name, factory, version){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE memberTBL(" +
                "id TEXT PRIMARY KEY," +
                "password TEXT NOT NULL," +
                "name TEXT NOT NULL," +
                "position TEXT NOT NULL," +
                "department TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "address TEXT NOT NULL);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS member_Tbl")
        onCreate(db)
    }

    fun insertMember(memberData: MemberData): Boolean{
        val db: SQLiteDatabase = writableDatabase
        var flag = false
        try {
            db.execSQL("INSERT INTO memberTBL VALUES ('${memberData.id}', '${memberData.password}'," +
                    " '${memberData.name}', '${memberData.position}', '${memberData.department}', " +
                    "'${memberData.phone}', '${memberData.email}', '${memberData.address}') ")
            Log.d("kr.or.mrhi.ynu", "INSERT SUCCESS")
            flag = true
        } catch (e: SQLException) {
            Log.d("kr.or.mrhi.ynu", "INSERT ${memberData.id}, ${memberData.name} ERROR ${e.printStackTrace()}")
            flag = false
        } finally {
            db.close()
        }
        return flag
    }

    fun selectCheckId(id: String) : Boolean {
        val db: SQLiteDatabase = readableDatabase
        var cursor: Cursor? = null
        var flag = false
        try {
            cursor = db.rawQuery("SELECT id FROM memberTBL WHERE id = '${id}'", null)
            if (cursor.moveToFirst()) {
                if(cursor.getString(0).equals(id)) flag = true
                Log.d("kr.or.mrhi.ynu", "SELECT CHECK ID SUCCESS ${id}")
            }
        } catch (e: SQLException) {
            Log.d("kr.or.mrhi.ynu", "SELECT CHECK ID ${id} ERROR ${e.printStackTrace()}")
            flag = false
        } finally {
            cursor?.close()
        }
        return flag
    }

    fun selectLogin(id: String, password: String) : Boolean {
        val db: SQLiteDatabase = readableDatabase
        var cursor: Cursor? = null
        var flag = false
        try {
            cursor = db.rawQuery("SELECT id, password FROM memberTBL WHERE id = '${id}' AND password = '${password}' ", null)
            if (cursor.moveToNext()) {
                if(cursor.getString(0).equals(id) && cursor.getString(1).equals(password)) flag = true
                Log.d("kr.or.mrhi.ynu", "SELECT LOGIN SUCCESS ${id}, ${password}")
            }
        } catch (e: SQLException) {
            Log.d("kr.or.mrhi.ynu", "SELECT LOGIN ${id}, ${password} ERROR ${e.printStackTrace()}")
            flag = false
        } finally {
            cursor?.close()
        }
        return flag
    }

    fun selectMemberAll(): MutableList<MemberData> {
        val db: SQLiteDatabase = readableDatabase
        var cursor: Cursor? = null
        val dataList: MutableList<MemberData> = mutableListOf<MemberData>()
        try {
            cursor = db.rawQuery("SELECT * FROM memberTBL", null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val member = MemberData(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7))
                    dataList.add(member)
                    Log.d("kr.or.mrhi.ynu", "SELECT ALL SUCCESS")
                }
            }
        } catch (e: SQLException) {
            Log.d("kr.or.mrhi.ynu", "SELECT ALL ERROR ${e.printStackTrace()}")
        } finally {
            cursor?.close()
        }
        return dataList
    }

    fun selectId(id: String) : MemberData? {
        val db: SQLiteDatabase = readableDatabase
        var cursor: Cursor? = null
        var member: MemberData? = null
        try {
            cursor = db.rawQuery("SELECT * FROM memberTBL WHERE id = '${id}'", null)
            if (cursor.moveToFirst()) {
                member = MemberData(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7))
                Log.d("kr.or.mrhi.ynu", "SELECT ID SUCCESS ${id}")
            }
        } catch (e: SQLException) {
            Log.d("kr.or.mrhi.ynu", "SELECT ID ${id} ERROR ${e.printStackTrace()}")
        }
        cursor?.close()
        return member
    }

    fun deleteMember(id: String) : Boolean {
        val db: SQLiteDatabase = writableDatabase
        var flag = false
        try {
            db.execSQL("DELETE FROM memberTBL WHERE id = '${id}'")
            Log.d("kr.or.mrhi.ynu", "DELETE SUCCESS")
            flag = true
        } catch (e: SQLException) {
            Log.d("kr.or.mrhi.ynu", "DELETE ${id} ERROR ${e.printStackTrace()}")
            flag = false
        } finally {
            db.close()
        }
        return flag
    }

    fun updateMember(memberData: MemberData?) : Boolean {
        val db: SQLiteDatabase = writableDatabase
        var flag = false
        if (memberData != null) {
            try {
                db.execSQL("UPDATE memberTBL SET password = '${memberData.password}', name = '${memberData.name}', " +
                        "position = '${memberData.position}', department = '${memberData.department}', phone = '${memberData.phone}', " +
                        "email = '${memberData.email}', address = '${memberData.address}' WHERE id = '${memberData.id}'")
                Log.d("kr.or.mrhi.ynu", "UPDATE SUCCESS")
                flag = true
            } catch (e: SQLException) {
                Log.d("kr.or.mrhi.ynu", "UPDATE ${memberData.id}, ${memberData.name} ERROR ${e.printStackTrace()}")
                flag = false
            } finally {
                db.close()
            }
        }
        return flag
    }
}