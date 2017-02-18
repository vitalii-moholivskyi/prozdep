/**
 *
 */
package department.ui.controller.model;

import department.utils.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import rx.Observable;
import rx.subjects.BehaviorSubject;

@Value
@Getter(value = AccessLevel.NONE)
public class PaperViewModel {

    int id;
    BehaviorSubject<String> name;
    BehaviorSubject<String> type;
    BehaviorSubject<Integer> year;

    public PaperViewModel(int id, String name, String type, int year) {

        Preconditions.checkArgument(id > 0, "id <= 0");
        Preconditions.checkArgument(year >= 1900, "year < 1900");

        this.id = id;
        this.name = BehaviorSubject.create(Preconditions.notNull(name));
        this.type = BehaviorSubject.create(Preconditions.notNull(type));
        this.year = BehaviorSubject.create(year);
    }

    public int getId() {
        return id;
    }

    public Observable<String> getNameObs() {
        return name.asObservable();
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.onNext(name);
    }

    public Observable<String> getTypeObs() {
        return type.asObservable();
    }

    public String getType() {
        return type.getValue();
    }

    public void setType(String type) {
        this.type.onNext(type);
    }

    public Observable<Integer> getYearObs() {
        return year.asObservable();
    }

    public int getYear() {
        return year.getValue();
    }

    public void setYear(int year) {
        this.year.onNext(year);
    }

}
