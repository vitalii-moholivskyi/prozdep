/**
 * 
 */
package department.ui.controller.model;

import department.utils.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.NonFinal;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.util.Date;

@Value
@Getter(AccessLevel.NONE)
public class TeacherViewModel {

    int id;
    BehaviorSubject<String> phone;
    BehaviorSubject<String> firstName;
    BehaviorSubject<Date> startDate;
    BehaviorSubject<Integer> department;
    BehaviorSubject<String> position;
    BehaviorSubject<String> degree;

    @Getter
    @Value
    public static class Builder {

        int id, departmentId;
        String firstName, position, degree;
        Date startDate;

        @NonFinal String phone;

        public Builder(int id, String firstName, Date startDate, int departmentId, String position, String degree) {
            Preconditions.checkArgument(id > 0, "id <= 0");
            Preconditions.checkArgument(departmentId > 0, "departmentId <= 0");
            this.id = id;
            this.departmentId = departmentId;
            this.position = Preconditions.notNull(position);
            this.degree = Preconditions.notNull(degree);
            this.firstName = Preconditions.notNull(firstName);
            this.startDate = Preconditions.notNull(startDate);
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public TeacherViewModel build() {
            return new TeacherViewModel(this);
        }

    }

    private TeacherViewModel(Builder builder) {
        this.id = builder.getId();
        this.firstName = BehaviorSubject.create(builder.getFirstName());
        this.phone = BehaviorSubject.create(builder.getPhone());
        this.startDate = BehaviorSubject.create(builder.getStartDate());
        this.department = BehaviorSubject.create(builder.getDepartmentId());
        this.position = BehaviorSubject.create(builder.getPosition());
        this.degree = BehaviorSubject.create(builder.getDegree());
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

    public Date getStartDate() {
        return startDate.getValue();
    }

    public void setStartDate(Date start) {
        this.startDate.onNext(start);
    }

    public int getDepartment() {
        return department.getValue();
    }

    public void setDepartment(int department) {
        this.department.onNext(department);
    }

    public void setPosition(String position) {
        this.position.onNext(position);
    }

    public String getPosition() {
        return position.getValue();
    }

    public String getDegree() {
        return degree.getValue();
    }

    public void setDegree(String degree) {
        this.degree.onNext(degree);
    }

    public Observable<String> getPositionObs() {
        return position.asObservable();
    }

    public Observable<String> getDegreeObs() {
        return degree.asObservable();
    }

    public Observable<String> getFirstNameObs() {
        return firstName.asObservable();
    }

    public Observable<String> getPhoneObs() {
        return phone.asObservable();
    }

    public Observable<Date> getStartDateObs() {
        return startDate.asObservable();
    }

    public Observable<Integer> getDepartmentObs() {
        return department.asObservable();
    }

}
