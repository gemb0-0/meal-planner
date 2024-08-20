        package com.example.mealplannerapplication.model.db;

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
            List<Meal> getAllMeals();

            @Insert(onConflict = OnConflictStrategy.IGNORE)
            void insert(List<Meal> mealDetail);

            @Delete
            void delete(Meal meal);

        }
