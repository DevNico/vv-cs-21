package de.nicolasschlecker.vv.aktor.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ExecutorBase {
    private final TaskExecutor taskExecutor;
    private final ApplicationContext applicationContext;

    @Autowired
    public ExecutorBase(TaskExecutor taskExecutor, ApplicationContext applicationContext) {
        this.taskExecutor = taskExecutor;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void atStartup() {
        final var registrationService = applicationContext.getBean(RegistrationService.class);
        taskExecutor.execute(registrationService);
    }
}