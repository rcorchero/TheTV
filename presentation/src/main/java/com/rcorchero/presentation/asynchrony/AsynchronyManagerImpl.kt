package com.rcorchero.presentation.asynchrony

import com.rcorchero.domain.functional.Either
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AsynchronyManagerImpl(
    override val coroutineContext: CoroutineContext,
    private val io: CoroutineContext
) : AsynchronyManager, CoroutineScope {

    override fun <A, B> launch(
        function: suspend () -> Either<A, B>,
        error: (A) -> Unit,
        success: (B) -> Unit
    ): Job =
        launch {
            io { function() }.fold(fnL = { error(it) }, fnR = { success(it) })
        }

    private suspend fun <T> io(block: suspend CoroutineScope.() -> T): T =
        withContext(io) { block() }

    override fun cancel() = coroutineContext.cancelChildren()
}