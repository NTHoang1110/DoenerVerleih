package de.hsrm.mi.web.projekt.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.benutzer.services.BenutzerService;
import de.hsrm.mi.web.projekt.entities.benutzer.Benutzer;

@Service
public class BenutzerUserDatailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BenutzerService benutzerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBuilder userBuilder = User.builder();
        Optional<Benutzer> benutzer = benutzerService.findBenutzerById(username);
        if (username.equals("admin")) {
            UserDetails user = userBuilder.username(username).password(passwordEncoder.encode(username)).roles("ADMIN")
                    .build();

            return user;
        } else if (benutzer.isPresent()) {
            UserDetails user = userBuilder.username(username).password(benutzer.get().getLosung())
                    .roles(benutzer.get().getRolleAuswahl()).build();
            return user;
        } else {
            throw new UsernameNotFoundException("No username found. Try again");
        }

    }

}
