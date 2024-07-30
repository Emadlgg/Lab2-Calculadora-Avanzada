import java.util.Stack

class Calculator {
    // Método principal para evaluar una expresión
    fun evaluate(expression: String): Double {
        val rpn = infixToRPN(expression)
        return evaluateRPN(rpn)
    }

    // Convertir expresión infija a notación polaca inversa (RPN)
    private fun infixToRPN(expression: String): List<String> {
        val output = mutableListOf<String>()
        val operators = Stack<String>()
        val tokens = expression.replace("(", " ( ").replace(")", " ) ").split(" ")

        val precedence = mapOf(
            "+" to 1,
            "-" to 1,
            "*" to 2,
            "/" to 2,
            "^" to 3,
            "sqrt" to 3,
            "exp" to 3
        )

        for (token in tokens) {
            when {
                token.isEmpty() -> continue
                token.toDoubleOrNull() != null -> output.add(token)
                token == "(" -> operators.push(token)
                token == ")" -> {
                    while (operators.isNotEmpty() && operators.peek() != "(") {
                        output.add(operators.pop())
                    }
                    if (operators.isEmpty() || operators.pop() != "(") {
                        throw IllegalArgumentException("Paréntesis desbalanceados")
                    }
                }
                token in precedence.keys -> {
                    while (operators.isNotEmpty() && operators.peek() != "(" &&
                        precedence[operators.peek()]!! >= precedence[token]!!
                    ) {
                        output.add(operators.pop())
                    }
                    operators.push(token)
                }
                else -> throw IllegalArgumentException("Token desconocido: $token")
            }
        }

        while (operators.isNotEmpty()) {
            val op = operators.pop()
            if (op == "(" || op == ")") {
                throw IllegalArgumentException("Paréntesis desbalanceados")
            }
            output.add(op)
        }

        return output
    }

    // Evaluar expresión en RPN
    private fun evaluateRPN(tokens: List<String>): Double {
        val stack = Stack<Double>()

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                token == "+" -> stack.push(stack.pop() + stack.pop())
                token == "-" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(a - b)
                }
                token == "*" -> stack.push(stack.pop() * stack.pop())
                token == "/" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    if (b == 0.0) throw ArithmeticException("División por cero")
                    stack.push(a / b)
                }
                token == "^" -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(Math.pow(a, b))
                }
                token == "sqrt" -> stack.push(Math.sqrt(stack.pop()))
                token == "exp" -> stack.push(Math.exp(stack.pop()))
                else -> throw IllegalArgumentException("Operador desconocido: $token")
            }
        }

        return if (stack.size == 1) stack.pop() else throw IllegalArgumentException("Expresión inválida")
    }
}

fun main() {
    val calculator = Calculator()
    val expression = "( 454 + ( 34 / 2 ) ^ 3 ) + 5"
    try {
        val result = calculator.evaluate(expression)
        println("Resultado: $result")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
