package com.example.boxgame.ui.game.ui

import com.example.boxgame.MainActivity
import com.example.boxgame.R
import com.example.boxgame.base.FragmentBase
import com.example.boxgame.base.ToolbarModel
import com.example.boxgame.bind.GenericRecyclerViewAdapter
import com.example.boxgame.databinding.FragmentGameBinding
import com.example.boxgame.databinding.ItemBoxBinding
import com.example.boxgame.ui.game.model.BoxModel
import com.example.boxgame.ui.game.viewmodel.GameViewModel
import com.example.boxgame.utils.CommonFunctionHelper
import com.example.boxgame.utils.Constant.BLUE
import com.example.boxgame.utils.Constant.GREEN
import com.example.boxgame.utils.Constant.RED
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : FragmentBase<GameViewModel, FragmentGameBinding>() {
    override fun getLayoutId() = R.layout.fragment_game

    override fun setupToolbar() {
        (activity as MainActivity).setStatusBarColor(R.color.black, false)
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true, title = "Game Screen"
            )
        )
    }

    override fun initializeScreenVariables() {
        getDataBinding().viewModel = viewModel
        viewModel.initVariables()
        selectDefaultBox()
        setUpBoxRecyclerView()
    }

    private fun selectDefaultBox() {
        val randomModel = viewModel.boxList.randomOrNull()
        if (randomModel != null) {
            randomModel.isBoxSelected = true
            randomModel.boxBgColor = RED
            randomModel.isClickable = true
            viewModel.boxList[randomModel.boxIndex] = randomModel
            viewModel.colorCount = 1
        }
    }

    private fun setUpBoxRecyclerView() {
        getDataBinding().rvBox.adapter = object :
            GenericRecyclerViewAdapter<BoxModel, ItemBoxBinding>(
                requireContext(),
                viewModel.boxList
            ) {
            override val layoutResId: Int
                get() = R.layout.item_box

            override fun onItemClick(model: BoxModel, position: Int) {
                if (model.isClickable) {
                   /* model.isClickable = false
                    updateItem(model, model.boxIndex)*/



                    if (viewModel.colorCount != viewModel.boxList.size) {
                        val randomModel = getNextRandomModel()?.boxIndex?.let { getItem(it) }
                        if (randomModel != null) {
                            model.isClickable = false
                            updateItem(model, model.boxIndex)
                            viewModel.colorCount += 1
                            randomModel.boxBgColor = viewModel.selectionColor.value.toString()
                            randomModel.isClickable = true
                            randomModel.isBoxSelected =true
                            updateItem(randomModel, randomModel.boxIndex)
                        }
                    }
                    else{
                        viewModel.colorCount = 0
                        viewModel.boxList.forEach {
                            it.isBoxSelected =false
                            val tempModel = getItem(it.boxIndex)
                            tempModel.isBoxSelected = false
                            updateItem(tempModel, tempModel.boxIndex)
                        }

                        if (viewModel.selectionColor.value.toString() == RED) {
                            viewModel.selectionColor.value = GREEN
                            val randomModel = getNextRandomModel()?.boxIndex?.let { getItem(it) }
                            if (randomModel != null) {
                                model.isClickable = false
                                updateItem(model, model.boxIndex)
                                viewModel.colorCount += 1
                                randomModel.boxBgColor = viewModel.selectionColor.value.toString()
                                randomModel.isClickable = true
                                randomModel.isBoxSelected =true
                                updateItem(randomModel, randomModel.boxIndex)
                            }
                        } else if (viewModel.selectionColor.value == GREEN) {
                            viewModel.selectionColor.value = BLUE
                            val randomModel = getNextRandomModel()?.boxIndex?.let { getItem(it) }
                            if (randomModel != null) {
                                model.isClickable = false
                                updateItem(model, model.boxIndex)
                                viewModel.colorCount += 1
                                randomModel.boxBgColor = viewModel.selectionColor.value.toString()
                                randomModel.isClickable = true
                                randomModel.isBoxSelected =true
                                updateItem(randomModel, randomModel.boxIndex)
                            }
                        } else if (viewModel.selectionColor.value == BLUE) {
                            viewModel.showSnackbarMessage("Game Level Completed")
                            (activity as MainActivity).onBackClick()
                        }
                    }


                }
            }

            override fun onBindData(model: BoxModel, position: Int, dataBinding: ItemBoxBinding) {
                dataBinding.model = model
                CommonFunctionHelper.setFadeAnimation(dataBinding.root)
                dataBinding.executePendingBindings()
            }

        }
    }

    private fun getNextRandomModel(): BoxModel? {
        val randomModel = viewModel.boxList.randomOrNull()

        return if (randomModel?.isBoxSelected == false) {
            randomModel
        } else {
            getNextRandomModel()
        }
    }

    override fun getViewModelClass() = GameViewModel::class.java

    override fun getViewModelIsSharedViewModel() = false

}