package department.ui.controller.model;

import department.utils.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import javax.validation.constraints.NotNull;

@Value
@Getter(AccessLevel.NONE)
public class DepartmentViewModel {

    int id;
    BehaviorSubject<String> name;
    BehaviorSubject<String> phone;

    public DepartmentViewModel(@NotNull int id, @NotNull String name, String phone) {

        if(id <= 0)
            throw new IllegalArgumentException("id <= 0");

        this.id = id;
        this.name = BehaviorSubject.create(Preconditions.notNull(name, "name == null"));
        this.phone = BehaviorSubject.create(phone);
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name.onNext(name);
    }

    public void setPhone(String phone) {
        this.phone.onNext(phone);
    }

    public String getName() {
        return name.getValue();
    }

    public String getPhone() {
        return phone.getValue();
    }

    public Observable<String> getNameObs() {
        return name.asObservable();
    }

    public Observable<String> getPhoneObs() {
        return phone.asObservable();
    }
}
