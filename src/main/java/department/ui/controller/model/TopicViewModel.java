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
@Getter(value = AccessLevel.NONE)
public class TopicViewModel {

    int id, department;
    String client;
    BehaviorSubject<String> name;
    BehaviorSubject<Date> startDate;
    BehaviorSubject<Date> endDate;
    BehaviorSubject<Integer> chiefScientist;

    @Getter
    @Value
    public static class Builder {

        int id, department, chiefScientist;
        String client, name;
        Date startDate;

        @NonFinal Date endDate;

        public Builder(int id, String client, Date startDate, int department, int chiefScientist, String name) {
            Preconditions.checkArgument(id > 0, "id <= 0");
            Preconditions.checkArgument(department > 0, "department <= 0");
            Preconditions.checkArgument(chiefScientist > 0, "chiefScientist <= 0");
            this.client = Preconditions.notNull(client);
            this.startDate = Preconditions.notNull(startDate);
            this.name = Preconditions.notNull(name);
            this.department = department;
            this.chiefScientist = chiefScientist;
            this.id = id;
        }

        public Builder setEndDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public TopicViewModel build() {
            return new TopicViewModel(this);
        }

    }

    private TopicViewModel(Builder builder) {
        this.id = builder.getId();
        this.client = builder.getClient();
        this.startDate = BehaviorSubject.create(builder.getStartDate());
        this.endDate = BehaviorSubject.create(builder.getEndDate());
        this.name = BehaviorSubject.create(builder.getName());
        this.department = builder.getDepartment();
        this.chiefScientist = BehaviorSubject.create(builder.getChiefScientist());
    }

    public int getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public int getDepartment() {
        return department;
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

    public int getChiefScientist() {
        return chiefScientist.getValue();
    }

    public void setChiefScientist(int chiefScientist) {
        this.chiefScientist.onNext(chiefScientist);
    }

    public Observable<Date> getStartDateObs() {
        return startDate.asObservable();
    }

    public Observable<Date> getEndDateObs() {
        return endDate.asObservable();
    }

    public Observable<Integer> getChiefScientistObs() {
        return chiefScientist;
    }


}
