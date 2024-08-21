        package com.example.mealplannerapplication.model.db;

        import androidx.lifecycle.LiveData;
        import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.OnConflictStrategy;
        import androidx.room.Query;

        import com.example.mealplannerapplication.model.Meal;

        import java.util.List;
        @Dao
        public interface DAO {
            @Query("SELECT * FROM meal_table")
            LiveData<List<Meal>> getAllMeals();

            @Query("SELECT * FROM meal_table WHERE idMeal = :mealId")
            List<Meal> getMealDetail(String mealId);

            @Insert(onConflict = OnConflictStrategy.IGNORE)
            void insert(Meal mealDetail);

            @Delete
            void delete(Meal meal);

        }
