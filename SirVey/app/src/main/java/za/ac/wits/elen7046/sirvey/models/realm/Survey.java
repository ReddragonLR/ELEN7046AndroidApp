package za.ac.wits.elen7046.sirvey.models.realm;

import io.realm.RealmObject;

public class Survey extends RealmObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}