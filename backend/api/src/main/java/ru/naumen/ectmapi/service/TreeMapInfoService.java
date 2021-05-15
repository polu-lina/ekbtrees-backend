package ru.naumen.ectmapi.service;

import lombok.RequiredArgsConstructor;
import org.postgis.Point;
import org.springframework.stereotype.Service;
import ru.naumen.ectmapi.entity.TreeMapInfo;
import ru.naumen.ectmapi.repository.TreeMapInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeMapInfoService {

    private final TreeMapInfoRepository treeMapInfoRepository;

    public List<TreeMapInfo> getInRegion(Point topLeft, Point bottomRight) {
        return treeMapInfoRepository.findInRegion(topLeft, bottomRight);
    }
}
