import androidx.room.*
import java.time.DayOfWeek

@Entity
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val days: Map<DayOfWeek, Boolean>
)

@Dao
interface HabitDao {
    @Insert
    suspend fun insert(habit: HabitEntity)

    @Update
    suspend fun update(habit: HabitEntity)

    @Query("SELECT * FROM HabitEntity WHERE id = :id")
    suspend fun getHabitById(id: Int): HabitEntity?

    @Query("SELECT * FROM HabitEntity")
    suspend fun getAllHabits(): List<HabitEntity>
}

@Database(entities = [HabitEntity::class], version = 1)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}