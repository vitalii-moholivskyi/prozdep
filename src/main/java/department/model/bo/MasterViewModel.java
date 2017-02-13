package department.model.bo;

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
    int teacherId;
    BehaviorSubject<String> firstName;
    BehaviorSubject<String> phone;
    BehaviorSubject<String> degree;
    BehaviorSubject<Date> startDate;
    BehaviorSubject<Date> endDate;
    BehaviorSubject<String> topic;

    @Getter
    @Value
    public static class Builder {

        int id;
        String firstName;
        Date startDate;

        @NonFinal int teacherId;
        @NonFinal String phone;
        @NonFinal String degree;
        @NonFinal Date endDate;
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

        public Builder setDegree(String degree) {
            this.degree = degree;
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

        public MasterViewModel build() {
            return new MasterViewModel(this);
        }

    }

    private MasterViewModel(Builder builder) {
        this.id = builder.getId();
        this.teacherId = builder.getTeacherId();
        this.firstName = BehaviorSubject.create(builder.getFirstName());
        this.phone = BehaviorSubject.create(builder.getPhone());
        this.degree = BehaviorSubject.create(builder.getDegree());
        this.startDate = BehaviorSubject.create(builder.getStartDate());
        this.endDate = BehaviorSubject.create(builder.getEndDate());
        this.topic = BehaviorSubject.create(builder.getTopic());
    }

    public int getId() {
        return id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public Observable<String> getFirstNameObs() {
        return firstName.asObservable();
    }

    public Observable<String> getPhoneObs() {
        return phone.asObservable();
    }

    public Observable<String> getDegreeObs() {
        return degree.asObservable();
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

}
