package ru.ekbtreeshelp.api.tree.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ekbtreeshelp.core.entity.SpeciesTree;
import ru.ekbtreeshelp.core.repository.SpeciesTreeRepository;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeciesTreeService {

    private final SpeciesTreeRepository speciesTreeRepository;

    public Long save(SpeciesTree speciesTree) {
        return speciesTreeRepository.save(speciesTree).getId();
    }

    public List<SpeciesTree> getAll() {
        var all = speciesTreeRepository.findAll();
        return all.stream().sorted((o1, o2) -> {
            if ((o1.getTitle().equals("Другое") && o2.getTitle().equals("Невозможно определить")) ||
                (o2.getTitle().equals("Другое") && o1.getTitle().equals("Невозможно определить"))) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            if (o1.getTitle().equals("Другое") || o1.getTitle().equals("Невозможно определить")) {
                return 1;
            }
            if (o2.getTitle().equals("Другое") || o2.getTitle().equals("Невозможно определить")) {
                return -1;
            }
            return o1.getTitle().compareTo(o2.getTitle());
        }).toList();
    }

    public void delete(Long id) {
        speciesTreeRepository.deleteById(id);
    }
}
