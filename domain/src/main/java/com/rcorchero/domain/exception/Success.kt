package com.rcorchero.domain.exception

sealed class Success
object SaveSuccess : Success()
object DeleteSuccess : Success()