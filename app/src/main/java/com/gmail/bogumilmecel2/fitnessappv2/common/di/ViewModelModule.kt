package com.gmail.bogumilmecel2.fitnessappv2.common.di

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetDiaryEntriesListFromResponseUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.SortDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_product.SaveNewProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @ViewModelScoped
    @Provides
    fun provideGetDiaryEntriesListFromResponseUseCase():
            GetDiaryEntriesListFromResponseUseCase = GetDiaryEntriesListFromResponseUseCase()

    @ViewModelScoped
    @Provides
    fun provideGetDiaryEntriesUseCase(
        diaryRepository: DiaryRepository,
        getDiaryEntriesListFromResponseUseCase: GetDiaryEntriesListFromResponseUseCase
    ): GetDiaryEntriesUseCase = GetDiaryEntriesUseCase(
        diaryRepository = diaryRepository,
        sortDiaryEntriesUseCase = SortDiaryEntriesUseCase(),
        getDiaryEntriesListFromResponseUseCase = getDiaryEntriesListFromResponseUseCase
    )

    @ViewModelScoped
    @Provides
    fun provideSaveNewProductUseCase(
        diaryRepository: DiaryRepository,
        resourceProvider: ResourceProvider
    ): SaveNewProductUseCase = SaveNewProductUseCase(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider
    )
}