package itcenter.dangara.mycalculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView
    private lateinit var currentExpression: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvExpression = findViewById(R.id.tv_expression)
        tvResult = findViewById(R.id.tv_result)

        val buttons = listOf(
            R.id.btn_0,
            R.id.btn_1,
            R.id.btn_2,
            R.id.btn_3,
            R.id.btn_4,
            R.id.btn_5,
            R.id.btn_6,
            R.id.btn_7,
            R.id.btn_8,
            R.id.btn_9,
            R.id.btn_add,
            R.id.btn_subtract,
            R.id.btn_multiply,
            R.id.btn_divide,
            R.id.btn_decimal
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { appendExpression((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.btn_clear).setOnClickListener { clear() }
        findViewById<Button>(R.id.btn_equals).setOnClickListener { calculate() }
        findViewById<Button>(R.id.btn_percent).setOnClickListener { appendExpression("%") }
        findViewById<Button>(R.id.btn_sign).setOnClickListener { toggleSign() }
    }

    private fun appendExpression(value: String) {
        currentExpression = tvExpression.text.toString()

        // Don't allow consecutive operators or starting with an operator
        if ((value in listOf("+", "-", "×", "÷", "%") && currentExpression.isEmpty()) ||
            (currentExpression.lastOrNull() in listOf('+', '-', '×', '÷', '%') && value in listOf("+", "-", "×", "÷", "%"))
        ) {
            return
        }

        tvExpression.append(value)
    }

    private fun clear() {
        tvExpression.text = ""
        tvResult.text = ""
    }

    private fun toggleSign() {
        currentExpression = tvExpression.text.toString()
        if (currentExpression.isEmpty()) return

        try {
            val value = currentExpression.toDouble()
            val toggledValue = -value
            tvExpression.text = toggledValue.toString()
        } catch (e: Exception) {
            tvResult.text = "Error"
        }
    }

    private fun calculate() {
        try {
            val expression = ExpressionBuilder(
                tvExpression.text.toString().replace("×", "*").replace("÷", "/")
            ).build()

            val result = expression.evaluate()

            tvResult.text = result.toString()
        } catch (e: Exception) {
            tvResult.text = "Error"
        }
    }
}
