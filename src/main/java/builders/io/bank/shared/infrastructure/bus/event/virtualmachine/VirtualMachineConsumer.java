package builders.io.bank.shared.infrastructure.bus.event.virtualmachine;

import builders.io.bank.shared.domain.bus.event.DomainEvent;
import builders.io.bank.shared.domain.transaction.TransactionSentDomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

@Service
public class VirtualMachineConsumer implements  Consumer<Event<DomainEvent>> {
    private static final Logger logger = LoggerFactory.getLogger(VirtualMachineConsumer.class);
    @Autowired
    private VirtualMachineActionsService virtualMachineActionsService;

    @Override
    public void accept(Event<DomainEvent> notificationDataEvent) {
        DomainEvent eventData = notificationDataEvent.getData();
        try {
            if (eventData instanceof TransactionSentDomainEvent) {
                virtualMachineActionsService.executeTransactionOnTransactionSent(eventData);
            }
        } catch (InterruptedException e) {
            logger.warn("executeTransactionOnTransactionSent interrupted!: {}",e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
