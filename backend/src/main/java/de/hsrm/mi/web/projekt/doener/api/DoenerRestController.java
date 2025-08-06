package de.hsrm.mi.web.projekt.doener.api;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import de.hsrm.mi.web.projekt.doener.services.DoenerService;
import de.hsrm.mi.web.projekt.doener.mapper.DoenerMapper;

@RestController
@RequestMapping("/api/doener")
public class DoenerRestController {

    private final DoenerService service;
    private final DoenerMapper mapper;

    public DoenerRestController(DoenerService service, DoenerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public List<DoenerDTO> alleDoener() {
        var alle = service.findAllDoener();
        return mapper.doenerListToDoenerDTOList(alle);
    }

    @GetMapping("/{id}")
    public DoenerDTO einzelnerDoener(@PathVariable("id") long id) {
        var doener = service.findDoenerById(id)
                .orElseThrow(() -> new DoenerNotFoundException(id));
        return mapper.doenerToDoenerDTO(doener);
    }
}
