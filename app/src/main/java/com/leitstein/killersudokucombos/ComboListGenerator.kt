package com.leitstein.killersudokucombos

import com.marcinmoskala.math.combinations

class ComboListGenerator (private val grid_size: Int) {

    class ItemType (val sum: Int = 0,  val listOfDigits: MutableList<Int>)

    private var digits =  mutableSetOf<Int>()
    private var comboList = mutableListOf<ItemType>()

    init {
        for (digit in 1..grid_size) digits.add(digit)
    }

    // Add list of all possible combinations and add their sums to the comboList
    private fun addToList(lst: Set<Set<Int>>) {
        val lstIterator = lst.iterator()
        while (lstIterator.hasNext()) {
            val item = lstIterator.next().toMutableList()
            item.sort()
            val newItem = ItemType(item.sum(), item)
            comboList.add(newItem)
        }
    }

    private fun getPermutationsList(numDigits: Int): Set<Set<Int>> {
        return digits.combinations(numDigits)
    }

    fun comboList() : Map<Int, List<ItemType>> {
        for (i in 2..grid_size)
            addToList(getPermutationsList(i))
        return comboList.groupBy { it.sum }
    }
}