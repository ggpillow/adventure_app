package com.kolyma.adventure.service;

import com.kolyma.adventure.model.Effect;
import com.kolyma.adventure.repository.EffectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EffectService {

    private final EffectRepository effectRepository;

    public EffectService(EffectRepository effectRepository) {
        this.effectRepository = effectRepository;
    }

    public List<Effect> getAll() {
        return effectRepository.findAll();
    }

    public Optional<Effect> getById(Long id) {
        return effectRepository.findById(id);
    }
}