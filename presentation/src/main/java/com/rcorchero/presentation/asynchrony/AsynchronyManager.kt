package com.rcorchero.presentation.asynchrony

import com.rcorchero.domain.functional.Either
import kotlinx.coroutines.Job

interface AsynchronyManager {

    fun <A, B> launch(
        function: suspend () -> Either<A, B>,
        error: (A) -> Unit = {},
        success: (B) -> Unit = {}
    ): Job

    fun cancel()
}