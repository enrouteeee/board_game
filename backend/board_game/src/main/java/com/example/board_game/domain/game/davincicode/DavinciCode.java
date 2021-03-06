package com.example.board_game.domain.game.davincicode;

import com.example.board_game.domain.game.Game;
import com.example.board_game.domain.room.Room;
import com.example.board_game.domain.user.User;

import java.util.ArrayList;
import java.util.List;

public class DavinciCode extends Game {
    private List<Player> players;
    private Board board;

    private int numberOfALivePlayers;    // 1 이면 게임 종료

    public DavinciCode(Room room) {
        super(room);
    }

    @Override
    public void init(List<User> users) {
        int numberOfUsers = users.size();
        players = new ArrayList<>();
        for (User user : users) {
            this.players.add(new Player(user));
        }
        numberOfALivePlayers = numberOfUsers;

        board = new Board();
        board.init();
    }

    @Override
    public void exitPlayer(Long playerId) {
        Player player = findPlayer(playerId);
        if(player.getState() == PlayerState.PLAYING) {
            player.AllFlip();
            updateLeaderBoard(player.getId());

            if(--numberOfALivePlayers == 1) {
                finishGame();
            }
        }
    }

    private void finishGame() {
        Player winner = findWinner();
        updateLeaderBoard(winner.getId());
        System.out.println("게임 끝");
        finish();
    }

    /*
    플레이어가 보드에서 카드를 가져오기
     */
    public void selectCard(Long playerId, int boardIdx, Card card) {
        Card selectedCard = board.selected(card, boardIdx);

        Player player = findPlayer(playerId);
        player.selectCard(selectedCard);
    }

    /*
    보드에서 가져온 카드 위치 설정하기
     */
    public void selectCardPosition(Long playerId, int playerCardIdx, Card card) {
        Player player = findPlayer(playerId);
        player.selectCardPosition(playerCardIdx, card);
    }

    /*
    선수가 다른 선수의 카드를 지목해서 맞추기
    맞은 경우 턴을 이어 나갈지 종료할지 선택,
    틀린 경우 자신이 가져온 패를 뒤집고 턴을 종료
     */
    public boolean predictCard(Long userId, int playerIdx, CardNumber expectedCard, int index) {
        boolean flag = checkCardNumber(playerIdx, expectedCard, index);
        Player player;
        if(flag) {
            player = players.get(playerIdx);
            player.flipCard(index);
        } else {
            player = findPlayer(userId);
            player.flipLastCard();
        }

        // 아웃된 플레이어 확인
        if(player.getState() == PlayerState.OUT){
            updateLeaderBoard(player.getId());
            //게임 종료 이벤트
            if(--numberOfALivePlayers == 1) {
                finishGame();
            }
        }

        return flag;
    }

    public boolean checkCardNumber(int playerIdx, CardNumber card, int index) {
        Player player = players.get(playerIdx);
        return player.checkCardNumber(card, index);
    }


    public Player findPlayer(Long id){
        for (Player player : players) {
            if(player.getId().equals(id)){
                return player;
            }
        }
        return null;
    }

    public Player findWinner() {
        if(numberOfALivePlayers != 1)
            return null;

        for (Player player : players) {
            if(player.getState() == PlayerState.PLAYING)
                return player;
        }
        return null;
    }

    public List<Card> getCards() {
        return board.getCards();
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}
