package com.example.testform.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testform.domain.model.RegistrationData
import com.example.testform.domain.usecase.SaveCadastroUseCase
import com.example.testform.domain.usecase.ValidateCadastroUseCase
import com.example.testform.presentation.state.CadastroUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CadastroEvent {
    data object EasterEggTapped : CadastroEvent
    data class EmailChanged(val value: String) : CadastroEvent
    data object ShowEasterEggToastHandled : CadastroEvent
}

@HiltViewModel
class CadastroViewModel @Inject constructor(
    private val validateCadastroUseCase: ValidateCadastroUseCase,
    private val saveCadastroUseCase: SaveCadastroUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CadastroUiState())
    val uiState: StateFlow<CadastroUiState> = _uiState.asStateFlow()

    private var easterEggTapCount = 0

    fun onEvent(event: CadastroEvent) {
        when (event) {
            is CadastroEvent.EasterEggTapped -> {
                if (!_uiState.value.showEmailField) {
                    easterEggTapCount++
                    if (easterEggTapCount == 3) {
                        _uiState.update { 
                            it.copy(
                                showEmailField = true, 
                                showEasterEggToast = true 
                            ) 
                        }
                        easterEggTapCount = 0
                    }
                }
            }
            is CadastroEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.value) }
            }
            is CadastroEvent.ShowEasterEggToastHandled -> {
                _uiState.update { it.copy(showEasterEggToast = false) }
            }
        }
    }

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    fun onPhoneChange(newPhone: String) {
        if (newPhone.all { it.isDigit() }) {
            _uiState.update { it.copy(phone = newPhone.take(11)) }
        }
    }

    fun onCpfChange(newCpf: String) {
        if (newCpf.all { it.isDigit() }) {
            _uiState.update { it.copy(cpf = newCpf.take(11)) }
        }
    }

    fun onBirthDateChange(newDate: String) {
        if (newDate.all { it.isDigit() }) {
            _uiState.update { it.copy(birthDate = newDate.take(8)) }
        }
    }

    fun onGenderChange(newGender: String) {
        _uiState.update { it.copy(selectedGender = newGender) }
    }

    fun onSaveClick() {
        val currentState = _uiState.value
        val isValid = validateCadastroUseCase(currentState.name, currentState.phone)
        
        if (isValid) {
            viewModelScope.launch {
                val data = RegistrationData(
                    name = currentState.name,
                    phone = currentState.phone,
                    cpf = currentState.cpf,
                    birthDate = currentState.birthDate,
                    gender = currentState.selectedGender,
                    email = if (currentState.showEmailField) currentState.email else null
                )
                val success = saveCadastroUseCase(data)
                _uiState.value = CadastroUiState(showDialog = true, dialogSuccess = success)
                easterEggTapCount = 0
            }
        } else {
            _uiState.value = CadastroUiState(showDialog = true, dialogSuccess = false)
            easterEggTapCount = 0
        }
    }

    fun onDismissDialog() {
        _uiState.value = CadastroUiState()
        easterEggTapCount = 0
    }
}
