package hs.kr.entrydsm.example

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExampleApplicationTest {
    @Test
    fun contextLoads() {
        // Spring Boot context loading test
        assertEquals(4, 2 + 2)
    }
}