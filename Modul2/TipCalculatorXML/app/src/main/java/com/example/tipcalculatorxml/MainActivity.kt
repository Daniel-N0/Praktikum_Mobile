    package com.example.tipcalculatorxml

    import android.os.Bundle
    import android.widget.ArrayAdapter
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.widget.doAfterTextChanged
    import com.example.tipcalculatorxml.databinding.ActivityMainBinding
    import java.text.NumberFormat
    import java.util.Locale

    class MainActivity : AppCompatActivity() {

        private lateinit var binding: ActivityMainBinding
        private var selectedTipPercent: Double = 0.15

        companion object {
            private const val KEY_SELECTED_TIP = "selected_tip"
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            if (savedInstanceState != null) {
                selectedTipPercent = savedInstanceState.getDouble(KEY_SELECTED_TIP, 0.15)
            }

            setupDropdown()
            setupListeners()

            calculateTip()
        }

        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            outState.putDouble(KEY_SELECTED_TIP, selectedTipPercent)
        }

        private fun setupDropdown() {
            val options = arrayOf("15%", "18%", "20%")
            val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, options)

            with(binding.tipOptions) {
                isSaveEnabled = false
                setAdapter(adapter)

                val defaultText = when (selectedTipPercent) {
                    0.15 -> "15%"
                    0.18 -> "18%"
                    0.20 -> "20%"
                    else -> "15%"
                }
                setText(defaultText, false)

                setOnItemClickListener { parent, _, position, _ ->
                    val selectedText = parent.getItemAtPosition(position).toString()

                    selectedTipPercent = when (selectedText) {
                        "15%" -> 0.15
                        "18%" -> 0.18
                        "20%" -> 0.20
                        else -> 0.15
                    }

                    calculateTip()
                }
            }
        }

        private fun setupListeners() {
            binding.costOfServiceEditText.doAfterTextChanged {
                calculateTip()
            }

            binding.roundUpSwitch.setOnCheckedChangeListener { _, _ ->
                calculateTip()
            }
        }

        private fun calculateTip() {
            val textInput = binding.costOfServiceEditText.text.toString()
            val cost = textInput.replace(",", ".").toDoubleOrNull() ?: 0.0

            var tip = cost * selectedTipPercent

            if (binding.roundUpSwitch.isChecked) {
                tip = kotlin.math.ceil(tip)
            }

            val formattedTip = NumberFormat.getCurrencyInstance(Locale.US).format(tip)
            binding.tipResult.text = "Tip Amount: $formattedTip"
        }
    }