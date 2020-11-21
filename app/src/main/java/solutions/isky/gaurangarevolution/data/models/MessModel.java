package solutions.isky.gaurangarevolution.data.models;

import java.util.HashMap;

public class MessModel {
    public Message message;
    HashMap<Integer,ItemsInMess> itemsInMess;
    UserInMess userInMess;
    public MessModel(Message message,HashMap<Integer,ItemsInMess> itemsInMess,UserInMess userInMess) {
        this.message = message;
        this.itemsInMess=itemsInMess;
        this.userInMess=userInMess;
    }
}
