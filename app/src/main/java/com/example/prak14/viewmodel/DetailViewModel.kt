package com.example.prak14.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.repository.RepositorySiswa
import com.example.prak14.modeldata.Siswa
import com.example.prak14.view.route.DestinasiDetail
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface StatusUIDetail {
    data class Success(val satusiswa: Siswa?) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    private val idSiswa: String =
        savedStateHandle.get<String>(DestinasiDetail.itemIdArg)
            ?: error("idSiswa tidak ditemukan di SavedStateHandle")

    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        getSatuSiswa()
    }

    fun getSatuSiswa() {
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            statusUIDetail = try {
                val siswa = repositorySiswa.getSatuSiswa(idSiswa)
                StatusUIDetail.Success(siswa)
            } catch (e: Exception) {
                StatusUIDetail.Error
            }
        }
    }


    suspend fun hapusSatuSiswa() {
        try {
            repositorySiswa.hapusSatuSiswa(idSiswa)
            println("Sukses Hapus Data: $idSiswa")
        } catch (e: Exception) {
            println("Gagal Hapus Data: ${e.message}")
        }
    }
}