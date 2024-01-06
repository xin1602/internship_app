package com.example.fin_pro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class resume : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume)

        val ed_stu2=findViewById<EditText>(R.id.ed_stu2)
        val ed_intro=findViewById<EditText>(R.id.ed_intro)
        val ed_work=findViewById<EditText>(R.id.ed_work)
        val ed_skill=findViewById<EditText>(R.id.ed_skill)
        val bt_save=findViewById<Button>(R.id.bt_save)

        val ib_home4=findViewById<ImageButton>(R.id.ib_home4)
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

            val getUserName = con.getUserName(userId!!)
            val getResume = con.getResume(userId!!)

            runOnUiThread {
                ed_stu2.setText(getUserName)
                ed_intro.setText(getResume[0])
                ed_work.setText(getResume[1])
                ed_skill.setText(getResume[2])
            }

        }.start()

        bt_save.setOnClickListener {
            Thread {
                val con = MysqlCon()

                // 讀取使用者資訊
                val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
                val userId = sharedPref.getString("userId", null)
                val setResume = con.setResume(userId!!,ed_intro.text.toString(),ed_work.text.toString(),ed_skill.text.toString())

                if (setResume){
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

        ib_home4.setOnClickListener {
            val intent= Intent(this,homepage::class.java)
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