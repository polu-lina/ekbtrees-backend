package ru.naumen.ectmapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.naumen.ectmapi.entity.Tree;
import ru.naumen.ectmapi.repository.TreeRepository;

@Service
@RequiredArgsConstructor
public class TreeService {

    private final TreeRepository treeRepository;

    public void save(Tree tree){
        treeRepository.save(tree);
    }

    public Tree get(Long id){
        return treeRepository.findById(id).get();
    }

    public void delete(Long id) {treeRepository.deleteById(id);}
}
