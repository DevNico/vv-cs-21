package de.nicolasschlecker.vv.aktor.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ExecutorBase {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorBase.class);

    private final TaskExecutor taskExecutor;
    private final ApplicationContext applicationContext;

    @Autowired
    public ExecutorBase(TaskExecutor taskExecutor, ApplicationContext applicationContext) {
        this.taskExecutor = taskExecutor;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void atStartup() {
        RegistrationService registrationService = applicationContext.getBean(RegistrationService.class);
        taskExecutor.execute(registrationService);
    }
}