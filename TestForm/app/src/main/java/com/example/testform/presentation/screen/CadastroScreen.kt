package com.example.testform.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testform.core.transformation.CpfVisualTransformation
import com.example.testform.core.transformation.DateVisualTransformation
import com.example.testform.core.transformation.PhoneVisualTransformation
import com.example.testform.presentation.viewmodel.CadastroEvent
import com.example.testform.presentation.viewmodel.CadastroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(viewModel: CadastroViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(uiState.showEasterEggToast) {
        if (uiState.showEasterEggToast) {
            viewModel.onEvent(CadastroEvent.ShowEasterEggToastHandled)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Cadastro") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Área do Easter Egg (30x30, Vermelha, ID: easter_egg_view)
                Surface(
                    modifier = Modifier
                        .size(50.dp)
                        .testTag("easter_egg_view")
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { viewModel.onEvent(CadastroEvent.EasterEggTapped) }
                        ),
                    color = MaterialTheme.colorScheme.background
                ) {}

                // Nome (Obrigatório, ID: input_name)
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::onNameChange,
                    label = { Text("Nome Completo*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_name"),
                    singleLine = true
                )

                // Telefone (Obrigatório, ID: input_phone)
                OutlinedTextField(
                    value = uiState.phone,
                    onValueChange = viewModel::onPhoneChange,
                    label = { Text("Telefone *") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_phone"),
                    visualTransformation = PhoneVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                // E-mail (Easter Egg, ID: input_email)
                if (uiState.showEmailField) {
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { viewModel.onEvent(CadastroEvent.EmailChanged(it)) },
                        label = { Text("E-mail") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_email"),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )
                }

                // CPF (Opcional, ID: input_cpf)
                OutlinedTextField(
                    value = uiState.cpf,
                    onValueChange = viewModel::onCpfChange,
                    label = { Text("CPF") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_cpf"),
                    visualTransformation = CpfVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                // Data de Nascimento (Opcional, ID: input_birthdate)
                OutlinedTextField(
                    value = uiState.birthDate,
                    onValueChange = viewModel::onBirthDateChange,
                    label = { Text("Data de Nascimento") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_birthdate"),
                    visualTransformation = DateVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                // Sexo (Opcional)
                Text(
                    text = "Sexo",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                val genderOptions = listOf("Masculino", "Feminino")
                Column(Modifier.selectableGroup()) {
                    genderOptions.forEach { text ->
                        val tag = if (text == "Masculino") "gender_option_masculino" else "gender_option_feminino"
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag(tag)
                                .selectable(
                                    selected = (text == uiState.selectedGender),
                                    onClick = { viewModel.onGenderChange(text) },
                                    role = Role.RadioButton
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == uiState.selectedGender),
                                onClick = null
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botão Salvar (ID: button_save)
                Button(
                    onClick = viewModel::onSaveClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("button_save")
                ) {
                    Text("Salvar")
                }
            }
        }

        // Modal de confirmação/erro (ID: dialog_confirmation)
        if (uiState.showDialog) {
            AlertDialog(
                onDismissRequest = viewModel::onDismissDialog,
                confirmButton = {
                    TextButton(
                        onClick = viewModel::onDismissDialog,
                        modifier = Modifier.testTag("dialog_button_ok")
                    ) {
                        Text("OK")
                    }
                },
                title = {
                    Text(if (uiState.dialogSuccess) "Sucesso" else "Erro")
                },
                text = {
                    Text(if (uiState.dialogSuccess) "Salvo com sucesso" else "Ops, algo deu errado")
                },
                modifier = Modifier.testTag("dialog_confirmation")
            )
        }
    }
}
