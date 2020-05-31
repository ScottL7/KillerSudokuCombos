package com.leitstein.killersudokucombos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Valid grid sizes, used for the spinner
        val gridSizes = resources.getStringArray(R.array.valid_grid_sizes)

        val test = ComboListGenerator(6).buildComboList()

        val iter = test.iterator()
        while (iter.hasNext()) {
            val currItem = iter.next()
            println("Sum: ${currItem.sum}, List: ${currItem.listOfDigits}")
        }

        val results = Html.fromHtml(getString(R.string.results), Html.FROM_HTML_MODE_LEGACY)
        textViewResults.text = results

        spinnerGridSize.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, gridSizes)
        spinnerGridSize.setSelection(2)

        spinnerGridSize.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nothing to do here...
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val size = gridSizes[position].toInt()
                Toast.makeText(this@MainActivity, getString(R.string.grid_size_toast, size, size) , Toast.LENGTH_SHORT).show()
                editTextCageSize.setText("")
                editTextCageSum.setText("")
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                // Nothing to do here...
            }
        }
    }
}