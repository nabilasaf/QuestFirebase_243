package com.example.prak14.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.repository.RepositorySiswa
import com.example.prak14.modeldata.DetailSiswa
import com.example.prak14.modeldata.UIStateSiswa
import com.example.prak14.modeldata.toDataSiswa
import com.example.prak14.modeldata.toUiStateSiswa
import com.example.prak14.view.route.DestinasiDetail
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    // ✅ FIX: ID STRING (BUKAN LONG)
    private val idSiswa: String =
        savedStateHandle.get<String>(DestinasiDetail.itemIdArg)
            ?: error("idSiswa tidak ditemukan di SavedStateHandle")

    init {
        viewModelScope.launch {
            val siswa = repositorySiswa.getSatuSiswa(idSiswa)
            if (siswa != null) {
                uiStateSiswa = siswa.toUiStateSiswa(isEntryValid = true)
            }
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    private fun validasiInput(
        detailSiswa: DetailSiswa = uiStateSiswa.detailSiswa
    ): Boolean {
        return detailSiswa.nama.isNotBlank()
                && detailSiswa.alamat.isNotBlank()
                && detailSiswa.telpon.isNotBlank()
    }

    suspend fun editSatuSiswa() {
        if (validasiInput()) {
            try {
                repositorySiswa.editSatuSiswa(
                    idSiswa,
                    uiStateSiswa.detailSiswa.toDataSiswa()
                )
                println("Update Sukses: $idSiswa")
            } catch (e: Exception) {
                println("Update Error: ${e.message}")
            }
        }
    }
}
