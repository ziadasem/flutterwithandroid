package com.example.fluttwithandroid

import android.content.Intent
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example.app/example"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
       // flutterEngine.destroy()
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
                call, result ->
            if (call.method == "sendData") {
                val value =(call.arguments as Map<*, *>);
                navigate(value["route"].toString(), value["data"].toString());
            } else {
                result.notImplemented()
            }

        }
    }



    fun navigate(route:String,  data:String):Unit {
        var intent: Intent? = null;
        intent = when (route) {
            "java" -> {
                Intent(this, JavaActivity::class.java)
            }
            "kotlin" -> {
                Intent(this, KotlinActivity::class.java)
            }
            else -> {
                return ;
            }
        }
        intent?.putExtra("value", data)
        startActivity(intent)
    }
}