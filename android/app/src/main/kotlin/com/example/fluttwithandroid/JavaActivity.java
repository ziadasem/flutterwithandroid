package com.example.fluttwithandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fluttwithandroid.R;
import com.google.android.material.textfield.TextInputLayout;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;


public class JavaActivity extends AppCompatActivity {

    Button flutterJavaNavigationButton;
    Button kotlinSendingButton ;

    TextInputLayout textInputLayout ;

    TextView resultTV ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent =  getIntent();
        String value = intent.getStringExtra("value");
        flutterJavaNavigationButton =  findViewById(R.id.flutterJavaNavigationButton);
        kotlinSendingButton =  findViewById(R.id.kotlinSendingButton);
        textInputLayout = findViewById(R.id.inputField);
        resultTV = findViewById(R.id.resultTV);
        resultTV.setText(value == null? "" : value);
        kotlinSendingButton.setOnClickListener(view -> navigateToKotlin());
        flutterJavaNavigationButton.setOnClickListener(view -> navigateToFlutter());
    }

    void navigateToKotlin(){
        Intent intent = new Intent(this, KotlinActivity.class);
        intent.putExtra("value", textInputLayout.getEditText().getText().toString() );
        startActivity(intent);
    }
    void navigateToFlutter(){
        MethodChannel methodChannel =  MainActivity.Companion.createMethodChannel("com.example.app/example");
        methodChannel.invokeMethod("receiveData",  textInputLayout.getEditText().getText().toString()) ;
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
