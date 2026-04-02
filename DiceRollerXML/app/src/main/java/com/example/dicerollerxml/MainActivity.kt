package com.example.dicerollerxml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val diceLeft = findViewById<ImageView>(R.id.DiceLeft)
        val diceRight = findViewById<ImageView>(R.id.DiceRight)
        val buttonRoll = findViewById<Button>(R.id.buttonRoll)
        val resultMessage = findViewById<TextView>(R.id.resultMessage)

        buttonRoll.setOnClickListener {
           rollDice(diceLeft, diceRight, resultMessage)
        }
    }

    fun rollDice(
        diceLeft: ImageView,
        diceRight: ImageView,
        resultMessage: TextView
    ) {
        val dadu1 = (1..6).random()
        val dadu2 = (1..6).random()

        diceLeft.setImageResource(getDiceImage(dadu1))
        diceRight.setImageResource(getDiceImage(dadu2))

        if (dadu1 == dadu2) {
            resultMessage.text = getString(R.string.msg_double)
        } else {
            resultMessage.text = getString(R.string.msg_not_lucky)
        }
    }

    fun getDiceImage(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_0
        }
    }
}