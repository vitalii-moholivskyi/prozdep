package department.ui.controller.model;

import department.utils.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.NonFinal;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import java.util.Date;

/**
 * Created by Максим on 2/1/2017.
 */
@Value
@Getter(AccessLevel.NONE)
public class MasterViewModel {

    int id;
    BehaviorSubject<Integer> teacherId;
    BehaviorSubject<String> firstName;
    BehaviorSubject<String> phone;
    BehaviorSubject<Date> startDate;
    BehaviorSubject<Date> endDate;
    BehaviorSubject<String> topic;
    BehaviorSubject<Integer> department;
    BehaviorSubject<String> departmentName;

    @Getter
    @Value
    public static class Builder {

        int id;
        String firstName;
        Date startDate;

        @NonFinal Integer teacherId;
        @NonFinal Integer departmentId;
        @NonFinal String phone;
        @NonFinal Date endDate;
        @NonFinal String topic;
        @NonFinal String departmentName;

        public Builder(int id, String firstName, Date startDate) {
            Preconditions.checkArgument(id > 0, "id <= 0");
            this.id = id;
            this.firstName = Preconditions.notNull(firstName);
            this.startDate = Preconditions.notNull(startDate);
        }

        public Builder setTeacherId(Integer teacherId) {
            this.teacherId = teacherId;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEndDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setTopic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder setDepartmentId(Integer departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Builder setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
            return this;
        }

        public MasterViewModel build() {
            return new MasterViewModel(this);
        }

    }

    private MasterViewModel(Builder builder) {
        this.id = builder.getId();
        this.teacherId = BehaviorSubject.create(builder.getTeacherId());
        this.firstName = BehaviorSubject.create(builder.getFirstName());
        this.phone = BehaviorSubject.create(builder.getPhone());
        this.startDate = BehaviorSubject.create(builder.getStartDate());
        this.endDate = BehaviorSubject.create(builder.getEndDate());
        this.topic = BehaviorSubject.create(builder.getTopic());
        this.department = BehaviorSubject.create(builder.getDepartmentId());
        this.departmentName = BehaviorSubject.create(builder.getDepartmentName());
    }

    public int getId() {
        return id;
    }

    public void setTeacherId(Integer id) {
        this.teacherId.onNext(id);
    }

    public Integer getTeacherId() {
        return teacherId.getValue();
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

    public Date getEndDate() {
        return endDate.getValue();
    }

    public void setEndDate(Date end) {
        this.endDate.onNext(end);
    }

    public String getTopic() {
        return topic.getValue();
    }

    public void setTopic(String topic) {
        this.topic.onNext(topic);
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

    public Observable<Date> getEndDateObs() {
        return endDate.asObservable();
    }

    public Observable<String> getTopicObs() {
        return topic.asObservable();
    }

    public Observable<Integer> getDepartmentObs() {
        return department.asObservable();
    }

    public Integer getDepartment() {
        return department.getValue();
    }

    public void setDepartment(Integer id) {
        this.department.onNext(id);
    }

    public void setDepartmentName(String name) {
        departmentName.onNext(name);
    }

    public Observable<String> getDepartmentNameObs() {
        return departmentName.asObservable();
    }

    public String getDepartmentName() {
        return departmentName.getValue();
    }

}
