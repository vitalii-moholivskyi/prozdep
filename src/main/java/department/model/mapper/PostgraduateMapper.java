/**
 * 
 */
package department.model.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import department.model.bo.Postgraduate;
import department.ui.controller.model.PostgraduateViewModel;
import department.utils.Preconditions;

/**
 * @author Nikolay
 *
 */
public final class PostgraduateMapper {

	private PostgraduateMapper() {
		throw new RuntimeException();
	}

	public static PostgraduateViewModel toViewModel(Postgraduate postgraduate) {
		Preconditions.notNull(postgraduate);
		return new PostgraduateViewModel.Builder(postgraduate.getId(), postgraduate.getName(),
				postgraduate.getStartDate()).setEndDate(postgraduate.getEndDate()).setPhone(postgraduate.getPhone())
						.setTeacherId(postgraduate.getTeacher() == null ? null : postgraduate.getTeacher().getId())
						.setTopic(postgraduate.getTopic()).setDepartmentName(postgraduate.getDepartment()!=null?postgraduate.getDepartment().getName():null)
						.setDepartmentId(postgraduate.getDepartment()!=null?postgraduate.getDepartment().getId():null).build();
	}

	public static Collection<? extends PostgraduateViewModel> toViewModel(Collection<? extends Postgraduate> masters) {
		return masters.stream().map(PostgraduateMapper::toViewModel).collect(Collectors.toList());
	}

}
