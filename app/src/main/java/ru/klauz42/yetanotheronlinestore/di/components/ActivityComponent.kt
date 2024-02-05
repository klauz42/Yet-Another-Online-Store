package ru.klauz42.yetanotheronlinestore.di.components

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Component
import ru.klauz42.yetanotheronlinestore.di.scopes.ActivityScope
import ru.klauz42.yetanotheronlinestore.domain.models.FavoritesRepository
import ru.klauz42.yetanotheronlinestore.domain.models.ProductsRepository
import ru.klauz42.yetanotheronlinestore.domain.models.UserPreferencesRepository
import ru.klauz42.yetanotheronlinestore.presentation.MainActivity
import ru.klauz42.yetanotheronlinestore.presentation.signin.SignInActivity


@ActivityScope
@Component(
    dependencies = [AppComponent::class],
)
interface ActivityComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: SignInActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: AppCompatActivity): Builder
        fun appComponent(appComponent: AppComponent): Builder

        fun build(): ActivityComponent
    }

    fun getUserDataRepository(): UserPreferencesRepository
    fun getViewModelFactory(): ViewModelProvider.Factory
    fun getProductsRepository(): ProductsRepository
    fun getFavoritesRepository(): FavoritesRepository
}