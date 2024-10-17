package com.example.mealplannerapplication.view.activity2.Profile;

import java.util.List;

public interface UserProfileInterface {
    void backupSuccess();
    void Error();

    void restoreSuccess();
    void setUserData(List<String> data);
}
