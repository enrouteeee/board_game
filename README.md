# board\_game

## 0\. 목차

### 개요

### 프로젝트 환경

### 동작 화면

### 구현한 것

### 구현하지 못한 것
<br/>

## 1\. 개요

웹사이트에서 동작하는 보드게임 어플리케이션을 구현한다.

다빈치 코드 게임을 시작으로 다양한 게임을 추가한다. 점수 시스템, 매칭 시스템 등 다양한 시스템을 추가한다.

다양한 게임과 서비스를 추가 확장할 수 있도록 객체지향적 설계를 적용한다.

클라이언트와 서버를 구현 및 배포까지 프로젝트가 진행되면서 요구되는 전반적인 경험을 해본다.
<br/><br/>

## 2\. 프로젝트 환경

#### **Spring boot**

-   Java 1.8
-   Spring boot 2.6.4
-   Spring Web MVC
-   Spring Security OAuth2
-   Spring boot WebSocket
-   Stomp-WebSocket

#### **DB**

-   H2
-   MariaDB

#### **Vue**

-   Vue 2.6.14
-   Vuex
-   Vue-Router
-   Axios
-   Vuetify

#### **Deploy**

-   Backend : AWS EC2
-   DB : AWS RDS
-   Frontend : Netlify
<br/><br/>

## 3\. 동작 화면

로그인 화면으로 소셜로그인(구글, 카카오, 네이버)를 이용해서 로그인 한다
<br/>
<img width="600" alt="로그인" src="https://user-images.githubusercontent.com/87449055/163831458-98c92e95-2656-472e-91d3-ee356ad3f425.png">
<br/>
<br/>
최초 로그인 시 닉네임을 지정한다
<br/>
<img width="600" alt="닉네임입력" src="https://user-images.githubusercontent.com/87449055/163831475-136cd13e-3bd9-4ae4-bc06-428c505cff51.png">
<br/>
<br/>
로그인 완료시 게임 방 리스트를 나타내는 화면이 나타난다
<br/>
<img width="600" alt="방리스트" src="https://user-images.githubusercontent.com/87449055/163831479-482b892f-931a-4418-bd6d-6e3d6432bbd4.png">
<br/>
<br/>
방만들기 버튼을 누르면 방 정보를 입력할 수 있는 모달 창이 나타난다
<br/>
<img width="600" alt="방만들기" src="https://user-images.githubusercontent.com/87449055/163831481-805e5d05-c01a-4f31-84dc-a343efe3dd20.png">
<br/>
<br/>
방에 입장하면 간단한 채팅을 나눌 수 있다
<br/>
<img width="600" alt="방내부" src="https://user-images.githubusercontent.com/87449055/163831482-e8cfe266-80f6-486b-8496-523775fced6f.png">
<br/>
<br/>
게임을 시작하면 각자 카드를 4장씩 선택한다
<br/>
<img width="600" alt="게임시작" src="https://user-images.githubusercontent.com/87449055/163831485-3db52c42-122e-475f-b602-a950b0d88a0c.png">
<br/>
<br/>
카드를 모두 선택했으면, 돌아가면서 보드판 위에서 카드를 한장 선택후 다른 플레이어의 카드를 예측한다
<br/>
<img width="600" alt="카드선택" src="https://user-images.githubusercontent.com/87449055/163831487-952eb4fa-a27d-474c-97bd-7233c1b4912b.png">
<br/>
<img width="600" alt="카드예측" src="https://user-images.githubusercontent.com/87449055/163831489-ba36a2de-debf-4701-bba7-9f89bf447314.png">
<br/>
<br/>
카드 예측을 실패한 경우 자신이 마지막으로 뽑은 (뒤집어 지지 않은)카드가 뒤집어진다
<br/>
<img width="600" alt="카드예측실패" src="https://user-images.githubusercontent.com/87449055/163831490-027c2be5-4adb-4391-ad73-919b8ed92f0e.png">
<br/>
<br/>
카드 예측에 성공한 경우 예측한 카드가 뒤집어지고, 예측을 계속할 것인지, 차례를 넘길 것인지 선택한다
<br/>
<img width="600" alt="카드예측성공" src="https://user-images.githubusercontent.com/87449055/163831491-a13acd94-a1f4-4ab3-95f6-02846543ed5d.png">
<br/>
<br/>
자신의 카드가 모두 뒤집어 지면 게임에서 아웃되고 차례가 스킵된다
<br/>
<img width="600" alt="플레이어죽음" src="https://user-images.githubusercontent.com/87449055/163831493-755b44bf-a5b5-4a49-8465-73f2484ca1e4.png">
<br/>
<br/>
플레이어가 모두 아웃되고 한명만 살아 남으면 게임이 종료된다
<br/>
<img width="600" alt="게임종료" src="https://user-images.githubusercontent.com/87449055/163831497-b1e3835b-f5a0-47a3-af4e-37419ad7e348.png">
<br/>
<br/>
매칭 조건(인원수)를 선택해서 매칭 대기열에 진입한다
<br/>
<img width="600" alt="매칭시작" src="https://user-images.githubusercontent.com/87449055/163831499-8d5a0a2c-d0f1-4d44-ab30-32e9e89137a2.png">
<br/>
<br/>
매칭진행중으로 매칭이 완료되면 조건에 맞는 플레이어들과 자동으로 방에 입장하게 된다
<br/>
<img width="600" alt="매칭중" src="https://user-images.githubusercontent.com/87449055/163831503-f1abd6ef-54df-462e-986c-8f4f6a2a7fe5.png">
<br/><br/>

