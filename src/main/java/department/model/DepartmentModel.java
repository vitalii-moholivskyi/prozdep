package department.model;

import department.entity.Department;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("restriction")
public class DepartmentModel implements IDepartmentModel {

	private final ObservableList<Department> departmentList = FXCollections.observableArrayList();

	private final ObjectProperty<Department> currentDepartment = new SimpleObjectProperty<>(null);

	public ObjectProperty<Department> currentDepartmentProperty() {
		return currentDepartment;
	}

	public final Department getCurrentDepartment() {
		return currentDepartment.get();
	}

	public final void setCurrentDepartment(Department department) {
		currentDepartment.set(department);
	}

	public ObservableList<Department> getDepartmentList() {
		return departmentList;
	}

}
