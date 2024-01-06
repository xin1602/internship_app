package com.example.fin_pro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.AdapterView
import android.widget.Spinner

class homepage : AppCompatActivity() {
    private fun filterJobs(
        allJobs: List<Job>,
        selectedArea: String,
        selectedDepartment: String
    ): List<Job> {
        return allJobs.filter { job ->
            val areaMatch = if (selectedArea == "全選") true else addressChoose(job.address) == selectedArea
            val departmentMatch = if (selectedDepartment == "全選") true else job.department == selectedDepartment

            areaMatch && departmentMatch
        }
    }

    private fun addressChoose(address: String): String {
        return if (address.length >= 2) {
            address.substring(0, 2)
        } else {
            address
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val ib_home1=findViewById<ImageButton>(R.id.ib_home1)
        val ib_list1=findViewById<ImageButton>(R.id.ib_list1)
        val ib_list2=findViewById<ImageButton>(R.id.ib_list2)
        val ib_list3=findViewById<ImageButton>(R.id.ib_list3)
        val spinner_1: Spinner =findViewById(R.id.spinner_1)
        val spinner_2: Spinner =findViewById(R.id.spinner_2)


        // recyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 讀取使用者資訊
        val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
        val userPermission = sharedPref.getInt("userPermission", -99)
        val userId = sharedPref.getString("userId", null)

        Thread {
            val con = MysqlCon()

            val openings = con.getAllOpening(userId!!)

            runOnUiThread {
                val adapter = OpeningAdapter(openings)
                recyclerView.adapter = adapter
            }
        }.start()


        spinner_1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                val selectedArea = spinner_1.selectedItem?.toString() ?: ""
                val selectedDepartment = spinner_2.selectedItem?.toString() ?: ""

                Thread {
                    val con = MysqlCon()

                    val getAllOpening = con.getAllOpening(userId!!)
                    val filteredJobs = filterJobs(getAllOpening, selectedArea, selectedDepartment)

                    // 更新 Adapter 的資料集
                    runOnUiThread {
                        val adapter = OpeningAdapter(filteredJobs)
                        recyclerView.adapter = adapter
                    }
                }.start()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // 不需要執行任何操作
            }
        }

        spinner_2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                val selectedArea = spinner_1.selectedItem?.toString() ?: ""
                val selectedDepartment = spinner_2.selectedItem?.toString() ?: ""

                Thread {
                    val con = MysqlCon()

                    val getAllOpening = con.getAllOpening(userId!!)
                    val filteredJobs = filterJobs(getAllOpening, selectedArea, selectedDepartment)

                    // 更新 Adapter 的資料集
                    runOnUiThread {
                        val adapter = OpeningAdapter(filteredJobs)
                        recyclerView.adapter = adapter
                    }
                }.start()
            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // 不需要執行任何操作
            }
        }

        if(userPermission==2){
            ib_list3.setImageResource(R.drawable.add_vacancy)
        }
        ib_home1.setOnClickListener {
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
