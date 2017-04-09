package com.hotjoe.services.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebListener
public class BaseContextListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(BaseContextListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("in BaseContextListener.contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info( "in BaseContextListener.contextDestroyed" );
    }
}
