package info.tatarintsev.firstlesson.observe;

import java.util.ArrayList;
import java.util.List;

import info.tatarintsev.firstlesson.data.NoticeData;

public class Publisher {
    private List<Observer> observers;   // Все обозреватели

    public Publisher() {
        observers = new ArrayList<>();
    }

    // Подписать
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие
    public void notifySingle(NoticeData noticeData) {
        for (Observer observer : observers) {
            observer.updateNoticeData(noticeData);
            unsubscribe(observer);
        }
    }
}
