import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CalculatorTest {

    private val calculator = Calculator()

    @Test
    fun testAddition() {
        val expression = "3 + 2"
        val result = calculator.evaluate(expression)
        assertEquals(5.0, result)
    }

    @Test
    fun testSubtraction() {
        val expression = "5 - 3"
        val result = calculator.evaluate(expression)
        assertEquals(2.0, result)
    }

    @Test
    fun testMultiplication() {
        val expression = "4 * 2"
        val result = calculator.evaluate(expression)
        assertEquals(8.0, result)
    }

    @Test
    fun testDivision() {
        val expression = "8 / 2"
        val result = calculator.evaluate(expression)
        assertEquals(4.0, result)
    }

    @Test
    fun testPower() {
        val expression = "2 ^ 3"
        val result = calculator.evaluate(expression)
        assertEquals(8.0, result)
    }

    @Test
    fun testSquareRoot() {
        val expression = "sqrt 16"
        val result = calculator.evaluate(expression)
        assertEquals(4.0, result)
    }

    @Test
    fun testExponential() {
        val expression = "exp 1"
        val result = calculator.evaluate(expression)
        assertEquals(Math.exp(1.0), result)
    }

    @Test
    fun testCombinedOperations() {
        val expression = "( 454 + ( 34 / 2 ) ^ 3 ) + 5"
        val result = calculator.evaluate(expression)
        assertEquals(12448.0, result)
    }

    @Test
    fun testDivisionByZero() {
        val expression = "5 / 0"
        val exception = assertThrows<ArithmeticException> {
            calculator.evaluate(expression)
        }
        assertEquals("División por cero", exception.message)
    }

    @Test
    fun testInvalidExpression() {
        val expression = "5 +"
        val exception = assertThrows<IllegalArgumentException> {
            calculator.evaluate(expression)
        }
        assertEquals("Expresión inválida", exception.message)
    }

    @Test
    fun testParenthesis() {
        val expression = "( 1 + 2 ) * 3"
        val result = calculator.evaluate(expression)
        assertEquals(9.0, result)
    }

    @Test
    fun testNestedParenthesis() {
        val expression = "( 1 + ( 2 * 3 ) ) * 2"
        val result = calculator.evaluate(expression)
        assertEquals(14.0, result)
    }
}
