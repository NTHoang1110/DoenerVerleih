package de.hsrm.mi.web.projekt.benutzer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import de.hsrm.mi.web.projekt.benutzer.ui.BenutzerFormular;
import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;

@Mapper(componentModel = "spring")
public interface BenutzerMapper {
    @Mapping(target = "losungWdh", ignore = true)
    @Mapping(target = "rolle", ignore = true)
    BenutzerFormular benutzerToBenutzerFormular(Benutzer benutzer);

    // @Mapping(target = "loginName", ignore = true)
    Benutzer benutzerFormularToBenutzer(BenutzerFormular formular);
}
