package com.example.project_kotlin

internal object TaskTracker {
    internal var onStart: () -> Unit = {}
    internal var onStop: () -> Unit = {}

    internal fun reset() {
        onStart = {}
        onStop = {}
    }
}