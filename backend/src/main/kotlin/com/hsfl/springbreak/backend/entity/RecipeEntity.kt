package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Recipe
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "recipe")
data class RecipeEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column var title: String,
    @Column(columnDefinition = "TEXT") var shortDescription: String?,
    @Column var price: Double?,
    @Column var duration: Int?,
    @ManyToOne @JoinColumn(name = "difficulty_id") var difficulty: DifficultyEntity,
    @ManyToOne @JoinColumn(name = "category_id") var category: CategoryEntity,
    @ManyToOne @JoinColumn(name = "users_id") var creator: UserEntity,
    @Column var createTime: LocalDateTime,
    @Column @Lob var image: String? = null,
    @Column(columnDefinition = "TEXT") var longDescription: String?,
    @Column var views: Int = 0,
    @Column var score: Double = 0.00,
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL]) var ratings: List<RatingEntity>? = listOf(),
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL]) var ingredients: List<IngredientRecipeEntity>?,
    @ManyToMany(mappedBy = "favorites") var userFavorites: List<UserEntity>? = listOf()
) {

    fun toDto(): Recipe = Recipe(id = this.id!!,
        title = this.title,
        shortDescription = this.shortDescription!!,
        price = this.price!!,
        duration = this.duration!!,
        difficulty = this.difficulty.toDto(),
        category = this.category.toDto(),
        creator = this.creator.toDto(),
        createTime = this.createTime,
        image = this.image?.let { "http://localhost:8080/api/recipe/image/${this.id}.png" },
        longDescription = this.longDescription,
        views = this.views,
        score = this.score,
        ratings = this.ratings!!.map { it.toDto() },
        ingredients = this.ingredients!!.map { it.toDto() }
    )

    fun toResponse(): Recipe.Response = Recipe.Response(
        id = this.id!!,
        title = this.title,
        shortDescription = this.shortDescription!!,
        price = this.price!!,
        duration = this.duration!!,
        difficulty = this.difficulty.toDto(),
        category = this.category.toDto(),
        creator = this.creator.toResponse(),
        createTime = this.createTime,
        image = this.image?.let { "http://localhost:8080/api/recipe/image/${this.id}.png" },
        longDescription = this.longDescription,
        views = this.views,
        ingredients = this.ingredients!!.map { it.toResponse() },
        score = this.score
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
            views = dto.views,
            ingredients = null
        )

        fun fromDto(
            recipe: Recipe.CreateRecipe,
            user: UserEntity,
            category: CategoryEntity,
            difficulty: DifficultyEntity,
            date: LocalDateTime
        ): RecipeEntity = RecipeEntity(
            title = recipe.title,
            shortDescription = recipe.shortDescription,
            price = recipe.price,
            duration = recipe.duration,
            difficulty = difficulty,
            category = category,
            creator = user,
            createTime = date,
            longDescription = recipe.longDescription,
            ingredients = null,
            views = 0
        )

    }
}
