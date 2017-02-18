package department.ui.controller.model;

import department.utils.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import rx.Observable;
import rx.subjects.BehaviorSubject;

@Value
@Getter(AccessLevel.NONE)
public class ScientistViewModel {

    int id;
    BehaviorSubject<String> phone;
    BehaviorSubject<String> firstName;

    public ScientistViewModel(int id, String phone, String firstName) {
        Preconditions.checkArgument(id > 0, "id <= 0");
        this.id = id;
        this.phone = BehaviorSubject.create(phone);
        this.firstName = BehaviorSubject.create(Preconditions.notNull(firstName));
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName.getValue();
    }

    public void setFirstName(String firstName) {
        this.firstName.onNext(firstName);
    }

    public String getPhone() {
        return phone.getValue();
    }

    public void setPhone(String phone) {
        this.phone.onNext(phone);
    }

    public Observable<String> getFirstNameObs() {
        return firstName.asObservable();
    }

    public Observable<String> getPhoneObs() {
        return phone.asObservable();
    }

}
