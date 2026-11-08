package learning.task020;

import java.util.ArrayDeque;

public class CinemaEntryQueue {
    private final ArrayDeque<String> list = new ArrayDeque<>();
    public CinemaEntryQueue(){}
    public void joinLine(String guestName){
        if(guestName == null || guestName.isEmpty()){
            throw new IllegalArgumentException();
        }
        list.addLast(guestName);
    }
    public String peekNextGuest(){
        return list.peekFirst();
    }
    public String callNextGuest(){
        return list.pollFirst();
    }
    public int getGuestCount(){
        return list.size();
    }
}
