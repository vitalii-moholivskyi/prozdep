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
public class PostgraduateViewModel {

    int id;
    BehaviorSubject<Integer> teacherId;
    BehaviorSubject<String> firstName;
    BehaviorSubject<String> phone;
    BehaviorSubject<Date> startDate;
    BehaviorSubject<Date> endDate;
    BehaviorSubject<Date> protectionDate;
    BehaviorSubject<String> topic;

    @Getter
    @Value
    public static class Builder {

        int id;
        String firstName;
        Date startDate;

        @NonFinal int teacherId;
        @NonFinal String phone;
        @NonFinal Date endDate;
        @NonFinal Date protectionDate;
        @NonFinal String topic;

        public Builder(int id, String firstName, Date startDate) {
            Preconditions.checkArgument(id > 0, "id > 0");
            this.id = id;
            this.firstName = Preconditions.notNull(firstName);
            this.startDate = Preconditions.notNull(startDate);
        }

        public Builder setTeacherId(int teacherId) {
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

        public Builder setProtectionDate(Date protectionDate) {
            this.protectionDate = protectionDate;
            return this;
        }

        public PostgraduateViewModel build() {
            return new PostgraduateViewModel(this);
        }

    }

    private PostgraduateViewModel(Builder builder) {
        this.id = builder.getId();
        this.teacherId = BehaviorSubject.create(builder.getTeacherId());
        this.firstName = BehaviorSubject.create(builder.getFirstName());
        this.phone = BehaviorSubject.create(builder.getPhone());
        this.startDate = BehaviorSubject.create(builder.getStartDate());
        this.endDate = BehaviorSubject.create(builder.getEndDate());
        this.topic = BehaviorSubject.create(builder.getTopic());
        this.protectionDate = BehaviorSubject.create(builder.getProtectionDate());
    }

    public int getId() {
        return id;
    }

    public void setTeacherId(int id) {
        this.teacherId.onNext(id);
    }

    public int getTeacherId() {
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

    public Date getProtectionDate() {
        return protectionDate.getValue();
    }

    public void setProtectionDate(Date protectionDate) {
        this.protectionDate.onNext(protectionDate);
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

    public Observable<Date> getProtectionDateObs() {
        return protectionDate.asObservable();
    }

    public Observable<String> getTopicObs() {
        return topic.asObservable();
    }

}
