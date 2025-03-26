package com.example.fitbit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Information
    private static final String DATABASE_NAME = "FitbitTracker.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_BMI = "bmi_records";
    private static final String TABLE_DIET_PLAN = "diet_plans";

    // BMI Table Columns
    public static final String COLUMN_BMI_ID = "id";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_BMI = "bmi";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_GOAL = "goal"; // lose, gain, maintain

    // Diet Plan Table Columns
    private static final String COLUMN_DIET_ID = "id";
    private static final String COLUMN_DIET_TYPE = "diet_type";
    private static final String COLUMN_RECOMMENDED_CALORIES = "recommended_calories";
    private static final String COLUMN_MEAL_PLAN = "meal_plan";

    // Create BMI Table
    private static final String CREATE_BMI_TABLE = "CREATE TABLE " + TABLE_BMI + "("
            + COLUMN_BMI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_HEIGHT + " REAL, "
            + COLUMN_WEIGHT + " REAL, "
            + COLUMN_BMI + " REAL, "
            + COLUMN_DATE + " TEXT, "
            + COLUMN_GOAL + " TEXT)";

    // Create Diet Plan Table
    private static final String CREATE_DIET_PLAN_TABLE = "CREATE TABLE " + TABLE_DIET_PLAN + "("
            + COLUMN_DIET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DIET_TYPE + " TEXT, "
            + COLUMN_RECOMMENDED_CALORIES + " INTEGER, "
            + COLUMN_MEAL_PLAN + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BMI_TABLE);
        db.execSQL(CREATE_DIET_PLAN_TABLE);
        insertDefaultDietPlans(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BMI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIET_PLAN);
        onCreate(db);
    }

    // Insert default diet plans
    private void insertDefaultDietPlans(SQLiteDatabase db) {
        // Weight Loss Diet Plan
        ContentValues weightLossPlan = new ContentValues();
        weightLossPlan.put(COLUMN_DIET_TYPE, "lose_weight");
        weightLossPlan.put(COLUMN_RECOMMENDED_CALORIES, 1500);
        weightLossPlan.put(COLUMN_MEAL_PLAN, "Breakfast: Oatmeal with berries\n" +
                "Snack: Greek yogurt\n" +
                "Lunch: Grilled chicken salad\n" +
                "Snack: Protein smoothie\n" +
                "Dinner: Baked fish with vegetables");
        db.insert(TABLE_DIET_PLAN, null, weightLossPlan);

        // Weight Gain Diet Plan
        ContentValues weightGainPlan = new ContentValues();
        weightGainPlan.put(COLUMN_DIET_TYPE, "gain_weight");
        weightGainPlan.put(COLUMN_RECOMMENDED_CALORIES, 3000);
        weightGainPlan.put(COLUMN_MEAL_PLAN, "Breakfast: Protein pancakes\n" +
                "Snack: Nuts and dried fruits\n" +
                "Lunch: Whole grain pasta with chicken\n" +
                "Snack: Protein shake\n" +
                "Dinner: Steak with rice and vegetables");
        db.insert(TABLE_DIET_PLAN, null, weightGainPlan);

        // Maintain Weight Diet Plan
        ContentValues maintainWeightPlan = new ContentValues();
        maintainWeightPlan.put(COLUMN_DIET_TYPE, "maintain_weight");
        maintainWeightPlan.put(COLUMN_RECOMMENDED_CALORIES, 2000);
        maintainWeightPlan.put(COLUMN_MEAL_PLAN, "Breakfast: Eggs and whole wheat toast\n" +
                "Snack: Apple with almond butter\n" +
                "Lunch: Quinoa bowl with mixed vegetables\n" +
                "Snack: Hummus with carrots\n" +
                "Dinner: Balanced meal with lean protein, whole grains, and vegetables");
        db.insert(TABLE_DIET_PLAN, null, maintainWeightPlan);
    }

    // Method to insert BMI record
    public long insertBMIRecord(double height, double weight, double bmi, String goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HEIGHT, height);
        values.put(COLUMN_WEIGHT, weight);
        values.put(COLUMN_BMI, bmi);
        values.put(COLUMN_DATE, java.time.LocalDate.now().toString());
        values.put(COLUMN_GOAL, goal);
        return db.insert(TABLE_BMI, null, values);
    }

    // Method to get diet plan based on goal
    public String[] getDietPlan(String goal) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_RECOMMENDED_CALORIES,
                COLUMN_MEAL_PLAN
        };
        String selection = COLUMN_DIET_TYPE + " = ?";
        String[] selectionArgs = { goal };

        Cursor cursor = db.query(
                TABLE_DIET_PLAN,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String[] result = new String[2];
        if (cursor.moveToFirst()) {
            result[0] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECOMMENDED_CALORIES));
            result[1] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEAL_PLAN));
        }
        cursor.close();
        return result;
    }

    // Method to get the latest BMI record
    public Cursor getLatestBMIRecord() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_HEIGHT,
                COLUMN_WEIGHT,
                COLUMN_BMI,
                COLUMN_DATE,
                COLUMN_GOAL
        };

        return db.query(
                TABLE_BMI,
                projection,
                null,
                null,
                null,
                null,
                COLUMN_BMI_ID + " DESC",
                "1"
        );
    }

    // Method to check if there are any BMI records
    public boolean hasBMIRecords() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BMI, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Method to calculate BMI
    public static double calculateBMI(double heightInCm, double weightInKg) {
        // Convert height to meters
        double heightInMeters = heightInCm / 100;
        // Calculate BMI
        return weightInKg / (heightInMeters * heightInMeters);
    }

    // Method to determine weight goal based on BMI
    public static String determineWeightGoal(double bmi) {
        if (bmi < 18.5) return "gain_weight";
        if (bmi >= 18.5 && bmi < 25) return "maintain_weight";
        return "lose_weight";
    }
}