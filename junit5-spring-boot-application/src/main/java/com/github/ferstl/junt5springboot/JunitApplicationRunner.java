package com.github.ferstl.junt5springboot;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.util.function.Supplier;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.LoggingListener;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class JunitApplicationRunner implements ApplicationRunner {

  private static final String BASE_PACKAGE = "com.github.ferstl";
  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Override
  public void run(ApplicationArguments args) throws Exception {
    LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
        .selectors(DiscoverySelectors.selectPackage(BASE_PACKAGE))
        .build();

    Launcher launcher = LauncherFactory.create();
    SummaryGeneratingListener summaryListener = new SummaryGeneratingListener();
    LoggingListener loggingListener = LoggingListener.forBiConsumer(JunitApplicationRunner::log);

    launcher.registerTestExecutionListeners(summaryListener, loggingListener);
    launcher.execute(request);

    TestExecutionSummary summary = summaryListener.getSummary();
    try (StringWriter summaryWriter = new StringWriter()) {
      summary.printTo(new PrintWriter(summaryWriter));
      LOGGER.info("Test finished. Summary:{}", summaryWriter.toString());
    }
  }

  private static void log(Throwable t, Supplier<String> msgSupplier) {
    if (t == null) {
      LOGGER.info(msgSupplier.get());
    } else {
      LOGGER.error(msgSupplier.get(), t);
    }
  }
}
