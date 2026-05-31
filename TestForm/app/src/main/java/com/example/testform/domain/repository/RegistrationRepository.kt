package com.example.testform.domain.repository

import com.example.testform.domain.model.RegistrationData

interface RegistrationRepository {
    suspend fun saveRegistration(data: RegistrationData): Boolean
}
