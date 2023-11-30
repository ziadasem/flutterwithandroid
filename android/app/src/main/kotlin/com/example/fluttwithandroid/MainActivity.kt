package com.example.fluttwithandroid

import android.content.Context
import android.content.Intent
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterEngineGroup
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.dart.DartExecutor.DartEntrypoint
import io.flutter.plugin.common.MethodChannel


class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.example.app/example"


    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        cachedFlutterEngine = flutterEngine ;
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

    companion object {
         var cachedFlutterEngine: FlutterEngine? = null
        fun createMethodChannel(
            channelId: String
        ): MethodChannel? {

            return cachedFlutterEngine?.let {
                cachedFlutterEngine?.dartExecutor?.let { it1 ->
                    MethodChannel(
                        it1,
                        channelId
                    )
                }
            }
        }

    }

}
