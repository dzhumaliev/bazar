package solutions.isky.gaurangarevolution.data.event;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import java.util.HashMap;
import solutions.isky.gaurangarevolution.data.models.CapObject;
import solutions.isky.gaurangarevolution.data.models.ChatsList;
import solutions.isky.gaurangarevolution.data.models.ChatsListNew;

public class GetChatList extends CapObject {

    @SerializedName("chats_list")
    HashMap<Integer,ChatsListNew> chatsLists;

    public HashMap<Integer, ChatsListNew> getChatsLists() {
        return (chatsLists!=null)?chatsLists:new HashMap<>();
    }

    public void setChatsLists(
        HashMap<Integer, ChatsListNew> chatsLists) {
        this.chatsLists = chatsLists;
    }
}