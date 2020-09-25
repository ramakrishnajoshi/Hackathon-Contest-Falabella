package com.example.dependencyinjection.utils

/*
* Most of the result class hierarchies look pretty similar. So it makes sense to introduce a
* generic class hierarchy.
*
* Note : The superpower of sealed classes is only unleashed if when is used as an expression.
* Otherwise, the compiler doesn’t force us to handle all cases. It make when exhaustive inabsence
* of return, add this piece of code
  val <T> T.exhaustive: T
    get() = this
* and use it like
    when(expression){
    ...
    }.exhaustive
*
* Alternatively, you can use the damn-it operator !! instead of the exhaustive property.
* */
sealed class BaseViewState<out T : Any> {
    data class Success<out T : Any>(val value: T) : BaseViewState<T>()
    data class Error(val message: String, val cause: Exception? = null) : BaseViewState<Nothing>()
}