package com.example.sqldatabasekotlin

data class Todo(var task: String, var done: Boolean) {
    override fun toString(): String {
        return this.task
    }
}

class models {
}