package ClearStartManager;

import java.util.List;
import java.util.NoSuchElementException;

class Customer {


    private final String name;
    private List<Setting> settings;

    Customer(String name, List<Setting> settings) {
        this.name = name;
        this.settings = settings;
    }

    String getName() {
        return name;
    }

    List<Setting> getSettings() {
        return settings;
    }

    void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

    void addEmptySettingIfNone() {
        for (Setting setting : this.getSettings()) {
            if ("".equals(setting.getKey())) {
                return;
            }
        }
        Setting emptySetting = new Setting("", "");
        this.addSetting(emptySetting);
    }

    private void addSetting(Setting setting) {
        this.settings.add(setting);
    }

    Setting getSettingByIndex(Integer index) throws NoSuchElementException {
        Setting setting = this.settings.get(index);
        if (setting != null) {
            return setting;
        }
        throw new NoSuchElementException();
    }

    void setSettingKeyByIndex(Integer index, String key) {
        Setting setting = getSettingByIndex(index);
        setting.setKey(key);
    }

    void setSettingValueByIndex(Integer index, String value) {
        Setting setting = getSettingByIndex(index);
        setting.setValue(value);
    }

}