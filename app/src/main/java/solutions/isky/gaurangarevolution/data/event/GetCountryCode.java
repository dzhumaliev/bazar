package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.CountryCode;

public class GetCountryCode extends CapObject {

  @NonNull
  @SerializedName("country_code")
  private List<CountryCode> countryCodes;

  @NonNull
  public List<CountryCode> getCountryCodes() {
    return (countryCodes == null) ? new ArrayList<>() : countryCodes;
  }

  public void setCountryCodes(@NonNull List<CountryCode> countryCodes) {
    this.countryCodes = countryCodes;
  }
}