package com.rcorchero.domain.exception

sealed class Failure {
    object DomainError : Failure()
    object ServerError : Failure()
}