package ru.naumen.ectmapi.entity;

import lombok.Data;
import org.postgis.Point;

/**
 * Кластер деревьев
 */
@Data
public class TreesCluster {

    /**
     * Географическая точка, центр кластера
     */
    private Point centre;

    /**
     * Количество деревьев в кластере
     */
    private int count;
}
