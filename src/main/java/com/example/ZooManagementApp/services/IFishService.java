package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Fish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFishService {
    List<Fish> findAllFish();
}
