package com.barinov.vladimir;

import com.barinov.vladimir.database.Database;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Main class
 */
public class MoneyTransfersTestApp extends Application<MoneyTransfersTestAppConfiguration> {

    public static final Logger logger = LoggerFactory.getLogger(MoneyTransfersTestApp.class);

    public static void main(String[] args) throws Exception {
        new MoneyTransfersTestApp().run(args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(MoneyTransfersTestAppConfiguration configuration, Environment environment) throws Exception {
        logger.info("Starting application...");
        Database.initialize(configuration, environment);
    }
}
