package com.example.fin_pro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class company_intro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_intro)


        val tv_com=findViewById<TextView>(R.id.tv_com)
        val tv_add=findViewById<TextView>(R.id.tv_add)
        val tv_phone=findViewById<TextView>(R.id.tv_phone)
        val tv_mail=findViewById<TextView>(R.id.tv_mail)
        val tv_introduce=findViewById<TextView>(R.id.tv_introduce)
        val tv_off=findViewById<TextView>(R.id.tv_off)
        val tv_salary=findViewById<TextView>(R.id.tv_salary)
        val tv_present=findViewById<TextView>(R.id.tv_present)
        val tv_ability=findViewById<TextView>(R.id.tv_ability)

        val ib_home5=findViewById<ImageButton>(R.id.ib_home5)
        val ib_list1=findViewById<ImageButton>(R.id.ib_list1)
        val ib_list2=findViewById<ImageButton>(R.id.ib_list2)
        val ib_list3=findViewById<ImageButton>(R.id.ib_list3)

        // 讀取使用者資訊
        val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
        val userPermission = sharedPref.getInt("userPermission", -99)

        // 讀取本職缺資訊
        val sharedPref2 = getSharedPreferences("job_info", MODE_PRIVATE)
        val companyName = sharedPref2.getString("companyName", null)
        val address = sharedPref2.getString("address", null)
        val phone = sharedPref2.getString("phone", null)
        val email = sharedPref2.getString("email", null)
        val introduction = sharedPref2.getString("introduction", null)
        val position = sharedPref2.getString("position", null)
        val jobDescription = sharedPref2.getString("jobDescription", null)
        val skills = sharedPref2.getString("skills", null)
        val salary = sharedPref2.getString("salary", null)

        tv_com.setText("公司:"+companyName)
        tv_add.setText("地址:\n"+address)
        tv_phone.setText("電話:"+phone)
        tv_mail.setText("Email:\n"+email)
        tv_introduce.setText("簡介:\n"+introduction)
        tv_off.setText("職位:"+position)
        tv_salary.setText("薪資:"+salary)
        tv_present.setText("介紹:\n"+jobDescription)
        tv_ability.setText("專業技能:\n" +skills)


        if(userPermission==2){
            ib_list3.setImageResource(R.drawable.add_vacancy)
        }

        ib_home5.setOnClickListener {
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