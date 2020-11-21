package solutions.isky.gaurangarevolution.data.event;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import solutions.isky.gaurangarevolution.data.models.CapObject;

public class BuyAdv extends CapObject {

    @SerializedName("user_balance")
    private UserBalans userBalans;
    @SerializedName("data")
    private DataObg dataObg;

    public DataObg getDataObg() {
        return dataObg;
    }

    public UserBalans getUserBalans() {
        return userBalans;
    }



    public class UserBalans {
        @SerializedName("balance")
        @NonNull
        private String balance;
        @SerializedName("balance_display")
        @NonNull
        private String balance_display;


        @NonNull
        public String getBalance() {
            return balance;
        }

        @NonNull
        public String getBalance_display() {
            return balance_display;
        }
    }
    public class DataObg {
        @SerializedName("data")
        @NonNull
        private String data;
        @SerializedName("signature")
        @NonNull
        private String signature;

        @SerializedName("cmd")
        @NonNull
        private String cmd;
        @SerializedName("hosted_button_id")
        @NonNull
        private String hosted_button_id;
        @SerializedName("custom")
        @NonNull
        private String custom;
        @SerializedName("quantity")
        @NonNull
        private String quantity;

        //**yandex parametrs
        @SerializedName("receiver")
        @NonNull
        private String receiver;
        @SerializedName("formcomment")
        @NonNull
        private String formcomment;
        @SerializedName("label")
        @NonNull
        private String label;
        @SerializedName("quickpay-form")
        @NonNull
        private String quickpay_form;
        @SerializedName("targets")
        @NonNull
        private String targets;
        @SerializedName("sum")
        @NonNull
        private String sum;
        @SerializedName("paymentType")
        @NonNull
        private String paymentType;
        @SerializedName("successURL")
        @NonNull
        private String successURL;
        //***


        @NonNull
        public String getCmd() {
            return cmd;
        }

        @NonNull
        public String getHosted_button_id() {
            return hosted_button_id;
        }

        @NonNull
        public String getCustom() {
            return custom;
        }

        @NonNull
        public String getQuantity() {
            return quantity;
        }

        @NonNull
        public String getData() {
            return data;
        }

        public void setData(@NonNull String data) {
            this.data = data;
        }

        @NonNull
        public String getSignature() {
            return signature;
        }

        public void setSignature(@NonNull String signature) {
            this.signature = signature;
        }

        @NonNull
        public String getReceiver() {
            return receiver;
        }

        @NonNull
        public String getFormcomment() {
            return formcomment;
        }

        @NonNull
        public String getLabel() {
            return label;
        }

        @NonNull
        public String getQuickpay_form() {
            return quickpay_form;
        }

        @NonNull
        public String getTargets() {
            return targets;
        }

        @NonNull
        public String getSum() {
            return sum;
        }

        @NonNull
        public String getPaymentType() {
            return paymentType;
        }

        @NonNull
        public String getSuccessURL() {
            return successURL;
        }
    }
}
