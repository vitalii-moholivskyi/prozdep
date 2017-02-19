/**
 * 
 */
package department.model.mapper;

import java.util.Collection;
import java.util.stream.Collectors;

import department.model.bo.Scientist;
import department.ui.controller.model.ScientistViewModel;
import department.utils.Preconditions;

/**
 * @author Nikolay
 *
 */
public final class ScientistMapper {

    private ScientistMapper() {
        throw new RuntimeException();
    }

    public static ScientistViewModel toViewModel(Scientist scientist) {
        Preconditions.notNull(scientist);
        return new ScientistViewModel(scientist.getId(), scientist.getPhone(), scientist.getName());
    }

    public static Collection<? extends ScientistViewModel> toViewModel(Collection<? extends Scientist> masters) {
        return masters.stream().map(ScientistMapper::toViewModel).collect(Collectors.toList());
    }

}
