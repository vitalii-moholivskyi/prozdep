/**
 * 
 */
package department.model.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import department.model.bo.Paper;
import department.ui.controller.model.PaperViewModel;
import department.utils.Preconditions;

/**
 * @author Nikolay
 *
 */
public final class PaperMapper {

	private PaperMapper() {
		throw new RuntimeException();
	}

	public static PaperViewModel toViewModel(Paper paper) {
		Preconditions.notNull(paper);
		return new PaperViewModel(paper.getId(), paper.getName(), paper.getType(), paper.getYear());
	}

	public static Collection<? extends PaperViewModel> toViewModel(Collection<? extends Paper> masters) {
		return masters.stream().map(PaperMapper::toViewModel).collect(Collectors.toList());
	}

}
