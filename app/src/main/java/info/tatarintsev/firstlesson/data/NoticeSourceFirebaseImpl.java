package info.tatarintsev.firstlesson.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import info.tatarintsev.firstlesson.NoticeData;

public class NoticeSourceFirebaseImpl implements NoticeSource {
    private static final String NOTICE_COLLECTION = "notices";
    private static final String TAG = "[NoticeSourceFirebaseImpl]";

    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    // Коллекция документов
    private CollectionReference collection = store.collection(NOTICE_COLLECTION);

    // Загружаемый список карточек
    private List<NoticeData> noticesData = new ArrayList<NoticeData>();

    @Override
    public NoticeSource init(final NoticeSourceResponse noticeSourceResponse) {
        // Получить всю коллекцию, отсортированную по полю «Дата»
        collection.orderBy(NoticeDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    // При удачном считывании данных загрузим список карточек
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            noticesData = new ArrayList<NoticeData>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                NoticeData noticeData = NoticeDataMapping.toNoticeData(id, doc);
                                noticesData.add(noticeData);
                            }
                            Log.d(TAG, "success " + noticesData.size() + " qnt");
                            noticeSourceResponse.initialized(NoticeSourceFirebaseImpl.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public NoticeData getNoticeData(int position) {
        return noticesData.get(position);
    }

    @Override
    public int size() {
        if (noticesData == null){
            return 0;
        }
        return noticesData.size();
    }

    @Override
    public void deleteNoticeData(int position) {
        // Удалить документ с определённым идентификатором
        collection.document(noticesData.get(position).getId()).delete();
        noticesData.remove(position);
    }

    @Override
    public void updateNoticeData(int position, NoticeData noticeData) {
        String id = noticeData.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(NoticeDataMapping.toDocument(noticeData));
    }

    @Override
    public void addNoticeData(final NoticeData noticeData) {
        // Добавить документ
        collection.add(NoticeDataMapping.toDocument(noticeData)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                noticeData.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearNoticeData() {
        for (NoticeData noticeData: noticesData) {
            collection.document(noticeData.getId()).delete();
        }
        noticesData = new ArrayList<NoticeData>();
    }
}

