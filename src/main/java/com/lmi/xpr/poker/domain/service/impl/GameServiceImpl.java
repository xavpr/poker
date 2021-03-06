package com.lmi.xpr.poker.domain.service.impl;

import com.lmi.xpr.poker.domain.model.*;
import com.lmi.xpr.poker.domain.repository.DeckRepository;
import com.lmi.xpr.poker.domain.repository.GameRepository;
import com.lmi.xpr.poker.domain.repository.PlayerRepository;
import com.lmi.xpr.poker.domain.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final DeckRepository deckRepository;

    @Override
    public Game createGame() {
        log.info("Creating new game");
        Game game = new Game();
        return gameRepository.createGame(game);
    }

    @Override
    public boolean existsById(Long idGame) {
        return gameRepository.existsById(idGame);
    }

    @Override
    public Game addPlayerToGame(Long idGame, Long idPlayer) {
        //At this level game and player should exist
        log.info("Adding player {} to game {}", idPlayer, idGame);
        Game game = gameRepository.getById(idGame).get();
        Player player = playerRepository.getById(idPlayer).get();
        game.addPlayer(player);
        return gameRepository.saveGame(game);
    }

    @Override
    public Game removePlayerFromGame(Long idGame, Long idPlayer) {
        //At this level game and player should exist
        log.info("Removing player {} from game {}", idPlayer, idGame);
        return gameRepository.removePlayer(idGame, idPlayer);
    }

    @Override
    public Game addDeckToGame(Long idGame, Long idDeck) {
        //At this level game and player should exist
        log.info("Adding deck {} to game {}", idDeck, idGame);
        Game game = gameRepository.getById(idGame).get();
        Deck deck = deckRepository.getById(idDeck).get();
        game.addDeck(deck);
        return gameRepository.saveGame(game);
    }

    @Override
    public void dealCard(Long idGame, Long idPlayer) {
        log.info("Adding deal card to player {} for game {}", idPlayer, idGame);
        Game game = gameRepository.getById(idGame).get();
        Player player = playerRepository.getById(idPlayer).get();
        player.receiveACard(game.dealCardFromDecks());
        playerRepository.savePlayer(player);
    }

    @Override
    public List<Score> getScore(Long idGame) {
        log.info("Get score for game {}", idGame);
        Game game = gameRepository.getById(idGame).get();
        return game.getGameScoreByPlayer();
    }

    public void shuffle(Long idGame) {
        log.info("Shuffle game deck {}", idGame);
        Game game = gameRepository.getById(idGame).get();
        game.shuffleGameDeck();
        deckRepository.saveDecks(game.getDecks());
    }

    @Override
    public List<Card> getPlayerHandGame(Long idGame, Long idPlayer) {
        log.info("Shuffle game deck {}", idGame);
        Game game = gameRepository.getById(idGame).get();
        Player player = playerRepository.getById(idPlayer).get();
        return player.getPlayerHandGameByDeckIds(game.getDeckIds());
    }

    @Override
    public GameDeckStatus getGameDeckStatus(Long idGame) {
        log.info("Get game deck status for id {}", idGame);
        Game game = gameRepository.getById(idGame).get();
        GameDeckStatus status = new GameDeckStatus();
        status.init(game.getAvailableCards());
        return status;
    }

    @Override
    public void deleteGame(Long idGame) {
        log.info("Deleting game with id {}", idGame);
        gameRepository.deleteGameById(idGame);
    }

}
