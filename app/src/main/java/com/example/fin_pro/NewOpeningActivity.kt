package com.example.fin_pro

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NewOpeningActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_opening)

        val spinner_3: Spinner = findViewById(R.id.spinner_3)
        val ed_com=findViewById<EditText>(R.id.ed_com)
        val ed_add=findViewById<EditText>(R.id.ed_add)
        val ed_phone=findViewById<EditText>(R.id.ed_phone)
        val ed_mail=findViewById<EditText>(R.id.ed_mail)
        val ed_introduce=findViewById<EditText>(R.id.ed_introduce)
        val ed_off=findViewById<EditText>(R.id.ed_off)
        val ed_salary=findViewById<EditText>(R.id.ed_salary)
        val ed_present=findViewById<EditText>(R.id.ed_present)
        val ed_ability=findViewById<EditText>(R.id.ed_ability)
        val bt_save=findViewById<Button>(R.id.bt_save)
        val ib_home3=findViewById<ImageButton>(R.id.ib_home3)
        val ib_list1=findViewById<ImageButton>(R.id.ib_list1)
        val ib_list2=findViewById<ImageButton>(R.id.ib_list2)
        val ib_list3=findViewById<ImageButton>(R.id.ib_list3)

        // 讀取使用者資訊
        val sharedPref = getSharedPreferences("user_info", MODE_PRIVATE)
        val userPermission = sharedPref.getInt("userPermission", -99)

        if(userPermission==2){
            ib_list3.setImageResource(R.drawable.add_vacancy)
        }

        bt_save.setOnClickListener {

            Thread {
                val con = MysqlCon()

                val setOpening = con.setOpening(spinner_3.selectedItem.toString(),ed_com.text.toString(),ed_add.text.toString(),ed_phone.text.toString(),ed_mail.text.toString(),ed_introduce.text.toString(),ed_off.text.toString(),ed_salary.text.toString(),ed_present.text.toString(),ed_ability.text.toString())

                if (setOpening){
                    runOnUiThread {
                        Toast.makeText(
                            getApplicationContext(),
                            "職缺新增成功",
                            Toast.LENGTH_LONG
                        ).show();
                        val nm = NotificationManagerCompat.from(this) //建立通知管理物件
                        //若 Android 版本在 8.0 以上必須先建立通知頻道
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            //設定頻道 Id、名稱及訊息優先權
                            val name = "My Channel"
                            val importance = NotificationManager.IMPORTANCE_DEFAULT
                            val channel = NotificationChannel("final-project-1121-kotlin", name, importance)
                            //建立頻道
                            nm.createNotificationChannel(channel)
                        }
                        //建立 Intent、PendingIntent，當通知被點選時開啟應用程式
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

                        //定義通知的訊息內容並發送
                        val text = ed_com.text.toString()+"："+ed_off.text.toString()+"/"+ed_salary.text.toString()+"\n熱烈招募中！！"
                        val builder = NotificationCompat.Builder(this, "final-project-1121-kotlin")
                            .setSmallIcon(android.R.drawable.btn_star_big_on) //通知圖示
                            .setContentTitle("【"+spinner_3.selectedItem.toString()+"實習資訊】") //通知標題
                            .setContentText(text) //通知內容
                            .setContentIntent(pendingIntent) //通知被點選後的意圖
                            .setAutoCancel(true) //通知被點選後自動消失
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //優先權
                        if (checkSelfPermission(android.Manifest.permission.USE_FULL_SCREEN_INTENT) == PackageManager.PERMISSION_GRANTED) {
                            // 已經有權限，執行通知
                            nm.notify(0, builder.build())
                            Log.v("Notification"," Notification sent successfully")
                        } else {
                            // 沒有權限，進行權限請求
                            // 可以使用 ActivityCompat.requestPermissions() 進行權限請求
                        }
                    }
                }
                else{
                    runOnUiThread {
                        Toast.makeText(
                            getApplicationContext(),
                            "職缺新增失敗",
                            Toast.LENGTH_LONG
                        ).show();
                    }
                }

            }.start()
            val intent= Intent(this,homepage::class.java)
            startActivity(intent)
        }

        ib_home3.setOnClickListener {
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

