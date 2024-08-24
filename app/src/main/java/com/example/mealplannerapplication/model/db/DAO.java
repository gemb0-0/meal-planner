        package com.example.mealplannerapplication.model.db;

        import androidx.lifecycle.LiveData;
        import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.OnConflictStrategy;
        import androidx.room.Query;

        import com.example.mealplannerapplication.model.Pojos.Meal;

        import java.util.List;
        @Dao
        public interface DAO {
           // @Query("SELECT * FROM meal_table WHERE inPlan != NULL AND mealDate = :today")

            @Query("SELECT * FROM meal_table WHERE inPlan ==1 AND weekDay =:today")
            List<Meal> getTodayMeals(String today);

            @Query("SELECT * FROM meal_table")
            LiveData<List<Meal>> getAllFavouriteMeals();

         //   @Query("SELECT * FROM meal_table WHERE idMeal = :mealId AND inPlan == NULL")
            @Query("SELECT * FROM meal_table WHERE idMeal = :mealId")
            List<Meal> getMealDetail(String mealId);

            @Insert(onConflict = OnConflictStrategy.REPLACE)
            void insert(Meal mealDetail);

            @Delete
            void delete(Meal meal);

            @Query ("DELETE FROM meal_table")
            void deleteAll();


            @Query("SELECT * FROM meal_table")
            LiveData<List<Meal>> getAllData();

        }
