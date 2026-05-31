package com.example.testform.presentation.state

data class CadastroUiState(
    val name: String = "",
    val phone: String = "",
    val cpf: String = "",
    val birthDate: String = "",
    val selectedGender: String = "",
    val showDialog: Boolean = false,
    val dialogSuccess: Boolean = false,
    val showEmailField: Boolean = false,
    val email: String = "",
    val showEasterEggToast: Boolean = false
)
