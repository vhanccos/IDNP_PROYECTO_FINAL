package com.example.myapplication001.data.repository

sealed class SyncResult {
    object Idle : SyncResult()
    object Syncing : SyncResult()
    object Success : SyncResult()
    data class Error(val message: String) : SyncResult()
}