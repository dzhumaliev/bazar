package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.ItemPayments;

public class GetMyPayments extends CapObject {
    @SerializedName("user_balance")
    @NonNull
    private UserBalance userBalance;

    @NonNull
    public UserBalance getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(@NonNull UserBalance userBalance) {
        this.userBalance = userBalance;
    }

    public class UserBalance {
        @SerializedName("balance")
        @NonNull
        private String balance;
        @SerializedName("balance_display")
        @NonNull
        private String balance_display;

        @SerializedName("balance_bonus")
        @NonNull
        private String balance_bonus;
        @SerializedName("log")
        @NonNull
        private ArrayList<ItemPayments> itemPayments;

        @NonNull
        public String getBalance_bonus() {
            return balance_bonus;
        }

        public void setBalance_bonus(@NonNull String balance_bonus) {
            this.balance_bonus = balance_bonus;
        }

        @NonNull
        public String getBalance() {
            return balance;
        }

        public void setBalance(@NonNull String balance) {
            this.balance = balance;
        }

        @NonNull
        public String getBalance_display() {
            return balance_display;
        }

        public void setBalance_display(@NonNull String balance_display) {
            this.balance_display = balance_display;
        }

        @NonNull
        public ArrayList<ItemPayments> getItemPayments() {
            return (itemPayments!=null)?itemPayments:new ArrayList<ItemPayments>();
        }

        public void setItemPayments(@NonNull ArrayList<ItemPayments> itemPayments) {
            this.itemPayments = itemPayments;
        }
    }
}