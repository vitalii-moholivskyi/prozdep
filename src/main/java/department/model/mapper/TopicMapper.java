/**
 * 
 */
package department.model.mapper;

import department.model.bo.Topic;
import department.ui.controller.model.TopicViewModel;
import department.utils.Preconditions;
import lombok.val;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Nikolay
 *
 */
public final class TopicMapper {

	private TopicMapper() {
		throw new RuntimeException();
	}

	public static TopicViewModel toViewModel(Topic topic) {
		Preconditions.notNull(topic);
		val teacher = topic.getChiefScientist();
		val department = topic.getDepartment();
		System.out.println(department.getName()+"+"+teacher.getName());
		return new TopicViewModel.Builder(topic.getId(), topic.getClient(), topic.getStartDate(),
				topic.getEndDate(), department.getId(), teacher.getId(), topic.getName())
				.setChiefScientistName(teacher!=null?teacher.getName():null)
				.setDepartmentTitle(department!=null?department.getName():null)
				.build();
	}

	public static Collection<? extends TopicViewModel> toViewModel(Collection<? extends Topic> masters) {
		return masters.stream().map(TopicMapper::toViewModel).collect(Collectors.toList());
	}

}
