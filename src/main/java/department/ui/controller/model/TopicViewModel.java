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
    BehaviorSubject<String> chiefScientistName;
    BehaviorSubject<String> departmentTitle;

    @Getter
    @Value
    public static class Builder {

        int id, department, chiefScientist;
        String client, name;
        Date startDate;

        @NonFinal String chiefScientistName, departmentTitle;
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

        public Builder setChiefScientistName(String chiefScientistName) {
            this.chiefScientistName = chiefScientistName;
            return this;
        }

        public Builder setDepartmentTitle(String departmentTitle) {
            this.departmentTitle = departmentTitle;
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
        this.chiefScientistName = BehaviorSubject.create(builder.getChiefScientistName());
        this.departmentTitle = BehaviorSubject.create(builder.getDepartmentTitle());
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

    public Observable<String> getChiefScientistNameObs() {
        return chiefScientistName.asObservable();
    }

    public Observable<String> getDepartmentTitleObs() {
        return departmentTitle.asObservable();
    }

    public String getChiefScientistName() {
        return chiefScientistName.getValue();
    }

    public void setChiefScientistName(String name) {
        this.chiefScientistName.onNext(name);
    }

    public String getDepartmentTitle() {
        return departmentTitle.getValue();
    }

    public void setDepartmentTitle(String name) {
        this.departmentTitle.onNext(name);
    }

}
