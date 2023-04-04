package com.app.brainobrain.helper;

public class Constant {
    public static final String MainBaseUrl = "https://demo.trainingzone.in/";
    public static final String BaseUrl = MainBaseUrl + "api/";
    public static final String LOGIN_URL = BaseUrl + "login";
    public static final String CALENDER_STATS_URL = BaseUrl + "user/stats-calendar";
    public static final String CALENDER_STATS_DAY_URL = BaseUrl + "user/stats-day";
    public static final String USER_INFO_URL = BaseUrl + "user/me";
    public static final String PRACTICE_LEVEL_URL = BaseUrl + "practice/levels";
    public Constant(){

    }

    public static String PRACTICE_SECTION_URL(String LevelId) {
        return BaseUrl + "practice/"+LevelId+"/sections";

    }
    public static String PRACTICE_SECTION_QUESTION_URL(String LevelId,String SectionId) {
        return BaseUrl + "practice/"+LevelId+"/"+SectionId;

    }
    public static String PRACTICE_SECTION_STORE_STATS_URL(String LevelId,String SectionId) {
        return BaseUrl + "practice/"+LevelId+"/"+SectionId+"/store-stats";

    }



    public static final String RESULTFRAGMENT = "ResultFragment";
    public static final String FLASHCARDQUESTIONFRAGMENT = "FlashCardQuestionFragment";
    public static final String ADDSUBNUMTYPEVISUALFRAGMENT = "AddSubNumTypeVisualFragment";
    public static final String ADDSUBNUMTYPEORALFRAGMENT = "AddSubNumTypeOralFragment";
    public static final String DOUBLINGVIEWFRAGMENT = "DouublingViewFragment";
    public static final String FlashCardsQuestionVisualFragment ="FlashCardsQuestionVisualFragment";
    public static final String SECONDS = "Seconds";
    public static final String MINIMUM = "minimum";
    public static final String MAXIMUM = "maximum";
    public static final String DIGITS = "digits";

    public static final String QUES_TYPE = "ques_type";
    public static final String AUTHORIZATION = "Authorization";
    public static final String TYPE = "type";
    public static final String LEVEL = "level";
    public static final String LEVEL_ID = "level_id";
    public static final String SECTION_ID = "section_id";
    public static final String LEVEL_NAME = "level_name";
    public static final String SCORE = "score";
    public static final String ID = "id";
    public static final String NUMBER = "number";
    public static final String ORAL_NUMBER = "oral_number";
    public static final String FLASH_CARD = "flash_card";
    public static final String IMAGE = "image";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";
    public static final String QUESTION_ARRAY = "question_array";

    public static final String API_ERROR = "Server Not Respond";
    public static final String DATA = "data" ;
    public static final String DATE = "date" ;
    public static final String LEVELS = "levels" ;
    public static final String NUMBER_OF_QUESTIONS = "number_of_questions" ;
    public static final String CORRECT_ANSWERS = "correct_answers" ;
    public static final String TIME_TAKEN = "time_taken" ;
    public static final String AVERAGE_TIME_TAKEN = "average_time_taken" ;
    public static final String USER_LEVELS = "user_levels" ;
    public static final String USER_PRACTICE_LEVELS_COUNT = "user_practice_levels_count" ;
    public static final String QUESTION = "question" ;
    public static final String QUESTION_NAME = "question_name" ;
    public static final String FRAG_LOCATE = "frag_locate" ;
    public static final String PRACTICE_FRAG = "practice_frag" ;
    public static final String SECTION_FRAG = "section_frag" ;
    public static final String EVENT_FRAG = "event_frag" ;
    public static final String ANSWERS = "answers" ;
    public static final String TOKEN = "token" ;
    public static final String NAME = "name" ;
}