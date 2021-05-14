package ru.naumen.ectmapi.service;

import lombok.RequiredArgsConstructor;
import org.postgis.Point;
import org.springframework.stereotype.Service;
import ru.naumen.ectmapi.entity.TreesCluster;
import ru.naumen.ectmapi.repository.TreesClusterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreesClusterService {

    private final TreesClusterRepository treesClusterRepository;

    public List<TreesCluster> getInRegion(Point topLeft, Point bottomRight) {
        return treesClusterRepository.findInRegion(topLeft, bottomRight);
    }
}
