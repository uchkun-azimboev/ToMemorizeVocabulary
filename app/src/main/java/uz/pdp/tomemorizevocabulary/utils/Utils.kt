package uz.pdp.tomemorizevocabulary.utils

import uz.pdp.tomemorizevocabulary.R

object Utils {

    fun getScoreTitle(stats: Int): Int {
        return when (stats) {
            in 0..54 -> R.string.str_unsatisfactory
            in 55..70 -> R.string.str_satisfactory
            in 71..85 -> R.string.str_good
            else -> R.string.str_excellent
        }
    }

}