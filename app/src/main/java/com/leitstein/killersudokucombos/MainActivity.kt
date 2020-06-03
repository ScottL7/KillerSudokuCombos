package com.leitstein.killersudokucombos

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.leitstein.killersudokucombos.ComboListGenerator.*
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName.toString()

    private var currGridSize : Int = 6

    // These depend on the cage size
    private var minCageSum = 0
    private var maxCageSum = 0

    private var allCombosList = mutableListOf<ItemType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Valid grid sizes, used for the spinner
        val gridSizes = resources.getStringArray(R.array.valid_grid_sizes)

//        val test = ComboListGenerator(6).buildComboList()
//
//        val iter = test.iterator()
//        while (iter.hasNext()) {
//            val currItem = iter.next()
//            Log.i(TAG, "Sum: ${currItem.sum}, List: ${currItem.listOfDigits}")
//            println("Sum: ${currItem.sum}, List: ${currItem.listOfDigits}")
//        }
//
//
//        textViewResults.text = getResultsText()

        spinnerGridSize.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, gridSizes)

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
                currGridSize = gridSizes[position].toInt()
                minCageSum = 0
                maxCageSum = 0
                editTextCageSize.setText("")
                editTextCageSum.setText("")
                updateCageHints()
                Log.i(TAG, "Grid sized changed: Grid size = $currGridSize" +
                        "Grid sized changed: Cage Size Hint = '${textViewCageSizeHint.text.toString()}'")

                doAsync {
                    allCombosList = ComboListGenerator(currGridSize).buildComboList()
                }

                Toast.makeText(this@MainActivity, getString(R.string.grid_size_toast, currGridSize, currGridSize) , Toast.LENGTH_LONG).show()
                editTextCageSize.requestFocus()
4            }

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

//        editTextCageSize.filters = arrayOf(InputFilterMinMax(2, currGridSize))
        editTextCageSize.setOnFocusChangeListener { v, _ ->
            val currVal = editTextCageSize.text.toString()
            if (currVal != "") {
                val currCageSize = currVal.toInt()
                if ((currCageSize < 2) or (currCageSize > currGridSize)) {
                    editTextCageSize.setText("")
                    minCageSum = 0
                    maxCageSum = 0
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.invalid_cage_size, currGridSize),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    minCageSum = (1..currCageSize).sum()
                    maxCageSum = ((currGridSize-currCageSize+1)..currGridSize).sum()
                    if (minCageSum == maxCageSum) editTextCageSum.setText(minCageSum.toString())
                    updateCageHints()
                }
            }
            hideSoftKeyboard(this@MainActivity, v)
        }

//        editTextCageSum.filters = arrayOf(InputFilterMinMax(3, maxCageSum))
        editTextCageSum.setOnFocusChangeListener { v, _ ->
            val currVal = editTextCageSum.text.toString()
            if (currVal != "") {
                val currCageSum = currVal.toInt()
                if ((currCageSum < minCageSum) or (currCageSum > maxCageSum)) {
                    editTextCageSum.setText("")
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.invalid_cage_sum,
                            if (minCageSum == 0) "?" else minCageSum.toString(),
                            maxCageSum.toString()),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            hideSoftKeyboard(this@MainActivity, v)
        }

        buttonShowCombos.setOnClickListener {
            updateResultsText()
        }
    }

    private fun updateCageHints() {
        textViewCageSizeHint.text = getString(R.string.cage_size_hint, 2, currGridSize)
        textViewCageSumHint.text = getString(R.string.cage_sum_hint,
                                    if (minCageSum == 0) "?" else minCageSum.toString(),
                                    if (maxCageSum == 0) "?" else maxCageSum.toString())
    }

    fun updateResultsText() {

        var results = Html.fromHtml(getString(R.string.results), Html.FROM_HTML_MODE_LEGACY)
        textViewResults.text = results

    }

    /**
     * Hides the Soft Keyboard on demand
     *
     * @param activity the activity from which to get the IMM
     * @param view the view from which to provide a windowToken
     */
    fun hideSoftKeyboard(activity: Activity, view: View?) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}