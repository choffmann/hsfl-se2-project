package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Recipe
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "recipe")
data class RecipeEntity(
    @Id @GeneratedValue val id: Long? = null,
    @Column val title: String,
    @Column val shortDescription: String,
    @Column val price: Double,
    @Column val duration: Double,
    @OneToOne(mappedBy = "recipe", cascade = [CascadeType.ALL]) @PrimaryKeyJoinColumn val rating: RatingEntity,
    @ManyToOne val difficulty: DifficultyEntity,
    @ManyToOne val category: CategoryEntity,
    @ManyToOne val creator: UserEntity,
    @Column val createTime: LocalDate,
    @OneToMany(mappedBy = "ingredient") val ingredients: List<IngredientRecipeEntity>,
    @Column val image: String,
    @Column val longDescription: String,
    @Column val views: Int,
    @ManyToMany(mappedBy = "favoriteRecipe") val userFavorites: List<UserEntity>? = null
) {

    fun toDto(): Recipe = Recipe(
        id = this.id!!,
        title = this.title,
        shortDescription = this.shortDescription,
        price = this.price,
        rating = this.rating.toDto(),
        duration = this.duration,
        difficulty = this.difficulty.toDto(),
        category = this.category.toDto(),
        creator = this.creator.toDto(),
        createTime = this.createTime,
        ingredients = this.ingredients.map { it.toIngredient() },
        image = this.image,
        longDescription = this.longDescription,
        views = this.views
    )

    companion object {
        fun fromDto(dto: Recipe): RecipeEntity = RecipeEntity(
            id = dto.id,
            title = dto.title,
            shortDescription = dto.shortDescription,
            price = dto.price,
            rating = RatingEntity.fromDto(dto.rating),
            duration = dto.duration,
            difficulty = DifficultyEntity.fromDto(dto.difficulty),
            category = CategoryEntity.fromDto(dto.category),
            creator = UserEntity.fromDto(dto.creator),
            createTime = dto.createTime,
            ingredients = dto.ingredients.map { IngredientRecipeEntity.fromIngredients(it, dto) },
            image = dto.image,
            longDescription = dto.longDescription,
            views = dto.views
        )
    }
}
