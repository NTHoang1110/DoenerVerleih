package de.hsrm.mi.web.projekt.doener.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.hsrm.mi.web.projekt.doener.api.DoenerDTO;
import de.hsrm.mi.web.projekt.doener.api.ZutatDTO;
import de.hsrm.mi.web.projekt.doener.ui.DoenerFormular;
import de.hsrm.mi.web.projekt.entities.doener.Doener;
import de.hsrm.mi.web.projekt.entities.zutat.Zutat;
import de.hsrm.mi.web.projekt.zutat.ui.ZutatFormular;

@Mapper(componentModel = "spring")
public interface DoenerMapper {
    @Mapping(target = "id", ignore = true)
    Doener doenerFormularToDoener(DoenerFormular formular);

    DoenerFormular doenerToDoenerFormular(Doener doener);

    List<ZutatFormular> zutatListToZutatFormList(List<Zutat> lstz);

    DoenerDTO doenerToDoenerDTO(Doener d);

    List<DoenerDTO> doenerListToDoenerDTOList(Collection<Doener> dList);

    ZutatDTO zutatToZutatDTO(Zutat z);
}
