package com.example.testform.data.repository

import com.example.testform.domain.model.RegistrationData
import com.example.testform.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor() : RegistrationRepository {
    override suspend fun saveRegistration(data: RegistrationData): Boolean {
        // Simulação de salvamento bem-sucedido, já que não há persistência real no momento
        return true
    }
}
