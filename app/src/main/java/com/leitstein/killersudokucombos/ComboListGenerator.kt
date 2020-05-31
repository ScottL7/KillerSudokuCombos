package com.leitstein.killersudokucombos

import com.marcinmoskala.math.combinations

internal class ComboListGenerator (val grid_size: Int) {

    lateinit var digits: MutableSet<Int> //= emptyList<Int>() as MutableList<Int>
    var comboList: MutableMap<Int, List<Int>> = mutableMapOf()

    init {
//        val digits: MutableList<Int> = emptyList<Int>() as MutableList<Int>
        for (digit in 1..grid_size) digits.add(digit)
    }

    // Add list of all possible combinations and add their sums to the comboList
    fun add_to_list(lst: List<List<Int>>) {
        for (item in lst) {
            comboList[item.sum()] = item
        }
    }

    fun get_permutations_list(numDigits: Int): Set<Set<Int>> {
        return digits.combinations(numDigits)}
}