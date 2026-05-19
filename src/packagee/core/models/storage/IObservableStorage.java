package packagee.core.models.storage;

public interface IObservableStorage extends IHospitalStorage {
    void addObserver(IStorageObserver observer);
    void removeObserver(IStorageObserver observer);
    void notifyObservers(String eventType);
}