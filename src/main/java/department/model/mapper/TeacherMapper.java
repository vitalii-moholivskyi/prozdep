/**
 * 
 */
package department.model.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import department.model.bo.Teacher;
import department.ui.controller.model.TeacherViewModel;
import department.utils.Preconditions;

/**
 * @author Nikolay
 *
 */
public final class TeacherMapper {

	private TeacherMapper() {
		throw new RuntimeException();
	}

	public static TeacherViewModel toViewModel(Teacher teacher) {

		return teacher == null ? null
				: new TeacherViewModel.Builder(teacher.getId(), teacher.getName(), teacher.getStartDate(),
						teacher.getDepartment() == null ? null : teacher.getDepartment().getId(), teacher.getPosition(),
						teacher.getDegree()).setPhone(teacher.getPhone()).build();
	}

	public static Collection<? extends TeacherViewModel> toViewModel(Collection<? extends Teacher> masters) {
		return masters.stream().map(TeacherMapper::toViewModel).collect(Collectors.toList());
	}

}
