package solutions.isky.gaurangarevolution.data.event;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.HashMap;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.ItemsInMess;
import solutions.isky.gaurangarevolution.data.models.MessModel;
import solutions.isky.gaurangarevolution.data.models.Message;
import solutions.isky.gaurangarevolution.data.models.UserInMess;

public class GetMessList extends CapObject {

  @SerializedName("chat")
  private ChatObj chatObj;
  private ArrayList<MessModel> messModels;


  public ArrayList<MessModel> getMessModels() {
    return messModels;
  }

  public void setMessModels(ArrayList<MessModel> messModels) {
    this.messModels = messModels;
  }

  public ChatObj getChatObj() {
    return chatObj;
  }

  public void setChatObj(ChatObj chatObj) {
    this.chatObj = chatObj;
  }

  public class ChatObj implements Serializable {

    @SerializedName("messages")
    private ArrayList<Message> chatsLists;
    @SerializedName("items")
    private HashMap<Integer, ItemsInMess> itemsInMessHashMap;


    @SerializedName("user")
    UserInMess userInMess;

    public ArrayList<Message> getChatsLists() {
      return chatsLists;
    }

    public HashMap<Integer, ItemsInMess> getItemsInMessHashMap() {
      return itemsInMessHashMap;
    }

    public void setItemsInMessHashMap(
        HashMap<Integer, ItemsInMess> itemsInMessHashMap) {
      this.itemsInMessHashMap = itemsInMessHashMap;
    }

    public void setChatsLists(ArrayList<Message> chatsLists) {
      this.chatsLists = chatsLists;
    }

    public UserInMess getUserInMess() {
      return userInMess;
    }

    public void setUserInMess(UserInMess userInMess) {
      this.userInMess = userInMess;
    }
  }

}
