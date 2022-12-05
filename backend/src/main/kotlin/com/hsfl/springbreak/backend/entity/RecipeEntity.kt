package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Recipe
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "recipe")
data class RecipeEntity(
    @Id @GeneratedValue val id: Long? = null,
    @Column var title: String,
    @Column var shortDescription: String,
    @Column var price: Double,
    @Column var duration: Double,
    // @OneToOne(mappedBy = "recipe", cascade = [CascadeType.ALL]) @PrimaryKeyJoinColumn var rating: RatingEntity?,
    @ManyToOne @JoinColumn(name = "difficulty_id") var difficulty: DifficultyEntity,
    @ManyToOne @JoinColumn(name = "category_id") var category: CategoryEntity,
    @ManyToOne @JoinColumn(name = "users_id") var creator: UserEntity,
    @Column var createTime: LocalDate,
    @Column var image: String,
    @Column var longDescription: String,
    @Column var views: Int
    // @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL]) var ingredients: List<IngredientRecipeEntity>?
    /*
    @Column var longDescription: String,
    @Column var views: Int,
    @ManyToMany(mappedBy = "favoriteRecipe") var userFavorites: List<UserEntity>? = null
     */
) {

    fun toDto(): Recipe = Recipe(
        id = this.id!!,
        title = this.title,
        shortDescription = this.shortDescription,
        price = this.price,
        duration = this.duration,
        difficulty = this.difficulty.toDto(),
        category = this.category.toDto(),
        creator = this.creator.toDto(),
        createTime = this.createTime,
        image = this.image,
        longDescription = this.longDescription,
        views = this.views
        // ingredients = this.ingredients!!.map { it.toIngredient() }
        /*
        rating = this.rating!!.toDto(),
         */
    )

    companion object {
        fun fromDto(dto: Recipe): RecipeEntity = RecipeEntity(
            id = dto.id,
            title = dto.title,
            shortDescription = dto.shortDescription,
            price = dto.price,
            duration = dto.duration,
            difficulty = DifficultyEntity.fromDto(dto.difficulty),
            category = CategoryEntity.fromDto(dto.category),
            creator = UserEntity.fromDto(dto.creator),
            createTime = dto.createTime,
            image = dto.image,
            longDescription = dto.longDescription,
            views = dto.views
            // ingredients = dto.ingredients.map { IngredientRecipeEntity.fromIngredients(it, dto) }
            /*
            rating = RatingEntity.fromDto(dto.rating),
             */
        )
    }
}
