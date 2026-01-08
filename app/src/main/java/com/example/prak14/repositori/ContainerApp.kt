package com.example.prak14.repositori

import android.app.Application
import com.example.myfirebase.repository.FirebaseRepositorySiswa
import com.example.myfirebase.repository.RepositorySiswa
import com.google.firebase.Firebase

interface ContainerApp{
    val repositorySiswa: RepositorySiswa
}

class DefaultContainerApp : ContainerApp{
    override val repositorySiswa: RepositorySiswa by lazy {
        FirebaseRepositorySiswa()
    }
}

class AplikasiDataSiswa : Application(){
    lateinit var container: ContainerApp
    override fun onCreate() {
        super.onCreate()
        this.container = DefaultContainerApp()

    }
}