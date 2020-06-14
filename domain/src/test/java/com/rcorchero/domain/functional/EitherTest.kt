package com.rcorchero.domain.functional

import com.rcorchero.domain.functional.Either.Left
import com.rcorchero.domain.functional.Either.Right
import org.junit.Assert.*
import org.junit.Test

class EitherTest {

    @Test
    fun `Either Right should return correct type`() {
        val result = Right("TVShow")

        assertTrue(Either::class.isInstance(result))
        assertTrue(result.isRight)
        assertFalse(result.isLeft)
        result.fold({},
            { right ->
                assertTrue(String::class.isInstance(right))
                assertEquals("TVShow", right)
            })
    }

    @Test
    fun `Either Left should return correct type`() {
        val result = Left("Error")

        assertTrue(Either::class.isInstance(result))
        assertTrue(result.isLeft)
        assertFalse(result.isRight)
        result.fold(
            { left ->
                assertTrue(String::class.isInstance(left))
                assertEquals("Error", left)
            }, {})
    }

    @Test
    fun `Either fold should ignore passed argument if it is Right type`() {
        val result = Right("Right").getOrElse("Other")

        assertEquals("Right", result)
    }

    @Test
    fun `Either fold should return argument if it is Left type`() {
        val result = Left("Left").getOrElse("Other")

        assertEquals("Other", result)
    }
}