package com.hsfl.springbreak.backend

import com.hsfl.springbreak.backend.entity.CategoryEntity
import com.hsfl.springbreak.backend.entity.DifficultyEntity
import com.hsfl.springbreak.backend.entity.IngredientEntity
import com.hsfl.springbreak.backend.entity.UserEntity
import com.hsfl.springbreak.backend.model.IngredientRecipe
import com.hsfl.springbreak.backend.model.Recipe
import com.hsfl.springbreak.backend.repository.*
import com.hsfl.springbreak.backend.service.RecipeService
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EntityConfiguration {

    @Bean
    fun databaseInitializer(
        ingredientRepository: IngredientRepository,
        ratingRepository: RatingRepository,
        recipeRepository: RecipeRepository,
        userRepository: UserRepository,
        categoryRepository: CategoryRepository,
        difficultyRepository: DifficultyRepository,
        ingredientRecipeRepository: IngredientRecipeRepository,
        recipeService: RecipeService
    ) = ApplicationRunner {
        /* Create Dummy-User */
        userRepository.save(
            UserEntity(
                firstName = "Root", lastName = "Administrator", email = "root@admin.com", password = "geheim"
            )
        )
        userRepository.save(
            UserEntity(
                firstName = "Hektor", lastName = "Panzer", email = "panzer@web.de", password = "secret"
            )
        )

        /* Prepopulate Difficulties*/
        difficultyRepository.save(DifficultyEntity(name = "Leicht"))
        difficultyRepository.save(DifficultyEntity(name = "Mittel"))
        difficultyRepository.save(DifficultyEntity(name = "Schwer"))

        /* Prepopulate Categories */
        categoryRepository.save(CategoryEntity(name = "Brot"))
        categoryRepository.save(CategoryEntity(name = "Eis"))
        categoryRepository.save(CategoryEntity(name = "Gebäck"))
        categoryRepository.save(CategoryEntity(name = "Kuchen"))
        categoryRepository.save(CategoryEntity(name = "Pasta & Nudeln"))
        categoryRepository.save(CategoryEntity(name = "Pizza"))
        categoryRepository.save(CategoryEntity(name = "Reis"))
        categoryRepository.save(CategoryEntity(name = "Rind"))
        categoryRepository.save(CategoryEntity(name = "Salat"))
        categoryRepository.save(CategoryEntity(name = "Schwein"))
        categoryRepository.save(CategoryEntity(name = "Suppen"))
        categoryRepository.save(CategoryEntity(name = "Vegetarisch"))
        categoryRepository.save(CategoryEntity(name = "Vegan"))

        /* Prepopulate Ingredients */
        ingredientRepository.save(IngredientEntity(name = "Apfel"))
        ingredientRepository.save(IngredientEntity(name = "Banane"))
        ingredientRepository.save(IngredientEntity(name = "Birne"))
        ingredientRepository.save(IngredientEntity(name = "Brombeeren"))
        ingredientRepository.save(IngredientEntity(name = "Butter"))
        ingredientRepository.save(IngredientEntity(name = "Chilli"))
        ingredientRepository.save(IngredientEntity(name = "Currypulver"))
        ingredientRepository.save(IngredientEntity(name = "Haferflocken"))
        ingredientRepository.save(IngredientEntity(name = "Heidelbeeren"))
        ingredientRepository.save(IngredientEntity(name = "Leinensamen"))
        ingredientRepository.save(IngredientEntity(name = "Mehl"))
        ingredientRepository.save(IngredientEntity(name = "Milch"))
        ingredientRepository.save(IngredientEntity(name = "Nudeln"))
        ingredientRepository.save(IngredientEntity(name = "Orangen"))
        ingredientRepository.save(IngredientEntity(name = "Paprika"))
        ingredientRepository.save(IngredientEntity(name = "Paprikapulver"))
        ingredientRepository.save(IngredientEntity(name = "Petersilie"))
        ingredientRepository.save(IngredientEntity(name = "Pfeffer"))
        ingredientRepository.save(IngredientEntity(name = "Reis"))
        ingredientRepository.save(IngredientEntity(name = "Salz"))
        ingredientRepository.save(IngredientEntity(name = "Schlagsahne"))
        ingredientRepository.save(IngredientEntity(name = "Speisestärke"))
        ingredientRepository.save(IngredientEntity(name = "Rosinen"))
        ingredientRepository.save(IngredientEntity(name = "Zimt"))
        ingredientRepository.save(IngredientEntity(name = "Zucker"))

        /* Prepopulate Recipes */
        recipeService.createRecipe(
            Recipe.CreateRecipe(
                title = "Haselnusskuchen",
                categoryId = 3,
                creatorId = 1,
                difficultyId = 1,
                duration = 10,
                ingredients = listOf(
                    IngredientRecipe.WithoutRecipe(
                        "Zucker", "Gramm", 200
                    ),
                ),
                longDescription = "Die Eier mit dem Zucker schaumig rühren. Die gemahlenen Haselnüsse zufügen, durchrühren. Nur noch in eine Kasten- oder andere beliebige Form füllen und bei ca. 170°C ca. 40 Minuten backen. Der Kuchen ist supersaftig!\n" + "\n" + "Geht auch mit Mandeln, dann ist er aber etwas trockener. Wer will, glasiert ihn noch.",
                price = 8.00,
                shortDescription = "Der einfachste Kuchen, den ich kenne. Nur 3 Zutaten. Das kann jeder!"
            )
        )

        recipeService.createRecipe(
            Recipe.CreateRecipe(
                title = "Roggenmischbrot",
                categoryId = 3,
                creatorId = 1,
                difficultyId = 1,
                duration = 10,
                ingredients = listOf(
                    IngredientRecipe.WithoutRecipe(
                        "Sauerteig", "Gramm", 150
                    ), IngredientRecipe.WithoutRecipe(
                        "Roggenmehl Type 1150", "Gramm", 150
                    ), IngredientRecipe.WithoutRecipe(
                        "Weizenvollkornmehl", "Gramm", 200
                    ), IngredientRecipe.WithoutRecipe(
                        "Weizenmehl", "Gramm", 100
                    ), IngredientRecipe.WithoutRecipe(
                        "Salz", "TL", 1
                    ), IngredientRecipe.WithoutRecipe(
                        "Zucker", "TL", 1
                    ), IngredientRecipe.WithoutRecipe(
                        "Trockenhefe", "Pck.", 1
                    ), IngredientRecipe.WithoutRecipe(
                        "Backmalz", "Gramm", 5
                    ), IngredientRecipe.WithoutRecipe(
                        "Brotgewürzmischung aus Kümmel, Anis und Fenchel", "TL", 1
                    ), IngredientRecipe.WithoutRecipe(
                        "Wasser", "ml", 340
                    )
                ),
                longDescription = "Sauerteig, Weizen- und Roggenmehl, Salz, Zucker, Hefe, Brotgewürz und Backmalz in eine Rührschüssel geben. Das Wasser hinzufügen und den Teig von einer Rührmaschine ein paar Minuten auf kleinster Stufe kneten lassen. Den Teig an einem warmen Ort 4 Stunden gehen lassen, bis sich das Volumen sichtbar vergrößert hat.\n" + "\n" + "Den Teig in eine große, eingefettete Kaiserkuchenbackform geben und den Ofen auf 240 Grad vorheizen. Das Brot nochmals gehen lassen, bis der Ofen auf Temperatur ist.\n" + "\n" + "Das Brot 30 Minuten backen, dann die Temperatur auf 150 Grad stellen und weitere 20 - 30 Minuten backen.",
                price = 4.00,
                shortDescription = "Mit Sauerteig und Weizenmehl."
            )
        )

    }

}
