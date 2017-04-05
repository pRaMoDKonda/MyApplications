package pramod.com.mystickynotes.realm;

import io.realm.RealmObject;

/**
 * Created by ipspl on 4/4/17.
 */

public class RealmString extends RealmObject {

    private String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
