package com.leitstein.killersudokucombos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

//    private val TAG = javaClass.simpleName.toString()

    private var currGridSize : Int = 6
    private var currCageSize : Int = 2
    private var currCageSum : Int = 0

    // These depend on the cage size
    private var minCageSum = 0
    private var maxCageSum = 0

//    private var allCombosList = mutableListOf<ItemType>()
    private var allCombosList = mapOf<Int, List<ComboListGenerator.ItemType>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateSpinnerList(spinnerGridSize, 4, 9)
        updateSpinnerList(spinnerCageSize, currCageSize, currGridSize)
        updateCageSumRangeAndSpinner()

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
//                currGridSize = gridSizes[position].toInt()
                currGridSize = spinnerGridSize.selectedItem.toString().toInt()
                updateSpinnerList(spinnerCageSize, 2, currGridSize)
                updateCageSumRangeAndSpinner()

                doAsync {
                    allCombosList = ComboListGenerator(currGridSize).comboList()
                }
                Toast.makeText(this@MainActivity, getString(R.string.grid_size_toast, currGridSize, currGridSize) , Toast.LENGTH_LONG).show()
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

        // Set default Grid Size to 6 (3rd position in the string-array
        spinnerGridSize.setSelection(2)

        // -------------------------------------------------------------------------------

        spinnerCageSize.onItemSelectedListener = object : AdapterView.OnItemClickListener,
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
                currCageSize = spinnerCageSize.selectedItem.toString().toInt()
                updateCageSumRangeAndSpinner()
                if (minCageSum == maxCageSum)
                    spinnerCageSum.setSelection(spinnerCageSum.count-1) // Set spinner to last position
                updateResultsText()
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

        // -------------------------------------------------------------------------------
        spinnerCageSum.onItemSelectedListener = object : AdapterView.OnItemClickListener,
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
                currCageSum = spinnerCageSum.selectedItem.toString().toInt()
                updateResultsText()
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

    private fun updateCageSumRangeAndSpinner() {
        minCageSum = (1..currCageSize).sum()
        maxCageSum = ((currGridSize - currCageSize + 1)..currGridSize).sum()
        updateSpinnerList(spinnerCageSum, minCageSum, maxCageSum)
    }

    private fun updateResultsText() {

        var resultsText = getString(R.string.results_header)

        val iterator = allCombosList[currCageSum]?.reversed()?.iterator()
        iterator?.forEach {
            if (it.listOfDigits.size == currCageSize) resultsText += "<li>${it.listOfDigits}"
        }
        val results = Html.fromHtml(resultsText, Html.FROM_HTML_MODE_LEGACY)
        textViewResults.text = results
    }

    private fun updateSpinnerList(spinner: Spinner, start: Int, end: Int) {
        val spinnerList = mutableListOf<String>()
        for (entry in start..end) {
            spinnerList.add(entry.toString())
        }

        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList)
        spinner.setSelection(0)  // Set position to first position by default
        currCageSum = spinner.selectedItem.toString().toInt()
    }
}