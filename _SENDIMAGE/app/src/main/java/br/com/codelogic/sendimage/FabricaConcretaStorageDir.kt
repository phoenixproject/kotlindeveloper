package br.com.codelogic.sendimage

import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import java.io.File


public class FabricaConcretaStorageDir {
    // Standard storage location for digital camera files
    public val CAMERA_DIR : String = "/Coletor"

    fun getAlbumStorageDir(albumName: String): File {
        return File(
            Environment.getExternalStorageDirectory().toString() + CAMERA_DIR
        )
    }

    fun getAlbumStorageDir(): String {
        return Environment.getExternalStorageDirectory().toString() + CAMERA_DIR
    }
}