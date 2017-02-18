package department.utils;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Created by Максим on 2/18/2017.
 */
@Value
@AllArgsConstructor
public class Tuple <V1, V2> {
    V1 v1;
    V2 v2;
}
