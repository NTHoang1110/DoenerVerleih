package de.hsrm.mi.web.projekt.doener.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DoenerNamingServiceImpl implements DoenerNamingService {
    private RestClient restClient = RestClient.create("https://pokeapi.co/");

    private record NameOnly(String name) {
    }

    @Override
    public String getName(int id) {
        try {
            NameOnly response = restClient.get().uri("/api/v2/pokemon/{id}", id).retrieve().body(NameOnly.class);
            return response.name + "dön";
        } catch (Exception e) {
            return "FehlerDön-" + id;
        }
    }

}
