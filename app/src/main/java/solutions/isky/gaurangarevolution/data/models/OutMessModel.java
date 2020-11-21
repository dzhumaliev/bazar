package solutions.isky.gaurangarevolution.data.models;

import java.util.HashMap;

public class OutMessModel extends MessModel {

  public OutMessModel(Message message,HashMap<Integer,ItemsInMess> itemsInMess,UserInMess userInMess) {
    super(message,itemsInMess,userInMess);
  }
}
