package com.lmi.xpr.poker.data.repository.impl;

import com.lmi.xpr.poker.data.mapper.PlayerEntityMapper;
import com.lmi.xpr.poker.data.repository.PlayerEntityRepository;
import com.lmi.xpr.poker.domain.model.Player;
import com.lmi.xpr.poker.domain.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class PlayerRepositoryImpl implements PlayerRepository {

    private final PlayerEntityRepository repository;

    private final PlayerEntityMapper mapper;

    @Override
    public Player createPlayer(Player player) {
        log.info("Starting player creation for: {} - {}", player.getFirstName(), player.getLastName());
        Player result = mapper.toModel(repository.save(mapper.toEntity(player)));
        log.info("Player successfully created with id: {}", result.getIdPlayer());
        return result;
    }

    @Override
    public List<Player> getAll() {
        return mapper.toModels(repository.findAll());
    }
}
