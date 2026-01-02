package com.example.prak14.view.route

import com.example.prak14.R

object DestinasiEdit: DestinasiNavigasi {
    override val route = "item_edit"
    override val titleRes = R.string.edit_siswa
    const val itemIdArg = "idSiswa"
    val routeWithArgs = "$route/{$itemIdArg}"
}