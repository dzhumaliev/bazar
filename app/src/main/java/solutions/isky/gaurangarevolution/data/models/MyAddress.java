package solutions.isky.gaurangarevolution.data.models;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class MyAddress implements Serializable {
    @NonNull
    public String formattedAddress;
    @NonNull
    public double latitude;
    @NonNull
    public double longitute;
    @NonNull
    public String postalCode;
    @NonNull
    public String  country;
    @NonNull
    public String  street_number;
    @NonNull
    public String  route;
    @NonNull
    public String  city_name;


    @NonNull
    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(@NonNull String city_name) {
        this.city_name = city_name;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    @NonNull
    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(@NonNull String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    @NonNull
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull double latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public double getLongitute() {
        return longitute;
    }

    public void setLongitute(@NonNull double longitute) {
        this.longitute = longitute;
    }

    @NonNull
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(@NonNull String postalCode) {
        this.postalCode = postalCode;
    }

    @NonNull
    public String getRoute() {
        return route;
    }

    public void setRoute(@NonNull String route) {
        this.route = route;
    }

    @NonNull
    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(@NonNull String street_number) {
        this.street_number = street_number;
    }

    @Override
    public String toString() {
        return formattedAddress;
    }
}
