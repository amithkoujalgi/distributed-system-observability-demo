package io.github.amithkoujalgi.demo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    final
    InstrumentProducer instrumentProducer;

    final
    OrderProducer orderProducer;

    public ApplicationStartup(InstrumentProducer instrumentProducer, OrderProducer orderProducer) {
        this.instrumentProducer = instrumentProducer;
        this.orderProducer = orderProducer;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        instrumentProducer.start();
        orderProducer.start();
    }
}

