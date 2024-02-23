package com.example.myussd


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import com.google.firebase.inappmessaging.model.Button

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onExecuteUssd: (String) -> Unit) {
    var ussdCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = ussdCode,
            onValueChange = { ussdCode = it },
            label = { Text("USSD Code") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { onExecuteUssd(ussdCode) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Execute")
        }
    }
}
