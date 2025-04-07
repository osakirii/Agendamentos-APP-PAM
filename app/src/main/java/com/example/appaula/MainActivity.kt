package com.example.appaula

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appaula.ui.theme.AppAulaTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppAulaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Campo(valor: String, label: String, onValueChange: (String) -> Unit){
    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        label = { Text(label) }
    )
}

@Composable
fun App(modifier: Modifier = Modifier) {
    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    Column(Modifier
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier
            .fillMaxWidth(), Arrangement.Center) {
            Text(
                text = "App Agendamentos",
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.Bold
            )
        }
        Row(Modifier
            .fillMaxWidth(), Arrangement.Center) {
            Campo(nome, "Nome", onValueChange = { nome = it })
        }
        Row(Modifier
            .fillMaxWidth(), Arrangement.Center) {
            Campo(endereco, "Endereco", onValueChange = { endereco = it })
        }
        Row(Modifier
            .fillMaxWidth(), Arrangement.Center) {
            Campo(bairro, "Bairro", onValueChange = { bairro = it })
        }
        Row(Modifier
            .fillMaxWidth(), Arrangement.Center) {
            Campo(cep, "CEP", onValueChange = { cep = it })
        }
        Row(Modifier
            .fillMaxWidth(), Arrangement.Center) {
            Campo(cidade, "Cidade", onValueChange = { cidade = it })
        }
        Row(Modifier
            .fillMaxWidth(), Arrangement.Center) {
            Campo(estado, "Estado", onValueChange = { estado = it })
        }
        Row(Modifier
            .fillMaxWidth(), Arrangement.Center) {
            Button(
                onClick = {
                    val db = Firebase.firestore

                    val agendamentos = hashMapOf(
                        "nome" to nome,
                        "endereco" to endereco,
                        "bairro" to bairro,
                        "CEP" to cep,
                        "cidade" to cidade,
                        "estado" to estado
                    )
                    db.collection("agendamentos")
                        .add(agendamentos)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                },
                modifier = Modifier.padding(10.dp)
            ) { Text("Cadastrar") }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppAulaTheme {
        App()
    }
}