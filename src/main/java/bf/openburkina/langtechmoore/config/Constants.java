package bf.openburkina.langtechmoore.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "fr";
    public static final String API_SMS_USERNAME = "ACa7a719f8501f2ffb60c78dda37c6b950";

    public static final String TWILIO_API_KEY_SID = "SK427198523510245abd8b715f64e8d4ca";
    public static final String TWILIO_API_KEY_SECRET = "9wHwus06Jc8VbggwiZh3sC9USHdCAWt0";
    public static final String TWILIO_ACCOUNT_ID = "ACa7a719f8501f2ffb60c78dda37c6b950";
    public static final String TWILIO_SENDER = "OPENBURKINA";
    public static final String API_SMS_PASSWORD = "d3048946d8c856ef3dd9e59d32853eb8";
    public static final String API_SMS_TELEPHONE = "+12056561317";

    public static final String MOIS[]={"january", "february","march","april","may","june","july","august","september","october","november","december"};

    private Constants() {}
}
