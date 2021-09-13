package pl.dubiel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListUtils {

    public static List<Integer> onlyEvenNumbersList(List<Integer> allNumbersList){
        return Optional.ofNullable(allNumbersList).orElseGet(Collections::emptyList)
                .stream().filter( o -> o % 2 == 0).collect(Collectors.toList());
    }

}
