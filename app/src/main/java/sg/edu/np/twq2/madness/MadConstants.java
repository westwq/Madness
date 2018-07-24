package sg.edu.np.twq2.madness;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MadConstants {
    public static GoogleSignInAccount acct;

    public static String getEmail()
    {
        return acct.getEmail();
    }

    public static String getId()
    {
        return acct.getId();
    }
}
