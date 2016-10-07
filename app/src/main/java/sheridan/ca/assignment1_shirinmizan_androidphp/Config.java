package sheridan.ca.assignment1_shirinmizan_androidphp;

/**
 * Created by Shirin on 10/6/2016.
 */

public class Config {

        //Address of our scripts of the CRUD
        public static final String URL_ADD="http://mizan.dev.fast.sheridanc.on.ca/assignment1_android/insert.php";
        public static final String URL_GET_ALL = "http://mizan.dev.fast.sheridanc.on.ca/assignment1_android/showListUsers.php";
        public static final String URL_GET_ONE = "http://mizan.dev.fast.sheridanc.on.ca/assignment1_android/showAUser.php?id=";
        public static final String URL_UPDATE_FAV = "http://mizan.dev.fast.sheridanc.on.ca/assignment1_android/update.php";
        public static final String URL_DELETE_FAV = "http://mizan.dev.fast.sheridanc.on.ca/assignment1_android/delete.php?id=";

        //Keys that will be used to send the request to php scripts
        public static final String KEY_FAV_ID = "id";
        public static final String KEY_FAV_NAME = "name";
        public static final String KEY_FAV_EMAIL = "email";
        public static final String KEY_FAV_FAVFOOD = "favfood";
        public static final String KEY_FAV_TVSHOW = "favtv";
        public static final String KEY_FAV_MOVIE = "favmovie";

        //JSON Tags
        public static final String TAG_JSON_ARRAY="result";
        public static final String TAG_ID = "id";
        public static final String TAG_NAME = "name";
        public static final String TAG_EMAIL = "email";
        public static final String TAG_FAVFOOD = "favfood";
        public static final String TAG_FAVTV = "favtv";
        public static final String TAG_FAVMOVIE = "favmovie";

        //employee id to pass with intent
        public static final String FAV_ID = "fav_id";
}


