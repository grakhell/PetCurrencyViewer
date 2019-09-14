package ru.grakhell.petcurrencyviewer.data

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class State private constructor(
    val status: Status,
    val msg: String? = null,
    val throwable: Throwable? = null
) {
    companion object {
        fun finished() = State(Status.SUCCESS,"Done")
        fun working() = State(Status.RUNNING, "In progress")
        fun error(t:Throwable?) = State(Status.FAILED, t?.message, t)
    }
}