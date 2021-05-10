package ru.naumen.ectmapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmapi.entity.Tree;
import ru.naumen.ectmapi.repository.TreeRepository;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TreeService {

    private final TreeRepository treeRepository;

    public void save(Tree tree){
        treeRepository.save(tree);
    }

    public Tree get(Long id){
        return treeRepository.findById(id).orElseThrow();
    }

    public void delete(Long id) {treeRepository.deleteById(id);}
}
