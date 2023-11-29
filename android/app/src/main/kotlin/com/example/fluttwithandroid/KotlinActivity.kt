package com.example.fluttwithandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidapp.ui.theme.AndroidAppTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


class KotlinActivity : ComponentActivity() {
    private var textFieldValue = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val message = intent.getStringExtra("value")
        setContent {
            AndroidAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Send Text",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        SimpleFilledTextFieldSample()
                        Text(text = if (message.isNullOrEmpty()) "" else "Text From Java Activity is $message")

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                colors = ButtonDefaults.buttonColors( containerColor = Color(0xFF3700B3)),
                                onClick = {
                                    navigateToJava()
                                },
                            ) {
                                Text(text = "Send To Java")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                colors = ButtonDefaults.buttonColors( containerColor = Color(0xFF3700B3)),
                                onClick = { /*TODO*/ }) {
                                Text(text = "Send To Flutter")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateToJava() {
        val intent = Intent(this, JavaActivity::class.java)

        startActivity(intent)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SimpleFilledTextFieldSample() {
        var text by remember { mutableStateOf(textFieldValue) }
        TextField(
            value = text,
            onValueChange = { text = it
                textFieldValue= it
                            },
            label = { Text("Write here") },
            modifier = Modifier.fillMaxWidth()
        )
    }

}
