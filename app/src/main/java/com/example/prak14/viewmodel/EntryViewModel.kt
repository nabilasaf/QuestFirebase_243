package com.example.prak14.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myfirebase.repositori.RepositorySiswa
import com.example.prak14.modeldata.DetailSiswa
import com.example.prak14.modeldata.UIStateSiswa
import com.example.prak14.modeldata.toDataSiswa

class EntryViewModel(private val repositorySiswa: RepositorySiswa): ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa ): Boolean
    {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(
                detailSiswa = detailSiswa, isEntryValid = validasiInput
                    (detailSiswa)
            )
    }

    /* Fungsi untuk menyimpan data yang di-entry */
    suspend fun addSiswa() {
        if (validasiInput()) {
            repositorySiswa.postDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
        }
    }
}