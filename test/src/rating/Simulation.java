package rating;

import java.util.*;

public class Simulation {

    private static final List<Player> players = new ArrayList<>();

    private static int baseRating;
    private static int numOfGame;
    private static int standardRating;
    private static int maxScore;
    private static int minScore;
    private static double modScore;

    public static class Params {
        int sR;
        int maxS;
        int minS;
        double modS;

        public Params(int sR, int maxS, int minS, double modS) {
            this.sR = sR;
            this.maxS = maxS;
            this.minS = minS;
            this.modS = modS;
        }

        @Override
        public String toString() {
            return "{" +
                    "sR=" + sR +
                    ", maxS=" + maxS +
                    ", minS=" + minS +
                    ", modS=" + modS +
                    "} ";
        }
    }

    public static void main(String[] args) {

        baseRating = 1000;
        numOfGame = 500000;

        List<Params> paramsList = new ArrayList<>();


        /*
        // 조건에 맞는 파라미터 찾기
        for(int a=0; a<10; a++){
            standardRating = 100 + a*20;
            for(int b=0; b<10; b++){
                maxScore = 100 + b*20;
                for(int c=7; c<9; c++) {
                    minScore = c * 2;
                    for(int d=0; d<2; d++) {
                        modScore = 2.6 + d*0.1;

                        simulation();

                        // rating 으로 정렬
                        players.sort(new Comparator<Player>() {
                            @Override
                            public int compare(Player p1, Player p2) {
                                return p1.getRating() - p2.getRating();
                            }
                        });

                        Params p = new Params(standardRating, maxScore, minScore, modScore);

                        // 최대 최소값이 어느정도 범위 이내
                        int rf = players.get(0).getRating();
                        int rl = players.get(players.size() - 1).getRating();

                        // 평균이 1100, 1200, 1300 대
                        int averageR = 0;
                        int averageNG = 0;
                        for (Player player : players) {
                            averageR += player.getRating();
                            averageNG += player.getNumOfGame();
                        }
                        averageR /= players.size();
                        averageNG /= players.size();
                        averageR /= 100;

                        System.out.println(p +" "+ rf +" "+ rl +" " + averageR*100 + " " +averageNG);

                        if (rf < 400 || rf > 900)
                            continue;
                        if (rl > 2300 || rl < 1600)
                            continue;
                        if(!(averageR == 11 || averageR == 12 || averageR == 13))
                            continue;



                        // 조건에 맞는 파라미터 리스트에 추가
                        paramsList.add(p);

                        System.out.println("$");

                        System.out.println("{"
                                + players.get(0).getRating()
                                + " "
                                + players.get(players.size() - 1).getRating()
                                + "}");
                    }
                }
            }
        }
        */

        paramsList.add(new Params(240, 120, 16, 2.7));
//        paramsList.add(new Params(260, 100, 14, 2.6));
//        paramsList.add(new Params(260, 120, 16, 2.7));
        paramsList.add(new Params(280, 100, 14, 2.6));


        // 조건에 맞는 파라미터로 시뮬레이션 돌려서 분포 확인
        for (Params params : paramsList) {
            standardRating = params.sR;
            maxScore = params.maxS;
            minScore = params.minS;
            modScore = params.modS;

            System.out.print("{"
                    + standardRating
                    + " "
                    + maxScore
                    + " "
                    + minScore
                    + " "
                    + modScore
                    + "} : ");

            for (int c = 0; c < 3; c++) {
                simulation();

                players.sort(new Comparator<Player>() {
                    @Override
                    public int compare(Player p1, Player p2) {
                        return p1.getRating() - p2.getRating();
                    }
                });

                int averageNG = 0;
                for (Player player : players) {
                    averageNG += player.getNumOfGame();
                }
                averageNG /= players.size();

                System.out.println("{"
                        + players.get(0).getRating()
                        + " "
                        + players.get(players.size() - 1).getRating()
                        + " "
                        + averageNG
                        + "} ");
                printRating();

//                for (Player player : players) {
//                    System.out.println(player);
//                }
            }
            System.out.println();
        }

    }