## 4\. 구현한 것

### - **로그인**

OAuth2 + JWT 를 이용한 소셜 로그인


<br/><br/>

### - **게임방**

<br/>
<img width="400" alt="게임방객체" src="https://user-images.githubusercontent.com/87449055/163846172-51988e62-86e9-4a8d-ba00-dcdd18f2568f.png">
<br/>

```
@Getter
@RequiredArgsConstructor
public enum GameInfo {
    DAVINCI_CODE(2, 4),
    HALLI_GALLI(2, 6);

    private final int minNumOfPlayer;
    private final int maxNumOfPlayer;

    public boolean checkStart(int num) {
        return num <= maxNumOfPlayer && num >= minNumOfPlayer;
    }

    public Game createGame(Room room) {
    	if(!this.checkStart(room.getNumberOfUsers())))
        	return null;
        switch(this) {
            case DAVINCI_CODE:
            	return new DavinciCode(room);
            case HALLI_GALLI:
                return new HalliGalli(room);
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
```
Room 은 게임을 변경 할 책임과 게임을 시작 할 책임을 갖는다. Room은 이에 대한 책임을 전문가인 GameInfo에게 요청을 한다. GameInfo는 게임의 종류를 나타내는 enum class로 자기 자신의 타입에 따라 각 게임을 생성할 수 있다.<br/>
Room 은 Game을 의존하고 있다. 게임이 종료되었을 때 Game을 null로 초기화해서 GC 대상이 되게 하기 위해서이다.

<br/><br/>

### - **다빈치코드 게임**

<br/>
<img width="400" alt="다빈치코드객체" src="https://user-images.githubusercontent.com/87449055/163846185-4e5f7604-eabd-4d88-a021-3e136d31e07c.png">
<br/>
각각의 Command 요청이 오면 Board, Player와 함께 협력하여 요구사항을 처리한다.

<br/><br/>
### - **점수 시스템**

<br/>
<img width="400" alt="게임옵저버객체" src="https://user-images.githubusercontent.com/87449055/163846188-535d8737-b1ea-46ea-9679-6731ac493cec.png">
<br/>
게임이 끝난경우 Room의 상태를 게임중에서 모집중으로 변경해야하고, RatingService에서 게임 결과에 따른 점수 결과를 업데이트 해야한다.<br/>
게임 종료가 발생했을때 해당 게임(Publisher)는 Room과 RatingService(Observer)에 notify해준다.<br/>
옵저버 패턴을 이용해서 느슨한 결합으로 구현했지만 계속된 update가 아닌 단 한번의 notify이다. Spring Event를 공부해 보도록 하도록 하자.(ex. GameFinishedEvent)

