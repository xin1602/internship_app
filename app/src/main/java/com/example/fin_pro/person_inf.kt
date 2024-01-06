package com.example.fin_pro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class person_inf : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_inf)

        val ed_stu2=findViewById<EditText>(R.id.ed_stu2)
        val ed_stu3=findViewById<EditText>(R.id.ed_stu3)
        val ed_pw2=findViewById<EditText>(R.id.ed_pw2)
        val ed_em=findViewById<EditText>(R.id.ed_em)
        val ed_ph=findViewById<EditText>(R.id.ed_ph)
        val ed_maj=findViewById<EditText>(R.id.ed_maj)
        val bt_save=findViewById<Button>(R.id.bt_save)
        val ib_home2=findViewById<ImageButton>(R.id.ib_home2)
        val bt_out=findViewById<Button>(R.id.bt_out)
        val ib_list1=findViewById<ImageButton>(R.id.ib_list1)
        val ib_list2=findViewById<ImageButton>(R.id.ib_list2)
        val ib_list3=findViewById<ImageButton>(R.id.ib_list3)

        // 讀取使用者資訊
        val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
        val userPermission = sharedPref.getInt("userPermission", -99)

        if(userPermission==2){
            ib_list3.setImageResource(R.drawable.add_vacancy)
        }

        Thread {
            val con = MysqlCon()

            // 讀取使用者資訊
            val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
            val userId = sharedPref.getString("userId", null)

            val getInfo = con.getInfo(userId!!)

            runOnUiThread {
                ed_stu2.setText(getInfo[0])
                ed_stu3.setText(getInfo[1])
                ed_pw2.setText(getInfo[2])
                ed_em.setText(getInfo[3])
                ed_ph.setText(getInfo[4])
                ed_maj.setText(getInfo[5])
            }

        }.start()

        bt_save.setOnClickListener {
            Thread {
                val con = MysqlCon()

                // 讀取使用者資訊
                val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
                val userId = sharedPref.getString("userId", null)
                val setInfo = con.setInfo(userId!!,ed_pw2.text.toString(),ed_em.text.toString(),ed_ph.text.toString())

                if (setInfo){
                    runOnUiThread {
                        Toast.makeText(
                            getApplicationContext(),
                            "儲存成功",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                }
                else{
                    runOnUiThread {
                        Toast.makeText(
                            getApplicationContext(),
                            "儲存失敗",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                }
            }.start()

            val intent= Intent(this,homepage::class.java)
            startActivity(intent)
        }

        ib_home2.setOnClickListener {
            val intent= Intent(this,homepage::class.java)
            startActivity(intent)
        }
        bt_out.setOnClickListener {
            // 登出時清除使用者資訊
            val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.remove("userId")
            editor.remove("userPermission")
            editor.apply()

            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        ib_list1.setOnClickListener {
            val intent= Intent(this,person_inf::class.java)
            startActivity(intent)
        }
        ib_list2.setOnClickListener {
            val intent= Intent(this,favorite::class.java)
            startActivity(intent)
        }
        ib_list3.setOnClickListener {
            if (userPermission == 1) {
                val intent = Intent(this, resume::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, NewOpeningActivity::class.java)
                startActivity(intent)
            }
        }
    }
}