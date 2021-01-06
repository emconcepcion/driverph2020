package com.cav.DriverphTruckerlearningPH2020;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class Database {
	public static final String DATABASE_NAME= com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.DATABASE_NAME;
	public static final String TABLE_NAME= com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.TABLE_NAME;
	public static final int DATABSE_VERSION=1;
	

	//to create a table

	final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
			com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.TABLE_NAME + "(" +
			com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
			com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_QUESTION + " TEXT UNIQUE, " +
			com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
			com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
			com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
			com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
			com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
			com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_CHAPTER + " TEXT" +
			");";

	final String SQL_CREATE_SCORES_TABLE = "CREATE TABLE " +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.TABLE_NAME_SCORES + "(" +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_USER_ID + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_EMAIL + " TEXT, " +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_SCORE + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_NUM_ITEMS + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_CHAPTER + " TEXT," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_NUM_ATTEMPT + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_DURATION + " TEXT," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_DATE_TAKEN + " TEXT," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_IS_LOCKED + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_IS_COMPLETED + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.SYNC_STATUS + " INTEGER" +
			");";

	//for displaying all passed tests
	final String SQL_CREATE_SCORES_TABLE_FROM_SERVER = "CREATE TABLE " +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.TABLE_NAME_SCORES_MYSQL + "(" +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_USER_ID_MYSQL + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_EMAIL_MYSQL + " TEXT, " +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_SCORE_MYSQL + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_NUM_ITEMS_MYSQL + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_CHAPTER_MYSQL + " TEXT UNIQUE," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_NUM_ATTEMPT_MYSQL + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_DURATION_MYSQL + " TEXT," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_DATE_TAKEN_MYSQL + " TEXT," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_IS_LOCKED_MYSQL + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_IS_COMPLETED_MYSQL + " INTEGER" +
			");";

	//for retrieval of attempts, unlock and updating
	final String SQL_CREATE_SCORES_ALL_ATTEMPTS_TABLE_FROM_SERVER = "CREATE TABLE " +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.TABLE_NAME_SCORES_ALL + "(" +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.COLUMN_NAME_USER_ID_ALL + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.COLUMN_NAME_EMAIL_ALL + " TEXT, " +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.COLUMN_NAME_SCORE_ALL + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.COLUMN_NAME_NUM_ITEMS_ALL + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.COLUMN_NAME_CHAPTER_ALL + " TEXT UNIQUE," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.COLUMN_NAME_NUM_ATTEMPT_ALL + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.COLUMN_NAME_DURATION_ALL + " TEXT," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.COLUMN_NAME_DATE_TAKEN_ALL + " TEXT," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.COLUMN_NAME_IS_LOCKED_ALL + " INTEGER," +
			com.cav.DriverphTruckerlearningPH2020.DbContract.AllAttemptsMySQLTable.COLUMN_NAME_IS_COMPLETED_ALL + " INTEGER" +
			");";

	private Context context;
	public static SQLiteDatabase db;	// manipulation with database
	DatabaseHelper dbhelper;
	
	public Database(Context ctx) {
		// TODO Auto-generated constructor stub
		this.context=ctx;
		dbhelper=new DatabaseHelper(ctx);
	}
	
 	
	//SQLITEOpenHelper has methods to creae and open
	class DatabaseHelper extends SQLiteOpenHelper
	{
		//DatabaseHelper's constructor will create the database
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABSE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
			db.execSQL(SQL_CREATE_SCORES_TABLE);
			db.execSQL(SQL_CREATE_SCORES_TABLE_FROM_SERVER);
			db.execSQL(SQL_CREATE_SCORES_ALL_ATTEMPTS_TABLE_FROM_SERVER);
			Log.d("table is created..","Table is created...");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS tbl_questions");
			db.execSQL("DROP TABLE IF EXISTS tbl_scores");
			db.execSQL("DROP TABLE IF EXISTS tbl_scores_server");
			db.execSQL("DROP TABLE IF EXISTS tbl_scores_all_attempts");
			onCreate(db);
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
			db.disableWriteAheadLogging();
		}

	}

	public com.cav.DriverphTruckerlearningPH2020.Database Open() throws SQLException
	{
			db=dbhelper.getWritableDatabase();
			return this;
	}
	
	public void close()
	{
		dbhelper.close();
	}
	
	public long addQuestion(com.cav.DriverphTruckerlearningPH2020.Question question)
	{
		ContentValues cv = new ContentValues();

		cv.put(com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_QUESTION, question.getQuestion());
		cv.put(com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_OPTION1, question.getOption1());
		cv.put(com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_OPTION2, question.getOption2());
		cv.put(com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_OPTION3, question.getOption3());
		cv.put(com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_OPTION4, question.getOption4());
		cv.put(com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
		cv.put(com.cav.DriverphTruckerlearningPH2020.QuizContract.QuestionsTable.COLUMN_CHAPTER, question.getChapter());
			
			Log.d("inserted... ", question.getQuestion()+"");
			Log.d("inserted... ", question.getAnswerNr()+"");
			return db.insertWithOnConflict(TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
	}

	public long addAllAttempts(com.cav.DriverphTruckerlearningPH2020.Score score)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_USER_ID, score.getUser_id());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_EMAIL, score.getEmail());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_SCORE, score.getScore());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_NUM_ITEMS, score.getNum_of_items());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_CHAPTER, score.getChapter());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_NUM_ATTEMPT, score.getNum_of_attempt());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_DURATION, score.getDuration());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_DATE_TAKEN, score.getDate_taken());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_IS_LOCKED, score.getIsLocked());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.COLUMN_NAME_IS_COMPLETED, score.getIsCompleted());

		Log.d("inserted... ", score.getUser_id()+"");
		Log.d("inserted... ", score.getNum_of_attempt()+"");
		return db.insertWithOnConflict(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.TABLE_NAME_SCORES, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

	}

	public long addScoresServer(com.cav.DriverphTruckerlearningPH2020.MyScoresServer scoresServer)
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_USER_ID_MYSQL, scoresServer.getUser_id());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_EMAIL_MYSQL, scoresServer.getEmail());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_SCORE_MYSQL, scoresServer.getScore());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_NUM_ITEMS_MYSQL, scoresServer.getNum_of_items());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_CHAPTER_MYSQL, scoresServer.getChapter());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_NUM_ATTEMPT_MYSQL, scoresServer.getNum_of_attempt());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_DURATION_MYSQL, scoresServer.getDuration());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_DATE_TAKEN_MYSQL, scoresServer.getDate_taken());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_IS_LOCKED_MYSQL, scoresServer.getIsLocked());
		contentValues.put(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.COLUMN_NAME_IS_COMPLETED_MYSQL, scoresServer.getIsCompleted());

		Log.d("inserted... ", scoresServer.getUser_id()+"");
		Log.d("inserted... ", scoresServer.getNum_of_attempt()+"");
		return db.insertWithOnConflict(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresMySQLTable.TABLE_NAME_SCORES_MYSQL, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

	}

	public void deleteAllQuestion(String studentRoll) {
		// TODO Auto-generated method stub
		db.delete(TABLE_NAME,  null,null);
	      
	        Log.d("Delete Table","Delete Question called.....");
	        
	}

	public void deleteAllScores() {
		// TODO Auto-generated method stub
		db.delete(com.cav.DriverphTruckerlearningPH2020.DbContract.ScoresTable.TABLE_NAME_SCORES,  null,null);

		Log.d("Delete Table","Delete Scores called.....");

	}
}
