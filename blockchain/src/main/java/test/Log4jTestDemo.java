package test;
import org.checkerframework.common.reflection.qual.GetClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Log4jTestDemo {
    private Logger logger = LoggerFactory.getLogger(getClass());

    
    /**
     * @param args
     */
    @Test
    public void sss() {
    	logger.info("this is a example");
    	logger.info("Method:Hello");
        logger.info("World");
    }


}