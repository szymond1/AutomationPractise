package pl.dubiel.unit;

import org.junit.*;
import org.junit.rules.TestName;
import pl.dubiel.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtilsTest {

    private ListUtils utils;

    @Rule public TestName testName = new TestName();

    @Before
    public void setUp(){
        System.out.println("Wykonywanie testu: " + testName.getMethodName());
        utils = new ListUtils();
    }

    @After
    public void tearDown(){
        System.out.println("Zakończenie wykonywania testu: " + testName.getMethodName());
    }


    @Test
    public void listShouldReturnEmptyListWhenMethodArgumentIsNull(){

        Assert.assertTrue(utils.onlyEvenNumbersList(new ArrayList<Integer>()).isEmpty());
    }

    @Test
    public void listShouldReturnEmptyListWhenMethodArgumentIsEmpty(){

        Assert.assertTrue(utils.onlyEvenNumbersList(new ArrayList<Integer>()).isEmpty());
    }

    @Test
    public void listShouldContainsOnlyEvenNumbers(){
        List<Integer> allNumberList = Arrays.asList(new Integer[]{0,1,5,4,8,15,17,22,28,33,50});
        List<Integer> evenNumberList = utils.onlyEvenNumbersList(allNumberList);
        Assert.assertTrue(evenNumberList.stream().allMatch(o -> o % 2 == 0));
    }

}
