package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BottomBar
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomSnackbar
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGrey
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation1
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.util.BottomBarScreen
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.util.Screen
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ErrorUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.TAG
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.asLifecycleAwareState
import com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation.AccountScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.login.LoginScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.register.RegisterScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.reset_password.ResetPasswordScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.util.AuthScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary.DiaryScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.edit_nutrition_goals.EditNutritionGoalsScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product.NewProductScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.ProductScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.recipe.RecipeScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.SearchScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.presentation.IntroductionScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.loading.presentation.SplashScreen
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.SummaryScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun NavHostGraph(
    navController: NavHostController = rememberNavController(),
    navigator: Navigator,
//    startDestination: String = Screen.RecipeScreen.route + "?recipe={recipe}",
//    startDestination: String = Screen.LoadingScreen.route,
//    startDestination: String = Screen.NewProductScreen.route + "?mealName={mealName}&barcode={barcode}"
//    startDestination: String = Screen.NewRecipeScreen.route
    startDestination: String = BottomBarScreen.Summary.route
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val navigatorState by navigator.navActions.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = null
    )

    var statusBarColor by remember {
        mutableStateOf(DarkGrey)
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = statusBarColor)

    val scaffoldState = rememberScaffoldState()

    var bottomNavigationState by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(navigatorState) {
        navigatorState?.let {
            it.parcelableArguments.forEach { argument ->
                navController.currentBackStackEntry?.arguments?.putParcelable(
                    argument.key,
                    argument.value
                )
            }
            Log.e(TAG,it.toString())
            if (it.destination == "navigateUp") {
                navController.navigateUp()
            } else {
                Log.e(TAG," navigate")
                navController.navigate(it.destination, it.navOptions)
            }
            statusBarColor =
                if (navController.currentDestination?.route == Screen.SearchScreen.route + "?mealName={mealName}") DarkGreyElevation1 else DarkGrey
        }
    }

    LaunchedEffect(key1 = true) {
        ErrorUtils.errorState.receiveAsFlow().collect {
            scaffoldState.snackbarHostState.showSnackbar(
                message = it
            )
        }
    }

    Scaffold(
        bottomBar = {
            if (bottomNavigationState) {
                BottomBar(navController = navController)
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = { hostState ->
            CustomSnackbar(snackbarHostState = hostState)
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colors.background
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(
                    route = Screen.LoadingScreen.route
                ) {
                    bottomNavigationState = false
                    SplashScreen()
                }

                composable(
                    route = Screen.IntroductionScreen.route
                ) {
                    bottomNavigationState = false
                    IntroductionScreen()
                }

                composable(
                    route = AuthScreen.LoginAuthScreen.route
                ) {
                    bottomNavigationState = false
                    LoginScreen()
                }
                composable(
                    route = AuthScreen.RegisterAuthScreen.route
                ) {
                    bottomNavigationState = false
                    RegisterScreen()
                }
                composable(
                    route = AuthScreen.ResetPasswordAuthScreen.route
                ) {
                    bottomNavigationState = false
                    ResetPasswordScreen()
                }
                composable(
                    route = BottomBarScreen.Summary.route
                ) {
                    Log.e(TAG, "Summary screen")
                    bottomNavigationState = true
                    SummaryScreen()
                }

                composable(
                    route = BottomBarScreen.Diary.route
                ) {
                    bottomNavigationState = true
                    DiaryScreen(
                        paddingValues = paddingValues
                    )
                }

                composable(
                    route = BottomBarScreen.Account.route
                ) {
                    bottomNavigationState = true
                    AccountScreen()
                }
                composable(
                    route = Screen.SearchScreen.route + "?mealName={mealName}",
                    arguments = listOf(
                        navArgument(
                            name = "mealName"
                        ) {
                            type = NavType.StringType
                            defaultValue = "Breakfast"
                        }
                    )
                ) {
                    bottomNavigationState = false
                    SearchScreen()
                }

                composable(
                    route = Screen.ProductScreen.route + "?mealName={mealName}" + "&product={product}",
                    arguments = listOf(
                        navArgument(
                            name = "mealName"
                        ) {
                            type = NavType.StringType
                            defaultValue = "Breakfast"
                        },
                        navArgument(
                            name = "product"
                        ) {
                            type = NavType.StringType
                            defaultValue = null
                            nullable = true
                        }
                    )
                ) { backStackEntry ->
                    ProductScreen(
                        product = backStackEntry.arguments?.getString("product")?.let { productString ->
                            Gson().fromJson(productString, Product::class.java)
                        } ?: Product()
                    )
                }

                composable(
                    Screen.NewProductScreen.route + "?mealName={mealName}&barcode={barcode}",
                    arguments = listOf(
                        navArgument(
                            name = "mealName"
                        ) {
                            type = NavType.StringType
                            defaultValue = "Breakfast"
                        },
                        navArgument(
                            name = "barcode"
                        ) {
                            type = NavType.StringType
                            nullable = true
                            defaultValue = null
                        }
                    )
                ) {
                    NewProductScreen()
                }

                composable(
                    route = Screen.NewRecipeScreen.route
                ) {
                    NewRecipeScreen()
                }

                composable(
                    route = Screen.RecipeScreen.route + "?recipe={recipe}",
                    arguments = listOf(
                        navArgument(
                            name = "recipe"
                        ) {
                            type = NavType.StringType
                            nullable = false
                            defaultValue = Gson().toJson(Recipe(
                                name = "Spaghetti Bolognese",
                                nutritionValues = NutritionValues(
                                    calories = 128,
                                    carbohydrates = 12.2,
                                    protein = 27.6,
                                    fat = 2.5
                                ),
                                servings = 5,
                                ingredients = (1..10).map {
                                    Ingredient(
                                        id = it,
                                        weight = it * 215,
                                        product = Product(
                                            nutritionValues = NutritionValues(
                                                calories = it * 37
                                            ),
                                            name = "abc"
                                        )
                                    )
                                }
                            ))
                        }
                    )
                ) { backStackEntry ->
                    RecipeScreen(
                        recipe = backStackEntry.arguments?.getString("recipe")?.let { recipeString ->
                            Gson().fromJson(recipeString, Recipe::class.java)
                        } ?: Recipe(),
                        mealName = "Breakfast"
                    )
                }

                composable(
                    route = Screen.EditNutritionGoalsScreen.route
                ) {
                    bottomNavigationState = false
                    EditNutritionGoalsScreen()
                }
            }
        }
    }


}