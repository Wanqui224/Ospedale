package packagee.core.models.storage;

public interface IStorageObserver {
    void onStorageChanged(String eventType);
}