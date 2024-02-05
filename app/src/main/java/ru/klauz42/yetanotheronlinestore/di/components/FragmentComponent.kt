package ru.klauz42.yetanotheronlinestore.di.components

import dagger.Component
import ru.klauz42.yetanotheronlinestore.di.scopes.FragmentScope
import ru.klauz42.yetanotheronlinestore.presentation.catalog.CatalogFragment
import ru.klauz42.yetanotheronlinestore.presentation.favorites.FavoritesFragment
import ru.klauz42.yetanotheronlinestore.presentation.product.ProductFragment
import ru.klauz42.yetanotheronlinestore.presentation.profile.ProfileFragment


@FragmentScope
@Component(
    dependencies = [ActivityComponent::class],
)
interface FragmentComponent {
    fun inject(fragment: CatalogFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: ProductFragment)
    fun inject(fragment: ProfileFragment)

    @Component.Builder
    interface Builder {
        fun activityComponent(activityComponent: ActivityComponent): Builder
        fun build(): FragmentComponent
    }
}