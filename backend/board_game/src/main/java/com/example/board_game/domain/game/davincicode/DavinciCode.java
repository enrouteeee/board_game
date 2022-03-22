package com.example.board_game.domain.game.davincicode;

import com.example.board_game.domain.game.Game;

import java.util.ArrayList;
import java.util.List;

public class DavinciCode extends Game {
    private List<Player> players = new ArrayList<>();
    private Board board;

    private int numberOfLivePlayers;    // 1 이면 게임 종료

    public DavinciCode(Long roomId, int numberOfUsers) {
        super(roomId, numberOfUsers);
    }

    @Override
    public boolean checkStart() {
        int num = getNumberOfUsers();
        return num <= 4 && num >= 2;
    }

    @Override
    public void start(List<Long> userIds) {
        int numberOfUsers = getNumberOfUsers();
        for (Long userId : userIds) {
            this.players.add(new Player(userId));
        }
        numberOfLivePlayers = numberOfUsers;

        board = new Board();
        board.init();
    }

    /*
    플레이어가 보드에서 카드를 가져오기
     */
    public void selectCard(Long playerId, CardNumber number, CardColor color) {
        Card selectedCard = board.selected(number, color);
        Player player = findPlayer(playerId);
        player.addCard(selectedCard);
    }
    public void selectCard(Long playerId, CardNumber number, CardColor color, int index) {
        Card selectedCard = board.selected(number, color);
        Player player = findPlayer(playerId);
        player.addCard(selectedCard, index);
    }

    /*
    카드 선택이 끝났는지(각자 3~4장씩 카드를 가져왔는지) 확인
     */
    public boolean checkPlayerReady(){
        boolean flag = false;
        if(getNumberOfUsers() == 4) {
            for (Player p : players) {
                flag = p.getNumberOfCard() == 3;
            }
        } else {
            for (Player p : players) {
                flag = p.getNumberOfCard() == 4;
            }
        }
        return flag;
    }

    /*
    선수가 다른 선수의 카드를 지목해서 맞추기
    맞은 경우 턴을 이어 나갈지 종료할지 선택,
    틀린 경우 자신이 가져온 패를 뒤집고 턴을 종료
     */
    public boolean predictCard(Long playerId1, Long playerId2, CardNumber expectedCard, int index) {
        boolean flag = checkCardNumber(playerId2, expectedCard, index);
        Player player;
        if(flag) {
            player = findPlayer(playerId2);
            player.flipCard(index);
        } else {
            player = findPlayer(playerId1);
            player.flipCard(player.getLastAddedCardIndex());
        }

        // 아웃된 플레이어 확인
        if(player.getState() == PlayerState.OUT){
            numberOfLivePlayers--;
        }

        return flag;
    }

    public boolean checkCardNumber(Long playerId, CardNumber card, int index) {
        Player player = findPlayer(playerId);
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

    public List<Card> getCards() {
        return board.getCards();
    }

    public List<Long> getOrder() {
        List<Long> order = new ArrayList<>();
        for (Player player : players) {
            order.add(player.getId());
        }
        return order;
    }
}
