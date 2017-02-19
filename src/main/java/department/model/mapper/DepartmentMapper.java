/**
 * 
 */
package department.model.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import department.model.bo.Department;
import department.ui.controller.model.DepartmentViewModel;
import department.utils.Preconditions;

/**
 * @author Nikolay
 *
 */
public final class DepartmentMapper {

	private DepartmentMapper() {
		throw new RuntimeException();
	}

	public static DepartmentViewModel toViewModel(Department department) {
		Preconditions.notNull(department);
		return new DepartmentViewModel(department.getId(), department.getName(), department.getPhone());
	}

	public static Collection<? extends DepartmentViewModel> toViewModel(Collection<? extends Department> masters) {
		return masters.stream().map(DepartmentMapper::toViewModel).collect(Collectors.toList());
	}

}
