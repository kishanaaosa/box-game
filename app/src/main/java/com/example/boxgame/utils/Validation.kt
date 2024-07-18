package com.example.boxgame.utils

object Validation {

    /**
     * method is used for checking if string is empty or not.
     *
     * @param mString as String
     * @return boolean true if [mString] is notnull.
     */
    fun isNotNull(mString: String?): Boolean {
        return when {
            mString == null -> {
                false
            }

            mString.equals("", ignoreCase = true) -> {
                false
            }

            mString.equals("N/A", ignoreCase = true) -> {
                false
            }

            mString.equals("[]", ignoreCase = true) -> {
                false
            }

            mString.equals("null", ignoreCase = true) -> {
                false
            }

            mString.isEmpty() -> {
                false
            }

            else -> !mString.equals("{}", ignoreCase = true)
        }
    }

    fun isValidBoxSize(mString: String?): Boolean {
        return mString.toString().trim().toInt() in 5..25
    }

}