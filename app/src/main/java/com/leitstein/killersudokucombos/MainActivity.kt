package com.leitstein.killersudokucombos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        spinnerGridSize.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, gridSizes)
        spinnerGridSize.setSelection(3)

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
                Toast.makeText(this@MainActivity, gridSizes[position] , Toast.LENGTH_SHORT).show()
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@MainActivity, gridSizes[position] , Toast.LENGTH_SHORT).show()
            }

        }
    }
}