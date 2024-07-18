package com.example.boxgame.bind

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.boxgame.R
import com.example.boxgame.utils.Constant.BLUE
import com.example.boxgame.utils.Constant.GREEN
import com.example.boxgame.utils.Constant.RED

/**
 * Bind data used for data binding
 */
class BindAdapters {
    companion object {

        @BindingAdapter("setBoxColor")
        @JvmStatic
        fun setBoxColor(cardView: CardView, color: String?) {
            when (color) {
                RED -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardView.context,
                            R.color.red
                        )
                    )
                }

                GREEN -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardView.context,
                            R.color.green
                        )
                    )
                }

                BLUE -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardView.context,
                            R.color.blue
                        )
                    )
                }

                else -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardView.context,
                            R.color.gray
                        )
                    )
                }

            }

        }


        @BindingAdapter("setTitleColor")
        @JvmStatic
        fun setTitleColor(cardView: CardView, color: String?) {
            when (color) {
                RED -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardView.context,
                            R.color.red
                        )
                    )
                }

                GREEN -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardView.context,
                            R.color.green
                        )
                    )
                }

                BLUE -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardView.context,
                            R.color.blue
                        )
                    )
                }

                else -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(
                            cardView.context,
                            R.color.gray
                        )
                    )
                }

            }

        }
    }
}