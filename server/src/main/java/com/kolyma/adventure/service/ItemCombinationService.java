package com.kolyma.adventure.service;

import com.kolyma.adventure.model.ItemCombination;
import com.kolyma.adventure.repository.ItemCombinationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCombinationService {

    private final ItemCombinationRepository repo;

    public ItemCombinationService(ItemCombinationRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Optional<ItemCombination> findByResources(Long res1Id, Long res2Id) {
        return repo.findByResourcePair(res1Id, res2Id);
    }

    public List<ItemCombination> getAll() {
        return repo.findAll();
    }
}