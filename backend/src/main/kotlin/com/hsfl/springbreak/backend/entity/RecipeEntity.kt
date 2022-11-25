package com.hsfl.springbreak.backend.entity

import com.hsfl.springbreak.backend.model.Ingredient
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.model.User
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "recipe")
data class RecipeEntity(
    @Id @GeneratedValue val id: Long? = null,
    @Column val title: String,
    @Column val shortDescription: String,
    @Column val price: Double,
    @OneToOne val rating: RatingEntity?,
    @Column val duration: Double,
    @ManyToOne val difficulty: DifficultyEntity?,
    @ManyToOne val category: CategoryEntity?,
    @ManyToOne val creator: UserEntity?,
    @Column val createTime: LocalDate?,
    @OneToMany val ingredients: MutableList<IngredientRecipe> = mutableListOf(),
    @Column val image: String,
    @Column val longDescription: String,
    @Column val views: Int,
    @ManyToMany val favorite: MutableList<UserEntity> = mutableListOf()
) : DataEntity<Recipe, RecipeEntity> {


    override fun toDto(): Recipe = Recipe(
        id = this.id!!,
        title = this.title,
        shortDescription = this.shortDescription,
        price = this.price,
        rating = this.rating!!.toDto(),
        duration = this.duration,
        difficulty = this.difficulty!!.toDto(),
        category = this.category!!.toDto(),
        creator = this.creator!!.toDto(),
        createTime = this.createTime!!,
        ingredients = this.ingredients.map{ it.toIngredient() },
        image = this.image,
        longDescription = this.longDescription,
        views = this.views
    )

    override fun fromDto(dto: Recipe): RecipeEntity = RecipeEntity(
        id = dto.id!!,
        title = dto.title,
        shortDescription = dto.shortDescription,
        price = dto.price,
        rating = RatingEntity(recipe = null, like = 0, dislike = 0 ).fromDto(dto.rating),
        duration = dto.duration,
        difficulty = DifficultyEntity(name = "").fromDto(dto.difficulty),
        category = CategoryEntity(name = "").fromDto(dto.category),
        creator = UserEntity(firstName = "", lastName = "", email = "", password = "").fromDto(dto.creator),
        createTime = dto.createTime,
        ingredients = dto.ingredients.map{IngredientRecipe(id = IngredientRecipeKey(null, ingredient.id),
            recipe = createEmptyRecipe(),
            ingredient = IngredientEntity(name = "", recipe = mutableListOf()),
            unit = "",
            amount = 0).fromDto(it)}.toMutableList(),
        image = dto.image,
        longDescription = dto.longDescription,
        views = dto.views
    )


}
