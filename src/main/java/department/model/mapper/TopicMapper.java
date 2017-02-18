/**
 * 
 */
package department.model.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import department.model.bo.Topic;
import department.ui.controller.model.TopicViewModel;
import department.utils.Preconditions;

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
		return new TopicViewModel.Builder(topic.getId(), topic.getClient(), topic.getStartDate(),
				topic.getDepartment() == null ? null : topic.getDepartment().getId(),
				topic.getChiefScientist() == null ? null : topic.getChiefScientist().getId(), topic.getName())
						.setEndDate(topic.getEndDate()).build();
	}

	public static Collection<? extends TopicViewModel> toViewModel(Collection<? extends Topic> masters) {
		return masters.stream().map(TopicMapper::toViewModel).collect(Collectors.toList());
	}

}
