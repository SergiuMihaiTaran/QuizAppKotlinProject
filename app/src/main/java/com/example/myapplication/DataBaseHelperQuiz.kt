package com.example.myapplication
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelperQuiz(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                QUESTION_MESSAGE + " TEXT, " +
                OPTION1 + " TEXT, " +
                OPTION2 + " TEXT, " +
                OPTION3 + " TEXT, " +
                OPTION4 + " TEXT, " +

                CORRECT_OPTION_INDEX + " INTEGER, "+
                IMAGE_ID+ " TEXT)"
                )
        val createResultsTable = ("CREATE TABLE $TABLE_RESULTS (" +
                "$RESULT_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$TIMESTAMP TEXT DEFAULT CURRENT_TIMESTAMP, " +
                "$NUMBER_OF_QUESTIONS INTEGER, " +
                "$CORRECT_ANSWERS INTEGER, " +
                "$IS_CUSTOM INTEGER DEFAULT 0)")
        db.execSQL(createResultsTable)
        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addQuizResult(numberOfQuestions: Int?, correctAnswers: Int?, isCustom: Boolean?):Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NUMBER_OF_QUESTIONS, numberOfQuestions)
        values.put(CORRECT_ANSWERS, correctAnswers)
        values.put(IS_CUSTOM, if (isCustom == true) 1 else 0) // Store boolean as integer (0 = false, 1 = true)

        val ret=db.insert(TABLE_RESULTS, null, values)
        db.close()
        return ret.toInt()!=-1
    }

    fun addAllQuestions(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,"", arrayOf())
        addQuestion("How many days were there in the year 2021?","364","365","250","252",2,"calendar_imag");
        addQuestion("What is the value of Planck's constant (h) in Joule-seconds?", "6.63 x 10^-34", "3.14 x 10^-34", "9.81 x 10^-34", "1.62 x 10^-34", 1, "planck_image");
        addQuestion("Which of these elements has the highest electronegativity?", "Oxygen", "Fluorine", "Chlorine", "Nitrogen", 2, "electronegativity_image");
        addQuestion("Who formulated the laws of planetary motion?", "Einstein", "Galileo", "Kepler", "Newton", 3, "kepler_image");
        addQuestion("What is the derivative of sin(x)?", "cos(x)", "-sin(x)", "tan(x)", "-cos(x)", 1, "calculus_image");
        addQuestion("In quantum mechanics, what does the Heisenberg Uncertainty Principle state?", "Position and momentum cannot be simultaneously determined with arbitrary precision", "Electrons orbit the nucleus in fixed paths", "Energy is quantized", "Light behaves only as a wave", 1, "uncertainty_image");
        addQuestion("What is the Schwarzschild radius?", "The radius at which an object becomes a black hole", "The minimum possible radius of a neutron star", "The distance at which gravity ceases to exist", "The radius of the observable universe", 1, "karl_schwarzschild_portrait");
        addQuestion("Which of the following is not a fundamental force of nature?", "Gravity", "Electromagnetism", "Nuclear Force", "Friction", 4, "forces_image");
        addQuestion("What is the determinant of the matrix [[2,3],[1,4]]?", "5", "6", "7", "8", 1, "matrix_image");
        addQuestion("Which programming paradigm is primarily used in functional programming?", "Imperative", "Procedural", "Declarative", "Object-Oriented", 3, "programming_image");
        addQuestion("In statistics, what does a p-value less than 0.05 indicate?", "There is strong evidence against the null hypothesis", "The null hypothesis is correct", "The sample size is too small", "The data is inconclusive", 1, "statistics_image");
        addQuestion("What is the meaning of Euler’s Identity?", "It relates five fundamental mathematical constants", "It describes the motion of planetary orbits", "It explains the concept of infinity", "It proves the existence of prime numbers", 1, "euler_identity_image");
        addQuestion("What is the main purpose of the Turing Test?", "To determine whether a machine can exhibit human-like intelligence", "To measure the speed of computers", "To evaluate programming efficiency", "To test a system’s security", 1, "turing_test_image");
        addQuestion("What is the Navier-Stokes equation used for?", "Modeling fluid dynamics", "Calculating electrical resistance", "Determining gravitational forces", "Solving algebraic equations", 1, "fluid_dynamics_image");
        addQuestion("What does the Higgs boson particle explain?", "The origin of mass", "The expansion of the universe", "The nature of black holes", "The composition of dark matter", 1, "higgs_boson_image");
        addQuestion("Which branch of mathematics deals with the study of symmetries and groups?", "Algebra", "Topology", "Group Theory", "Number Theory", 3, "group_theory_image");
        addQuestion("What does Gödel’s Incompleteness Theorem state?", "Some mathematical truths cannot be proven", "All axioms lead to contradictions", "Mathematics is completely self-consistent", "Numbers are infinite", 1, "godel_theorem_image");
        addQuestion("What is the speed of light in vacuum?", "3.00 x 10^8 m/s", "2.99 x 10^8 m/s", "3.14 x 10^8 m/s", "1.50 x 10^8 m/s", 1, "speed_of_light_image");
        addQuestion("Which number is known as the golden ratio?", "1.618", "3.141", "2.718", "0.618", 1, "golden_ratio_image");
        addQuestion("What does Maxwell’s equations describe?", "Electromagnetism", "Gravity", "Nuclear reactions", "Quantum mechanics", 1, "maxwell_image");
        addQuestion("What is the principle behind the Schrödinger’s Cat thought experiment?", "Superposition", "Quantum entanglement", "Wave-particle duality", "Heisenberg’s uncertainty", 1, "schrodinger_image");
    }
    // This method is for adding data in our database
    private fun addQuestion(questionMessage:String,option1 : String, option2 : String,option3 : String,option4 : String,rightOption:Int,imageId:String ){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(QUESTION_MESSAGE, questionMessage)
        values.put(OPTION1, option1)
        values.put(OPTION2, option2)
        values.put(OPTION3, option3)
        values.put(OPTION4, option4)
        values.put(CORRECT_OPTION_INDEX,rightOption)
        values.put(IMAGE_ID,imageId)
        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database

        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getQuestion(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }
    fun getAllQuizResults(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_RESULTS", null)
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private const val DATABASE_NAME = "QuizCustomAppDataBase"

        // below is the variable for database version
        private const val DATABASE_VERSION = 2

        // below is the variable for table name
        const val TABLE_NAME = "QuizQuestions"

        // below is the variable for id column
        const val ID_COL = "id"

        // below is the variable for name column
        const val QUESTION_MESSAGE = "Question_message"

        // below is the variable for age column
        const val OPTION1 = "Option1"
        const val OPTION2 = "Option2"
        const val OPTION3 = "Option3"
        const val OPTION4 = "Option4"
        const val CORRECT_OPTION_INDEX="Correct_Option"
        const val IMAGE_ID="Image_ID"
        //Quiz Results
        const val TABLE_RESULTS = "QuizResults"
        const val RESULT_ID = "id"
        const val TIMESTAMP = "timestamp"
        const val NUMBER_OF_QUESTIONS = "numberOfQuestions"
        const val CORRECT_ANSWERS = "correctAnswers"
        const val IS_CUSTOM = "isCustom"
    }
}