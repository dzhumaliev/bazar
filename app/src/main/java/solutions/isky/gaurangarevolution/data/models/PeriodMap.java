package solutions.isky.gaurangarevolution.data.models;

import java.io.Serializable;
import java.util.HashMap;

public class PeriodMap implements Serializable {
    private HashMap<String,PeriodItem>periodItemHashMap;

    public HashMap<String, PeriodItem> getPeriodItemHashMap() {
        return periodItemHashMap;
    }

    public void setPeriodItemHashMap(HashMap<String, PeriodItem> periodItemHashMap) {
        this.periodItemHashMap = periodItemHashMap;
    }
}
