package com.rcorchero.domain.exception

sealed class Failure
object CacheError : Failure()
object ServerError : Failure()