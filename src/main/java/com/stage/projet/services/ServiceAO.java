package com.stage.projet.services;

import com.stage.projet.models.Appeldoffre;
import com.stage.projet.models.RepoAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ServiceAO {
    @Autowired
    private RepoAO repoAO;
    public Appeldoffre addAppeldoffre(@NotNull Appeldoffre Appeldoffre) {
        return repoAO.save(Appeldoffre);
    }

    public List<Appeldoffre> findAllAppeldoffres() {
        return repoAO.findAll();
    }

    public Appeldoffre updateAppeldoffre(Appeldoffre Appeldoffre) {
        return repoAO.save(Appeldoffre);
    }

    public Appeldoffre findAppeldoffreById(Integer id) {
        return repoAO.findById(id)
                .orElse(null);
    }
  

    public void deleteAppeldoffre(Integer id){
        repoAO.deleteById(id);
    }
}
