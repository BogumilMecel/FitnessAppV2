package com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation

import androidx.navigation.NavOptions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.NavigationAction
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.util.BottomBarScreen
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.util.Screen
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.util.AuthScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.google.gson.Gson

object NavigationActions {

    object General {
        fun navigateUp() = object : NavigationAction {
            override val destination: String = "navigateUp"
        }

        fun resetScreen() = object : NavigationAction {
            override val destination: String = "reset"
        }
    }

    //Auth
    object LoginScreen {
        fun loginToRegister() = object : NavigationAction {
            override val destination: String = AuthScreen.RegisterAuthScreen.route
        }

        fun loginToReset() = object : NavigationAction {
            override val destination: String = AuthScreen.ResetPasswordAuthScreen.route
        }

        fun loginToLoading() = object : NavigationAction {
            override val destination: String = Screen.LoadingScreen.route
            override val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(0, true).build()
        }
    }

    object RegisterScreen {
        fun registerToLogin() = object : NavigationAction {
            override val destination: String
                get() = AuthScreen.LoginAuthScreen.route
        }

        fun registerToLoading() = object : NavigationAction {
            override val destination: String = Screen.LoadingScreen.route
            override val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(0, true).build()
        }

    }

    object ResetScreen {
        fun resetToLogin() = object : NavigationAction {
            override val destination: String = AuthScreen.LoginAuthScreen.route
        }
    }


    //Introducion
    object IntroductionScreen {
        fun introductionToSummary() = object : NavigationAction {
            override val destination: String = BottomBarScreen.Summary.route
            override val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(0, true).build()
        }
    }


    //Loading
    object LoadingScreen {
        fun loadingToIntroduction() = object : NavigationAction {
            override val destination: String = Screen.IntroductionScreen.route
            override val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(0, true).build()
        }

        fun loadingToLogin() = object : NavigationAction {
            override val destination: String = AuthScreen.LoginAuthScreen.route
            override val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(0, true).build()
        }

        fun loadingToSummary() = object : NavigationAction {
            override val destination: String = BottomBarScreen.Summary.route
            override val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(0, true).build()
        }
    }

    //Summary
    object SummaryScreen {
    }

    //Account
    object AccountScreen {
        fun accountToLogin() = object : NavigationAction {
            override val destination: String = AuthScreen.LoginAuthScreen.route
        }
    }

    //Diary
    object DiaryScreen {
        fun diaryToSearch(mealName: String) = object : NavigationAction {
            override val destination: String = Screen.SearchScreen.route + "?mealName=$mealName"
        }
    }


    //Search
    object SearchScreen {
        fun searchToNewProduct(mealName: String = "Breakfast", barcode: String? = null) =
            object : NavigationAction {
                override val destination: String = Screen.NewProductScreen.route + "?mealName=$mealName" + "&barcode=$barcode"
            }

        fun searchToProduct(product: Product, mealName: String) = object :
            NavigationAction {
            override val destination: String = Screen.ProductScreen.route + "?mealName=$mealName" + "&product=${
                    Gson().toJson(product)
                }"
        }

        fun searchToNewRecipe() = object : NavigationAction {
            override val destination: String = Screen.NewRecipeScreen.route
        }
    }


    //Product
    object ProductScreen {
        fun productToDiary() = object : NavigationAction {
            override val destination: String = BottomBarScreen.Diary.route
            override val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(0, true).build()
        }
    }

    //New Product
    object NewProductScreen {
        fun newProductToProduct(
            mealName: String,
            product: Product
        ) = object : NavigationAction {
            override val destination: String =
                Screen.ProductScreen.route + "?mealName=$mealName" + "&product=${
                    Gson().toJson(product)
                }" 
        }
    }

    //New recipe
    object NewRecipeScreen {
        fun newRecipeToProduct(
            recipe: Recipe,
            product: Product,
            mealName: String
        ) = object : NavigationAction {
            override val destination: String =
                Screen.ProductScreen.route + "?mealName=$mealName" + "&product=${
                    Gson().toJson(product)
                }" + "recipe=${
                    Gson().toJson(recipe)
                }"
        }
    }
}