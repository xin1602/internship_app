package com.example.fin_pro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bt_log = findViewById<Button>(R.id.bt_log)

        bt_log.setOnClickListener {

            Thread {
                val con = MysqlCon()
                con.run()

                val userId = findViewById<EditText>(R.id.ed_stu)
                val password = findViewById<EditText>(R.id.ed_pw)

                val userPermission = con.checkUserPermission(userId.text.toString(), password.text.toString())
                val getUserName = con.getUserName(userId.text.toString())

                val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("userName", getUserName)  // 將使用者ID存入SharedPreferences
                editor.apply()

                when (userPermission) {
                    1 -> {
                        runOnUiThread {
                            Toast.makeText(
                                getApplicationContext(),
                                "登入成功，您為學生身分",
                                Toast.LENGTH_LONG
                            ).show();
                        }

                        val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
                        val editor = sharedPref.edit()
                        editor.putString(
                            "userId",
                            userId.text.toString()
                        )  // 將使用者ID存入SharedPreferences
                        editor.putInt("userPermission", 1)  // 將使用者權限存入SharedPreferences
                        editor.apply()

                        val intent = Intent(this, homepage::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        runOnUiThread {
                            Toast.makeText(
                                getApplicationContext(),
                                "登入成功，您為職員身分",
                                Toast.LENGTH_LONG
                            ).show();
                        }

                        val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
                        val editor = sharedPref.edit()
                        editor.putString("userId", userId.text.toString())  // 將使用者ID存入SharedPreferences
                        editor.putInt("userPermission", 2)  // 將使用者權限存入SharedPreferences
                        editor.apply()

                        val intent = Intent(this, homepage::class.java)
                        startActivity(intent)
                    }
                    else -> {
                        runOnUiThread {
                            Toast.makeText(
                                getApplicationContext(),
                                "登入失敗",
                                Toast.LENGTH_LONG
                            ).show();
                            userId.setText("")
                            password.setText("")
                        }
                    }
                }
            }.start()
        }
    }
}