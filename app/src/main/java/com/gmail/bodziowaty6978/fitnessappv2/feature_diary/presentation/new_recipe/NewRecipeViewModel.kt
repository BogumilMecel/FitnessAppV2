package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe

import androidx.lifecycle.ViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewRecipeViewModel @Inject constructor(
    private val navigator: Navigator
): ViewModel(){
    private val _state = MutableStateFlow(NewRecipeState())
    val state : StateFlow<NewRecipeState> = _state

    init {
        _state.update {
            it.copy(
                times = listOf("15 min","30 min","45 min","60+ min"),
                difficulties = listOf("1","2","3","4","5")
            )
        }
    }

    fun onEvent(event: NewRecipeEvent){
        when(event){
            is NewRecipeEvent.ClickedTimeArrow -> {
                _state.update {
                    it.copy(
                        isTimeExpanded = !it.isTimeExpanded
                    )
                }
            }
            is NewRecipeEvent.ClickedDifficultyArrow -> {
                _state.update {
                    it.copy(
                        isDifficultyExpanded = !it.isDifficultyExpanded
                    )
                }
            }
            is NewRecipeEvent.ClickedServingsArrow -> {
                _state.update {
                    it.copy(
                        isServingsExpanded = !it.isServingsExpanded
                    )
                }
            }
            is NewRecipeEvent.ClickedBackArrow -> {
                if (_state.value.isSearchSectionVisible){
                    _state.update {
                        it.copy(
                            isSearchSectionVisible = false
                        )
                    }
                }else{
                    navigator.navigate(NavigationActions.General.navigateUp())
                }
            }
            is NewRecipeEvent.SelectedDifficulty -> {
                _state.update {
                    it.copy(
                        selectedDifficulty = it.difficulties[event.index],
                        isDifficultyExpanded = !it.isDifficultyExpanded
                    )
                }
            }
            is NewRecipeEvent.EnteredServing -> {
                _state.update {
                    it.copy(
                        servings = event.value,
                        isServingsExpanded = !it.isServingsExpanded
                    )
                }
            }
            is NewRecipeEvent.SelectedTime -> {
                _state.update {
                    it.copy(
                        selectedTime = it.times[event.index].split(" ").getOrNull(0) ?: "15",
                        isTimeExpanded = !it.isTimeExpanded
                    )
                }
            }
            is NewRecipeEvent.EnteredName -> {
                _state.update {
                    it.copy(
                        name = event.value,
                    )
                }
            }
            is NewRecipeEvent.ClickedAddNewIngredient -> {
                _state.update {
                    it.copy(
                        isSearchSectionVisible = true
                    )
                }
            }
            is NewRecipeEvent.ClickedProduct -> {

            }
        }
    }
    private fun updateNutritionData(){

    }
}