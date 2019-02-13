package test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Log4jTestDemo {
    private static Logger logger = LoggerFactory.getLogger(Log4jTestDemo.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
    	logger.info("this is a example");
    	logger.info("Method:Hello");
        logger.info("World");
    }


}