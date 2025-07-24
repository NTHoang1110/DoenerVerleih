package de.hsrm.mi.web.projekt.doener.services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.entities.doener.Doener;
import de.hsrm.mi.web.projekt.entities.doener.DoenerRepository;
import de.hsrm.mi.web.projekt.messaging.FrontendNachrichtEvent;
import jakarta.transaction.Transactional;

@Service
public class DoenerServiceImpl implements DoenerService {
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    DoenerRepository doenerRepository;

    @Override
    public Doener saveDoener(Doener d) {
        boolean isNew = (d.getId() == 0);
        Doener gespeicherterDoener = doenerRepository.save(d);

        publisher.publishEvent(new FrontendNachrichtEvent(
                FrontendNachrichtEvent.EventTyp.DOENER,
                gespeicherterDoener.getId(),
                isNew ? FrontendNachrichtEvent.Operation.CREATE : FrontendNachrichtEvent.Operation.UPDATE));

        return gespeicherterDoener;
    }

    @Override
    @Transactional
    public Optional<Doener> findDoenerById(long id) {
        return doenerRepository.findById(id);
    }

    @Override
    public Collection<Doener> findAllDoener() {
        return doenerRepository.findAll();
    }

    @Override
    public void deleteDoenerById(long id) {
        doenerRepository.deleteById(id);
        publisher.publishEvent(new FrontendNachrichtEvent(
                FrontendNachrichtEvent.EventTyp.DOENER,
                id,
                FrontendNachrichtEvent.Operation.DELETE));
    }

}
