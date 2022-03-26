package com.chetangani.regexlib.interfaces

interface PermissionCallInterface {
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
}