<br/><br/>
[시뮬레이션 코드 링크](https://github.com/enrouteeee/board_game/tree/main/test/src/rating)
<br/>
<img width="500" alt="시뮬레이션결과화면" src="https://user-images.githubusercontent.com/87449055/163861185-0da477e1-9572-4434-92f0-aadf3f22581d.png">
<br/>
<시뮬레이션 점수 분포 그림><br/>
점수 업데이트 하는 로직은 점수차이가 많이 나는 플레이어를 이긴경우 점수를 많이 획득하고, 점수차이가 많이 나는 플레이어에게 패배한 경우 점수를 적게 잃게한다.<br/>
몇 가지 보정값을 설정후 시뮬레이션을 돌려서 이상적인(내가 생각하기에) 점수 분포를 만족하는 파라미터들을 찾아내어 적용하였다.

<br/><br/>
### - **매칭 시스템**

<br/>
<img width="400" alt="매칭객체" src="https://user-images.githubusercontent.com/87449055/163846191-65d16775-2118-4eb6-9884-0cdaff3eec21.png">
<br/>
매칭 서비스는 요청이 들어오면 요청의 종류에 따라 알맞은 매칭 시스템을 선택해서 요구사항을 처리한다.

```
public class MatchingQueue {
    private final List<Vector<Long>> queue;     // userId(Long) 큐
    private final int capacity;                 // 첫번째 조건 : 인원수
    private final int[] conditions;             // 부수적인 조건들

    public MatchingQueue(int capacity, int... condition) {
        this.capacity = capacity;
        this.conditions = condition;
        int multi = (capacity+1);
        for (int cnt : condition) {
            multi *= cnt;
        }
        this.queue = new ArrayList<>(multi);
        for (int i=0; i<multi; i++) {
            queue.add(new Vector<>());
        }
    }

    public List<Long> enQueue(Long userId, int capacity, int... condition) {
        checkCondition(capacity, condition);

        int index = findIndex(capacity, condition);

        queue.get(index).add(userId);

        List<Long> result = null;
        if(queue.get(index).size() == capacity) {
            result = new ArrayList<>(queue.get(index));
            queue.get(index).clear();
        }

        return result;
    }

    // 조건으로부터 index 를 찾는 메서드
    private int findIndex(int capacity, int[] condition) {
        ...
    }

    // 조건을 확인하는 메서드
    private void checkCondition(int capacity, int[] condition) {
        ...
    }
}
```
<br/>
<img width="500" alt="매칭그림" src="https://user-images.githubusercontent.com/87449055/163857823-967cfb81-5869-4c30-9da5-889a66b4a4f9.png">
<br/>

각 시스템은 적절한 MatchingQueue를 생성한다. 매칭큐는 벡터의 리스트로 이루어져 있다. 각 벡터에 같은 조건(게임 종류, 인원수, 점수 등)의 유저들이 추가되며, 인원수가 만족되면 리턴된다.<br/>
다차원 배열은 2차원 배열로 나타낼수 있다. int[][][] array = new int[a][b][c] 일때 array[x][y][z] = array[x*b*c + y*c + z] 이다.<br/>
이를 이용해서 매칭 큐를 초기화 한다. 예를 들어 매칭 조건이 3가지가 있고, 각 조건은 3,5,6 가지 상태를 가질 수 있다고 한다면 queue[3][5][6] = queue[3x5x6] 로 초기화 할 수 있다.

<br/><br/>

## 5\. 해야할 것

로그인 : refresh token 기능 구현

배포 + https 설정 : 클라이언트(https)와 서버(http) Mixed content 문제 발생, 서버 https 설정하기

게임 추가 : 게임(할리갈리)를 추가하고 방에서 게임을 선택할 수 있도록 변경한다.

validation : Http Request로 오는 값(DTO)를 검증 없이 사용하고 있다. validation 적용하기

예외 처리 : 검증에 대한 오류나 로직에 대한 오류에 있어서 서버에서 제대로 된 응답을 주기

페이지를 강제로 나가거나, 새로고침 한 경우 처리 : 정상적으로 stomp연결을 해제하지 않는 경우에 대해 적절한 로직을 추가해주기

프론트엔드 리펙토링 : .view 파일마다 각 페이지에 대한 모든 소스코드가 전부 들어가 있다. 컴포넌트, 모듈 등을 이용해서 리펙토링 하기

~디자인 : 예쁘게 디자인 하기~
<br/><br/>
