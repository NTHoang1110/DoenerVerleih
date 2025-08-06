package de.hsrm.mi.web.projekt.entleihung.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.projekt.doener.api.DoenerDTO;
import de.hsrm.mi.web.projekt.doener.mapper.DoenerMapper;
import de.hsrm.mi.web.projekt.entleihung.services.EntleihungService;

@RestController
public class EntleihungRestController {
    @Autowired
    EntleihungService entleihungService;

    @Autowired
    DoenerMapper mapper;

    record EntleihungCreateDTO(long doenerId, String loginName) {

    }

    @PostMapping("/api/entleihung")
    public void posting(@RequestBody EntleihungCreateDTO dto) {
        entleihungService.entleiheDoener(dto.doenerId, dto.loginName);
    }

    @DeleteMapping("/api/entleihung/{loginName}/{doenerId}")
    public void deleting(@PathVariable String loginName, @PathVariable int doenerId) {
        entleihungService.zurueckgebeDoener(doenerId, loginName);
    }

    @GetMapping("/api/entleihung/{loginName}")
    public List<DoenerDTO> gettingList(@PathVariable String loginName) {
        return mapper.doenerListToDoenerDTOList(entleihungService.findeEntleihungenVonBenutzer(loginName));
    }
}
