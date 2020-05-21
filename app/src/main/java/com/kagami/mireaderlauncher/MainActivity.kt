package com.kagami.mireaderlauncher

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import android.view.*
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_app.view.*
import kotlinx.coroutines.selects.select


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: AppsViewModel
    lateinit var adapter:Adapter
    lateinit var layoutManager:GridLayoutManager
    var columnCount = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedValue.init(applicationContext)

        setContentView(R.layout.activity_main)
        columnCount = SharedValue.getInt("columnCount", 4)

        viewModel = ViewModelProviders.of(this)[AppsViewModel::class.java]
        adapter = Adapter()
        layoutManager = GridLayoutManager(this, columnCount)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        buildNotification()
        viewModel.appsLiveData.observe(this, Observer {
            adapter.dataSet=it
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.reloadApps()
    }

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)

        val item = menu?.add(Menu.NONE, R.id.menu_settings, Menu.NONE, "settings")
        item?.setIcon(android.R.drawable.ic_menu_preferences)
        item?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)

        val item2 = menu?.add(Menu.NONE, R.id.menu_license, Menu.NONE, "License Info")
        item2?.setIcon(android.R.drawable.ic_menu_info_details)
        item2?.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER)

        return true
    }

    fun dpToPx(fDp: Float): Int {
        val resResource = Resources.getSystem()
        return Math.round(fDp * resResource.displayMetrics.densityDpi.toFloat() / 160.0f)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)

        when (item.itemId) {
            R.id.menu_settings -> showSettingsPopup()
            R.id.menu_license -> startActivity(Intent(this, LicenseActivity::class.java))
        }

        return true
    }

    private fun showSettingsPopup() {
        val rootView = findViewById<ViewGroup>(android.R.id.content)

        val popupView = LayoutInflater.from(this).inflate(R.layout.settings_popup, null)
        when (columnCount) {
            5 -> popupView.findViewById<RadioButton>(R.id.radioColumn5).isChecked = true
            6 -> popupView.findViewById<RadioButton>(R.id.radioColumn6).isChecked = true
            7 -> popupView.findViewById<RadioButton>(R.id.radioColumn7).isChecked = true
            else -> popupView.findViewById<RadioButton>(R.id.radioColumn4).isChecked = true
        }
        popupView.findViewById<RadioGroup>(R.id.rgColumns).setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioColumn5 -> columnCount = 5
                R.id.radioColumn6 -> columnCount = 6
                R.id.radioColumn7 -> columnCount = 7
                else -> columnCount = 4
            }
            SharedValue.setInt("columnCount", columnCount)
            layoutManager.spanCount = columnCount
            adapter.notifyDataSetChanged()
        }


        val popup = PopupWindow(popupView, dpToPx(235f), dpToPx(70f), true)
        popup.isOutsideTouchable = false
        popup.showAtLocation(rootView, Gravity.TOP or Gravity.RIGHT, dpToPx(5f), dpToPx(80f) )
    }

    fun uninstallApp(appId:String){
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$appId")
        startActivity(intent)
    }


    fun startAppDetail(packageName: String){
        val intent=Intent()
        intent.action=Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS
        //val uri=Uri.fromParts("package",packageName,null)
        //intent.setData(uri)
        startActivity(intent)
    }
    fun startActivity(packageName: String) {
        var intent = packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) { // We found the activity now start the activity
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {

        }
    }
    private fun buildNotification() {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "mainchannel"
            val descriptionText = "mainchannel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("mainchannel", name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        val resultPendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT
        )
        val mNotificationBuilder = NotificationCompat.Builder(this,"mainchannel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setColor(getColor(R.color.colorPrimary))
            .setContentTitle(getString(R.string.app_name))
            .setOngoing(true)
            .setContentIntent(resultPendingIntent)
            with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, mNotificationBuilder.build())
        }
        //startForeground(FOREGROUND_SERVICE_ID, mNotificationBuilder.build())
    }


    inner class Adapter:RecyclerView.Adapter<ViewHolder>(){
        var dataSet = listOf<AppsViewModel.AppData>()
         set(value) {
             field=value
             notifyDataSetChanged()
         }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)).apply {
                itemView.setOnClickListener {
                    startActivity(data.appId)
                    //startAppDetail(data.appId)
                }
                itemView.setOnLongClickListener {
                    uninstallApp(data.appId)
                    true
                }
            }
        }

        override fun getItemCount(): Int {
            return dataSet.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data=dataSet[position]
            holder.data=data
            holder.itemView.run {
                nameText.text = data.name
                if(data.icon!=null){
                    imageView.setImageDrawable(data.icon)
                }else{
                    imageView.setImageResource(R.mipmap.ic_launcher)
                }

                val textSize:Float
                val iconSize:Int
                when(columnCount) {
                    7 -> { textSize = 12f; iconSize = 40 }
                    6 -> { textSize = 14f; iconSize = 50 }
                    5 -> { textSize = 15f; iconSize = 60 }
                    else -> { textSize = 16f; iconSize = 70 }
                }
                nameText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
                imageView.layoutParams.width = iconSize
                imageView.layoutParams.height = iconSize
            }
        }

    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var data:AppsViewModel.AppData
    }



}
