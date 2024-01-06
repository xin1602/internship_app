package com.example.fin_pro

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView



class FavoriteAdapter(private var jobs: List<Job>) : RecyclerView.Adapter<FavoriteAdapter.JobViewHolder>() {

    interface OnJobItemClickListener {
        fun onJobItemClick(clickedJob: Job)
    }

    class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lin_layout1: LinearLayout = itemView.findViewById(R.id.lin_layout1)
        val companyNameTextView: TextView = itemView.findViewById(R.id.tv_name)
        val positionTextView: TextView = itemView.findViewById(R.id.tv_offer)
        val addressTextView: TextView = itemView.findViewById(R.id.tv_address)
        val departmentLabelTextView: TextView = itemView.findViewById(R.id.tv_labm)
        val salaryLabelTextView: TextView = itemView.findViewById(R.id.tv_labs)
        val ib_ema: ImageButton = itemView.findViewById(R.id.ib_ema)
        val ib_fav: ImageButton = itemView.findViewById(R.id.ib_fav)
    }


    private var onJobItemClickListener: OnJobItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_favorite, parent, false)
        return JobViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val currentJob = jobs[position]

        holder.companyNameTextView.text = currentJob.companyName
        holder.positionTextView.text = currentJob.position
        holder.addressTextView.text = currentJob.address
        holder.departmentLabelTextView.text = currentJob.department
        holder.salaryLabelTextView.text = currentJob.salary

        holder.lin_layout1.setOnClickListener {
            // 處理點擊事件

            val sharedPref = holder.itemView.context.getSharedPreferences("job_info", AppCompatActivity.MODE_PRIVATE)

            // 取得點擊的職缺資訊
            val clickedJob = jobs[position]

            with(sharedPref.edit()) {
                putString("id", clickedJob.id)
                putString("companyName", clickedJob.companyName)
                putString("address", clickedJob.address)
                putString("phone", clickedJob.phone)
                putString("email", clickedJob.email)
                putString("introduction", clickedJob.introduction)
                putString("position", clickedJob.position)
                putString("jobDescription", clickedJob.jobDescription)
                putString("skills", clickedJob.skills)
                putString("salary", clickedJob.salary)
                putString("department", clickedJob.department)
                apply()
            }

            val intent = Intent(holder.itemView.context, company_intro::class.java)
            holder.itemView.context.startActivity(intent)

            // 將點擊的職缺通知給活動
            onJobItemClickListener?.onJobItemClick(clickedJob)

        }

        holder.ib_fav.setOnClickListener {

            // 讀取使用者資訊
            val sharedPref = holder.itemView.context.getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
            val userId = sharedPref.getString("userId", null)

            // 取得點擊的職缺資訊
            val clickedJob = jobs[position]

            Thread {
                val con = MysqlCon()

                val isJobFavorite = con.isJobFavorite(userId!!,clickedJob.id)
                if (isJobFavorite){
                    holder.itemView.post {
                        // 將星星圖示改為 @drawable/favorites(尚未收藏圖示)
                        holder.ib_fav.setImageResource(R.drawable.favorites)
                        // 通知 Adapter 資料集變更，刷新 RecyclerView
                        notifyDataSetChanged()
                    }
                    // 職缺已經收藏，移除收藏
                    con.removeFavorite(userId!!,clickedJob.id)
                    holder.itemView.post {
                        Toast.makeText(holder.itemView.context, "移除收藏", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    holder.itemView.post {
                        // 將星星圖示改為 @drawable/star(已收藏圖示)
                        holder.ib_fav.setImageResource(R.drawable.star)
                        // 通知 Adapter 資料集變更，刷新 RecyclerView
                        notifyDataSetChanged()
                    }
                    // 職缺尚未收藏，加入收藏
                    con.addFavorite(userId!!,clickedJob.id)
                    holder.itemView.post {
                        Toast.makeText(holder.itemView.context, "已收藏", Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }

        holder.ib_ema.setOnClickListener {
            Log.v("OpeningAdapter", "Email button clicked")
            val clickedJob = jobs[position]
            Thread {
                val con = MysqlCon()

                val sharedPref = holder.itemView.context.getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
                val userId = sharedPref.getString("userId", null)
                val getUserName = con.getUserName(userId!!)
                val userPermission = sharedPref.getInt("userPermission", -99)
                Log.v("QQQQ",userPermission.toString())

                val getResume = con.getResume(userId)
                val v1=getResume[0].toString()
                val v2=getResume[1].toString()
                val v3=getResume[2].toString()

                if(getResume[0]!="尚未填寫"){

                    // 使用 Intent.ACTION_SEND 動作，指定郵件地址
                    val TO = arrayOf("${clickedJob.email}")
                    val emailIntent = Intent(Intent.ACTION_SEND)
                    emailIntent.data= Uri.parse("mailto:")
                    emailIntent.type = "text/plain"

                    // 設定郵件主題和文字內容
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getUserName+"的應徵履歷")
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "申請者：$getUserName\n\n簡歷:\n $v1\n\n工作經驗: \n$v2\n\n專業技能: \n$v3")

                    try {
                        // 啟動郵件應用程式
                        holder.itemView.context.startActivity(emailIntent)
                        Log.i("Finished sending email", "傳送")
                    } catch (e: ActivityNotFoundException) {
                        // 若找不到郵件應用程式，顯示提示訊息
                        holder.itemView.post {
                            Toast.makeText(
                                holder.itemView.context,
                                "找不到郵件應用程式，請安裝郵件應用程式後再試一次。",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                else if(userPermission==2){
                    holder.itemView.post {
                        // 顯示訊息，提示此用戶為職員身分(非學生)，無法填寫履歷
                        Toast.makeText(
                            holder.itemView.context,
                            "為職員身分(非學生)，無寄送履歷功能",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else{
                    holder.itemView.post {
                        // 顯示訊息，提示填寫履歷相關資料
                        Toast.makeText(
                            holder.itemView.context,
                            "請填寫履歷相關資料",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }.start()
        }
    }

    override fun getItemCount() = jobs.size

}