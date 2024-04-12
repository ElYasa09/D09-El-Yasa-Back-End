package com.myjisc.kelas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myjisc.kelas.repository.KontenMapelDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class KontenMapelRestServiceImpl implements KontenMapelRestService{
    @Autowired
    private KontenMapelDb kontenMapelDb;
}
