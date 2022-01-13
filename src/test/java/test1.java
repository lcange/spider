import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spider.mapper.SpiderDataMapper;

@SpringBootTest
public class test1 {
    @Autowired
    private SpiderDataMapper spiderData;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
    }
}
