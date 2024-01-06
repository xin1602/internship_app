package com.example.fin_pro

import android.util.Log
import java.sql.DriverManager
import java.sql.SQLException

class MysqlCon {
    // 資料庫定義
    var mysql_ip = "10.0.2.2"
    var mysql_port = 3306 // 預設為 3306
    var db_name = "internship"
    var url = "jdbc:mysql://$mysql_ip:$mysql_port/$db_name"
//    var db_user = "root"
    var db_user = "lxh"
    var db_password = "1234"

    fun run() {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            Log.v("DB", "加載驅動成功")
        } catch (e: ClassNotFoundException) {
            Log.e("DB", "加載驅動失敗")
            return
        }

        // 連接資料庫
        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            Log.v("DB", "遠端連接成功")
        } catch (e: SQLException) {
            Log.e("DB", "遠端連接失敗")
            Log.e("DB", e.toString())
        }
    }

    // 確認使用者權限 "學生" -> 1 "職員" -> 2
    fun checkUserPermission(userId: String, password: String): Int {
        var permission = -99 // 默認值

        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "SELECT `權限` FROM info WHERE `使用者id` = ? AND `密碼` = ?"

            val pstmt = con.prepareStatement(sql)
            pstmt.setString(1, userId)
            pstmt.setString(2, password)
            val rs = pstmt.executeQuery()

            if (rs.next()) {
                val userPermission = rs.getString("權限")
                Log.v("userPermission", userPermission)

                permission = when (userPermission) {
                    "學生" -> 1
                    "職員" -> 2
                    else -> -99
                }
            }
            pstmt.close()
            con.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return permission
    }

    //取得履歷內容
    fun getResume(userId: String):Array<String?>{
        var resumeArray = arrayOfNulls<String?>(3)  // 創建長度為3的String陣列

        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "SELECT `resume`.簡歷,`resume`.工作經驗,`resume`.專業技能 FROM `resume` where `resume`.使用者id=?;"
            val pstmt = con.prepareStatement(sql)
            pstmt.setString(1, userId)

            val rs = pstmt.executeQuery()
            if (rs.next()) {
                val value1 = rs.getString("簡歷")
                val value2 = rs.getString("工作經驗")
                val value3 = rs.getString("專業技能")
                resumeArray = arrayOf(value1, value2, value3)
//                Log.v("OK", resumeArray[0]!!)

            }else{
                val value1 = "尚未填寫"
                val value2 = "尚未填寫"
                val value3 = "尚未填寫"
                resumeArray = arrayOf(value1, value2, value3)
            }
            pstmt.close()
            con.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return resumeArray
    }

    //取得使用者名稱
    fun getUserName(userId: String): String {
        var userName = ""
        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "SELECT 姓名 FROM info WHERE info.使用者id = ?;"
            val pstmt = con.prepareStatement(sql)
            pstmt.setString(1, userId)
            val rs = pstmt.executeQuery()

            if (rs.next()) {
                userName = rs.getString("姓名")
//                Log.v("OK", userName)
            }
            pstmt.close()
            con.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return userName
    }

    //設定履歷內容
    fun setResume(userId: String,intro: String,work: String,skill: String):Boolean {
        var isSuccess = false
        try {
            val con = DriverManager.getConnection(url, db_user, db_password)

            val sql1 = "select count(*) from `resume` where `使用者id`=?;"
            val pstmt1 = con.prepareStatement(sql1)
            pstmt1.setString(1, userId)
            val rs1 = pstmt1.executeQuery()

            if (rs1.next()) {
                val count = rs1.getInt(1)
                if (count==0){
                    val sql2 = "INSERT INTO `resume` (`使用者id`,`簡歷`,`工作經驗`,`專業技能`) VALUES (?,?,?,?);"
                    val pstmt2 = con.prepareStatement(sql2)
                    pstmt2.setString(1, userId)
                    pstmt2.setString(2, intro)
                    pstmt2.setString(3, work)
                    pstmt2.setString(4, skill)
                    pstmt2.executeUpdate()
                    pstmt2.close()
                    Log.v("DB", "寫入資料完成")
                    isSuccess = true
                }
                else{
                    val sql2 = "UPDATE `resume` SET `簡歷`=?,`工作經驗`=?,`專業技能`=? WHERE `使用者id`=?;"
                    val pstmt2 = con.prepareStatement(sql2)
                    pstmt2.setString(1, intro)
                    pstmt2.setString(2, work)
                    pstmt2.setString(3, skill)
                    pstmt2.setString(4, userId)
                    pstmt2.executeUpdate()
                    pstmt2.close()
                    Log.v("DB", "寫入資料完成")
                    isSuccess = true
                }
            }

        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("DB", "寫入資料失敗")
            Log.e("DB", e.toString())
        }
        return isSuccess
    }
    //取得個人資料
    fun getInfo(userId: String):Array<String?>{
        var infoArray = arrayOfNulls<String?>(7)  // 創建長度為7的String陣列

        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "SELECT `使用者id`,`姓名`,`密碼`,`電子郵件`,`電話`,`隸屬`,`權限` FROM `info` where 使用者id=?;"
            val pstmt = con.prepareStatement(sql)
            pstmt.setString(1, userId)
            val rs = pstmt.executeQuery()
            if (rs.next()) {
                val value1 = rs.getString("使用者id")
                val value2 = rs.getString("姓名")
                val value3 = rs.getString("密碼")
                val value4 = rs.getString("電子郵件")
                val value5 = rs.getString("電話")
                val value6 = rs.getString("隸屬")
                val value7 = rs.getString("權限")
                infoArray = arrayOf(value1, value2, value3,value4, value5, value6, value7)
            }
            pstmt.close()
            con.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return infoArray
    }

    //設定個人資料
    fun setInfo(userId: String,password: String,email: String,phone: String):Boolean {
        var isSuccess = false
        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "UPDATE `info` SET `密碼`=?,`電子郵件`=?,`電話`=? WHERE `使用者id`=?;"
            val pstmt = con.prepareStatement(sql)
            pstmt.setString(1, password)
            pstmt.setString(2, email)
            pstmt.setString(3, phone)
            pstmt.setString(4, userId)
            pstmt.executeUpdate()
            pstmt.close()
            Log.v("setInfo", "寫入資料完成")
            isSuccess = true

        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("setInfo", "寫入資料失敗")
            Log.e("setInfo", e.toString())
        }
        return isSuccess
    }

    //上架職缺
    fun setOpening(dep: String,com: String,add: String,phone: String,mail: String,introduce: String,off: String,salary: String,present: String,ability: String):Boolean {
        Log.v("DB", dep)
        var isSuccess = false
        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "INSERT INTO `job` (`系別`,`公司`,`地址`,`電話`,`email`,`簡介`,`職位`,`薪資`,`介紹`,`專業技能`) VALUES (?,?,?,?,?,?,?,?,?,?);"
            val pstmt = con.prepareStatement(sql)
            pstmt.setString(1, dep)
            pstmt.setString(2, com)
            pstmt.setString(3, add)
            pstmt.setString(4, phone)
            pstmt.setString(5, mail)
            pstmt.setString(6, introduce)
            pstmt.setString(7, off)
            pstmt.setString(8, salary)
            pstmt.setString(9, present)
            pstmt.setString(10, ability)
            pstmt.executeUpdate()
            pstmt.close()
            Log.v("setOpening", "寫入資料完成")
            isSuccess = true

        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("setOpening", "寫入資料失敗")
            Log.e("setOpening", e.toString())
        }
        return isSuccess
    }

    //取得所有實習職缺資料
    fun getAllOpening(userId: String): List<Job> {
        val allOpeningList = mutableListOf<Job>()

        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "SELECT `id`,`公司`,`地址`,`電話`,`email`,`簡介`,`職位`,`介紹`,`專業技能`,`薪資`,`系別` FROM `job`;"
            val pstmt = con.prepareStatement(sql)
            val rs = pstmt.executeQuery()

            while (rs.next()) {
                val sql2 = "SELECT count(*) FROM user_favorite WHERE user_id = ? AND job_id = ?;"
                val pstmt2 = con.prepareStatement(sql2)
                pstmt2.setString(1, userId)
                pstmt2.setInt(2, rs.getString("id").toInt())
                val rs2 = pstmt2.executeQuery()
                if (rs2.next()) {
                    val count = rs2.getInt(1)

                    if (count == 0) {
                        val job = Job(
                            rs.getString("id"),
                            rs.getString("公司"),
                            rs.getString("地址"),
                            rs.getString("電話"),
                            rs.getString("email"),
                            rs.getString("簡介"),
                            rs.getString("職位"),
                            rs.getString("介紹"),
                            rs.getString("專業技能"),
                            rs.getString("薪資"),
                            rs.getString("系別"),
                            false
                        )
                        allOpeningList.add(job)

                    } else {
                        val job = Job(
                            rs.getString("id"),
                            rs.getString("公司"),
                            rs.getString("地址"),
                            rs.getString("電話"),
                            rs.getString("email"),
                            rs.getString("簡介"),
                            rs.getString("職位"),
                            rs.getString("介紹"),
                            rs.getString("專業技能"),
                            rs.getString("薪資"),
                            rs.getString("系別"),
                            true
                        )
                        allOpeningList.add(job)
                    }
                }
            }

            pstmt.close()
            con.close()
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("getAllOpening", e.toString())
        }

        return allOpeningList
    }

    //取得使用者收藏列表的職缺資料
    fun getFavorite(userId: String): List<Job> {
        val favoriteList = mutableListOf<Job>()

        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "SELECT `job`.`id`,`job`.`公司`,`job`.`地址`,`job`.`電話`,`job`.`email`,`job`.`簡介`,`job`.`職位`,`job`.`介紹`,`job`.`專業技能`,`job`.`薪資`,`job`.`系別` FROM `job`,`user_favorite` WHERE `job`.id = `user_favorite`.job_id AND `user_favorite`.user_id = ? ;"
            val pstmt = con.prepareStatement(sql)
            pstmt.setString(1, userId)
            val rs = pstmt.executeQuery()

            while (rs.next()) {
                val sql2 = "SELECT count(*) FROM user_favorite WHERE user_id = ? AND job_id = ?;"
                val pstmt2 = con.prepareStatement(sql2)
                pstmt2.setString(1, userId)
                pstmt2.setInt(2, rs.getString("id").toInt())
                val rs2 = pstmt2.executeQuery()
                if (rs2.next()) {
                    val count = rs2.getInt(1)
                    if (count == 0) {
                        val job = Job(
                            rs.getString("id"),
                            rs.getString("公司"),
                            rs.getString("地址"),
                            rs.getString("電話"),
                            rs.getString("email"),
                            rs.getString("簡介"),
                            rs.getString("職位"),
                            rs.getString("介紹"),
                            rs.getString("專業技能"),
                            rs.getString("薪資"),
                            rs.getString("系別"),
                            false
                        )
                        favoriteList.add(job)

                    } else {
                        val job = Job(
                            rs.getString("id"),
                            rs.getString("公司"),
                            rs.getString("地址"),
                            rs.getString("電話"),
                            rs.getString("email"),
                            rs.getString("簡介"),
                            rs.getString("職位"),
                            rs.getString("介紹"),
                            rs.getString("專業技能"),
                            rs.getString("薪資"),
                            rs.getString("系別"),
                            true
                        )
                        favoriteList.add(job)
                    }
                }
            }

            pstmt.close()
            con.close()
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("DB", e.toString())
        }
        return favoriteList
    }

    //判斷此職缺是否被此使用者收藏
    fun isJobFavorite(userId: String, jobId: String): Boolean {
        var isFavorite = false

        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "SELECT * FROM user_favorite WHERE user_id = ? AND job_id = ?;"
            val pstmt = con.prepareStatement(sql)
            pstmt.setString(1, userId)
            pstmt.setInt(2, jobId.toInt())
            val rs = pstmt.executeQuery()

            if (rs.next()) {
                isFavorite = true
                Log.v("isJobFavorite", "判斷為收藏")
            }else{
                Log.v("isJobFavorite", "判斷不為收藏")
            }

            pstmt.close()
            con.close()

        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("isJobFavorite", e.toString())
        }
        return isFavorite
    }

    //將此職缺加入此使用者的收藏
    fun addFavorite(userId: String, jobId: String) {

        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "INSERT INTO user_favorite (user_id, job_id) VALUES (?, ?);"
            val pstmt = con.prepareStatement(sql)
            pstmt.setString(1, userId)
            pstmt.setInt(2, jobId.toInt())
            pstmt.executeUpdate()

            pstmt.close()
            con.close()
            Log.v("addFavorite", "添加收藏成功")

        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("addFavorite", "添加收藏失敗")
            Log.e("addFavorite", e.toString())
        }
    }

    // 將此職缺移除此使用者的收藏
    fun removeFavorite(userId: String, jobId: String) {
        try {
            val con = DriverManager.getConnection(url, db_user, db_password)
            val sql = "DELETE FROM user_favorite WHERE user_id = ? AND job_id = ?;"
            val pstmt = con.prepareStatement(sql)
            pstmt.setString(1, userId)
            pstmt.setInt(2, jobId.toInt())
            pstmt.executeUpdate()

            pstmt.close()
            con.close()
            Log.v("DB", "移除收藏成功")
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.e("DB", "移除收藏失敗")
            Log.e("DB", e.toString())
        }
    }

}

//職缺資料
data class Job(
    val id: String,
    val companyName: String,
    val address: String,
    val phone: String,
    val email: String,
    val introduction: String,
    val position: String,
    val jobDescription: String,
    val skills: String,
    val salary: String,
    val department: String,
    var isFavorite: Boolean
)
