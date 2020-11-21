package solutions.isky.gaurangarevolution.data.event;

import com.google.gson.annotations.SerializedName;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.SettingsApp;

public class GetSettings extends CapObject {
    @SerializedName("settings")
    private SettingsApp settingsApp;

    public SettingsApp getSettingsApp() {
        return settingsApp;
    }

    public void setSettingsApp(SettingsApp settingsApp) {
        this.settingsApp = settingsApp;
    }
}
