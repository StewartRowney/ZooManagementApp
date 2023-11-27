package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Zoo;

import java.util.List;

public interface IZooService {
    List<Zoo> findAllZoos();
}
