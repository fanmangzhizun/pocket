package com.example.pocket

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.*

class MainActivity : AppCompatActivity() {
    private var editView: EditText? = null
    private var encrypt: TextView? = null // 加密
    private var decrypt: TextView? = null // 解密
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        encrypt?.setOnClickListener(View.OnClickListener { // 加密粘贴到剪辑版
            var s:String = editView?.text.toString()
            s = encryptPassword(s).toString()
            var clip:ClipboardManager = baseContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            var clipData:ClipData = ClipData.newPlainText(null,s)
            clip.setPrimaryClip(clipData)
            Toast.makeText(applicationContext, s,
                Toast.LENGTH_SHORT).show();
        })
        decrypt?.setOnClickListener(View.OnClickListener { // 解密
            var s:String? = editView?.text.toString()
            s = s?.let { it1 -> decryptPassword(it1) }
            Toast.makeText(applicationContext, s,
                Toast.LENGTH_SHORT).show();
        })
    }

    fun initUI(){
        editView = findViewById(R.id.password_edit_view)
        encrypt = findViewById(R.id.encrypt)
        decrypt = findViewById(R.id.decrypt)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptPassword(s:String):String?{
        var s:ByteArray = Base64.getDecoder().decode(s)
        var decryptRes:String = String(s)
        return decryptRes
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encryptPassword(s:String): String? {
        var a:ByteArray = s.toByteArray()
        var encryptRes = Base64.getEncoder().encodeToString(a)
        return encryptRes
    }
}