    private static void printAbility() {
        // 능력치로 정렬
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.getAbility() - p2.getAbility();
            }
        });

        printPlayers();
    }

    private static void printRating() {
        // 레이팅으로 정렬
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.getRating() - p2.getRating();
            }
        });

        printPlayers();
    }

    private static void printPlayers() {
        System.out.println("*");
        int sumA = 0;
        int sumR = 0;
        int cnt = 1;
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getRating()/10 != players.get(i - 1).getRating()/10) {
                System.out.print(sumA/cnt + " ");
                System.out.println(sumR/cnt + " ");
                sumA = 0;
                sumR = 0;
                cnt = 0;
            }
            System.out.print("*");
            sumR += players.get(i).getRating();
            sumA += players.get(i).getAbility();
            cnt++;
        }
        System.out.println();
    }

    public static void simulation() {
        // player data 세팅 : 능력치 삼각 그래프
        players.clear();
        long uid = 1L;
        for(int i=1; i<=100; i++) {
            for(int c=0; c<-Math.abs(i-50) + 50; c++) {
                players.add(new Player(uid++, i, baseRating));
            }
        }

        Random rd = new Random();


        // 배틀 세팅 - 랜덤 뽑기 게임 수 균등
        for(int i=0; i<numOfGame; i++) {
            List<Player> combination = new ArrayList<>();
            int capacity = rd.nextInt(3) + 2;

            // 배틀 조합 뽑기
            int[] check = new int[players.size()];
            for(int c=0; c<capacity; c++) {
                int index = rd.nextInt(players.size());
                while(check[index] != 0)
                    index = rd.nextInt(players.size());
                check[index] = 1;

                combination.add(players.get(index));
            }

            List<Player> battleResult = battle(combination);

            updateRating(battleResult);
        }

    }

    public static List<Player> battle(List<Player> players) {
        List<Player> ranking = new ArrayList<>();

        //플레이어 게임 수 더하기
        for (Player player : players) {
            player.playGame();
        }

        // 높은 능력치가 높은 확률로 뽑히게 한명씩 순위에 기록
        Random rd = new Random();

        while(players.size() > 0) {
            int sumAbility = 0;
            for (Player player : players) {
                sumAbility += player.getAbility();
            }

            int r = rd.nextInt(sumAbility);
            for (Player player : players) {
                if(r <= player.getAbility()) {
                    ranking.add(player);
                    players.remove(player);
                    break;
                }
                r -= player.getAbility();
            }
        }

        // 우승자
        ranking.get(0).winGame();

        return ranking;
    }

    // 랭킹을 받아서 점수를 업데이트
    // 점수차이가 많이 나는 사람을 이길수록 더 많은 점수 받을수있게
    // 점수차이가 많이 나는 사람에게 질수록 더 적은 점수 줄어들게
    public static void updateRating(List<Player> players) {
        int size = players.size();
        for(int i=0; i<size; i++) {
            int addRating = 0;
            for(int j=0; j<size; j++) {
                if(i < j) {
                    //이긴 경우
                    int iRating = players.get(i).getRating();
                    int jRating = players.get(j).getRating();

                    int subRating = jRating - iRating;
                    if(subRating > standardRating) {
                        addRating += maxScore;
                    } else if(subRating > standardRating * 0.8) {
                        addRating += maxScore * 0.8;
                    } else if(subRating > standardRating * 0.6) {
                        addRating += maxScore * 0.6;
                    } else if(subRating > standardRating * 0.4) {
                        addRating += maxScore * 0.4;
                    } else if(subRating > standardRating * 0.2) {
                        addRating += maxScore * 0.2;
                    } else {
                        addRating += minScore;
                    }
//                    addRating *= 1.5;

                } else if (i > j) {
                    //진 경우
                    int iRating = players.get(i).getRating();
                    int jRating = players.get(j).getRating();

                    int subRating = jRating - iRating;
                    if(subRating > standardRating) {
                        addRating -= minScore;
                    } else if(subRating > standardRating * 0.8) {
                        addRating -= maxScore * 0.2;
                    } else if(subRating > standardRating * 0.6) {
                        addRating -= maxScore * 0.4;
                    } else if(subRating > standardRating * 0.4) {
                        addRating -= maxScore * 0.6;
                    } else if(subRating > standardRating * 0.2) {
                        addRating -= maxScore * 0.8;
                    } else {
                        addRating -= maxScore;
                    }
                }
                addRating /= modScore;
            }
            addRating /= (size -1);
            players.get(i).addRating(addRating);

        }
    }
}
