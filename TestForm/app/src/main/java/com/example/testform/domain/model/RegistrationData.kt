package com.example.testform.domain.model

data class RegistrationData(
    val name: String,
    val phone: String,
    val cpf: String,
    val birthDate: String,
    val gender: String,
    val email: String? = null
)
