package com.example.testform.domain.usecase

import javax.inject.Inject

class ValidateCadastroUseCase @Inject constructor() {
    /**
     * Valida se os campos obrigatórios foram preenchidos.
     * Mantém exatamente a mesma lógica do projeto original.
     */
    operator fun invoke(name: String, phone: String): Boolean {
        return name.isNotBlank() && phone.isNotBlank()
    }
}
