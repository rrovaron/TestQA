package com.example.testform.domain.usecase

import com.example.testform.domain.model.RegistrationData
import com.example.testform.domain.repository.RegistrationRepository
import javax.inject.Inject

class SaveCadastroUseCase @Inject constructor(
    private val repository: RegistrationRepository
) {
    suspend operator fun invoke(data: RegistrationData): Boolean {
        return repository.saveRegistration(data)
    }
}
